<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<%@ include file="../fragments/head.jsp" %>
<body>

	<%@ include file="../fragments/bandeau.jsp" %>

	<div class="wrapper wrapper-md">

		<div class="container-fluid">
		
			<c:choose>
				<%-- La vente est en cours ou non débutée, que l'utilisateur soit le vendeur ou non, il va avoir accès au détail de la vente (+ possibilité d'aller vers la modification d'article si l'utilisateur est le vendeur) --%>
				<c:when test="${article.idEtatVente <= 2}">
					<h1>Détail vente</h1>
				</c:when>
				<%-- La vente est est finie (l'utilisateur ne peut y accéder que s'il est acquéreur (via "mes enchères remportées") ou vendeur (via "mes ventes terminées")) --%>
				<c:when test="${article.idEtatVente > 2}">
					<c:choose>					
						<%-- L'utilisateur est le vendeur --%>
						<c:when test="${utilisateur.id == article.utilisateur.id}">
							<h1>${article.listeEncheres[0].utilisateur.pseudo} a remporté l'enchère</h1>
						</c:when>
						<%-- L'utilisateur est l'acquéreur --%>
						<c:when test="${utilisateur.id == article.listeEncheres[0].utilisateur.id}">
							<h1>Vous avez remporté la vente</h1>
						</c:when>
						
					</c:choose>
					
				</c:when>
			
			</c:choose>
			

			<%@ include file="../fragments/affichageErreurs.jsp" %>
			
			<div class="row">
			
				<div class="col-12 col-md-3">
					img
				</div>
				
				<div class="col-12 col-md-9">
					<div class="col-12">${article.nom}</div>
					<div class="row">
						<div class="col-4">Description : </div>
						<div class="col-8">${article.description}</div>		
					</div>
					<div class="row">
						<div class="col-4">Catégorie : </div>
						<div class="col-8">${article.categorie.libelle}</div>
					</div>				
					<div class="row">
						<div class="col-4">Meilleure offre : </div>
						<c:choose>
							<c:when test="${(!empty article.listeEncheres[0].montantEnchere?utilisateur.id == article.listeEncheres[0].utilisateur.id:false) && article.idEtatVente > 2}">
								<div class="col-8">${article.listeEncheres[0].montantEnchere += " points"}</div>
							</c:when>
							<c:when test="${article.idEtatVente == 1}">
								<fmt:parseDate  value="${article.dateDebut}"  type="date" pattern="yyyy-MM-dd" var="parsedDateDebut" />							
								<div class="col-8">L'enchère n'a pas encore commencé ! Prévue à partir du <fmt:formatDate value="${parsedDateDebut}" type="date" pattern="dd/MM/yyyy" /></div>
							</c:when>
							<%-- L'utilisateur est vendeur et la vente est terminée, un acquéreur a été trouvé --%>
							<c:when test="${article.idEtatVente > 2 && utilisateur.id == article.utilisateur.id && !empty article.listeEncheres[0].utilisateur.id}">
								<div class="col-8">${article.listeEncheres[0].montantEnchere} points par
								<c:url var="servletConsultationProfil" value="/consultationProfil"/>
									<form class="post-btn pseudo-link" action="${servletConsultationProfil}" method="POST">
										<button type="submit" name="consultationProfil" value="${article.listeEncheres[0].utilisateur.id}" class="btn-link">${article.listeEncheres[0].utilisateur.pseudo}</button>
									</form>
								</div>
							</c:when>
							<%-- L'utilisateur est vendeur et la vente est terminée, mais aucune enchère n'a été faite --%>
							<c:when test="${article.idEtatVente > 2 && utilisateur.id == article.utilisateur.id && empty article.listeEncheres[0].utilisateur.id}">
								<div class="col-8">Aucune enchère n'a été proposée !</div>	
							</c:when>
							<%-- La vente est encore en cours, aucune proposition pour l'instant --%>
							<c:otherwise>
								<div class="col-8">${!empty article.listeEncheres[0].montantEnchere?article.listeEncheres[0].montantEnchere += " points par " += article.listeEncheres[0].utilisateur.pseudo:"Pas d'offre pour le moment"}</div>	
							</c:otherwise>
						</c:choose>					
					</div>
					<div class="row">
						<div class="col-4">Mise à prix : </div>
						<div class="col-8">${article.prixInitial} points</div>	
					</div>
					<div class="row">
						<div class="col-4">Fin de l'enchère : </div>
						<div class="col-8">
							<fmt:parseDate  value="${article.dateFin}"  type="date" pattern="yyyy-MM-dd" var="parsedDateFin" />
							<fmt:formatDate value="${parsedDateFin}" type="date" pattern="dd/MM/yyyy" />
						</div>
					</div>
					<div class="row">
						<div class="col-4">Retrait : </div>
						<div class="col-8">
							<div>${article.retrait.rue}</div>
							<div>${article.retrait.codePostal += " " += article.retrait.ville}</div>
						</div>	
					</div>
					<div class="row">
						<div class="col-4">Vendeur : </div>						
						<c:choose>
							<%-- L'utilisateur est acquéreur et la vente est terminée : lien vers le profil du vendeur --%>
							<c:when test="${article.idEtatVente > 2 && !empty article.listeEncheres[0].utilisateur.id && utilisateur.id == article.listeEncheres[0].utilisateur.id}">
								<div class="col-8">
								<c:url var="servletConsultationProfil" value="/consultationProfil"/>
									<form class="post-btn pseudo-link" action="${servletConsultationProfil}" method="POST">
										<button type="submit" name="consultationProfil" value="${article.utilisateur.id}" class="btn-link">${article.utilisateur.pseudo}</button>
									</form>
								</div>
							</c:when>
							<%-- Sinon pas de lien vers le profil du vendeur --%>
							<c:otherwise>
								<div class="col-8">${article.utilisateur.pseudo}</div>
							</c:otherwise>
						</c:choose>
						
					</div>					
					
				</div>	
							
			</div>			
			
			<c:choose>
			
				<%-- Si l'utilisateur n'est pas le vendeur, il peut tenter de faire une enchère : boutons "enchérir" (enabled seulement si l'utiliateur a au moins autant de crédit que la dernière offre + 1) et "retour" --%>
				<c:when test="${article.utilisateur.id != utilisateur.id && article.idEtatVente == 2}">
					<c:url var="servletDetailArticle" value="/detailArticle" />		
					<form method="POST" action="${servletDetailArticle}">				
						<div class="row">
							<div class="col-9 offset-md-3">
								<div class="row">			
									<div class="col-4">Ma proposition: </div>
									<div class="col-8">
										<c:choose>
											<c:when test="${!empty article.listeEncheres[0].montantEnchere?utilisateur.credit > article.listeEncheres[0].montantEnchere:utilisateur.credit > article.prixInitial}">
											<%-- min : dernière enchère + 1, max : nombre de points totaux de l'utilisateur --%>		
												<input type="number" id="proposition" name="proposition" min="${(!empty article.listeEncheres[0].montantEnchere?article.listeEncheres[0].montantEnchere:article.prixInitial) + 1}" max="${utilisateur.credit}" step="1" required value="${(!empty article.listeEncheres[0].montantEnchere?article.listeEncheres[0].montantEnchere:article.prixInitial) + 1}" />	
											</c:when>
											<c:otherwise>
												<input type="number" value="${(!empty article.listeEncheres[0].montantEnchere?article.listeEncheres[0].montantEnchere:article.prixInitial) + 1}" disabled />
												<div>Vous n'avez pas assez de points (actuellement ${utilisateur.credit} points) pour pouvoir enchérir sur cet objet.</div>
											</c:otherwise>
										</c:choose>
									</div>
								</div>	
							</div>
						</div>						
						<div class="row">	
							<div class="col-3 offset-3">
								<c:choose>	
									<c:when test="${!empty article.listeEncheres[0].montantEnchere?utilisateur.credit > article.listeEncheres[0].montantEnchere:utilisateur.credit > article.prixInitial}">										
										<button class="btn btn-light width-100 height-100" id="encherir" type="submit" value="${article.id}" name="encherir">Enchérir</button>							
									</c:when>
									<c:otherwise>
										<input class="width-100 height-100" id="encherir-disabled" type="submit" disabled />
									</c:otherwise>
								</c:choose>								
								
							</div>							
							<div class="col-3">
								<c:url var="servletAccueil" value="/accueil"/>
								<a class="btn btn-light width-100 height-100" id="retourDetailArticle" href="${servletAccueil}">Retour</a>
							</div>		
						</div>
					</form>				
				</c:when>
				
				<%--  Si l'utilisateur est le vendeur et que la vente n'a pas commencé, l'utilisateur peut modifier l'article ou le supprimer : boutons "modifier", "supprimer" et "retour"--%>
				<c:when test="${article.utilisateur.id == utilisateur.id && article.idEtatVente == 1}">
					<c:url var="servletVenteArticle" value="/venteArticle" />
					<form method="POST" action="${servletVenteArticle}">
						<div class="row">												
							<div class="col-4 col-md-2 offset-md-3">								
								<button class="btn btn-light width-100 height-100" id="modifierArticle" type="submit" name="idArticleAModifier" value="${article.id}">Modifier</button>
							</div>
							<div class="col-4 col-md-2">
								<button class="btn btn-light width-100 height-100" id="supprimerArticle" type="submit" name="idArticleASupprimer" value="${article.id}">Supprimer</button>
							</div>								
							<div class="col-4 col-md-2">
								<c:url var="servletAccueil" value="/accueil"/>
								<a class="btn btn-light width-100 height-100" id="retourDetailArticle" href="${servletAccueil}">Retour</a>
							</div>					
						</div>
					</form>
				</c:when>
				
				<%-- Si la vente est terminée, ou (l'utilisateur est le vendeur et la vente est en cours) : seule la consultation est possible. Seul bouton disponible : "retour" --%>
				<c:when test="${article.idEtatVente >= 3 || (article.utilisateur.id == utilisateur.id && article.idEtatVente == 2)}">
					<div class="row">
						<div class="col-md-2 offset-md-5">
							<c:url var="servletAccueil" value="/accueil"/>
							<a class="btn btn-light width-100 height-100" id="retourDetailArticle" href="${servletAccueil}">Retour</a>
						</div>
					</div>
				</c:when>
			
			</c:choose>	

		</div>
			
	</div>
</body>
</html>