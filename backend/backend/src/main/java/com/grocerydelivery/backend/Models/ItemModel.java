package com.grocerydelivery.backend.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
@Entity
@Table(name = "Item")
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
    private Integer QUNATITY;

    @Column(name="PRICE")
    private Double PRICE;

    public ItemModel(){

    }
}
