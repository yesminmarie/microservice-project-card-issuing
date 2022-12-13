package io.github.yesminmarie.msavaliadorcredito.application;

import feign.FeignException;
import io.github.yesminmarie.msavaliadorcredito.application.exception.DadosClienteNotFoundException;
import io.github.yesminmarie.msavaliadorcredito.application.exception.ErroComunicacaoMicroservicesException;
import io.github.yesminmarie.msavaliadorcredito.domain.model.CartaoCliente;
import io.github.yesminmarie.msavaliadorcredito.domain.model.DadosCliente;
import io.github.yesminmarie.msavaliadorcredito.domain.model.SituacaoCliente;
import io.github.yesminmarie.msavaliadorcredito.infra.clients.CartoesResourceClient;
import io.github.yesminmarie.msavaliadorcredito.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;
    private final CartoesResourceClient cartoesClient;

    public SituacaoCliente obterSituacaoCliente(String cpf)
            throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCartoesByCliente(cpf);

            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();
        }catch (FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }
    }
}