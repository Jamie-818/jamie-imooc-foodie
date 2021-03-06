package com.foodie.pojo.vo;

import com.foodie.pojo.Items;
import com.foodie.pojo.ItemsImg;
import com.foodie.pojo.ItemsParam;
import com.foodie.pojo.ItemsSpec;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 应用模块名称：商品详情VO
 * @author jamie
 * @since 2019/11/28 23:08
 */
@Data
@ApiModel(value = "商品详情VO", description = "从数据库返回的对象")
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ItemInfoVO {

    private Items item;

    private List<ItemsSpec> itemSpecList;

    private List<ItemsImg> itemImgList;

    private ItemsParam itemParams;

}
