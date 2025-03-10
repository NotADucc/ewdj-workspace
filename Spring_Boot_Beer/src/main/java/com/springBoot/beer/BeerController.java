package com.springBoot.beer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.ColorBean;
import domain.ExpertBean;

@Controller
@RequestMapping("/beer")
public class BeerController {

	@Autowired
	private ColorBean colorBean;

	@Autowired
	private ExpertBean expertBean;

	@GetMapping
	public String showFormPage(Model model) {
		model.addAttribute("colorsList", colorBean.getColorsList());
		model.addAttribute("beerCommand", new BeerCommand());
		return "formView";
	}

	@PostMapping
	public String onSubmit(BeerCommand cmd, Model model) {
		model.addAttribute("selectedColor", cmd.getColorSelected());
		model.addAttribute("beerList", expertBean.getExpert(cmd.getColorSelected()));
		return "beerView";
	}
}
