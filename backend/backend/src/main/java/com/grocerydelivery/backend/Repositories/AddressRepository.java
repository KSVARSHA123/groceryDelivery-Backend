package com.grocerydelivery.backend.Repositories;

import com.grocerydelivery.backend.Models.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressModel,Long> {

    @Query(value = "SELECT CONCAT_WS(',',ADDRESSLINE,COUNTRY,STATE,CITY,CONCAT('-',PINCODE)) FROM ADDRESS WHERE USERID= :USERID LIMIT 1",nativeQuery = true)
    String getAddress(@Param("USERID") Long USERID);

    @Query(value = "SELECT ADDRESSID,CONCAT_WS(',',ADDRESSLINE,COUNTRY,STATE,CITY,CONCAT('-',PINCODE)) FROM ADDRESS WHERE USERID= :USERID",nativeQuery = true)
    List<Object[]> getAddressa(@Param("USERID") Long USERID);
}
