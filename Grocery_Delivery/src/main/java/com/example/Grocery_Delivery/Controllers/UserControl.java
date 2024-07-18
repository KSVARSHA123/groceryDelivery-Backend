package com.example.Grocery_Delivery.Controllers;

import com.example.Grocery_Delivery.Models.UserModel;
import com.example.Grocery_Delivery.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserControl {

    @Autowired
    UserService userService;

    @GetMapping("/getAllUsers")
    public List<UserModel> getAllUsers(){
        return userService.getAllUsers();
    }
}
