
package zw.co.zimra.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author amunhanga
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidateAssessment {

    private Long year;
    private String assNo;
    private String office;

    @Override
    public String toString() {
        return "{" +
                "year='" + year + '\'' +
                ", assNo='" + assNo + '\'' +
                ", office='" + office + '\'' +
                '}';
    }
}
