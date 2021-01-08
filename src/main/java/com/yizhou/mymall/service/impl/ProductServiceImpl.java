package com.yizhou.mymall.service.impl;

import com.yizhou.mymall.entity.Product;
import com.yizhou.mymall.mapper.ProductMapper;
import com.yizhou.mymall.service.ProductService;

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
public class ProductServiceImpl implements ProductService {

    private List<Product> productList;

    @Autowired
    private DataSource dataSource;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    /**
     * find product category by its type
     * @param type
     * @param id
     * @return
     */

    @Override
    public List<Product> findByCategoryId(String type,Integer id) {
        productList = new ArrayList<>();
        String cate = "";
        switch (type){
            case "one":
                cate = "categorylevelone_id";
                break;
            case "two":
                cate = "categoryleveltwo_id";
                break;
                case "three":
                cate = "categorylevelthree_id";
                break;
        }
        String sql = "select id, name,price, file_name from product where " + cate + "=" + id;
       try{
           connection = dataSource.getConnection();
           statement = connection.prepareStatement(sql);

           resultSet = statement.executeQuery(sql);
           Product product;
           while (resultSet.next()) {
               product = new Product();
               product.setId(resultSet.getInt(1));
               product.setName(resultSet.getString(2));
               product.setPrice(resultSet.getFloat(3));
               product.setFileName(resultSet.getString(4));
               productList.add(product);
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
        return productList;
    }

    /**
     * find product details
     * @param id
     * @return
     */

    @Override
    public Product findProductById(Integer id) {

        String sql = "select * from product where id = " + id;
        Product product = new Product();
        try{
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);

            resultSet=statement.executeQuery();

            while (resultSet.next()) {
                product.setId(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setDescription(resultSet.getString(3));
                product.setPrice(resultSet.getFloat(4));
                product.setStock(resultSet.getInt(5));
                product.setFileName(resultSet.getString(9));

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
        return product;
    }
}
