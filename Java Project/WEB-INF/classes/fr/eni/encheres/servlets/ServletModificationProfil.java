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
 * Servlet implementation class ServletMonProfil
 */
@WebServlet("/modificationProfil")
public class ServletModificationProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/modificationProfil.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd;	
		// premier passage : on arrive depuis le bouton "modifier" de la consultation de son profil
		if (request.getParameter("modifierMonProfil") != null) {
			rd = request.getRequestDispatcher("WEB-INF/pages/modificationProfil.jsp");
			rd.forward(request, response);
		
		} else {
			HttpSession session = request.getSession();
			List<Integer> listeCodesErreur = new ArrayList<>();
			Utilisateur utilisateurSaisie = buildUtilisateurDepuisSaisie(request, listeCodesErreur);
			request.setAttribute("confMotDePasse", request.getParameter("confMotDePasse"));
			request.setAttribute("utilisateurSaisie", utilisateurSaisie);
			int id = ((Utilisateur)session.getAttribute("utilisateur")).getId();
			utilisateurSaisie.setId(id);
			utilisateurSaisie.setCredit(((Utilisateur)session.getAttribute("utilisateur")).getCredit());
			EncheresManager encheresManager = new EncheresManager();
			
			// l'utilisateur veut enregistrer des changements
			if (request.getParameter("enregistrer") != null) {			
				if (listeCodesErreur.size() > 0) {
					List<Integer> listeCodesErreurSansDoublons = new ArrayList<>(new HashSet<>(listeCodesErreur)); // pour enlever les doublons "tous les champs * doivent être remplis"
					request.setAttribute("listeCodesErreur", listeCodesErreurSansDoublons);							
					rd = request.getRequestDispatcher("/WEB-INF/pages/modificationProfil.jsp");
					rd.forward(request, response);
				} else {
					try {
						encheresManager.modifierUtilisateur(id, utilisateurSaisie, request.getParameter("confMotDePasse"));
						session.setAttribute("utilisateur", utilisateurSaisie);
						rd = request.getRequestDispatcher("/accueil");
						rd.forward(request, response);	
					} catch (BusinessException e) {
						e.printStackTrace();
						request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
						rd = request.getRequestDispatcher("/WEB-INF/pages/modificationProfil.jsp");
						rd.forward(request, response);
					} 
				}
				
			// l'utilisateur veut supprimer son compte
			} else if (request.getParameter("supprimer") != null) {
				System.out.println("suppression");
				try {
					encheresManager.supprimerUtilisateur(id);
					session.removeAttribute("utilisateur");
					request.removeAttribute("utilisateurSaisie");
					request.removeAttribute("confMotDePasse");
					rd = request.getRequestDispatcher("WEB-INF/pages/accueil.jsp");
					rd.forward(request, response);
				} catch (BusinessException e) {
					e.printStackTrace();
					request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
					rd = request.getRequestDispatcher("/WEB-INF/pages/modificationProfil.jsp");
					rd.forward(request, response);
				}
				
			// l'utilisateur veut retourner à l'accueil
			} else if (request.getParameter("retour") != null) {
				rd = request.getRequestDispatcher("/accueil");
				rd.forward(request, response);
			}
		}
	}
	
	// Méthodes privées
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
