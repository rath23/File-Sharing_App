# 📁 File Sharing Application

A simple and secure file-sharing web application built with **Spring Boot**, **Thymeleaf**, **MySQL**, and **Spring MVC**. Users can register, verify their email, upload files, and share files with others. All shared files are automatically deleted after 24 hours.

## 🚀 Features

- 📝 User registration with email verification
- 🔐 Login with email and password authentication
- 📤 Upload any type of file
- 🔗 Share files using a unique link
- ⏱️ Files auto-delete after 24 hours
- 💡 Clean and minimal dark UI using Thymeleaf

## 🔧 Tech Stack

- **Backend:** Spring Boot, Spring MVC, Spring Security, Java Mail
- **Frontend:** HTML, CSS, Thymeleaf
- **Database:** MySQL
- **Deployment:** Railway.app

## 📸 Screenshots

### 🔐 Login Page
![Login](Screenshots/Screenshot%202025-07-08%20175215.png)

### 🧾 Registration Page
![Register](./Screenshots/Screenshot%202025-07-08%20175252.png)

### ✅ Signup Success Message
![Signup Success](./Screenshots/Screenshot%202025-07-08%20175313.png)

### 📧 Email Verification Mail
![Verification Mail](./Screenshots/Screenshot%202025-07-08%20175355.png)

### 🎉 Verified Account Message
![Verified](./Screenshots/Screenshot%202025-07-08%20175407.png)

### 📥 File Download & Share Page
![Download & Share1](./Screenshots/Screenshot%202025-07-08%20175457.png)
![Download & Share2](./Screenshots/Screenshot%202025-07-08%20180434.png)
![Share](./Screenshots/Screenshot%202025-07-08%20180453.png)

## 🛠 How It Works

1. **User Registration:**
   - Users sign up by entering a username, email, and password.
   - A verification link is sent to their email.

2. **Email Verification:**
   - Users must verify their account via the email link before logging in.

3. **Authentication:**
   - Login is handled using Spring Security (email + password).

4. **File Upload & Sharing:**
   - Users can upload any file.
   - The file is stored in the database (LONGBLOB) and a unique shareable URL is generated.

5. **File Access:**
   - The recipient can download the file using the shared URL.
   - Each file expires and is auto-deleted after 24 hours.

## ⚙️ Setup Locally

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/file-sharing-app.git
   cd file-sharing-app
