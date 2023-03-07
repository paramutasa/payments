package zw.co.zimra.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author amunhanga
 */

//Response


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessPaymentResponse {

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
    private String customsReceiptNumber;
    private String receiptDate;
    private String customsReceiptDate;
    private String receiptTime;
    private String type;
    private String iD;
    private String number;
    private String message;
    private String logNumber;
    private String logMessageNumber;
    private String messageV1;
    private String messageV2;
    private String messageV3;
    private String messageV4;
    private String customsMessage;
    private String rRN;
    private String userID;

}
