package com.cadastra.people.client.Impl;

import com.cadastra.people.client.ViaCEPClient;
import com.cadastra.people.client.dto.AddressRequestClientDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Slf4j
public class ViaCEPClientImpl implements ViaCEPClient {
    private final RestTemplate restTemplate;

    @Autowired
    private ViaCEPClientImpl viaCEPClient;

    @Override
    public AddressRequestClientDTO buscarEndereco(String cep) {
        //TODO implementar tratamento de exceções
            log.info("Iniciando busca pelo cep: {}", cep);
            AddressRequestClientDTO requestClientDTO = viaCEPClient.buscarEndereco(cep);
            return requestClientDTO;
    }
}
