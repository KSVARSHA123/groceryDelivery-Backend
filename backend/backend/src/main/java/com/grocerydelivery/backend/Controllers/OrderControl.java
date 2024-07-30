package com.grocerydelivery.backend.Controllers;

import com.grocerydelivery.backend.Models.OrderModel;
import com.grocerydelivery.backend.Repositories.OrderRepository;
import com.grocerydelivery.backend.Repositories.PaymentRepository;
import com.grocerydelivery.backend.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/order")
public class OrderControl {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/getOrders")
    public List<OrderModel> getAllOrders() {
        return orderService.getAllOrders();
    }



    @GetMapping("/getOrder/{ORDERID}")
    public OrderModel getOrder(@PathVariable Long ORDERID) {
        return orderService.getOrder(ORDERID);
    }

    @PutMapping("/updateStatus/{USERID}")
    public void updateStatus(@PathVariable Long ORDERID) {
        if (orderRepository.viewStatus(ORDERID) == 1) {
            orderService.updateStatus1(ORDERID);
        }
        else if(orderRepository.viewStatus(ORDERID) == 2){
            orderRepository.updateStatus2(ORDERID);
        }
        else if(orderRepository.viewStatus(ORDERID) == 3){
            orderRepository.updateStatus3(ORDERID);
        }
    }

}