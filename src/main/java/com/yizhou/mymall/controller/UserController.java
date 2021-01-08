package com.yizhou.mymall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yizhou.mymall.entity.CartVO;
import com.yizhou.mymall.entity.User;
import com.yizhou.mymall.service.CartService;
import com.yizhou.mymall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @PostMapping("/register")
    public String RegisterUser(User user, Model model){
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        user.setCreateTime(now);
        if (userService.RegisterUser(user)) {
            return "login";
        }
        model.addAttribute("error", "Your User Name is same with others." +
                "Please enter another User name");
        return "register";
    }

   @PostMapping("/login")
    public String login(String loginName, String password, HttpSession httpSession) throws SQLException {
     /*  QueryWrapper queryWrapper = new QueryWrapper();
       *//**
        * 这边的话如果用框架去做login的服务，这边的字段要写表的column名字
        * 不是类中的变量名
        *//*
       queryWrapper.eq("userName", loginName);
       queryWrapper.eq("password",password);*/

       User user = userService.CheckUserLogin(loginName,password);

       if (user!=null) {
           httpSession.setAttribute("user",user);
           return "redirect:/productCategory/list";
       }else{
           return "login";
       }
   }
   @GetMapping("/logout")
    public String logout(HttpSession httpSession){
       httpSession.invalidate();
       return "login";
   }
   @GetMapping("/userinfo")
   public ModelAndView modelAndView(HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
       List<CartVO> list = cartService.findCartVOByUserID(user.getId());
       if (list==null) {
           list = new ArrayList<>();
       }
       modelAndView.addObject("cartList",
               list);
       modelAndView.setViewName("userInfo");
       return modelAndView;
   }

}

