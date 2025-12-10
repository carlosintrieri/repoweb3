package com.autobots.automanager.entidades;

import jakarta.persistence.Embeddable;

@Embeddable
public class Email {

    private String endereco;

    // GETTERS E SETTERS

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
