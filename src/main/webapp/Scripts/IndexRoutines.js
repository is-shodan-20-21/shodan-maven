
$(document).ready(
	() => {
		$.ajax(
			{
				method: "GET",
				url: "GameServlet",
				data: {
					action: "shop",
					limit: 4,
					endpoint: "View/AJAX_Components/GameSlideshow.jsp",
					cookie: navigator.cookieEnabled,
					jsession: window.location.href.substring(
						window.location.href.lastIndexOf("=") + 1
					)
				},
				beforeSend: () => {
					$(".left").html("<div class='loader-container'><div class=\"loader\"></div>");
				},
				success: (data) => {
					setTimeout(() => {
						$(".left").html(data);
					}, 400);
				}
			}
		);
		
		$.ajax(
			{
				method: "GET",
				url: "GameServlet",
				data: {
					action: "shop",
					limit: 4,
					order: "DESC",
					endpoint: "View/AJAX_Components/GameSlideshow.jsp",
					cookie: navigator.cookieEnabled,
					jsession: window.location.href.substring(
						window.location.href.lastIndexOf("=") + 1
					)
				},
				beforeSend: () => {
					$(".right").html("<div class='loader-container'><div class=\"loader\"></div>");
				},
				success: (data) => {
					setTimeout(() => {
						$(".right").html(data);
					}, 400)
				}
			}
		);
		
		$("#flip-login-card").click(
			() => {
				$("#login-form").hide();
				$("#signin-form").show();
			}
		);

		$("#flip-signin-card").click(
			() => {
				$("#signin-form").hide();
				$("#login-form").show();
			}
		);
	}
);

function tryLogin(){
	$.ajax(
		{
			method: "POST",
			url: "LoginServlet",
			data: {
				username: $("#login-username").val(),
				password: $("#login-password").val(),
				cookie: navigator.cookieEnabled
			},
			success: (data) => {
				if(data.length == 0)
					window.history.pushState(null, null, "app.jsp");
				else
					window.history.pushState(null, null, data);
				
				$(document.body).fadeOut(400, 
					() => {
						$(document.body).load("View/AJAX_Components/Loading.jsp");
						$(document.body).fadeIn();
						
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

						MAIN_HANDLER.done(function(data) {
							localStorage.setItem("last-page", data.split("/")[1].split(".")[0]);
						});
					}
				);
			},
			error: (data) => {
				$("#login-message").html(data.responseText);
				$("#login-message").css("color", "red");
				$("#login-fail").show();
			}	
		}
	);
}

function trySignIn(){
	const password_regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{5,}$/;
	
	if($("#signin-password").val().match(password_regex)) {
		$.ajax(
			{
				method: "POST",
				url: "SignInServlet",
				data: {
					username: $("#signin-username").val(),
					password: $("#signin-password").val(),
					password2: $("#signin-password-again").val(),
					email: $("#signin-email").val()
				},
				success: (data) => {
					$("#signin-message").html(data);
					$("#signin-message").css("color", "green");
					$("#signin-message").show();

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

					MAIN_HANDLER.done(function(data) {
						localStorage.setItem("last-page", data.split("/")[1].split(".")[0]);
					});
				},
				error: (data) => {
					$("#signin-message").html(data.responseText);
					$("#signin-message").css("color", "red");
					$("#signin-message").show();
				}
			}
		);
	} else {
		$("#signin-message").css("color", "red");
		$("#signin-message").html(
			"La password non rispetta i criteri.<br/>" +
			"Sono necessari almeno cinque caratteri,<br/>" +
			"di cui per lo meno un numero e una lettera."
		);
	}
}

