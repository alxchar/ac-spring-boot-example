package com.alex.customer;

import com.alex.exception.DuplicateResourceException;
import com.alex.exception.ResourceNotFound;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jpa") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers() {
        return customerDao.selectAllCustomers();
    }

    public Customer getCustomer(Integer id) {
        return customerDao.selectCustomerById(id)
                .orElseThrow(() -> new ResourceNotFound("Customer with id: [%s], does not exist".formatted(id)));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        if (customerDao.existsPersonWithEmail(customerRegistrationRequest.email())) {
            throw new DuplicateResourceException("customer with email: [%s] already exists".formatted(customerRegistrationRequest.email()));
        }
        Customer customer = new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age()
        );
        customerDao.insertCustomer(customer);
    }

    public void deleteCustomerByCustomerId(Integer customerId) {
        if (!customerDao.existsPersonWithId(customerId)) {
            throw new ResourceNotFound("customer with id: [%s] does not exist".formatted(customerId));
        }

        customerDao.deleteCustomerById(customerId);
    }

    public void updateCustomer(Integer customerId, CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = customerDao.selectCustomerById(customerId).orElse(null);
        if (customer != null) {
            if (!customer.getName().equals(customerRegistrationRequest.name()))
                customer.setName(customerRegistrationRequest.name());
            else
                throw new ResourceNotFound("customer with id: [%s] does not exist".formatted(customerId));

            if (!customer.getEmail().equals(customerRegistrationRequest.email()))
                customer.setEmail(customerRegistrationRequest.email());
            else
                throw new ResourceNotFound("customer with id: [%s] does not exist".formatted(customerId));

            if (!customer.getAge().equals(customerRegistrationRequest.age()))
                customer.setAge(customerRegistrationRequest.age());
            else
                throw new ResourceNotFound("customer with id: [%s] does not exist".formatted(customerId));

            customerDao.insertCustomer(customer);
        } else {
            throw new ResourceNotFound("customer with id: [%s] does not exist".formatted(customerId));
        }
    }
}
