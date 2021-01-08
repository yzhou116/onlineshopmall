package com.yizhou.mymall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yizhou.mymall.entity.User;

import java.sql.SQLException;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yizhou
 * @since 2021-01-02
 */
public interface UserService {
    User CheckUserLogin(String username, String password) throws SQLException;

    boolean RegisterUser(User user);



}
