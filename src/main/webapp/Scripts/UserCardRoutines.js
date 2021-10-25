$(document).ready(
    () => {
        $("#add-card").click(
            () => {
                if(navigator.cookieEnabled)
                    localStorage.setItem("last-page", "Payment");
                $("#app").load("View/Payment.jsp");
            }
        )
    }
)