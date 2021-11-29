$(document).ready(
    () => { 
        /*
            A0 -> Prezzo negativo
			A1 -> Formato della data non valido
			A2 -> Immagini già presenti nel database
			A3 -> Impossibile caricare le immagini
			A4 -> Input non validi
            A5 -> Titolo già presente
		*/

        loadGamesTable();
        let response = $("#add-game-result");
        if(new URLSearchParams(window.location.search).has("__SRVC:OK")) {
            response.css("color", "#89f189");
            response.html("Gioco aggiunto con successo!");
        }

        if(new URLSearchParams(window.location.search).has("__ERR:NO")) {
            switch(new URLSearchParams(window.location.search).get("__ERR:NO")) {
                case "A0":
                    response.html("Il prezzo non puo' essere negativo");
                    break;

                case "A1":
                    response.html("Formato della data non valido");
                    break;
                        
                case "A2":
                    response.html("Immagini già presenti nel database");
                    break;
                    
                case "A3": 
                    response.html("Impossibile caricare le immagini");
                    break;
                    
                case "A4":
                    response.html("Input non validi");
                    break;

                 case "A5":
                    response.html("Titolo gi&agrave; presente");
                    break;
                    
                default:
                    response.html("Errore non meglio definito");
                    break; 
            }
        }
        window.history.pushState(null, null, "app.jsp");
    }
);

function loadGamesTable() {
    $.ajax(
        {
            method: "GET",
            url: "GameServlet",
            data: {
                action: "getGames",
                cookie: navigator.cookieEnabled,
                jsession: window.location.href.substring(
                    window.location.href.lastIndexOf("=") + 1
                ),
                endpoint: "View/AJAX_Components/GamesTable.jsp"
            },
            success: (data) => {
                $(".table-storeman").html(data);
            },
            error: () => {
                $(".table-storeman").html("Impossibile ottenere la tabella dei giochi.")
            }
        }
    );
}

function tryDeleteGame(e) {
    e.preventDefault();

    $.ajax(
        {
            method: "POST",
            url: "GameServlet",
            data: {
                action: "deleteGame",
                cookie: navigator.cookieEnabled,
                jsession: window.location.href.substring(
                    window.location.href.lastIndexOf("=") + 1
                ),
                deleteGameId: $("#delete-game-id").val()
            },
            success: (data) => {
                $("#delete-game-result").css("color", "#89f189");
                $("#delete-game-result").html("Gioco eliminato con successo!");
                updateCart();
                loadGamesTable();
            },
            error: (data) => {
                $("#delete-game-result").css("color", "#ea4e4e");
                $("#delete-game-result").html("Non &egrave; stato possibile eliminare il gioco.");
                loadGamesTable();
            }
        }
    );
}

function tryUpdateGame(e) {
    e.preventDefault();

    $.ajax(
        {
            method: "POST",
            url: "GameServlet",
            data: {
                action: "updateGame",
                cookie: navigator.cookieEnabled,
                jsession: window.location.href.substring(
                    window.location.href.lastIndexOf("=") + 1
                ),
                updateGameId: $("#update-game-id").val(),
                updateGamePrice: $("#update-game-price").val(),
            },
            success: (data) => {
                $("#update-game-result").css("color", "#89f189");
                $("#update-game-result").html("Gioco aggiornato con successo!");
                loadGamesTable();
            },
            error: (data) => {
                $("#update-game-result").css("color", "#ea4e4e");
                $("#update-game-result").html("Non &egrave; stato possibile aggiornare il gioco.");
                loadGamesTable();
            }
        }
    );
}