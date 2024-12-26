package com.grocerydelivery.backend.Services;

import com.grocerydelivery.backend.Models.AddressModel;
import com.grocerydelivery.backend.Repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;
    public AddressModel addAddress(AddressModel addressModel){
        return addressRepository.save(addressModel);
    }
}
