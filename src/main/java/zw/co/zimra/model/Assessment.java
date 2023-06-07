package zw.co.zimra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assessment", schema = "PAYMENTS")
public class Assessment {

    @Id
    private String assNo;
    private String year;
    private String office;
    private String amount;
    private String currency;
}
