package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateursDAO {
	
	// ajoute l'utilisateur en base et set aussi l'id de l'utilisateur passé en param avec l'id nouvellement créé
	public void insertUtilisateur(Utilisateur utilisateur) throws BusinessException;
	
	public List<Utilisateur> selectAllUtilisateurs() throws BusinessException;
	
	public List<Utilisateur> selectAllUtilisateursExceptOne(int id) throws BusinessException;
	
	// retourne un Utilisateur nul si le pseudo demandé n'est pas en base
	public Utilisateur selectUtilisateurById(int id) throws BusinessException;
	
	// retourne un Utilisateur nul si le login demandé n'est pas en base
	public Utilisateur selectUtilisateurByLogin(String login) throws BusinessException;
	
	// retourne un Utilisateur nul si le pseudo demandé n'est pas en base
	public Utilisateur selectUtilisateurByPseudo(String pseudo) throws BusinessException;
	
	public void updateUtilisateur(int id, Utilisateur utilisateur) throws BusinessException;
	
	public void deleteUtilisateur(int id) throws BusinessException;
	
	// Pour vérifier si le pseudo et l'e-mail sont déjà présents en base avant de les ajouter pour création de compte ou modification de profil
	// Si on est sur de la modification, on exclut de la recherche l'id de l'utilisateur qu'on est en train de modifier
	// Si création de compte et que l'id n'existe pas encore, indiquer "0" en id
	public boolean presencePseudo(String pseudo, int id) throws BusinessException;
	
	// (voir ci-dessus)
	public boolean presenceEmail(String email, int id) throws BusinessException;
	
}
