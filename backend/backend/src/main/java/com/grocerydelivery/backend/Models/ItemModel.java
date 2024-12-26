package com.grocerydelivery.backend.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "Items")
public class ItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ITEMID")
    private Long ITEMID;

    @Column(name="ORDERID")
    private Long ORDERID;

    @Column(name="PRODUCTID")
    private Long PRODUCTID;

    @Column(name="QUANTITY")
    private Long QUNATITY;

    @Column(name="PRICE")
    private Float PRICE;

    @Column(name="USERID")
    private Long USERID;

    public ItemModel(){

    }
}
