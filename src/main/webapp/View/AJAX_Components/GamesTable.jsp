<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
%>

<table class="table">
    <tr class="table-title">
        <th>ID</th>
        <th>Nome</th>
        <th>Prezzo</th>
        <th>Rilascio</th>
        <th>Cover</th>
        <th>Landscape</th>
    </tr>
    
    <c:forEach items="${gameList}" var="game">
        <tr class="table-row">
            <td class="table-data">${game.id}</td>
            <td class="table-data">${game.name}</td>
            <td class="table-data">${game.price}&euro;</td>
            <td class="table-data">${game.release}</td>
            <td class="table-data">${game.image}</td>
            <td class="table-data">${game.landscape}</td>
        </tr>
     </c:forEach>
</table>
