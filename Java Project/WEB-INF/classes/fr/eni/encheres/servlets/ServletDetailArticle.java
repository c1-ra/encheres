package fr.eni.encheres.servlets;

import java.io.IOException;
import java.time.LocalDate;
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
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletVendreArticle
 */
@WebServlet("/detailArticle")
public class ServletDetailArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/detailArticle.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EncheresManager encheresManager = new EncheresManager();
		
		// L'utilisateur enchérit sur l'article
		if (request.getParameter("proposition") != null) {
			HttpSession session = request.getSession();
			Utilisateur utilisateur = (Utilisateur)session.getAttribute("utilisateur");
			Article article = new Article();
			int idArticle = Integer.parseInt(request.getParameter("encherir"));
			article.setId(idArticle);
			Enchere enchere = new Enchere(utilisateur, article, LocalDate.now(), Integer.parseInt(request.getParameter("proposition")));
			try {
				encheresManager.ajouterEnchere(enchere);
				int creditUtilisateur = utilisateur.getCredit();
				int montantEnchere = enchere.getMontantEnchere();
				// mise à jour du crédit de l'utilisateur en attribut de session
				utilisateur.setCredit(creditUtilisateur - montantEnchere);
				session.setAttribute("utilisateur", utilisateur);
				RequestDispatcher rd = request.getRequestDispatcher("/accueil");
				rd.forward(request, response);
			} catch (BusinessException e) {				
				e.printStackTrace();
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				try {
					article = encheresManager.selectionnerDetailArticle(idArticle);
					request.setAttribute("article", article);
				} catch (BusinessException e2) {
					e.printStackTrace();
					request.setAttribute("listeCodesErreur", e2.getListeCodesErreur());
				}		
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/detailArticle.jsp");
				rd.forward(request, response);
			}	
			
		// L'utilisateur consulte un article
		} else {
			int idArticle = Integer.parseInt(request.getParameter("detailArticle"));			
			try {
				Article article = encheresManager.selectionnerDetailArticle(idArticle);
				request.setAttribute("article", article);
			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			}		
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/detailArticle.jsp");
			rd.forward(request, response);
		}
	}
		
}
