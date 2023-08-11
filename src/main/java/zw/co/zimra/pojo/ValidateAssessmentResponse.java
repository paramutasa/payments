
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
public class ValidateAssessmentResponse {

    private Long year;
    private String assNo;
    private String office;
    private Double amount;
    private String currency;
    private String found;

}
