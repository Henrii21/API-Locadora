package com.locadora.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Multa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    private double valor;

    private boolean paga = false;
}
