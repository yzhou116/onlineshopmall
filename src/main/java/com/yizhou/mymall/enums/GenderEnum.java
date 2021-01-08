package com.yizhou.mymall.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum GenderEnum {
    FEMALE(0, "FEMALE"),
    MALE(1,"MALE");

    @EnumValue
    private Integer code;
    private String value;

    GenderEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
