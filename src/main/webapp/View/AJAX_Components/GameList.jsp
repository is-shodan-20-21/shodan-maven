<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
%>

<c:forEach items="${source}" var="source">
	<div 
		data-game-id="${source.game.id}" 
		data-game-name="${source.game.name}" 
		data-game-price="${source.game.price}"
		style="background-image: url('Static/GamePictures/${source.game.image}')" 
		class="game-container"
	>
		<div 
			class="game-add"
			data-game-owned="${source.owned}" 
		>
			+
		</div>
		<div class="game-overlay">
			${source.game.name} <span class="game-price">&bull; ${source.game.price}&euro;</span>
		</div>
	</div>
</c:forEach>

<script src="Scripts/GameListRoutines.js"></script>