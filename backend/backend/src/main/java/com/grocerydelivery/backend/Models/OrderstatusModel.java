package com.grocerydelivery.backend.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "ORDERSTATUS")
@Entity
public class OrderstatusModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATUSID")
    private Long STATUSID;

    @Column(name = "ORDERSTATUS")
    private String ORDERSTATUS;
}
