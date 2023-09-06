package zw.co.zimra.service;

import org.springframework.http.ResponseEntity;
import zw.co.zimra.pojo.PaymentToSwtpRequest;
import zw.co.zimra.pojo.PaymentToSwtpResponse;
import zw.co.zimra.pojo.SerialReference;

public interface PaymentToSwtpService {

    ResponseEntity<PaymentToSwtpResponse> sendToSwtp(PaymentToSwtpRequest paymentToSwtpRequest);
}
