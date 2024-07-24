package com.grocerydelivery.backend.Controllers;

import com.grocerydelivery.backend.Models.*;
import com.grocerydelivery.backend.Repositories.ItemRepository;
import com.grocerydelivery.backend.Repositories.OrderRepository;
import com.grocerydelivery.backend.Repositories.TimeSlotRepository;
import com.grocerydelivery.backend.Repositories.UserRepository;
import com.grocerydelivery.backend.Services.ProductService;
import com.grocerydelivery.backend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/USERS")
@CrossOrigin(origins = "http://localhost:3000")
public class UserControl {

    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    TimeSlotRepository timeSlotRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrderRepository orderRepository;

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
            return loggedInUser.getUSERROLE();
        }
        else{
            return "Invalid Login";
        }
    }
    @GetMapping("/getAllproduct")
    public List<ProductModel> getAll(){
        return productService.getAllProduct();
    }

    @PostMapping("/addToCart/{USERID}")
    public void addToCart(@PathVariable Long USERID,@RequestParam Long PRODUCTID,@RequestParam Long QUANTITY){
        productService.addToCart(USERID,PRODUCTID,QUANTITY);
    }

    @GetMapping("/getSlot")
    public List<TimeSlotModel> getSlot(){
        return timeSlotRepository.findAll();
    }

    @PutMapping("/OrderPlace")
    public String OrderPlace(@PathVariable Long USERID, @RequestParam Long SLOTID, @RequestParam LocalDate DELIVERYDATE){
        if(itemRepository.existsUser(USERID)){
            Float total=itemRepository.total(USERID);
            OrderModel order=new OrderModel();
            order.setUSERID(USERID);
            order.setORDERSTATUSID(1L);
            order.setTIMESLOTID(SLOTID);
            order.setORDERDATE(LocalDate.now());
            order.setDELIVERYDATE(DELIVERYDATE);
            order.setTOTAL(total);
            orderRepository.save(order);
            return "ORDER PLACED";
        }
        else{
            return "No Items in the cart";
        }
    }
}
