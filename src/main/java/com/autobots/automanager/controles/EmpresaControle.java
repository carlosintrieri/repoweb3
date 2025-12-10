package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
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
@RequestMapping("/empresa")
@CrossOrigin(origins = "*")
public class EmpresaControle {

    @Autowired
    private EmpresaRepositorio repositorio;

    @GetMapping
    public ResponseEntity<CollectionModel<Empresa>> obterEmpresas() {
        List<Empresa> empresas = repositorio.findAll();
        for (Empresa empresa : empresas) {
            empresa.add(linkTo(methodOn(EmpresaControle.class).obterEmpresa(empresa.getId())).withSelfRel());
            empresa.add(linkTo(methodOn(EmpresaControle.class).obterEmpresas()).withRel("empresas"));
        }
        Link selfLink = linkTo(methodOn(EmpresaControle.class).obterEmpresas()).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(empresas, selfLink));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> obterEmpresa(@PathVariable Long id) {
        return repositorio.findById(id)
                .map(empresa -> {
                    empresa.add(linkTo(methodOn(EmpresaControle.class).obterEmpresa(id)).withSelfRel());
                    empresa.add(linkTo(methodOn(EmpresaControle.class).obterEmpresas()).withRel("empresas"));
                    return ResponseEntity.ok(empresa);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Empresa> cadastrarEmpresa(@RequestBody Empresa empresa) {
        empresa.setCadastro(new Date());
        Empresa salva = repositorio.save(empresa);
        salva.add(linkTo(methodOn(EmpresaControle.class).obterEmpresa(salva.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> atualizarEmpresa(@PathVariable Long id, @RequestBody Empresa empresaAtualizada) {
        return repositorio.findById(id)
                .map(empresa -> {
                    if (empresaAtualizada.getRazaoSocial() != null) {
                        empresa.setRazaoSocial(empresaAtualizada.getRazaoSocial());
                    }
                    if (empresaAtualizada.getNomeFantasia() != null) {
                        empresa.setNomeFantasia(empresaAtualizada.getNomeFantasia());
                    }
                    if (empresaAtualizada.getTelefones() != null) {
                        empresa.setTelefones(empresaAtualizada.getTelefones());
                    }
                    if (empresaAtualizada.getEndereco() != null) {
                        empresa.setEndereco(empresaAtualizada.getEndereco());
                    }
                    Empresa salva = repositorio.save(empresa);
                    salva.add(linkTo(methodOn(EmpresaControle.class).obterEmpresa(id)).withSelfRel());
                    return ResponseEntity.ok(salva);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEmpresa(@PathVariable Long id) {
        if (repositorio.existsById(id)) {
            repositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
