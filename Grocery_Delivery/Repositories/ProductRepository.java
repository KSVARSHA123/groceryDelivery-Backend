package com.example.Grocery_Delivery.Repositories;

import com.example.Grocery_Delivery.Models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE Product SET PRODUCTDESCRIPTION = :PRODUCTDESCRIPTION WHERE PRODUCTID = :PRODUCTID", nativeQuery = true)
    void updateProductD(@Param("PRODUCTID") Long PRODUCTID, @Param("PRODUCTDESCRIPTION") String PRODUCTDESCRIPTION);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Product SET PRICE = :PRICE WHERE PRODUCTID = :PRODUCTID", nativeQuery = true)
    void updateProductP(@Param("PRODUCTID") Long PRODUCTID, @Param("PRICE") Float PRICE);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Product SET PRODUCTDESCRIPTION = :PRODUCTDESCRIPTION,PRICE = :PRICE WHERE PRODUCTID = :PRODUCTID", nativeQuery = true)
    void updateProductDP(@Param("PRODUCTID") Long PRODUCTID, @Param("PRODUCTDESCRIPTION") String PRODUCTDESCRIPTION,@Param("PRICE") Float PRICE);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Product SET STOCK= :STOCK, MANUFACTUREDATE= :M_DATE,EXPIRYDATE= :EXP_DATE WHERE PRODUCTID = :PRODUCTID", nativeQuery = true)
    void updateProductStockDate(@Param("PRODUCTID") Long PRODUCTID, @Param("STOCK") Long STOCK, @Param("M_DATE")LocalDate M_DATE,@Param("EXP_DATE") LocalDate EXP_DATE);
}
