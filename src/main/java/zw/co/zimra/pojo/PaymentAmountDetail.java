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
public class PaymentAmountDetail{
    public String collectionCurrencyCode;
    public Double collectionCurrencyAmount;
    public Double exchangeRate;
    public String totalAssessedCurrencyCode;
    public Double totalAssessedCurrencyAmount;

    @Override
    public String toString() {
        return "{" +
                "collectionCurrencyCode='" + collectionCurrencyCode + '\'' +
                ", collectionCurrencyAmount=" + collectionCurrencyAmount +
                ", exchangeRate=" + exchangeRate +
                ", totalAssessedCurrencyCode='" + totalAssessedCurrencyCode + '\'' +
                ", totalAssessedCurrencyAmount=" + totalAssessedCurrencyAmount +
                '}';
    }
}
