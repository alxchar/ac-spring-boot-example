package com.alex;

import com.alex.customer.Customer;
import com.alex.customer.CustomerRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@SpringBootApplication
public class SpringBootExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootExampleApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {
            Faker faker = new Faker();
            Random random = new Random();
            String firstname = faker.name().firstName();
            String lastname = faker.name().lastName();
            Customer customer = new Customer(
                    firstname + " " + lastname,
                    firstname.toLowerCase() + "." + lastname.toLowerCase() + "@gmail.com",
                    random.nextInt(16, 99)
            );
            customerRepository.save(customer);
        };
    }

}
