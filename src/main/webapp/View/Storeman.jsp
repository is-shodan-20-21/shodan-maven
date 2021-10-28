<head>
	<link rel="stylesheet" href="Style/Admin.css">
</head>

<h1 id="header">
	<i class="fas fa-dice-d6"></i>
	Cataloghista
</h1>

<div class="storeman-div">
    <div class="add-game">
        <form enctype="multipart/form-data" onsubmit="tryAddGame(); return false" method="POST">
            <h2>Aggiungi gioco</h2>
            <input class="input" name="game-name" type="text" id="game-name" placeholder="Inserisci il nome del gioco" required>
            <input class="input" name="game-price" type="number" id="game-price" placeholder="Inserisci il prezzo del gioco" required>
            <input class="input" id="date-input" name="game-date" type="date" required>
            <textarea class="input" id="textarea-game" name="game-description" rows="4" cols="5" placeholder="Inserisci una descrizione del gioco"></textarea>
            <input class="input" id="image-loader" name="game-image" type="file" required>
            <input class="input" id="landscape-loader" name="game-landscape" type="file" required>
            <input class="button" type="submit" value="Aggiungi">
        </form>
    </div>

    <div class="modify-game">
        <form action="" method="POST" >
            <h2>Modifica gioco</h2>
            <input class="input" name="game-id" type="number" placeholder="Inserisci l'ID del gioco" style="margin-bottom: 10px;" required>
            <input class="input" name="game-name" type="text" placeholder="Inserisci il nome del gioco" required>
            <input class="input" name="game-price" type="number" placeholder="Inserisci il prezzo del gioco" required>
            <textarea class="input" id="textarea-game" name="game-description" rows="4" cols="5" placeholder="Inserisci una nuova descrizione del gioco"></textarea>
            <input class="input" id="file-loader" name="game-image" type="file" required>
            <input class="button" type="submit" value="Modifica">
        </form>
    </div>

    <div class="remove-game">
        <form action="GameServlet" method="POST" >
            <h2>Elimina gioco</h2>
            <input class="input" name="game-id" type="number" placeholder="Inserisci l'ID del gioco" style="margin-bottom: 10px;" required>
            <input type="hidden" name="action" value="deleteGame">
            <input class="button" type="submit" value="Elimina">
        </form>
    </div>

</div>

<h1 id="header">
	<i class="fas fa-info-circle"></i>
	Informazioni sui giochi
</h1>

<div class="table-storeman">
    
        

</div>

<script src="Scripts/StoremanRoutines.js"></script>