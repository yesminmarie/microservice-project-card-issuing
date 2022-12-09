package io.github.yesminmarie.msclientes.application;

import io.github.yesminmarie.msclientes.application.representation.ClienteSaveRequest;
import io.github.yesminmarie.msclientes.domain.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
public class ClienteResource {

    private final ClienteService service;

    @GetMapping
    public String status(){
        return "ok";
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ClienteSaveRequest request){
        var cliente = request.toModel();
        service.save(cliente);
        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(cliente.getCpf())
                .toUri();
        return ResponseEntity.created(headerLocation).build();
    }

    @GetMapping(params = "cpf")
    public ResponseEntity dadosCliente(@RequestParam("cpf") String cpf){
        var cliente = service.getByCPF(cpf);
        if(cliente.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }
}
