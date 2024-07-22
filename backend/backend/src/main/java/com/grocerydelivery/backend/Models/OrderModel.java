package com.grocerydelivery.backend.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Data
@Entity
@Table(name="Order")
public class OrderModel  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDERID")
    private Long ORDERID;


    @Column (name = "USERID")
    private Long USERID;

    @Column(name = "STATUSID")
    private Long STATUSID;

    @Column(name = "TOTALAMOUNT")
    private Double TOTALAMOUNT;

    @Column(name = "ORDERDATE")
    private LocalDate ORDERDATE;

    @Column(name = "DELIVERYDATE")
    private LocalDate DELIVERYDATE;

    @Column(name = "ORDERCONFIRMATION")
    private boolean ORDERCONFIRMATION;

    @Column(name = "PAYMENTCONFIRMATION")
    private boolean PAYMENTCONFIRMATION;


    public OrderModel(){

    }

}
