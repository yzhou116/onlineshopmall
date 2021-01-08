package com.yizhou.mymall.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yizhou.mymall.entity.ProductCategory;
import com.yizhou.mymall.entity.User;
import com.yizhou.mymall.mapper.UserMapper;
import com.yizhou.mymall.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yizhou
 * @since 2021-01-02
 */
@Service
public class UserServiceImpl  implements UserService {
    @Autowired
    private DataSource dataSource;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    @Override
    public User CheckUserLogin(String loginname, String password) throws SQLException {
        /**
         * In normal way people use md5 method to check login.
         */

        String sql = "select * from user where login_name = '" + loginname + "'" + " " +
                "and password = '" + password + "'";
        User user = new User();
        try{
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                user.setId(resultSet.getInt(1));
                user.setLoginName(resultSet.getString(2));
                user.setUserName(resultSet.getString(3));
                user.setPassword(null);
                user.setGender(resultSet.getInt(5));
                user.setIdentityCode(resultSet.getString(6));
                user.setEmail(resultSet.getString(7));
                user.setMobile(resultSet.getString(8));
                user.setFileName(resultSet.getString(9));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DataBaseResource.releaseAll(connection,statement,resultSet);
        }

        return user ;
    }

    @Override
    public boolean RegisterUser(User user) {
        try {
            String sql = "insert into user (login_name, password, gender, identity_code, email, " +
                    "mobile,create_time,update_time,user_name) values(?,?,?,?,?,?,?,?,?)";
            connection = dataSource.getConnection();
            statement= connection.prepareStatement(sql);
            statement.setString(1,user.getLoginName());
            statement.setString(2,user.getPassword());
            statement.setInt(3,user.getGender());
            statement.setString(4,user.getIdentityCode());
            statement.setString(5,user.getEmail());
            statement.setString(6,user.getMobile());
            long millis = user.getCreateTime().toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
            Date date = new Date(millis);
         //   java.sql.Date sqlDate = java.sql.Date.valueOf(String.valueOf(user.getCreateTime()));

            statement.setString(7, String.valueOf(date));
            statement.setString(8, String.valueOf(date));
            statement.setString(9,user.getUserName());
            statement.executeUpdate();
         //   statement = connection.prepareStatement(sql);
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
