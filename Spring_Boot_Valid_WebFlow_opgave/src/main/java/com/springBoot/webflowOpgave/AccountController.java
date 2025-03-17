package com.springBoot.webflowOpgave;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.Account;

@Controller
@RequestMapping("/account")
public class AccountController {

       
    @GetMapping
    public String showHomePage(Model model) {
        model.addAttribute("account", new Account());
        return "accountForm";
    }
        
    @PostMapping
    public String onSubmit(
            Account account, Model model) {
        account.simpleExample();
        return "exampleView";
    }
  
}