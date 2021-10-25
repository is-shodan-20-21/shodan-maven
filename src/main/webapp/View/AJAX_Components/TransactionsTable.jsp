<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table id="transactions-table">
    <tr class="table-header">
        <td>Operazione</td>
        <td>Titolo</td>
        <td>Data</td>
        <td>Costo</td>
    </tr>
    <c:forEach items="${transactions}" var="transaction">
        <tr>
            <td>Acquisto</td>
            <td class="transaction-link">
                <span data-game-id="${transaction.game.id}">${transaction.game.name}</span>
            </td>
            <td>${transaction.transaction_date}</td>
            <td>${transaction.game.price}&euro;</td>
        </tr>
    </c:forEach>
</table>

<script src="Scripts/TransactionsTableRoutines.js"></script>