package zw.co.zimra.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import zw.co.zimra.pojo.MeansOfPayment;
import zw.co.zimra.pojo.PaymentAmountDetail;
import zw.co.zimra.pojo.PaymentToSwtpRequest;
import zw.co.zimra.pojo.PaymentToSwtpResponse;
import zw.co.zimra.service.PaymentToSwtpService;

import java.util.Arrays;

@Service
@Slf4j
public class PaymentToSwtpImpl implements PaymentToSwtpService {

    @Autowired
    RestTemplate restTemplate;
    @Override
    public ResponseEntity<PaymentToSwtpResponse> sendToSwtp(PaymentToSwtpRequest paymentToSwtpRequest) {

        String url ="https://api.esw.gov.zw/accounts/api/v1/payment-gateway/payment?api-id=stanbic&api-key=F51141ABF522";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<PaymentToSwtpRequest> entity = new HttpEntity<>(paymentToSwtpRequest, headers);

        ResponseEntity<PaymentToSwtpResponse> response = restTemplate.exchange(url, HttpMethod.POST,entity,PaymentToSwtpResponse.class);

        System.out.println(response);
        return response;
    }
}
