package com.grocerydelivery.backend.Controllers;

import com.grocerydelivery.backend.Models.*;
import com.grocerydelivery.backend.Repositories.*;
import com.grocerydelivery.backend.Services.AddressService;
import com.grocerydelivery.backend.Services.HashUtil;
import com.grocerydelivery.backend.Services.ProductService;
import com.grocerydelivery.backend.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    AddressService addressService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    OrderControl orderControl;
    @Autowired
    AddressRepository addressRepository;

    @GetMapping("/getAllUsers")
    public List<UserModel> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/addUser")
    public UserModel addUser(@RequestBody UserModel userModel) {
        return userService.addUser(userModel);
    }

    @PutMapping("/{USERID}")
    public void updateUser(@PathVariable Long USERID, @RequestParam(required = false) String NAME, @RequestParam(required = false) Long PHONE) {
        if (PHONE == null) {
            userService.updateUserN(NAME, USERID);
        } else if (NAME == null) {
            userService.updateUserP(PHONE, USERID);
        } else {
            userService.updateUserNP(NAME, PHONE, USERID);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserModel userModel){
        UserModel loggedInUser = userService.Login(userModel.getUSEREMAIL(), HashUtil.hashPassword(userModel.getUSERPASSWORD()));
        if (loggedInUser != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("userid", loggedInUser.getUSERID()); // Return the userid
            response.put("userrole", loggedInUser.getUSERROLE());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Invalid Login"));
        }
    }


    @GetMapping("/getAllproduct")
    public List<ProductModel> getAll() {
        return productService.getAllProduct();
    }

    @PostMapping("/addToCart/{USERID}")
    public void addToCart(@PathVariable Long USERID, @RequestParam Long PRODUCTID, @RequestParam Long QUANTITY) {
        productService.addToCart(USERID, PRODUCTID, QUANTITY);
    }

    @GetMapping("/getSlot")
    public List<TimeSlotModel> getSlot() {
        return timeSlotRepository.findAll();
    }

    @PutMapping("/OrderPlace/{USERID}")
    public String OrderPlace(@PathVariable Long USERID, @RequestParam Long SLOTID, @RequestParam LocalDate DELIVERYDATE) {
        if (itemRepository.existsUser(USERID) == 1) {
            Float total = itemRepository.total(USERID);
            OrderModel order = new OrderModel();
            order.setUSERID(USERID);
            order.setORDERSTATUSID(1L);
            order.setTIMESLOTID(SLOTID);
            order.setORDERDATE(LocalDate.now());
            order.setDELIVERYDATE(DELIVERYDATE);
            order.setTOTAL(total);
            orderRepository.save(order);
            itemRepository.updateOrderid(USERID, order.getORDERID());
            return order.getORDERID().toString();
        } else {
            return "No Items in the cart";
        }
    }

    @GetMapping("/cartItems/{USERID}")
    public List<String> showItems(@PathVariable Long USERID) {
        List<String> a = itemRepository.showItems(USERID);
        if (a.size() == 0) {
            a.add("NO ITEMS IN THE CART");
        }
        return a;
    }

    @PutMapping("/confirmOrder/{USERID}")
    public String confirmOrder(@PathVariable Long USERID, @RequestParam String confirm) {
        if (confirm.equals("yes")) {
            orderRepository.confirmT(USERID, 1L);
            return "ORDER CONFIRMED";
        } else {
            orderRepository.confirmF(USERID, 0L);
            return "ORDER CANCELLED";
        }
    }


    @PutMapping("/modifyCart/{ORDERID}")
    private void modifyCart(@PathVariable Long ORDERID, @RequestParam Long USERID, @RequestParam String action, @RequestParam(required = false) Long QUANTITY, @RequestParam(required = false) Long PRODUCTID) {
        if (action.equals("add")) {
            itemRepository.updateOrderid(USERID, ORDERID);
        } else if (action.equals("delete")) {
            if (QUANTITY != null && PRODUCTID != null && QUANTITY > 0) {
                itemRepository.decreaseItemQuantity(USERID, ORDERID, QUANTITY, PRODUCTID);
            } else if(PRODUCTID != null){
                itemRepository.deleteItem(USERID, ORDERID,PRODUCTID);
            }
        }
    }

    @PutMapping("/addPayment/{ORDERID}")
    private void addPayment(@PathVariable Long ORDERID, @RequestParam String PAYMENTMETHOD) {
        Long USERID = orderRepository.getUSERID(ORDERID);
        Float AMOUNT = orderRepository.getAMOUNT(ORDERID);
        if (PAYMENTMETHOD.equals("cash on delivery")) {
            paymentRepository.insertPayment(ORDERID, USERID, AMOUNT, PAYMENTMETHOD, true);
            orderRepository.updatePayment(ORDERID,true);
        } else if (PAYMENTMETHOD.equals("upi")) {
            paymentRepository.insertPayment(ORDERID, USERID, AMOUNT, PAYMENTMETHOD, false);
        }
    }

    @PutMapping("/makePayment/{ORDERID}")
    private String makePayment(@PathVariable Long ORDERID, @RequestParam String Confirm){
        if(paymentRepository.getPaymentStatus(ORDERID)==true){
            return "Already Made Payment";
        }
        else if(Confirm.equals("yes")){
            paymentRepository.makePayment(ORDERID,true);
            orderRepository.updatePayment(ORDERID,true);
            return "Made Payment";
        }
        else {
            return "Payment Pending";
        }
    }

    @PutMapping("/addAddress")
    private AddressModel addAddress(@RequestBody AddressModel addressModel){
        return addressService.addAddress(addressModel);
    }

    @PutMapping("/addDelivery/{USERID}")
    private String addDeliver(@PathVariable Long USERID){
        if(userRepository.checkRole(USERID)=="delivery" && deliveryRepository.checkUser(USERID)==0){
            deliveryRepository.addDelivery(USERID,true,null,null);
            return USERID.toString();
        }
        else {
            return "Delivery Person Already Exists";
        }
    }
    @PutMapping("/assignDelivery")
    private void assignDelivery(@PathVariable Long ORDERID){
        Long USERID=orderRepository.getUSERID(ORDERID);
        if(deliveryRepository.availability()>0){
            deliveryRepository.assignDelivery(USERID,ORDERID);
        }
    }

    @GetMapping("/showDelivery")
    private ResponseEntity<Map<String, Object>> showDelivery(@PathVariable Long DELIVERYPERSONID){
        Long ORDERID=deliveryRepository.getOrderid(DELIVERYPERSONID);
        Long USERID=deliveryRepository.getUserid(DELIVERYPERSONID);
        Long VENDORID=itemRepository.getVendorid(ORDERID);
        Float TOTAL=orderRepository.getAMOUNT(ORDERID);
        String PAYMENTMETHOD=paymentRepository.getPaymentMode(ORDERID);
        String VENDORADDRESS=addressRepository.getAddress(VENDORID);
        String USERADDRESS=addressRepository.getAddress(USERID);
        Map<String, Object> r = new HashMap<>();
        r.put("pickup",VENDORADDRESS);
        r.put("drop",USERADDRESS);
        r.put("total",TOTAL);
        r.put("paymentmethod",PAYMENTMETHOD);
        return ResponseEntity.ok(r);
    }

    @PutMapping("/updateAvailability")
    private void updateAvailability(@PathVariable Long DELIVERYPERSONID,@PathVariable String delivered){
        if(delivered.equals("yes")){
            Long ORDERID=deliveryRepository.getOrderid(DELIVERYPERSONID);
            deliveryRepository.updateAvailability(DELIVERYPERSONID);
            orderControl.updateStatus(ORDERID);
        }
    }

    @GetMapping("/showOrder/{ORDERID}")
    private List<Object[]> showOrder(@PathVariable Long ORDERID){
        Float TOTAL=orderRepository.getAMOUNT(ORDERID);
        List<Object[]> a=itemRepository.findItemsByOrderId(ORDERID);
        Object[] totalArray = new Object[] {"TOTAL:", TOTAL.toString()};
        a.addLast(totalArray);
        return a;
    }


}