package zw.co.zimra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.zimra.pojo.ProcessPayment;
import zw.co.zimra.pojo.ProcessPaymentResponse;
import zw.co.zimra.pojo.ValidateAssessment;
import zw.co.zimra.pojo.ValidateAssessmentResponse;
import zw.co.zimra.service.Assessment;
import zw.co.zimra.service.PaymentProcessor;

@RestController
@RequiredArgsConstructor
public class PaymentsController {

    @Autowired
     Assessment assessment;

    @Autowired
    PaymentProcessor processor;

    @GetMapping("/validateAssessment")
    public ValidateAssessmentResponse validate (ValidateAssessment validateAssessment){
        return assessment.checkIfAssessmentExists(validateAssessment);
    }

    @PostMapping("/paymentAdvice")
    public ProcessPaymentResponse process (ProcessPayment processPayment){
        return processor.paymentProcessFlow(processPayment);
    }

}
