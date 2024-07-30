package com.grocerydelivery.backend.Repositories;

import com.grocerydelivery.backend.Models.ItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemModel,Long> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO ITEMS (PRODUCTID, QUANTITY, PRICE, USERID) VALUES (:PRODUCTID, :QUANTITY, :PRICE, :USERID)", nativeQuery = true)
    void saveItem(@Param("PRODUCTID") Long PRODUCTID, @Param("QUANTITY") Long QUANTITY, @Param("PRICE") Float PRICE, @Param("USERID") Long USERID);

    @Query(value = "SELECT CASE WHEN COUNT(ITEMID) > 0 THEN 1 ELSE 0 END FROM ITEMS WHERE USERID = :USERID AND ORDERID IS NULL",nativeQuery = true)
    Long existsUser(@Param("USERID") Long USERID);

    @Query(value = "SELECT SUM(CAST(PRICE AS Float)*CAST(QUANTITY AS Float)) AS total FROM ITEMS WHERE USERID= :USERID",nativeQuery = true)
    Float total(@Param("USERID") Long USERID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ITEMS SET ORDERID= :ORDERID WHERE USERID= :USERID AND ORDERID IS NULL",nativeQuery = true)
    void updateOrderid(@Param("USERID") Long USERID,@Param("ORDERID") Long ORDERID);

    @Query(value = "SELECT P.PRODUCTNAME,I.QUANTITY,I.PRICE FROM ITEMS I JOIN PRODUCT P ON I.PRODUCTID=P.PRODUCTID WHERE I.USERID= :USERID AND I.ORDERID IS NULL",nativeQuery = true)
    List<String> showItems(@Param("USERID") Long USERID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ITEMS SET QUANTITY = QUANTITY - :QUANTITY WHERE USERID = :USERID AND ORDERID = :ORDERID AND QUANTITY >= :QUANTITY AND PRODUCTID= :PRODUCTID",nativeQuery = true)
    void decreaseItemQuantity(@Param("USERID") Long USERID, @Param("ORDERID") Long ORDERID,@Param("QUANTITY") Long QUANTITY,@Param("PRODUCTID") Long PRODUCTID);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM ITEMS WHERE USERID = :USERID AND ORDERID = :ORDERID AND PRODUCTID= :PRODUCTID",nativeQuery = true)
    void deleteItem(@Param("USERID") Long USERID, @Param("ORDERID") Long ORDERID,@Param("PRODUCTID") Long PRODUCTID);

    @Query(value = "SELECT p.PRODUCTNAME, p.PRICE, i.QUANTITY FROM ITEMS i JOIN Product p ON i.PRODUCTID = p.PRODUCTID WHERE i.ORDERID = :ORDERID", nativeQuery = true)
    List<Object[]> findItemsByOrderId(@Param("ORDERID") Long ORDERID);

    @Query(value = "SELECT P.VENDORID FROM ITEMS I JOIN PRODUCT P ON I.PRODUCTID=P.PRODUCTID WHERE ORDERID= :ORDERID LIMIT 1",nativeQuery = true)
    Long getVendorid(@Param("ORDERID") Long ORDERID);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM ITEMS WHERE USERID= :USERID AND PRODUCTID= :PRODUCTID AND ORDERID IS NULL",nativeQuery = true)
    void removeFromCart(@Param("USERID") Long USERID,@Param("PRODUCTID") Long PRODUCTID);

    @Query(value = "SELECT QUANTITY FROM ITEMS WHERE USERID= :USERID AND PRODUCTID= :PRODUCTID AND ORDERID IS NULL",nativeQuery = true)
    Long checkItem(@Param("USERID") Long USERID,@Param("PRODUCTID") Long PRODUCTID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ITEMS SET QUANTITY= QUANTITY-:QUANTITY WHERE USERID= :USERID AND PRODUCTID= :PRODUCTID AND ORDERID IS NULL",nativeQuery = true)
    void removeFromCart(@Param("USERID") Long USERID,@Param("PRODUCTID") Long PRODUCTID,@Param("QUANTITY") Long QUANTITY);
    }
