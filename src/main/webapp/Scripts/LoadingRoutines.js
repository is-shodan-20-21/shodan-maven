$(document).ready(
	() => {
		$("body").css("background-color", "#171717");
		$("body").css("align-items", "center");
		$("body").css("justify-content", "center");
		
		console.log("# Shodan [Loading]");
		
		if(navigator.cookieEnabled) {
			$("#loading-text>span").html("#" + document.cookie.split("=")[1]);
		} else {
			const parsed_jsession = window.location.href.substring(
				window.location.href.lastIndexOf("=") + 1
			);
			
			$("#loading-text>span").html("#" + parsed_jsession);
		}
					
		$("#loading-logo").fadeIn();
					
		setTimeout(
			() => {
				$("#loading-main").fadeIn();
			},
		500);
					
		setTimeout(
			() => {
				progress();
			},
		850);
	}
);
			
function progress() {
	if($("#loading-line").width() != $("#loading-wrap").width()) {
		$("#loading-line").width($("#loading-line").width() + 5);
		setTimeout(
			() => progress(),
		10);
	} else {
		$("#loading-container").fadeOut(250, 
			() => {
				document.head.innerHTML = '<link rel="icon" href="Static/Assets/Icon.png" type="image/x-icon">';
				document.body.setAttribute("style", "background-color: #171717");	
			}
		);
		if(navigator.cookieEnabled) {
			setTimeout(
				() => {
					$(document.body).fadeOut(0, 
						() => {
							$(document.body).load("app.jsp");
							$(document.body).fadeIn(300, () => document.body.setAttribute("style", ""));	
						}
					);
				},
			350);
		} else
			location.reload();
	}
}