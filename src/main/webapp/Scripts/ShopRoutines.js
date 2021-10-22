$("#shop-games").html("");

$(document).ready(
	() => {
		$.ajax(
			{
				type: "GET",
				url: "GameServlet",
				data: {
					action: "shop",
					endpoint: "View/AJAX_Components/GameList.jsp",
					cookie: navigator.cookieEnabled,
					jsession: window.location.href.substring(
						window.location.href.lastIndexOf("=") + 1
					)
				},
				beforeSend: () => {
					$("#shop-games").html("<div class=\"loader loader-lowered\">");
				},
				success: (data) => {
					setTimeout(() => {
						$("#shop-games").html(data);

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
								error: (data) => $(".game-add").click(() => window.location.href="index.jsp")
							}
						);
					}, 250);
				},
				error: () => {
					setTimeout(() => $("#shop-games").html(setEmptyView()), 250);
				}
			}
		);
	}
);