$(document).ready(
	() => {
		$.ajax(
			{
				method: "GET",
				url: "UserServlet",
				data: {
					action: "info",
					cookie: navigator.cookieEnabled,
					jsession: window.location.href.substring(
						window.location.href.lastIndexOf("=") + 1
					),
					endpoint: "View/Settings.jsp"
				},
				beforeSend: () => {
					$("#settings-forms-loader").html("<div class=\"loader loader-lowered\">");
					$("#transactions-table-loader").html("<div class=\"loader\">");
					$("#cards-table-loader").html("<div class=\"loader\">")
				},
				success: (data) => {
					setTimeout(() => {
						loadSettingsForms();
						loadTransactionsTable();
						loadCardsTable();
					}, 300);
				}
			}
		);
	}
);

function loadSettingsForms() {
	$.ajax(
		{
			method: "GET",
			url: "SettingsServlet",
			data: {
				action: "settingsForms",
				cookie: navigator.cookieEnabled,
				jsession: window.location.href.substring(
					window.location.href.lastIndexOf("=") + 1
				),
				endpoint: "View/AJAX_Components/SettingsForms.jsp"
			},
			success: (data) => {
				setTimeout(() => {
					$("#settings-forms-loader").html(data)
				}, 400)
			},
			error: () => {
				setTimeout(() => {
					$("#settings-forms-loader").html(setEmptyView())
				}, 400)
			}
		}
	);
}

function loadTransactionsTable() {
	$.ajax(
		{
			method: "GET",
			url: "SettingsServlet",
			data: {
				action: "transactionsTable",
				cookie: navigator.cookieEnabled,
				jsession: window.location.href.substring(
					window.location.href.lastIndexOf("=") + 1
				),
				endpoint: "View/AJAX_Components/TransactionsTable.jsp"
			},
			success: (data) => {
				setTimeout(() => {
					$("#transactions-table-loader").html(data)
				}, 400)
			},
			error: () => {
				setTimeout(() => {
					$("#transactions-table-loader").html(setEmptyView())
				}, 400)
			}
		}
	);
}

function loadCardsTable() {
	$.ajax(
		{
			method: "GET",
			url: "SettingsServlet",
			data: {
				action: "cardsTable",
				cookie: navigator.cookieEnabled,
				jsession: window.location.href.substring(
					window.location.href.lastIndexOf("=") + 1
				),
				endpoint: "View/AJAX_Components/CardsTable.jsp"
			},
			success: (data) => {
				setTimeout(() => {
					$("#cards-table-loader").html(data)
				}, 400)
			},
			error: () => {
				setTimeout(() => {
					$("#cards-table-loader").html(setEmptyView())
				}, 400)
			}
		}
	);
}