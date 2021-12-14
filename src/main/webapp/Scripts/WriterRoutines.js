
$(document).ready(
	() => { 
		loadTable();
	}
);

function loadTable() {
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

function tryAddArticle(e) {
	e.preventDefault();
	
	$.ajax(
		{
			method: "POST",
			url: "BlogServlet",
			data: {
				action: "addArticle",
				title: $("#add-article-title").val(),
				shortTitle: $("#add-article-shortTitle").val(),
				html: $("#article-description").val(),
				cookie: navigator.cookieEnabled,
				jsession: window.location.href.substring(
					window.location.href.lastIndexOf("=") + 1
				),
			},
			success: () => {
				$("#add-article-result").html("Articolo aggiunto con successo!");
				loadTable();
			},
			error: (data) => $("#add-article-result").html(data.responseText),
		}
	);
}

function tryRemoveArticle(e) {
	e.preventDefault();

	$.ajax(
		{
			method: "POST",
			url: "BlogServlet",
			data: {
				action: "deleteArticle",
				deleteArticleId: $("#remove-article-id").val(),
				cookie: navigator.cookieEnabled,
				jsession: window.location.href.substring(
					window.location.href.lastIndexOf("=") + 1
				),
			},
			success: () => {
				$("#remove-article-result").html("Articolo rimosso con successo!");
				loadTable();
			},
			error: (data) => $("#remove-article-result").html(data.responseText),
		}
	);
}