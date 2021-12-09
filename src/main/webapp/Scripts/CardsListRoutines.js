$(document).ready(
    () => {
        $("#amount-input").on("click", function() {
            $("#amount-input").attr("placeholder", "0");
            $("#amount-input-radio").attr("checked", "true");
        });

        $("#amount-input").on("input", function() {
            $("#amount-input-radio").attr("value", $("#amount-input").val());
        });

        $(".valid-credit-card").click(
            function(e) {
                let amount = $("input[name=\"amount\"]:checked").val();
                let cardId = $(e.target).attr("data-card-id");
                
                console.log($(e.target));

                if(amount == 0)
                    alert("[Payment API Demo] Inserisci un ammontare valido.");
                else {
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
                                amount: amount,
                                cardId: cardId
                            },
                            success: () => alert("[Payment API Demo] Ricarica di " + amount + "€ effettuata sul saldo!"),
                            error: () => alert("[Payment API Demo] Impossibile effettuare la ricarica. Ricontrolla i dati.")
                        }
                    )
                }
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
                cardCVV: $(".card-cvv").val()
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