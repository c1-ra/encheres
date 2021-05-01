package fr.eni.encheres.dal;

/**
 * Les codes disponibles sont entre 10000 et 19999
 */
public abstract class CodesResultatDAL {

	/******************* UTILISATEURS *******************/
	
	/* Echec à vérifier si les informations de connexion sont correctes */
	public static final int SELECT_UTILISATEUR_BY_LOGIN_ERREUR=10000;
	
	/* Echec à l'insertion d'un utilisateur */
	public static final int INSERT_UTILISATEUR_ERREUR=10001;

	/* Echec à la sélection de tous les utilisateurs */
	public static final int SELECT_ALL_UTILISATEURS_ERREUR=10002;
	
	/* Echec à sélectionner un utilisateur par pseudo */
	public static final int SELECT_UTILISATEUR_BY_PSEUDO_ERREUR=10003;
	
	/* Echec à sélectionner un utilisateur par id */
	public static final int SELECT_UTILISATEUR_BY_ID_ERREUR=10004;
	
	/* Echec à updater les données d'un utilisateur */
	public static final int UPDATE_UTILISATEUR_ERREUR=10005;
	
	public static final int SELECT_ALL_UTILISATEURS_EXCEPT_ONE_ERREUR=10006;
	
	public static final int DELETE_UTILISATEUR_ERREUR=10007;
	
	public static final int COUNT_PSEUDO_ERREUR=10008;
	
	public static final int COUNT_EMAIL_ERREUR=10009;
	
	/******************* ARTICLES *******************/
	
	public static final int INSERT_ARTICLE_ERREUR=10010;
	
	public static final int SELECT_EN_COURS_ERREUR=10011;
	
	public static final int SELECT_ENCHERES_UTILISATEUR_EN_COURS_ERREUR=10012;
	
	public static final int SELECT_ENCHERES_REMPORTEES_ERREUR=10013;
	
	public static final int SELECT_VENTES_UTILISATEUR_ERREUR=10014;
	
	public static final int SELECT_DETAIL_ARTICLE_ERREUR=10015;
	
	public static final int UPDATE_ARTICLE_ERREUR=10016;
	
	public static final int DELETE_ARTICLE_ERREUR=10018;
	
	/******************* ENCHERES *******************/	
	
	public static final int INSERT_ENCHERE_ERREUR=10017;
	
}