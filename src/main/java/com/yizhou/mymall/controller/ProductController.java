package com.yizhou.mymall.controller;


import com.yizhou.mymall.entity.CartVO;
import com.yizhou.mymall.entity.Product;
import com.yizhou.mymall.entity.User;
import com.yizhou.mymall.service.CartService;
import com.yizhou.mymall.service.ProductCategoryService;
import com.yizhou.mymall.service.ProductService;
import com.yizhou.mymall.service.impl.ProductCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yizhou
 * @since 2021-01-02
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private CartService cartService;
    private User user;
    List<CartVO> cartList;

    @GetMapping("/list/{type}/{id}")
    public ModelAndView list(@PathVariable("type") String type, @PathVariable("id") Integer id, HttpSession httpSession){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productList");
        List<Product> productList = productService.findByCategoryId(type,id);
        modelAndView.addObject("products",productList);
        List<ProductCategoryVO> list = productCategoryService.getAllProductCategoryVO();
        /**
         * Here need to pass cartlist to all of the webpage, otherwise thorws nullexception
         *
         */
        user = (User) httpSession.getAttribute("user");

        if (user !=null) {
            cartList = cartService.findCartVOByUserID(user.getId());
        }else{
            cartList = new ArrayList<>();
        }
        modelAndView.addObject("cartList", cartList);
        modelAndView.addObject("list",list);
        /*Product product = productService.findProductById(id);
        modelAndView.addObject("product",product);*/
        return modelAndView;
    }
    @GetMapping("/findById/{id}")
    public ModelAndView GetById(@PathVariable("id") Integer id,HttpSession httpSession){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productDetail");
        Product product= productService.findProductById(id);
        modelAndView.addObject("product",product);
        user = (User) httpSession.getAttribute("user");

        if (user !=null) {
            cartList = cartService.findCartVOByUserID(user.getId());
        }else{
            cartList = new ArrayList<>();
        }
        modelAndView.addObject("cartList", cartList);
        List<ProductCategoryVO> list = productCategoryService.getAllProductCategoryVO();
        modelAndView.addObject("list",list);
        return modelAndView;
    }


}

