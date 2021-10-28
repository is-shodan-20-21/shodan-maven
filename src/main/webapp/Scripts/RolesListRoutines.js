$(document).ready(() => {
    function openRolesList(e) {
        e.stopImmediatePropagation();

        if($("#roles-link").hasClass("selected")) {
            $("#roles-link").removeClass("selected");
            $(".fa-chevron-right").removeClass("rotate-icon");
            $("#roles-items").hide();
        } else {
            $("#roles-link").addClass("selected");
            $(".fa-chevron-right").addClass("rotate-icon");
            $("#roles-items").show();
        }
    }

    $(".role-selector-icon").click((e) => openRolesList(e))
    $("#roles-link").click((e) => openRolesList(e));

    $(".role-item").click(
        function() {
            let requestedRole = $(this).attr("data-role-name");

            console.log("# Shodan [Requesting " + requestedRole +" views]");

            let parsed_path = window.location.href.substring(0, window.location.href.indexOf("?"));
			window.history.pushState(null, null, parsed_path);

            var NAV_HANDLER = $.ajax(
                {
                    type: "GET",
                    url: "ShodanViews",
                    data: {
                        view: "NAV",
                        requestedRole: requestedRole,
                        cookie: navigator.cookieEnabled,
                        jsession: window.location.href.substring(
                            window.location.href.lastIndexOf("=") + 1
                        )
                    },
                    error: (data) => console.log("# ShodanViews - 4**/5**" + data),
                    success: (data) => {
                        localStorage.setItem("last-nav", data.split("/")[2].split(".")[0]); 
                        $("nav").load(data);
                    }
                }
            );

            var MAIN_HANDLER = $.ajax(
                {
                    type: "GET",
                    url: "ShodanViews",
                    data: {
                        view: "MAIN",
                        requestedRole: requestedRole,
                        cookie: navigator.cookieEnabled,
                        jsession: window.location.href.substring(
                            window.location.href.lastIndexOf("=") + 1
                        )
                    },
                    error: (data) => console.log("# ShodanViews - 4**/5**" + data),
                    success: (data) => { 
                        console.log("# Shodan [Dashboard loaded]");
                        localStorage.setItem("last-page", data.split("/")[1].split(".")[0]); 
                        $("#app").load(data);  
                    }
                }
            );
        }
    );
});