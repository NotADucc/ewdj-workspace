package com.springBoot_list2Start;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.Student;
import service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentDetailController {

	@Autowired
    private StudentService studentService;
	
	@ModelAttribute("username")
    public String populateColors(Principal principal) {
        return principal.getName();
    }
	
	@GetMapping(value = "/{id}")
    public String show(@PathVariable("id") Integer studentId, Model model) {
       
        Student student = studentService.findById(studentId);
        if (student == null) {
			return "redirect:/students/list";
		}
        model.addAttribute("student", student);
        return "grade/detailStudent";
    }
}
