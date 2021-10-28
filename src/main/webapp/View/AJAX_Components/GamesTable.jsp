<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
%>
<table class="table">
    <tr class="table-title">
        <th>ID gioco</th>
        <th>Nome gioco</th>
        <th>Prezzo gioco</th>
        <th>Data di rilascio</th>
        <th>Immagine gioco</th>
        <th>Copertina gioco</th>
    </tr>
    
    <c:forEach items="${gameList}" var="game">
        <tr class="table-row">
            <td class="table-data">${game.id}</td>
            <td class="table-data">${game.name}</td>
            <td class="table-data">${game.price}</td>
            <td class="table-data">${game.release}</td>
            <td class="table-data">${game.image}</td>
            <td class="table-data">${game.landscape}</td>
        </tr>
     </c:forEach>
    
</table>
