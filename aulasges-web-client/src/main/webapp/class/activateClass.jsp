<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<!DOCTYPE html>
<!--  No scriptlets!!! 
	  See http://download.oracle.com/javaee/5/tutorial/doc/bnakc.html 
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="model" class="presentation.web.model.ActivateClassModel" scope="request"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/resources/app.css"> 
<title>AulaGes: ativar aula</title>
</head>
<body>
<h2>Ativar Aula</h2>
<form action="activateClass" method="post">
    <div class="mandatory_field">
    	<label for="name">Nome da aula:</label> 
    	<input type="text" name="name" size="50" value="${model.name}"/> 
    </div>
    <div class="mandatory_field">
    	<label for="beginDate">Data de inicio (yyyy-mm-dd):</label> 
    	<input type="date" name="beginDate" size="50" value="${model.beginDate}"/> 
    </div>
    <div class="mandatory_field">
    	<label for="endDate">Data de fim (yyyy-mm-dd):</label> 
    	<input type="date" name="endDate" size="50" value="${model.endDate}"/> 
    </div>
    <div class="mandatory_field">
    	<label for="max">Numero máximo de alunos:</label> 
    	<input type="text" name="max" size="50" value="${model.max}"/> 
    </div>
    <div class="mandatory_field">
		<label for="facility">Instalação:</label>
		<select name="facility">  
			<c:forEach var="facility" items="${model.facilities}">
				<c:choose>
    				<c:when test="${model.facility == facility.name}">
						<option selected = "selected" value="${facility.name}">${facility.name}</option>
				    </c:when>
    				<c:otherwise>
						<option value="${facility.name}">${facility.name}</option>
				    </c:otherwise>
					</c:choose>
			</c:forEach> 
		</select>
   </div>
    <div class="button" align="right">
   		<input type="submit" value="Activate Class">
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