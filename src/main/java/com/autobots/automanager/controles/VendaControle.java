package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.VendaRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/venda")
@CrossOrigin(origins = "*")
public class VendaControle {

    @Autowired
    private VendaRepositorio repositorio;
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private VeiculoRepositorio veiculoRepositorio;

    @GetMapping
    public ResponseEntity<CollectionModel<Venda>> obterVendas() {
        List<Venda> vendas = repositorio.findAll();
        for (Venda venda : vendas) {
            venda.add(linkTo(methodOn(VendaControle.class).obterVenda(venda.getId())).withSelfRel());
        }
        Link selfLink = linkTo(methodOn(VendaControle.class).obterVendas()).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(vendas, selfLink));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> obterVenda(@PathVariable Long id) {
        return repositorio.findById(id)
                .map(venda -> {
                    venda.add(linkTo(methodOn(VendaControle.class).obterVenda(id)).withSelfRel());
                    return ResponseEntity.ok(venda);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<?> cadastrarVenda(@RequestBody Venda venda) {
        // Validar se cliente existe (se fornecido)
        if (venda.getCliente() != null && venda.getCliente().getId() != null) {
            Long clienteId = venda.getCliente().getId();
            if (!usuarioRepositorio.existsById(clienteId)) {
                return ResponseEntity.badRequest()
                    .body("Erro: Cliente com id " + clienteId + " não existe. Cadastre o cliente primeiro.");
            }
        }
        
        // Validar se funcionário existe (se fornecido)
        if (venda.getFuncionario() != null && venda.getFuncionario().getId() != null) {
            Long funcionarioId = venda.getFuncionario().getId();
            if (!usuarioRepositorio.existsById(funcionarioId)) {
                return ResponseEntity.badRequest()
                    .body("Erro: Funcionário com id " + funcionarioId + " não existe. Cadastre o funcionário primeiro.");
            }
        }
        
        // Validar se veículo existe (se fornecido)
        if (venda.getVeiculo() != null && venda.getVeiculo().getId() != null) {
            Long veiculoId = venda.getVeiculo().getId();
            if (!veiculoRepositorio.existsById(veiculoId)) {
                return ResponseEntity.badRequest()
                    .body("Erro: Veículo com id " + veiculoId + " não existe. Cadastre o veículo primeiro.");
            }
        }
        
        // O @PrePersist da entidade Venda já seta a data
        Venda salva = repositorio.save(venda);
        salva.add(linkTo(methodOn(VendaControle.class).obterVenda(salva.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarVenda(@PathVariable Long id, @RequestBody Venda vendaAtualizada) {
        return repositorio.findById(id)
                .map(venda -> {
                    if (vendaAtualizada.getIdentificacao() != null) {
                        venda.setIdentificacao(vendaAtualizada.getIdentificacao());
                    }
                    
                    if (vendaAtualizada.getCliente() != null) {
                        if (vendaAtualizada.getCliente().getId() != null) {
                            Long clienteId = vendaAtualizada.getCliente().getId();
                            if (!usuarioRepositorio.existsById(clienteId)) {
                                return ResponseEntity.badRequest()
                                    .body("Erro: Cliente com id " + clienteId + " não existe.");
                            }
                        }
                        venda.setCliente(vendaAtualizada.getCliente());
                    }
                    
                    if (vendaAtualizada.getFuncionario() != null) {
                        if (vendaAtualizada.getFuncionario().getId() != null) {
                            Long funcionarioId = vendaAtualizada.getFuncionario().getId();
                            if (!usuarioRepositorio.existsById(funcionarioId)) {
                                return ResponseEntity.badRequest()
                                    .body("Erro: Funcionário com id " + funcionarioId + " não existe.");
                            }
                        }
                        venda.setFuncionario(vendaAtualizada.getFuncionario());
                    }
                    
                    if (vendaAtualizada.getVeiculo() != null) {
                        if (vendaAtualizada.getVeiculo().getId() != null) {
                            Long veiculoId = vendaAtualizada.getVeiculo().getId();
                            if (!veiculoRepositorio.existsById(veiculoId)) {
                                return ResponseEntity.badRequest()
                                    .body("Erro: Veículo com id " + veiculoId + " não existe.");
                            }
                        }
                        venda.setVeiculo(vendaAtualizada.getVeiculo());
                    }
                    
                    if (vendaAtualizada.getMercadorias() != null) {
                        venda.setMercadorias(vendaAtualizada.getMercadorias());
                    }
                    if (vendaAtualizada.getServicos() != null) {
                        venda.setServicos(vendaAtualizada.getServicos());
                    }
                    
                    Venda salva = repositorio.save(venda);
                    salva.add(linkTo(methodOn(VendaControle.class).obterVenda(id)).withSelfRel());
                    return ResponseEntity.ok(salva);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirVenda(@PathVariable Long id) {
        if (repositorio.existsById(id)) {
            repositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
