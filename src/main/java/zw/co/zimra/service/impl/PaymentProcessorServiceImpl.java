package zw.co.zimra.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zw.co.zimra.model.Payment;
import zw.co.zimra.pojo.ProcessPayment;
import zw.co.zimra.repository.PaymentRepository;
import zw.co.zimra.service.PaymentProcessorService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service
@Slf4j
public class PaymentProcessorServiceImpl implements PaymentProcessorService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment paymentProcessFlow(ProcessPayment processPayment) {

        Payment ppr = new Payment();
        if (paymentRepository.findBySerialNumberIgnoreCase(processPayment.getSerialNumber()) == null) {
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
//generated responses
            ppr.setReceiptNumber(givenUsingJava8_whenGeneratingRandomAlphanumericString_thenCorrect());
            ppr.setReceiptDate(dateFormated());
            ppr.setReceiptTime(timeFormated());
            ppr.setMessage("SUCCESS");

            paymentRepository.save(ppr);
            log.info("Successfully processed the payment");
        } else {
            log.info("The payment with Serial number : " + processPayment.getSerialNumber() + " exists");
            ppr=paymentRepository.findBySerialNumberIgnoreCase(processPayment.getSerialNumber());
            ppr.setMessage("PAYMENT EXISTS");

        }
        return ppr;

    }

    public String givenUsingJava8_whenGeneratingRandomAlphanumericString_thenCorrect() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public String dateFormated() {

        String pattern = "MMddyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        return date;
    }

    public String timeFormated() {
        String pattern = "HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        return date;
    }
}
