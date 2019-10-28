package me.dominikoso.restschools.repository;

import me.dominikoso.restschools.model.School;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolRepository extends CrudRepository<School, Long> {
    List<School> findAll();
    List<School> findAllByCity(String city);
}
