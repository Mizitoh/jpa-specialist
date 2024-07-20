package com.algaworks.model;

import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@Embeddable
public class EnderecoEntregaPedido {

    private String cep;

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private String cidade;

    private String estado;
}
