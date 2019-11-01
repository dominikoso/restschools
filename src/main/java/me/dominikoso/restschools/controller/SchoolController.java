package me.dominikoso.restschools.controller;

import me.dominikoso.restschools.model.School;
import me.dominikoso.restschools.repository.SchoolRepository;
import me.dominikoso.restschools.tools.SchoolControllersTools;
import me.dominikoso.restschools.tools.SchoolFilterEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;

/**
 * School controller class - used for serving data to user in form of json
 * @author dominikoso
 * @see School
 */
@RestController
@RequestMapping(value = "/school", produces = "application/json; charset=utf-8")
public class SchoolController {

    @Autowired
    private SchoolRepository schoolRepository;

    private Map<SchoolFilterEnum, Function<String, List<School>>> finders = new HashMap<SchoolFilterEnum, Function<String, List<School>>>() {
        {
            put(SchoolFilterEnum.CITY, (String type) -> schoolRepository.findAllByCity(type));
            put(SchoolFilterEnum.POWIAT, (String type) -> schoolRepository.findAllByPowiat(type));
            put(SchoolFilterEnum.TYPE, (String type) -> schoolRepository.findAllByType(type));
            put(SchoolFilterEnum.WOJEWODZTWO, (String type) -> schoolRepository.findAllByWojewodztwo(type));
            put(SchoolFilterEnum.FULLNAME, (String type) -> schoolRepository.findAllBySchoolFullNameContaining(type));
        }
    };

    private SchoolControllersTools controllersTools = new SchoolControllersTools();

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAll(@RequestParam(value = "fields", required = false) String fields) {
        return ResponseEntity.ok(getSchools(fields));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/id/{id}")
    public ResponseEntity getById(@RequestParam(value = "fields", required = false) String fields,
                                  @PathVariable(name = "id") Long id){
        School school = schoolRepository.findBySchoolId(id);
        if (school != null){
            return ResponseEntity.ok(controllersTools.parsedSchool(school, fields));
        }else {
            return ResponseEntity.badRequest().body("School with id: "+id+" not found");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/city/{city}")
    public ResponseEntity getAllByCity(@RequestParam(value = "fields", required = false) String fields,
                                     @PathVariable(name = "city") String city) {
        return ResponseEntity.ok(getSchoolsByFilter(fields, SchoolFilterEnum.CITY, city));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/type/{type}")
    public ResponseEntity getAllByType(@RequestParam(value = "fields", required = false) String fields,
                                     @PathVariable(name = "type") String type) {
        return ResponseEntity.ok(getSchoolsByFilter(fields, SchoolFilterEnum.TYPE, type));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/wojewodztwo/{wojewodztwo}")
    public ResponseEntity getAllByWojewodztwo(@RequestParam(value = "fields", required = false) String fields,
                                       @PathVariable(name = "wojewodztwo") String wojewodztwo) {
        return ResponseEntity.ok(getSchoolsByFilter(fields, SchoolFilterEnum.WOJEWODZTWO, wojewodztwo));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/powiat/{powiat}")
    public ResponseEntity getAllByPowiat(@RequestParam(value = "fields", required = false) String fields,
                                              @PathVariable(name = "powiat") String powiat) {
        return ResponseEntity.ok(getSchoolsByFilter(fields, SchoolFilterEnum.POWIAT, powiat));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/name/{name}")
    public ResponseEntity getAllByFullName(@RequestParam(value = "fields", required = false) String fields,
                                         @PathVariable(name = "name") String name) {
        return ResponseEntity.ok(getSchoolsByFilter(fields, SchoolFilterEnum.FULLNAME, name));
    }

    private Object getSchools(String fields) {
        List<School> schools = schoolRepository.findAll();
        return controllersTools.parsedSchools(schools, fields);
    }

    private Object getSchoolsByFilter(String fields, SchoolFilterEnum filter, String value){
        List<School> schools = finders.get(filter).apply(value);
        return controllersTools.parsedSchools(schools, fields);
    }

    @PostMapping
    public ResponseEntity blockPost(){
        return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("We don't do that here");
    }

}
