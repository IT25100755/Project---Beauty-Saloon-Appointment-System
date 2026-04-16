
// --- 1. Helper Functions for Cookies ---
function setCookie(name, value, days) {
    let expires = "";
    if (days) {
        let date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + (value || "")  + expires + "; path=/";
}

function getCookie(name) {
    let nameEQ = name + "=";
    let ca = document.cookie.split(';');
    for(let i=0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) == ' ') c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
}

function eraseCookie(name) {   
    document.cookie = name +'=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

// --- 2. Authentication UI Logic ---
function updateHeader() {
    const authSection = document.getElementById('auth-section');
    
    // In the future, this will check for a secure database token, not just a name!
    const user = getCookie('bodyGlowUser'); 

    if (user) {
        // USER IS LOGGED IN
        authSection.innerHTML = `
            <div class="flex items-center gap-4">
                <a href="dashboard.html" class="text-gray-300 hover:text-[#C9A961] transition-colors p-2 flex items-center gap-2">
                    <i data-lucide="user" class="w-5 h-5"></i>
                    <span class="text-[#C9A961] font-medium hidden md:inline">Welcome, ${user}</span>
                </a>
                
                
                <button class="bg-[#C9A961] hover:bg-[#D4AF37] text-[#1A1A1A] px-4 py-2 rounded-md font-semibold transition-all shadow-lg">
                    Book Now
                </button>
            </div>
        `;
    } else {
        // USER IS NOT LOGGED IN - Redirects to signin.html
        authSection.innerHTML = `
            <div class="flex items-center gap-3">
                <a href="signin.html" class="text-gray-300 hover:text-[#C9A961] transition-colors p-2 flex items-center gap-2">
                    <i data-lucide="user" class="w-5 h-5"></i>
                    <span class="hidden md:inline">Sign In</span>
                </a>
                <button onclick="window.location.href='bookingform.html'" class="glass-button bg-[#C9A961] flex items-center justify-center hover:scale-110 transition-transform text-[#1A1A1A] px-4 py-2 rounded-md font-semibold transition-all shadow-lg">
                    Book Now
                </button>
            </div>
        `;
        // Re-initialize Lucide icons for the newly injected HTML
        if (typeof lucide !== 'undefined') {
            lucide.createIcons();
        }
    }
}


// Run the check as soon as the DOM is ready
document.addEventListener('DOMContentLoaded', updateHeader);