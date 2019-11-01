package me.dominikoso.restschools.repository;

import me.dominikoso.restschools.model.School;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * School repository class. Used for fetching data from database.
 * @author dominikoso
 * @see School
 */
@Repository
public interface SchoolRepository extends CrudRepository<School, Long> {
    /**
     * Function that fetch all entries from schools table.
     */
    @Cacheable("all")
    List<School> findAll();
    /**
     * Function that fetch one entry from schools table by id.
     * @param id database id
     */
    @Cacheable("id")
    School findBySchoolId(Long id);
    /**
     * Function that fetch all entries from schools table by location.
     * @param city city name
     */
    @Cacheable("city")
    List<School> findAllByCity(String city);
    /**
     * Function that fetch all entries from schools table by school type.
     * @param type school type
     */
    @Cacheable("type")
    List<School> findAllByType(String type);
    /**
     * Function that fetch all entries from schools table by location.
     * @param wojewodztwo wojewodztwo name
     */
    @Cacheable("wojewodztwo")
    List<School> findAllByWojewodztwo(String wojewodztwo);
    /**
     * Function that fetch all entries from schools table by location.
     * @param powiat powiat name
     */
    @Cacheable("powiat")
    List<School> findAllByPowiat(String powiat);
    /**
     * Function that fetch all entries from schools table by name likability.
     * @param name part of whole name of school
     */
    @Cacheable("name")
    List<School> findAllBySchoolFullNameContaining(String name);
}
