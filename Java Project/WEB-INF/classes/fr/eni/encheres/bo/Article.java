package fr.eni.encheres.bo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Article implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nom;
	private String description;
	private LocalDate dateDebut;
	private LocalDate dateFin;
	private int prixInitial;
	private int prixVente;
	private Categorie categorie;
	private Retrait retrait;
	private Utilisateur utilisateur;
	private List<Enchere> listeEncheres;
	private int idEtatVente; //1 : non commencé, 2 : en cours, 3 : terminée
	

	public Article() {
		super();
		categorie = new Categorie();
		retrait = new Retrait();
		utilisateur = new Utilisateur();
		listeEncheres = new ArrayList<>();
	}
	
	// Avec tous les champs obligatoires pour insertion en bdd
	// sans id, prixVente ((prix en cours)) est initialisé au prixInitial. Pour savoir si une enchère a été faite ou si la vente est terminée,
	// se reporter à la date et à l'idEtatVente
	public Article(String nom, String description, LocalDate dateDebut, LocalDate dateFin, int prixInitial,
			int idCategorie, Retrait retrait, int idUtilisateur) {
		this();
		this.nom = nom;
		this.description = description;
		this.setDateDebut(dateDebut);
		this.setDateFin(dateFin);
		this.prixInitial = prixInitial;
		this.prixVente = prixInitial;
		this.categorie.setId(idCategorie);
		this.retrait = retrait;
		this.utilisateur.setId(idUtilisateur);
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the dateDebut
	 */
	public LocalDate getDateDebut() {
		return dateDebut;
	}

	/**
	 * @param dateDebut the dateDebut to set
	 */
	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * @return the dateFin
	 */
	public LocalDate getDateFin() {
		return dateFin;
	}

	/**
	 * @param dateFin the dateFin to set
	 */
	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	/**
	 * @return the prixInitial
	 */
	public int getPrixInitial() {
		return prixInitial;
	}

	/**
	 * @param prixInitial the prixInitial to set
	 */
	public void setPrixInitial(int prixInitial) {
		this.prixInitial = prixInitial;
	}

	/**
	 * @return the prixVente
	 */
	public int getPrixVente() {
		return prixVente;
	}

	/**
	 * @param prixVente the prixVente to set
	 */
	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}

	/**
	 * @return the categorie
	 */
	public Categorie getCategorie() {
		return categorie;
	}

	/**
	 * @param categorie the categorie to set
	 */
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	/**
	 * @return the categorie
	 */
	public Retrait getRetrait() {
		return retrait;
	}

	/**
	 * @param categorie the categorie to set
	 */
	public void setRetrait(Retrait retrait) {
		this.retrait = retrait;
	}
	
	/**
	 * @return the utilisateur
	 */
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	/**
	 * @param idUtilisateur the idUtilisateur to set
	 */
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	/**
	 * @return the listeEncheres
	 */
	public List<Enchere> getListeEncheres() {
		return listeEncheres;
	}

	/**
	 * @param listeEncheres the listeEncheres to set
	 */
	public void setListeEncheres(List<Enchere> listeEncheres) {
		this.listeEncheres = listeEncheres;
	}

	/**
	 * @return the etatVente
	 */
	public int getIdEtatVente() {
		return idEtatVente;
	}

	/**
	 * @param etatVente the etatVente to set
	 */
	public void setIdEtatVente(int idEtatVente) {
		this.idEtatVente = idEtatVente;
	}

	@Override
	public String toString() {
		String str = "Article [id=" + id + ", nom=" + nom + ", description=" + description + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + 
				", prixInitial=" + prixInitial + "prixVente=" + prixVente + ", categorie=" + categorie.getId() + ", retrait=" + retrait.toString() + ", idUtilisateur=" + utilisateur.getId() + ", pseudoUtilisateur=" + utilisateur.getPseudo() + "]";
		return str;
	}
	

}
