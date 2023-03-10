package zw.co.zimra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public ValidateAssessmentResponse validate (@RequestParam String assNo,@RequestParam String office,@RequestParam String year){

        ValidateAssessment validateAssessment = new ValidateAssessment();
        validateAssessment.setAssNo(assNo);
        validateAssessment.setOffice(office);
        validateAssessment.setYear(year);

        return assessment.checkIfAssessmentExists(validateAssessment);
    }

    @PostMapping("/paymentAdvice")
    public ProcessPaymentResponse process ( @RequestBody ProcessPayment processPayment){
        return processor.paymentProcessFlow(processPayment);
    }

}
