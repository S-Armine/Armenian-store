package com.armstore.armeniannativestore.controller;

import com.armstore.armeniannativestore.model.company.Company;
import com.armstore.armeniannativestore.model.customer.Address;
import com.armstore.armeniannativestore.model.customer.Customer;
import com.armstore.armeniannativestore.service.CompanyService;
import com.armstore.armeniannativestore.service.CustomerService;
import com.armstore.armeniannativestore.service.Validator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final CustomerService customerService;
    private final CompanyService companyService;

    public RegistrationController(CustomerService customerService, CompanyService companyService) {
        this.customerService = customerService;
        this.companyService = companyService;
    }

    @GetMapping(path = {"", "/customer"})
    public String getRegisterCustomer(Model model) {
        Customer customer = new Customer();;
        model.addAttribute("customer", customer);
        return "customer-signup";
    }

    @GetMapping("/company")
    public String getRegisterCompany(Model model) {
        model.addAttribute("company", new Company());
        return "company-signup";
    }

    @PostMapping(path = {"", "/customer"})
    public String registerCustomer(
            @ModelAttribute("customer") Customer customer,
            @RequestParam("day") int day,
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            RedirectAttributes redirectAttributes
            ) {
        LocalDate date = LocalDate.of(year, month, day);
        customer.setBirthday(date);
        if (!Validator.isValidCustomer(customer)) {
            redirectAttributes.addFlashAttribute("message", "Credentials are not valid." +
                    "Try again.");
            return "redirect:/registration/customer";
        }
        boolean isSaved = customerService.save(customer);
        if (!isSaved) {
            redirectAttributes.addFlashAttribute("message", "Such customer already exists." +
                    "Try again.");
            return "redirect:/registration/customer";
        }
        redirectAttributes.addFlashAttribute("message", "Customer registered successfully. Now log in.");
        return "redirect:/login/customer";
    }

    @PostMapping("/company")
    public String registerCompany(@ModelAttribute("company") Company company, RedirectAttributes redirectAttributes) {
        if (!Validator.isValidCompany(company)) {
            redirectAttributes.addFlashAttribute("message", "Credentials are not valid." +
                    "Try again.");
            return "redirect:/registration/company";
        }
        boolean isSaved = companyService.save(company);
        if (!isSaved) {
            redirectAttributes.addFlashAttribute("message", "Such company already exists." +
                    "Try again.");
            return "redirect:/registration/company";
        }
        redirectAttributes.addFlashAttribute("message", "Company page registered successfully. Now you can log in.");
        return "redirect:/login/company";
    }
}
