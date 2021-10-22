function openSider() {
	localStorage.setItem("sider", "open");
		
	$("#nav-logo-container").css("display", "flex");
	$("#nav-items>div>span").css("display", "inline-block");
	$("nav").css("width", "275px");
	$("main").css("margin-left", "274px");
	$("nav").addClass("open");
	$("#roles-placeholder").css("display", "block");
}
	
function closeSider() {
	localStorage.setItem("sider", "closed");
		
	$("#nav-logo-container").css("display", "none");
	$("#nav-items>div>span").css("display", "none");
	$("nav").css("width", "55px");
	$("main").css("margin-left", "55px");
	$("nav").removeClass("open");
	console.log(0);
	$("#roles-placeholder").css("display", "none");
}

if(!navigator.cookieEnabled) {
	$("#nav-close").hide();
	$(".cart-quantity").hide();
}

$(document).ready(
	() => {
		var ROLES_HANDLER = $.ajax(
			{
				type: "GET",
				url: "UserServlet",
				data: {
					cookie: navigator.cookieEnabled,
					jsession: window.location.href.substring(
						window.location.href.lastIndexOf("=") + 1
					),
					action: "switchableRoles",
					endpoint: "View/AJAX_Components/RolesList.jsp",
				},
				success: (data) => { 
					console.log("# Shodan [Multiple roles detected]");
					$("<div id='roles-placeholder'></div>").insertBefore($("#logout-link"));
					$("#roles-placeholder").html(data);
				},
				error: (data) => {
					console.log("# Shodan [User either has no admin powers or is a guest]");
				}
			}
		).done(
			() => {
				if(navigator.cookieEnabled) {
					if(localStorage.getItem("sider") == "open" || localStorage.getItem("sider") == null) 
						openSider();
					else
						closeSider();
				}
			}
		);

		if(navigator.cookieEnabled) {
			if(localStorage.getItem("last-page") != null) {
				const container = "#" + localStorage.getItem("last-page").toLowerCase().split(".")[0] + "-link";
				
				$("#nav-items>div").each(
					function() {
						if($(this).hasClass("selected"))
							$(this).toggleClass("selected");
					}
				);
				
				console.log("# Shodan [Component: " + container + "]");
				
				if(container == "#game-link")				
					$("#shop-link").addClass("selected");
				else if(container == "#article-link")					
					$("#blog-link").addClass("selected");
				else			
					$(container).addClass("selected");
			}
				
			setTimeout(() => {
				refreshCart()
			}, 1000);
		} else
			$("#dashboard-link").addClass("selected");
		
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
				}
			}
		);

		$("#nav-logo").click(
			() => {			
				$("#nav-items>div").each(
					function() {
						if($(this).hasClass("selected"))
							$(this).toggleClass("selected");
					}
				);

				MAIN_HANDLER.done(function(data) {
					$("#app").load(data);
					$("#" + data.split("/")[1].split(".")[0].toLowerCase() + "-link").addClass("selected");
					if(navigator.cookieEnabled)
						localStorage.setItem("last-page", data.split("/")[1].split(".")[0]);
				});

				
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
						success: (data) => { 
							console.log("# Shodan [Sidebar loaded]");
							$("nav").load(data);
						}
					}
				);
			}
		);
				
		$("#nav-items>div").click(	
			function() {
				if($(this).attr("id").split("-")[0] != "roles") {
					let parsed_path = window.location.href.substring(0, window.location.href.indexOf("?"));
					window.history.pushState(null, null, parsed_path);
						
					$("#nav-items>div").each(
						function() {
							if($(this).hasClass("selected"))
								$(this).toggleClass("selected");
						}
					);
						
					$(this).addClass("selected");
						
					let container = $(this).attr("id")[0].toUpperCase() + $(this).attr("id").split("-")[0].slice(1) + ".jsp";
					
					if(navigator.cookieEnabled && $(this) && $(this).attr("id").split("-")[0] != "admin" && $(this).attr("id").split("-")[0] != "logout")
						localStorage.setItem("last-page", container.split(".")[0]);
						
					$("#app").load("View/" + container).fadeIn("slow");
				}
			}
		);
	
		$("#nav-close").click(
			() => {
				if(navigator.cookieEnabled) {
					if($("nav").hasClass("open"))
						closeSider()
					else
						openSider()
				}
			}
		);
	
		$("#logout-link").click(
			() => {
				document.cookie = "user_session=; Expires=Thu, 01 Jan 1970 00:00:01 GMT;";
				
				$.ajax(
					{
						method: "POST",
						url: "UserServlet",
						data: {
							action: "logout",
							cookie: navigator.cookieEnabled,
							jsession: window.location.href.substring(
								window.location.href.lastIndexOf("=") + 1
							)
						},
						success: () => {
							window.location.replace("index.jsp");
							localStorage.removeItem("last-page");
							localStorage.removeItem("last-nav");
						}
					}
				);
			}
		);

		$("#back-link").click(
			() => {
				document.cookie = "user_session=; Expires=Thu, 01 Jan 1970 00:00:01 GMT;";
				window.location.replace("index.jsp");
				localStorage.removeItem("last-page");
				localStorage.removeItem("last-nav");
			}
		);
	}
);