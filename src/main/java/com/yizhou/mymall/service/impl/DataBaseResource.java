package com.yizhou.mymall.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseResource {

    public static void releaseAll(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        if (connection!=null) {
            connection.close();
        }if(resultSet!=null){
            resultSet.close();
        }if(statement != null){
            statement.close();
        }
    }
}
