package com.whoscared.amonic.repositories;

import com.whoscared.amonic.domain.utils.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {
    public List<Office> findAll ();

}
