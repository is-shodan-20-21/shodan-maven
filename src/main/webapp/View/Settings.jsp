<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="content">
	<div id="settings-page">
		<div class="settings-routines">
			<h1>
				<i class="far fa-address-book"></i>
				Impostazioni
			</h1>

			<div class="settings-container">
				<div id="settings-forms">
					<div id="settings-forms-loader">
						<!-- AJAX_Components/SettingsForms.jsp -->
					</div>
				</div>

				<div class="settings-tables">
					<div id="transactions-table-container">
						<h1>
							<i class="fas fa-shopping-basket"></i>Cronologia degli acquisti
						</h1>
						<div id="transactions-table-loader">
							<!-- AJAX_Components/TransactionsTable.jsp -->
						</div>
					</div>
					
					<div id="cards-table-container">
						<h1>
							<i class="fas fa-money-check"></i>Carte dell'utente
						</h1>
						<div id="cards-table-loader">
							<!-- AJAX_Components/TransactionsTable.jsp -->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="Scripts/SettingsRoutines.js"></script>