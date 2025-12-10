package com.autobots.automanager.entidades;

import com.autobots.automanager.enums.TipoVeiculo;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

@Entity
public class Veiculo extends RepresentationModel<Veiculo> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private TipoVeiculo tipo;
    
    @Column(nullable = false)
    private String modelo;
    
    @Column(nullable = false, unique = true)
    private String placa;
    
    @ManyToOne
    @JoinColumn(name = "proprietario_id")
    private Usuario proprietario;

    // GETTERS E SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoVeiculo getTipo() {
        return tipo;
    }

    public void setTipo(TipoVeiculo tipo) {
        this.tipo = tipo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Usuario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Usuario proprietario) {
        this.proprietario = proprietario;
    }
}
