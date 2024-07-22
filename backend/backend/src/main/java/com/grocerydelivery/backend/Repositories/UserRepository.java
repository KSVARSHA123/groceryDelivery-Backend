package com.grocerydelivery.backend.Repositories;

import com.grocerydelivery.backend.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE Users SET USERNAME = :NAME, USERPHONE = :PHONE WHERE USERID = :USERID", nativeQuery = true)
    void updateUserNP(@Param("NAME") String NAME, @Param("PHONE") Long PHONE, @Param("USERID") Long USERID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Users SET USERNAME = :NAME WHERE USERID = :USERID", nativeQuery = true)
    void updateUserN(@Param("NAME") String NAME, @Param("USERID") Long USERID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Users SET USERPHONE = :PHONE WHERE USERID = :USERID", nativeQuery = true)
    void updateUserP(@Param("PHONE") Long PHONE, @Param("USERID") Long USERID);

    UserModel findByUSEREMAILAndUSERPASSWORD(String USEREMAIL,String USERPASSWORD);
}
