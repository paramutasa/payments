package zw.co.zimra.pojo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 *
 * @author amunhanga
 */

//Response


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SerialReference {
    public String date;
    public Date dateTime;
    public Long node;
    public Long number;
    public String office;
    public String serial;
    public Long year;

    @Override
    public String toString() {
        return "PaymentToSwtpResponse{" +
                "date='" + date + '\'' +
                ", dateTime=" + dateTime +
                ", node=" + node +
                ", number=" + number +
                ", office='" + office + '\'' +
                ", serialLetter='" + serial + '\'' +
                ", year=" + year +
                '}';
    }
}
