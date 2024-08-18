package com.cadastra.people.service;

import com.cadastra.people.client.AddressRequestClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AddressService {
    private final RestTemplate restTemplate;

    public AddressService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AddressRequestClient buscarEndereco(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        return restTemplate.getForObject(url, AddressRequestClient.class);
    }
}
