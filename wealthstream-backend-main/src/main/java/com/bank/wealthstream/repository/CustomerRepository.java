package com.bank.wealthstream.repository;

import com.bank.wealthstream.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query("SELECT C FROM Customer C " +
            "JOIN Person P ON C.idPer = P.idPer " +
            "WHERE P.identification=?1")
    Customer getCustomerByIdentification(String identification);

    @Query("SELECT C FROM Customer C " +
            "JOIN Person P ON C.idPer = P.idPer " +
            "WHERE P.identification=?1")
    Optional<Customer> getCustomerByIdentificationOptional(String identification);
}
