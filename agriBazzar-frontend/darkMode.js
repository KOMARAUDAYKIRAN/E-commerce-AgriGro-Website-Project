document.addEventListener('DOMContentLoaded', () => {
    console.log("Dark mode script loaded!");

    const toggleBtn = document.getElementById('toggle-theme');
    const icon = document.getElementById('theme-icon');

    const savedTheme = localStorage.getItem('theme');
    if (savedTheme === 'dark') {
        document.body.classList.add('dark-mode');
        if (icon) {
            icon.classList.remove('fa-circle-half-stroke');
            icon.classList.add('fa-sun');
        }
    }

    if (toggleBtn && icon) {
        toggleBtn.addEventListener('click', () => {
            document.body.classList.toggle('dark-mode');

            const isDark = document.body.classList.contains('dark-mode');

            if (isDark) {
                icon.classList.remove('fa-circle-half-stroke');
                icon.classList.add('fa-sun');
                localStorage.setItem('theme', 'dark');
            } else {
                icon.classList.remove('fa-sun');
                icon.classList.add('fa-circle-half-stroke');
                localStorage.setItem('theme', 'light');
            }
        });
    }
});
