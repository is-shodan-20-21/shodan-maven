$(document).ready(
    () => {
        $(".valid-credit-card").click(
            () => {
                alert("[DEMO] Ricarica effettuata, +100€ sul saldo!");
                $.ajax(
                    {
                        type: "GET",
                        url: "UserServlet",
                        data: {
                            action: "updateBalance",
                            cookie: navigator.cookieEnabled,
				            jsession: window.location.href.substring(
					            window.location.href.lastIndexOf("=") + 1
				            ),
                        }
                    }
                )
            }
        );

        $(".credit-card-add").click(
            (e) => {
                let newCard = "<form onsubmit='tryNewCard(); return false' autocomplete='nope'>"
                    + "<input required class='card-type' type='search' placeholder='Circuito'>"
                    + "<input required class='card-number' type='search' placeholder='Numero di carta'>"
                    + "<input required class='card-owner' type='search' placeholder='Titolare'>"
                    + "<input required class='card-cvv' type='search' placeholder='CVV'>"
                    + "<input required class='card-due' type='search' placeholder='Scadenza'>"
                    + "<input type='submit' style='visibility: hidden'>"
                    + "</form>";
                $(e.target).off();
                $(e.target).addClass("credit-card-wip");
                $(e.target).removeClass("credit-card-add");
                $(e.target).html(newCard);
            }
        );
    }
);

function tryNewCard() {
    $.ajax(
        {
            type: "POST",
            url: "UserServlet",
            data: {
                action: "newCard",
                cookie: navigator.cookieEnabled,
				jsession: window.location.href.substring(
					window.location.href.lastIndexOf("=") + 1
				),
                cardType: $(".card-type").val(),
                cardNumber: $(".card-number").val(),
                cardOwner: $(".card-owner").val(),
                cardDate: $(".card-due").val(),
            },
            success: () => {
                $("#app").load("View/Payment.jsp");
            },
            error: () => {
                $(".card-tip").css("color", "darkred");
                $(".card-tip").html("I dati inseriti non sono validi. Riprova digitando correttamente i dati.");
            }
        }
    )
}