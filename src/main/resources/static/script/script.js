/* ================================================================
   Body Glow Salon — Main JavaScript
   Auth management, API helpers, toast notifications
   ================================================================ */

const API_BASE = '/api';

// ── Toast Notification ──────────────────────────────────────────
function showToast(message, type = 'info', duration = 3500) {
    const existing = document.querySelector('.toast');
    if (existing) existing.remove();

    const icons = { success: '✓', error: '✕', info: '✦' };
    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    toast.innerHTML = `<span style="font-size:1.1em;font-weight:900">${icons[type] || '✦'}</span><span>${message}</span>`;

    document.body.appendChild(toast);
    requestAnimationFrame(() => { requestAnimationFrame(() => toast.classList.add('show')); });

    setTimeout(() => {
        toast.classList.remove('show');
        setTimeout(() => toast.remove(), 450);
    }, duration);
}

// ── Cookie Helpers ─────────────────────────────────────────────────
function setCookie(name, value, days) {
    let expires = "";
    if (days) {
        const date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + (value || "") + expires + "; path=/; SameSite=Lax";
}

function getCookie(name) {
    const nameEQ = name + "=";
    const ca = document.cookie.split(';');
    for(let i=0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
    }
    return null;
}

function eraseCookie(name) {
    document.cookie = name +'=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

// ── Auth State Management ────────────────────────────────────────
function getCurrentUser() {
    try {
        const data = getCookie('bodyglow_current_user');
        return data ? JSON.parse(decodeURIComponent(data)) : null;
    } catch { return null; }
}

function getRegisteredUsers() {
    try {
        const data = localStorage.getItem('bodyglow_users');
        return data ? JSON.parse(data) : [];
    } catch { return []; }
}

function saveRegisteredUser(record) {
    const users = getRegisteredUsers();
    const idx = users.findIndex(u => u.email.toLowerCase() === record.email.toLowerCase());
    if (idx >= 0) users[idx] = record;
    else users.push(record);
    localStorage.setItem('bodyglow_users', JSON.stringify(users));
}

function setCurrentUser(record) {
    // Save login session in a cookie for 30 days
    setCookie('bodyglow_current_user', encodeURIComponent(JSON.stringify(record)), 30);
}

function signOut() {
    eraseCookie('bodyglow_current_user');
    showToast('Signed out. See you soon! ✨', 'info');
    setTimeout(() => window.location.href = 'index.html', 1100);
}

function requireAuth() {
    const user = getCurrentUser();
    if (!user) {
        sessionStorage.setItem('bodyglow_redirect', window.location.pathname);
        window.location.href = 'signin.html';
        return null;
    }
    return user;
}

// ── Render Auth Section in Header ───────────────────────────────
function renderAuthSection() {
    const el = document.getElementById('auth-section');
    if (!el) return;

    const user = getCurrentUser();
    if (user) {
        el.innerHTML = `
            <span class="text-[#C9A961] font-semibold hidden md:inline text-sm mr-1">
                Hello, ${escapeHtml(user.name.split(' ')[0])} ✨
            </span>
            <a href="dashboard.html"
               class="glass-button border border-[#C9A961] text-[#C9A961] hover:bg-[#C9A961] hover:text-[#1A1A1A] px-4 py-2 rounded-md transition-all text-sm font-semibold">
               Dashboard
            </a>
            <button onclick="signOut()"
                    class="text-gray-500 hover:text-white text-sm transition-colors ml-1 px-2 py-1 rounded hover:bg-white/5">
                Sign Out
            </button>`;
    } else {
        el.innerHTML = `
            <a href="signin.html"
               class="text-[#C9A961] hover:text-white text-sm font-semibold transition-colors">
               Sign In
            </a>
            <a href="signin.html"
               class="glass-button border border-[#C9A961] text-[#C9A961] hover:bg-[#C9A961] hover:text-[#1A1A1A] px-4 py-2 rounded-md transition-all text-sm font-semibold ml-2">
               Register
            </a>`;
    }
}

// ── API Helpers ──────────────────────────────────────────────────
async function apiGet(endpoint) {
    const res = await fetch(`${API_BASE}${endpoint}`);
    if (!res.ok) throw new Error(`${res.status} ${res.statusText}`);
    return res.json();
}

async function apiPost(endpoint, data) {
    const res = await fetch(`${API_BASE}${endpoint}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    });
    const text = await res.text();
    try { return { ok: res.ok, status: res.status, data: JSON.parse(text) }; }
    catch { return { ok: res.ok, status: res.status, data: text }; }
}

async function apiPut(endpoint) {
    const res = await fetch(`${API_BASE}${endpoint}`, { method: 'PUT' });
    const text = await res.text();
    try { return { ok: res.ok, data: JSON.parse(text) }; }
    catch { return { ok: res.ok, data: text }; }
}

// ── Utility ──────────────────────────────────────────────────────
function escapeHtml(str) {
    if (!str) return '';
    return String(str)
        .replace(/&/g, '&amp;').replace(/</g, '&lt;')
        .replace(/>/g, '&gt;').replace(/"/g, '&quot;');
}

function formatDateTime(isoString) {
    if (!isoString) return '—';
    const d = new Date(isoString);
    return d.toLocaleDateString('en-US', { weekday:'short', month:'short', day:'numeric', year:'numeric' })
        + ' at '
        + d.toLocaleTimeString('en-US', { hour:'2-digit', minute:'2-digit' });
}

function statusBadge(status) {
    const s = (status || '').toLowerCase();
    const cls = s === 'booked' ? 'badge-booked' : s === 'completed' ? 'badge-completed' : 'badge-cancelled';
    const dot = s === 'booked' ? '●' : s === 'completed' ? '✓' : '✕';
    return `<span class="badge ${cls}">${dot} ${status}</span>`;
}

// ── Init ─────────────────────────────────────────────────────────
document.addEventListener('DOMContentLoaded', () => {
    renderAuthSection();
    if (typeof lucide !== 'undefined') lucide.createIcons();
});
