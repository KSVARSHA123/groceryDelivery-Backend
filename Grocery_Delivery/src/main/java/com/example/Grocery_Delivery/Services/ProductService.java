package com.example.Grocery_Delivery.Services;

import com.example.Grocery_Delivery.Models.ProductModel;
import com.example.Grocery_Delivery.Models.VendorModel;
import com.example.Grocery_Delivery.Repositories.ProductRepository;
import com.example.Grocery_Delivery.Repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository product;

    public List<ProductModel> getAll(){
        return (List<ProductModel>) product.findAll();
    }

    public void addProduct(ProductModel Product){
        product.save(Product);
    }

}
