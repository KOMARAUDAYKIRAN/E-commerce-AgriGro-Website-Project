/**
 * AGRIBAZAAR UI/UX ENHANCEMENT SYSTEM
 * ===================================
 * Author: GitHub Copilot Contribution
 * Date: October 2025
 * Description: JavaScript utilities for enhanced user experience
 */

// ==============================================
// TOAST NOTIFICATION SYSTEM
// ==============================================

class ToastNotification {
    constructor() {
        this.container = null;
        this.toastCount = 0;
        this.init();
    }

    init() {
        // Create toast container if it doesn't exist
        if (!document.querySelector('.toast-container')) {
            this.container = document.createElement('div');
            this.container.className = 'toast-container';
            document.body.appendChild(this.container);
        } else {
            this.container = document.querySelector('.toast-container');
        }
    }

    show(options) {
        const {
            type = 'info',
            title = '',
            message = '',
            duration = 5000,
            closable = true
        } = options;

        const toast = this.createToast(type, title, message, closable);
        this.container.appendChild(toast);

        // Auto-dismiss after duration
        if (duration > 0) {
            setTimeout(() => {
                this.dismiss(toast);
            }, duration);
        }

        return toast;
    }

    createToast(type, title, message, closable) {
        const toast = document.createElement('div');
        toast.className = `toast ${type}`;
        toast.id = `toast-${++this.toastCount}`;

        const icon = this.getIcon(type);
        
        toast.innerHTML = `
            <div class="toast-icon">${icon}</div>
            <div class="toast-content">
                ${title ? `<div class="toast-title">${title}</div>` : ''}
                <div class="toast-message">${message}</div>
            </div>
            ${closable ? '<button class="toast-close" onclick="Toast.dismiss(this.parentElement)">&times;</button>' : ''}
            <div class="toast-progress"></div>
        `;

        // Add click handler for close button
        const closeBtn = toast.querySelector('.toast-close');
        if (closeBtn) {
            closeBtn.addEventListener('click', (e) => {
                e.preventDefault();
                this.dismiss(toast);
            });
        }

        return toast;
    }

    getIcon(type) {
        const icons = {
            success: '✓',
            error: '✕',
            warning: '⚠',
            info: 'i'
        };
        return icons[type] || icons.info;
    }

    dismiss(toast) {
        if (toast && toast.parentElement) {
            toast.classList.add('hiding');
            setTimeout(() => {
                if (toast.parentElement) {
                    toast.parentElement.removeChild(toast);
                }
            }, 300);
        }
    }

    // Convenience methods
    success(message, title = 'Success') {
        return this.show({ type: 'success', title, message });
    }

    error(message, title = 'Error') {
        return this.show({ type: 'error', title, message });
    }

    warning(message, title = 'Warning') {
        return this.show({ type: 'warning', title, message });
    }

    info(message, title = 'Info') {
        return this.show({ type: 'info', title, message });
    }

    // Clear all toasts
    clear() {
        const toasts = this.container.querySelectorAll('.toast');
        toasts.forEach(toast => this.dismiss(toast));
    }
}

// Global Toast instance
const Toast = new ToastNotification();

// ==============================================
// LOADING SPINNER SYSTEM
// ==============================================

class LoadingSpinner {
    constructor() {
        this.overlay = null;
        this.activeSpinners = new Set();
    }

    // Show full-page loading overlay
    show(message = 'Loading...') {
        if (!this.overlay) {
            this.overlay = document.createElement('div');
            this.overlay.className = 'spinner-overlay';
            this.overlay.innerHTML = `
                <div style="text-align: center;">
                    <div class="spinner"></div>
                    <div style="margin-top: 16px; font-size: 16px; color: #666;">${message}</div>
                </div>
            `;
            document.body.appendChild(this.overlay);
        } else {
            this.overlay.classList.remove('hidden');
            const messageEl = this.overlay.querySelector('div:last-child');
            if (messageEl) messageEl.textContent = message;
        }
        document.body.style.overflow = 'hidden';
        return this.overlay;
    }

    // Hide full-page loading overlay
    hide() {
        if (this.overlay) {
            this.overlay.classList.add('hidden');
            document.body.style.overflow = '';
        }
    }

    // Add loading state to button
    addToButton(button, spinnerType = 'btn-spinner') {
        if (!button || button.classList.contains('btn-loading')) return;
        
        button.classList.add('btn-loading');
        button.disabled = true;
        
        // Store original content
        button.dataset.originalContent = button.innerHTML;
        
        // Add spinner
        const spinner = document.createElement('span');
        spinner.className = spinnerType;
        button.innerHTML = '';
        button.appendChild(spinner);
        button.appendChild(document.createTextNode('Loading...'));
        
        this.activeSpinners.add(button);
    }

    // Remove loading state from button
    removeFromButton(button) {
        if (!button || !button.classList.contains('btn-loading')) return;
        
        button.classList.remove('btn-loading');
        button.disabled = false;
        
        // Restore original content
        if (button.dataset.originalContent) {
            button.innerHTML = button.dataset.originalContent;
            delete button.dataset.originalContent;
        }
        
        this.activeSpinners.delete(button);
    }

    // Add inline spinner to element
    addToElement(element, spinnerType = 'spinner-dots') {
        const spinnerId = 'spinner-' + Date.now();
        const spinner = document.createElement('div');
        spinner.id = spinnerId;
        spinner.className = spinnerType;
        
        if (spinnerType === 'spinner-dots') {
            spinner.innerHTML = '<div class="dot"></div><div class="dot"></div><div class="dot"></div>';
        } else if (spinnerType === 'spinner-pulse') {
            spinner.className = 'spinner-pulse';
        } else {
            spinner.className = 'spinner';
        }
        
        element.appendChild(spinner);
        this.activeSpinners.add(spinner);
        return spinner;
    }

    // Remove spinner from element
    removeFromElement(element, spinnerId) {
        const spinner = spinnerId ? document.getElementById(spinnerId) : element.querySelector('[class*="spinner"]');
        if (spinner) {
            spinner.remove();
            this.activeSpinners.delete(spinner);
        }
    }

    // Clear all active spinners
    clearAll() {
        this.hide();
        this.activeSpinners.forEach(spinner => {
            if (spinner.tagName === 'BUTTON') {
                this.removeFromButton(spinner);
            } else {
                spinner.remove();
            }
        });
        this.activeSpinners.clear();
    }
}

// Global Loading instance
const Loading = new LoadingSpinner();

// ==============================================
// FORM VALIDATION SYSTEM
// ==============================================

class FormValidator {
    constructor() {
        this.validators = new Map();
        this.initDefaultValidators();
    }

    initDefaultValidators() {
        // Email validator
        this.addValidator('email', (value) => {
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            return {
                isValid: emailRegex.test(value),
                message: emailRegex.test(value) ? 'Valid email address' : 'Please enter a valid email address'
            };
        });

        // Password validator
        this.addValidator('password', (value) => {
            const hasMinLength = value.length >= 8;
            const hasUpperCase = /[A-Z]/.test(value);
            const hasLowerCase = /[a-z]/.test(value);
            const hasNumbers = /\d/.test(value);
            const hasSpecial = /[!@#$%^&*(),.?":{}|<>]/.test(value);

            const strength = [hasMinLength, hasUpperCase, hasLowerCase, hasNumbers, hasSpecial].filter(Boolean).length;
            
            let strengthText = '';
            let strengthClass = '';
            
            if (strength < 2) {
                strengthText = 'Weak password';
                strengthClass = 'weak';
            } else if (strength < 3) {
                strengthText = 'Fair password';
                strengthClass = 'fair';
            } else if (strength < 4) {
                strengthText = 'Good password';
                strengthClass = 'good';
            } else {
                strengthText = 'Strong password';
                strengthClass = 'strong';
            }

            return {
                isValid: hasMinLength && hasUpperCase && hasLowerCase && hasNumbers,
                message: strengthText,
                strength: strengthClass,
                requirements: {
                    minLength: hasMinLength,
                    upperCase: hasUpperCase,
                    lowerCase: hasLowerCase,
                    numbers: hasNumbers,
                    special: hasSpecial
                }
            };
        });

        // Required field validator
        this.addValidator('required', (value) => {
            return {
                isValid: value.trim().length > 0,
                message: value.trim().length > 0 ? 'Field is filled' : 'This field is required'
            };
        });

        // Username validator
        this.addValidator('username', (value) => {
            const usernameRegex = /^[a-zA-Z0-9_]{3,20}$/;
            return {
                isValid: usernameRegex.test(value),
                message: usernameRegex.test(value) ? 'Valid username' : 'Username must be 3-20 characters, letters, numbers, and underscores only'
            };
        });
    }

    addValidator(name, validatorFn) {
        this.validators.set(name, validatorFn);
    }

    validateField(input, validatorName) {
        const validator = this.validators.get(validatorName);
        if (!validator) return { isValid: true, message: '' };

        const result = validator(input.value);
        this.updateFieldUI(input, result, validatorName);
        return result;
    }

    updateFieldUI(input, result, validatorName) {
        // Remove existing validation classes
        input.classList.remove('valid', 'invalid');
        
        // Add appropriate class
        input.classList.add(result.isValid ? 'valid' : 'invalid');

        // Update validation message
        let messageContainer = input.parentElement.querySelector('.validation-message');
        if (!messageContainer) {
            messageContainer = document.createElement('div');
            messageContainer.className = 'validation-message';
            input.parentElement.appendChild(messageContainer);
        }

        messageContainer.className = `validation-message ${result.isValid ? 'success' : 'error'}`;
        messageContainer.innerHTML = `
            <span class="validation-icon">${result.isValid ? '✓' : '✕'}</span>
            <span class="validation-text">${result.message}</span>
        `;

        // Handle password strength indicator
        if (validatorName === 'password' && result.strength) {
            this.updatePasswordStrength(input, result);
        }
    }

    updatePasswordStrength(input, result) {
        let strengthContainer = input.parentElement.querySelector('.password-strength');
        if (!strengthContainer) {
            strengthContainer = document.createElement('div');
            strengthContainer.className = 'password-strength';
            strengthContainer.innerHTML = `
                <div class="password-strength-bar">
                    <div class="password-strength-fill"></div>
                </div>
                <div class="password-strength-text"></div>
            `;
            input.parentElement.appendChild(strengthContainer);
        }

        const fillElement = strengthContainer.querySelector('.password-strength-fill');
        const textElement = strengthContainer.querySelector('.password-strength-text');

        fillElement.className = `password-strength-fill ${result.strength}`;
        textElement.textContent = result.message;
    }

    setupFormValidation(form) {
        const inputs = form.querySelectorAll('input[data-validate]');
        
        inputs.forEach(input => {
            const validators = input.dataset.validate.split(',');
            
            // Real-time validation on input
            input.addEventListener('input', () => {
                validators.forEach(validator => {
                    this.validateField(input, validator.trim());
                });
            });

            // Validation on blur
            input.addEventListener('blur', () => {
                validators.forEach(validator => {
                    this.validateField(input, validator.trim());
                });
            });
        });

        // Form submission validation
        form.addEventListener('submit', (e) => {
            let isFormValid = true;
            
            inputs.forEach(input => {
                const validators = input.dataset.validate.split(',');
                validators.forEach(validator => {
                    const result = this.validateField(input, validator.trim());
                    if (!result.isValid) {
                        isFormValid = false;
                    }
                });
            });

            if (!isFormValid) {
                e.preventDefault();
                Toast.error('Please fix the validation errors before submitting');
                return false;
            }
        });
    }

    // Utility method to confirm password match
    setupPasswordConfirm(passwordField, confirmField) {
        const validateMatch = () => {
            const isMatch = passwordField.value === confirmField.value;
            
            confirmField.classList.remove('valid', 'invalid');
            confirmField.classList.add(isMatch ? 'valid' : 'invalid');

            let messageContainer = confirmField.parentElement.querySelector('.validation-message');
            if (!messageContainer) {
                messageContainer = document.createElement('div');
                messageContainer.className = 'validation-message';
                confirmField.parentElement.appendChild(messageContainer);
            }

            messageContainer.className = `validation-message ${isMatch ? 'success' : 'error'}`;
            messageContainer.innerHTML = `
                <span class="validation-icon">${isMatch ? '✓' : '✕'}</span>
                <span class="validation-text">${isMatch ? 'Passwords match' : 'Passwords do not match'}</span>
            `;

            return isMatch;
        };

        confirmField.addEventListener('input', validateMatch);
        confirmField.addEventListener('blur', validateMatch);
        passwordField.addEventListener('input', () => {
            if (confirmField.value) validateMatch();
        });
    }
}

// Global FormValidator instance
const Validator = new FormValidator();

// ==============================================
// UTILITY FUNCTIONS
// ==============================================

// Enhance existing buttons with loading states
function enhanceButton(button) {
    if (!button.classList.contains('btn-enhanced')) {
        button.classList.add('btn-enhanced');
        
        // Add primary class if no specific button class exists
        if (!button.classList.contains('btn-primary') && 
            !button.classList.contains('btn-secondary')) {
            button.classList.add('btn-primary');
        }
    }
}

// Setup form with enhanced validation
function setupForm(formSelector) {
    const form = document.querySelector(formSelector);
    if (form) {
        Validator.setupFormValidation(form);
        
        // Enhance form inputs
        const inputs = form.querySelectorAll('input');
        inputs.forEach(input => {
            if (!input.classList.contains('form-input')) {
                input.classList.add('form-input');
            }
        });

        // Enhance labels
        const labels = form.querySelectorAll('label');
        labels.forEach(label => {
            if (!label.classList.contains('form-label')) {
                label.classList.add('form-label');
            }
        });

        // Enhance buttons
        const buttons = form.querySelectorAll('button[type="submit"]');
        buttons.forEach(enhanceButton);
    }
    return form;
}

// Show page loading
function showPageLoading(message = 'Loading page...') {
    Loading.show(message);
}

// Hide page loading
function hidePageLoading() {
    Loading.hide();
}

// AJAX request with loading states
async function makeRequest(url, options = {}) {
    const {
        method = 'GET',
        data = null,
        loadingMessage = 'Processing...',
        successMessage = null,
        errorMessage = 'Request failed',
        showLoading = false,
        button = null
    } = options;

    try {
        if (showLoading) Loading.show(loadingMessage);
        if (button) Loading.addToButton(button);

        const response = await fetch(url, {
            method,
            headers: {
                'Content-Type': 'application/json',
                ...options.headers
            },
            body: data ? JSON.stringify(data) : null
        });

        const result = await response.json();

        if (response.ok) {
            if (successMessage) Toast.success(successMessage);
            return result;
        } else {
            throw new Error(result.message || errorMessage);
        }
    } catch (error) {
        Toast.error(error.message || errorMessage);
        throw error;
    } finally {
        if (showLoading) Loading.hide();
        if (button) Loading.removeFromButton(button);
    }
}

// Initialize enhanced UI components when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    // Auto-setup forms with data-enhance attribute
    const formsToEnhance = document.querySelectorAll('form[data-enhance="true"]');
    formsToEnhance.forEach(form => {
        setupForm('#' + form.id);
    });

    // Auto-enhance buttons with data-enhance attribute
    const buttonsToEnhance = document.querySelectorAll('button[data-enhance="true"]');
    buttonsToEnhance.forEach(enhanceButton);
});

// Export for global use
window.Toast = Toast;
window.Loading = Loading;
window.Validator = Validator;
window.enhanceButton = enhanceButton;
window.setupForm = setupForm;
window.showPageLoading = showPageLoading;
window.hidePageLoading = hidePageLoading;
window.makeRequest = makeRequest;