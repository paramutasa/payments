package zw.co.zimra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.zimra.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String> {

    Payment findBySerialNumberIgnoreCaseAndReferenceNumberIgnoreCaseAndRRNIgnoreCase(String serialNumber, String referenceNumber, String rrn);
    Payment findBySerialNumberIgnoreCaseAndReferenceNumberIgnoreCaseAndRRNIgnoreCaseAndReceiptNumberIsNotNullAndReceiptDateIsNotNullAndReceiptTimeIsNotNull(String serialNumber, String referenceNumber, String rrn);
    Payment findBySerialNumberIgnoreCaseAndReferenceNumberIgnoreCaseAndRRNIgnoreCaseAndReceiptNumberIsNullAndReceiptDateIsNullAndReceiptTimeIsNull(String serialNumber, String referenceNumber, String rrn);
}
