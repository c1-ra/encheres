package fr.eni.encheres.bll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticlesDAO;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.EncheresDAO;
import fr.eni.encheres.dal.UtilisateursDAO;
import fr.eni.encheres.servlets.CodesResultatServlets;

public class EncheresManager {
	private UtilisateursDAO utilisateursDAO;
	private ArticlesDAO articlesDAO;
	private EncheresDAO encheresDAO;
	
	public EncheresManager() {
		this.utilisateursDAO = DAOFactory.getUtilisateursDAO();
		this.articlesDAO = DAOFactory.getArticlesDAO();
		this.encheresDAO = DAOFactory.getEncheresDAO();		
	}

		
	
	/************************ UTILISATEURS ***********************/
	
	public void ajouterUtilisateur(Utilisateur utilisateur, String confMotDePasse) throws BusinessException {
		BusinessException businessException = new BusinessException();
		validerPseudoUnique(utilisateur.getPseudo(), 0, businessException);
		validerEmailUnique(utilisateur.getEmail(), 0, businessException);
		validerChampsObligatoires(utilisateur, businessException);
		validerMotsDePasse(utilisateur, confMotDePasse, businessException);
		if (!businessException.hasErreurs()) {
			utilisateursDAO.insertUtilisateur(utilisateur);
		} else {
			throw businessException;
		}
	}
	
	public List<Utilisateur> selectionnerUtilisateurs() throws BusinessException {
		List<Utilisateur> listeUtilisateurs = utilisateursDAO.selectAllUtilisateurs();
		return listeUtilisateurs;
	}
	
	public Utilisateur selectionnerUtilisateurById(int id) throws BusinessException {
		Utilisateur utilisateur = utilisateursDAO.selectUtilisateurById(id);
		return utilisateur;
	}
	
	// sélectionner l'utilisateur par pseudo ou adresse mail
	public Utilisateur connexion(String login) throws BusinessException {
		Utilisateur utilisateur = utilisateursDAO.selectUtilisateurByLogin(login);
		return utilisateur;
	}
	
	public Utilisateur selectionnerUtilisateurByPseudo(String pseudo) throws BusinessException {
		Utilisateur utilisateur = utilisateursDAO.selectUtilisateurByPseudo(pseudo);
		return utilisateur;
	}
	
	public void modifierUtilisateur(int id, Utilisateur utilisateur, String confMotDePasse) throws BusinessException {
		BusinessException businessException = new BusinessException();
		
		validerPseudoUnique(utilisateur.getPseudo(), id, businessException);
		validerEmailUnique(utilisateur.getEmail(), id, businessException);
		validerChampsObligatoires(utilisateur, businessException);
		validerMotsDePasse(utilisateur, confMotDePasse, businessException);
		if (!businessException.hasErreurs()) {
			utilisateursDAO.updateUtilisateur(id, utilisateur);
		} else {
			throw businessException;
		}
	}
	
	public void supprimerUtilisateur(int id) throws BusinessException {
		utilisateursDAO.deleteUtilisateur(id);
	}
	
	private void validerPseudoUnique(String pseudo, int id, BusinessException businessException) throws BusinessException {
		if(utilisateursDAO.presencePseudo(pseudo, id)) {
				businessException.ajouterErreur(CodesResultatBLL.PSEUDO_DEJA_UTILISE);
		}
	}
	
	private void validerEmailUnique(String email, int id, BusinessException businessException) throws BusinessException {
		if(utilisateursDAO.presencePseudo(email, id)) {
			businessException.ajouterErreur(CodesResultatBLL.EMAIL_DEJA_UTILISE);
		}
	}
	
	
	
	// Méthodes privées
	
	private void validerChampsObligatoires(Utilisateur utilisateur, BusinessException businessException) {
		if (utilisateur.getPseudo() == null || utilisateur.getPseudo().trim().equals("") || utilisateur.getPseudo().trim().length() > 30 || !utilisateur.getPseudo().matches("^[a-zA-Z0-9]+$")) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_PSEUDO_ERREUR);
		};
		if (utilisateur.getNom() == null || utilisateur.getNom().trim().equals("") || utilisateur.getNom().trim().length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_NOM_UTILISATEUR_ERREUR);
		};
		if (utilisateur.getPrenom() == null || utilisateur.getPrenom().trim().equals("") || utilisateur.getPrenom().trim().length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_PRENOM_ERREUR);
		};
		if (utilisateur.getEmail() == null || utilisateur.getEmail().trim().equals("") || utilisateur.getEmail().trim().length() > 20) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_EMAIL_ERREUR);
		};
		if (utilisateur.getTelephone().trim().length() > 15) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_TELEPHONE_ERREUR);
		};
		if (utilisateur.getRue() == null || utilisateur.getRue().trim().equals("") || utilisateur.getRue().trim().length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_RUE_ERREUR);
		};
		if (utilisateur.getCodePostal() == null || utilisateur.getCodePostal().trim().equals("") || utilisateur.getCodePostal().trim().length() > 10) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_CODE_POSTAL_ERREUR);
		};
		if (utilisateur.getVille() == null || utilisateur.getVille().trim().equals("") || utilisateur.getVille().trim().length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_VILLE_ERREUR);
		};
		if (utilisateur.getMotDePasse() == null || utilisateur.getMotDePasse().trim().equals("") || utilisateur.getMotDePasse().trim().length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_MOT_DE_PASSE_ERREUR);
		};
	}
	
	private void validerMotsDePasse(Utilisateur utilisateur, String confMotDePasse, BusinessException businessException) {
		if (!utilisateur.getMotDePasse().equals(confMotDePasse) ) {
			businessException.ajouterErreur(CodesResultatBLL.MOTS_DE_PASSE_DIFFERENTS);
		}		
	}
	
	
	
	
	/************************ ARTICLES ***********************/
	
	public void ajouterArticle(Article article) throws BusinessException {
		BusinessException businessException = new BusinessException();
		validerChampsObligatoires(article, businessException); // Vérification de non nullité déjà faite dans la servlet, mais vérifiée de nouveau au cas où. Vérification du format également.
		if (!businessException.hasErreurs()) {
			articlesDAO.insertArticle(article);
		} else {
			throw businessException;
		}
	}
	
	public void modifierArticle(Article article) throws BusinessException {
		BusinessException businessException = new BusinessException();
		validerChampsObligatoires(article, businessException); // Vérification de non nullité déjà faite dans la servlet, mais vérifiée de nouveau au cas où. Vérification du format également.
		if (!businessException.hasErreurs()) {
			articlesDAO.updateArticle(article);
		} else {
			throw businessException;
		}
	}
	
	public List<Article> selectionnerEncheresOuvertes() throws BusinessException {
		return articlesDAO.selectEncheresOuvertes();
	}
	
	public List<Article> selectionnerEncheresOuvertes(String recherche, int idCategorie) throws BusinessException {
		List<Article> listeResultat;
		if (recherche.trim().equals("") && idCategorie == 0) {
			listeResultat = articlesDAO.selectEncheresOuvertes();
		} else if (!recherche.trim().equals("") && idCategorie != 0) {
			listeResultat = articlesDAO.selectEncheresOuvertes(recherche, idCategorie);
		} else if (idCategorie == 0) {
			listeResultat = articlesDAO.selectEncheresOuvertes(recherche);
		} else {
			listeResultat = articlesDAO.selectEncheresOuvertes(idCategorie);
		}		
		return listeResultat;
	}

	public List<Article> selectionnerArticlesSelonFiltresEtModes(int choix, int idUtilisateur, String recherche, int idCategorie) throws BusinessException {
		List<Article> listeResultat = new ArrayList<>();
		if (choix == 0) {
			listeResultat = selectionnerMethodeEncheresOuvertes(recherche, idCategorie);
		} else if (choix == 1) {
			listeResultat = selectionnerMethodeChoix1(idUtilisateur, recherche, idCategorie);
		} else if (choix == 2) {
			listeResultat = selectionnerMethodeChoix2(idUtilisateur, recherche, idCategorie);
		} else if (choix == 3) {
			listeResultat = selectionnerMethodeVentes(idUtilisateur, 2, recherche, idCategorie);
		} else if (choix == 4) {
			listeResultat = selectionnerMethodeVentes(idUtilisateur, 1, recherche, idCategorie);
		} else if (choix == 5) {
			listeResultat = selectionnerMethodeVentes(idUtilisateur, 3, recherche, idCategorie);
		}
		return listeResultat;
	}
	
	public Article selectionnerDetailArticle(int idArticle) throws BusinessException {
		Article article = articlesDAO.selectDetailArticle(idArticle);
		return article;
	}
	
	public void supprimerArticle(int idArticle) throws BusinessException {
		articlesDAO.deleteArticle(idArticle);
	}
	
	
	
	// Méthodes privées
	
	private void validerChampsObligatoires(Article article, BusinessException businessException) {
		if (article.getNom() == null || article.getNom().trim().equals("") || article.getNom().trim().length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_NOM_ARTICLE_ERREUR);
		};
		if (article.getDescription() == null || article.getDescription().trim().equals("") || article.getDescription().trim().length() > 300) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_DESCRIPTION_ERREUR);
		};
		if (article.getDateDebut() == null || article.getDateDebut().isBefore(LocalDate.now())) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_DATE_DEBUT_ERREUR);
		};
		if (article.getDateFin() == null || article.getDateFin().isBefore(LocalDate.now()) || article.getDateFin().isBefore(article.getDateDebut())) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_DATE_FIN_ERREUR);
		};
		if (article.getPrixInitial() == 0) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_PRIX_INITIAL_ERREUR);
		};
		if (article.getCategorie().getId() == 0) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_CATEGORIE_ERREUR);
		};
	}
	
	private List<Article> selectionnerMethodeEncheresOuvertes(String recherche, int idCategorie) throws BusinessException {
		List<Article> listeResultat;
		if (recherche.trim().equals("") && idCategorie == 0) {
			listeResultat = articlesDAO.selectEncheresOuvertes();
		} else if (!recherche.trim().equals("") && idCategorie != 0) {
			listeResultat = articlesDAO.selectEncheresOuvertes(recherche, idCategorie);
		} else if (idCategorie == 0) {
			listeResultat = articlesDAO.selectEncheresOuvertes(recherche);
		} else {
			listeResultat = articlesDAO.selectEncheresOuvertes(idCategorie);
		}		
		return listeResultat;
	}
	
	private List<Article> selectionnerMethodeChoix1(int idUtilisateur, String recherche, int idCategorie) throws BusinessException {
		List<Article> listeResultat;
		if (recherche.trim().equals("") && idCategorie == 0) {
			listeResultat = articlesDAO.selectEncheresUtilisateurEnCours(idUtilisateur);
		} else if (!recherche.trim().equals("") && idCategorie != 0) {
			listeResultat = articlesDAO.selectEncheresUtilisateurEnCours(idUtilisateur, recherche, idCategorie);
		} else if (idCategorie == 0) {
			listeResultat = articlesDAO.selectEncheresUtilisateurEnCours(idUtilisateur, recherche);
		} else {
			listeResultat = articlesDAO.selectEncheresUtilisateurEnCours(idUtilisateur, idCategorie);
			
		}		
		return listeResultat;
	}
		
	private List<Article> selectionnerMethodeChoix2(int idUtilisateur, String recherche, int idCategorie) throws BusinessException {
		List<Article> listeResultat;
		if (recherche.trim().equals("") && idCategorie == 0) {
			listeResultat = articlesDAO.selectEncheresUtilisateurRemportees(idUtilisateur);
		} else if (!recherche.trim().equals("") && idCategorie != 0) {
			listeResultat = articlesDAO.selectEncheresUtilisateurRemportees(idUtilisateur, recherche, idCategorie);
		} else if (idCategorie == 0) {
			listeResultat = articlesDAO.selectEncheresUtilisateurRemportees(idUtilisateur, recherche);
		} else {
			System.out.println("recherche catégorie");
			listeResultat = articlesDAO.selectEncheresUtilisateurRemportees(idUtilisateur, idCategorie);
			
		}		
		return listeResultat;
	}
	
	private List<Article> selectionnerMethodeVentes(int idUtilisateur, int etatVente, String recherche, int idCategorie) throws BusinessException {
		List<Article> listeResultat;
		if (recherche.trim().equals("") && idCategorie == 0) {
			listeResultat = articlesDAO.selectVentesUtilisateur(idUtilisateur, etatVente);
		} else if (!recherche.trim().equals("") && idCategorie != 0) {
			listeResultat = articlesDAO.selectVentesUtilisateur(idUtilisateur, etatVente, recherche, idCategorie);
		} else if (idCategorie == 0) {
			listeResultat = articlesDAO.selectVentesUtilisateur(idUtilisateur, etatVente, recherche);
		} else {
			listeResultat = articlesDAO.selectVentesUtilisateur(idUtilisateur, etatVente, idCategorie);
		}		
		return listeResultat;
	}
	
	
	
	/************************ ENCHERES ***********************/
		
	public void ajouterEnchere(Enchere enchere) throws BusinessException {
		BusinessException businessException = new BusinessException();
		validerSoldeSuffisant(enchere, businessException);
		if (!businessException.hasErreurs()) {
			encheresDAO.insertEnchere(enchere);
		} else {
			throw businessException;
		}
	}
	
	// Méthodes privées
	
	private void validerSoldeSuffisant(Enchere enchere, BusinessException businessException) {
		if (enchere.getUtilisateur().getCredit() < enchere.getMontantEnchere()) {
			businessException.ajouterErreur(CodesResultatBLL.NOMBRE_DE_POINTS_INSUFFISANT);
		}
	}
	
}
