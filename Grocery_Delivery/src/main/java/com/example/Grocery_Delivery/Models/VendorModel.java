package com.example.Grocery_Delivery.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Data
@Table(name = "Vendor")
@Entity
public class VendorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "V_Id")
    private Long V_Id;

    @Column(name = "V_Name")
    private String V_Name;

    @Column(name = "V_Email")
    private String V_Email;

    @Column(name = "Address_id")
    private Long Address_id;

    @Column(name = "V_Phone")
    private Long V_Phone;

    @Column(name = "V_Password")
    private String V_Password;

    public VendorModel(Long V_Id,String V_Name,String V_Email,Long Address_id,Long V_Phone,String V_Password) {
        this.V_Id = V_Id;
        this.V_Name = V_Name;
        this.V_Email = V_Email;
        this.Address_id = Address_id;
        this.V_Phone = V_Phone;
        this.V_Password=V_Password;
    }
}
