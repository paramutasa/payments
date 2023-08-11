package zw.co.zimra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ZWSTP_PAYMENT_REGISTRATION")
public class Assessment {

    @Id
    @Column(name="INSTANCE_ID")
    private Long instanceId;
    @Column(name="ASSESSED_CURRENCY_AMOUNT")
    private Double amount;
    @Column(name="ASSESSED_CURRENCY_CODE")
    private String currency;
    @Column(name="ASS_NO")
    private String assNo;
    @Column(name="PAYMENT_DOCUMENT_ID")
    private String paymentDocumentId;
    @Column(name="PAYMENT_DOCUMENT_NUMBER")
    private Long paymentDocumentNumber;
    @Column(name="PAYMENT_DOCUMENT_OFFICE")
    private String office;
    @Column(name="PAYMENT_DOCUMENT_SERIAL")
    private String paymentDocumentSerial;
    @Column(name="PAYMENT_DOCUMENT_YEAR")
    private Long year;
    @Column(name="PAYMENT_TYPE")
    private String paymentType;
    @Column(name="PAYMENT_REGISTRATION_NUMBER")
    private String paymentRegistrationNumber;

    @Override
    public String toString() {
        return "Assessment{" +
                "instanceId=" + instanceId +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", assNo='" + assNo + '\'' +
                ", paymentDocumentId='" + paymentDocumentId + '\'' +
                ", paymentDocumentNumber=" + paymentDocumentNumber +
                ", office='" + office + '\'' +
                ", paymentDocumentSerial='" + paymentDocumentSerial + '\'' +
                ", year=" + year +
                ", paymentType='" + paymentType + '\'' +
                ", paymentRegistrationNumber='" + paymentRegistrationNumber + '\'' +
                '}';
    }
}
