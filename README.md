# PRODIGY_JP_01
It's a console bases application to manage books in library
# 📚 Library Management System (Java + MySQL)

This is a **Library Management System** built using **Java and MySQL**, which supports **CRUD operations (Create, Read, Update, Delete)**.

## 🚀 Features:
✅ Add new books with title, author, and ISBN.  
✅ View all books in a **formatted table output**.  
✅ Update book details **(title, author, ISBN) dynamically**.  
✅ Issue and return books **with status tracking**.  
✅ Delete books from the system.  

## 🛠 Technologies Used:
- **Java** (JDBC for database connectivity)
- **MySQL** (Relational Database)
- **JDBC API** (Database Integration)
- **Scanner (User Input Handling)**

## 📌 Table Structure:
```sql
CREATE TABLE books (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    isIssued BOOLEAN DEFAULT FALSE
);
