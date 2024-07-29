package com.grocerydelivery.backend.Repositories;

import com.grocerydelivery.backend.Models.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressModel,Long> {
}
