package me.dominikoso.restschools.tools;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.dominikoso.restschools.model.School;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResponseControllersTools {

    public Object parsedSchool(School school, String fields) {
        if (fields == null || fields.isEmpty()) {
            return school;
        } else {
            return getSchoolJson(school, Arrays.asList(fields.split(",")));
        }
    }

    public Object parsedSchools(List<School> schools, String excludedFields) {
        if (excludedFields == null || excludedFields.isEmpty()) {
            return schools;
        } else {
            return getSchoolsJson(schools, Arrays.asList(excludedFields.split(",")));
        }
    }

    private String getSchoolJson(School school, List<String> fields) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(gson.toJson(school)).getAsJsonObject();

        List<String> excludedFields = getExcludedFields(fields);
        for (String field : excludedFields) {
            jsonObject.remove(field);
        }
        return jsonObject.toString();
    }

    private String getSchoolsJson(List<School> schools, List<String> fields) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(gson.toJson(schools)).getAsJsonArray();
        JsonArray resultArray = new JsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonArray.get(i);

            List<String> excludedFields = getExcludedFields(fields);
            for (String excludedField : excludedFields) {
                jsonObject.remove(excludedField);
            }
            resultArray.add(jsonObject);
        }
        return resultArray.toString();
    }


    private List<String> getExcludedFields(List<String> fields) {
        List<String> excludedFields = new ArrayList<>(Arrays.asList(SCHOOL_FIELDS));
        excludedFields.removeAll(fields);
        return excludedFields;
    }

    private static final String[] SCHOOL_FIELDS = new String[]{
    "schoolId",
    "wojewodztwo",
    "powiat",
    "city",
    "type",
    "schoolFullName",
    "street",
    "houseNumber",
    "postcode",
    "postCity",
    "phone",
    "www"
    };
}
