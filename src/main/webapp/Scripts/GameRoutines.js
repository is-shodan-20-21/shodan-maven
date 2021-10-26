$("#game-page").html("");

$(document).ready(
	() => {
		$.ajax(
			{
				type: "GET",
				url: "GameServlet",
				data: {
					action: "game",
					endpoint: "View/Game.jsp",
					cookie: navigator.cookieEnabled,
					jsession: window.location.href.substring(
						window.location.href.lastIndexOf("=") + 1
					),
					game_id: new URLSearchParams(window.location.search).get("game")
				},
				beforeSend: () => {
					$("#game-page").html("<div class=\"loader loader-lowered\">");
				},
				success: (data) => {
					setTimeout(
						() => {
							$(".content").replaceWith(data.substring(0, data.lastIndexOf("\n")));

							$.ajax(
								{
									type: "GET",
									url: "UserServlet",
									data: {
										action: "role",
										cookie: navigator.cookieEnabled,
										jsession: window.location.href.substring(
											window.location.href.lastIndexOf("=") + 1
										)
									},
									error: (data) => $("#add-to-cart").click(() => window.location.href="index.jsp")
								}
							);

							$.ajax(
								{
									type: "GET",
									url: "GameServlet",
									data: {
										action: "hasGame",
										game_id: new URLSearchParams(window.location.search).get("game"),
										cookie: navigator.cookieEnabled,
										jsession: window.location.href.substring(
											window.location.href.lastIndexOf("=") + 1
										)
									},
									success: () => {
										console.log("# Shodan [Game already owned; purchase not allowed]");
										console.log($("#add-to-cart"));
										$("#play-game").css("display", "inline-block");

										$("#play-game").click(
											() => alert("Avvio del gioco...")
										);
									},
									error: () => {
										$("#add-to-cart").css("display", "inline-block");
									}
								}
							);

						}, 150)
				},
				error: () => {
					$(".content").html(setEmptyView("Questo gioco non esiste..."));
				}
			}
		);
	}
);

$(document).off().on("click", "#add-to-cart", () => {
	$.ajax(
		{
			type: "POST",
			url: "CartServlet",
			data: {
				action: "addGame",
				cookie: navigator.cookieEnabled,
				game_id: $(".game-info-container").parent().attr("data-game-id"),
				jsession: window.location.href.substring(
					window.location.href.indexOf("=") + 1, window.location.href.indexOf("=") + 33
					
				),
				total: $(".last-row-total").text().split(" ")[1]
			},
			success: () => {
				$(".button--cart").css("cssText", "font-size: 17.5px; cursor: defualt; display: inline-block; padding: 15px !important");
				$("#add-to-cart").html("Gioco aggiunto al carrello!");
				updateCart();
			},
			error: () => {
				$(".button--cart").css("cssText", "font-size: 17.5px; cursor: defualt; display: inline-block; padding: 15px !important");
				$("#add-to-cart").html("Non è stato possibile aggiungere il gioco al carrello!");	
			}	
		}
	);
});