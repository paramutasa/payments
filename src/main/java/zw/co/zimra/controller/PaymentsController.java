package zw.co.zimra.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import zw.co.zimra.model.Payment;
import zw.co.zimra.pojo.ProcessPayment;
import zw.co.zimra.pojo.ProcessPaymentResponse;
import zw.co.zimra.pojo.ValidateAssessment;
import zw.co.zimra.pojo.ValidateAssessmentResponse;
import zw.co.zimra.service.AssessmentService;
import zw.co.zimra.service.PaymentProcessorService;
import zw.co.zimra.service.PaymentToSwtpService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PaymentsController {

    @Autowired
    AssessmentService assessment;

    @Autowired
    PaymentProcessorService processor;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    PaymentToSwtpService paymentToSwtpService;

    @GetMapping("/validateAssessment")
    public ValidateAssessmentResponse validate (@RequestParam String assNo,@RequestParam String office,@RequestParam String year){

        ValidateAssessment validateAssessment = new ValidateAssessment();
        validateAssessment.setAssNo(assNo+"_"+year);
        validateAssessment.setOffice(office);
        validateAssessment.setYear(Long.valueOf(year));

        log.info("The validateAssessment request payload : " +validateAssessment);

        return assessment.checkIfAssessmentExists(validateAssessment);
    }

    @PostMapping("/paymentAdvice")
    public ProcessPaymentResponse process (@RequestBody ProcessPayment processPayment){

        log.info("The paymentAdvice request payload : " +processPayment);

        processPayment.setRRN(processPayment.getRRN().substring(1));

        if (checkRrn(processPayment.getRRN()).length()!=9){
            ProcessPaymentResponse response = new ProcessPaymentResponse();
            response.setMessage("put correct rrn format");
            log.info("the rrn is in wrong format : " + processPayment.getRRN());
        return response;
        }
        else {
            return processor.paymentProcessFlow(processPayment);
        }
    }
    public String checkRrn(String rrn){
        int index = rrn.indexOf('_');
        rrn = rrn.substring(0,index);
        return rrn;
    }
}
