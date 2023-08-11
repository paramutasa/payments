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
public class MeansOfPayment{
    public String code;
    public String name;
    public String reference;
    public String bank;
    public Double paidAmount;
    public String collectedCurrency;
    public Double collectedAmount;

    @Override
    public String toString() {
        return "{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", reference='" + reference + '\'' +
                ", bank='" + bank + '\'' +
                ", paidAmount=" + paidAmount +
                ", collectedCurrency='" + collectedCurrency + '\'' +
                ", collectedAmount=" + collectedAmount +
                '}';
    }
}
