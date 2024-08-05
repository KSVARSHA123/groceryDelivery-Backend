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
//
//    @Autowired
//    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserControl userControl;
//
//    @GetMapping("/getOrders")
//    public List<OrderModel> getAllOrders() {
//        return orderService.getAllOrders();
//    }
//
//
//
//    @GetMapping("/getOrder/{ORDERID}")
//    public OrderModel getOrder(@PathVariable Long ORDERID) {
//        return orderService.getOrder(ORDERID);
//    }
//
    @PutMapping("/updateStatus/{ORDERID}")
    public void updateStatus(@PathVariable Long ORDERID) {
        if (orderRepository.getOrderStatusid(ORDERID) == 2L) {
            orderRepository.updateStatusPackaging(ORDERID,4L);
        }
        else if(orderRepository.getOrderStatusid(ORDERID) == 3L){
            orderRepository.updateStatusPackaging(ORDERID,5L);
        }
        else if(orderRepository.getOrderStatusid(ORDERID) == 5L){
            orderRepository.updateStatusOFD(ORDERID,6L);
            userControl.assignDelivery(ORDERID);
        }
    }

}