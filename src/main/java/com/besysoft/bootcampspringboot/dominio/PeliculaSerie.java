package com.besysoft.bootcampspringboot.dominio;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import static com.besysoft.bootcampspringboot.utilidades.Fechas.formatear;

@Data
@EqualsAndHashCode(exclude = "id")
@NoArgsConstructor
@Entity
@Table(name = "peliculas_series")
public class PeliculaSerie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(length = 60, name = "TITULO", nullable = false, unique = true)
    private String titulo;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "FECHA_DE_CREACION", nullable = false)
    private LocalDate fechaDeCreacion;

    @Column( name = "CALIFICACION", nullable = false)
    private Integer calificacion;

    @ManyToOne
    @JoinColumn(name = "GENERO_ID", nullable = false)
    private Genero genero;

    //@JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "PERSONAJES_PELICULAS_SERIES",
            joinColumns = @JoinColumn(name = "PELICULA_SERIE_ID"),
            inverseJoinColumns = @JoinColumn(name = "PERSONAJE_ID")
    )
    private List<Personaje> personajesAsociados;


    public PeliculaSerie(Long id, String titulo, String fechaDeCreacion, Integer calificacion, Genero genero, List<Personaje> personajesAsociados) {

        this.id = id;
        this.titulo = titulo;
        this.fechaDeCreacion = formatear(fechaDeCreacion);
        this.calificacion = calificacion;
        this.genero = genero;
        this.personajesAsociados = personajesAsociados;
    }
}
