package me.dominikoso.restschools.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="schools")
public class School{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schoolId;
    private String wojewodztwo;
    private String powiat;
    private String city;
    private String type;
    private String schoolFullName;
    private String street;
    private String houseNumber;
    private String postcode;
    private String postCity;
    private String phone;
    private String www;

}
