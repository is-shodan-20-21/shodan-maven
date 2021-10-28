<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="content">
	<div class="game-header-flex">
		<div>
			<h1>
				<i class="fas fa-comments-dollar"></i>
				Esplora il catalogo
			</h1>
		</div>

		<div class="game-header-search">
			<input id="game-search-input" placeholder="Cerca un gioco..." type="text">
		</div>
	</div>
	
	<div class="game-confirm">
		<i class="fas fa-comments-dollar"></i>&nbsp;
		<span></span>
	</div>
					
	<div id="shop-games" class="games">
		<!-- View/AJAX_Components/GameList.jsp -->
	</div>
</div>

<script src="Scripts/ShopRoutines.js"></script>