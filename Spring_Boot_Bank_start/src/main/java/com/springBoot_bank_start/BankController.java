package com.springBoot_bank_start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.BankCustomer;
import service.BankCustomerService;

@Controller
@RequestMapping("/bank")
public class BankController {

	@Autowired
	private BankCustomerService bankService;
	
	@GetMapping
	public String showHomePage(Model model) {
		model.addAttribute("bankRequest", new BankRequest());
		return "form";
	}

	@PostMapping
	public String onSubmit(BankRequest req, Model model) {
		var id = req.getId();
		
		BankCustomer customer = bankService.getCustomer(id);
		if (customer == null)
			return "unknownCustomer";
		
		String view = customer.isBalancePositive() ? "balance" : "negativeBalance";

		model.addAttribute("customer", customer);

		return view;
	}
}