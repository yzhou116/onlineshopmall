package com.yizhou.mymall.service.impl;

import com.yizhou.mymall.entity.ProductVO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductCategoryVO {

    private Integer id;

    private String name;

    private List<ProductCategoryVO> children = new ArrayList<>();

    private String bannerImg;

    private String topImg;

    private List<ProductVO> productVOList;

    public ProductCategoryVO() {
    }

    public ProductCategoryVO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
