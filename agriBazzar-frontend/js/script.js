document.addEventListener('DOMContentLoaded', () => {
  console.log("AgriBazaar frontend loaded!");
  const toggle = document.getElementById('darkModeToggle');
  if (toggle) {
    // Set initial mode from localStorage
    if (localStorage.getItem('darkMode') === 'enabled') {
      document.body.classList.add('dark-mode');
      toggle.checked = true;
    }
    toggle.addEventListener('change', () => {
      if (toggle.checked) {
        document.body.classList.add('dark-mode');
        localStorage.setItem('darkMode', 'enabled');
      } else {
        document.body.classList.remove('dark-mode');
        localStorage.setItem('darkMode', 'disabled');
      }
    });
  }
});
