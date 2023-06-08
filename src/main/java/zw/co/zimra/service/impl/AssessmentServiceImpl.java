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

        if (assessment != null
                && validateAssessment.getAssNo().equalsIgnoreCase(assessment.getAssNo())
                && validateAssessment.getYear().equalsIgnoreCase(assessment.getYear())
                && validateAssessment.getOffice().equalsIgnoreCase(assessment.getOffice())
        ) {
            var.setYear(assessment.getYear());
            var.setAssNo(assessment.getAssNo());
            var.setOffice(assessment.getOffice());
            var.setFound("IS PRESENT");
            var.setCurrency(assessment.getCurrency());
            var.setAmount(assessment.getAmount());

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
