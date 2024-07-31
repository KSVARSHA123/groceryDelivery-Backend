package com.grocerydelivery.backend.Repositories;

import com.grocerydelivery.backend.Models.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressModel,Long> {

    @Query(value = "SELECT ADDRESSID,CONCAT(ADDRESSLINE,',',COUNTRY,',',STATE,',',CITY,'-','PINCODE') FROM ADDRESS WHERE USERID= :USERID",nativeQuery = true)
    List<Object[]> getAddress(@Param("USERID") Long USERID);
}
