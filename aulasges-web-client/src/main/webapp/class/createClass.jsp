<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<!DOCTYPE html>
<!--  No scriptlets!!! 
	  See http://download.oracle.com/javaee/5/tutorial/doc/bnakc.html 
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="model" class="presentation.web.model.CreateClassModel" scope="request"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/resources/app.css"> 
<title>AulaGes: criar aula</title>
</head>
<body>
<h2>Criar Aula</h2>
<form action="createClass" method="post">
    <div class="mandatory_field">
    	<label for="name">Nome da aula:</label> 
    	<input type="text" name="name" size="50" value="${model.name}"/> 
    </div>
    <div class="mandatory_field">
    	<label for="beginTime">Hora de inicio (hh:mm:ss):</label> 
    	<input type="text" name="beginTime" size="50" value="${model.beginTime}"/> 
    </div>
    <div class="mandatory_field">
    	<label for="duration">Duração da aula:</label> 
    	<input type="text" name="duration" size="50" value="${model.duration}"/> 
    </div>
    <div class="mandatory_field">
        <input type="checkbox" id="monday" name="daysOfWeek" value="MONDAY"> Monday
        <input type="checkbox" id="tuesday" name="daysOfWeek" value="TUESDAY"> Tuesday
        <input type="checkbox" id="wednesday" name="daysOfWeek" value="WEDNESDAY"> Wednesday
        <input type="checkbox" id="thursday" name="daysOfWeek" value="THURSDAY"> Thursday
        <input type="checkbox" id="friday" name="daysOfWeek" value="FRIDAY"> Friday
        <input type="checkbox" id="saturday" name="daysOfWeek" value="SATURDAY"> Saturday
        <input type="checkbox" id="sunday" name="daysOfWeek" value="SUNDAY"> Sunday
    </div>
    <div class="mandatory_field">
		<label for="modality">Instalação:</label>
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
    <div class="button" align="right">
   		<input type="submit" value="Create Class">
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