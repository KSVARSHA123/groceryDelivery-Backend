package com.grocerydelivery.backend.Repositories;

import com.grocerydelivery.backend.Models.ItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ItemRepository extends JpaRepository<ItemModel,Long> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO ITEMS (PRODUCTID, QUANTITY, PRICE, USERID) VALUES (:PRODUCTID, :QUANTITY, :PRICE, :USERID)", nativeQuery = true)
    void saveItem(@Param("PRODUCTID") Long PRODUCTID, @Param("QUANTITY") Long QUANTITY, @Param("PRICE") Float PRICE, @Param("USERID") Long USERID);

    @Query("SELECT CASE WHEN COUNT(ITEMID) > 0 THEN true ELSE false END FROM ITEMS WHERE USERID = :USERID AND ORDERID IS NULL")
    boolean existsUser(@Param("USERID") Long USERID);

    @Query("SELECT SUM(CAST(PRICE AS Float)*CAST(QUANTITY AS Float)) AS total FROM ITEMS WHERE USERID= :USERID")
    Float total(@Param("USERID") Long USERID);
}
