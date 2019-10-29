package me.dominikoso.restschools.controller;

import me.dominikoso.restschools.model.School;
import me.dominikoso.restschools.repository.SchoolRepository;
import me.dominikoso.restschools.tools.ResponseControllersTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/school", produces = "application/json; charset=utf-8")
public class SchoolController {

    @Autowired
    private SchoolRepository schoolRepository;

    private ResponseControllersTools controllersTools = new ResponseControllersTools();

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAll(@RequestParam(value = "fields", required = false) String fields) {
        return ResponseEntity.ok(getSchools(fields));
    }

    private Object getSchools(String fields) {
        List<School> schools = schoolRepository.findAll();
        return controllersTools.parsedSchools(schools, fields);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/city/{city}")
    public ResponseEntity getAllByCity(@RequestParam(value = "fields", required = false) String fields,
                                     @PathVariable(name = "city") String city) {
        return ResponseEntity.ok(getSchoolsByCity(fields, city));
    }

    private Object getSchoolsByCity(String fields, String city) {
        List<School> schools = schoolRepository.findAllByCity(city);
        return controllersTools.parsedSchools(schools, fields);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/type/{type}")
    public ResponseEntity getAllByType(@RequestParam(value = "fields", required = false) String fields,
                                     @PathVariable(name = "type") String type) {
        return ResponseEntity.ok(getSchoolsByTypes(fields, type));
    }

    private Object getSchoolsByTypes(String fields, String type) {
        List<School> schools = schoolRepository.findAllByType(type);
        return controllersTools.parsedSchools(schools, fields);
    }


    @PostMapping
    public ResponseEntity blockPost(){
        return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("We don't do that here");
    }

}
