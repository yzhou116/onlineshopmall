package com.yizhou.mymall.service.impl;


import com.yizhou.mymall.entity.Cart;
import com.yizhou.mymall.entity.CartVO;
import com.yizhou.mymall.enums.ResultEnums;
import com.yizhou.mymall.exception.MyException;
import com.yizhou.mymall.mapper.CartMapper;
import com.yizhou.mymall.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.time.ZoneOffset;
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
public class CartServiceImpl implements CartService {

    @Autowired
    private DataSource dataSource;


    @Override
    public boolean SaveCart(Cart cart) {

        Connection connection = null;
        Connection connection1 = null;
        Connection connection2 = null;

        PreparedStatement statement = null;
        ResultSet resultSet = null;
         Statement statement2 = null;
         PreparedStatement statement3 = null;

        try{
            connection = dataSource.getConnection();
            String sql = "insert into cart (product_id, quantity, cost, user_id, create_time, update_time) " +
                    "values (?,?,?,?,?,?)";
            String sql2 = "select stock from product where id=" + cart.getProductId();
            statement= connection.prepareStatement(sql);

            statement.setInt(1,cart.getProductId());
            statement.setInt(2,cart.getQuantity());
            statement.setFloat(3,cart.getCost());
            statement.setInt(4,cart.getUserId());
            long millis = cart.getCreateTime().toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
            Date date = new Date(millis);
            statement.setString(5, String.valueOf(date));
            statement.setString(6, String.valueOf(date));
            statement.executeUpdate();
            connection1 = dataSource.getConnection();

            statement2 = connection1.createStatement();
            resultSet = statement2.executeQuery(sql2);
            int change = 0;
            int cartQ = 0;

            while (resultSet.next()) {
                cartQ = resultSet.getInt(1);
            }
            change = cartQ - cart.getQuantity();
            if(change < 0){
                throw new MyException(ResultEnums.STOCK_ERROR);
            }
            String sql3 = "update product SET stock = " + change  + "    where id = " + cart.getProductId();

            connection2 = dataSource.getConnection();
            statement3 = connection2.prepareStatement(sql3);
            statement3.executeUpdate();
            return true;
            //   java.sql.Date sqlDate = java.sql.Date.valueOf(String.valueOf(user.getCreateTime()));

        }catch (SQLException e){

        }finally {
            try {
                DataBaseResource.releaseAll(connection,statement,resultSet);
                DataBaseResource.releaseAll(connection1,statement2,null);
                DataBaseResource.releaseAll(connection2,statement3,null);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public List<CartVO> findCartVOByUserID(Integer id) {
        List<CartVO> cartVOList = new ArrayList<>();

        Connection connection = null;

        Statement statement = null;

        ResultSet resultSet = null;

        Connection connection1 = null;

        Statement statement1 = null;

        ResultSet resultSet1 = null;

        String sql1 = "select id, product_id, quantity,cost,user_id from cart where user_id = " + id;


        try{
            connection = dataSource.getConnection();
            connection1 = dataSource.getConnection();

            statement = connection.createStatement();

            statement1 = connection1.createStatement();

            resultSet = statement.executeQuery(sql1);

            while (resultSet.next()) {
                CartVO cartVO = new CartVO();
                cartVO.setCartid(resultSet.getInt(1));
                cartVO.setProductID(resultSet.getInt(2));
                cartVO.setQuantity(resultSet.getInt(3));
                cartVO.setCost(resultSet.getFloat(4));
                String sql2 = "select name, price, file_name,stock from product where id = " + cartVO.getProductID();
                resultSet1 = statement1.executeQuery(sql2);
                while (resultSet1.next()) {
                    cartVO.setName(resultSet1.getString(1));
                    cartVO.setPrice(resultSet1.getFloat(2));
                    cartVO.setFileName(resultSet1.getString(3));
                    cartVO.setStock(resultSet1.getInt(4));
                }
                cartVOList.add(cartVO);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                DataBaseResource.releaseAll(connection,statement,resultSet);
                DataBaseResource.releaseAll(connection1,statement1,resultSet1);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return cartVOList;
    }

    @Override
    public boolean DeleteByID(Integer id) throws SQLException {

        Connection connection = null;

        Statement statement = null;

        try {
            connection = dataSource.getConnection();
            statement= connection.createStatement();
            String sql = "delete from cart where id = " + id;
            statement.executeUpdate(sql);
            return true;

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DataBaseResource.releaseAll(connection,statement,null);
        }

        return false;
    }

    @Override
    public boolean UpdateCart(Integer id, Integer quantity, Float cost) {
        Connection connection = null;

        Statement statement = null;

        try {
            connection = dataSource.getConnection();
            statement= connection.createStatement();
            String sql = "update cart set quantity= " + quantity + ",  cost= " + cost +
                    " where id = " + id;
            statement.executeUpdate(sql);
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
