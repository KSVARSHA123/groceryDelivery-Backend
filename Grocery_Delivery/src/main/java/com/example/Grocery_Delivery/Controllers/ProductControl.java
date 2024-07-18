package com.example.Grocery_Delivery.Controllers;

import com.example.Grocery_Delivery.Models.ProductModel;
import com.example.Grocery_Delivery.Models.VendorModel;
import com.example.Grocery_Delivery.Services.ProductService;
import com.example.Grocery_Delivery.Services.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductControl {

    @Autowired
    ProductService productService;

    @GetMapping("/getAllproduct")
    public List<ProductModel> getAll(){
        return productService.getAll();
    }

    @GetMapping("/addProduct")
    public  List<ProductModel> addProduct(@RequestBody ProductModel product){
        productService.addProduct(product);
        return productService.getAll();
    }



}
