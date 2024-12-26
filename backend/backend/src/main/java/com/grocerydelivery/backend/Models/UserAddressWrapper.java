package com.grocerydelivery.backend.Models;

public class UserAddressWrapper {

    private UserModel userModel;
    private AddressModel addressModel;

    // Getters and Setters
    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public AddressModel getAddressModel() {
        return addressModel;
    }

    public void setAddressModel(AddressModel addressModel) {
        this.addressModel = addressModel;
    }
}
