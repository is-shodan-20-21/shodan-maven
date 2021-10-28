
$(document).ready(
	() => { 
		$.ajax(
			{
				method: "GET",
				url: "BlogServlet",
				data: {
					action: "blog",
					cookie: navigator.cookieEnabled,
					jsession: window.location.href.substring(
						window.location.href.lastIndexOf("=") + 1
					),
					endpoint: "View/AJAX_Components/ArticlesTable.jsp"
				},
				success: (data) => {
					$(".table-writer").html(data);
				},
				error: () => {
					$(".table-writer").html("Impossibile ottenere il la tabella degli articoli.")
				}
			}
		);
	}
);