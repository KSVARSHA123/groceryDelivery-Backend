package com.grocerydelivery.backend.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@Data
@Table(name = "Product")
@Entity
public class ProductModel {
//    PRODUCTID
//            VENDORID
//    PRODUCTDESCRIPTION
//            PRICE
//    MANUFACTUREDATE
//            EXPIRYDATE
//    CATEGORY
//            QUANTITY

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCTID")
    private Long PRODUCTID;

    @Column(name = "VENDORID")
    private Long VENDORID;

    @Column(name = "PRODUCTNAME")
    private String PRODUCTNAME;

    @Column(name = "PRODUCTDESCRIPTION")
    private String PRODUCTDESCRIPTION;

    @Column(name = "PRICE")
    private Float PRICE;

    @Column(name = "MANUFACTUREDATE")
    private LocalDate MANUFACTUTREDATE;

    @Column(name = "EXPIRYDATE")
    private LocalDate EXPIRYDATE;

    @Column(name = "CATEGORY")
    private String CATEGORY;

    @Column(name = "STOCK")
    private Long STOCK;

    public ProductModel(){

    }
}
