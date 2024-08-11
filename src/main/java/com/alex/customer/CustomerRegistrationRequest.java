package com.alex.customer;

public record CustomerRegistrationRequest(
        String email,
        String name,
        Integer age ) {
}
