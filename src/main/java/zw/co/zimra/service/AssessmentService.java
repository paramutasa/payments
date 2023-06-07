package zw.co.zimra.service;


import zw.co.zimra.pojo.ValidateAssessment;
import zw.co.zimra.pojo.ValidateAssessmentResponse;

//@Service
public interface AssessmentService {
    ValidateAssessmentResponse checkIfAssessmentExists(ValidateAssessment validateAssessment);
}
