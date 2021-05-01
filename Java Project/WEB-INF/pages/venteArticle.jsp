<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%@ include file="../fragments/head.jsp" %>
<body>

	<%@ include file="../fragments/bandeau.jsp" %>

	<div class="wrapper wrapper-md">

		<div class="container-fluid">
		
			<h1>Vente d'un article</h1>

			<%@ include file="../fragments/affichageErreurs.jsp" %>
			
			<c:url var="servletVenteArticle" value="/venteArticle" />
			<form method="POST" action="${servletVenteArticle}">
				<div class="row">
					<div class="col-4"><label for="nom">Article : </label></div>
					<div class="col-8"><input class="width-100" type="text" id="nom" name="nom" required value="${article.nom}" /></div>	
				</div>
				<div class="row">
					<div class="col-4"><label for="cate">Catégorie : </label></div>
					<div class="col-8">
						<select class="width-100" name="cate" id="cate" required>
							<option value="1" <c:if test="${article.categorie.id == 1}">selected</c:if>>Ameublement</option>
							<option value="2"<c:if test="${article.categorie.id == 2}">selected</c:if>>Informatique</option>
							<option value="3"<c:if test="${article.categorie.id == 3}">selected</c:if>><c:out value="Sport&Loisirs"/></option>
							<option value="4"<c:if test="${article.categorie.id == 4}">selected</c:if>>Vêtement</option>
						</select>
					</div>		
				</div>
				<div class="row">
					<div class="col-4"><label for="description">Description : </label></div>
					<div class="col-8"><textarea id="description" name="description" class="width-100" required>${article.description}</textarea></div>
				</div>
				
				<div class="row">
					<div class="col-6"><label for="prixInitial">Prix initial : </label></div>
					<div class="col-6"><input  type="number" id="prixInitial" name="prixInitial" min="1" step="1" required value="${!empty article?article.prixInitial:1}" /></div>	
				</div>
				<div class="row">
					<div class="col-6"><label for="dateDebut">Début de l'enchère : </label></div>
					<div class="col-6"><input class="width-100" type="date" id="dateDebut" name="dateDebut" required value="${article.dateDebut}" /></div>	
				</div>
				<div class="row">
					<div class="col-6"><label for="dateFin">Fin de l'enchère : </label></div>
					<div class="col-6"><input class="width-100" type="date" id="dateFin" name="dateFin" required value="${article.dateFin}" /></div>	
				</div>
	
				<div class="row">
					<div class="col-12">
						<fieldset class="width-100">
			    			<legend>Retrait</legend>
			    			<div class="row">
			    				<div class="col-6"><label for="rue">Rue : </label></div>
			    				<div class="col-6"><input class="width-100" type="text" id="rue" name="rue" required value="${!empty article.retrait.rue?article.retrait.rue:utilisateur.rue}" /></div>
			    			</div>
			    			<div class="row">
			    				<div class="col-6"><label for="codePostal">Code postal : </label></div>
			    				<div class="col-6"><input class="width-100" type="text" id="codePostal" name="codePostal" required value="${!empty article.retrait.codePostal?article.retrait.codePostal:utilisateur.codePostal}" /></div>
			    			</div>
			    			<div class="row">
			    				<div class="col-6"><label for="ville">Ville : </label></div>
			    				<div class="col-6"><input class="width-100" type="text" id="ville" name="ville" required value="${!empty article.retrait.ville?article.retrait.ville:utilisateur.ville}" /></div>
			    			</div>
						</fieldset>
					</div>
				</div>	
				
				<div class="row">
					<div class="col-md-4 offset-md-2">
						<button class="btn btn-light width-100 height-100" id="enregistrerArticle" type="submit" value="${article.id}" name="enregistrer">Enregistrer</button>
					</div>
					<div class="col-md-4">
						<c:url var="servletAccueil" value="/accueil"/>
						<a class="btn btn-light width-100 height-100" id="annulerEnregistrerArticle" href="${servletAccueil}">Annuler</a>
					</div>
				</div>
					
			</form>

		</div>
			
	</div>
</body>
</html>