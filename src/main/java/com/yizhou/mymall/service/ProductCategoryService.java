package com.yizhou.mymall.service;


import com.yizhou.mymall.entity.ProductCategory;
import com.yizhou.mymall.service.impl.ProductCategoryVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yizhou
 * @since 2021-01-02
 */
public interface ProductCategoryService  {

    List<ProductCategoryVO> getAllProductCategoryVO();

}
