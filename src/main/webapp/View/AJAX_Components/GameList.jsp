<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
%>

<c:forEach items="${games}" var="game">
	<div 
		data-game-id="${game.id}" 
		data-game-name="${game.name}" 
		data-game-price="${game.price}" 
		style="background-image: url('Static/GamePictures/${game.image}')" 
		class="game-container"
	>
		<div class="game-add">+</div>
		<div class="game-overlay">
			${game.name} <span class="game-price">&bull; ${game.price}&euro;</span>
		</div>
	</div>
</c:forEach>

<script src="Scripts/GameListRoutines.js"></script>