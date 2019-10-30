package me.dominikoso.restschools.repository;

import me.dominikoso.restschools.model.School;
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
    List<School> findAll();
    /**
     * Function that fetch all entries from schools table by location.
     * @param city city name
     */
    List<School> findAllByCity(String city);
    /**
     * Function that fetch all entries from schools table by location.
     * @param type school type
     */
    List<School> findAllByType(String type);
}
