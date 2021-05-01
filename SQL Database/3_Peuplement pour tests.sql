/* ETATS_VENTE */
INSERT INTO ETATS_VENTE VALUES('Cr��e');
INSERT INTO ETATS_VENTE VALUES('En cours');
INSERT INTO ETATS_VENTE VALUES('Termin�e');
INSERT INTO ETATS_VENTE VALUES('Retir�e');

/* CATEGORIES */
INSERT INTO CATEGORIES VALUES('Ameublement');
INSERT INTO CATEGORIES VALUES('Informatique');
INSERT INTO CATEGORIES VALUES('Sport&Loisirs');
INSERT INTO CATEGORIES VALUES('Vetement');

/* UTILISATEURS */
INSERT INTO UTILISATEURS VALUES('pseudo1', 'nom1', 'prenom1', 'mail1@mail.fr', '0000000000', '0 rue truc', '00000', 'ville1', 'mdp1', 75, 0);
INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe) VALUES ('pseudo2', 'nom2', 'prenom2', 'mail2@mail.fr', '0000000002', '2 rue truc', '20000', 'ville2', 'mdp2');
INSERT INTO UTILISATEURS VALUES('pseudo3', 'nom3', 'prenom3', 'mail3@mail.fr', '0000030000', '3 rue truc', '03000', 'ville3', 'mdp3', 128, 0);
INSERT INTO UTILISATEURS VALUES('pseudo4', 'nom4', 'prenom4', 'mail4@mail.fr', '0000040000', '4 rue truc', '04000', 'ville4', 'mdp4', 1122, 0);

/* ARTICLES */
INSERT INTO ARTICLES VALUES('bureau', 'desc', GETDATE()-7, GETDATE()+8, 7, 0, 2, 1);
INSERT INTO ARTICLES VALUES('t�l�phone', 'desc', GETDATE()-9, GETDATE()+2, 3, 0, 2, 2);
INSERT INTO ARTICLES VALUES('tapis de sport', 'desc', GETDATE()-2, GETDATE()+10, 525, 0, 1, 3);
INSERT INTO ARTICLES VALUES('pull', 'desc', GETDATE()-3, GETDATE()+20, 1890, 0, 1, 4);
INSERT INTO ARTICLES VALUES('ordinateur', 'desc', GETDATE()-1, GETDATE()+5, 325, 0, 2, 2);
INSERT INTO ARTICLES VALUES('lampe de bureau', 'desc', GETDATE()-1, GETDATE()+5, 32, 0, 3, 1);
INSERT INTO ARTICLES VALUES('bonnet', 'desc', GETDATE()-15, GETDATE()-12, 32, 0, 3, 4);
INSERT INTO ARTICLES VALUES('lecteur mp3', 'desc', GETDATE()+21, GETDATE()+27, 325, 0, 1, 2);
INSERT INTO ARTICLES VALUES('gourde', 'desc', GETDATE()-23, GETDATE()-10, 32, 0, 3, 3);
INSERT INTO ARTICLES VALUES('v�lo', 'desc', GETDATE()-22, GETDATE()-12, 32, 0, 1, 3);
INSERT INTO ARTICLES VALUES('briques', 'desc', GETDATE()-22, GETDATE()+12, 0, 10, 3, 3);
INSERT INTO ARTICLES VALUES('commode', 'desc', GETDATE()-9, GETDATE()+2, 3, 0, 3, 1);

/* ENCHERES */
INSERT INTO ENCHERES  VALUES (1, 1, GETDATE()-5, 9);
INSERT INTO ENCHERES  VALUES (1, 2, GETDATE()-7, 49);
INSERT INTO ENCHERES  VALUES (2, 3, GETDATE()-1, 755);
INSERT INTO ENCHERES  VALUES (3, 4, GETDATE()-2, 47);
INSERT INTO ENCHERES  VALUES (2, 5, GETDATE(), 93);
INSERT INTO ENCHERES  VALUES (1, 6, GETDATE(), 97);
INSERT INTO ENCHERES  VALUES (3, 1, GETDATE()-4, 77);
INSERT INTO ENCHERES  VALUES (1, 2, GETDATE()-5, 53);
INSERT INTO ENCHERES  VALUES (1, 7, GETDATE()-14, 53);
INSERT INTO ENCHERES  VALUES (2, 7, GETDATE()-13, 55);
INSERT INTO ENCHERES  VALUES (1, 7, GETDATE()-12, 88);
INSERT INTO ENCHERES  VALUES (1, 9, GETDATE()-15, 109);
INSERT INTO ENCHERES  VALUES (2, 9, GETDATE()-12, 567);
INSERT INTO ENCHERES  VALUES (2, 10, GETDATE()-20, 875);
INSERT INTO ENCHERES  VALUES (3, 10, GETDATE()-15, 1098);

/* RETRAITS */
INSERT INTO RETRAITS VALUES (1, '7 rue de la Fontaine', 'villeDiverse', '75000');
INSERT INTO RETRAITS VALUES (2, '3 avenue Orange', 'villeR', '34982');
INSERT INTO RETRAITS VALUES (3, '8 avenue Machin', 'villeR2', '49034');
INSERT INTO RETRAITS VALUES (4, '8 rue de la Foret', 'villeR3', '23451');
INSERT INTO RETRAITS VALUES (5, '9 rue de la Foret', 'villeR3', '23451');
INSERT INTO RETRAITS VALUES (6, '10 rue des Petits Champs', 'villeR3', '23451');
INSERT INTO RETRAITS VALUES (7, '1 rue des Peupliers', 'villeR3', '23451');
INSERT INTO RETRAITS VALUES (8, '2 rue Truc', 'villeR3', '23451');
INSERT INTO RETRAITS VALUES (9, '3 place des Coquelicots', 'villeR3', '23451');
INSERT INTO RETRAITS VALUES (10, '20 boulevard Magenta', 'villeR3', '23451');
INSERT INTO RETRAITS VALUES (11, '12 rue des Bleuets', 'villeR3', '23451');
INSERT INTO RETRAITS VALUES (12, '12 rue des Bleuets', 'villeR4', '23451');