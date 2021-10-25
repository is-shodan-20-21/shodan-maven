$(document).ready(
    () => {
        $(".valid-credit-card").click(
            () => {
                alert("[DEMO] Ricarica effettuata, +100€ sul saldo!");
            }
        );

        $(".credit-card-add").click(
            (e) => {
                let newCard = "<form onsubmit='tryAddCard(); return false' autocomplete='nope'>"
                    + "<input required class='card-type' type='search' placeholder='Circuito'>"
                    + "<input required class='card-number' type='search' placeholder='Numero di carta'>"
                    + "<input required class='card-owner' type='search' placeholder='Titolare'>"
                    + "<input required class='card-cvv' type='password' placeholder='CVV'>"
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