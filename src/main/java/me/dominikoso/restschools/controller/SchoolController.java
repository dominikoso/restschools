package me.dominikoso.restschools.controller;

import me.dominikoso.restschools.model.School;
import me.dominikoso.restschools.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/school")
public class SchoolController {

    @Autowired
    private SchoolRepository schoolRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<School> getAll() {return schoolRepository.findAll();}

    @RequestMapping(method = RequestMethod.GET, value = "/city/{city}")
    public List<School> getAllByCity(@PathVariable(name = "city") String city) {return schoolRepository.findAllByCity(city);}

    @RequestMapping(method = RequestMethod.GET, value = "/type/{type}")
    public List<School> getAllByType(@PathVariable(name = "type") String type) {return schoolRepository.findAllByType(type);}
}
