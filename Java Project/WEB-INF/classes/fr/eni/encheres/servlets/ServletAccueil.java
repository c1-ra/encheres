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
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletAccueil
 */
@WebServlet("/accueil")
public class ServletAccueil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();		
		int idUtilisateur = 0;
		Utilisateur utilisateur;	
		boolean connecte = session.getAttribute("utilisateur") != null;
		
		List<String> listeFiltres = new ArrayList<>();
		
		EncheresManager encheresManager = new EncheresManager();
		
		List<Article> listeAffichage;
		
		// Vérification de si l'utilisateur est connecté et initialisation des variables nécessaires pour la suite
		if (connecte) {
			utilisateur = (Utilisateur)session.getAttribute("utilisateur");
			idUtilisateur = utilisateur.getId();
		}
		
		// cas 1 : utilisateur non connecté et les paramètres des filtres n'existent pas encore ou bien ils sont vides.
		if (!connecte && (((((request.getParameter("recherche") == null)
				&& (request.getParameter("categorie") == null)
				&& (request.getParameter("filtre") == null))
				&& request.getParameter("mode") == null))
				|| (request.getParameter("recherche").equals("") && request.getParameter("categorie").equals("0")))) {
			// initialisation des attributs de session pour pouvoir garder en mémoire les derniers filtres utilisés et les ré-afficher quand l'utilisateur
			// navigue entre différentes pages
			session.setAttribute("recherche", "");
			session.setAttribute("categorie", 0);
			session.setAttribute("mode", 0);
			try {
				listeAffichage = encheresManager.selectionnerEncheresOuvertes();
				request.setAttribute("listeAffichage", listeAffichage);
			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			}
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp");
			rd.forward(request, response);

		// S'il y a des filtres
		} else {
			String recherche;
			int categorie;
			
			// s'il y a des paramètres de request, on les récupère et on les met en paramètres de session (pour garder l'info si jamais on va sur une autre page puis qu'on renvient)
			// s'il n'y a pas de paramètre de request car on vient d'une autre page, on vérifie les paramètres de session pour trouver les filtres			
			
			if (request.getParameter("recherche") != null) {
				recherche = request.getParameter("recherche");
				session.setAttribute("recherche", recherche);
			} else if (session.getAttribute("recherche") != null) {
				recherche = (String)session.getAttribute("recherche");
			} else {
				// valeur par défaut : fera apparaître le placeholder
				recherche = "";
			}
			
			if (request.getParameter("categorie") != null) {
				categorie = Integer.parseInt(request.getParameter("categorie"));
				session.setAttribute("categorie", categorie);
			} else if (session.getAttribute("categorie") != null) {
				categorie = (Integer)session.getAttribute("categorie");	
			} else {
				// valeur par défaut : valeur "toutes"
				categorie = 0;
			}
			
			// si non connecté, seuls les filtres peuvent êtres appliqués : méthode qui prend en compte les filtres seulement
			if (!connecte)	{
				try {
					listeAffichage = encheresManager.selectionnerEncheresOuvertes(recherche, categorie);
					request.setAttribute("listeAffichage", listeAffichage);
					
				} catch (BusinessException e) {
					e.printStackTrace();
					request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				}
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp");
				rd.forward(request, response);
				
			// si connecté : il y a des modes en plus des filtres à prendre en compte pour interroger la bdd
			} else if (request.getParameter("deconnexion") == null) {
				// si l'utilisateur vient de la page de connexion ou création de compte, je reset les filtres
				if (request.getParameter("creationCompte") != null || request.getParameter("connexion") != null) {
					session.setAttribute("recherche", "");
					session.setAttribute("categorie", 0);
					session.setAttribute("mode", 0);
				}
				int choix = -1;
				if (request.getParameter("radioAchats") != null) {
					choix = Integer.parseInt(request.getParameter("radioAchats"));
					session.setAttribute("mode", choix);					
				} else if (request.getParameter("radioVentes") != null) {	
					choix = Integer.parseInt(request.getParameter("radioVentes"));
					session.setAttribute("mode", choix);
				} else if (session.getAttribute("mode") != null) {
					choix = (int)session.getAttribute("mode");
				} else if (session.getAttribute("mode") == null) {
					// valeur par défaut : achats / enchères ouvertes
					session.setAttribute("mode", 0);
				}
				try {
					listeAffichage = encheresManager.selectionnerArticlesSelonFiltresEtModes(choix, idUtilisateur, recherche, categorie);
					request.setAttribute("listeAffichage", listeAffichage);
				} catch (BusinessException e) {
					e.printStackTrace();
					request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				}
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp");
				rd.forward(request, response);			
				
				// Déconnexion de l'utilisateur
			} else if (request.getParameter("deconnexion") != null && request.getParameter("deconnexion").equals("true")) {
				session.removeAttribute("utilisateur");
				session.setAttribute("recherche", "");
				session.setAttribute("categorie", 0);
				session.setAttribute("mode", 0);
				try {
					listeAffichage = encheresManager.selectionnerEncheresOuvertes();
					request.setAttribute("listeAffichage", listeAffichage);
				} catch (BusinessException e) {
					e.printStackTrace();
					request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				}
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp");
				rd.forward(request, response);
			}
					
		}
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}


}
