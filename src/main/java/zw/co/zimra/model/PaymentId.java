package zw.co.zimra.model;

import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PaymentId implements Serializable {
    private String serialNumber;
    private String referenceNumber;
    private String rRN;
}
