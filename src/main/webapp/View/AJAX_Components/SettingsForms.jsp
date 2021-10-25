<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="shodan-form-container bordered">
    <form id="settings-form-info" onsubmit="return false">
        <!-- AJAX_Components/UserCard.jsp -->
    </form>

    <form onsubmit="tryPasswordChange(); return false" class="psw-form">
        <div class="settings-start">
            <h2>Cambia la password</h2>
            <input id="settings-input-old-password" type="password" required placeholder="Password attuale">
            <input id="settings-input-new-password" type="password" required placeholder="Nuova password">
            <input id="settings-input-new-password-again" type="password" required placeholder="Ripeti la password">
        </div>

        <div class="settings-end">
            <input id="settings-submit-password" class="button button--submit" type="submit">
        </div>

        <div id="password-change-message"></div>
    </form>

    <form onsubmit="tryEmailChange(); return false" class="email-form">
        <div class="settings-start">
            <h2>Cambia l'email</h2>
            <input id="settings-input-email" type="email" required placeholder="email@dominio.eu">
        </div>
        
        <div class="settings-end">
            <input id="settings-submit-email" class="button button--submit" type="submit">
        </div>

        <div id="email-change-message"></div>
    </form>
</div>

<script src="Scripts/SettingsFormsRoutines.js"></script>