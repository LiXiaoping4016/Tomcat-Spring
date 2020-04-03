package com.controller;

import com.annotation.Autowired;
import com.annotation.Controller;
import com.annotation.RequestMapping;
import com.serviceImpl.OrderService;

/**
 * order
 *
 * @Author lixp23692
 * @Date 2020.04.01
 */
@Controller
@RequestMapping("/order")
public class Order {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/giveOrder")
    public String giveOrder() {
        return orderService.giveOrder();
    }

    @RequestMapping("/queryOrder")
    public String queryOrder() {
        return orderService.queryOrder();
    }

}
