package fr.eni.encheres.servlets;

import java.io.IOException;
import java.util.ArrayList;
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
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletConnexion
 */
@WebServlet("/connexion")
public class ServletConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getAttribute("listeCodesErreur") != null) {
			request.removeAttribute("listeCodesErreur");
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/connexion.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// sera utilisé pour remplir les champs avec la saisie de l'utilisateur si la connexion n'aboutit pas
		Utilisateur utilisateurSaisie = new Utilisateur();
		
		Utilisateur utilisateur = new Utilisateur();
		
		String login;
		String mdp;
		
		List<Integer> listeCodesErreur = new ArrayList<>();
		
		RequestDispatcher rd;
		
		login = request.getParameter("login");
		mdp = request.getParameter("mdp");
		
		// vérification que les champs obligatoires sont remplis
		if (login == null || login.trim().isEmpty()) {
			listeCodesErreur.add(CodesResultatServlets.LOGIN_VIDE_ERREUR);
		} else {
			utilisateurSaisie.setPseudo(login);
		}
		
		if (mdp == null || mdp.trim().isEmpty()) {
			listeCodesErreur.add(CodesResultatServlets.MDP_VIDE_ERREUR);
		} else {
			utilisateurSaisie.setMotDePasse(mdp);
		}	
		
		HttpSession session = request.getSession();
		request.setAttribute("utilisateurSaisie", utilisateurSaisie);
			
		// s'il y a des erreurs : renvoi vers la connexion avec la liste et les données précédemment saisies
		if (listeCodesErreur.size() > 0) {
			request.setAttribute("listeCodesErreur",listeCodesErreur);
			rd = request.getRequestDispatcher("/WEB-INF/pages/connexion.jsp");
			rd.forward(request, response);
		// s'il n'y a pas d'erreurs, vérification que le login est en base, et comparaison au mot de passe donné
		} else {
			EncheresManager encheresManager = new EncheresManager();
			try {
				utilisateur = encheresManager.connexion(login);
			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				rd = request.getRequestDispatcher("/WEB-INF/pages/connexion.jsp");
				rd.forward(request, response);
			}	
			if (!(utilisateurSaisie.getMotDePasse()).equals(utilisateur.getMotDePasse())) {
				listeCodesErreur.add(1);
				request.setAttribute("listeCodesErreur", listeCodesErreur);
				rd = request.getRequestDispatcher("/WEB-INF/pages/connexion.jsp");
				rd.forward(request, response);
			}
			// si les informations sont correctes : envoi vers l'accueil avec Utilisateur
			if (utilisateur.getId() != 0 && (utilisateurSaisie.getMotDePasse()).equals(utilisateur.getMotDePasse())) {
				session.setAttribute("utilisateur", utilisateur);
				request.setAttribute("connexion", "true");
				rd = request.getRequestDispatcher("/accueil");
			// si les informations ne sont pas correctes : renvoie vers la page de connexion avec un message et UtilisateurSaisie contenant le login et mdp précédemment envoyés pour pré-remplir les champs
			} else {
				request.setAttribute("listeCodesErreur", listeCodesErreur);
				rd = request.getRequestDispatcher("/WEB-INF/pages/connexion.jsp");
			}
			rd.forward(request, response);

		}
	}

}
