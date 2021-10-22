<div class="content">
	<div id="game-page">
		<h1>
			<i class="fas fa-gamepad"></i>
			${game.name}
		</h1>
		
		<div 
			data-game-id="${game.id}" 
			data-game-name="${game.name}" 
			data-game-price="${game.price}" 
			class="game-flex"
		>
		
			<div class="game-info-container">
				<p class="game-price"> ${game.price}&euro; </p>
				<p class="game-release-date">Pubblicato il ${game.release} </p>
				<p class="game-description"> ${game.description} </p>

				<div class="button button--submit" id="add-to-cart">Aggiungi al carrello</div>
				<div class="button button--submit" id="play-game">Gioca!</div>
			</div>
				
			<div class="game-image-container">
				<div data-game-id="${game.id}" style="background-image: url('Static/GamePictures/${game.image}')" class="game-image"></div>
			</div>
			
		</div>
	</div>
</div>

<script src="Scripts/GameRoutines.js"></script>