package com.whoscared.amonic.repositories;

import com.whoscared.amonic.domain.person.Person;
import com.whoscared.amonic.domain.utils.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Optional<Activity> findById (Long id);
    List<Activity> findByPerson (Person person);
}
