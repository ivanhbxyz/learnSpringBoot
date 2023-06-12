package com.ivanhb.contractorman;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {
    
    /*
     @RequestParam, binds the value of the query string parameter

     Realize that the implementation body relies on a View technology(in this case Thymeleaf)
     to perform the server-side rendering of the HTML
     */
    @GetMapping("/greeting")
    public String greeting(
        @RequestParam(name="name", required=false, defaultValue="World")String name, 
        Model model) {

            model.addAttribute("name", name);
            return "greeting";
    }
}
