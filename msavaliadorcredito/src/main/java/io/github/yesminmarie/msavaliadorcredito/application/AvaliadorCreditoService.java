package io.github.yesminmarie.msavaliadorcredito.application;

import io.github.yesminmarie.msavaliadorcredito.domain.model.DadosCliente;
import io.github.yesminmarie.msavaliadorcredito.domain.model.SituacaoCliente;
import io.github.yesminmarie.msavaliadorcredito.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;

    public SituacaoCliente obterSituacaoCliente(String cpf){
        ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);

        return SituacaoCliente
                .builder()
                .cliente(dadosClienteResponse.getBody())
                .build();
    }
}
