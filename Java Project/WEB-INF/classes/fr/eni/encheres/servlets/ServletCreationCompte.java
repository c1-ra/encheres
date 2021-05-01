package fr.eni.encheres.servlets;

import java.io.IOException;
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
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class creationCompte
 */
@WebServlet("/creationCompte")
public class ServletCreationCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("listeCodesErreur") != null) {
			request.removeAttribute("listeCodesErreur");
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/creationCompte.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<Integer> listeCodesErreur=new ArrayList<>();
		Utilisateur utilisateurSaisie = buildUtilisateurDepuisSaisie(request, listeCodesErreur);
		request.setAttribute("utilisateurSaisie", utilisateurSaisie);
		// cas 1 : des champs obligatoires manquent ou les mots de passe ne concordent pas.
		// on prépare un renvoi vers la création de compte, avec la liste des erreurs et les infos déjà saisies via Utilisateur utilisateurSaisie.
		if (listeCodesErreur.size() > 0) {
			List<Integer> listeCodesErreurSansDoublons = new ArrayList<>(new HashSet<>(listeCodesErreur)); // pour enlever les doublons "tous les champs * doivent être remplis"
			request.setAttribute("listeCodesErreur", listeCodesErreurSansDoublons);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/creationCompte.jsp");
			rd.forward(request, response);
		// Champs obligatoires remplis et mots de passe qui concordent
		} else	{
			EncheresManager encheresManager = new EncheresManager();
			// cas 2 : succès : envoi vers l'accueil en mode connecté
			try {
				encheresManager.ajouterUtilisateur(utilisateurSaisie, request.getParameter("confMotDePasse"));
				session.setAttribute("utilisateur", utilisateurSaisie);
				request.setAttribute("creaCompte", "true");
				RequestDispatcher rd = request.getRequestDispatcher("/accueil");
				rd.forward(request, response);
			// cas 3 : erreur au niveau des autres couches
			// Renvoi vers la création de compte avec la liste des erreurs les infos déjà saisies via Utilisateur utilisateurSaisie.
			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/creationCompte.jsp");
				rd.forward(request, response);
			}		
		}
	}
	
	private Utilisateur buildUtilisateurDepuisSaisie(HttpServletRequest request, List<Integer> listeCodesErreur) {
		Utilisateur utilisateurSaisie = new Utilisateur();
		utilisateurSaisie.setPseudo(request.getParameter("pseudo"));
		utilisateurSaisie.setNom(request.getParameter("nom"));
		utilisateurSaisie.setPrenom(request.getParameter("prenom"));
		utilisateurSaisie.setEmail(request.getParameter("email"));
		utilisateurSaisie.setTelephone(request.getParameter("telephone"));
		utilisateurSaisie.setRue(request.getParameter("rue"));
		utilisateurSaisie.setCodePostal(request.getParameter("codePostal"));
		utilisateurSaisie.setVille(request.getParameter("ville"));
		utilisateurSaisie.setMotDePasse(request.getParameter("motDePasse"));					
		return utilisateurSaisie;
	}

}
