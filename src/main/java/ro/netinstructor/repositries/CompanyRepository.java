package ro.netinstructor.repositries;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.netinstructor.entities.Company;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByCif(String cif);
}
