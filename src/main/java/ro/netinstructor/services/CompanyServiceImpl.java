package ro.netinstructor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.netinstructor.entities.Company;
import ro.netinstructor.models.CompanyDto;
import ro.netinstructor.repositries.CompanyRepository;

import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService{

    @Autowired
    private CompanyRepository companyRepository;

    public Optional<CompanyDto> findByCif(String cif) {
        return companyRepository.findByCif(cif).map(company -> new CompanyDto(company.getName(),
                company.getAddress(), company.getCif()));
    }

    public boolean registerCompany(CompanyDto companyDto) {
        if (verifyCompany(companyDto.getCif())) {
            Company company = new Company(companyDto.getName(), companyDto.getAddress(), companyDto.getCif());
            companyRepository.save(company);
            return true;
        } else {
            return false;
        }
    }

    private boolean verifyCompany(String companyId) {
        Company company = companyRepository.findByCif(companyId)
                .orElseGet(() -> new Company("n/a", "n/a", "n/a"));

        return "n/a".equals(company.getCif());
    }

}
