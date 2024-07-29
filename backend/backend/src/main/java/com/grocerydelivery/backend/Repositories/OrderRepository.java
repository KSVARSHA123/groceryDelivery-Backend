package com.grocerydelivery.backend.Repositories;

import com.grocerydelivery.backend.Models.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel,Long> {

    @Transactional
    @Modifying
    @Query(value ="UPDATE ORDERDETAILS SET ORDERCONFIRMATION= :CONFIRM where USERID= :USERID AND ORDERSTATUSID=1",nativeQuery = true)
    void confirmT(@Param("USERID") Long USERID,@Param("CONFIRM") Long CONFIRM);

    @Transactional
    @Modifying
    @Query(value ="UPDATE ORDERDETAILS SET ORDERCONFIRMATION= :CONFIRM,ORDERSTATUSID=5 where USERID= :USERID AND ORDERSTATUSID=1",nativeQuery = true)
    void confirmF(@Param("USERID") Long USERID,@Param("CONFIRM") Long CONFIRM);

    @Query(value = "SELECT ORDERSTATUSID FROM ORDERDETAILS WHERE ORDERID= :ORDERID",nativeQuery = true)
    Long viewStatus(@Param("ORDERID") Long ORDERID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ORDERDETAILS od JOIN TIMESLOT s ON od.TIMESLOTID = s.SLOTID SET od.ORDERSTATUSID = od.ORDERSTATUSID+1 WHERE od.ORDERID = :ORDERID AND od.ORDERSTATUSID = 1 AND od.DELIVERYDATE = DATE(CURRENT_DATE) AND (TIME(NOW()) BETWEEN s.STARTTIME AND s.ENDTIME) AND od.ORDERCONFIRMATION = true",nativeQuery = true)
    void updateStatus1(@Param("ORDERID") Long ORDERID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ORDERDETAILS od JOIN TIMESLOT s ON od.TIMESLOTID = s.SLOTID SET od.ORDERSTATUSID = od.ORDERSTATUSID+1 WHERE od.ORDERID = :ORDERID AND od.ORDERSTATUSID = 1 AND od.DELIVERYDATE = DATE(CURRENT_DATE) AND (TIME(NOW()) BETWEEN s.STARTTIME AND s.ENDTIME) AND od.ORDERCONFIRMATION = true AND od.PAYMENTCONFIRMATION = true",nativeQuery = true)
    void updateStatus2(@Param("ORDERID") Long ORDERID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ORDERDETAILS SET od.ORDERSTATUSID = od.ORDERSTATUSID+1 WHERE od.ORDERID = :ORDERID",nativeQuery = true)
    void updateStatus3(@Param("ORDERID") Long ORDERID);

    @Query(value = "SELECT USERID FROM ORDERDETAILS WHERE ORDERID= :ORDERID",nativeQuery = true)
    Long getUSERID(@Param("ORDERID") Long ORDERID);

    @Query(value = "SELECT TOTAL FROM ORDERDETAILS WHERE ORDERID= :ORDERID",nativeQuery = true)
    Float getAMOUNT(@Param("ORDERID") Long ORDERID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE PAYMENTCONFIRMATION= :PAYMENTCONFIRMATION WHERE ORDERID= :ORDERID",nativeQuery = true)
    void updatePayment(@Param("ORDERID") Long ORDERID,@Param("PAYMENTCONFIRMATION") boolean PAYMENTCONFIRMATION);
}
