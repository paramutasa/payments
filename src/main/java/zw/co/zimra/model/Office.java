package zw.co.zimra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Table(name = "office", schema = "PAYMENTS")
@Table(name = "office")
public class Office {
    @Id
    private String officeCode;
    private String officeName;
    private String province;
}
