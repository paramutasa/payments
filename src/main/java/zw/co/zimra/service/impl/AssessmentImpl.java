package zw.co.zimra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zw.co.zimra.pojo.ValidateAssessment;
import zw.co.zimra.pojo.ValidateAssessmentResponse;
import zw.co.zimra.service.Assessment;


@Service
@RequiredArgsConstructor
public class AssessmentImpl implements Assessment {


    @Override
    public ValidateAssessmentResponse checkIfAssessmentExists(ValidateAssessment validateAssessment) {

        ValidateAssessmentResponse var = new ValidateAssessmentResponse();

        var.setYear(validateAssessment.getYear());
        var.setAssNo(validateAssessment.getAssNo());
        var.setOffice(validateAssessment.getOffice());
        //hard coding values
        var.setFound("IS PRESENT");
        var.setCurrency("USD $");
        var.setAmount("200");



        return  var;
    }
}
