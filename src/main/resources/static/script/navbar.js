(function () {

    var html =
        '<header class="fixed top-0 left-0 right-0 z-50 glass-header overflow-hidden">'
        + '<div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">'
        + '<div class="flex justify-between items-center py-4 gap-4 overflow-hidden">'

        /* Logo */
        + '<div class="flex items-center gap-2 shrink-0">'
        + '<i data-lucide="sparkles" class="w-8 h-8 text-[#C9A961]"></i>'
        + '<a href="index.html">'
        + '<span class="text-3xl font-serif text-[#C9A961] text-outline text-luxury-shadow whitespace-nowrap">'
        + 'Body Glow'
        + '</span>'
        + '</a>'
        + '</div>'

        /* Navigation */
        + '<b>'
        + '<nav '
        + 'class="hidden md:flex items-center flex-nowrap '
        + 'gap-3 lg:gap-5 xl:gap-6 '
        + 'text-shadow-glow text-[#C9A961] '
        + 'text-sm lg:text-base xl:text-lg '
        + 'font-medium whitespace-nowrap overflow-hidden" '
        + 'id="main-nav">'

        + '<a href="index.html" '
        + 'class="nav-link hover:scale-110 transition-all duration-300 transform inline-block" '
        + 'data-page="index">Home</a>'

        + '<a href="services.html" '
        + 'class="nav-link hover:scale-110 transition-all duration-300 transform inline-block" '
        + 'data-page="services">Services</a>'

        + '<a href="products.html" '
        + 'class="nav-link hover:scale-110 transition-all duration-300 transform inline-block" '
        + 'data-page="products">Products</a>'

        + '<a href="offers.html" '
        + 'class="nav-link hover:scale-110 transition-all duration-300 transform inline-block" '
        + 'data-page="offers">Offers</a>'

        + '<a href="feedback.html" '
        + 'class="nav-link hover:scale-110 transition-all duration-300 transform inline-block" '
        + 'data-page="feedback">Feedback</a>'

        + '<a href="bookingform.html" '
        + 'class="nav-link hover:scale-110 transition-all duration-300 transform inline-block" '
        + 'data-page="bookingform">Book Now</a>'

        + '<!-- Admin Link -->'
        + '<a href="admin.html" '
        + 'id="nav-admin-link" '
        + 'class="nav-link hover:scale-110 transition-all duration-300 transform inline-block hidden" '
        + 'title="Admin Panel">Admin</a>'

        + '</nav>'
        + '</b>'

        /* Auth Section */
        + '<div id="auth-section" '
        + 'class="flex items-center gap-2 shrink min-w-0 whitespace-nowrap overflow-hidden">'

        /* Username */
        + '<span '
        + 'id="nav-username" '
        + 'class="max-w-[120px] truncate inline-block align-middle '
        + 'text-[#C9A961] text-sm lg:text-base font-medium">'
        + 'Loading...'
        + '</span>'

        /* Sign Out Button */
        + '<button '
        + 'id="logout-btn" '
        + 'class="px-3 py-1.5 rounded-lg border border-[#C9A961] '
        + 'text-[#C9A961] text-sm hover:bg-[#C9A961] '
        + 'hover:text-black transition-all duration-300 shrink-0">'
        + 'Sign Out'
        + '</button>'

        + '</div>'

        + '</div>'
        + '</div>'
        + '</header>';

    var el = document.getElementById('navbar-placeholder');

    if (el) {
        el.innerHTML = html;
    }

    /* Highlight active nav link */
    var path = window.location.pathname;

    var page =
        path.substring(path.lastIndexOf('/') + 1)
            .replace('.html', '') || 'index';

    document.querySelectorAll('.nav-link[data-page]').forEach(function (a) {

        if (a.getAttribute('data-page') === page) {

            a.style.borderBottom = '2px solid #C9A961';
            a.style.paddingBottom = '2px';
        }
    });

    /* Example Username */
    var username = localStorage.getItem("username") || "User";

    var usernameEl = document.getElementById("nav-username");

    if (usernameEl) {
        usernameEl.textContent = username;
    }

    /* Logout */
    var logoutBtn = document.getElementById("logout-btn");

    if (logoutBtn) {

        logoutBtn.addEventListener("click", function () {

            localStorage.removeItem("username");

            window.location.href = "login.html";
        });
    }

})();