package com.yizhou.mymall.service;

import com.yizhou.mymall.entity.Product;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yizhou
 * @since 2021-01-02
 */
public interface ProductService  {
    List<Product> findByCategoryId(String type,Integer id);

    Product findProductById(Integer id);
}
