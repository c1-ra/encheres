<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<%@ page import= "java.util.GregorianCalendar"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<%@ include file="../fragments/head.jsp" %>
<body>

	<div class="wrapper-bandeau">

		<c:url var="servletAccueil" value="/accueil" />
		<a class="logo" href="${servletAccueil}"><img id="logo" src="<c:url value="/ressources/img/logo.PNG" />"></a>
		
		<nav class="menu">
			<c:choose>
				<c:when test="${!empty utilisateur}">
					<div class="row">
					<%-- Pas encore implémenté 
						<c:url var="servletEnchere" value="#" />			
						<a class="menu-link" href="${servletEnchere}">Enchères</a>--%>
						<c:url var="servletVenteArticle" value="/venteArticle" />			
						<a class="menu-link" href="${servletVenteArticle}">Vendre un article</a>
						<c:url var="servletConsultationProfil" value="/consultationProfil" />
						<form id="monProfilForm"  class="post-btn" action="${servletConsultationProfil}" method="POST">
							<button id="monProfilLink" type="submit" name="consultationProfil" value="${utilisateur.id}" class="btn-link">Mon profil</button>
						</form>
						<c:url var="servletAccueilDeco" value="/accueil?deconnexion=true" />
						<a class="menu-link" href="${servletAccueilDeco}">Déconnexion</a>
					</div>				
				</c:when>
				<c:otherwise>
					<c:url var="servletConnexion" value="/connexion" />
					<a class="btn-c2 width-100 height-100" href="${servletConnexion}">S'inscrire - Se connecter</a>
				</c:otherwise>
			</c:choose>
		</nav>
	</div>
	
	
	<div id="filtresAccueil">
		
		<div class="wrapper wrapper-lg">
					
			<div class="container-fluid">
			
				<h1>Liste des enchères</h1>
					
					<%@ include file="../fragments/affichageErreurs.jsp" %>
					
					<div id="accueilForm">
						<c:url var="servletAccueil" value="/accueil" />
						<form method="GET" action="${servletAccueil}" id="filtresForm">			
							<div class="row">
								<div class="col-12 col-md-8 offset-md-2">
								Filtres :
									<div id="rechercheDiv">
										<c:choose>
											<c:when test="${!empty recherche && not(recherche eq '')}">
												<input class="width-100" type="text" id="recherche" name="recherche" value="${recherche}" />
											</c:when>
											<c:otherwise>
												<input class="width-100" type="text" id="recherche" name="recherche" placeholder="Le nom de l'article contient" />
											</c:otherwise>	
										</c:choose>
									</div>
								</div>
								<div class="col-4 col-md-2 offset-md-2">	
									<label for="categorie">Catégorie : </label>
								</div>
								<div class="col-8 col-md-6">	
									<select class="width-100" name="categorie" id="categorie">
										<fmt:parseNumber var="categorieInt" value="${categorie}" integerOnly="true"/>
										<option value="0" <c:if test="${empty categorie || categorie == 0}">selected</c:if>>Toutes</option>								
										<option value="1" <c:if test="${categorie == 1}">selected</c:if>>Ameublement</option>
										<option value="2" <c:if test="${categorie == 2}">selected</c:if>>Informatique</option>
										<option value="3" <c:if test="${categorie == 3}">selected</c:if>><c:out value="Sport&Loisirs"/></option>
										<option value="4" <c:if test="${categorie == 4}">selected</c:if>>Vêtement</option>
									</select>
								</div>
								
								<c:if test="${!empty utilisateur}">
								<%-- Dans l'ordre, les 6 choix possible sont numérotés de 0 à 5 : 0 à 2 : mode achats, 3 à 5 : mode vente --%>
									<div class="col-12 col-md-4 offset-md-2">
									<fmt:parseNumber var="modeInt" value="${mode}" integerOnly="true"/>
										<input type="radio" id="achats" name="filtre" value="achats" <c:if test="${empty mode || modeInt < 3}">checked</c:if>/>
										<label for="achats">Achats</label>
										<ul>
											<li>
												<input type="radio" id="encheresOuvertes" value="0" name="radioAchats" <c:if test="${modeInt >= 3}">disabled</c:if> <c:if test="${empty mode || modeInt == 0}">checked</c:if>>
												<label for="encheresOuvertes">enchères ouvertes</label>
											</li>
											<li>
												<input type="radio" id="mesEncheresEnCours" value="1" name="radioAchats" <c:if test="${modeInt >= 3}">disabled</c:if> <c:if test="${modeInt == 1}">checked</c:if>>
												<label for="mesEncheresEnCours">mes enchères en cours</label>
											</li>
											<li>
												<input type="radio" id="mesEncheresRemportees" value="2" name="radioAchats" <c:if test="${modeInt >= 3}">disabled</c:if> <c:if test="${modeInt == 2}">checked</c:if>>
												<label for="mesEncheresRemportees">mes enchères remportées</label>
											</li>									
										</ul>								
									</div>
									<div class="col-12 col-md-4">
										<input type="radio" id="ventes" name="filtre" value="ventes" <c:if test="${modeInt >= 3}">checked</c:if>/>
										<label for="ventes">Mes ventes</label>
										<ul>
											<li>
												<input type="radio" id="mesVentesEnCours" value="3" name="radioVentes" <c:if test="${empty mode || modeInt < 3}">disabled</c:if> <c:if test="${modeInt == 3}">checked</c:if>>
												<label for="mesVentesEnCours">mes ventes en cours</label>
											</li>
											<li>
												<input type="radio" id="ventesNonDebutees" value="4" name="radioVentes" <c:if test="${empty mode || modeInt < 3}">disabled</c:if> <c:if test="${modeInt == 4}">checked</c:if>>
												<label for="ventesNonDebutees">ventes non débutées</label>
											</li>
											<li>
												<input type="radio" id="ventesTerminees" value="5" name="radioVentes" <c:if test="${empty mode || modeInt < 3}">disabled</c:if> <c:if test="${modeInt == 5}">checked</c:if>>
												<label for="ventesTerminees">ventes terminées</label>
											</li>									
										</ul>	
									</div>
								</c:if>												
							</div>
							<div class="row">
								<div class="col-12 col-md-2 offset-md-5">
									<input class="height-100 width-100" type="submit" name="rechercher" id="rechercher" value="Rechercher">
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		
		<div class="wrapper wrapper-lg">
					
			<div class="container-fluid">
				
				<c:choose>
					
					<c:when test="${!empty utilisateur}">			
						<div class="row">
							<c:forEach var="art" items="${listeAffichage}">
								<div class="col-12 col-md-6">
									<div class="card">
										<div class="card-horizontal">
											<div class="card-body">
												<div class="row">
													<div class="col-12 col-sm-3 col-md-6 img">img</div>
													<div class="col-12 col-sm-9 col-md-6">
														<c:url var="servletDetailArticle" value="/detailArticle" />
														<form class="art-link" action="${servletDetailArticle}" method="POST">											
															<div class="card-title"><button type="submit" name="detailArticle" value="${art.id}" class="btn-link art-btn-link">${art.nom}</button></div>	
														</form>	
														<div class="card-text">
															<div>Prix : ${art.prixVente} points</div>
															<div>
																Fin de l'enchère : 
																<fmt:parseDate  value="${art.dateFin}"  type="date" pattern="yyyy-MM-dd" var="parsedDateFin" />
																<fmt:formatDate value="${parsedDateFin}" type="date" pattern="dd/MM/yyyy" />
															</div>
															<c:url var="servletConsultationProfil" value="/consultationProfil" />
															<form class="art-link" action="${servletConsultationProfil}" method="POST">											
																<div>Vendeur : <button type="submit" name="consultationProfil" value="${art.utilisateur.id}" class="btn-link vendeur-btn-link">${art.utilisateur.pseudo}</button></div>	
															</form>	
														</div>
													</div>
												</div>
											</div>
										</div>							
									</div>
								</div>
							</c:forEach>
						</div>				
					</c:when>
					
					<c:otherwise>
						<div class="row">
							<c:forEach var="art" items="${listeAffichage}">
								<div class="col-12 col-md-6">
									<div class="card">										
										<div class="card-horizontal">
											<div class="card-body">
												<div class="row">
													<div class="col-12 col-sm-3 col-md-6 img">img</div>
													<div class="col-12 col-sm-9 col-md-6">									
														<div class="card-body">
															<div class="card-title">${art.nom}</div>
															<div class="card-text">
																<div>Prix : ${art.prixVente} points</div>
																<div>
																	Fin de l'enchère : 
																	<fmt:parseDate  value="${art.dateFin}"  type="date" pattern="yyyy-MM-dd" var="parsedDateFin" />
																	<fmt:formatDate value="${parsedDateFin}" type="date" pattern="dd/MM/yyyy" />
																</div>
																<div>Vendeur : ${art.utilisateur.pseudo}</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>					
									</div>
								</div>
							</c:forEach>
						</div>
					</c:otherwise>
				
				</c:choose>

					
		</div>	
	</div>

<!---------------------- Script(s) ---------------------->

	<script>
	/* Nécessaire pour que le dropdown menu et le champ de recherche s'actualisent lorsque l'utilisateur utilise le bouton back*/
		window.addEventListener( "pageshow", function ( event ) {
			  var historyTraversal = event.persisted || 
			                         ( typeof window.performance != "undefined" && 
			                              window.performance.navigation.type === 2 );
			  if ( historyTraversal ) {
			    
			    /* option 1 : pour que toute la page se ré-actualise (et que la requête vers la base de données soit de nouveau exécutée (passage par la servlet)) */
			    /* Si utilisation de option 1, également décommenter la dernière ligne de cette balise <script> */
			   	window.location.reload(true);
			    
			    /* option 2 : pour que seuls le dropdown menu et le champ de recherche s'actualisent mais que les requêtes vers la base de données ne soient pas ré-envoyées (pas de passage par la servlet) */
			    /* Si utilisation de option 2, commenter la dernière ligne de cette balise <script> */
			    /*var containerCat = document.getElementById("categorie");
			    var contentCat = containerCat.innerHTML;
			    containerCat.innerHTML= contentCat;
			    var containerRecherche = document.getElementById("rechercheDiv");
			    var contentRecherche = containerRecherche.innerHTML;
			    containerRecherche.innerHTML= contentRecherche;*/
			  }
			});
	
			/* option 1 : Nécessaire pour que toute la page se ré-actualise sous Firefox (pas nécessaire, à commenter si on souhaite juste actualiser le dropdown menu et le champ de recherche : option 2) */
			window.onunload = function(){};
	</script>

	<script>
	
		/* Script pour disable les radio button de sous-menu des modes achats et ventes lorsque ceux-ci sont dé-sélectionnés, et inversement */
		var radioAchats = document.getElementById("achats");
		var radioVentes = document.getElementById("ventes");
		
		var checkboxAchats1 = document.getElementById("encheresOuvertes");
		var checkboxAchats2 = document.getElementById("mesEncheresEnCours");
		var checkboxAchats3 = document.getElementById("mesEncheresRemportees");
		
		var checkboxVentes1 = document.getElementById("mesVentesEnCours");
		var checkboxVentes2 = document.getElementById("ventesNonDebutees");
		var checkboxVentes3 = document.getElementById("ventesTerminees");
		
		changeDisabledState = () => {
			if (radioAchats.checked == true) {
				checkboxVentes1.checked = false;
				checkboxVentes2.checked = false;
				checkboxVentes3.checked = false;
				
				checkboxVentes1.disabled = true;
				checkboxVentes2.disabled = true;
				checkboxVentes3.disabled = true;
				
				checkboxAchats1.disabled = false;
				checkboxAchats2.disabled = false;
				checkboxAchats3.disabled = false;
				
				checkboxAchats1.checked = true;
			} else {
				checkboxAchats1.checked = false;
				checkboxAchats2.checked = false;
				checkboxAchats3.checked = false;
				
				checkboxAchats1.disabled = true;
				checkboxAchats2.disabled = true;
				checkboxAchats3.disabled = true;
				
				checkboxVentes1.disabled = false;
				checkboxVentes2.disabled = false;
				checkboxVentes3.disabled = false;
				
				checkboxVentes1.checked = true;
			}
		};
		
		radioAchats.addEventListener("click", changeDisabledState);
		radioVentes.addEventListener("click", changeDisabledState);

	</script>

</body>
</html>