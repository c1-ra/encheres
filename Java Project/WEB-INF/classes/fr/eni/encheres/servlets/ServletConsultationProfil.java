package fr.eni.encheres.servlets;

import java.io.IOException;

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
 * Servlet implementation class ServletProfil
 */
@WebServlet("/consultationProfil")
public class ServletConsultationProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EncheresManager encheresManager = new EncheresManager();
		String pseudo = "";
		if (request.getParameter("profilPseudo") != null) {	
			pseudo = request.getParameter("profilPseudo");
			System.out.println(pseudo);
		} else {
			pseudo = request.getParameter("pseudo");
		}
		
		try {
			Utilisateur utilisateur = encheresManager.selectionnerUtilisateurByPseudo(pseudo);
			request.setAttribute("profil", utilisateur);
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			RequestDispatcher rd = request.getRequestDispatcher("/accueil");
			rd.forward(request, response);
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/profil.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (request.getParameter("consultationProfil") != null) {
			int idProfil = Integer.parseInt(request.getParameter("consultationProfil"));
			// Vérification de s'il s'agit du profil de l'utilisateur car il aura des options en plus (modification) si c'est le cas
			if (idProfil == ((Utilisateur)session.getAttribute("utilisateur")).getId()) {
				request.setAttribute("monProfil", "true");
			} else {
				EncheresManager encheresManager = new EncheresManager();
				try {
					Utilisateur utilisateur = encheresManager.selectionnerUtilisateurById(idProfil);
					request.setAttribute("profil", utilisateur);
				} catch (BusinessException e) {
					e.printStackTrace();
					request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
					RequestDispatcher rd = request.getRequestDispatcher("/accueil");
					rd.forward(request, response);
				}
			}
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/profil.jsp");
			rd.forward(request, response);
		}
	}	
}
