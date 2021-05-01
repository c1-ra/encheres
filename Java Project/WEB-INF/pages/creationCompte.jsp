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
		
			<h1>Créer un compte</h1>

			<%@ include file="../fragments/affichageErreurs.jsp" %>
			
			<c:url var="servletCreationCompte" value="/creationCompte" />
			<form method="POST" action="${servletCreationCompte}">
				<div class="row">
					<div class="col-4 col-md-2"><label for="pseudo">Pseudo* : </label></div>
					<div class="col-8 col-md-4"><input class="width-100" type="text" id="pseudo" name="pseudo" required value="${utilisateurSaisie.pseudo}" /></div>
					<div class="col-4 col-md-2"><label for="nom">Nom* : </label></div>
					<div class="col-8 col-md-4"><input class="width-100" type="text" id="nom" name="nom" required value="${utilisateurSaisie.nom}" /></div>
				</div>
				<div class="row">
					<div class="col-4 col-md-2"><label for="prenom">Prénom* : </label></div>
					<div class="col-8 col-md-4"><input class="width-100" type="text" id="prenom" name="prenom" required value="${utilisateurSaisie.prenom}" /></div>
					<div class="col-4 col-md-2"><label for="email">Email* : </label></div>
					<div class="col-8 col-md-4"><input class="width-100" type="email" id="email" name="email" required value="${utilisateurSaisie.email}" /></div>					
				</div>
				<div class="row">
					<div class="col-4 col-md-2"><label for="telephone">Téléphone : </label></div>
					<div class="col-8 col-md-4"><input class="width-100" type="tel" id="telephone" name="telephone" value="${utilisateurSaisie.telephone}" /></div>
					<div class="col-4 col-md-2"><label for="rue">Rue* : </label></div>
					<div class="col-8 col-md-4"><input class="width-100" type="text" id="rue" name="rue" required value="${utilisateurSaisie.rue}" /></div>					
				</div>
				<div class="row">
					<div class="col-4 col-md-2"><label for="codePostal">Code postal* : </label></div>
					<div class="col-8 col-md-4"><input class="width-100" type="text" id="codePostal" name="codePostal" required value="${utilisateurSaisie.codePostal}" /></div>
					<div class="col-4 col-md-2"><label for="ville">Ville* : </label></div>
					<div class="col-8 col-md-4"><input class="width-100" type="text" id="ville" name="ville" required value="${utilisateurSaisie.ville}" /></div>					
				</div>
				<div class="row">
					<div class="col-4 col-md-2"><label for="motDePasse">Mot de passe* : </label></div>
					<div class="col-8 col-md-4"><input class="width-100" type="password" id="motDePasse" name="motDePasse" value="" /></div>
					<div class="col-4 col-md-2"><label for="confMotDePasse">Confirmation* : </label></div>
					<div class="col-8 col-md-4"><input class="width-100" type="password" id="confMotDePasse" name="confMotDePasse" value="" /></div>					
				</div>
				<div class="row" id="boutons-crea-row">
					<div class="col-12 col-md-4 offset-md-2">
						<input class="height-100 width-100" type="submit" id="creationCompte" name="creationCompte" value="Créer" />
					</div>
					<div class="col-12 col-md-4" id="btn-link-div">
					<c:url var="servletAccueil" value="/accueil" />
						<a class="btn btn-light width-100" id="annulerCreaCompte" href="${servletAccueil}">Annuler</a>
					</div>
				</div>
				
			</form>

		</div>
			
	</div>
</body>
</html>