/**
 * COMPREHENSIVE DARK MODE SYSTEM
 * ================================
 * Author: GitHub Copilot Contribution
 * Date: October 2025
 * Description: Advanced dark mode implementation with system theme detection,
 * smooth transitions, accessibility features, and cross-page persistence.
 */

class DarkModeManager {
    constructor() {
        this.themes = {
            LIGHT: 'light',
            DARK: 'dark',
            SYSTEM: 'system'
        };
        
        this.currentTheme = this.themes.SYSTEM;
        this.systemPrefersDark = false;
        this.mediaQuery = null;
        this.toggleButton = null;
        this.observers = [];
        
        this.init();
    }

    /**
     * Initialize the dark mode system
     */
    init() {
        this.setupSystemThemeDetection();
        this.loadSavedTheme();
        this.createToggleButton();
        this.setupKeyboardShortcuts();
        this.setupAccessibilityFeatures();
        this.applyTheme();
        
        // Notify that dark mode system is ready
        this.notifyObservers('initialized');
        
        console.log('🌓 Enhanced Dark Mode System initialized');
    }

    /**
     * Set up system theme detection
     */
    setupSystemThemeDetection() {
        if (window.matchMedia) {
            this.mediaQuery = window.matchMedia('(prefers-color-scheme: dark)');
            this.systemPrefersDark = this.mediaQuery.matches;
            
            // Listen for system theme changes
            this.mediaQuery.addEventListener('change', (e) => {
                this.systemPrefersDark = e.matches;
                if (this.currentTheme === this.themes.SYSTEM) {
                    this.applyTheme();
                }
                this.notifyObservers('systemThemeChanged', { prefersDark: e.matches });
            });
        }
    }

    /**
     * Load saved theme preference
     */
    loadSavedTheme() {
        const savedTheme = localStorage.getItem('agribazaar-theme');
        if (savedTheme && Object.values(this.themes).includes(savedTheme)) {
            this.currentTheme = savedTheme;
        } else {
            // Default to system preference
            this.currentTheme = this.themes.SYSTEM;
        }
    }

    /**
     * Create and insert theme toggle button
     */
    createToggleButton() {
        // Remove existing toggle if present
        const existingToggle = document.getElementById('theme-toggle-enhanced');
        if (existingToggle) {
            existingToggle.remove();
        }

        // Create new toggle button
        this.toggleButton = document.createElement('button');
        this.toggleButton.id = 'theme-toggle-enhanced';
        this.toggleButton.className = 'theme-toggle';
        this.toggleButton.setAttribute('aria-label', 'Toggle theme');
        this.toggleButton.setAttribute('title', 'Toggle between light, dark, and system theme');
        
        this.updateToggleButton();
        
        // Add event listener
        this.toggleButton.addEventListener('click', () => this.cycleTheme());
        
        // Insert into page
        document.body.appendChild(this.toggleButton);
    }

    /**
     * Update toggle button appearance and text
     */
    updateToggleButton() {
        if (!this.toggleButton) return;

        const icons = {
            [this.themes.LIGHT]: '☀️',
            [this.themes.DARK]: '🌙',
            [this.themes.SYSTEM]: '🔄'
        };

        const labels = {
            [this.themes.LIGHT]: 'Light',
            [this.themes.DARK]: 'Dark',
            [this.themes.SYSTEM]: 'System'
        };

        const currentIcon = icons[this.currentTheme];
        const currentLabel = labels[this.currentTheme];

        this.toggleButton.innerHTML = `
            <span class="theme-toggle-icon" role="img" aria-hidden="true">${currentIcon}</span>
            <span class="theme-toggle-text">${currentLabel}</span>
        `;

        // Update aria-label for accessibility
        this.toggleButton.setAttribute('aria-label', `Current theme: ${currentLabel}. Click to change theme.`);
    }

    /**
     * Cycle through themes: system -> light -> dark -> system
     */
    cycleTheme() {
        const themeOrder = [this.themes.SYSTEM, this.themes.LIGHT, this.themes.DARK];
        const currentIndex = themeOrder.indexOf(this.currentTheme);
        const nextIndex = (currentIndex + 1) % themeOrder.length;
        
        this.setTheme(themeOrder[nextIndex]);
    }

    /**
     * Set specific theme
     * @param {string} theme - Theme to set ('light', 'dark', 'system')
     */
    setTheme(theme) {
        if (!Object.values(this.themes).includes(theme)) {
            console.warn(`Invalid theme: ${theme}`);
            return;
        }

        const previousTheme = this.currentTheme;
        this.currentTheme = theme;
        
        // Save to localStorage
        localStorage.setItem('agribazaar-theme', theme);
        
        // Apply the theme
        this.applyTheme();
        
        // Update toggle button
        this.updateToggleButton();
        
        // Notify observers
        this.notifyObservers('themeChanged', { 
            from: previousTheme, 
            to: theme, 
            effectiveTheme: this.getEffectiveTheme() 
        });

        // Show notification
        this.showThemeChangeNotification(theme);
    }

    /**
     * Get the effective theme (resolves 'system' to actual theme)
     */
    getEffectiveTheme() {
        if (this.currentTheme === this.themes.SYSTEM) {
            return this.systemPrefersDark ? this.themes.DARK : this.themes.LIGHT;
        }
        return this.currentTheme;
    }

    /**
     * Apply the current theme to the document
     */
    applyTheme() {
        const effectiveTheme = this.getEffectiveTheme();
        const isDark = effectiveTheme === this.themes.DARK;
        
        // Add transition class for smooth animation
        document.body.classList.add('theme-transitioning');
        
        // Apply theme classes
        document.body.classList.toggle('dark-mode', isDark);
        document.documentElement.setAttribute('data-theme', effectiveTheme);
        
        // Update meta theme-color for mobile browsers
        this.updateThemeColor(isDark);
        
        // Remove transition class after animation
        setTimeout(() => {
            document.body.classList.remove('theme-transitioning');
        }, 300);

        console.log(`🎨 Theme applied: ${this.currentTheme} (effective: ${effectiveTheme})`);
    }

    /**
     * Update theme color meta tag for mobile browsers
     */
    updateThemeColor(isDark) {
        let metaThemeColor = document.querySelector('meta[name="theme-color"]');
        if (!metaThemeColor) {
            metaThemeColor = document.createElement('meta');
            metaThemeColor.name = 'theme-color';
            document.head.appendChild(metaThemeColor);
        }
        
        metaThemeColor.content = isDark ? '#1a1a1a' : '#4CAF50';
    }

    /**
     * Setup keyboard shortcuts for theme switching
     */
    setupKeyboardShortcuts() {
        document.addEventListener('keydown', (e) => {
            // Ctrl/Cmd + Shift + D to toggle dark mode
            if ((e.ctrlKey || e.metaKey) && e.shiftKey && e.key.toLowerCase() === 'd') {
                e.preventDefault();
                this.cycleTheme();
            }
        });
    }

    /**
     * Setup accessibility features
     */
    setupAccessibilityFeatures() {
        // Respect user's motion preferences
        if (window.matchMedia('(prefers-reduced-motion: reduce)').matches) {
            document.body.classList.add('reduce-motion');
        }

        // Add high contrast support
        if (window.matchMedia('(prefers-contrast: high)').matches) {
            document.body.classList.add('high-contrast');
        }
    }

    /**
     * Show theme change notification
     */
    showThemeChangeNotification(theme) {
        // Only show if Toast system is available
        if (typeof Toast !== 'undefined') {
            const messages = {
                [this.themes.LIGHT]: 'Switched to light theme ☀️',
                [this.themes.DARK]: 'Switched to dark theme 🌙',
                [this.themes.SYSTEM]: 'Using system theme preference 🔄'
            };
            
            Toast.info(messages[theme], 'Theme Changed');
        } else {
            // Fallback notification
            console.log(`Theme changed to: ${theme}`);
        }
    }

    /**
     * Add observer for theme changes
     */
    addObserver(callback) {
        this.observers.push(callback);
    }

    /**
     * Remove observer
     */
    removeObserver(callback) {
        this.observers = this.observers.filter(obs => obs !== callback);
    }

    /**
     * Notify all observers of theme events
     */
    notifyObservers(event, data = {}) {
        this.observers.forEach(callback => {
            try {
                callback(event, { ...data, currentTheme: this.currentTheme, effectiveTheme: this.getEffectiveTheme() });
            } catch (error) {
                console.error('Error in theme observer:', error);
            }
        });
    }

    /**
     * Get current theme information
     */
    getThemeInfo() {
        return {
            currentTheme: this.currentTheme,
            effectiveTheme: this.getEffectiveTheme(),
            systemPrefersDark: this.systemPrefersDark,
            isDarkMode: this.getEffectiveTheme() === this.themes.DARK
        };
    }

    /**
     * Force refresh theme (useful after dynamic content changes)
     */
    refreshTheme() {
        this.applyTheme();
    }

    /**
     * Destroy the dark mode manager (cleanup)
     */
    destroy() {
        if (this.toggleButton) {
            this.toggleButton.remove();
        }
        if (this.mediaQuery) {
            this.mediaQuery.removeEventListener('change', this.handleSystemThemeChange);
        }
        this.observers = [];
    }
}

// ==============================================
// UTILITY FUNCTIONS
// ==============================================

/**
 * Initialize dark mode for pages that need custom setup
 */
function initializePageDarkMode(pageSpecificOptions = {}) {
    const options = {
        autoCreateToggle: true,
        showNotifications: true,
        enableKeyboardShortcuts: true,
        ...pageSpecificOptions
    };

    if (!options.autoCreateToggle && window.darkModeManager) {
        // Don't create toggle if one exists and auto-create is disabled
        return window.darkModeManager;
    }

    return window.darkModeManager;
}

/**
 * Apply dark mode to dynamically created content
 */
function applyDarkModeToElement(element) {
    if (!window.darkModeManager) return;
    
    const isDark = window.darkModeManager.getEffectiveTheme() === 'dark';
    element.classList.toggle('dark-mode', isDark);
}

/**
 * Get current theme for conditional styling
 */
function getCurrentTheme() {
    return window.darkModeManager ? window.darkModeManager.getThemeInfo() : null;
}

/**
 * Watch for theme changes (useful for components that need to react)
 */
function watchThemeChanges(callback) {
    if (window.darkModeManager) {
        window.darkModeManager.addObserver(callback);
    }
}

// ==============================================
// INITIALIZATION
// ==============================================

// Initialize when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    // Create global dark mode manager instance
    window.darkModeManager = new DarkModeManager();
    
    // Add legacy support for existing toggle elements
    const legacyToggle = document.getElementById('toggle-theme');
    if (legacyToggle) {
        legacyToggle.addEventListener('click', (e) => {
            e.preventDefault();
            window.darkModeManager.cycleTheme();
        });
    }
});

// Export for module systems
if (typeof module !== 'undefined' && module.exports) {
    module.exports = { 
        DarkModeManager, 
        initializePageDarkMode, 
        applyDarkModeToElement, 
        getCurrentTheme,
        watchThemeChanges 
    };
}

// Global utility functions
window.initializePageDarkMode = initializePageDarkMode;
window.applyDarkModeToElement = applyDarkModeToElement;
window.getCurrentTheme = getCurrentTheme;
window.watchThemeChanges = watchThemeChanges;