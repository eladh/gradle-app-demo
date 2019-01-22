package com.jfrog.demogradle.controllers;


import com.jfrog.demogradle.entities.Customer;
import com.jfrog.demogradle.repositories.CustomerRepository;
import com.jfrog.demogradle.services.GeoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerRepository repository;

    // Define the log object for this class
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @RequestMapping("/getIpInfo")
    @ResponseBody
    public String getInfo(@RequestParam("ip") String ip) throws Exception {
        return  "";
    }


    private void init() {
        this.repository.deleteAll();

        // save a couple of customers
        this.repository.save(new Customer("Alice", "Smith"));
        this.repository.save(new Customer("Bob", "Smith"));

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : this.repository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Customer found with findByFirstName('Alice'):");
        System.out.println("--------------------------------");
        System.out.println(this.repository.findByFirstName("Alice"));

        System.out.println("Customers found with findByLastName('Smith'):");
        System.out.println("--------------------------------");
        for (Customer customer : this.repository.findByLastName("Smith")) {
            System.out.println(customer);
        }
    }

}