package com.grocerydelivery.backend.Repositories;

import com.grocerydelivery.backend.Models.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel,Long> {

    @Transactional
    @Modifying
    @Query(value ="UPDATE ORDERDETAILS O JOIN TIMESLOT T SET O.ORDERCONFIRMATION= 1,O.ORDERSTATUSID=2 where O.ORDERID= :ORDERID AND O.ORDERSTATUSID=1 AND TIME(NOW())<T.STARTTIME-INTERVAL 2 HOUR",nativeQuery = true)
    void confirmT(@Param("ORDERID") Long ORDERID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ORDERDETAILS SET TOTAL=:TOTAL WHERE ORDERID=:ORDERID",nativeQuery = true)
    void setTOTAL(@Param("ORDERID") Long ORDERID,@Param("TOTAL") Float TOTAL);

    @Transactional
    @Modifying
    @Query(value ="UPDATE ORDERDETAILS O JOIN TIMESLOT T ON O.TIMESLOTID = T.SLOTID SET O.ORDERSTATUSID=:ORDERSTATUSID WHERE ORDERID=:ORDERID AND O.DELIVERYDATE<=CURRENT_DATE AND ((O.ORDERSTATUSID = 1 AND TIME(NOW()) > T.STARTTIME - INTERVAL 2 HOUR) OR (O.ORDERSTATUSID = 2 AND TIME(NOW()) > T.STARTTIME-INTERVAL 1 HOUR) OR (O.ORDERSTATUSID = 4 AND TIME(NOW()) > T.STARTTIME-INTERVAL 30 MINUTE))",nativeQuery = true)
    void cancellation(@Param("ORDERID") Long ORDERID,@Param("ORDERSTATUSID") Long ORDERSTATUSID);


    @Transactional
    @Modifying
    @Query(value ="UPDATE ORDERDETAILS SET ORDERSTATUSID=8 WHERE ORDERID=:ORDERID ",nativeQuery = true)
    void cancel(@Param("ORDERID") Long ORDERID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ORDERDETAILS O JOIN TIMESLOT T ON O.TIMESLOTID=T.SLOTID SET O.ORDERSTATUSID=:ORDERSTATUSID WHERE O.ORDERID=:ORDERID AND (TIME(NOW())>T.STARTTIME-INTERVAL 1 HOUR) AND O.DELIVERYDATE=CURRENT_DATE()",nativeQuery = true)
    void updateStatusPackaging(@Param("ORDERID") Long ORDERID,@Param("ORDERSTATUSID") Long ORDERSTATUSID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ORDERDETAILS O JOIN TIMESLOT T ON O.TIMESLOTID=T.SLOTID SET O.ORDERSTATUSID=:ORDERSTATUSID WHERE O.ORDERID=:ORDERID AND  (TIME(NOW())>T.STARTTIME-30 MINUTE) AND O.DELIVERYDATE=CURRENT_DATE()",nativeQuery = true)
    void updateStatusOFD(@Param("ORDERID") Long ORDERID,@Param("ORDERSTATUSID") Long ORDERSTATUSID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ORDERDETAILS O JOIN TIMESLOT T SET O.ORDERSTATUSID = 7 WHERE O.ORDERID = :ORDERID AND (TIME(NOW())>T.STARTTIME)",nativeQuery = true)
    void updateStatusD(@Param("ORDERID") Long ORDERID);


    @Query(value = "SELECT ORDERSTATUSID FROM ORDERDETAILS WHERE ORDERID=:ORDERID",nativeQuery = true)
    Long getOrderStatusid(@Param("ORDERID") Long ORDERID);

    @Query(value = "SELECT USERID FROM ORDERDETAILS WHERE ORDERID= :ORDERID",nativeQuery = true)
    Long getUSERID(@Param("ORDERID") Long ORDERID);

    @Query(value = "SELECT TOTAL FROM ORDERDETAILS WHERE ORDERID= :ORDERID",nativeQuery = true)
    Float getAMOUNT(@Param("ORDERID") Long ORDERID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ORDERDETAILS SET PAYMENTCONFIRMATION = :PAYMENTCONFIRMATION, ORDERSTATUSID = :ORDERSTATUSID WHERE ORDERID = :ORDERID", nativeQuery = true)
    void updatePayment(@Param("ORDERID") Long ORDERID, @Param("PAYMENTCONFIRMATION") boolean PAYMENTCONFIRMATION, @Param("ORDERSTATUSID") Long ORDERSTATUSID);

    @Query(value = "SELECT o.ORDERCONFIRMATION,s.ORDERSTATUS, o.DELIVERYDATE, t.STARTTIME, t.ENDTIME ,o.TOTAL, o.ORDERDATE,o.ORDERID FROM ORDERDETAILS o JOIN ORDERSTATUS s ON o.ORDERSTATUSID = s.STATUSID JOIN TIMESLOT t ON o.TIMESLOTID = t.SLOTID WHERE o.USERID = :USERID", nativeQuery = true)
    List<Object[]> showOrders(@Param("USERID") Long USERID);

    @Query(value = "SELECT o.ORDERID,p.PRODUCTNAME,i.QUANTITY,p.PRODUCTDESCRIPTION,p.PRICE FROM ORDERDETAILS o JOIN ORDERSTATUS s ON o.ORDERSTATUSID = s.STATUSID JOIN TIMESLOT t ON o.TIMESLOTID = t.SLOTID JOIN ITEMS i ON o.ORDERID = i.ORDERID JOIN PRODUCT p ON p.PRODUCTID=i.PRODUCTID WHERE p.VENDORID = :VENDORID",nativeQuery = true)
    List<Object[]> findProductByVendorId(@Param("VENDORID") Long VENDORID);

    @Query(value = "SELECT s.ORDERSTATUS, o.DELIVERYDATE, t.STARTTIME, t.ENDTIME ,o.TOTAL,o.ORDERID FROM ORDERDETAILS o JOIN ORDERSTATUS s ON o.ORDERSTATUSID = s.STATUSID JOIN TIMESLOT t ON o.TIMESLOTID = t.SLOTID JOIN ITEMS i ON o.ORDERID = i.ORDERID JOIN PRODUCT p ON p.PRODUCTID=i.PRODUCTID WHERE p.VENDORID = :VENDORID GROUP BY ORDERID", nativeQuery = true)
    List<Object[]> findOrdersByVendorId(@Param("VENDORID") Long VENDORID);

    @Query(value = "SELECT CONCAT_WS(',',ADDRESSLINE,COUNTRY,STATE,CITY,CONCAT('-',PINCODE)) FROM ADDRESS A JOIN ORDERDETAILS O ON A.ADDRESSID=O.ADDRESSID WHERE O.ORDERID= :ORDERID",nativeQuery = true)
    String getAddress(@Param("ORDERID") Long ORDERID);
}
