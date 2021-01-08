package com.yizhou.mymall.service;

import com.yizhou.mymall.entity.Order;
import com.yizhou.mymall.entity.OrderDetail;
import com.yizhou.mymall.entity.User;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yizhou
 * @since 2021-01-02
 */
public interface OrderDetailService {
    boolean SaveUserOrder(User user, Integer id);
    boolean DeleteUserCart(User user);

}
