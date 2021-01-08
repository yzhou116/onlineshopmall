package com.yizhou.mymall.entity;

import lombok.Data;

@Data
public class ProductVO {


    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Float price;

    private String fileName;
}
