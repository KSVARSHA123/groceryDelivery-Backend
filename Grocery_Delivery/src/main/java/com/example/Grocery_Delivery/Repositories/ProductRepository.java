package com.example.Grocery_Delivery.Repositories;

import com.example.Grocery_Delivery.Models.ProductModel;
import com.example.Grocery_Delivery.Models.VendorModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<ProductModel, Long> {
}
