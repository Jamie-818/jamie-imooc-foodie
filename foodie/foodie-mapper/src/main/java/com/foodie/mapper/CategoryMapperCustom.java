package com.foodie.mapper;

import com.foodie.pojo.vo.CategoryVO;
import com.foodie.pojo.vo.NewItemsVO;

import java.util.List;

/**
 * 自定义 分类Mapper
 * @author jamie
 * @date 2019/11/28 23:14
 */
public interface CategoryMapperCustom {

    /**
     * 根据一级分类id获取子分类
     * @param rootCatId 一级分类ID
     * @return java.util.List
     * @author jamie
     * @date 2019/11/28 23:04
     */
    List<CategoryVO> getSubCatList(Integer rootCatId);

    /**
     * 查询首页每个一级分类下的6条信息商品信息
     * @param rootCatId 一级分类ID
     * @return java.util.List
     * @author jamie
     * @date 2019/11/28 23:43
     */
    List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);

}