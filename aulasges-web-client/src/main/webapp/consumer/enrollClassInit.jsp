<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<!DOCTYPE html>
<!--  No scriptlets!!! 
	  See http://download.oracle.com/javaee/5/tutorial/doc/bnakc.html 
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="model" class="presentation.web.model.EnrollClassModel" scope="request"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/resources/app.css"> 
<title>AulaGes: Inscrever em aula</title>
</head>
<body>
<h2>Inscrever em Aula</h2>
<h3>Escolha a inscrição:</h3>
<form action="enrollClassInit" method="post">
    <div class="mandatory_field">
		<label for="modality">Modalidade:</label>
		<select name="modality">  
			<c:forEach var="modality" items="${model.modalities}">
				<c:choose>
    				<c:when test="${model.modality == modality.name}">
						<option selected = "selected" value="${modality.name}">${modality.name}</option>
				    </c:when>
    				<c:otherwise>
						<option value="${modality.name}">${modality.name}</option>
				    </c:otherwise>
					</c:choose>
			</c:forEach> 
		</select>
   </div>
    <div class="mandatory_field">
		<label for="subscription">Tipo de subscrição:</label>
		<select name="subscription">  
			<c:forEach var="subscription" items="${model.subscriptions}">
				<c:choose>
    				<c:when test="${model.subscription == subscription}">
						<option selected = "selected" value="${subscription}">${subscription}</option>
				    </c:when>
    				<c:otherwise>
						<option value="${subscription}">${subscription}</option>
				    </c:otherwise>
					</c:choose>
			</c:forEach> 
		</select>
   </div>
    <div class="button" align="right">
   		<input type="submit" value="Get Classes">
   	</div>
</form>
<form action="/aulasges-web-client" method="get">
<div class="button" align="right">
	<input type="submit" value="Home">
</div>
</form>
<c:if test="${model.hasMessages}">
	<p>Mensagens</p>
	<ul>
	<c:forEach var="mensagem" items="${model.messages}">
		<li>${mensagem} 
	</c:forEach>
	</ul>
</c:if>
</body>
</html>