package com.example.Grocery_Delivery.Services;

import com.example.Grocery_Delivery.Models.ProductModel;
import com.example.Grocery_Delivery.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<ProductModel> getAllProduct(){
        return (List<ProductModel>) productRepository.findAll();
    }



}
