/* Trigger afin de supprimer les infos liées à un utilisateur quand celui-ci supprime son compte (impossible avec ON DELETE CASCADE car erreur sql de possibilité de suppression en cascade) */
/* A laisser dans un fichier sql séparé (doit être la première exécution de son fichier), à exécuter après le fichier 1 de création des tables*/
CREATE OR ALTER TRIGGER trg_delete_cascade_utilisateurs_articles_fk
	ON UTILISATEURS INSTEAD OF DELETE
AS
BEGIN
	DELETE FROM ARTICLES WHERE id_utilisateur IN (SELECT id FROM DELETED);
	DELETE FROM ENCHERES WHERE id_utilisateur IN (SELECT id FROM DELETED);
	DELETE FROM UTILISATEURS WHERE id IN (SELECT id FROM DELETED);
END;