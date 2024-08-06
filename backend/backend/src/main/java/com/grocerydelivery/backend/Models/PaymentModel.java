package com.grocerydelivery.backend.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "PAYMENT")
@Entity
public class PaymentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PAYMENTID")
    private Long PAYMENTID;

    @Column(name = "ORDERID")
    private Long ORDERID;

    @Column(name = "USERID")
    private Long USERID;

    @Column(name = "PAYMENTMETHOD")
    private Long PAYMENTMETHOD;

    @Column(name = "AMOUNT")
    private Float AMOUNT;

    @Column(name = "PAYMENTSTATUS")
    private boolean PAYMENTSTATUS;
}
