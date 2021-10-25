<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
%>

<div class="content">
	<h1>
		<i class="fas fa-shopping-cart"></i>
		Carrello
	</h1>
	
	<div id="cart-container">
		<table id="cart">
			<tr class="cart-item first-row">
				<td>Copertina</td>
				<td>Titolo</td>
				<td>Prezzo</td>
				<td class="nullable">&nbsp;</td>
			</tr>
			<c:forEach items="${games}" var="game">
				<tr class="cart-item"
					data-game-id=${game.id}
				>
					<td class="cart-picture-col"> 
						<img src="Static/GamePictures/${game.image}" />
					</td>
					
					<td class="cart-title-col"> 
						<p> ${game.name} </p> 
						<span>Rilasciato il ${game.release}</span>
					</td>
					
					<td class="cart-price-col"> ${game.price} &euro; </td>
					<td class="nullable del-cart-item cart-price-col">
						<span class="button button--danger button--unshadow">
							Rimuovi
						</span>
					</td>
				</tr>
			</c:forEach>
			<tr class="last-row">
				<td class="last-row-info"> &nbsp; </td>
				<td class="last-row-tag"> <strong>Totale</strong> </td>
				<td class="last-row-total"> ${total} &euro; </td>
				<td class="nullable">&nbsp;</td>
			</tr>
		</table>
		
		<div class="cart-buttons">
			<div class="button button--submit button--unshadow" id="cart-pay">
				Completa l'acquisto
			</div>
			
			<div class="button button--danger button--unshadow" id="cart-delete">
				Svuota il carrello
			</div>
		</div>
		
		<div class="cart-status">
			<i class="fas fa-comments-dollar"></i>&nbsp;
			<span></span>
		</div>
	</div>
	
</div>

<script src="Scripts/CartRoutines.js"></script>