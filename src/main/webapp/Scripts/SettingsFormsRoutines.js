$(document).ready(
	() => { 
		$.ajax(
			{
				method: "GET",
				url: "SettingsServlet",
				data: {
					action: "userCard",
					cookie: navigator.cookieEnabled,
					jsession: window.location.href.substring(
						window.location.href.lastIndexOf("=") + 1
					),
					endpoint: "View/AJAX_Components/UserCard.jsp"
				},
				success: (data) => {
					$("#settings-form-info").html(data)
				},
				error: () => {
					$("#settings-form-info").html("Impossibile ottenere la UserCard.")
				}
			}
		);
	}
);

function tryEmailChange() {
	$.ajax(
		{
			method: "POST",
			url: "SettingsServlet",
			data: {
				action: "updateEmail",
				cookie: navigator.cookieEnabled,
				jsession: window.location.href.substring(
					window.location.href.lastIndexOf("=") + 1
				),
				email: $("#settings-input-email").val()
			},
			success: (data) => {
				$("#email-change-message").html(data);
				$("#email-change-message").show();
				$(".user-data>h2").html($("#settings-input-email").val());
				setTimeout(() => $("#email-change-message").hide(), 2500);
			}
		}
	);
}

function tryPasswordChange() {
	const password_regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{5,}$/;
	
	if(
		$("#settings-input-new-password").val().match(password_regex) &&
		$("#settings-input-new-password-again").val().match(password_regex)
	) {
		$.ajax(
			{
				method: "POST",
				url: "SettingsServlet",
				data: {
					action: "updatePassword",
					cookie: navigator.cookieEnabled,
					jsession: window.location.href.substring(
						window.location.href.lastIndexOf("=") + 1
					),
					old_password: $("#settings-input-old-password").val(),
					new_password: $("#settings-input-new-password").val(),
					new_password_again: $("#settings-input-new-password-again").val()
				},
				success: (data) => {
					$("#password-change-message").html(data);
					$("#password-change-message").show();
					setTimeout(() => $("#password-change-message").hide(), 2500);
				}
			}
		);
	} else {
		$("#password-change-message").html("La password deve avere almeno cinque caratteri, di cui almeno una lettera e un numero.");
		$("#password-change-message").show();
		setTimeout(() => $("#password-change-message").hide(), 2500);
	}
}