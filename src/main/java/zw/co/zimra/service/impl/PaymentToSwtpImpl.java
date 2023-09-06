package zw.co.zimra.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import zw.co.zimra.pojo.PaymentToSwtpRequest;
import zw.co.zimra.pojo.PaymentToSwtpResponse;
import zw.co.zimra.pojo.SerialReference;
import zw.co.zimra.service.PaymentToSwtpService;

import java.util.Arrays;

@Service
@Slf4j
public class PaymentToSwtpImpl implements PaymentToSwtpService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private Environment env;
    @Override
    public ResponseEntity<PaymentToSwtpResponse> sendToSwtp(PaymentToSwtpRequest paymentToSwtpRequest) {

        String url = env.getProperty("swtp.payment.endpoint");
        String headerName ="swtp.headerName";
        String headerValue ="swtp.headerValue";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add(headerName, headerValue);
        HttpEntity<PaymentToSwtpRequest> entity = new HttpEntity<>(paymentToSwtpRequest, headers);

        ResponseEntity<PaymentToSwtpResponse> response = restTemplate.exchange(url, HttpMethod.POST,entity, PaymentToSwtpResponse.class);

        System.out.println(response);
        return response;
    }
}
