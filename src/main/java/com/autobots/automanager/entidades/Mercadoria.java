package com.autobots.automanager.entidades;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Entity
public class Mercadoria extends RepresentationModel<Mercadoria> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date cadastro;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private Double valor;
    
    @Column(nullable = false)
    private Long quantidade;
    
    @Column(length = 1000)
    private String descricao;

    // GETTERS E SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCadastro() {
        return cadastro;
    }

    public void setCadastro(Date cadastro) {
        this.cadastro = cadastro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
