package com.example.Grocery_Delivery.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Table(name = "Product")
@Entity
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "P_Id")
    private Long P_Id;

    @Column(name = "V_Id")
    private Long V_Id;

    @Column(name = "P_Name")
    private String P_Name;

    @Column(name = "P_Desc")
    private String P_Desc;

    @Column(name = "Price")
    private Float Price;

    @Column(name = "M_Date")
    private Date M_Date;

    @Column(name = "E_Date")
    private Date E_Date;

    @Column(name = "C_Name")
    private String C_Name;

    @Column(name = "quantity")
    private Long Quantity;
}
