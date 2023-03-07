package zw.co.zimra.service;

import zw.co.zimra.pojo.ProcessPayment;
import zw.co.zimra.pojo.ProcessPaymentResponse;

public interface PaymentProcessor {
    ProcessPaymentResponse paymentProcessFlow (ProcessPayment processPayment);
}
