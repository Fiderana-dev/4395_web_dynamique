CREATE TABLE marque (
    id SERIAL PRIMARY KEY,
    libele VARCHAR(100)
);

CREATE TABLE model (
    id SERIAL PRIMARY KEY,
    idMarque INTEGER,
    libele VARCHAR(100),
    FOREIGN KEY (idMarque) REFERENCES marque (id)
);

CREATE TABLE ordinateur (
    id SERIAL PRIMARY KEY,
    idModel INTEGER,
    processeur VARCHAR(100),
    ram INTEGER,
    diskdur INTEGER,
    FOREIGN KEY (idModel) REFERENCES model (id)
);


CREATE TABLE utilisateur (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100),
    motdepasse VARCHAR(255),
    role VARCHAR(100)
);

CREATE TABLE etat (
    id SERIAL PRIMARY KEY,
    libele VARCHAR(100)
);


CREATE table probleme(
    id_probleme SERIAL PRIMARY KEY,
    libele VARCHAR(100)
);

CREATE TABLE ordinateurEtat (
    id SERIAL PRIMARY KEY,
    idPc INTEGER,
    idEtat INTEGER,
    id_probleme INTEGER,
    date DATE,
    observation VARCHAR(100),
    FOREIGN KEY (id_probleme) REFERENCES probleme (id_probleme),
    FOREIGN KEY (idPc) REFERENCES ordinateur (id),
    FOREIGN KEY (idEtat) REFERENCES etat (id)
);

INSERT INTO
    marque (libele)
VALUES ('Dell'),
    ('HP'),
    ('Lenovo'),
    ('Dell'),
    ('Acer');

INSERT INTO
    model (idMarque, libele)
VALUES (1, 'Optiplex'),
    (1, 'prodex'),
    (1, 'thinkCenter'),
    (2, 'probook'),
    (2, 'latitude'),
    (2, 'procesion'),
    (3, 'Veriton');

INSERT INTO ordinateur (idModel , processeur , ram , diskdur)
VALUES (1 , 7 , 16 , 512),
       (2 , 7 , 8 , 256),
       (3 , 5 , 8 , 512),
       (4 , 9 , 16 , 512),
       (5 , 5 , 16 , 512),
       (6 , 7 , 16 , 256),
       (6 , 7 , 8 , 256);
INSERT INTO
    utilisateur (nom, motdepasse, role)
VALUES ('admin', 'admin', 'admin'),
    ('user', 'user', 'user');

INSERT INTO etat (libele) VALUES ('fonctionnel'), ('disfonctionnel');



