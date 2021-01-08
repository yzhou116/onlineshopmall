package com.yizhou.mymall.service.impl;

import com.yizhou.mymall.entity.*;
import com.yizhou.mymall.mapper.OrderDetailMapper;
import com.yizhou.mymall.service.CartService;
import com.yizhou.mymall.service.OrderDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yizhou
 * @since 2021-01-02
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {


    @Autowired
    private DataSource dataSource;
    @Autowired
    private CartService cartService;


    Connection connection = null;

    PreparedStatement statement = null;

    @Override
    public boolean SaveUserOrder(User user,Integer id) {
        List<CartVO> carts = cartService.findCartVOByUserID(user.getId()) ;

        try{
            connection = dataSource.getConnection();
            String sql = "insert into order_detail (order_id, product_id, quantity,cost)" +
                    " values(?, ?, ?,?)";
            statement = connection.prepareStatement(sql);
            for (CartVO cart : carts) {
                statement.setInt(1, id);
                statement.setInt(2, cart.getProductID());
                statement.setInt(3, cart.getQuantity());
                statement.setFloat(4, cart.getCost());
                statement.executeUpdate();
            }
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                DataBaseResource.releaseAll(connection,statement,null);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean DeleteUserCart(User user) {
        try{
            connection = dataSource.getConnection();
            String sql = "delete from cart where user_id = " + user.getId();
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            return true;


        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                DataBaseResource.releaseAll(connection,statement,null);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }
}
