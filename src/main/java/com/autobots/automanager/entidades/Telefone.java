package com.autobots.automanager.entidades;

import jakarta.persistence.Embeddable;

@Embeddable
public class Telefone {
    
    private String ddd;
    private String numero;

    // GETTERS E SETTERS

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
