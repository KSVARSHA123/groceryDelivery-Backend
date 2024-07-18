package com.example.Grocery_Delivery.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
@Table(name = "Users")
@Entity
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERID")
    private Long USERID;

    @Column(name = "USERNAME")
    private String USERNAME;

    @Column(name = "USEREMAIL")
    private String USEREMAIL;

    @Column(name = "USERPHONE")
    private Long USERPHONE;

    @Column(name = "USERPASSWORD")
    private String USERPASSWORD;

    @Column(name = "USERROLE")
    private String USERROLE;

    public UserModel(){}

//    public UserModel(Long USERID,String USERNAME,String USEREMAIL,Long USERPHONE,String USERPASSWORD,String USERROLE) {
//        this.USERID = USERID;
//        this.USERNAME = USERNAME;
//        this.USEREMAIL = USEREMAIL;
//        this.USERPHONE = USERPHONE;
//        this.USERPASSWORD = USERPASSWORD;
//        this.USERROLE=USERROLE;
//    }
}
