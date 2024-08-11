package com.alex.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsCustomerByEmail(String email);

    @Query("delete from Customer where id=:customerId")
    Customer deleteCustomerById(Integer customerId);

}
