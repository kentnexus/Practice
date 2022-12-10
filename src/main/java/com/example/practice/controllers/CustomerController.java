package com.example.practice.controllers;

import com.example.practice.entities.Customer;
import com.example.practice.repositories.CustomerRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/index")
    public String customers(Model model, ModelMap mm) throws ParseException {
        List<Customer> customers = customerRepository.findAll();

        mm.put("a", 0);
        int custSize = 0;
        if (customers.size() != 0)
            custSize = customers.size();

        int rSeats = 20 - custSize;

//        date
        Date date1 = new Date();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(date1);

        model.addAttribute("customers", customers);
        model.addAttribute("seats", rSeats);
        model.addAttribute("cust", new Customer());
        model.addAttribute("todays", date);
        return "index";
    }

    @GetMapping("/")
    public String customers2(Model model, ModelMap mm) {
        List<Customer> customers = customerRepository.findAll();

        mm.put("a", 0);
        int custSize = 0;
        if (customers.size() != 0)
            custSize = customers.size();

        int rSeats = 20 - custSize;

//        date
        Date date1 = new Date();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(date1);

        model.addAttribute("customers", customers);
        model.addAttribute("seats", rSeats);
        model.addAttribute("cust", new Customer());
        model.addAttribute("todays", date);

        return "index";
    }

    @PostMapping("/save")
    public String save(Customer customer, HttpServletRequest request, ModelMap mm) {
        List<Customer> customers = customerRepository.findAll();

        String seatno = request.getParameter("seatno");

        for (Customer c : customers) {
            if (c.getSeatno() == seatno) {
                mm.put("a", 1);
            } else {
                mm.put("a", 0);
                customerRepository.save(customer);
            }
        }

        return "redirect:/";
    }

    @PostMapping("/edit/save")
    public String editSave(Customer customer, HttpServletRequest request, ModelMap mm) throws ParseException {

        long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        String seatno = request.getParameter("seatno");
        String date = request.getParameter("tdate");

        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);


        Customer cust = customerRepository.findById(id).orElseThrow();
        cust.setSeatno(seatno);
        cust.setName(name);
        cust.setTdate(date1);

        List<Customer> customers = customerRepository.findAll();
        for (Customer c : customers) {
            if (c.getSeatno() == seatno && c.getId() != id) {
                mm.put("a", 1);
            } else {
                mm.put("a", 0);
                customerRepository.save(cust);
            }
        }

        return "redirect:/";
    }

    @GetMapping("/delete")
    public String delete(Customer customer, long id) {
        customerRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String edit(Customer customer, long id, Model model) {

        customer = customerRepository.findById(id).orElseThrow();

        model.addAttribute("cust", customer);

        return "edit";
    }

}
