package fr.eni.encheres.servlets;

/**
 * Les codes disponibles sont entre 30000 et 39999
 */
public abstract class CodesResultatServlets {
	
	/* Connexion */
	
	/* Si l'utilisateur tente de se connecter sans renseigner de login ou un login vide (que des espaces) */
	public static final int LOGIN_VIDE_ERREUR = 30000;
	
	/* Si l'utilisateur tente de se connecter sans renseigner de mot de passe ou un mot de passe vide (que des espaces) */
	public static final int MDP_VIDE_ERREUR = 30001;
	
	/* Création de compte */
	/* Si l'utilisateur n'a pas rempli un des champs obligatoires (ou n'y a mis que des espaces) */
	public static final int PSEUDO_OBLIGATOIRE = 30002;
	
	public static final int NOM_OBLIGATOIRE = 30003;
	
	public static final int PRENOM_OBLIGATOIRE = 30004;
	
	public static final int EMAIL_OBLIGATOIRE = 30005;
	
	public static final int RUE_OBLIGATOIRE = 30006;
	
	public static final int CODE_POSTAL_OBLIGATOIRE = 30007;
	
	public static final int VILLE_OBLIGATOIRE = 30008;
	
	public static final int MOT_DE_PASSE_OBLIGATOIRE = 30009;
	
	/* La confirmation du mot de passe et le premier mot de passe rentré ne concordent pas */
	public static final int MOTS_DE_PASSE_DIFFERENTS = 30010;
	
	/* Erreur général quand un champ obligatoire n'est pas renseigné */
	public static final int CHAMP_OBLIGATOIRE = 30011;

}