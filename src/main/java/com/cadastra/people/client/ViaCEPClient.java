package com.cadastra.people.client;

import com.cadastra.people.client.dto.AddressRequestClientDTO;

public interface ViaCEPClient {
    public AddressRequestClientDTO buscarEndereco(String cep);
}
