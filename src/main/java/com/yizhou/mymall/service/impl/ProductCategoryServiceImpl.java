package com.yizhou.mymall.service.impl;

import com.yizhou.mymall.entity.ProductCategory;
import com.yizhou.mymall.entity.ProductVO;
import com.yizhou.mymall.mapper.ProductCategoryMapper;
import com.yizhou.mymall.mapper.ProductMapper;
import com.yizhou.mymall.service.ProductCategoryService;

import com.yizhou.mymall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
public class ProductCategoryServiceImpl implements ProductCategoryService {


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;
    private List<ProductCategory> productCategories;

    private List<ProductCategoryVO> list;


    @Override
    public List<ProductCategoryVO> getAllProductCategoryVO() {
        /**   this one is pro's recursion method,similar situation. haven't check it yet:
         *     private List<ProductlevelOne> getChildrens(ProductlevelOne parent, List<ProductlevelOne> all) {
         *
         *         List<ProductlevelOne> children =  all.stream().filter((category) -> {
         *             return category.getParentId().equals(parent.getId());
         *         }).map((e) -> {
         *
         *             e.setChildren(getChildrens(e, all));
         *             return e;
         *         }).collect(Collectors.toList());
         *
         *         return children;
         *     }
         */

        try {
            productCategories = getAllProductCategory();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        list = new ArrayList<>();

        ProductCategoryVO productCategoryVO1 = null;
        ProductCategoryVO productCategoryVO2 = null;
        ProductCategoryVO productCategoryVO3 = null;
     //   Iterable<ProductCategory> iterable = (Iterable<ProductCategory>) productCategories.iterator();

        int i = 0;
        int j = 0;
        int k = 0;
        //TODO:这里写的比较垃圾，迟点要还是要换成上面的递归或者三个for循环分开添加
/*
        while(i < productCategories.size()){
            if(i == -1){
                i=0;
            }else if(i > productCategories.size()-1){
                i = productCategories.size()-1;
            }
            if(productCategories.get(i).getType()==1){
                productCategoryVO1 = new ProductCategoryVO();
                productCategoryVO1.setId(productCategories.get(i).getId());
                productCategoryVO1.setName(productCategories.get(i).getName());
                list.add(productCategoryVO1);

                iteratorRemove(productCategories,productCategories.get(i));
                continue;
            }
            i++;
        }
        i = 0;
        for(; i < list.size(); i++){
            while(j < productCategories.size()){
                if(j == -1){
                    j=0;
                }else if(j > productCategories.size()-1){
                    j = productCategories.size()-1;
                }
                if(list.get(i).getId().equals(productCategories.get(j).getParentId()) ){
                    productCategoryVO1 = new ProductCategoryVO();
                    productCategoryVO1.setId(productCategories.get(j).getId());
                    productCategoryVO1.setName(productCategories.get(j).getName());
                    list.get(i).getChildren().add(productCategoryVO1);
                    iteratorRemove(productCategories,productCategories.get(j));
                }
                j++;
            }
            j=0;
        }
        productCategories.get(j);





        i = 0;*/




       while(i < productCategories.size()){
            if(productCategories.get(i).getType()==1){
                productCategoryVO1  = new ProductCategoryVO();
                productCategoryVO1.setId(productCategories.get(i).getId());
                productCategoryVO1.setName(productCategories.get(i).getName());
                iteratorRemove(productCategories,productCategories.get(i));
              //  productCategories.remove(productCategories.get(i));
                while(j < productCategories.size()){
                    if (productCategories.get(j).getParentId().equals(productCategoryVO1.getId())
                            ) {
                        productCategoryVO2 = new ProductCategoryVO();
                        productCategoryVO2.setId(productCategories.get(j).getId());
                        productCategoryVO2.setName(productCategories.get(j).getName());
                        iteratorRemove(productCategories,productCategories.get(j));
                      //  productCategories.remove(productCategories.get(j));
                        while(k<productCategories.size()) {
                            if (productCategories.get(k).getParentId().equals(productCategoryVO2.getId())
                                    ) {
                                productCategoryVO3 = new ProductCategoryVO();
                                productCategoryVO3.setId(productCategories.get(k).getId());
                                productCategoryVO3.setName(productCategories.get(k).getName());
                                iteratorRemove(productCategories,productCategories.get(k));
                                productCategoryVO2.getChildren().add(productCategoryVO3);
                        //        productCategories.remove(productCategories.get(k));
                                k=0;
                            }else{
                                k++;
                            }
                        }
                        k=0;
                        productCategoryVO1.getChildren().add(productCategoryVO2);
                    }else{
                        j++;
                    }
                }
                i=0;
                j=0;
                k=0;
                list.add(productCategoryVO1);
            }else{
                i++;
            }

        }

        /**
         * add picture to productCategoryVO list
         */
        for (int l = 0; l < list.size(); l++) {
            list.get(l).setBannerImg("/images/banner" + l + ".png");
            list.get(l).setTopImg("/images/top" + l + ".png");
        }
        /**
         * add productvo
         */
        try {
            addProductVO(list);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return list;
        }

    /**
     * sub products
     * @param list
     * @throws SQLException
     */
        public void addProductVO(List<ProductCategoryVO> list) throws SQLException {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;
            try{
                connection = dataSource.getConnection();
                statement = connection.createStatement();
                List<ProductVO> productVOList;

                for (int i = 0; i < list.size(); i++) {
                    Integer id = list.get(i).getId();
                    ProductVO productVO = new ProductVO();
                    productVOList = new ArrayList<>();
                    String sql = "select id, name, price,file_name from product where " +
                            "categorylevelone_id = " + id;
                    resultSet = statement.executeQuery(sql);
                    while (resultSet.next()){
                        productVO.setId(resultSet.getInt(1));
                        productVO.setName(resultSet.getString(2));
                        productVO.setPrice(resultSet.getFloat(3));
                        productVO.setFileName(resultSet.getString(4));
                        productVOList.add(productVO);
                        productVO = new ProductVO();

                    }
                    list.get(i).setProductVOList(productVOList);
                }
            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                DataBaseResource.releaseAll(connection,statement,resultSet);
            }
        }
        public  List<ProductCategory> getAllProductCategory() throws SQLException {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;
            List<ProductCategory> list = new ArrayList<>();
            String sql = "select * from product_category";
            try{
           connection = dataSource.getConnection();
                 statement = connection.createStatement();
             resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    list.add(new ProductCategory(resultSet.getInt(1),
                            resultSet.getString(2), resultSet.getInt(3),
                          resultSet.getInt(4) ));
                }


        }catch (SQLException e){
            e.printStackTrace();
        }finally {
          DataBaseResource.releaseAll(connection,statement,resultSet);
        }
            return list;

        }


    public void iteratorRemove(List<ProductCategory> list, ProductCategory obj){

        list.removeIf(item -> item.getId().equals(obj.getId()));
    }






    }

