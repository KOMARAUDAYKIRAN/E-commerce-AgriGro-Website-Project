// // import { getAuth, createUserWithEmailAndPassword } from "https://www.gstatic.com/firebasejs/9.23.0/firebase-auth.js";
// // import { app } from "./firebase.js";

// const auth = getAuth(app);

// const signupForm = document.getElementById("signup-form");
// const message = document.getElementById("message");

// signupForm.addEventListener("submit", async (e) => {
//   e.preventDefault();
//   const email = document.getElementById("email").value;
//   const password = document.getElementById("password").value;

//   try {
//     await createUserWithEmailAndPassword(auth, email, password);
//     message.textContent = "Account created successfully!";
//     message.style.color = "green";
//     signupForm.reset();
//   } catch (err) {
//     message.textContent = err.message;
//     message.style.color = "red";
//   }
// });
