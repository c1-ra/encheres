/* Trigger afin de supprimer les infos li�es � un utilisateur quand celui-ci supprime son compte (impossible avec ON DELETE CASCADE car erreur sql de possibilit� de suppression en cascade) */
/* A laisser dans un fichier sql s�par� (doit �tre la premi�re ex�cution de son fichier), � ex�cuter apr�s le fichier 1 de cr�ation des tables*/
CREATE OR ALTER TRIGGER trg_delete_cascade_utilisateurs_articles_fk
	ON UTILISATEURS INSTEAD OF DELETE
AS
BEGIN
	DELETE FROM ARTICLES WHERE id_utilisateur IN (SELECT id FROM DELETED);
	DELETE FROM ENCHERES WHERE id_utilisateur IN (SELECT id FROM DELETED);
	DELETE FROM UTILISATEURS WHERE id IN (SELECT id FROM DELETED);
END;