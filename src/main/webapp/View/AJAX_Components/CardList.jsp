<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="card-tip">Ricarica il tuo saldo utilizzando una carta registrata, oppure aggiungine una nuova.</div>

<div id="cards-container">
    <c:forEach items="${cards}" var="parsed">
        <div
            class="valid-credit-card credit-card ${parsed.card.card_type}"
        >
            <div>
                <h1>${parsed.card.card_type}</h1>
                <h2>${parsed.card.card_number}</h2>
                <h3>${parsed.card.card_owner}</h2>
                <h4>${parsed.card.card_date}</h2>
            </div>
        </div>
    </c:forEach>
    <div class="credit-card credit-card-add"> + </div>
</div>

<script src="Scripts/CardsListRoutines.js"></script>