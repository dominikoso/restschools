package me.dominikoso.restschools.controller;

import me.dominikoso.restschools.model.School;
import me.dominikoso.restschools.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    private SchoolRepository schoolRepository;

    private List<String> endpoints = Arrays.asList("id", "city", "type", "wojewodztwo", "powiat", "name");
    private List<String> descriptions = Arrays.asList(
            "Fetch one school entry using it's id",
            "Fetch all schools entries using city location",
            "Fetch all schools entries using school type",
            "Fetch all schools entries using wojewodztwo location",
            "Fetch all schools entries using powiat location",
            "Fetch all schools entries with name containing query");

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePageLoader(Model model, HttpServletRequest request){
        model.addAttribute("endpoints", endpoints);
        model.addAttribute("fullUrl", request.getRequestURL().toString());
        model.addAttribute("desc", descriptions);

        return "home";
    }

    @RequestMapping(value = "/schools-list", method = RequestMethod.GET)
    public String graphicalInfoLoader(@PageableDefault(size = 100) Pageable pageable, Model model, HttpServletRequest request){
        Page<School> page = schoolRepository.findAll(pageable);
        model.addAttribute("page", page);

        return "schools-list";
    }

}
