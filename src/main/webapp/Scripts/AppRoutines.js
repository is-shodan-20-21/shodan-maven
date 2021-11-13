$(document).ready(
	() => {
		console.log("# Shodan [Container: App]");

		$("#cta a").click(() => {
			localStorage.removeItem("last-nav");
			localStorage.removeItem("last-page");
		});

		$.ajax(
			{
				type: "GET",
				url: "CartServlet",
				data: {
					action: "quantity",
					cookie: navigator.cookieEnabled,
					jsession: window.location.href.substring(
						window.location.href.lastIndexOf("=") + 1
					)
				},
				success: (data) => localStorage.setItem("cart", data),
				error: (data) => deleteCart()
			}
		).done(
			() => refreshCart()
		);

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
				error: (data) => {
					localStorage.removeItem("last-page");
					localStorage.removeItem("last-nav");

					$("#app").load("View/Dashboard.jsp");
					$("nav").load("View/Nav/NavGuest.jsp");

					$("#cta").show();
				}
			}
		);

		var NAV_HANDLER = $.ajax(
			{
				type: "GET",
				url: "ShodanViews",
				data: {
					view: "NAV",
					cookie: navigator.cookieEnabled,
					jsession: window.location.href.substring(
						window.location.href.lastIndexOf("=") + 1
					)
				},
				error: (data) => console.log("# ShodanViews - 4**/5**" + data),
				success: (data) => console.log("# Shodan [Sidebar loaded]")
			}
		);

		var MAIN_HANDLER = $.ajax(
			{
				type: "GET",
				url: "ShodanViews",
				data: {
					view: "MAIN",
					cookie: navigator.cookieEnabled,
					jsession: window.location.href.substring(
						window.location.href.lastIndexOf("=") + 1
					)
				},
				error: (data) => console.log("# ShodanViews - 4**/5**" + data),
				success: (data) => console.log("# Shodan [Dashboard loaded]")
			}
		);

		if(navigator.cookieEnabled) {
			if(localStorage.getItem("last-page") != null) {
				if((localStorage.getItem("last-page") == "Game") && (new URLSearchParams(window.location.search).has("game")))
					$("#app").load("View/Game.jsp");
				else if((localStorage.getItem("last-page") == "Article") && (new URLSearchParams(window.location.search).has("blog")))
					$("#app").load("View/Article.jsp");
				else
					$("#app").load("View/" + localStorage.getItem("last-page") + ".jsp");
			} else {
				MAIN_HANDLER.done(function(data) {
					$("#app").load(data);
					localStorage.setItem("last-page", data.split("/")[1].split(".")[0]); 	
				});
			}

			if(localStorage.getItem("last-nav") != null && localStorage.getItem("last-nav") != "NavGuest")
				$("nav").load("View/Nav/" + localStorage.getItem("last-nav") + ".jsp");
			else {
				NAV_HANDLER.done(function(data) {
					$("nav").load(data);
					console.log(data);
					localStorage.setItem("last-nav", data.split("/")[2].split(".")[0]);  	
				});
			}
		} else {
			MAIN_HANDLER.done(function(data) {
				$("#app").load(data); 	
			});

			NAV_HANDLER.done(function(data) {
				$("nav").load(data); 	
			});
		}
	}
);

let parsed_path = window.location.href.substring(0, window.location.href.indexOf("?"));
		
function refreshCart() {
	if(navigator.cookieEnabled) {
		$(".cart-quantity-value").text(localStorage.getItem("cart"));
		if(localStorage.getItem("cart") != null) {
			$(".fa-clipboard").fadeOut("slow", 
				() => {
					$(".cart-quantity-value").show(700);
				}
			);
		}
	}
}

function deleteCart() {
	$(".cart-quantity-value").fadeOut("slow", 
		() => {
			$(".fa-clipboard").fadeIn("slow");
		}
	);
	localStorage.removeItem("cart");
}

function updateCart() {
	$.ajax(
		{
			type: "GET",
			url: "CartServlet",
			data: {
				action: "quantity",
				cookie: navigator.cookieEnabled,
				jsession: window.location.href.substring(
					window.location.href.lastIndexOf("=") + 1
				)
			},
			success: (data) => localStorage.setItem("cart", data)
		}
	).done(
		() => refreshCart()
	);
}

function setEmptyView(error = "Non c'è ancora nulla qui...") {
	let view = "<div class=\"empty-view\"><i class=\"far fa-folder-open\"></i>";
	view += "<div>" + error + "</div></div>";
	
	return view;
}