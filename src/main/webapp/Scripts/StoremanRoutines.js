$(document).ready(
    () => { 
        loadGamesTable();
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
                $(".table-storeman").html("Impossibile ottenere il la tabella dei giochi.")
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
                $("#delete-game-result").html("Gioco eliminato con successo!");
                loadGamesTable();
            },
            error: (data) => {
                $("#delete-game-result").html("Non &egrave; stato possibile eliminare il gioco.");
                loadGamesTable();
            }
        }
    );
}