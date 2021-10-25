<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table id="cards-table">
	<tr class="table-header">
		<td>Circuito</td>
		<td>Numero</td>
		<td>Titolare</td>
		<td>Scadenza</td>
	</tr>
	<c:forEach items="${cards}" var="card">
        <tr>
            <td>${card.card_type}</td>
            <td>${card.card_number}</td>
            <td>${card.card_owner}</td>
            <td>${card.card_date}&euro;</td>
        </tr>
    </c:forEach>
</table>


<script src="Scripts/CardsTable.js"></script>