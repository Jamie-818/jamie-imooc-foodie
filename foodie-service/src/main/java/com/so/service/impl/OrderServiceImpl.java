package com.so.service.impl;

import java.util.Date;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.so.bo.SubmitOrderBO;
import com.so.enums.OrderStatusEnum;
import com.so.enums.YesOrNo;
import com.so.mapper.OrderItemsMapper;
import com.so.mapper.OrderStatusMapper;
import com.so.mapper.OrdersMapper;
import com.so.pojo.*;
import com.so.service.AddressService;
import com.so.service.ItemService;
import com.so.service.OrderService;

/**
 * 应用模块名称：
 *
 * @author show
 * @since 2019/12/3 22:22
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderItemsMapper orderItemsMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private Sid sid;

    @Override
    public void createOrder(SubmitOrderBO bo) {
        String userId = bo.getUserId();
        String addressId = bo.getAddressId();
        String itemSpecIds = bo.getItemSpecIds();
        Integer payMethod = bo.getPayMethod();
        String leftMsg = bo.getLeftMsg();
        // 包邮费用设置为0
        Integer postAmount = 0;
        String orderId = sid.nextShort();
        // 1.新订单数据保存
        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setUserId(userId);
        UserAddress userAddress = addressService.queryAddress(userId, addressId);
        orders.setReceiverName(userAddress.getReceiver());
        orders.setReceiverMobile(userAddress.getMobile());
        orders.setReceiverAddress(userAddress.getProvince() + " " + userAddress.getCity() + " "
            + userAddress.getDistrict() + " " + userAddress.getDetail());
        orders.setPostAmount(postAmount);
        orders.setPayMethod(payMethod);
        orders.setLeftMsg(leftMsg);
        orders.setIsComment(YesOrNo.NO.type);
        orders.setIsDelete(YesOrNo.NO.type);
        orders.setCreatedTime(new Date());
        orders.setUpdatedTime(new Date());
        // 2.循环根据itemSpecIds保存订单商品信息表
        String[] itemSpecIdArr = itemSpecIds.split(",");
        // 商品原价累计
        Integer totalAmount = 0;
        // 优化后的时机支付价格累计
        Integer realPayAmount = 0;
        for (String itemSpecId : itemSpecIdArr) {
            // 2.1 根据规格id，查询规格的具体信息，主要获取价格
            ItemsSpec itemsSpec = itemService.queryItemSpecById(itemSpecId);
            // TODO: 2019/12/3 整合redis后，商品购买的数量重新从redis的购物车中获取
            int buyCounts = 1;
            Integer priceDiscount = itemsSpec.getPriceDiscount();
            Integer priceNormal = itemsSpec.getPriceNormal();
            totalAmount += priceNormal * buyCounts;
            realPayAmount += priceDiscount * buyCounts;
            // 2.2 根据商品id，获取商品图片信息以及商品图片
            String itemId = itemsSpec.getItemId();
            Items item = itemService.queryItemById(itemId);
            String imgUrl = itemService.queryItemMainImgById(itemId);
            // 2.3 循环保存子订单数据到数据库
            String subOrderId = sid.nextShort();
            OrderItems orderItems = new OrderItems();
            orderItems.setId(subOrderId);
            orderItems.setOrderId(orderId);
            orderItems.setItemId(itemId);
            orderItems.setItemImg(imgUrl);
            orderItems.setItemName(item.getItemName());
            orderItems.setItemSpecId(itemSpecId);
            orderItems.setItemSpecName(itemsSpec.getName());
            orderItems.setPrice(priceDiscount);
            orderItems.setBuyCounts(buyCounts);
            orderItemsMapper.insert(orderItems);
            // 2.4 在用户提交订单以后，规格表需要扣除库存
            itemService.decreaseItemSpecStock(itemSpecId, buyCounts);
        }
        orders.setTotalAmount(totalAmount);
        orders.setRealPayAmount(realPayAmount);
        ordersMapper.insert(orders);
        // 3.保存订单状态表
        OrderStatus waitPayOrderStatus = new OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waitPayOrderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(waitPayOrderStatus);
    }
}
