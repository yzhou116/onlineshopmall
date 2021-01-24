package com.yizhou.mymall.service.impl;

import com.yizhou.mymall.entity.User;
import com.yizhou.mymall.entity.UserAddress;
import com.yizhou.mymall.mapper.UserAddressMapper;
import com.yizhou.mymall.service.UserAddressService;

import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
public class UserAddressServiceImpl implements UserAddressService {
    @Autowired
    private DataSource dataSource;


    @Override
    public List<UserAddress> FindAllUserAddress(Integer id) {
        List<UserAddress> list = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection=dataSource.getConnection();
            String sql = "select id,user_id,address,remark,isdefault from user_address where user_id = " + id;
            statement=connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
               UserAddress userAddress = new UserAddress();
               userAddress.setId(resultSet.getInt(1));
               userAddress.setUserId(resultSet.getInt(2));
               userAddress.setAddress(resultSet.getString(3));
               userAddress.setRemark(resultSet.getString(4));
               userAddress.setIsdefault(resultSet.getInt(5));

               list.add(userAddress);
            }


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

    @Override
    public boolean deleteUserAddressById(Integer id)  {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection= dataSource.getConnection();
            String sql = "delete from user_address where id = + " + id;
          preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                DataBaseResource.releaseAll(connection,preparedStatement,null);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


        return false;
    }


}
