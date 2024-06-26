package zw.co.zimra.service;

import org.springframework.http.ResponseEntity;
import zw.co.zimra.pojo.PaymentToSwtpRequest;
import zw.co.zimra.pojo.PaymentToSwtpResponse;

public interface PaymentToSwtpService {

    ResponseEntity<PaymentToSwtpResponse> sendToSwtp(PaymentToSwtpRequest paymentToSwtpRequest);
}
