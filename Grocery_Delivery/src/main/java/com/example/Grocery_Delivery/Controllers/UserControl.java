package com.example.Grocery_Delivery.Controllers;

import com.example.Grocery_Delivery.Models.UserModel;
import com.example.Grocery_Delivery.Repositories.UserRepository;
import com.example.Grocery_Delivery.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/USERS")
public class UserControl {

    @Autowired
    UserService userService;

    @GetMapping("/getAllUsers")
    public List<UserModel> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/addUser")
    public UserModel addUser(@RequestBody UserModel userModel){
        return userService.addUser(userModel);
    }

    @PutMapping("/{USERID}")
    public void updateUser(@PathVariable Long USERID,@RequestParam(required = false) String NAME,@RequestParam(required = false) Long PHONE) {
        if(PHONE==null) {userService.updateUserN(NAME,USERID);}
        else if(NAME==null){userService.updateUserP(PHONE,USERID);}
        else{userService.updateUserNP(NAME,PHONE,USERID);}

    }
}
