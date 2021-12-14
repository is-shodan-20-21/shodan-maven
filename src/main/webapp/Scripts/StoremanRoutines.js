var oracle;

$(document).ready(
    () => { 
        loadGamesTable();

        console.log(new URLSearchParams(window.location.search).has("__ORACLE"));

        if(new URLSearchParams(window.location.search).has("__ORACLE")) {
            oracle = new URLSearchParams(window.location.search).get("__ORACLE");
            console.log(oracle);
        }
        
        window.history.pushState(null, null, "app.jsp");
        if(oracle == "Gioco aggiunto")
            $("#add-game-result").css("color", "green");
            
        $("#add-game-result").text(oracle);

        console.log(0);
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
                $("#delete-game-result").html(data.responseText);
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
                $("#update-game-result").html(data.responseText);
                loadGamesTable();
            }
        }
    );
}