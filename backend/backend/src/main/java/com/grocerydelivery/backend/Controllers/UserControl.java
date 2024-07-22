package com.grocerydelivery.backend.Controllers;

import com.grocerydelivery.backend.Models.UserModel;
import com.grocerydelivery.backend.Repositories.UserRepository;
import com.grocerydelivery.backend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/USERS")
@CrossOrigin(origins = "http://localhost:3000")
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

    @PostMapping("/login")
    public String login(@RequestBody UserModel userModel){
        UserModel loggedInUser=userService.Login(userModel.getUSEREMAIL(),userModel.getUSERPASSWORD());
        if(loggedInUser!=null){
            return "Login Succeccful";
        }
        else{
            return "Invalid Login";
        }
    }
}
