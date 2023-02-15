USE ejercicio_4;

INSERT INTO generos(ID, NOMBRE)
	VALUES (1, 'Drama');

INSERT INTO generos(ID, NOMBRE)
	VALUES (2, 'Aventura');

INSERT INTO generos(ID, NOMBRE)
	VALUES (3, 'Accion');

INSERT INTO generos(ID, NOMBRE)
	VALUES (4, 'Terror');

--INSERT INTO personajes(ID, NOMBRE, EDAD, PESO, HISTORIA)
--    VALUES (10001,'Jesica', 28, 70.0, 'Jesica es una chica que vive sola en la casa del pueblo que sus padres le dejaron.');

INSERT INTO personajes(NOMBRE, EDAD, PESO, HISTORIA)
    VALUES
        ('Jesica', 28, 70.0, 'Jesica es una chica que vive sola en la casa del pueblo que sus padres le dejaron.'),
        ('Oracio', 26, 82.4, 'Oracio es un chico timido que es el cartero del pueblo.'),
        ("Harry Potter", 10, 43.3, "Harry es un niño hurfano que fue creado por sus tios que lo desprecian por sus habilidades magicas"),
        ("Ron Haswich", 10, 45.6, "Ron es un niño torpe y despistado, hijo de padres magos que lo aman"),
        ("Hermione Jean Granger", 10, 39.9, "Hermione es una niña hija de muggles dentistas, sin embargo ella es maga y asiste ala escuela de magia"),
        ("La Roca", 45, 102.8, "Un tipo rudo que no obedece las reglas, su vida fue siempre muy dura y no le teme al peligro"),
        ("Hernesto Palacio", 40, 78.7, "Un hombre comun que viene ed una familia de clase media. Nadacido en Mexico y siempre se mete en problemas"),
        ("Keyti", 35, 68.5, "Una chica rebelde que no obedece las reglas, nacida en Miami de familia, es mecanica de niña"),
        ("Lisandra", 16, 46.7, "Lisandra es una esclava carismatica y hermosa, hija de padres esclavos rebeldes"),
        ("Lisandro", 18, 58.7, "Lisandro es una esclavo rudo y hermoso, hijo de padres esclavos rebeldes"),
        ("Hernesto Hernandez", 50, 86.6, "Hernesto es un esclavista viudo  y cruel con sus esclavos"),
        ("Isaias Norting", 48, 83.1, "Isaias es un esclavista hermoso y bueno con sus esclavos"),
        ("Lisa", 13, 38.2, "Lisa es un huerfana retraida y timida poseida por un ser maligno"),
        ("Julio", 39, 89.7, "Julio es un hombre solitario que le gusta ver sufrir a las personas");


--INSERT INTO peliculas_series(ID , TITULO, FECHA_DE_CREACION, CALIFICACION, GENERO_ID)
--    VALUES (101, "El Temblor 1", "2018-02-27", 3, 1 );

INSERT INTO peliculas_series(TITULO, FECHA_DE_CREACION, CALIFICACION, GENERO_ID)
    VALUES
        ("El Temblor 1", "2018-02-27", 3, 1 ),
        ("El Temblor 2", "2021-03-17", 2, 1),
        --("Harry Potter 1", "2003-03-17", 4, 2),
        ("Harry Potter 1", "2003-03-17", 4, 2),
        ("Harry Potter 2", "2005-03-17", 3, 2),
        ("Harry Potter 3", "2007-03-17", 4, 2),
        ("Harry Potter 4", "2009-03-17", 4, 2),
        ("Harry Potter 5", "2012-03-17", 5, 2),
        ("Rapido y Furioso 1", "1999-03-17", 5, 3),
        ("Rapido y Furioso 2", "2003-06-19", 3,3),
        ("Rapido y Furioso 3", "2008-01-07", 3, 3),
        ("Rapido y Furioso 4", "2009-03-17", 2,3),
        ("Rapido y Furioso 30", "2015-03-17", 1, 3),
        ("La Esclava", "2015-09-25", 2, 1),
        ("La Huerfana 1", "2003-08-01", 4, 4),
        ("La Huerfana 2", "2005-07-09", 5, 4),
        ("La Huerfana 3", "2010-02-17", 3, 4),
        ("La Huerfana 4", "2013-07-29", 4, 4),
        ("El Juego del Miedo 1", "2001-04-15", 5, 4),
        ("El Juego del Miedo 2", "2008-08-17", 4, 4),
        ("El Juego del Miedo 3", "2014-09-17", 3, 4),
        ("El Juego del Miedo 4", "2018-11-17", 5, 4);

    INSERT INTO personajes_peliculas_series(PERSONAJE_ID, PELICULA_SERIE_ID)
    	VALUES
    	    (1, 1),(1, 2),
    	    (2, 1),(2, 2),

    	    (3, 3),(3, 4),(3, 5),(3, 6), (3, 7),
    	    (4, 3),(4, 4),(4, 5),(4, 6), (4, 7),
    	    (5, 3),(5, 4),(5, 5),(5, 6), (5, 7),

            (6, 8),(6, 9),(6, 10),(6, 11), (6, 12),
            (7, 8),(7, 9),(7, 10),(7, 11), (7, 12),
            (8, 8),(8, 9),(8, 10),(8, 11), (8, 12),

            (9,13),
            (10,13),
            (11,13),
            (12,13),

            (13,14),(13,15),(13,16),(13,17),

            (14,18),(14,19),(14,19),(14,19);
