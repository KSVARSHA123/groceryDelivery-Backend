package com.grocerydelivery.backend.Repositories;

import com.grocerydelivery.backend.Models.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressModel,Long> {

    @Query(value = "SELECT CONCAT(ADDRESSLINE,',',COUNTRY,',',STATE,',',CITY,'-','PINCODE') FROM ADDRESS WHERE USERID= :VENDORID",nativeQuery = true)
    String getAddress(@Param("VENDORID") Long VENDORID);
}
