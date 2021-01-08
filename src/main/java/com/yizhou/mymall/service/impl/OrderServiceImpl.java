package com.yizhou.mymall.service.impl;

import com.yizhou.mymall.entity.Order;
import com.yizhou.mymall.entity.User;
import com.yizhou.mymall.entity.UserAddress;
import com.yizhou.mymall.mapper.OrderMapper;
import com.yizhou.mymall.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
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
public class OrderServiceImpl  implements OrderService {
    @Autowired
    private DataSource dataSource;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    @Override
    public boolean AddOrder(Order order) {
        try{
            connection = dataSource.getConnection();
            String sql = "insert into `order` (user_id, login_name, user_address, \n" +
                    "                     cost,  create_time, update_time,serialnumber) values (?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,order.getUserId());
            preparedStatement.setString(2,order.getLoginName());
            preparedStatement.setString(3,order.getUserAddress());
            preparedStatement.setFloat(4,order.getCost());
            long millis = order.getCreateTime().toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
            Date date = new Date(millis);
            preparedStatement.setString(5,String.valueOf(date));
            preparedStatement.setString(6,String.valueOf(date));
            preparedStatement.setString(7,order.getSerialnumber());
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                DataBaseResource.releaseAll(connection,preparedStatement,null);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void UpdateAddress(String address, String remark, User user) {
        UserAddress userAddress = new UserAddress();
        userAddress.setAddress(address);
        userAddress.setRemark(remark);
        userAddress.setIsdefault(1);
        LocalDateTime localDateTime = LocalDateTime.now();
        userAddress.setCreateTime(localDateTime);
        userAddress.setUpdateTime(localDateTime);
        userAddress.setUserId(user.getId());
        PreparedStatement statement =null;
        PreparedStatement statement1 = null;

        String sql = "update user_address SET isdefault = 0 where user_id = " + user.getId();
        String sql1 = "insert into user_address (user_id, address, remark, isdefault, " +
                "create_time, update_time) values (?,?,?,?,?,?)";

        try{
            connection= dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            statement1 = connection.prepareStatement(sql1);
            statement1.setInt(1,user.getId());
            statement1.setString(2,userAddress.getAddress());
            statement1.setString(3,userAddress.getRemark());
            statement1.setInt(4,1);
            long millis = userAddress.getCreateTime().toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
            Date date = new Date(millis);
            statement1.setString(5,String.valueOf(date));
            statement1.setString(6,String.valueOf(date));
            statement1.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                DataBaseResource.releaseAll(connection,statement,null);
                DataBaseResource.releaseAll(null,statement1,null);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Integer> FindUserLastOrder(User user) {
        List<Integer> list = new ArrayList<>();
        Statement statement =null;
        ResultSet resultSet = null;
       try{
           connection = dataSource.getConnection();
           statement = connection.createStatement();
           String sql = "select id from `order`  where user_id = " + user.getId();
           resultSet = statement.executeQuery(sql);
           while (resultSet.next()) {
               list.add(resultSet.getInt(1));
           }
           return list;
       }catch (SQLException e){
           e.printStackTrace();
       }finally {

           try {
               DataBaseResource.releaseAll(connection,statement,resultSet);
           } catch (SQLException throwables) {
               throwables.printStackTrace();
           }
       }
        return list;
    }

}
