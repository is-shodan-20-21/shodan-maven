<%@ page 
	language="java" 
	contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="content-type" content="text/html; charset=utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="icon" href="Static/Assets/Icon.png" type="image/x-icon" />
		<title>Shodan</title>
		
		<!-- Stylesheet -->
		<link rel="stylesheet" href="Style/App.css">
		
		<!-- FontAwesome Icons -->
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.2/css/all.css">
		
		<!-- jQuery -->
		<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

		<!-- Karla -->
		<link href="https://fonts.googleapis.com/css2?family=Karla:ital,wght@0,200;0,300;0,400;0,500;0,600;0,700;0,800;1,200;1,300;1,400;1,500;1,600;1,700;1,800&display=swap" rel="stylesheet">
		
		<!-- AJAX & JavaScript Routines -->
		<script src="Scripts/AppRoutines.js"></script>
	</head>
	
	<body>
		<nav></nav>
		<main>
			<div id="cta">
				<!-- 
					Il CTA di login/registrazione viene mostrato esclusivamente se non si è loggati.
					Tuttavia, viene caricato a prescindere per evitare problemi di flickering.
				-->
				<a href="index.jsp">
					<strong>Effettua l'accesso</strong> oppure 
					<strong>crea un account</strong>
				</a> per sfruttare tutte le funzionalità di Shodan!
			</div>
			<div id="app">
				<!--
					Shodan è ispirato a React, Angular e Vue.

					Analogamente ai tre framework sopracitati, utilizza un'architettura a componenti.
					I componenti sono porzioni di template inserite all'interno dei container ..
					.. che a loro volta sono contenitori di componenti.

					{APP}, e in particolare il div #app, rappresentano il container-madre di Shodan.
					Al suo interno, possono coesistere più container o semplicemente più componenti. 
					Ad esempio, {DASHBOARD} è un container che contiene due componenti (una lista di giochi e una lista di notizie) ..
					.. oltre che un gioco in evidenza, che però non rappresenta un componente in quanto statico e immutabile.

					{APP} inizia il suo ciclo di vita caricando in sé, di default, il container {DASHBOARD} e il componente {NAV}.
					Una volta renderizzati, ambe due evocheranno asincronicamente ulteriori componenti, oppure eseguiranno delle azioni.

					Il componente {NAV} permette di navigare Shodan. Cliccare su una voce di {NAV} produrrà un caricamento asincrono ..
					.. finalizzato a sostituire il container {DASHBOARD} con un altro container o componente.

					Ad ogni componente e container è associata una Routine.
					Le Routines sono file JavaScript che determinano il comportamento del componente durante il suo ciclo di vita.

					Infine, componenti e container sono stilizzati in uno stylesheet localizzato in /Style/.
					Risorse statiche come immagini e vettoriali sono depositati in /Static/.
				-->
			</div>	
		</main>
	</body>
</html>