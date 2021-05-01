<c:if test="${!empty listeCodesErreur}">
	<div class="alert alert-danger" role="alert">
	  <strong>Erreur!</strong>
	  <ul>
	  	<c:forEach var="code" items="${listeCodesErreur}">
	  		<li>${LecteurMessage.getMessageErreur(code)}</li>
	  	</c:forEach>
	  </ul>
	</div>
</c:if>