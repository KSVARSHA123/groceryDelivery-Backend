package com.grocerydelivery.backend.Controllers;

import com.grocerydelivery.backend.Models.*;
import com.grocerydelivery.backend.Repositories.*;
import com.grocerydelivery.backend.Services.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

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
    ItemService itemService;
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
    public ResponseEntity<Map<String, Object>> addUser(@RequestBody UserModel userModel) {
        UserModel a=userService.addUser(userModel);
        Map<String, Object> response = new HashMap<>();
        response.put("userid", a.getUSERID()); // Return the userid
        response.put("userrole", a.getUSERROLE());
        response.put("userid", a.getUSERID());
        return ResponseEntity.ok(response);
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

    @PutMapping("/removeFromCart/{USERID}")
    public void removeFromCart(@PathVariable Long USERID,@RequestParam Long PRODUCTID,@RequestParam(required = false) Long QUANTITY){
        if(QUANTITY==null){itemRepository.removeFromCart(USERID,PRODUCTID);}
        else{
            if(itemRepository.checkItem(USERID,PRODUCTID)>=QUANTITY){
                itemRepository.removeFromCart(USERID,PRODUCTID,QUANTITY);}
        }
    }

    @GetMapping("/getSlot")
    public List<TimeSlotModel> getSlot() {
        return timeSlotRepository.findAll();
    }

    @PutMapping("/OrderPlace/{USERID}")
    public ResponseEntity<String> OrderPlace(@PathVariable Long USERID, @RequestParam Long SLOTID, @RequestParam LocalDate DELIVERYDATE,@RequestParam Long ADDRESSID) {
        if (itemRepository.existsUser(USERID) == 1) {
            Float total = itemRepository.total(USERID);
            OrderModel order = new OrderModel();
            order.setUSERID(USERID);
            order.setORDERSTATUSID(1L);
            order.setTIMESLOTID(SLOTID);
            order.setORDERDATE(LocalDate.now());
            order.setDELIVERYDATE(DELIVERYDATE);
            order.setTOTAL(total);
            order.setADDRESSID(ADDRESSID);
            orderRepository.save(order);
            itemRepository.updateOrderid(USERID, order.getORDERID());
            return ResponseEntity.ok(order.getORDERID().toString());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Items in the cart");
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


    @PutMapping("/confirmOrder/{ORDERID}")
    public String confirmOrder(@PathVariable Long ORDERID, @RequestParam String confirm) {
        if (confirm.equals("yes")) {
            orderRepository.confirmT(ORDERID, 1L);
            return "ORDER CONFIRMED";
        } else {
            orderRepository.confirmF(ORDERID, 0L);
            return "ORDER CANCELLED";
        }
    }

    @GetMapping("/getItemsByOrder/{ORDERID}")
    public List<ItemModel> getItemsByOrder(@PathVariable Long ORDERID, @RequestParam Long USERID) {
        return itemService.getItemsByOrderIdCheckCart(ORDERID, USERID);
    }


    @PutMapping("/modifyCart/{ORDERID}")
    private void modifyCart(@PathVariable Long ORDERID, @RequestParam Long USERID, @RequestParam String action, @RequestParam(required = false) Long QUANTITY, @RequestParam(required = false) Long PRODUCTID) {
        if (action.equals("add")) {
            getItemsByOrder(USERID, ORDERID);
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
    @PutMapping("/assignDelivery/{ORDERID}")
    private void assignDelivery(@PathVariable Long ORDERID){
        Long USERID=orderRepository.getUSERID(ORDERID);
        if(deliveryRepository.availability()>0){
            deliveryRepository.assignDelivery(USERID,ORDERID);
        }
    }

//    @GetMapping("/showDelivery/{DELIVERYPERSONID}")
//    private ResponseEntity<Map<String, Object>> showDelivery(@PathVariable Long DELIVERYPERSONID){
//        Long ORDERID=deliveryRepository.getOrderid(DELIVERYPERSONID);
//        Long USERID=deliveryRepository.getUserid(DELIVERYPERSONID);
//        Long VENDORID=itemRepository.getVendorid(ORDERID);
//        Float TOTAL=orderRepository.getAMOUNT(ORDERID);
//        String PAYMENTMETHOD=paymentRepository.getPaymentMode(ORDERID);
//        String VENDORADDRESS=addressRepository.getAddress(VENDORID);
//        String USERADDRESS=addressRepository.getAddress(USERID);
//        Map<String, Object> r = new HashMap<>();
//        r.put("pickup",VENDORADDRESS);
//        r.put("drop",USERADDRESS);
//        r.put("total",TOTAL);
//        r.put("paymentmethod",PAYMENTMETHOD);
//        return ResponseEntity.ok(r);
//    }

    @PutMapping("/updateAvailability/{DELIVERYPERSONID}")
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

    @GetMapping("/showOrders/{USERID}")
    public ResponseEntity<List<Map<String, Object>>> showOrders(@PathVariable Long USERID) {
        List<Object[]> results = orderRepository.showOrders(USERID);
        List<Map<String, Object>> orders = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> order = new HashMap<>();
            order.put("orderconfirmation", result[0]);
            order.put("orderStatus", result[1]);
            order.put("deliveryDate", result[2]);
            order.put("startTime", result[3]);
            order.put("endTime", result[4]);
            order.put("total",result[5]);
            order.put("orderDate",result[6]);
            orders.add(order);
        }

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/getOrdersByVendor/{VENDORID}")
    public List<Map<String, Object>> getOrdersByVendor(@PathVariable Long VENDORID) {
        List<Object[]> results = orderRepository.findOrdersByVendorId(VENDORID);
        List<Map<String, Object>> orders = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> order = new HashMap<>();
            order.put("productname", result[0]);
            order.put("orderStatus", result[1]);
            order.put("deliveryDate", result[2]);
            order.put("startTime", result[3]);
            order.put("endTime", result[4]);
            order.put("total", result[5]);
            orders.add(order);
        }

        return orders;
    }

    @GetMapping("/showAddress/{USERID}")
    public ResponseEntity<List<Map<String, Object>>> showAddress(@PathVariable Long USERID){
        List<Object[]> results=addressRepository.getAddress(USERID);
        if(results.size()>0) {
            List<Map<String, Object>> addresses = new ArrayList<>();
            for (Object[] result : results) {
                Map<String, Object> address = new HashMap<>();
                address.put("Addressid", result[0]);
                address.put("Address", result[1]);
                addresses.add(address);
            }
            return ResponseEntity.ok(addresses);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }
    }
}