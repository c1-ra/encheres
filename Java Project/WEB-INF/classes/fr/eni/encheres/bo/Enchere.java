package fr.eni.encheres.bo;

import java.io.Serializable;
import java.time.LocalDate;

public class Enchere implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Utilisateur utilisateur;
	private Article article;
	private LocalDate dateEnchere;
	private int montantEnchere;
	
	public Enchere() {
		super();
		utilisateur = new Utilisateur();
		article = new Article();
	}
	
	// Constructeur pour l'affichage du détail d'un article
	public Enchere(int idUtilisateur, String pseudo, int montantEnchere) {
		this();
		this.utilisateur.setId(idUtilisateur);
		this.utilisateur.setPseudo(pseudo);
		this.montantEnchere = montantEnchere;
	}

	public Enchere(Utilisateur utilisateur, Article article, LocalDate dateEnchere, int montantEnchere) {
		this();
		this.utilisateur = utilisateur;
		this.article = article;
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
	}

	/**
	 * @return the utilisateur
	 */
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	/**
	 * @param utilisateur the utilisateur to set
	 */
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	/**
	 * @return the article
	 */
	public Article getArticle() {
		return article;
	}

	/**
	 * @param article the article to set
	 */
	public void setArticle(Article article) {
		this.article = article;
	}

	/**
	 * @return the dateEnchere
	 */
	public LocalDate getDateEnchere() {
		return dateEnchere;
	}

	/**
	 * @param dateEnchere the dateEnchere to set
	 */
	public void setDateEnchere(LocalDate dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	/**
	 * @return the montantEnchere
	 */
	public int getMontantEnchere() {
		return montantEnchere;
	}

	/**
	 * @param montantEnchere the montantEnchere to set
	 */
	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}
	
	
	
}
