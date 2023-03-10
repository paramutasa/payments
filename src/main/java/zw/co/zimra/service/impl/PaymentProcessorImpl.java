package zw.co.zimra.service.impl;

import org.springframework.stereotype.Service;
import zw.co.zimra.pojo.ProcessPayment;
import zw.co.zimra.pojo.ProcessPaymentResponse;
import zw.co.zimra.service.PaymentProcessor;

@Service
public class PaymentProcessorImpl implements PaymentProcessor {
    @Override
    public ProcessPaymentResponse paymentProcessFlow(ProcessPayment processPayment) {

        ProcessPaymentResponse ppr = new ProcessPaymentResponse();
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
        ppr.setAmount(processPayment.getAmount());
        ppr.setClientName(processPayment.getClientName());
        //hard coding value
        ppr.setReceiptNumber("ZIMRA0000011111122222");
        ppr.setCustomsReceiptDate(" Customs reciept date");
        ppr.setReceiptTime("Reciept time");
        ppr.setType("TYPE");
        ppr.setID(" ID********");
        ppr.setMessage("message");
        ppr.setNumber("Number");
        ppr.setLogNumber("LOg Number");
        ppr.setLogMessageNumber(" log msg number");
        ppr.setCustomsMessage("Customs msg");

        return ppr;
    }
}
