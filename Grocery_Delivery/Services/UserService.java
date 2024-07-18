package com.example.Grocery_Delivery.Services;

import com.example.Grocery_Delivery.Models.UserModel;
import com.example.Grocery_Delivery.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserModel> getAllUsers(){
        return (List<UserModel>) userRepository.findAll();
    }

    public UserModel addUser(UserModel userModel){
        return userRepository.save(userModel);
    }

    public void updateUserNP(String NAME, Long PHONE, Long USERID) {
        userRepository.updateUserNP(NAME, PHONE, USERID);
    }

    public void updateUserN(String NAME, Long USERID) {
        userRepository.updateUserN(NAME, USERID);
    }

    public void updateUserP(Long PHONE, Long USERID) {
        userRepository.updateUserP(PHONE, USERID);
    }

}
