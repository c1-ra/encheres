package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;

public interface ArticlesDAO {
	
	// ajoute l'article en base et set aussi l'id de l'article passé en param avec l'id nouvellement créé
	public void insertArticle(Article article) throws BusinessException;
	
	public void updateArticle(Article article) throws BusinessException;
	
	// retourne les infos utilies pour l'affichage des enchères sur la page d'accueil, selon les modes et/ou filtres
	public List<Article> selectEncheresOuvertes() throws BusinessException;	
	public List<Article> selectEncheresOuvertes(String recherche, int idCategorie) throws BusinessException;
	public List<Article> selectEncheresOuvertes(String recherche) throws BusinessException;
	public List<Article> selectEncheresOuvertes(int idCategorie) throws BusinessException;
	
	public List<Article> selectEncheresUtilisateurEnCours(int idUtilisateur) throws BusinessException;
	public List<Article> selectEncheresUtilisateurEnCours(int idUtilisateur, String recherche, int idCategorie) throws BusinessException;
	public List<Article> selectEncheresUtilisateurEnCours(int idUtilisateur, String recherche) throws BusinessException;
	public List<Article> selectEncheresUtilisateurEnCours(int idUtilisateur, int idCategorie) throws BusinessException;	
	
	public List<Article> selectEncheresUtilisateurRemportees(int idUtilisateur) throws BusinessException;
	public List<Article> selectEncheresUtilisateurRemportees(int idUtilisateur, String recherche, int idCategorie) throws BusinessException;
	public List<Article> selectEncheresUtilisateurRemportees(int idUtilisateur, String recherche) throws BusinessException;
	public List<Article> selectEncheresUtilisateurRemportees(int idUtilisateur, int idCategorie) throws BusinessException;
	
	public List<Article> selectVentesUtilisateur(int idUtilisateur, int etatVente) throws BusinessException;
	public List<Article> selectVentesUtilisateur(int idUtilisateur, int etatVente, String recherche, int idCategorie) throws BusinessException;
	public List<Article> selectVentesUtilisateur(int idUtilisateur, int etatVente, String recherche) throws BusinessException;
	public List<Article> selectVentesUtilisateur(int idUtilisateur, int etatVente, int idCategorie) throws BusinessException;
	
	public Article selectDetailArticle(int idArticle) throws BusinessException;
	
	public void deleteArticle(int idArticle) throws BusinessException;

 }
