package com.yizhou.mymall.controller;


import com.yizhou.mymall.entity.Order;
import com.yizhou.mymall.entity.OrderDetailVO;
import com.yizhou.mymall.entity.User;
import com.yizhou.mymall.service.OrderDetailService;
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
@RequestMapping("/orderDetail")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/list")
    public ModelAndView listUserOrder( HttpSession httpSession){
        ModelAndView modelAndView = new ModelAndView();
        User user = (User) httpSession.getAttribute("user");
        modelAndView.setViewName("orderList");

        List<OrderDetailVO> orderDetailVOList = new ArrayList<>();
        List<Order> list = orderDetailService.listUserOrders(user.getId());
        for (int i = 0; i < list.size(); i++) {
            orderDetailVOList = orderDetailService.getOrderDetailVoByOrderiD(list.get(i).getId());
            list.get(i).setList(orderDetailVOList);
         /*   List<OrderDetailVO> orderDetailVOList1 = new ArrayList<>();
            orderDetailVOList1 = orderDetailService.getOrderDetailVoByOrderiD(list.get(i).getId());
            for (int j = 0; j < orderDetailVOList1.size(); j++) {
                orderDetailVOList2.add(orderDetailVOList1.get(j));
            }*/
        }
        int i = 0;
       /* modelAndView.addObject("orderDetailVOList",orderDetailVOList2);*/
        modelAndView.addObject("list",list);

        return modelAndView;
    }


}

