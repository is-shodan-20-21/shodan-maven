<head>
	<link rel="stylesheet" href="Style/Admin.css">
</head>

<h1 id="header">
	<i class="fas fa-feather-alt"></i>
	Gestione degli articoli
</h1>

<div class="writer-div">
	<div id="add-article">
		<h2>Pubblica un articolo</h2>
		<form>
			<input class="input" id="add-article-title" type="text" placeholder="Inserisci il titolo dell'articolo" required>
			<input class="input" id="add-article-shortTitle" type="text" placeholder="Inserisci il sottotitolo dell'articolo" required>
			<textarea class="input" id="article-description" name="article-html" rows="4" cols="5" placeholder="Inserisci il contenuto dell'articolo"></textarea>
			<input type="hidden" name="action" value="addArticle">
			<input onclick="tryAddArticle(event)" class="button" id="add-article-button" type="submit" value="Aggiungi">
			<div id="add-article-result"></div>
		</form>
	</div>
	
	<div class="remove-article">
		<h2>Rimuovi articolo</h2>
		<form>
			<input class="input" id="remove-article-id" type="text" placeholder="Inserisci l'ID dell'articolo" required style="margin-bottom: 10px;">
			<input onclick="tryRemoveArticle(event)" class="button" type="submit" value="Elimina">
			<div id="remove-article-result"></div>
		</form>
	</div>
</div>


<h1 id="header">
	<i class="fas fa-info-circle"></i>
	Informazioni sugli articoli
</h1>

<div class="table-writer">
	<!-- AJAX_Components/ArticlesTable.jsp -->
</div>

<script src="Scripts/WriterRoutines.js"></script>
