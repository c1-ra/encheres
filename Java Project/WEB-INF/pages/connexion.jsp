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
			
			<c:if test="${!empty listeCodesErreur}">
				<div class="alert alert-danger" role="alert">
				  <strong>Vos informations de connexion sont incorrectes</strong>
				</div>
			</c:if>
			
			<c:url var="servletConnexion" value="/connexion" />
			<form method="POST" action="${servletConnexion}">
				<div class="row">
					<div class="col-5"><label for="login">Identifiant : </label></div>
					<div class="col-7"><input class="width-100" type="text" id="login" name="login" value="${utilisateurSaisie.pseudo}" /></div>
				</div>
				<div class="row">
					<div class="col-5"><label for="login">Mot de passe : </label></div>
					<div class="col-7"><input class="width-100" type="password" id="mdp" name="mdp" value="${utilisateurSaisie.motDePasse}" /></div>
				</div>
				<div class="row" id="connexion-row">
					<div class="col-6"><input class="width-100 height-100" id="connexion" name="connexion" type="submit" value="Connexion"></div>
					<%-- Pas mis en place --%>
					<%--<div class="col-6">
						<input type="checkbox" name="seSouvenir" id="seSouvenir" value="seSouvenir">
						<label for="seSouvenir">Se souvenir de moi</label>
						<br />
						<a href="#">Mot de passe oublié</a>	
					</div>--%>				
				</div>
			</form>
			
			<c:url var="servletCreationCompte" value="/creationCompte" />
			<a class="btn btn-light width-100 height-100" id="creaCompte" href="${servletCreationCompte}">Créer un compte</a>
							
		</div>
			
	</div>
</body>
</html>