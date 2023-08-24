package zw.co.zimra.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zw.co.zimra.model.Assessment;
import zw.co.zimra.model.Payment;
import zw.co.zimra.pojo.*;
import zw.co.zimra.repository.AssessmentRepository;
import zw.co.zimra.repository.PaymentRepository;
import zw.co.zimra.service.PaymentProcessorService;
import zw.co.zimra.service.PaymentToSwtpService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
@Slf4j
public class PaymentProcessorServiceImpl implements PaymentProcessorService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    PaymentToSwtpService paymentToSwtpService;


    @Override
    public Payment paymentProcessFlow(ProcessPayment processPayment) {

        Payment ppr = new Payment();
        if (paymentRepository.findBySerialNumberIgnoreCaseAndReferenceNumberIgnoreCaseAndRRNIgnoreCase(processPayment.getSerialNumber(), processPayment.getReferenceNumber(), processPayment.getRRN()) == null) {
            ppr.setSerialNumber(processPayment.getSerialNumber());
            ppr.setReferenceNumber(processPayment.getReferenceNumber());
            ppr.setBPNumber(processPayment.getBPNumber());
            ppr.setAccountNumber(processPayment.getAccountNumber());
            ppr.setRRN(processPayment.getRRN());
            ppr.setUserID(processPayment.getUserID());
            ppr.setTaxCode(processPayment.getTaxCode());
            ppr.setRegion(processPayment.getRegion());
            ppr.setPaymentDate(processPayment.getPaymentDate());
            ppr.setCaptureTime(processPayment.getCaptureTime());
            ppr.setCurrency(processPayment.getCurrency());
            ppr.setAmount(Double.valueOf(processPayment.getAmount()));
            ppr.setClientName(processPayment.getClientName());
            ppr.setAssNo(processPayment.getBPNumber());

            paymentRepository.save(ppr);
//generated responses

            //do the call to ZwSTP endpoint here

            Assessment assessment = assessmentRepository.findByAssNoIgnoreCase(processPayment.getBPNumber());

            PaymentToSwtpRequest paymentToSwtpRequest= new PaymentToSwtpRequest();
            paymentToSwtpRequest.setPaymentOrderId(assessment.getPaymentDocumentId());
            paymentToSwtpRequest.setBankCashierId(ppr.getUserID());

            PaymentAmountDetail paymentAmountDetail = new PaymentAmountDetail();
            paymentAmountDetail.setCollectionCurrencyCode(ppr.getCurrency());
            paymentAmountDetail.setCollectionCurrencyAmount(ppr.getAmount());
            paymentAmountDetail.setExchangeRate(1D);
            paymentAmountDetail.setTotalAssessedCurrencyCode(assessment.getCurrency());
            paymentAmountDetail.setTotalAssessedCurrencyAmount(assessment.getAmount());

            paymentToSwtpRequest.setPaymentAmountDetail(paymentAmountDetail);

            ArrayList<MeansOfPayment> meansOfPaymentList = new ArrayList<>();
            MeansOfPayment meansOfPayment = new MeansOfPayment();

            meansOfPayment.setCode("08");
            meansOfPayment.setName("ZETSS");
            meansOfPayment.setReference(ppr.getReferenceNumber());
            meansOfPayment.setBank(ppr.getRRN().substring(11));
            meansOfPayment.setPaidAmount(assessment.getAmount());
            meansOfPayment.setCollectedCurrency(ppr.getCurrency());
            meansOfPayment.setCollectedAmount(ppr.getAmount());

            meansOfPaymentList.add(meansOfPayment);

            paymentToSwtpRequest.setMeansOfPayments(meansOfPaymentList);


            //get the response from ZwSTP endpoint here
            PaymentToSwtpResponse paymentToSwtpResponse= paymentToSwtpService.sendToSwtp(paymentToSwtpRequest).getBody();

            if(paymentToSwtpResponse == null){
                ppr.setMessage("NOT COMPLETE");
                paymentRepository.save(ppr);
                log.info("Partially processed the payment");
            }else {
                ppr.setReceiptNumber(receiptNumberProcessed(paymentToSwtpResponse));
                ppr.setReceiptDate(dateFormatedEdit(paymentToSwtpResponse.getDateTime()));
                ppr.setReceiptTime(timeFormatedEdit(paymentToSwtpResponse.getDateTime()));
                ppr.setMessage("SUCCESS");

                paymentRepository.save(ppr);
                log.info("Successfully processed the payment");
            }


        } else {
            log.info("The payment with Serial number : " + processPayment.getSerialNumber()+" ,Reference Number : "+processPayment.getReferenceNumber()+" ,RRN : "+processPayment.getRRN() + " exists");
            ppr=paymentRepository.findBySerialNumberIgnoreCaseAndReferenceNumberIgnoreCaseAndRRNIgnoreCase(processPayment.getSerialNumber(), processPayment.getReferenceNumber(), processPayment.getRRN());
            ppr.setMessage("PAYMENT EXISTS");

        }
        return ppr;

    }

    public String receiptNumberProcessed(PaymentToSwtpResponse paymentToSwtpResponse){
        String receiptNumberAsText = paymentToSwtpResponse.getYear()+"-"+
                paymentToSwtpResponse.getOffice()+"-"+
                paymentToSwtpResponse.getSerialLetter()+"-"+
                paymentToSwtpResponse.getNumber();
        return receiptNumberAsText;
    }

    public String dateFormatedEdit(Date dateFromResponse)  {

        String pattern = "ddMMyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(dateFromResponse);
        return date;
    }

    public String timeFormatedEdit(Date dateFromResponse) {
        String pattern = "HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(dateFromResponse);
        return date;
    }
}
