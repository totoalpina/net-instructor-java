package ro.netinstructor.services;

import ro.netinstructor.models.CompanyDto;

import java.util.Optional;

public interface CompanyService {

    Optional<CompanyDto> findByCif(String cif);
    boolean registerCompany(CompanyDto companyDto);
}
