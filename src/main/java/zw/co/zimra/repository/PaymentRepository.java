package zw.co.zimra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.zimra.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String> {

    Payment  findBySerialNumberIgnoreCase(String serialNumber);
    Payment findBySerialNumberIgnoreCaseAndReferenceNumberIgnoreCaseAndRRNIgnoreCase(String serialNumber, String referenceNumber, String rRN);
}
