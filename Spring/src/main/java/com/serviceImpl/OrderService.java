package com.serviceImpl;

import com.annotation.Service;

/**
 * 订单
 *
 * @Author lixp23692
 * @Date 2020.04.01
 */
@Service
public class OrderService {
    public String giveOrder() {
        return "我收到你的订单啦";
    }

    public String queryOrder() {
        return "没有找到你相关的订单";
    }
}
