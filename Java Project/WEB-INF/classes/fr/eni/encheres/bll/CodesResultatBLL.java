package fr.eni.encheres.bll;

/**
 * Les codes disponibles sont entre 20000 et 29999
 */
public abstract class CodesResultatBLL {
	
	/****************** UTILISATEURS ******************/

	 /* Si l'utilisateur essaie de créer un compte avec un pseudo déjà présent en base */
	public static final int PSEUDO_DEJA_UTILISE = 20000;
	
	 /* Si l'utilisateur essaie de créer un compte avec un e-mail déjà présent en base */
	public static final int EMAIL_DEJA_UTILISE = 20001;
	
	/* Le champ est null, vide, n'est pas composé que de caractères alphanumériques ou dépasse les 30 caractères autorisés */	
	public static final int REGLE_PSEUDO_ERREUR = 20002;
	
	/* Le champ est null, vide, ou dépasse les 30 caractères autorisés */
	public static final int REGLE_NOM_UTILISATEUR_ERREUR = 20003;
	
	/* Le champ est null, vide, ou dépasse les 30 caractères autorisés */
	public static final int REGLE_PRENOM_ERREUR = 20004;
	
	/* Le champ est null, vide, ou dépasse les 20 caractères autorisés */
	public static final int REGLE_EMAIL_ERREUR = 20005;
	
	/* Le champ dépasse les 15 caractères autorisés */
	public static final int REGLE_TELEPHONE_ERREUR = 20006;
	
	/* Le champ est null, vide, ou dépasse les 30 caractères autorisés */
	public static final int REGLE_RUE_ERREUR = 20007;
	
	/* Le champ est null, vide, ou dépasse les 10 caractères autorisés */
	public static final int REGLE_CODE_POSTAL_ERREUR = 20008;
	
	/* Le champ est null, vide, ou dépasse les 30 caractères autorisés */
	public static final int REGLE_VILLE_ERREUR = 20009;
	
	/* Le champ est null, vide, ou dépasse les 30 caractères autorisés */
	public static final int REGLE_MOT_DE_PASSE_ERREUR = 20010;
	
	/* Les mots de passe ne concordent pas */
	public static final int MOTS_DE_PASSE_DIFFERENTS = 20018;
	
	
	/****************** ARTICLES ******************/
	
	/* Le champ est null, vide, ou dépasse les 30 caractères autorisés */
	public static final int REGLE_NOM_ARTICLE_ERREUR = 20011;
	
	/* Le champ est null, vide, ou dépasse les 300 caractères autorisés */
	public static final int REGLE_DESCRIPTION_ERREUR = 20012;
	
	/* Le champ est null ou vide ou la date est déjà passée*/
	public static final int REGLE_DATE_DEBUT_ERREUR = 20013;
	
	/* Le champ est null ou vide ou la date est déjà passée ou antérieure à la date de début */
	public static final int REGLE_DATE_FIN_ERREUR = 20014;
	
	/* Le champ est égal à 0 */
	public static final int REGLE_PRIX_INITIAL_ERREUR = 20015;
	
	/* Le champ est null ou vide */
	public static final int REGLE_CATEGORIE_ERREUR = 20016;
	
	
	/****************** ENCHERES ******************/
	
	/* L'utilisateur n'a pas assez de points pour effectuer cette enchère */
	public static final int NOMBRE_DE_POINTS_INSUFFISANT = 20017;
	
}