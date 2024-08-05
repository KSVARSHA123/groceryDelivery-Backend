package com.grocerydelivery.backend.Repositories;

import com.grocerydelivery.backend.Models.DeliveryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;

public interface DeliveryRepository extends JpaRepository<DeliveryModel,Long> {

    @Query(value = "SELECT COUNT(*) FROM DELIVERY WHERE DELIVERYPERSONID= :USERID",nativeQuery = true)
    Long checkUser(@Param("USERID") Long USERID);

    @Query(value = "SELECT COUNT(*) FROM DELIVERY WHERE AVAILABILITY=true",nativeQuery = true)
    Long availability();

    @Transactional
    @Modifying
    @Query(value = "UPDATE DELIVERY SET USERID= :USERID,ORDERID= :ORDERID,AVAILABILITY=false WHERE DELIVERYPERSONID=(SELECT DELIVERYPERSONID FROM DELIVERY WHERE AVAILABILITY=true LIMIT 1)",nativeQuery = true)
    void assignDelivery(@Param("USERID") Long USERID, @Param("ORDERID") Long ORDERID);

//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE DELIVERY SET USERID= null,ORDERID=null,AVAILABILITY=true WHERE DELIVERYPERSONID= :DELIVERYPERSONID",nativeQuery = true)
//    void updateAvailability(@Param("DELIVERYPERSONID") Long DELIVERYPERSONID);

    @Query(value = "SELECT ORDERID FROM DELIVERY WHERE DELIVERYPERSONID= :DELIVERYPERSONID",nativeQuery = true)
    Long getOrderid(@Param("DELIVERYPERSONID") Long DELIVERYPERSONID);

    @Query(value = "SELECT USERID FROM DELIVERY WHERE DELIVERYPERSONID= :DELIVERYPERSONID",nativeQuery = true)
    Long getUserid(@Param("DELIVERYPERSONID") Long DELIVERYPERSONID);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO DELIVERY VALUES(:DELIVERYPERSONID,:AVAILABILITY,:ORDERID,:USERID)",nativeQuery = true)
    void addDelivery(@Param("DELIVERYPERSONID") Long DELIVERYPERSONID,@Param("AVAILABILITY") boolean AVAILABILITY,@Param("ORDERID") Long ORDERID,@Param("USERID") Long USERID);
}
