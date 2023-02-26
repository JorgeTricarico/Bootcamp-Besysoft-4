package com.besysoft.bootcampspringboot.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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

        public Personaje() {
        }
        public Personaje(Long id, String nombre, Integer edad, Double peso, String historia, List<PeliculaSerie> peliculasSeries) {
            this.id = id;
            this.nombre = nombre;
            this.edad = edad;
            this.peso = peso;
            this.historia = historia;
            this.peliculasSeries = peliculasSeries;
        }
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public String getNombre() {
            return nombre;
        }
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        public Integer getEdad() {
            return edad;
        }
        public void setEdad(Integer edad) {
            this.edad = edad;
        }
        public Double getPeso() {
            return peso;
        }
        public void setPeso(Double peso) {
            this.peso = peso;
        }
        public String getHistoria() {
            return historia;
        }
        public void setHistoria(String historia) {
            this.historia = historia;
        }
        public List<PeliculaSerie> getPeliculasSeries() {
            return peliculasSeries;
        }
        public void setPeliculasSeries(List<PeliculaSerie> peliculasSeries) {
            this.peliculasSeries = peliculasSeries;
        }
    }


