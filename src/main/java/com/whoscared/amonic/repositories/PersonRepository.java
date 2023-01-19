package com.whoscared.amonic.repositories;

import com.whoscared.amonic.domain.person.Person;
import com.whoscared.amonic.domain.utils.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Override
    Optional<Person> findById(Long aLong);
    Optional<Person> findByEmail (String email);

    List<Person> findAll();
    List<Person> findAllByOffice (Office office);
}
