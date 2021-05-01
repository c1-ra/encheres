<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%@ include file="../fragments/head.jsp" %>
<body>

	<%@ include file="../fragments/bandeau.jsp" %>

	<div class="wrapper wrapper-sm">

		<div class="container-fluid">
		
		<%@ include file="../fragments/affichageErreurs.jsp" %>

				<div class="row">
					<div class="col-6">Pseudo : </div>
					<div class="col-6">${!empty profil.pseudo?profil.pseudo:utilisateur.pseudo}</div>
				</div>
				<div class="row">
					<div class="col-6">Nom : </div>
					<div class="col-6">${!empty profil.nom?profil.nom:utilisateur.nom}</div>
				</div>
				<div class="row">
					<div class="col-6">Prénom : </div>
					<div class="col-6">${!empty profil.prenom?profil.prenom:utilisateur.prenom}</div>
				</div>
				<div class="row">
					<div class="col-6">Email : </div>
					<div class="col-6">${!empty profil.email?profil.email:utilisateur.email}</div>					
				</div>
				<div class="row">
					<div class="col-6">Téléphone : </div>
					<div class="col-6">${!empty profil.telephone?profil.telephone:utilisateur.telephone}</div>
				</div>
				<div class="row">
					<div class="col-6">Rue : </div>
					<div class="col-6">${!empty profil.rue?profil.rue:utilisateur.rue}</div>					
				</div>
				<div class="row">
					<div class="col-6">Code postal : </div>
					<div class="col-6">${!empty profil.codePostal?profil.codePostal:utilisateur.codePostal}</div>
				</div>
				<div class="row">
					<div class="col-6">Ville : </div>
					<div class="col-6">${!empty profil.ville?profil.ville:utilisateur.ville}</div>					
				</div>
				<c:if test="${!empty monProfil}">
					<div class="row">
						<div class="col-6">Points : </div>
						<div class="col-6">${utilisateur.credit}</div>		
					</div>
				</c:if>
				
				
				<div class="row">
					<c:if test="${!empty monProfil}">
						<div class="col-12 col-md-5 offset-md-1">
							<c:url var="servletModificationProfil" value="/modificationProfil" />
							<form class="post-btn" action="${servletModificationProfil}" method="POST">
								<button id="modifierProfil" type="submit" name="modifierMonProfil" value="${utilisateur.id}" class="btn btn-light width-100 height-100">Modifier</button>
							</form>
						</div>
					</c:if>
					<div class="col-12 col-md-5">
						<c:url var="servletAccueil" value="/accueil"/>
						<a class="btn btn-light width-100 height-100" id="retourProfil" href="${servletAccueil}">Revenir à l'accueil</a>
					</div>
				</div>
							
		</div>
			
	</div>
	
</body>
</html>