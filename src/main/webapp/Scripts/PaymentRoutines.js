$(document).ready(
    () => {
        console.log("# Shodan [Accesso alla pagina di ricarica del saldo]");
        $.ajax(
            {
                type: "GET",
                url: "UserServlet",
                data: {
                    action: "cardList",
                    endpoint: "View/AJAX_Components/CardList.jsp"
                },
                beforeSend: () => {
                    $("#card-list").html("<div class=\"loader loader-lowered\">");
                },
                success: (data) => {
                    $("#card-list").html(data);
                    console.log("# Shodan [Rilevate carte di pagamento valide]")
                },
                error: () => {
                    $("#card-list").html(setEmptyView());
                    console.log("# Shodan [Nessuna carta rilevata]")
                }
            }
        );
    }
);