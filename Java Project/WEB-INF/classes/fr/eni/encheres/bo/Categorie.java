package fr.eni.encheres.bo;

import java.io.Serializable;
import java.time.LocalDate;

public class Categorie implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String libelle;
	
	public Categorie() {
		super();
	}
		
	public Categorie(int idCategorie, String libelle) {
		this();
		this.id = idCategorie;
		this.libelle = libelle;
	}

	/**
	 * @return the idCategorie
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param idCategorie the idCategorie to set
	 */
	public void setId(int idCategorie) {
		this.id = idCategorie;
	}

	/**
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
		
}
