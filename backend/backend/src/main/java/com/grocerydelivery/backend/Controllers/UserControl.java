package com.grocerydelivery.backend.Controllers;

import com.grocerydelivery.backend.Models.*;
import com.grocerydelivery.backend.Repositories.*;
import com.grocerydelivery.backend.Services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/USERS")
@CrossOrigin(origins = "http://localhost:3000")
public class UserControl {
    @Autowired
    UserService userService;
    @Autowired
    AddressService addressService;
    @Autowired
    UserRepository userRepository;

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
    //    @Autowired
//    AddressService addressService;
    @Autowired
    ItemService itemService;
    //    @Autowired
//    UserRepository userRepository;
    @Autowired
    DeliveryRepository deliveryRepository;
    //    @Autowired
//    OrderControl orderControl;
    @Autowired
    AddressRepository addressRepository;
//

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody UserAddressWrapper userAddressWrapper) {
        UserModel userModel = userAddressWrapper.getUserModel();
        AddressModel addressModel = userAddressWrapper.getAddressModel();
        UserModel a=userService.addUser(userModel);
        Long USERID=a.getUSERID();
        addressModel.setUSERID(USERID);
        addressService.addAddress(addressModel);
//        Map<String, Object> response = new HashMap<>();
//        response.put("userid", a.getUSERID()); // Return the userid
//        response.put("userrole", a.getUSERROLE());
//        response.put("userid", a.getUSERID());
        return ResponseEntity.ok(a.getUSERROLE());
    }

//    @GetMapping("/getRole")
//    public String getRole(HttpSession httpSession){
//        Long USERID=(Long) httpSession.getAttribute("USERID");
//        return userRepository.getRole(USERID);
//    }
@GetMapping("/getRole")
public ResponseEntity<String> getRole(HttpSession session) {
    Long USERID = (Long) session.getAttribute("USERID");
    System.out.println(USERID);
    if (USERID == null) {
        return ResponseEntity.badRequest().body("User ID not found in session");
    }
    String role = userRepository.getRole(USERID);
    if (role == null) {
        return ResponseEntity.badRequest().body("Role not found for user Email: " + USERID);
    }
    return ResponseEntity.ok(role);
}

    @GetMapping("/get")
    public UserModel getUserById(HttpSession httpSession) {
        return userService.getUserById((long)httpSession.getAttribute("USERID"));
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserModel userModel, HttpSession session){
        UserModel loggedInUser = userRepository.findByUSEREMAIL(userModel.getUSEREMAIL());
        if (loggedInUser != null && userService.Login(userModel.getUSEREMAIL(), HashUtil.hashPassword(userModel.getUSERPASSWORD()))!=null) {
            session.setAttribute("USERID",loggedInUser.getUSERID());
//            Long ORDERID=orderRepository.getOrderid((Long) session.getAttribute("USERID"));
//            if(ORDERID!=null){
//                session.setAttribute("ORDERID",ORDERID);
//            }
//            System.out.println(session.getAttribute("ORDERID"));
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }

//@RestController
//@RequestMapping("/USERS")
//@CrossOrigin(origins = "http://localhost:3000")
//public class UserControl {
//

    @GetMapping("/getAllUsers")
    public List<UserModel> getAllUsers() {
        return userService.getAllUsers();

    }
//
//    @PostMapping("/addUser")
//    public ResponseEntity<Map<String, Object>> addUser(@RequestBody UserAddressWrapper userAddressWrapper) {
//        UserModel userModel = userAddressWrapper.getUserModel();
//        AddressModel addressModel = userAddressWrapper.getAddressModel();
//        UserModel a=userService.addUser(userModel);
//        Long USERID=a.getUSERID();
//        addressModel.setUSERID(USERID);
//        addressService.addAddress(addressModel);
//        Map<String, Object> response = new HashMap<>();
//        response.put("userid", a.getUSERID()); // Return the userid
//        response.put("userrole", a.getUSERROLE());
//        response.put("userid", a.getUSERID());
//        return ResponseEntity.ok(response);
//    }
//
//    @PutMapping("/{USERID}")
//    public void updateUser(@PathVariable Long USERID, @RequestParam(required = false) String NAME, @RequestParam(required = false) Long PHONE) {
//        if (PHONE == null) {
//            userService.updateUserN(NAME, USERID);
//        } else if (NAME == null) {
//            userService.updateUserP(PHONE, USERID);
//        } else {
//            userService.updateUserNP(NAME, PHONE, USERID);
//        }
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<Map<String, Object>> login(@RequestBody UserModel userModel){
//        UserModel loggedInUser = userService.Login(userModel.getUSEREMAIL(), HashUtil.hashPassword(userModel.getUSERPASSWORD()));
//        if (loggedInUser != null) {
//            Map<String, Object> response = new HashMap<>();
//            response.put("userid", loggedInUser.getUSERID()); // Return the userid
//            response.put("userrole", loggedInUser.getUSERROLE());
//            return ResponseEntity.ok(response);
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Invalid Login"));
//        }
//    }
//
//
//
@GetMapping("/userDetails")
public ResponseEntity<List<Map<String, String>>> userDetails() {
    List<Object[]> users = userRepository.findUserDetails();
    List<Map<String, String>> userDetails = users.stream().map(user -> {
        Map<String, String> userMap = new HashMap<>();
        userMap.put("username", (String) user[0]);
        userMap.put("useremail", (String) user[1]);
        userMap.put("userphonenumber", (String) user[2]);
        return userMap;
    }).collect(Collectors.toList());
    return ResponseEntity.ok(userDetails);
}


    @PostMapping("/addToCart")
    public void addToCart( @RequestParam Long PRODUCTID, @RequestParam Long QUANTITY,HttpSession session) {
        productService.addToCart(PRODUCTID, QUANTITY,(Long) session.getAttribute("USERID"));
    }

    @PutMapping("/removeFromCart")
    public void removeFromCart(@RequestParam String PRODUCTID,HttpSession session){
        Long USERID=(Long) session.getAttribute("USERID");
        itemRepository.removeFromCart(USERID,Long.parseLong(PRODUCTID));
    }

    @GetMapping("/getSlot")
    public List<TimeSlotModel> getSlot() {
        return timeSlotRepository.findAll();
    }

    @PutMapping("/CancelOrder/{ORDERID}")
    public ResponseEntity<String> CancelOrder(@PathVariable Long ORDERID){
        orderRepository.cancel(ORDERID);
        return ResponseEntity.ok("CANCELLED");
    }

    @PutMapping("/OrderPlace")
    public ResponseEntity<String> OrderPlace(@RequestParam Long SLOTID, @RequestParam LocalDate DELIVERYDATE,@RequestParam Long ADDRESSID,HttpSession session) {
        Long USERID=(Long) session.getAttribute("USERID");
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
//            session.setAttribute("ORDERID",order.getORDERID());
            itemRepository.updateOrderid(USERID, order.getORDERID());
            return ResponseEntity.ok(order.getORDERID().toString());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Items in the cart");
        }
    }

    @GetMapping("/cartItems")
    public ResponseEntity<Object> showItems(HttpSession session) {
        List<String> a = itemRepository.showItems((Long) session.getAttribute("USERID"));
        if (a.size() == 0) {
            return ResponseEntity.badRequest().body("No Items");
        }
        return ResponseEntity.ok().body(a);
    }
//
//
    @PutMapping("/confirmOrder/{ORDERID}")
    public String confirmOrder(@PathVariable Long ORDERID,@RequestParam String confirm,HttpSession session) {
        if (confirm.equals("yes")) {
            orderRepository.confirmT(ORDERID);
            return "ORDER CONFIRMED";
        } else {
            orderRepository.confirmF(ORDERID);
            return "ORDER CANCELLED";
        }
    }
//
//    @GetMapping("/getItemsByOrder/{ORDERID}")
//    public List<ItemModel> getItemsByOrder(@PathVariable Long ORDERID, @RequestParam Long USERID) {
//        return itemService.getItemsByOrderIdCheckCart(ORDERID, USERID);
//    }


    @PutMapping("/modifyCart/{ORDERID}")
    private void modifyCart(@PathVariable Long ORDERID,HttpSession session) {
        Long USERID=(Long) session.getAttribute("USERID");
        itemRepository.ADD(ORDERID,USERID);
//        if (action.equals("add")) {
//            if(QUANTITY!=null){
//                itemService.MaddProduct(USERID,ORDERID,QUANTITY, PRODUCTID);
//            }
//            else{
//                itemService.MaddProduct(USERID,ORDERID,1L, PRODUCTID);
//            }
////            itemService.getItemsByOrderIdCheckCart(USERID, ORDERID);
//        } else if (action.equals("delete")) {
//            if (QUANTITY != null && PRODUCTID != null && QUANTITY > 0) {
//                itemRepository.decreaseItemQuantity(USERID, ORDERID, QUANTITY, PRODUCTID);
//            } else if(PRODUCTID != null){
//                itemRepository.deleteItem(USERID, ORDERID,PRODUCTID);
//            }
//        }
    }

    @PutMapping("/addPayment/{ORDERID}")
    private void addPayment(@PathVariable Long ORDERID,@RequestParam String PAYMENTMETHOD,HttpSession session) {
        Long USERID = (Long) session.getAttribute("USERID");
        Float AMOUNT = orderRepository.getAMOUNT(ORDERID);
        if (PAYMENTMETHOD.equals("cash on delivery")) {
            if(orderRepository.getOrderStatusid(ORDERID)== 4L){
                orderRepository.updatePayment(ORDERID,true, 5L);
            }
            else if(orderRepository.getOrderStatusid(ORDERID)== 2L){
                orderRepository.updatePayment(ORDERID,true, 3L);
            }
            paymentRepository.insertPayment(ORDERID, USERID, AMOUNT, PAYMENTMETHOD, true);

        } else if (PAYMENTMETHOD.equals("upi")) {
            paymentRepository.insertPayment(ORDERID, USERID, AMOUNT, PAYMENTMETHOD, false);
        }
    }

    @PutMapping("/makePayment/{ORDERID}")
    private String makePayment(@PathVariable Long ORDERID,@RequestParam String Confirm){
//        Long ORDERID=(Long) session.getAttribute("ORDERID");
        System.out.println(paymentRepository.getPaymentStatus(ORDERID));
        if(paymentRepository.getPaymentStatus(ORDERID)=="true"){
            return "Already Made Payment";
        }
        else if(Confirm.equals("yes")){
            if(orderRepository.getOrderStatusid(ORDERID)== 4L){
                orderRepository.updatePayment(ORDERID,true, 5L);
            }
            else if(orderRepository.getOrderStatusid(ORDERID)== 2L){
                orderRepository.updatePayment(ORDERID,true, 3L);
            }
            paymentRepository.makePayment(ORDERID,1L);
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

    @PostMapping("/addDelivery")
    private String addDeliver(HttpSession session){
        Long USERID=(Long) session.getAttribute("USERID");
        System.out.println(deliveryRepository.checkUser(USERID));
        if(deliveryRepository.checkUser(USERID).equals("0")){
            deliveryRepository.addDelivery(USERID,1L,null,null);
            return USERID.toString();
        }
        else {
            return "Delivery Person Already Exists";
        }
    }

    @PutMapping("/assignDelivery/{ORDERID}")
    public void assignDelivery(@PathVariable Long ORDERID){
        Long USERID=orderRepository.getUSERID(ORDERID);
        if(deliveryRepository.availability().equals("1")){
            Long DELIVERYPERSONID=deliveryRepository.getDelivery();
            deliveryRepository.assignDelivery(USERID,ORDERID,DELIVERYPERSONID);
        }
    }

    @GetMapping("/showDelivery")
    private ResponseEntity<Map<String, Object>> showDelivery(HttpSession session){
        Long DELIVERYPERSONID=(Long) session.getAttribute("USERID");
        if(deliveryRepository.getOrderid(DELIVERYPERSONID)!=null) {
            Long ORDERID = deliveryRepository.getOrderid(DELIVERYPERSONID);
            Long VENDORID = itemRepository.getVendorid(ORDERID);
            Float TOTAL = orderRepository.getAMOUNT(ORDERID);
            String PAYMENTMETHOD = paymentRepository.getPaymentMode(ORDERID);
            String VENDORADDRESS = addressRepository.getAddress(VENDORID);
            String USERADDRESS = orderRepository.getAddress(ORDERID);
            Map<String, Object> r = new HashMap<>();
            r.put("pickup", VENDORADDRESS);
            r.put("drop", USERADDRESS);
            r.put("total", TOTAL);
            r.put("paymentmethod", PAYMENTMETHOD);
            return ResponseEntity.ok(r);
        }
        else{
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "No order found for the delivery person.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/updateAvailability")
    private void updateAvailability(@RequestParam String delivered,HttpSession session){
        Long DELIVERYPERSONID=(Long) session.getAttribute("USERID");
        if(delivered.equals("yes")){
            Long ORDERID=deliveryRepository.getOrderid(DELIVERYPERSONID);
            deliveryRepository.updateAvailability(DELIVERYPERSONID);
            orderRepository.updateStatusD(ORDERID);
        }
    }

//    @GetMapping("/showOrder/{ORDERID}")
//    private List<Object[]> showOrder(@PathVariable Long ORDERID){
//        Float TOTAL=orderRepository.getAMOUNT(ORDERID);
//        List<Object[]> a=itemRepository.findItemsByOrderId(ORDERID);
//        Object[] totalArray = new Object[] {"TOTAL:", TOTAL.toString()};
//        a.addLast(totalArray);
//        return a;
//    }
//
@GetMapping("/showOrdersByUser")
public ResponseEntity<List<Map<String, Object>>> showOrders(HttpSession session) {
    Long USERID = (Long) session.getAttribute("USERID");
    List<Object[]> results = orderRepository.showOrders(USERID);
    List<Map<String, Object>> orders = new ArrayList<>();

    for (Object[] result : results) {
        if(userService.cancellation(((Number) result[7]).longValue())==8L){
            result[1] = "CANCELLED";
        }
//        try {
//            orderRepository.cancellation(((Number) result[7]).longValue());
//            if (orderRepository.getOrderStatusid(((Number) result[7]).longValue()) == 8L) {
//                result[1] = "CANCELLED";
//            }
//        } catch (Exception e) {
//            e.printStackTrace(); // Log the exception
//        }
        Map<String, Object> order = new HashMap<>();
        order.put("orderconfirmation", result[0]);
        order.put("orderStatus", result[1]);
        order.put("deliveryDate", result[2]);
        order.put("startTime", result[3]);
        order.put("endTime", result[4]);
        order.put("total", result[5]);
        order.put("orderDate", result[6]);
        order.put("orderid", result[7]);
        orders.add(order);
    }

    return ResponseEntity.ok(orders);
}


    @GetMapping("/getOrdersByVendor")
    public ResponseEntity<List<Map<String, Object>>> getOrdersByVendor(HttpSession session) {
        Long VENDORID = (Long) session.getAttribute("USERID");
        List<Object[]> productResults = orderRepository.findProductByVendorId(VENDORID);
        List<Object[]> orderResults = orderRepository.findOrdersByVendorId(VENDORID);

        // Create a dictionary to map orderid to list of products
        Map<Long, List<Map<String, Object>>> orderProductsMap = new HashMap<>();
        for (Object[] productResult : productResults) {
            Long orderId = ((Number) productResult[0]).longValue();
            String productName = (String) productResult[1];
            Long quantity = ((Number) productResult[2]).longValue();
            String desc = (String) productResult[3];
            Float price=(Float) productResult[4];

            Map<String, Object> productDetails = new HashMap<>();
            productDetails.put("productName", productName);
            productDetails.put("quantity", quantity);
            productDetails.put("description", desc);
            productDetails.put("price",price);

            orderProductsMap.computeIfAbsent(orderId, k -> new ArrayList<>()).add(productDetails);
        }

        List<Map<String, Object>> orders = new ArrayList<>();
        for (Object[] orderResult : orderResults) {
            if (userService.cancellation(((Number) orderResult[5]).longValue())==8L){
                orderResult[0]="CANCELLED";
            }
            Map<String, Object> order = new HashMap<>();
            order.put("orderStatus", orderResult[0]);
            order.put("deliveryDate", orderResult[1]);
            order.put("startTime", orderResult[2]);
            order.put("endTime", orderResult[3]);
            order.put("total", orderResult[4]);
            Long orderId = ((Number) orderResult[5]).longValue();
            order.put("orderid", orderId);

            // Add the list of products to the order
            order.put("products", orderProductsMap.get(orderId));

            orders.add(order);
        }

        return ResponseEntity.ok().body(orders);
    }



    @GetMapping("/showAddress")
    public ResponseEntity<List<Map<String, Object>>> showAddress(HttpSession session){
        Long USERID=(Long)session.getAttribute("USERID");
        List<Object[]> results=addressRepository.getAddressa(USERID);
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