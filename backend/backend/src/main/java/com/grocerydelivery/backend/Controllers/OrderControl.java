package com.grocerydelivery.backend.Controllers;

import com.grocerydelivery.backend.Models.OrderModel;
import com.grocerydelivery.backend.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/order")
public class OrderControl {

    @Autowired
    OrderService orderService;

    @GetMapping("/getOrders")
    public List<OrderModel> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/getOrder/{ORDERID}")
    public OrderModel getOrder(@PathVariable Long ORDERID){
        return orderService.getOrder(ORDERID);
    }

}
