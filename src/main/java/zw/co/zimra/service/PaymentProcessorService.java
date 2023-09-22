package zw.co.zimra.service;

import zw.co.zimra.model.Payment;
import zw.co.zimra.pojo.ProcessPayment;
import zw.co.zimra.pojo.ProcessPaymentResponse;

public interface PaymentProcessorService {
    ProcessPaymentResponse paymentProcessFlow (ProcessPayment processPayment);
}
