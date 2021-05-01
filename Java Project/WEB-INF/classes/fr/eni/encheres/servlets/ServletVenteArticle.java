package fr.eni.encheres.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.EncheresManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletVendreArticle
 */
@WebServlet("/venteArticle")
public class ServletVenteArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/venteArticle.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<Integer> listeCodesErreur=new ArrayList<>();
		
		// Suppression d'un article
		if (request.getParameter("idArticleASupprimer") != null) {
			int idArticleASupprimer = Integer.parseInt(request.getParameter("idArticleASupprimer"));
			EncheresManager encheresManager = new EncheresManager();
			try {
				encheresManager.supprimerArticle(idArticleASupprimer);		
			} catch (BusinessException e) {
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			}
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp");
			rd.forward(request, response);
		
			// Insertion d'un nouvel article depuis l'accueil
		} else if (request.getParameter("idArticleAModifier") == null) {
			Article articleSaisie = buildArticleDepuisSaisie(request, listeCodesErreur);
			articleSaisie.getUtilisateur().setId((((Utilisateur)(session.getAttribute("utilisateur"))).getId()));
			request.setAttribute("article", articleSaisie);
			EncheresManager encheresManager = new EncheresManager();
			// Succès : envoi vers l'accueil
			try {
				if (request.getParameter("enregistrer") != null && !request.getParameter("enregistrer").equals("") && !request.getParameter("enregistrer").equals("0")) {
					articleSaisie.setId(Integer.parseInt(request.getParameter("enregistrer")));
					encheresManager.modifierArticle(articleSaisie);
				} else {
					encheresManager.ajouterArticle(articleSaisie);
				}
				RequestDispatcher rd = request.getRequestDispatcher("/accueil");
				rd.forward(request, response);
			// Erreur au niveau des autres couches
			// Renvoi vers la création de compte avec la liste des erreurs les infos déjà saisies via Utilisateur utilisateurSaisie.
			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/venteArticle.jsp");
				rd.forward(request, response);
			}
			
			// Modification d'un article déjà existant depuis la page de consultation d'un article dont la vente n'a pas encore débutée
		} else if (request.getParameter("idArticleAModifier") != null) {
			int idArticleAModifier = Integer.parseInt(request.getParameter("idArticleAModifier"));
			EncheresManager encheresManager = new EncheresManager();
			try {
				Article articleAModifier = encheresManager.selectionnerDetailArticle(idArticleAModifier);
				request.setAttribute("article", articleAModifier);			
			} catch (BusinessException e) {
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			}
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/venteArticle.jsp");
			rd.forward(request, response);
		}
	}
	
	// Méthodes privées

	private Article buildArticleDepuisSaisie(HttpServletRequest request, List<Integer> listeCodesErreur) {
		Article articleSaisie = new Article();
		articleSaisie.setNom(request.getParameter("nom"));
		articleSaisie.getCategorie().setId(Integer.parseInt(request.getParameter("cate")));
		articleSaisie.setDescription(request.getParameter("description"));
		articleSaisie.setPrixInitial(Integer.parseInt(request.getParameter("prixInitial")));
		articleSaisie.setPrixVente(Integer.parseInt(request.getParameter("prixInitial")));
		articleSaisie.setDateDebut(LocalDate.parse(request.getParameter("dateDebut")));
		articleSaisie.setDateFin(LocalDate.parse(request.getParameter("dateFin")));	
		articleSaisie.getRetrait().setRue(request.getParameter("rue"));
		articleSaisie.getRetrait().setCodePostal(request.getParameter("codePostal"));
		articleSaisie.getRetrait().setVille(request.getParameter("ville"));				
		return articleSaisie;
	}

}
