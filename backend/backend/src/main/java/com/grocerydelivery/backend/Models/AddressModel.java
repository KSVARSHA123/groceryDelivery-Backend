package com.grocerydelivery.backend.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "ADDRESS")
@Entity
public class AddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDRESSID")
    private Long ADDRESSID;

    @Column(name = "ADDRESSLINE")
    private String ADDRESSLINE;

    @Column(name = "COUNTRY")
    private String COUNTRY;

    @Column(name = "STATE")
    private String STATE;

    @Column(name = "CITY")
    private String CITY;

    @Column(name = "PINCODE")
    private Float PINCODE;

    @Column(name = "USERID")
    private Long USERID;
}
