package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class IncentiveService {
    @Autowired
    private RestTemplate restTemplate;
    private final String url = "http://localhost:8080/incentive";

    public int fetchIncentive(Transaction tx){
        Incentive response = restTemplate.postForObject(url,tx, Incentive.class);
        return response.getAmount();
    }
}
