package com.yizhou.mymall.controller;


import com.yizhou.mymall.entity.User;
import com.yizhou.mymall.entity.UserAddress;
import com.yizhou.mymall.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yizhou
 * @since 2021-01-02
 */
@RestController
@RequestMapping("/userAddress")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @GetMapping("/list")
    public ModelAndView UserAddress(HttpSession httpSession){
        ModelAndView modelAndView = new ModelAndView();
        User user = (User) httpSession.getAttribute("user");

        List<UserAddress> list = userAddressService.FindAllUserAddress(user.getId());

        modelAndView.setViewName("userAddressList");
        modelAndView.addObject("addressList",list);

        return modelAndView;
    }
    @GetMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id, HttpServletResponse response){

        userAddressService.deleteUserAddressById(id);
        try {
            response.sendRedirect("/userAddress/list");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}

