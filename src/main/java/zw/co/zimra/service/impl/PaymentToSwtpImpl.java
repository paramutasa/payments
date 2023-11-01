package zw.co.zimra.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import zw.co.zimra.pojo.PaymentToSwtpRequest;
import zw.co.zimra.pojo.PaymentToSwtpResponse;
import zw.co.zimra.service.PaymentToSwtpService;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
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

        try {
            restTemplate = testRestClient();
            ResponseEntity<PaymentToSwtpResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, PaymentToSwtpResponse.class);

//        log.info(String.format("%s-%s-%s",
//                response.getBody().getSerialReference().getOffice(),
//                response.getBody().getSerialReference().getSerial(),
//                response.getBody().getSerialReference().getNode() ));
            System.out.println(response);
            return response;
        }catch (Exception r){
            r.printStackTrace();
        }
        return  null;
    }

    public RestTemplate testRestClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;

    }

}
