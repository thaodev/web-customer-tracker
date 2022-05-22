package com.luv2code.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	//need to inject the customer DAO as Customer Controller depends on Customer DAO
	@Autowired//with autowired, Spring will scan for component that implements CustomerDAO
	//private CustomerDAO customerDAO;//controller no longer connect to DAO but thru services
	private CustomerService customerService;
	
	
	@GetMapping("/list")
	public String listCustomers(Model theModel) {
		
		//get customers from the DAO, switch to service
		List<Customer> theCustomers = customerService.getCustomers();
		
		
		//add the customers to the model
		theModel.addAttribute("customers", theCustomers);
		
		return "list-customers";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		//create model attribute to bind form data
		Customer theCustomer = new Customer();
		
		theModel.addAttribute("customer", theCustomer);
		
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer( Customer theCustomer){
		//@ModelAttribute("customers")
		//save the customer using our service
		customerService.saveCustomer(theCustomer);
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String updateCustomer(@RequestParam("customerId") int theId,
					Model theModel) {
		
		//get the customer from our service
		Customer theCustomer = customerService.getCustomers(theId);
		
		//update customer as a model attribute to pre-populate the form
		theModel.addAttribute("customer", theCustomer);
		
		//send over to our form
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId){
		
		//save the customer using our service
		customerService.deleteCustomer(theId);
		return "redirect:/customer/list";
	}
	
	@GetMapping("/search")
	public String searchCustomer(@RequestParam("theSearchName") String theSearchName, 
												Model theModel) {
		//search customers from the service
		List<Customer> theCustomers = customerService.searchCustomers(theSearchName);
		
		//add the customers to the model
		theModel.addAttribute("customers", theCustomers);
		return "list-customers";
	}

}
