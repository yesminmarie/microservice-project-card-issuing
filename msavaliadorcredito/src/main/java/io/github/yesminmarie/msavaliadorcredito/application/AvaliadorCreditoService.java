package io.github.yesminmarie.msavaliadorcredito.application;

import io.github.yesminmarie.msavaliadorcredito.domain.model.CartaoCliente;
import io.github.yesminmarie.msavaliadorcredito.domain.model.DadosCliente;
import io.github.yesminmarie.msavaliadorcredito.domain.model.SituacaoCliente;
import io.github.yesminmarie.msavaliadorcredito.infra.clients.CartoesResourceClient;
import io.github.yesminmarie.msavaliadorcredito.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;
    private final CartoesResourceClient cartoesClient;

    public SituacaoCliente obterSituacaoCliente(String cpf){
        ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);
        ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCartoesByCliente(cpf);

        return SituacaoCliente
                .builder()
                .cliente(dadosClienteResponse.getBody())
                .cartoes(cartoesResponse.getBody())
                .build();
    }
}
