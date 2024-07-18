package com.example.Grocery_Delivery.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

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
    private Date M_Date;

    @Column(name = "EXPIRYDATE")
    private Date EXPIRYDATE;

    @Column(name = "C_Name")
    private String CATEGORY;

    @Column(name = "QUANTITY")
    private Long QUANTITY;
}
