package com.foodie.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * 应用模块名称： 用户展示商品评价的VO
 *
 * @author jamie
 * @since 2019/11/30 22:05
 */
@Data
public class ItemCommentVO {

    private Integer commentLevel;

    private String content;

    private String specName;

    private Date createdTime;

    private String userFace;

    private String nickname;

}
