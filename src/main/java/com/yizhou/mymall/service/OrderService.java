package com.yizhou.mymall.service;

import com.yizhou.mymall.entity.Order;
import com.yizhou.mymall.entity.User;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yizhou
 * @since 2021-01-02
 */
public interface OrderService {

    boolean AddOrder(Order order);

    void UpdateAddress(String address, String remark, User user);

    List<Integer> FindUserLastOrder(User user);






}
