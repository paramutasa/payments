package zw.co.zimra.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

/**
 *
 * @author amunhanga
 */

//Response


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentToSwtpRequest {
    public String paymentOrderId;
    public PaymentAmountDetail paymentAmountDetail;
    public ArrayList<MeansOfPayment> meansOfPayments;
    public String bankCashierId;

    @Override
    public String toString() {
        return "PaymentToSwtpRequest{" +
                "paymentOrderId='" + paymentOrderId + '\'' +
                ", paymentAmountDetail=" + paymentAmountDetail +
                ", meansOfPayments=" + meansOfPayments +
                ", bankCashierId='" + bankCashierId + '\'' +
                '}';
    }
}
