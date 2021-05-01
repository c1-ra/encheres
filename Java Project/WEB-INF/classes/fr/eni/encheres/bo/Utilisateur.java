package fr.eni.encheres.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Utilisateur implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String pseudo;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private String rue;
	private String codePostal;
	private String ville;
	private String motDePasse;
	private int credit;
	private boolean administrateur;
	private List<Article> listeArticles;
	private List<Enchere> listeEncheres;
	
	public Utilisateur() {
		super();
		this.listeArticles = new ArrayList<>();
		this.listeEncheres = new ArrayList<>();
	}
	
	// Avec seulement id et pseudo : les champs utiles pour l'affichage des enchères en cours
	public Utilisateur(int id, String pseudo) {
		this();
		this.id = id;
		this.pseudo = pseudo;		
	}
	
	// Avec tous les champs obligatoires pour insertion en bdd
	// sans id (int : 0 par défaut), listeArticles, credit (int : 0 par défaut) et administrateur (boolean : false par défaut)
	public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue,
			String codePostal, String ville, String motDePasse) {
		this();
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.motDePasse = motDePasse;
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
	 * @return the pseudo
	 */
	public String getPseudo() {
		return pseudo;
	}

	/**
	 * @param pseudo the pseudo to set
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
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
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the rue
	 */
	public String getRue() {
		return rue;
	}

	/**
	 * @param rue the rue to set
	 */
	public void setRue(String rue) {
		this.rue = rue;
	}

	/**
	 * @return the codePostal
	 */
	public String getCodePostal() {
		return codePostal;
	}

	/**
	 * @param codePostal the codePostal to set
	 */
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	/**
	 * @return the ville
	 */
	public String getVille() {
		return ville;
	}

	/**
	 * @param ville the ville to set
	 */
	public void setVille(String ville) {
		this.ville = ville;
	}

	/**
	 * @return the motDePasse
	 */
	public String getMotDePasse() {
		return motDePasse;
	}

	/**
	 * @param motDePasse the motDePasse to set
	 */
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	/**
	 * @return the credit
	 */
	public int getCredit() {
		return credit;
	}

	/**
	 * @param credit the credit to set
	 */
	public void setCredit(int credit) {
		this.credit = credit;
	}

	/**
	 * @return the administrateur
	 */
	public boolean isAdministrateur() {
		return administrateur;
	}

	/**
	 * @param administrateur the administrateur to set
	 */
	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}
		
	/**
	 * @return the listeArticles
	 */
	public List<Article> getListeArticles() {
		return this.listeArticles;
	}
	
	/**
	 * @param listeArticles the listeArticles to set
	 */
	public void setListeArticles(List<Article> listeArticles) {
		this.listeArticles = listeArticles;
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

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Utilisateur [id=" + id + ", pseudo=" + pseudo + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", telephone=" + telephone + ", rue=" + rue + 
				", codePostal=" + codePostal + ", ville=" + ville + ", motDePasse=" + motDePasse + ", credit=" + credit + ", administrateur=" + administrateur + "]");
		for (Article a : listeArticles) {
			sb.append(System.lineSeparator());
			sb.append(a.toString());
		}
		return sb.toString();
	}	

}
