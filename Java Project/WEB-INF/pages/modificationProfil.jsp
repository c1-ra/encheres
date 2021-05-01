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
		
			<h1>Mon profil</h1>

			<%@ include file="../fragments/affichageErreurs.jsp" %>
			
			<c:url var="servletModificationProfil" value="/modificationProfil" />
			<form method="POST" action="${servletModificationProfil}">
				<div class="row">
					<div class="col-4 col-md-2"><label for="pseudo">Pseudo* : </label></div>
					<div class="col-8 col-md-4"><input class="width-100" type="text" id="pseudo" name="pseudo" required value="${!empty utilisateurSaisie.pseudo?utilisateurSaisie.pseudo:utilisateur.pseudo}" /></div>
					<div class="col-4 col-md-2"><label for="nom">Nom* : </label></div>
					<div class="col-8 col-md-4"><input class="width-100" type="text" id="nom" name="nom" required value="${!empty utilisateurSaisie.nom?utilisateurSaisie.nom:utilisateur.nom}" /></div>
				</div>
				<div class="row">
					<div class="col-4 col-md-2"><label for="prenom">Prénom* : </label></div>
					<div class="col-8 col-md-4"><input class="width-100" type="text" id="prenom" name="prenom" required value="${!empty utilisateurSaisie.prenom?utilisateurSaisie.prenom:utilisateur.prenom}" /></div>
					<div class="col-4 col-md-2"><label for="email">Email* : </label></div>
					<div class="col-8 col-md-4"><input class="width-100" type="email" id="email" name="email" required value="${!empty utilisateurSaisie.email?utilisateurSaisie.email:utilisateur.email}" /></div>					
				</div>
				<div class="row">
					<div class="col-4 col-md-2"><label for="telephone">Téléphone : </label></div>
					<div class="col-8 col-md-4"><input class="width-100" type="tel" id="telephone" name="telephone" value="${!empty utilisateurSaisie.telephone?utilisateurSaisie.telephone:utilisateur.telephone}" /></div>
					<div class="col-4 col-md-2"><label for="rue">Rue* : </label></div>
					<div class="col-8 col-md-4"><input class="width-100" type="text" id="rue" name="rue" required value="${!empty utilisateurSaisie.rue?utilisateurSaisie.rue:utilisateur.rue}" /></div>					
				</div>
				<div class="row">
					<div class="col-4 col-md-2"><label for="codePostal">Code postal* : </label></div>
					<div class="col-8 col-md-4"><input class="width-100" type="text" id="codePostal" name="codePostal" required value="${!empty utilisateurSaisie.codePostal?utilisateurSaisie.codePostal:utilisateur.codePostal}" /></div>
					<div class="col-4 col-md-2"><label for="ville">Ville* : </label></div>
					<div class="col-8 col-md-4"><input class="width-100" type="text" id="ville" name="ville" required value="${!empty utilisateurSaisie.ville?utilisateurSaisie.ville:utilisateur.ville}" /></div>					
				</div>
				<div class="row">
					<div class="col-4 col-md-2"><label for="motDePasse">Mot de passe* : </label></div>
					<div class="col-8 col-md-4"><input class="width-100" type="password" id="motDePasse" name="motDePasse" required value="${!empty utilisateurSaisie.motDePasse?utilisateurSaisie.motDePasse:utilisateur.motDePasse}" /></div>
					<div class="col-4 col-md-2"><label for="confMotDePasse">Confirmation* : </label></div>
					<div class="col-8 col-md-4"><input class="width-100" type="password" id="confMotDePasse" name="confMotDePasse" required value="${!empty confMotDePasse?confMotDePasse:utilisateur.motDePasse}" /></div>					
				</div>
				
				<div class="row">
					<div class="col-4 col-md-2">Crédit :</div>
					<div class="col-8 col-md-4">${utilisateur.credit}</div>
				</div>
				
				<div class="row">
					<div class="col-12 col-md-4"><button class="btn btn-light width-100 height-100" id="enregistrerModifProfil" type="submit" name="enregistrer">Enregistrer</button></div>
					<div class="col-12 col-md-4"><button class="btn btn-light width-100 height-100" id="supprimerProfil" type="submit" name="supprimer" value="${utilisateur.id}">Supprimer mon compte</button></div>
					<c:url var="servletAccueil" value="/accueil" />
					<div class="col-12 col-md-4"><a class="btn btn-light width-100 height-100" id="retourModifProfil" href="${servletAccueil}">Retour</a></div>
				</div>
				
			</form>

		</div>
			
	</div>
</body>
</html>