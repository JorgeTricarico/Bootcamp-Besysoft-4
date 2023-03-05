package com.besysoft.bootcampspringboot.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

    @Data
    @EqualsAndHashCode(exclude = "id")
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Table(name = "personajes")
    public class Personaje implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ID")
        private Long id;

        @Column(length = 30, name = "NOMBRE", nullable = false)
        private String nombre;

        @Column(name = "EDAD", nullable = false)
        private Integer edad;

        @Column(name = "PESO", nullable = false)
        private Double peso;

        @Column(name = "HISTORIA", nullable = false)
        private String historia;

        @JsonIgnore
        @ManyToMany(fetch = FetchType.LAZY, mappedBy = "personajesAsociados")
        private List<PeliculaSerie> peliculasSeries;

    }


