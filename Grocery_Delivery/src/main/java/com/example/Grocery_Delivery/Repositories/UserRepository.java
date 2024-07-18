package com.example.Grocery_Delivery.Repositories;

import com.example.Grocery_Delivery.Models.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserModel,Long> {
}
