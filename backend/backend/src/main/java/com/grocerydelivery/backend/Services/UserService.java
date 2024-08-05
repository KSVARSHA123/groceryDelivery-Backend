package com.grocerydelivery.backend.Services;

import com.grocerydelivery.backend.Models.UserModel;
import com.grocerydelivery.backend.Repositories.OrderRepository;
import com.grocerydelivery.backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;

    public List<UserModel> getAllUsers(){
        return (List<UserModel>) userRepository.findAll();
    }

    public UserModel getUserById(Long USERID) {
        Optional<UserModel> customerOptional = userRepository.findById(USERID);
        return customerOptional.orElse(null);
    }

    public Long cancellation(Long ORDERID){
        System.out.println(ORDERID);
        orderRepository.cancellation(ORDERID,8L);
        return orderRepository.getOrderStatusid(ORDERID);
    }

    public UserModel addUser(UserModel userModel){
        String hashedPassword = HashUtil.hashPassword(userModel.getUSERPASSWORD());
        userModel.setUSERPASSWORD(hashedPassword);
        return userRepository.save(userModel);
    }
//
//    public void updateUserNP(String NAME, Long PHONE, Long USERID) {
//        userRepository.updateUserNP(NAME, PHONE, USERID);
//    }
//
//    public void updateUserN(String NAME, Long USERID) {
//        userRepository.updateUserN(NAME, USERID);
//    }
//
//    public void updateUserP(Long PHONE, Long USERID) {
//        userRepository.updateUserP(PHONE, USERID);
//    }
//
    public UserModel Login(String EMAIL,String PASSWORD){
        return userRepository.findByUSEREMAILAndUSERPASSWORD(EMAIL,PASSWORD);
    }
}
