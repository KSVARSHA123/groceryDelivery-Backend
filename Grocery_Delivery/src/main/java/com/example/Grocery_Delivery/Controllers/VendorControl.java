package com.example.Grocery_Delivery.Controllers;

import com.example.Grocery_Delivery.Models.VendorModel;
import com.example.Grocery_Delivery.Services.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VendorControl {

    @Autowired
    private VendorService vservice;

    @GetMapping("/getAllvendor")
    public List<VendorModel> getAll(){
        return vservice.getAll();
    }

    @PostMapping("/addVendor")
    public void addVendor(VendorModel Vendor){
        vservice.addVendor(Vendor);
    }
}
