$("#shop-games").html("");

$(document).ready(
	() => {
		$.ajax(
			{
				type: "GET",
				url: "GameServlet",
				data: {
					action: "parsed",
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

						$("#game-search-input").on("input", function() {
							if($(this).val() != "") {
								console.log("# Shodan [Looking for {" + $(this).val() + "}]");
								$.ajax(
									{
										type: "GET",
										url: "GameServlet",
										data: {
											action: "searchGameShop",
											endpoint: "View/AJAX_Components/GameList.jsp",
											search_query: $(this).val(),
											cookie: navigator.cookieEnabled,
											jsession: window.location.href.substring(
												window.location.href.lastIndexOf("=") + 1
											)
										},
										beforeSend: () => {
											//$("#shop-games").html("<div class=\"loader loader-lowered\">");
										},
										success: (result) => {
											$("#shop-games").html(result);
										},
										error: () => {
											$("#shop-games").html(setEmptyView());
										}
									}
								)
							} else
								$("#shop-games").html(data);
						});
					}, 250);
				},
				error: () => {
					setTimeout(() => $("#shop-games").html(setEmptyView()), 250);
				}
			}
		);
	}
);