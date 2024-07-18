package com.example.Grocery_Delivery.Services;

import com.example.Grocery_Delivery.Models.ProductModel;
import com.example.Grocery_Delivery.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {

    @Autowired
     private ProductRepository productRepository;

    public List<ProductModel> getAllProduct(){
        return (List<ProductModel>) productRepository.findAll();
    }

    public ProductModel addProduct(ProductModel productModel){
        return productRepository.save(productModel);
    }

    public void updateProductP(Long PRODUCTID,Float PRICE ){
        productRepository.updateProductP(PRODUCTID,PRICE);
    }

    public void updateProductD(Long PRODUCTID,String PRODUCTDESCRIPTION){
        productRepository.updateProductD(PRODUCTID,PRODUCTDESCRIPTION);
    }

    public void updateProductDP(Long PRODUCTID,String PRODUCTDESCRIPTION,Float PRICE ){
        productRepository.updateProductDP(PRODUCTID,PRODUCTDESCRIPTION,PRICE);
    }

    public void updateProductStockDate(Long PRODUCTID,Long STOCK, LocalDate M_DATE,LocalDate EXP_DATE){
        productRepository.updateProductStockDate(PRODUCTID,STOCK,M_DATE,EXP_DATE);
    }

    public void removeProduct(Long PRODUCTID){
        productRepository.deleteById(PRODUCTID);
    }

}
