package me.dominikoso.restschools.controller;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.dominikoso.restschools.model.School;
import me.dominikoso.restschools.repository.SchoolRepository;
import me.dominikoso.restschools.tools.SchoolControllersTools;
import me.dominikoso.restschools.tools.SchoolFilterEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

@OpenAPIDefinition(info =
@Info(
        title = "RestSchools API",
        version = "0.9.2",
        description = "API for processing polish schools data",
        contact = @Contact(url = "https://github.com/dominikoso", name = "dominikoso", email = "mcdominikoso@gmail.com")
)
)
@RestController
@RequestMapping(value = "/api/schools", produces = "application/json; charset=utf-8")
@Tag(name = "SchoolController", description = "Controller that handles data based on School Entity")
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

    @Operation(
            summary = "Get the list of school",
            description = "Get the list of Schools paginated",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Entity representation of School Class Object. Using GET request you can access `page` and `size` param"
                    + "for data manipulation. In swagger you will only see first 1000 elements",
                    content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = School.class)
                            )
                    )
            }
    )
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAll(@RequestParam(value = "fields", required = false) String fields,
    @PageableDefault(page = 0, size = 1000)  @Parameter(hidden = true) Pageable pageable) {
        return ResponseEntity.ok(getSchools(fields, pageable));
    }

    @Operation(
            summary = "Return one school based on id",
            description = "Return one School entity based on `id` in database",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Entity representation of one requested School Class Object",
                        content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = School.class)
                            )
                    ),
                    @ApiResponse(
                        responseCode = "404",
                        description =  "Unable to find School with requested id"
                    )

            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/id/{id}")
    public ResponseEntity getById(@RequestParam(value = "fields", required = false) String fields,
                                  @PathVariable(name = "id") Long id){
        School school = schoolRepository.findBySchoolId(id);
        if (school != null){
            return ResponseEntity.ok(controllersTools.parsedSchool(school, fields));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("School with id: "+id+" not found");
        }
    }

    @Operation(
            summary = "Get the list of school by city",
            description = "Get the list of Schools in selected city",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "List of all schools in selected city",
                    content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = School.class)
                            )
                    )
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/city/{city}")
    public ResponseEntity getAllByCity(@RequestParam(value = "fields", required = false) String fields,
                                     @PathVariable(name = "city") String city) {
        return ResponseEntity.ok(getSchoolsByFilter(fields, SchoolFilterEnum.CITY, city));
    }

    @Operation(
            summary = "Get the list of school by type",
            description = "Get the list of Schools by selected type. ex. `Przedszkole`, `Technikum` etc.",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "List of all schools in selected city",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = School.class)
                    )
            )
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/type/{type}")
    public ResponseEntity getAllByType(@RequestParam(value = "fields", required = false) String fields,
                                     @PathVariable(name = "type") String type) {
        return ResponseEntity.ok(getSchoolsByFilter(fields, SchoolFilterEnum.TYPE, type));
    }

    @Operation(
            summary = "Get the list of school by province",
            description = "Get the list of Schools in selected Wojewodztwo (polish province). ex. `WOJ. ZACHODNIOPOMORSKIE`",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "List of all schools in selected province",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = School.class)
                    )
            )
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/wojewodztwo/{wojewodztwo}")
    public ResponseEntity getAllByWojewodztwo(@RequestParam(value = "fields", required = false) String fields,
                                       @PathVariable(name = "wojewodztwo") String wojewodztwo) {
        return ResponseEntity.ok(getSchoolsByFilter(fields, SchoolFilterEnum.WOJEWODZTWO, wojewodztwo));
    }

    @Operation(
            summary = "Get the list of school by district",
            description = "Get the list of Schools in selected Powiat (polish district). ex. `Powiat Jaworski`",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "List of all schools in selected district",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = School.class)
                    )
            )
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/powiat/{powiat}")
    public ResponseEntity getAllByPowiat(@RequestParam(value = "fields", required = false) String fields,
                                              @PathVariable(name = "powiat") String powiat) {
        return ResponseEntity.ok(getSchoolsByFilter(fields, SchoolFilterEnum.POWIAT, powiat));
    }

    @Operation(
            summary = "Get the list of school by similar name",
            description = "Get the list of Schools with name like query",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "List of all schools with specified name",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = School.class)
                    )
            )
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/name/{name}")
    public ResponseEntity getAllByFullName(@RequestParam(value = "fields", required = false) String fields,
                                         @PathVariable(name = "name") String name) {
        return ResponseEntity.ok(getSchoolsByFilter(fields, SchoolFilterEnum.FULLNAME, name));
    }

    private Object getSchools(String fields, Pageable pageable) {
        List<School> schools = schoolRepository.findAll(pageable).getContent();
        return controllersTools.parsedSchools(schools, fields);
    }

    private Object getSchoolsByFilter(String fields, SchoolFilterEnum filter, String value){
        List<School> schools = finders.get(filter).apply(value);
        return controllersTools.parsedSchools(schools, fields);
    }

    @PostMapping
    @Hidden
    public ResponseEntity blockPost(){
        return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("We don't do that here");
    }

}
