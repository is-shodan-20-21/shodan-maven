<div class="content no-padding">
	<div id="game-page">

		<div 
			style="background-image: url('Static/GameLandscapes/${game.landscape}')"
			class="game-landscape"
		>
			<div class="game-left">					
				<div class="game-image-container">
					<div data-game-id="${game.id}" style="background-image: url('Static/GamePictures/${game.image}')" class="game-image"></div>
				</div>
				<h1>
					<i class="fas fa-gamepad"></i>
					${game.name}
				</h1>
				<h2 class="game-release-date">
					Pubblicato il ${game.release} 
				</h2>
			</div>

			<div class="game-right">
				<div class="button button--cart button--submit" id="add-to-cart">
					<div>
						<div class="add-to-cart-icon">${game.price}&euro;</div>
						<div class="add-to-cart-text">Aggiungi al carrello</div>
					</div>
				</div>
				<div class="button button--play button--submit" id="play-game">
					<div>
						<div class="play-game-icon">
							<i class="fas fa-play"></i>
						</div>
						<div class="play-game-text">
							Gioca!
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div 
			data-game-id="${game.id}" 
			data-game-name="${game.name}" 
			data-game-price="${game.price}" 
			class="game-flex"
		>
		
			<div class="game-info-container">
				<h1>Descrizione</h1>
				<p class="game-description"> ${game.description} </p>
			</div>	
		</div>
	</div>
</div>

<script src="Scripts/GameRoutines.js"></script>