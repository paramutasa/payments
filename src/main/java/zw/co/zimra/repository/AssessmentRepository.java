package zw.co.zimra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.zimra.model.Assessment;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    Assessment findByAssNoIgnoreCase(String assNo);
}
