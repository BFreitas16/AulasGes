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

	<h3>Inscrição:</h3>
	<form action="enrollClass" method="post">
		<div class="mandatory_field">
			<label for="consumerNumber">Numero de utente:</label> <input
				type="text" name="consumerNumber" size="50"
				value="${model.consumerNumber}" />
		</div>
		<div class="mandatory_field">
			<label for="classToEnroll">Aula:</label> <select name="classToEnroll">
				<c:forEach var="classToEnroll" items="${model.classes}">
					<c:choose>
						<c:when test="${model.classToEnroll == classToEnroll.name}">
							<option selected="selected" value="${classToEnroll.name}">${classToEnroll.name}</option>
						</c:when>
						<c:otherwise>
							<option value="${classToEnroll.name}">${classToEnroll.name}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>
		<div style="display: none;">
			<input type="text" name="modality" size="50"
				value="${model.modality}"/>
		</div>
		<div style="display: none;">
			<input type="text" name="subscription" size="50"
				value="${model.subscription}" />
		</div>
		<div class="button" align="right">
			<input type="submit" value="Enroll Class">
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