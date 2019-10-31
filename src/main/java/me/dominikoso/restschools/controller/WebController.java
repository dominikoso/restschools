package me.dominikoso.restschools.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
public class WebController {

    private List<String> endpoints = Arrays.asList("id", "city", "type", "wojewodztwo", "powiat", "name");

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePageLoader(Model model, HttpServletRequest request){
        model.addAttribute("endpoints", endpoints);
        model.addAttribute("fullUrl", request.getRequestURL().toString());

        return "home";
    }

}
