<head>
	<link rel="stylesheet" href="Style/Admin.css">
</head>

<h1 id="header">
	<i class="fas fa-dice-d6"></i>
	Articolista
</h1>

<div class="writer-div">
	<div id="add-article">
		<h2>Pubblica un articolo</h2>
			<form action="BlogServlet" method="POST">
				<input class="input" name="add-article-title" type="text" placeholder="Inserisci il titolo dell'articolo" required>
				<input class="input" name="article-shortTitle" type="text" placeholder="Inserisci il sottotitolo dell'articolo" required>
				<textarea class="input" id="article-description" name="article-html" rows="4" cols="5">Inserisci il contenuto dell'articolo.</textarea>
				<input type="hidden" name="action" value="addArticle">
				<input class="button" type="submit" value="Aggiungi">
			</form>
	</div>
	
	<div class="remove-article">
		<h2>Rimuovi articolo</h2>
		<form action="BlogServlet" method="POST">
			<input class="input" name="delete-article-id" type="number" placeholder="Inserisci l'ID dell'articolo" required style="margin-bottom: 10px;">
			<input type="hidden" name="action" value="deleteArticle">
			<input class="button" type="submit" value="Elimina">
			<span class="admin-message">${messageArticleDelete}</span>
			<span class="error-admin-message">${errorMessageArticleDelete}</span>
		</form>
	</div>
	
</div>

