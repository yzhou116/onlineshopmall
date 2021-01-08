package com.yizhou.mymall.controller;


import com.yizhou.mymall.entity.User;
import com.yizhou.mymall.entity.UserAddress;
import com.yizhou.mymall.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
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
@RequestMapping("/userAddress")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @GetMapping("/list")
    public ModelAndView UserAddress(HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");
        List<UserAddress> list = userAddressService.FindAllUserAddress(user.getId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userAddressList");
        modelAndView.addObject("addressList",list);

        return modelAndView;
    }

}

