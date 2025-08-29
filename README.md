# ✈️ Just4Flights – Flight Booking Application  

Just4Flights is a **Java-based flight booking system** that enables users to **search flights, book tickets, and manage their profiles**.  
The application is built with a **client-server architecture**, integrates with a **third-party flight API**, and uses **MySQL** for authentication and booking management.  

---

## 📖 Table of Contents  
- [✨ Features](#-features)   
- [⚙️ Installation](#️-installation)  
- [🚀 Usage](#-usage)  
- [📦 Tech Stack](#-tech-stack)  
- [🔧 Configuration](#-configuration)  
- [📈 Future Improvements](#-future-improvements)  

---

## ✨ Features  
- 🔐 **User Authentication** – Register, log in, and manage sessions securely  
- 🔎 **Flight Search** – Query real-time flights via external API  
- 🎫 **Booking Management** – Book, cancel, and update reservations  
- 👤 **User Profile** – Track booking history & update personal details  
- 🔗 **Client–Server Communication** – Built on socket programming for seamless interaction  

---

<img width="400" height="600" alt="image" src="https://github.com/user-attachments/assets/adf8f086-eb23-4769-a859-fc85621f4267" />

<img width="400" height="600" alt="image" src="https://github.com/user-attachments/assets/d30f91ea-f3fc-46ec-b8de-225ca0dc2f0e" />

<img width="400" height="600" alt="image" src="https://github.com/user-attachments/assets/7acdab8e-3332-4474-8fd0-172f2431848f" />

<img width="400" height="600" alt="image" src="https://github.com/user-attachments/assets/eaccd78f-71a3-4e1e-8e6f-ea614ee9034a" />

<img width="400" height="600" alt="image" src="https://github.com/user-attachments/assets/c8d369d1-a8ab-446b-8491-518623be215f" />

<img width="400" height="600" alt="image" src="https://github.com/user-attachments/assets/89862d1c-7b52-4be8-9c07-fc49da2044f5" />

<img width="400" height="600" alt="image" src="https://github.com/user-attachments/assets/dfc785a8-c711-4f5d-a03c-4d31980af9b4" />

---

## ⚙️ Installation  

### 1. Clone the repository  
```bash
git clone https://github.com/jacobalmon/SE-Project.git
cd SE-Project
```

### 2. Set up the database  
- Create a MySQL database named **`just4flights`**  
- Import the provided schema from `/database/schema.sql`  
- Update database credentials in `Server.java`  

### 3. Install dependencies  
- Install **JavaFX** for GUI  
- Add required libraries: **Unirest**, **Gson**, **MySQL Connector**  

### 4. Run the application  

Start the server:  
```bash
javac Server.java
java myapp.Server
```

Start the client:
```bash
javac Client.java
java myapp.Client
```

## 🚀 Usage  
- **Register/Login** – Create an account or log in  
- **Search Flights** – Enter origin, destination, and date  
- **Book Tickets** – Confirm booking and proceed to payment  
- **Manage Profile** – Update personal info & review booking history  

---

## 📦 Tech Stack  
- **Language**: Java (JavaFX for GUI)  
- **Database**: MySQL  
- **API**: Flight Search API (via Unirest HTTP client)  
- **Libraries**: Gson (JSON handling), Unirest (API calls), MySQL Connector  

---

## 🔧 Configuration  
- **Database Credentials** – update in `Server.java`  
- **API Keys** – replace in `SearchFlights.java` with your own credentials  

---

## 📈 Future Improvements  
- ✅ Payment gateway integration  
- ✅ RESTful API layer for modern clients (mobile/web)  
- ✅ Dockerized deployment  
