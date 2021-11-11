<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
%>

<head>
	<link rel="stylesheet" href="Style/Admin.css">
</head>

<h1 id="header">
	<i class="fas fa-dice-d6"></i>
	Gestione del catalogo
</h1>

<div class="storeman-div">
    <div class="add-game">
        <form enctype="multipart/form-data" action="GameServlet" method="POST">
            <h2>Aggiungi gioco</h2>
            <input type="hidden" value="addGame" name="action">
            <input class="input"  type="text" name="game-name" placeholder="Inserisci il nome del gioco" required>
            <input class="input" type="number" name="game-price" placeholder="Inserisci il prezzo del gioco" required>
            <input class="input" type="text" name="game-date" placeholder="Inserisci la data (YYYY-MM-DD)" required>
            <textarea class="input" id="textarea-game" name="game-description" rows="4" cols="5" placeholder="Inserisci una descrizione del gioco"></textarea>

            <div class="a">
                <label for="game-image">Cover</label>
                <input class="input" id="image-loader" name="game-image" type="file" required>
            </div>

            <div class="a">
                <label for="game-landscape">Sfondo</label>
                <input class="input" id="landscape-loader" name="game-landscape" type="file" required>
            </div>

            <input class="button" type="submit" value="Aggiungi">
            <div id="add-game-result">${addGameResponse}</div>
        </form>
    </div>

    <div class="admin-game-cont">
        <div class="remove-game">
            <form action="GameServlet" method="POST">
                <h2>Elimina gioco</h2>
                <input class="input" id="delete-game-id" type="number" placeholder="Inserisci l'ID del gioco" style="margin-bottom: 10px;" required>
                <input type="hidden" name="action" value="deleteGame">
                <input onclick="tryDeleteGame(event)" class="button" type="submit" value="Elimina">
                <div id="delete-game-result"></div>
            </form>
        </div>

        <div class="update-game">
            <form action="GameServlet" method="POST">
                <h2>Aggiorna gioco</h2>
                <input class="input" id="update-game-id" type="number" placeholder="Inserisci l'ID del gioco" style="margin-bottom: 10px;" required>
                <input class="input" id="update-game-price" type="number" placeholder="Inserisci il nuovo prezzo" style="margin-bottom: 10px;" required>
                <input type="hidden" name="action" value="updateGame">
                <input onclick="tryUpdateGame(event)" class="button" type="submit" value="Aggiorna">
                <div id="update-game-result"></div>
            </form>
        </div>
    </div>
</div>

<h1 id="header">
	<i class="fas fa-info-circle"></i>
	Informazioni sui giochi
</h1>

<div class="table-storeman">
    <!-- AJAX_Components/GamesTable.jsp -->
</div>

<script src="Scripts/StoremanRoutines.js"></script>