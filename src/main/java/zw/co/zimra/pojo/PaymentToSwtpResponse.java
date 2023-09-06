package zw.co.zimra.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentToSwtpResponse {
    public String errorCode;
    public String messageCode;
    public SerialReference serialReference;

    @Override
    public String toString() {
        return "PaymentToSwtpResponse{" +
                "errorCode='" + errorCode + '\'' +
                ", messageCode='" + messageCode + '\'' +
                ", serialReference=" + serialReference +
                '}';
    }
}
