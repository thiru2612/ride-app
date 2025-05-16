# 🚗 **Ride Booking Console App**

## 📌 **Overview**
This is a **Java-based console application** for managing ride bookings. It supports **two roles**:
- **Admin** → Can **view all ride details**.
- **User** → Can **book a ride, view ride history**, and **create an account if login fails**.

The application is built using separate **entity classes (`User` & `Ride`)** for better structure.

## 📂 **Project Structure**
- **`User.java`** → Represents users (username, password, role).
- **`Ride.java`** → Represents ride details (mode, location, distance, booking date).
- **`RideRepository.java`** → Stores all booked rides & maintains sorting order.
- **`RideService.java`** → Handles ride operations (booking, viewing history).
- **`UserService.java`** → Manages user authentication & account creation.
- **`Main.java`** → Entry point for the application.

## 🔧 **How to Run the Project**
### **Prerequisites**
- **Java 8 or higher installed**
- **Git installed** (optional, if cloning from GitHub)

### **Steps**
1️⃣ **Clone the repository**:
   ```sh
   git clone https://github.com/thiru2612/ride-app.git
   ```
2️⃣ **Navigate to the project directory**:
   ```sh
   cd ride-booking-app
   ```
3️⃣ **Compile the project**:
   ```sh
   javac Main.java
   ```
4️⃣ **Run the application**:
   ```sh
   java Main
   ```

## 🎯 **Features**
### **👑 Admin Features**
✅ **View all ride details (mode, location, distance, date).**

### **👤 User Features**
✅ **Book a ride (Bike, Auto, Car).**  
✅ **View booked ride history.**  
✅ **If login fails, create a new user account.**  

## 🚀 **Usage Example**
```
Welcome to the Ridy App
Please login to continue
Enter your username: user123
Enter your password: pass123
Login successful!

1. Book a ride
2. View Ride history
3. Exit
```

## 🛠 **Sorting Rides by Date**
Rides are stored in **descending order by booking date** using:
```java
rides.sort((r1, r2) -> r2.getD().compareTo(r1.getD()));
```
To store rides in **ascending order**, modify it as:
```java
rides.sort((r1, r2) -> r1.getD().compareTo(r2.getD()));
```

## 📜 **Contributing**
Feel free to:
- Submit **feature requests**.
- Report **bugs** in GitHub issues.
- Suggest **code improvements**.

## ⚖ **License**
This project is **open-source** under the **MIT License**.

---

Would you like me to generate a **GitHub repository description** for better visibility? 🚀  
Let me know if you need **README refinements** or **setup instructions for Eclipse users!** 🔥
```
This **README.md** is structured well for GitHub. If you'd like to add more details, such as **database integration** or **API extensions**, I can refine it further! 🚀  
Let me know how else I can help with your project setup. 🔥
