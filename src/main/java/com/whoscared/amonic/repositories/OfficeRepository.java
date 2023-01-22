package com.whoscared.amonic.repositories;

import com.whoscared.amonic.domain.utils.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {
    List<Office> findAll();
    Optional<Office> findById(Long id);

}
