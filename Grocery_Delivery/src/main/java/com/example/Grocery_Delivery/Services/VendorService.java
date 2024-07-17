package com.example.Grocery_Delivery.Services;

import com.example.Grocery_Delivery.Models.ProductModel;
import com.example.Grocery_Delivery.Models.VendorModel;
import com.example.Grocery_Delivery.Repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService {

    @Autowired
    VendorRepository vendor;

    public List<VendorModel> getAll(){
        return (List<VendorModel>) vendor.findAll();
    }

    public void addVendor(VendorModel Vendor){
        vendor.save(Vendor);
    }
}
