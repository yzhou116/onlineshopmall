package com.yizhou.mymall.exception;

import com.yizhou.mymall.enums.ResultEnums;

public class MyException extends RuntimeException{
    public MyException(String error){
        super(error);
    }

    public MyException(ResultEnums resultEnums) {
     super(resultEnums.getMsg());
    }
}
