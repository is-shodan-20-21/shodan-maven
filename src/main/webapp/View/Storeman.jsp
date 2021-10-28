<head>
	<link rel="stylesheet" href="Style/Admin.css">
</head>

<h1 id="header">
	<i class="fas fa-dice-d6"></i>
	Cataloghista
</h1>

<div class="storeman-div">
    <div class="add-game">
        <form enctype="multipart/form-data" action="GameServlet" method="POST">
            <h2>Aggiungi gioco</h2>
            <input class="input" name="game-name" type="text" placeholder="Inserisci il nome del gioco" required>
            <input class="input" name="game-price" type="number" placeholder="Inserisci il prezzo del gioco" required>
            <input class="input" id="date-input" name="game-date" type="date" required>
            <textarea class="input" id="textarea-game" name="game-description" rows="4" cols="5">Inserisci una descrizione del gioco</textarea>
            <input class="input" id="file-loader" name="game-image" type="file" required>
            <input type="hidden" name="action" value="addGame">
            <input class="button" type="submit" value="Aggiungi" style="margin-top: 10px;">
            <span class="admin-message">${messageGameAdd}</span>
            <span class="error-admin-message">${errorMessageGameAdd}</span>
        </form>
    </div>

    <div class="remove-game">
        <form action="GameServlet" method="POST" >
            <h2>Elimina gioco</h2>
            <input class="input" name="game-id" type="number" placeholder="Inserisci l'ID del gioco" style="margin-bottom: 10px;" required>
            <input type="hidden" name="action" value="deleteGame">
            <input class="button" type="submit" value="Elimina">
            <span class="admin-message">${messageGameDelete}</span>
            <span class="error-admin-message">${errorMessageGameDelete}</span>
        </form>
    </div>
    
</div>