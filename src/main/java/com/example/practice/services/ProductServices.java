package com.example.practice.services;

import com.example.practice.entities.Customer;
import com.example.practice.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServices {

    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getAllOrders(){
        List<Customer> list = (List<Customer>) customerRepository.findAll();
        return list;
    }
}
