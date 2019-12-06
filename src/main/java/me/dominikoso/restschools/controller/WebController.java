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

@Controller
public class WebController {

    @Autowired
    private SchoolRepository schoolRepository;

    @RequestMapping(value = "schools-list", method = RequestMethod.GET)
    public String graphicalInfoLoader(@PageableDefault(size = 100) Pageable pageable, Model model){
        Page<School> page = schoolRepository.findAll(pageable);
        model.addAttribute("page", page);

        return "schools-list";
    }

}
