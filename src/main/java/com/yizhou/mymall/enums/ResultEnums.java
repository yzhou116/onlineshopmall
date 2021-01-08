package com.yizhou.mymall.enums;

import lombok.Getter;

@Getter
public enum ResultEnums {
    STOCK_ERROR(1,"STOCK IS NOT ENOUGH");
    private Integer code;
    private String msg;

    ResultEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
