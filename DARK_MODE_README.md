# 🌓 AgriBazaar Dark Mode System

A comprehensive dark mode implementation for the AgriBazaar e-commerce platform, providing users with a seamless light/dark theme experience across all pages.

## ✨ Features

- **🎨 Complete Theme System**: Full light/dark mode support across all components
- **🔄 Smart Theme Detection**: Automatically detects system theme preferences
- **💾 Persistent Theme**: Remembers user's theme choice across sessions
- **⚡ Instant Switching**: Smooth transitions between themes
- **🎯 Floating Toggle**: Always-visible theme toggle button
- **⌨️ Keyboard Shortcuts**: Alt+D to quickly toggle themes
- **♿ Accessibility**: Full ARIA support and screen reader friendly
- **📱 Responsive**: Works perfectly on all device sizes

## 🚀 Implementation

### Files Added/Modified

#### New Files
- `css/dark-mode.css` - Comprehensive dark mode styles
- `js/enhanced-dark-mode.js` - Advanced dark mode functionality
- `css/dark-mode-toggle.css` - Toggle button styles (integrated into main CSS)

#### Updated Files
- `index.html` - Added dark mode support
- `product-catalog.html` - Added dark mode support
- `community.html` - Added dark mode support  
- `login.html` - Added dark mode support
- `signup.html` - Added dark mode support
- `Cart.html` - Added dark mode support
- `auction.html` - Added dark mode support
- `market-prices.html` - Added dark mode support
- `news.html` - Added dark mode support
- `landing.html` - Added dark mode support

## 🛠️ Technical Details

### CSS Architecture

The dark mode system uses CSS custom properties (CSS variables) for flexible theming:

```css
:root {
    --bg-color: #ffffff;
    --text-color: #333333;
    --border-color: #dddddd;
    /* ... more variables */
}

[data-theme="dark"] {
    --bg-color: #1a1a1a;
    --text-color: #ffffff;
    --border-color: #444444;
    /* ... dark theme overrides */
}
```

### JavaScript Features

The `DarkModeManager` class provides:

- **Theme Management**: Cycle between light, dark, and auto themes
- **System Detection**: Responds to OS theme changes
- **Local Storage**: Persists user preferences
- **Observer Pattern**: Notifies components of theme changes
- **Accessibility**: ARIA attributes and keyboard navigation

### Usage

#### Automatic Initialization
```javascript
// Dark mode initializes automatically on page load
document.addEventListener('DOMContentLoaded', function() {
    // Dark mode is ready!
});
```

#### Manual Theme Control
```javascript
// Get the dark mode manager
const darkMode = window.darkModeManager;

// Toggle theme
darkMode.toggleTheme();

// Set specific theme
darkMode.setTheme('dark');
darkMode.setTheme('light');
darkMode.setTheme('auto');

// Get current theme
const currentTheme = darkMode.getCurrentTheme();
```

#### Listen for Theme Changes
```javascript
// Using the global function
watchThemeChanges((event, themeInfo) => {
    console.log('Theme changed to:', themeInfo.newTheme);
    // Apply custom dark mode logic here
});

// Using the manager directly
darkMode.addThemeObserver((event, themeInfo) => {
    // Handle theme change
});
```

## 🎯 Integration Guide

### Adding Dark Mode to New Pages

1. **Add CSS Link** (in `<head>`):
```html
<link rel="stylesheet" href="css/dark-mode.css">
```

2. **Add JavaScript** (before `</body>`):
```html
<script src="js/enhanced-dark-mode.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        if (window.watchThemeChanges) {
            watchThemeChanges((event, themeInfo) => {
                // Apply dark mode to page-specific elements
                const customElements = document.querySelectorAll('.custom-component');
                customElements.forEach(el => applyDarkModeToElement(el));
            });
        }
    });
</script>
```

### Custom Component Styling

For new components, use CSS variables:

```css
.my-component {
    background-color: var(--card-bg);
    color: var(--text-color);
    border: 1px solid var(--border-color);
    transition: background-color 0.3s ease, color 0.3s ease;
}
```

## 🎨 Customization

### Theme Colors

Modify CSS variables in `css/dark-mode.css`:

```css
[data-theme="dark"] {
    --primary-color: #4a90e2;    /* Adjust primary color */
    --bg-color: #1a1a1a;        /* Background color */
    --text-color: #ffffff;       /* Text color */
    /* ... customize other variables */
}
```

### Toggle Button

Customize the floating toggle button:

```css
.dark-mode-toggle {
    top: 20px;           /* Position from top */
    right: 20px;         /* Position from right */
    width: 50px;         /* Button size */
    height: 50px;        /* Button size */
    /* ... other styles */
}
```

## ⚡ Performance

- **Minimal Bundle Size**: ~26KB total (CSS + JS)
- **Zero Dependencies**: Pure vanilla JavaScript
- **Optimized Transitions**: GPU-accelerated animations
- **Efficient Storage**: Uses localStorage for persistence

## ♿ Accessibility Features

- **Screen Reader Support**: Proper ARIA labels and roles
- **Keyboard Navigation**: Tab and Enter key support
- **Focus Management**: Clear focus indicators
- **Color Contrast**: WCAG compliant color schemes
- **Reduced Motion**: Respects user's motion preferences

## 🔧 Browser Support

- ✅ Chrome 88+
- ✅ Firefox 87+
- ✅ Safari 14+
- ✅ Edge 88+
- ✅ Mobile browsers

## 🚀 Future Enhancements

Potential improvements for future versions:

- **Custom Theme Colors**: User-selectable accent colors
- **Theme Scheduling**: Automatic theme switching based on time
- **Multiple Dark Themes**: Different dark theme variants
- **Animation Preferences**: Customizable transition speeds
- **Theme Presets**: Pre-configured theme combinations

## 📝 Changelog

### Version 1.0.0 (Current)
- ✅ Complete dark mode system implementation
- ✅ 10+ pages with dark mode support
- ✅ Floating toggle button
- ✅ System theme detection
- ✅ Persistent theme storage
- ✅ Accessibility features
- ✅ Smooth transitions

## 🤝 Contributing

To contribute to the dark mode system:

1. Test theme switching on all pages
2. Verify accessibility with screen readers
3. Check mobile responsiveness
4. Ensure smooth transitions
5. Test browser compatibility

## 📄 License

This dark mode system is part of the AgriBazaar project and follows the same license terms.

---

**Made with ❤️ for AgriBazaar** - Empowering Agriculture with Modern UI/UX