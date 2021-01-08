package com.yizhou.mymall.controller;


import com.yizhou.mymall.entity.CartVO;
import com.yizhou.mymall.entity.User;
import com.yizhou.mymall.service.CartService;
import com.yizhou.mymall.service.ProductCategoryService;
import com.yizhou.mymall.service.impl.ProductCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/productCategory")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private CartService cartService;
    private List<CartVO> cartList;

    @GetMapping("/list")
    public ModelAndView list(HttpSession httpSession){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main");
        List<ProductCategoryVO> list = productCategoryService.getAllProductCategoryVO();

        User user = (User) httpSession.getAttribute("user");
        if (user!=null) {
            cartList = cartService.findCartVOByUserID(user.getId());
        }else{
            cartList = new ArrayList<>();
        }
        modelAndView.addObject("cartList", cartList);

        modelAndView.addObject("list",list);
        return modelAndView;
    }



}

