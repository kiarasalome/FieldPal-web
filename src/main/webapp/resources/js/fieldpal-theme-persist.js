/* ═══════════════════════════════════════════════════════════
   FIELDPAL — Persistencia de modo oscuro entre páginas XHTML
   No modifica la lógica CSS existente (body:has(#fpDarkToggle:checked)).
   Solo sincroniza el checkbox #fpDarkToggle con localStorage
   en cada carga de página, para que el modo oscuro se mantenga
   al navegar entre .xhtml.
   ═══════════════════════════════════════════════════════════ */
(function () {
    var STORAGE_KEY = 'fpDarkMode';
    var toggle = document.getElementById('fpDarkToggle');
    if (!toggle) return; // por si esta página aún no incluye el checkbox

    // 1. Al cargar: aplicar la preferencia guardada (si existe)
    var saved = localStorage.getItem(STORAGE_KEY);
    if (saved === 'true') {
        toggle.checked = true;
    } else if (saved === 'false') {
        toggle.checked = false;
    }
    // Si no hay valor guardado, se respeta lo que ya trae el checkbox (por defecto: claro)

    // 2. Al cambiar: guardar la nueva preferencia
    toggle.addEventListener('change', function () {
        localStorage.setItem(STORAGE_KEY, toggle.checked ? 'true' : 'false');
    });
})();