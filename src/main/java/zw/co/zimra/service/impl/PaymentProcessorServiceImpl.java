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
    public ProcessPaymentResponse paymentProcessFlow(ProcessPayment processPayment) {

        Payment ppr = new Payment();
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


        if (paymentRepository.findBySerialNumberIgnoreCaseAndReferenceNumberIgnoreCaseAndRRNIgnoreCase(processPayment.getSerialNumber(), processPayment.getReferenceNumber(), processPayment.getRRN()) == null) {

        paymentRepository.save(ppr);

//generated responses

            //do the call to ZwSTP endpoint here

            Assessment assessment = assessmentRepository.findByAssNoIgnoreCase(processPayment.getBPNumber());

            PaymentToSwtpRequest paymentToSwtpRequest = new PaymentToSwtpRequest();
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
            PaymentToSwtpResponse paymentToSwtpResponse = paymentToSwtpService.sendToSwtp(paymentToSwtpRequest).getBody();

            if (paymentToSwtpResponse == null) {
                //this can happen if the Swtp did not return anything
                ppr.setMessage("NOT COMPLETE");
                paymentRepository.save(ppr);
                log.info("Partially processed the payment");

            } else if (paymentToSwtpResponse.getErrorCode().equalsIgnoreCase("E0001")) {
                //Payment receipted successfully


                ppr.setReceiptNumber(receiptNumberProcessed(paymentToSwtpResponse.getSerialReference()));
                ppr.setReceiptDate(dateFormatedEdit(paymentToSwtpResponse.getSerialReference().getDateTime()));
                ppr.setReceiptTime(timeFormatedEdit(paymentToSwtpResponse.getSerialReference().getDateTime()));
                ppr.setMessage("SUCCESS");

                paymentRepository.save(ppr);
                log.info("Successfully processed the payment");

            } else if (paymentToSwtpResponse.getErrorCode().equalsIgnoreCase("E0002")) {
                //Assessment was not found

                ppr.setMessage("ASSESSMENT NOT FOUND");
                log.info(paymentToSwtpResponse.getMessageCode());

            } else if (paymentToSwtpResponse.getErrorCode().equalsIgnoreCase("E0003")) {
                //Payment has already been made

                ppr.setReceiptNumber(receiptNumberProcessed(paymentToSwtpResponse.getSerialReference()));
                ppr.setReceiptDate(dateFormatedEdit(paymentToSwtpResponse.getSerialReference().getDateTime()));
                ppr.setReceiptTime(timeFormatedEdit(paymentToSwtpResponse.getSerialReference().getDateTime()));
                ppr.setMessage("SUCCESS");
                paymentRepository.save(ppr);
                ppr.setMessage("PAYMENT EXISTS");
                log.info(paymentToSwtpResponse.getMessageCode());

            } else if (paymentToSwtpResponse.getErrorCode().equalsIgnoreCase("E0004")) {
                //Any other occurred

                ppr.setMessage("AN ERROR OCCURRED");
                log.info(paymentToSwtpResponse.getMessageCode());

            }


        } else if (paymentRepository.findBySerialNumberIgnoreCaseAndReferenceNumberIgnoreCaseAndRRNIgnoreCaseAndReceiptNumberIsNullAndReceiptDateIsNullAndReceiptTimeIsNull(processPayment.getSerialNumber(), processPayment.getReferenceNumber(), processPayment.getRRN()) != null) {
            //payment notification table has receipt_number, receipt_date, receipt_time fields as null

            Assessment assessment = assessmentRepository.findByAssNoIgnoreCase(processPayment.getBPNumber());

            PaymentToSwtpRequest paymentToSwtpRequest = new PaymentToSwtpRequest();
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
            meansOfPayment.setBank(processPayment.getRRN().substring(11));
            meansOfPayment.setPaidAmount(assessment.getAmount());
            meansOfPayment.setCollectedCurrency(ppr.getCurrency());
            meansOfPayment.setCollectedAmount(ppr.getAmount());

            meansOfPaymentList.add(meansOfPayment);

            paymentToSwtpRequest.setMeansOfPayments(meansOfPaymentList);


            //get the response from ZwSTP endpoint here
            PaymentToSwtpResponse paymentToSwtpResponse = paymentToSwtpService.sendToSwtp(paymentToSwtpRequest).getBody();

            if (paymentToSwtpResponse == null) {
                //this can happen if the Swtp did not return anything
                ppr.setMessage("NOT COMPLETE");
                paymentRepository.save(ppr);
                log.info("Partially processed the payment");

            } else if (paymentToSwtpResponse.getErrorCode().equalsIgnoreCase("E0001")) {
                //Payment receipted successfully

                ppr.setReceiptNumber(receiptNumberProcessed(paymentToSwtpResponse.getSerialReference()));
                ppr.setReceiptDate(dateFormatedEdit(paymentToSwtpResponse.getSerialReference().getDateTime()));
                ppr.setReceiptTime(timeFormatedEdit(paymentToSwtpResponse.getSerialReference().getDateTime()));
                ppr.setMessage("SUCCESS");

                paymentRepository.save(ppr);
                log.info("Successfully processed the payment");

            } else if (paymentToSwtpResponse.getErrorCode().equalsIgnoreCase("E0002")) {
                //Assessment was not found

                ppr.setMessage("ASSESSMENT NOT FOUND");
                log.info(paymentToSwtpResponse.getMessageCode());

            } else if (paymentToSwtpResponse.getErrorCode().equalsIgnoreCase("E0003")) {
                //Payment has already been made

                ppr.setReceiptNumber(receiptNumberProcessed(paymentToSwtpResponse.getSerialReference()));
                ppr.setReceiptDate(dateFormatedEdit(paymentToSwtpResponse.getSerialReference().getDateTime()));
                ppr.setReceiptTime(timeFormatedEdit(paymentToSwtpResponse.getSerialReference().getDateTime()));
                ppr.setMessage("SUCCESS");
                paymentRepository.save(ppr);
                ppr.setMessage("PAYMENT EXISTS");
                log.info(paymentToSwtpResponse.getMessageCode());

            } else if (paymentToSwtpResponse.getErrorCode().equalsIgnoreCase("E0004")) {
                //Any other occurred

                ppr.setMessage("AN ERROR OCCURRED");
                log.info(paymentToSwtpResponse.getMessageCode());

            }

            //the fields have to be updated

        } else if (paymentRepository.findBySerialNumberIgnoreCaseAndReferenceNumberIgnoreCaseAndRRNIgnoreCaseAndReceiptNumberIsNotNullAndReceiptDateIsNotNullAndReceiptTimeIsNotNull(processPayment.getSerialNumber(), processPayment.getReferenceNumber(), processPayment.getRRN()) != null) {
            //payment exists and every field in the payment notification table is populated
            log.info("The payment with Serial number : " + processPayment.getSerialNumber() + " ,Reference Number : " + processPayment.getReferenceNumber() + " ,RRN : " + processPayment.getRRN() + " exists");
            ppr = paymentRepository.findBySerialNumberIgnoreCaseAndReferenceNumberIgnoreCaseAndRRNIgnoreCaseAndReceiptNumberIsNotNullAndReceiptDateIsNotNullAndReceiptTimeIsNotNull(processPayment.getSerialNumber(), processPayment.getReferenceNumber(), processPayment.getRRN());
            ppr.setMessage("PAYMENT EXISTS");

        }
        ProcessPaymentResponse response = new ProcessPaymentResponse();

        response.setSerialNumber(ppr.getSerialNumber());
        response.setReferenceNumber(ppr.getReferenceNumber());
        response.setRRN(ppr.getRRN());
        response.setBPNumber(ppr.getBPNumber());
        response.setClientName(ppr.getClientName());
        response.setAccountNumber(ppr.getAccountNumber());
        response.setTaxCode(ppr.getTaxCode());
        response.setRegion(ppr.getRegion());
        response.setCurrency(ppr.getCurrency());
        response.setAmount(ppr.getAmount().toString());
        response.setPaymentDate(ppr.getPaymentDate());
        response.setCaptureTime(ppr.getCaptureTime());
        response.setReceiptNumber(ppr.getReceiptNumber());
        response.setReceiptDate(ppr.getReceiptDate());
        response.setReceiptTime(ppr.getReceiptTime());
        response.setMessage(ppr.getMessage());
        response.setUserID(ppr.getUserID());

//        response.setCustomsReceiptNumber("");
//        response.setCustomsReceiptDate("");
//        response.setType("");
//        response.setID("");
//        response.setNumber("");
//        response.setLogNumber("");
//        response.setLogNumber("");
//        response.setMessageV1("");
//        response.setMessageV2("");
//        response.setMessageV3("");
//        response.setMessageV4("");
//        response.setCustomsMessage("");


        log.info(response.toString());
        return response;

    }

    public String receiptNumberProcessed(SerialReference serialReference){

        return serialReference.getYear()+"-"+
                serialReference.getOffice()+"-"+
                serialReference.getSerial()+"-"+
                serialReference.getNumber();
    }

    public String dateFormatedEdit(Date dateFromResponse)  {

        String pattern = "ddMMyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(dateFromResponse);
    }

    public String timeFormatedEdit(Date dateFromResponse) {
        String pattern = "HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(dateFromResponse);
    }
}
