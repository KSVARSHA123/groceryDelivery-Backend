package com.example.Grocery_Delivery.Controllers;

import com.example.Grocery_Delivery.Models.ProductModel;
import com.example.Grocery_Delivery.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductControl {

    @Autowired
    ProductService productService;

    @GetMapping("/getAllproduct")
    public List<ProductModel> getAll(){
        return productService.getAllProduct();
    }

}
