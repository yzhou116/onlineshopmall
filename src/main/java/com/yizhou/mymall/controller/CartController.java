package com.yizhou.mymall.controller;


import com.yizhou.mymall.entity.Cart;
import com.yizhou.mymall.entity.User;
import com.yizhou.mymall.service.CartService;
import com.yizhou.mymall.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yizhou
 * @since 2021-01-02
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserAddressService userAddressService;

    /**
     * add item to cart
     * @param productId
     * @param price
     * @param quantity
     * @param session
     * @return
     */

    @GetMapping("/add/{id}/{price}/{quantity}")
    public String add(@PathVariable("id") Integer productId
                        , @PathVariable("price") float price,
                            @PathVariable("quantity") Integer quantity,
                            HttpSession session){
        Cart cart = new Cart();
        cart.setProductId(productId);
        cart.setCost(price*quantity);
        cart.setQuantity(quantity);
        User user = (User) session.getAttribute("user");

        cart.setUserId(user.getId());
        LocalDateTime now = LocalDateTime.now();
        cart.setCreateTime(now);
        cart.setUpdateTime(now);
        try{
            if (cartService.SaveCart(cart)) {
                return "redirect:/cart/findAllCart";

            }
        }catch (Exception e){
          return "redirect:/productCategory/list";
        }
        return null;
    }

    /**
     * get all items in cart
     * @param httpSession
     * @return
     */
    @GetMapping("/findAllCart")
    public ModelAndView findAllCart(HttpSession httpSession){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settlement1");
        User user = (User) httpSession.getAttribute("user");

        modelAndView.addObject("cartList",cartService.findCartVOByUserID(user.getId()));
        return modelAndView;
    }

    /**
     * delete item from cart
     * @param id
     * @return
     */
    @GetMapping("/deleteById/{id}")
    public String deleteById(@PathVariable Integer id) {
        try {
            cartService.DeleteByID(id);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "redirect:/cart/findAllCart";
    }

    /**
     * ready to buy
     * @param httpSession
     * @return
     */
    @GetMapping("/settlement2")
    public ModelAndView settlement2(HttpSession httpSession){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settlement2");
        User user = (User) httpSession.getAttribute("user");
        modelAndView.addObject("addressList", userAddressService.FindAllUserAddress(user.getId()));
        modelAndView.addObject("cartList",cartService.findCartVOByUserID(user.getId()));
        return modelAndView;
    }

    /**
     * Method for Ajax to make sure number's change will effect databse
     * @param id
     * @param quantity
     * @param cost
     * @return
     */
    @PostMapping("/update/{id}/{quantity}/{cost}")
    @ResponseBody
    public String UpdateCart(@PathVariable("id") Integer id,
                             @PathVariable("quantity") Integer quantity,
                             @PathVariable("cost") Float cost){
        if (cartService.UpdateCart(id,quantity,cost)) {
            return "success";
        }
        return "fail";
    }



}

