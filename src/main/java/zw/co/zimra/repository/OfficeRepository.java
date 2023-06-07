package zw.co.zimra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.zimra.model.Office;

public interface OfficeRepository extends JpaRepository<Office, String> {
}
