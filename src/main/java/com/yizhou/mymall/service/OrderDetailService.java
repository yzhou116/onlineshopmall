package com.yizhou.mymall.service;

import com.yizhou.mymall.entity.Order;
import com.yizhou.mymall.entity.OrderDetailVO;
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
public interface OrderDetailService {
    boolean SaveUserOrder(User user, Integer id);
    boolean DeleteUserCart(User user);
    List<Order> listUserOrders(Integer id);
   List <OrderDetailVO> getOrderDetailVoByOrderiD(Integer id);

}
