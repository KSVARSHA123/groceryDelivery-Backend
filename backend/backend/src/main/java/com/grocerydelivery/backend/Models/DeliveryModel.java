package com.grocerydelivery.backend.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "DELIVERY")
@Entity
public class DeliveryModel {

    @Id
    @Column(name = "DELIVERYPERSONID")
    private Long DELIVERYPERSONID;

    @Column(name = "AVAILABILITY")
    private boolean AVAILABILITY;

    @Column(name = "ORDERID")
    private Long ORDERID;

    @Column(name = "USERID")
    private Long USERID;
}
