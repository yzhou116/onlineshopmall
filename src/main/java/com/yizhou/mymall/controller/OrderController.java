package com.yizhou.mymall.controller;


import com.yizhou.mymall.entity.Order;
import com.yizhou.mymall.entity.OrderDetail;
import com.yizhou.mymall.entity.User;
import com.yizhou.mymall.service.CartService;
import com.yizhou.mymall.service.OrderDetailService;
import com.yizhou.mymall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yizhou
 * @since 2021-01-02
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private CartService cartService;


    @PostMapping("/settlement3")
    public ModelAndView settlement3(String userAddress,
                                    Float cost,
                                    HttpSession httpSession,
                                    String address, String remark){
        String seria = null;
        try{
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < 32; i++) {
                stringBuffer.append(Integer.toHexString(new Random().nextInt(16)));
            }
            seria = stringBuffer.toString().toUpperCase();
        }catch (Exception e){
            e.printStackTrace();
        }
        Order order = new Order();
        ModelAndView modelAndView = new ModelAndView();
        order.setCost(cost);
        order.setSerialnumber(seria);
        order.setUserAddress(userAddress);
        User user = (User) httpSession.getAttribute("user");
        order.setLoginName(user.getLoginName());
        order.setUserId(user.getId());
        LocalDateTime now = LocalDateTime.now();
        order.setCreateTime(now);
        order.setUpdateTime(now);
        if (order.getUserAddress().equals("newAddress")) {
            orderService.UpdateAddress(address,remark,user);
            order.setUserAddress(address);
        }
        /**
         * b4 AddOrder get throught, order id is not exist, it automatically created by
         * database. but here need to use order id to insert data to order_detail table
         * have to get the last order that user placed, then send it back to orderDetailImpl
         * The best way is to use Mybaties-plus mapper.insert. It will set order_id the value for
         * order
         * once order_id is created by database. If it is muti-threads this
         * way is wrong.
         *
         */
        if (orderService.AddOrder(order)) {
            List<Integer> list = orderService.FindUserLastOrder(user);
            Collections.sort(list);
            orderDetailService.SaveUserOrder(user,list.get(list.size()-1));
        }
        modelAndView.setViewName("settlement3");
        modelAndView.addObject("cartList",cartService.findCartVOByUserID(user.getId()));
        if (orderDetailService.DeleteUserCart(user)) {
            modelAndView.addObject("cartList",cartService.findCartVOByUserID(user.getId()));
            modelAndView.addObject("orders", order);
            return modelAndView;
        }
        modelAndView.setViewName("settlement2");
        return modelAndView;
    }

}

