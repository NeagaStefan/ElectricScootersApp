package com.example.electricscootersapp.Repository;

import com.example.electricscootersapp.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {
    @Transactional
    @Query("select c from Customer c order by c.customerId")

    List<Customer> findAllCustomers();

    @Transactional
    @Modifying
    @Query("update Customer c set c.deleted = true where c.customerId = :customerId")
    void softDelete(@Param("customerId") Long customerId);

    @Transactional
    @Modifying
    @Query("update Customer  c set c.password =:newPass where c.userName =:userName")
    void updatePassword(@Param("userName") String userName,@Param("newPass") String newPass);

    @Transactional
    @Modifying
    @Query("update Scooter s set s.status='Taken', s.startDate=:startingTime where s.scooterId=:scooterId")
    void startRenting(@Param("startingTime") Timestamp startingTime, @Param("scooterId") Long scooterId);

    @Query("select c from Customer c where c.userName=?1")
    Customer findByUserName(String userName);
}
