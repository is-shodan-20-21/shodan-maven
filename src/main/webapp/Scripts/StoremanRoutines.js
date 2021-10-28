$(document).ready(
    () => { 
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
);

function tryAddGame() {

    $.ajax(
        {
            method: "POST",
            url: "GameServlet",
            data: {
                action: "addGame",
                cookie: navigator.cookieEnabled,
                jsession: window.location.href.substring(
                    window.location.href.lastIndexOf("=") + 1
                ),
                game_name: $("#game-name").val(),
                game_price: $("#game-price").val(),
                game_date: $("#date-input").val(),
                game_description: $("#textarea-game").val(),
                game_image: $("#image_loader").val(),
                game_landscape: $("#landscape-loader").val(),
            },
            processData: false,
            contentType: false,
            success: (data) => {
                alert(data);
            }
        }
    );
}