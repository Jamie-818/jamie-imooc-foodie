package com.foodie.service;

import com.foodie.common.utils.PageR;
import com.foodie.pojo.Items;
import com.foodie.pojo.ItemsImg;
import com.foodie.pojo.ItemsParam;
import com.foodie.pojo.ItemsSpec;
import com.foodie.pojo.vo.CommentLevelCountsVO;
import com.foodie.pojo.vo.ItemCommentVO;
import com.foodie.pojo.vo.SearchItemsVO;
import com.foodie.pojo.vo.ShopCartVO;

import java.util.List;

/**
 * 应用模块名称：
 * @author jamie
 * @since 2019/11/27 12:39
 */
public interface ItemService {

    /**
     * 根据商品ID查询详情
     * @param itemId 商品Id
     * @return com.so.pojo.Items
     */
    Items queryItemById(String itemId);

    /**
     * 根据商品Id查询商品图片列表
     * @param itemId 商品Id
     * @return java.util.List<com.so.pojo.ItemsImg>
     */
    List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品Id查询商品规格列表
     * @param itemId 商品Id
     * @return java.util.List<com.so.pojo.ItemsSpec>
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品Id查询商品参数
     * @param itemId 商品Id
     * @return com.so.pojo.ItemsParam
     */
    ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品Id查询商品的评价等级数据
     * @param itemId 商品Id
     * @return com.so.vo.CommentLevelCountsVO
     */
    CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 根据商品Id查询商品的评价列表
     * @param itemId   商品Id
     * @param level    评价等级
     * @param page     页码
     * @param pageSize 页数
     * @return java.util.List<com.so.vo.ItemCommentVO>
     */
    PageR<ItemCommentVO> queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize);

    /**
     * 搜索商品列表
     * @param keywords 关键字
     * @param sort     排序
     * @param page     页码
     * @param pageSize 页数
     * @return com.so.utils.PagedGridResult
     */
    PageR<SearchItemsVO> searchItems(String keywords, String sort, Integer page, Integer pageSize);

    /**
     * 根据三级分类搜索商品列表
     * @param catId    分类ID
     * @param sort     排序
     * @param page     页码
     * @param pageSize 页数
     * @return com.so.utils.PagedGridResult
     */
    PageR<SearchItemsVO> searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize);

    /**
     * 根据规格IDS查询最新的购物车中商品数据(用于刷新渲染购物车中的商品数据)
     * @param specIds 商品规格IDS
     * @return java.util.List<com.so.vo.ShopCartVO>
     */
    List<ShopCartVO> queryItemsBySpecIds(String specIds);

    /**
     * 根据商品规格id获取规格对象的具体信息
     * @param specId 商品规格id
     * @return com.so.pojo.ItemsSpec
     */
    ItemsSpec queryItemSpecById(String specId);

    /**
     * 根据商品id获得商品图片主图url
     * @param itemId 商品id
     * @return java.lang.String
     */
    String queryItemMainImgById(String itemId);

    /**
     * 减少库存
     * @param specId    商品规格Id
     * @param buyCounts 购买数量
     */
    void decreaseItemSpecStock(String specId, Integer buyCounts);

}
