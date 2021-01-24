package com.yizhou.mymall.service.impl;

import com.yizhou.mymall.entity.*;
import com.yizhou.mymall.service.CartService;
import com.yizhou.mymall.service.OrderDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
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
    Connection connection1 = null;

    PreparedStatement statement = null;

    Statement statement1 = null;
    Statement statement2 = null;

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

    @Override
    public List<Order> listUserOrders(Integer id) {
        List<Order> list = new ArrayList<>();
        ResultSet resultSet = null;
       try{
           connection = dataSource.getConnection();
           statement1 = connection.createStatement();
           String sql = "select id, login_name, user_address, cost,serialnumber from `order` where user_id = "+id;
           resultSet = statement1.executeQuery(sql);
           while (resultSet.next()) {
               Order order = new Order();
               order.setId(resultSet.getInt(1));
               order.setLoginName(resultSet.getString(2));
               order.setUserAddress(resultSet.getString(3));
               order.setCost(resultSet.getFloat(4));
               order.setSerialnumber(resultSet.getString(5));
               list.add(order);
           }
       }catch (Exception e){
           e.printStackTrace();
       }finally {
           try {
               DataBaseResource.releaseAll(connection, statement, resultSet);
           } catch (SQLException throwables) {
               throwables.printStackTrace();
           }
       }
        return list;
    }

    @Override
    public List<OrderDetailVO> getOrderDetailVoByOrderiD(Integer id) {
        List<OrderDetailVO> list = new ArrayList<>();
        ResultSet resultSet = null;
        try{
            connection = dataSource.getConnection();
            statement1 = connection.createStatement();
            String sql = "select product_id, quantity, cost from order_detail where order_id = " + id;
            resultSet = statement1.executeQuery(sql);
            while (resultSet.next()) {
                OrderDetailVO orderDetailVO = new OrderDetailVO();
                orderDetailVO.setProductId(resultSet.getInt(1));
                orderDetailVO.setQuantity(resultSet.getInt(2));
                orderDetailVO.setCost(resultSet.getInt(3));
                addfileNameAndName(orderDetailVO);
                list.add(orderDetailVO);
            }
        }catch (Exception e){

        }finally {
            try {
                DataBaseResource.releaseAll(connection,statement1,resultSet);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return list;
    }

    private void addfileNameAndName(OrderDetailVO orderDetailVO) {
        ResultSet resultSet = null;
        try {
            connection1 = dataSource.getConnection();
            statement2 = connection1.createStatement();
            String sql = "select `name`,file_name,price from product where id = " + orderDetailVO.getProductId();
            resultSet = statement2.executeQuery(sql);
            while (resultSet.next()) {
                orderDetailVO.setName(resultSet.getString(1));
                orderDetailVO.setFileName(resultSet.getString(2));
                orderDetailVO.setPrice(resultSet.getInt(3));
            }
        }catch (Exception e){
            e.printStackTrace();

        }finally {
            try {
                DataBaseResource.releaseAll(connection1,statement2,resultSet);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

}
