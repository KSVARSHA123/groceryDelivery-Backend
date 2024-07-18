package com.example.Grocery_Delivery.Services;

import com.example.Grocery_Delivery.Models.UserModel;
import com.example.Grocery_Delivery.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<UserModel> getAllUsers(){
        return (List<UserModel>) userRepository.findAll();
    }
}
