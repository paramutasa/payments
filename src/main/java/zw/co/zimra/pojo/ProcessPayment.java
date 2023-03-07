package zw.co.zimra.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author amunhanga
 */

//Request
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessPayment {

    private String serialNumber;
    private String referenceNumber;
    private String accountNumber;
    private String bPNumber;
    private String clientName;
    private String taxCode;
    private String region;
    private String paymentDate;
    private String captureTime;
    private String currency;
    private String amount;
    private String rRN;
    private String userID;

}
