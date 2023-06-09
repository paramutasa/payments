package zw.co.zimra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Table(name = "payment", schema = "PAYMENTS")
@Table(name = "payment")
public class Payment {
    @Id
    private String serialNumber;
    private String referenceNumber;
    private String bPNumber;
    private String clientName;
    private String accountNumber;
    private String taxCode;
    private String region;
    private String currency;
    private String amount;
    private String paymentDate;
    private String captureTime;
    private String receiptNumber;
    private String receiptDate;
    private String receiptTime;
    private String message;
    private String rRN;
    private String userID;
}
