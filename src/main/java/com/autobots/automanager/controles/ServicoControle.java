package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.repositorios.ServicoRepositorio;
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
@RequestMapping("/servico")
@CrossOrigin(origins = "*")
public class ServicoControle {

    @Autowired
    private ServicoRepositorio repositorio;

    @GetMapping
    public ResponseEntity<CollectionModel<Servico>> obterServicos() {
        List<Servico> servicos = repositorio.findAll();
        for (Servico servico : servicos) {
            servico.add(linkTo(methodOn(ServicoControle.class).obterServico(servico.getId())).withSelfRel());
        }
        Link selfLink = linkTo(methodOn(ServicoControle.class).obterServicos()).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(servicos, selfLink));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> obterServico(@PathVariable Long id) {
        return repositorio.findById(id)
                .map(servico -> {
                    servico.add(linkTo(methodOn(ServicoControle.class).obterServico(id)).withSelfRel());
                    return ResponseEntity.ok(servico);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Servico> cadastrarServico(@RequestBody Servico servico) {
        servico.setCadastro(new Date());
        Servico salvo = repositorio.save(servico);
        salvo.add(linkTo(methodOn(ServicoControle.class).obterServico(salvo.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizarServico(@PathVariable Long id, @RequestBody Servico servicoAtualizado) {
        return repositorio.findById(id)
                .map(servico -> {
                    if (servicoAtualizado.getNome() != null) {
                        servico.setNome(servicoAtualizado.getNome());
                    }
                    if (servicoAtualizado.getValor() != null) {
                        servico.setValor(servicoAtualizado.getValor());
                    }
                    if (servicoAtualizado.getDescricao() != null) {
                        servico.setDescricao(servicoAtualizado.getDescricao());
                    }
                    Servico salvo = repositorio.save(servico);
                    salvo.add(linkTo(methodOn(ServicoControle.class).obterServico(id)).withSelfRel());
                    return ResponseEntity.ok(salvo);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirServico(@PathVariable Long id) {
        if (repositorio.existsById(id)) {
            repositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
