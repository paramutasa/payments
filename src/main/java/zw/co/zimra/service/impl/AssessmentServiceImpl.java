package zw.co.zimra.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zw.co.zimra.model.Assessment;
import zw.co.zimra.pojo.ValidateAssessment;
import zw.co.zimra.pojo.ValidateAssessmentResponse;
import zw.co.zimra.repository.AssessmentRepository;
import zw.co.zimra.service.AssessmentService;


@Service
@RequiredArgsConstructor
@Slf4j
public class AssessmentServiceImpl implements AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Override
    public ValidateAssessmentResponse checkIfAssessmentExists(ValidateAssessment validateAssessment) {

        ValidateAssessmentResponse var = new ValidateAssessmentResponse();


        Assessment assessment = assessmentRepository.findByAssNoIgnoreCase(validateAssessment.getAssNo());
        System.out.println(assessment);
        if (assessment != null
                && validateAssessment.getAssNo().equalsIgnoreCase(assessment.getAssNo())
                && 0 == validateAssessment.getYear().compareTo(assessment.getYear())// shows the years are equal
                && validateAssessment.getOffice().equalsIgnoreCase(assessment.getOffice())
        ) {
            var.setYear(assessment.getYear().toString());
            var.setAssNo(assessment.getAssNo());
            var.setOffice(assessment.getOffice());
            var.setFound("IS PRESENT");
            var.setCurrency(assessment.getCurrency());
            var.setAmount(assessment.getAmount().toString());

            log.info("Assessment found");
        } else {
            var.setYear("");
            var.setAssNo("");
            var.setOffice("");
            var.setFound("NOT PRESENT");
            var.setCurrency("");
            var.setAmount("");

            log.info("Assessment not found");
        }

        return var;
    }
}
