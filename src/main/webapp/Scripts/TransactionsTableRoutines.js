$(document).ready(
    () => {
        $(".transaction-link>span").click(
            (e) => {
                window.history.pushState(null, null, "?game=" + $(e.target).attr("data-game-id"));
			    $("#app").load("View/Game.jsp");
			
			    if(navigator.cookieEnabled)
				    localStorage.setItem("last-page", "Game");
            }
        )
    }
);
