package com.springBoot.productStart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.ProductManager;

@Controller
@RequestMapping("/inventory")
public class InventoryController {
	@Autowired
	private ProductManager pManager;

	@GetMapping
	public String showInventory(Model model) {
		model.addAttribute("productList", pManager.getProducts());
		return "productOverview";
	}
}