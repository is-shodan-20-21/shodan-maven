$("#my-collection").html("");

$(document).ready(
	() => {
		$.ajax(
			{
				type: "GET",
				url: "GameServlet",
				data: {
					action: "library",
					endpoint: "View/AJAX_Components/GameList.jsp",
					cookie: navigator.cookieEnabled,
					jsession: window.location.href.substring(
						window.location.href.lastIndexOf("=") + 1
					)
				},
				beforeSend: () => {
					$("#my-collection").html("<div class=\"loader loader-lowered\">");
				},
				success: (data) => {
					setTimeout(() => {
						$("#my-collection").html(data);

						$("#game-search-input").on("input", function() {
							if($(this).val() != "") {
								console.log("# Shodan [Looking for {" + $(this).val() + "}]");
								$.ajax(
									{
										type: "GET",
										url: "GameServlet",
										data: {
											action: "searchGameLibrary",
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
											$("#my-collection").html(result);
										},
										error: () => {
											$("#my-collection").html(setEmptyView());
										}
									}
								)
							} else
								$("#my-collection").html(data);
						});
					}, 250);
				},
				error: () => { 
					setTimeout(() => $("#my-collection").html(setEmptyView()), 250);
				}
			}
		);
	}
);