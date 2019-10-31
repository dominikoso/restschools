package me.dominikoso.restschools.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * School model class. Used for creating objects that represents `schools` table entity.
 * @author dominikoso
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="schools")
public class School{

    /**
     * Id number of school in database. This field is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schoolId;
    /**
     * Wojewodztwo - polish province
     */
    private String wojewodztwo;
    /**
     * Powiat - polish town district
     */
    private String powiat;
    /**
     * City where school is located
     */
    private String city;
    /**
     * Type of school ex. Technikum
     */
    private String type;
    /**
     * Full name of school
     */
    private String schoolFullName;
    /**
     * Street where school is located
     */
    private String street;
    private String houseNumber;
    /**
     * School postcode
     */
    private String postcode;
    private String postCity;
    /**
     * Phone number to school secretariat
     */
    private String phone;
    /**
     * School website
     */
    private String www;

}
