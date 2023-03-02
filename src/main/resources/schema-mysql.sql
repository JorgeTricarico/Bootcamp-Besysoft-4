DROP DATABASE IF EXISTS ejercicio_4;

CREATE DATABASE IF NOT EXISTS ejercicio_4;

USE ejercicio_4;

create table IF NOT EXISTS generos (
       id bigint not null auto_increment,
        nombre varchar(30) not null,
        primary key (id)
    ) ;

    create table IF NOT EXISTS peliculas_series (
       id bigint not null auto_increment,
       titulo varchar(60) not null,
       fecha_de_creacion date not null,
       calificacion integer not null,
       genero_id bigint not null,
       primary key (id)
    ) ;

    create table IF NOT EXISTS personajes (
       id bigint not null auto_increment,
        nombre varchar(30) not null,
        edad integer not null,
        peso double precision not null,
        historia varchar(255) not null,
        primary key (id)
    ) ;

    create table IF NOT EXISTS personajes_peliculas_series (
       pelicula_serie_id bigint not null,
        personaje_id bigint not null
    ) ;


    alter table generos
       add constraint unique (nombre);

    alter table peliculas_series
       add constraint
       foreign key (genero_id)
       references generos (id);

    alter table personajes_peliculas_series
       add constraint
       foreign key (personaje_id)
       references personajes (id);

    alter table personajes_peliculas_series
       add constraint
       foreign key (pelicula_serie_id)
       references peliculas_series (id);