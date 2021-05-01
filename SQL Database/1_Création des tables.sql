/* Création des tables */

CREATE TABLE UTILISATEURS (
    id				 INTEGER IDENTITY(1,1) NOT NULL,
    pseudo           VARCHAR(30) NOT NULL,
    nom              VARCHAR(30) NOT NULL,
    prenom           VARCHAR(30) NOT NULL,
    email            VARCHAR(20) NOT NULL,
    telephone	     VARCHAR(15),
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(10) NOT NULL,
    ville            VARCHAR(30) NOT NULL,
    mot_de_passe     VARCHAR(30) NOT NULL,
    credit           INTEGER DEFAULT 0 NOT NULL,
    administrateur   bit DEFAULT 0 NOT NULL
);

ALTER TABLE UTILISATEURS ADD constraint utilisateurs_pk PRIMARY KEY (id);
ALTER TABLE UTILISATEURS ADD constraint utilisateurs_pseudo_un UNIQUE (pseudo);
ALTER TABLE UTILISATEURS ADD constraint utilisateurs_email_un UNIQUE (email);

CREATE TABLE CATEGORIES (
    id   INTEGER IDENTITY(1,1) NOT NULL,
    libelle        VARCHAR(30) NOT NULL
);

ALTER TABLE CATEGORIES ADD constraint categories_pk PRIMARY KEY (id);

CREATE TABLE ENCHERES (
    id_utilisateur   INTEGER NOT NULL,
    id_article       INTEGER NOT NULL,
    date_enchere     datetime NOT NULL,
	montant_enchere  INTEGER NOT NULL
);

ALTER TABLE ENCHERES ADD constraint encheres_pk PRIMARY KEY (id_utilisateur, id_article, montant_enchere);

CREATE TABLE RETRAITS (
	id_article         INTEGER NOT NULL,
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(15) NOT NULL,
    ville            VARCHAR(30) NOT NULL
);

ALTER TABLE RETRAITS ADD constraint retraits_pk PRIMARY KEY  (id_article);

CREATE TABLE ARTICLES (
    id			                INTEGER IDENTITY(1,1) NOT NULL,
    nom			                VARCHAR(30) NOT NULL,
    description                 VARCHAR(300) NOT NULL,
	date_debut_encheres         DATE NOT NULL,
    date_fin_encheres           DATE NOT NULL,
    prix_initial                INTEGER,
    prix_vente                  INTEGER,
    id_utilisateur              INTEGER NOT NULL,
    id_categorie                INTEGER NOT NULL,
);

ALTER TABLE ARTICLES ADD constraint articles_pk PRIMARY KEY (id);

ALTER TABLE ARTICLES ADD id_etat_vente AS CAST (
	CASE
		WHEN date_debut_encheres > GETDATE() THEN 1
		WHEN date_debut_encheres < GETDATE() AND date_fin_encheres > GETDATE() THEN 2
		WHEN date_fin_encheres < GETDATE() THEN 3
	END AS INTEGER)


CREATE TABLE ETATS_VENTE (
	id			INTEGER IDENTITY(1,1) NOT NULL,
	etat		VARCHAR(8)
)

ALTER TABLE ETATS_VENTE ADD constraint etats_vente_pk PRIMARY KEY (id);

/* Ajout des FK */
ALTER TABLE ENCHERES
    ADD CONSTRAINT encheres_utilisateurs_fk FOREIGN KEY (id_utilisateur) 
		REFERENCES UTILISATEURS (id)
ON DELETE NO ACTION
    ON UPDATE no action;

ALTER TABLE ENCHERES
    ADD CONSTRAINT encheres_articles_fk FOREIGN KEY (id_article)
        REFERENCES ARTICLES (id)
ON DELETE CASCADE
    ON UPDATE no action;

ALTER TABLE RETRAITS
    ADD CONSTRAINT retraits_articles_fk FOREIGN KEY (id_article)
        REFERENCES ARTICLES (id)
ON DELETE CASCADE
    ON UPDATE no action;

ALTER TABLE ARTICLES
    ADD CONSTRAINT articles_categories_fk FOREIGN KEY (id_categorie)
        REFERENCES CATEGORIES (id)
ON DELETE NO ACTION
    ON UPDATE no action;

ALTER TABLE ARTICLES
    ADD CONSTRAINT articles_utilisateurs_fk FOREIGN KEY (id_utilisateur)
        REFERENCES UTILISATEURS (id)
ON DELETE NO ACTION
    ON UPDATE no action;
