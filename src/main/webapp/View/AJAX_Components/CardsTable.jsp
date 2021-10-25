<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table id="cards-table">
	<tr class="table-header">
		<td>Circuito</td>
		<td>Numero</td>
		<td>Titolare</td>
		<td>Scadenza</td>
	</tr>
	<c:forEach items="${collection}" var="parsed">
        <tr>
            <td>${parsed.card.card_type}</td>
            <td>${parsed.safe_digits}
				&bull;&bull;&bull;&bull;
				&bull;&bull;&bull;&bull;
				&bull;&bull;&bull;&bull;
			</td>
            <td>${parsed.card.card_owner}</td>
            <td>${parsed.card.card_date}</td>
        </tr>
    </c:forEach>
</table>

<script src="Scripts/CardsTable.js"></script>