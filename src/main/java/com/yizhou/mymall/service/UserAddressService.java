package com.yizhou.mymall.service;

import com.yizhou.mymall.entity.User;
import com.yizhou.mymall.entity.UserAddress;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yizhou
 * @since 2021-01-02
 */
public interface UserAddressService  {
    List<UserAddress> FindAllUserAddress(Integer id);


}
