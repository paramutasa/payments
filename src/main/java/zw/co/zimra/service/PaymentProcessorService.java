package zw.co.zimra.service;

import zw.co.zimra.model.Payment;
import zw.co.zimra.pojo.ProcessPayment;

public interface PaymentProcessorService {
    Payment paymentProcessFlow (ProcessPayment processPayment);
}
