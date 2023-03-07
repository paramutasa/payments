package zw.co.zimra.service;


import org.springframework.stereotype.Service;
import zw.co.zimra.pojo.ValidateAssessment;
import zw.co.zimra.pojo.ValidateAssessmentResponse;

//@Service
public interface Assessment {
    ValidateAssessmentResponse checkIfAssessmentExists(ValidateAssessment validateAssessment);
}
