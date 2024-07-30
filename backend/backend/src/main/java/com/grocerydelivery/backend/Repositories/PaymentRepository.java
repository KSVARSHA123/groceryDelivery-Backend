package com.grocerydelivery.backend.Repositories;

import com.grocerydelivery.backend.Models.PaymentModel;
import com.grocerydelivery.backend.Models.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentModel, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO PAYMENT (ORDERID, USERID, AMOUNT, PAYMENTMETHOD, PAYMENTSTATUS) VALUES (:ORDERID, :USERID, :AMOUNT, :PAYMENTMETHOD, :PAYMENTSTATUS)", nativeQuery = true)
    void insertPayment(@Param("ORDERID") Long ORDERID, @Param("USERID") Long USERID, @Param("AMOUNT") Float AMOUNT, @Param("PAYMENTMETHOD") String PAYMENTMETHOD, @Param("PAYMENTSTATUS") boolean PAYMENTSTATUS);

    @Modifying
    @Transactional
    @Query(value = "UPDATE PAYMENT SET PAYMENTSTATUS = :PAYMENTSTATUS WHERE ORDERID = :ORDERID", nativeQuery = true)
    void makePayment(@Param("ORDERID") Long ORDERID, @Param("PAYMENTSTATUS") boolean PAYMENTSTATUS);

    @Query(value = "SELECT PAYMENTSTATUS FROM PAYMENT WHERE ORDERID= :ORDERID",nativeQuery = true)
    boolean getPaymentStatus(@Param("ORDERID") Long ORDERID);

    @Query(value = "SELECT PAYMENTMETHOD FROM PAYMENT WHERE ORDERID= :ORDERID",nativeQuery = true)
    String getPaymentMode(@Param("ORDERID") Long ORDERID);
}
