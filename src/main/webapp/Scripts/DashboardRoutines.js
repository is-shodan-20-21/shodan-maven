
$(".blog").html("");
$(".games").html("");

$(document).ready(
	() => {
		$.ajax(
			{
				method: "GET",
				url: "GameServlet",
				data: {
					action: "parsed",
					cookie: navigator.cookieEnabled,
					jsession: window.location.href.substring(
						window.location.href.lastIndexOf("=") + 1
					),
					endpoint: "View/AJAX_Components/GameList.jsp",
					limit: 5
				},
				beforeSend: () => {
					$(".games").html("<div class=\"loader\">");
				},
				success: (data) => {
					setTimeout(() => {
						$(".games").html(data);

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
					}, 400)
				},
				error: () => {
					setTimeout(() => {
						$(".games").html(setEmptyView());
						$(".games").css("min-height", "0");
					}, 400);
				}
			}
		);
		
		$.ajax(
			{
				method: "GET",
				url: "BlogServlet",
				data: {
					action: "blog",
					limit: 5,
					endpoint: "View/AJAX_Components/BlogList.jsp"
				},
				beforeSend: () => {
					$(".blog").html("<div class=\"loader\">");
				},
				success: (data) => {
					setTimeout(() => {
						$(".blog").html(data);
					}, 400)
				},
				error: () => {
					setTimeout(() => $(".blog").html(setEmptyView()), 400);
				}
			}			
		);
		
		$(document).ajaxComplete(
			() => {
				$(".featured-button").off().click(
					function() {
						window.history.pushState(null, null, "?game=6");
						$("#app").load("View/Game.jsp");
						
						if(navigator.cookieEnabled)
							localStorage.setItem("last-page", "Game");
					}
				);
				
			}
		);
		
	}
	
);
