package com.whoscared.amonic.services;

import com.whoscared.amonic.domain.utils.Office;
import com.whoscared.amonic.repositories.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeService {
    private final OfficeRepository officeRepository;

    @Autowired
    public OfficeService(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    public List<Office> findAll() {
        return officeRepository.findAll();
    }

}
