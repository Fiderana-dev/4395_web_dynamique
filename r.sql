CREATE TABLE IF NOT EXISTS marque (
    id SERIAL PRIMARY KEY,
    libele VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS model (
    id SERIAL PRIMARY KEY,
    idMarque INTEGER,
    libele VARCHAR(100),
    FOREIGN KEY (idMarque) REFERENCES marque (id)
);

CREATE TABLE IF NOT EXISTS ordinateur (
    id SERIAL PRIMARY KEY,
    idModel INTEGER,
    processeur VARCHAR(100),
    ram INTEGER,
    diskdur INTEGER,
    FOREIGN KEY (idModel) REFERENCES model (id)
);

CREATE TABLE IF NOT EXISTS utilisateur (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) UNIQUE,
    motdepasse VARCHAR(255),
    role VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS etat (
    id SERIAL PRIMARY KEY,
    libele VARCHAR(100) UNIQUE
);

CREATE TABLE IF NOT EXISTS probleme (
    id_probleme SERIAL PRIMARY KEY,
    libele VARCHAR(100) UNIQUE
);

CREATE TABLE IF NOT EXISTS ordinateurEtat (
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

INSERT INTO marque (libele) VALUES ('Dell'), ('HP'), ('Lenovo'), ('Acer')
ON CONFLICT DO NOTHING;

INSERT INTO model (idMarque, libele) VALUES
(1, 'Optiplex'), (1, 'Prodex'), (1, 'ThinkCenter'),
(2, 'ProBook'), (2, 'Latitude'), (2, 'Procession'),
(3, 'Veriton')
ON CONFLICT DO NOTHING;

INSERT INTO utilisateur (nom, motdepasse, role) VALUES
('admin', 'admin', 'admin'), ('user', 'user', 'user')
ON CONFLICT (nom) DO NOTHING;

INSERT INTO etat (libele) VALUES ('fonctionnel'), ('disfonctionnel')
ON CONFLICT DO NOTHING;

INSERT INTO probleme (libele) VALUES
('carte mere'), ('Processeur'), ('Disque dur'), ('Ram'),
('Alimentation'), ('Carte graphique')
ON CONFLICT (libele) DO NOTHING;



