package com.yizhou.mymall.service;


import com.yizhou.mymall.entity.Cart;
import com.yizhou.mymall.entity.CartVO;

import java.sql.SQLException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yizhou
 * @since 2021-01-02
 */
public interface CartService  {
    boolean SaveCart(Cart cart);

    List<CartVO> findCartVOByUserID(Integer id);

    boolean DeleteByID(Integer id) throws SQLException;

    boolean UpdateCart(Integer id,Integer quantity, Float cost);



}
