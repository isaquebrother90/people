package com.cadastra.people.service;

import com.cadastra.people.client.Impl.ViaCEPClientImpl;
import com.cadastra.people.client.dto.AddressRequestClientDTO;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    ViaCEPClientImpl viaCEPClient;

    public AddressRequestClientDTO buscarEndereco(String cep) {
        return viaCEPClient.buscarEndereco(cep);
    }
}
