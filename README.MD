# AgriBazaar 🚜🌱

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Build](https://img.shields.io/badge/Build-Passing-brightgreen.svg)]()
[![Tech Stack](<https://img.shields.io/badge/Backend-Java%20(Spring%20Boot)-blue.svg>)]()
[![Frontend](https://img.shields.io/badge/Frontend-HTML%2C%20CSS%2C%20JS-yellow.svg)]()
[![Database](https://img.shields.io/badge/Database-MySQL%20%7C%20PostgreSQL-orange.svg)]()
[![Server](https://img.shields.io/badge/Server-Apache%20Tomcat-lightgrey.svg)]()

---

## 🌟 Overview

**AgriBazaar** is a modern, full-stack e-commerce platform for the agricultural sector. It connects **farmers**, **suppliers**, and **buyers** for seamless transactions of seeds, fertilizers, pesticides, and farm equipment. Built with Java, Spring Boot, and MySQL/PostgreSQL, it offers a scalable, secure, and user-friendly experience.

---

## ✨ Key Features

- 🛒 **Shopping Cart**: Add, update, and remove products before checkout.
- 🔍 **Advanced Product Catalog**: Search, filter, and sort products by category, price, and more.
- 💳 **Secure Payments**: Integrated with trusted gateways (PayPal, Stripe, etc.).
- 🚚 **Order Tracking**: Real-time order status and delivery updates.
- 🧑‍🌾 **Farmer & Supplier Portal**: List products, manage inventory, and view analytics.
- 🛡️ **Admin Dashboard**: Manage users, products, and orders with ease.
- 🌙 **Dark Mode**: Toggle for comfortable browsing in any environment.
- ⭐ **User Reviews & Ratings**: Build trust with transparent feedback.
- 📱 **Responsive Design**: Works beautifully on desktop and mobile.
- 🔒 **Role-Based Access**: Secure, personalized experience for buyers, sellers, and admins.

---

## 💡 Why AgriBazaar?

- **Empowers Farmers**: Direct-to-customer sales, better margins, and digital reach.
- **Transparent Marketplace**: No hidden fees, clear product info, and verified sellers.
- **Community-Driven**: Open-source, welcoming to contributors and new ideas.
- **Modern Tech Stack**: Built with best practices for performance and security.
- **Scalable & Extensible**: Ready for new features, integrations, and growth.

---

## 🛣️ Roadmap

- [x] Product Catalog & Search
- [x] Cart & Checkout
- [x] Order Tracking
- [x] Dark Mode
- [ ] Wishlist/Favorites
- [ ] Push Notifications
- [ ] Multi-language Support
- [ ] AI-based Recommendations
- [ ] Mobile App (React Native/Flutter)
- [ ] More payment gateways

---

## 📋 Table of Contents

- [Features](#features)
- [Screenshots](#screenshots)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

---

## 🚀 Features

- User Registration & Login
- Product Catalog (search, filter, sort)
- Shopping Cart & Secure Checkout
- Payment Gateway Integration
- Order Management & Tracking
- User Reviews & Ratings
- Admin Dashboard
- **Dark Mode Toggle** (NEW!)

---

## 🖼️ Screenshots

> ![Product Catalog Screenshot](docs/screenshots/product-catalog.png) > ![Cart Screenshot](docs/screenshots/cart.png)

---

## 🛠️ Tech Stack

| Layer    | Technologies                  |
| -------- | ----------------------------- |
| Frontend | HTML, CSS, JavaScript, JSP    |
| Backend  | Java, Spring Boot, Spring MVC |
| Database | MySQL / PostgreSQL            |
| Server   | Apache Tomcat                 |
| ORM      | Hibernate / JPA               |

---

## ⚡ Getting Started

### 1. **Clone the Repository**

```bash
git clone https://github.com/yourusername/agribazaar.git
cd agribazaar
```

### 2. **Configure the Database**

Edit `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/agribazaar
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. **Build & Run the Backend**

```bash
cd E-commerce-AgriGro-Website-Project/agriBazaar-backend
mvn clean install
mvn spring-boot:run
```

### 4. **Run the Frontend**

- Open `agriBazzar-frontend/index.html` in your browser, or use Live Server/localhost.
- For JSP pages, deploy to Tomcat or use the embedded server.

---

## 🧭 Project Structure

```
E-commerce-AgriGro-Website-Project/
├── agriBazaar-backend/
│   ├── src/main/java/com/agriBazaar/backend/
│   ├── src/main/resources/
│   └── pom.xml
├── agriBazzar-frontend/
│   ├── index.html
│   ├── cart.html
│   ├── css/
│   └── js/
└── README.MD
```

---

## 🤝 Contributing

We welcome contributions! To get started:

1. Fork the repo and create your branch: `git checkout -b feature/your-feature`
2. Commit your changes: `git commit -am 'Add new feature'`
3. Push to the branch: `git push origin feature/your-feature`
4. Open a Pull Request

**Please read [CONTRIBUTING.md](CONTRIBUTING.md) for full guidelines.**

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---

## 📬 Contact

**Developer:** Uday Kiran  
**Email:** [komaraudaykiran2003@gmail.com](mailto:komaraudaykiran2003@gmail.com)  
**LinkedIn:** [Uday Kiran Komara](https://www.linkedin.com/in/udaykirankomara)

Special thanks to all contributors and the open-source community!
