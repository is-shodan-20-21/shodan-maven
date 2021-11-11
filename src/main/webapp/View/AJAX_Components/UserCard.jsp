<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="info-container">
    <div class="user-data">
        <h1>${user.name}</h1>
    </div>

    <div class="user-cash">
        <h3>
            <i class="fas fa-wallet"></i>
            Saldo: 
            <span>
                ${user.money}&euro;
            </span>
        </h3>
        <div id="add-card" class="button button--submit button--unshadow">Ricarica il saldo</div>
    </div>
</div>

<script src="Scripts/UserCardRoutines.js"></script>