# Split & Expense Tracker (Settele) 💰

An automated expense-splitting Java application designed to help group members (friends, roomies, travelers) split shared expenses fairly and determine "who owes whom" with minimal effort.

---

## 👥 Team Members & Course Info

* **Course:** CSC 113 - Computer Science II
* **Instructor:** Dr. [Wesam hattamlh]
* **University:** King Saud University

### Team Members:
* **[Salman Mislat Almutairi]** - Core Infrastructure & Base Architecture
* **[Fahad Mamdohe Almutairi]** - Derived Expenses & Main Menu UI

---

## 📌 Project Overview

**Split & Expense Tracker** eliminates the hassle of manually calculating shared expenses. Whether it's a road trip, restaurant bill, or monthly apartment rent, the system keeps track of payments, calculates individual shares using custom splitting strategies, and settles group balances automatically.

### Key Features:
* **User & Group Management:** Register users and form expense groups.
* **Flexible Expense Splitting:**
  * **Equal Expense:** Divide costs equally among all members.
  * **Custom Expense:** Assign specific percentages or amounts per person.
  * **Itemized Expense:** Breakdown costs item-by-item for detailed tracking.
* **Recursive Expense Summary:** Calculates total group expenses using recursive processing over object arrays.
* **Balance Settlement:** View clear balance statuses and settle debts easily.

---

## 🛠️ Object-Oriented Design & Architecture

This project is built strict to Object-Oriented Programming (OOP) guidelines, incorporating:

1. **Abstract Classes & Interfaces:**
   * `Expense` (Abstract Class): Serves as the base class for all expense types with `calculateUserShare(User u)`.
   * `Settlable` (Interface): Implemented by `User` and `ExpenseGroup` to unify balance settlement functionality.

2. **Inheritance (Multi-level):**
   * Level 1: `EqualExpense` and `CustomExpense` inherit from `Expense`.
   * Level 2: `ItemizedExpense` inherits from `CustomExpense`.

3. **Polymorphism:**
   * Dynamic method dispatch via `calculateUserShare()` and `settleBalance()`.

4. **Aggregation & Composition:**
   * `ExpenseGroup` holds arrays of `User` objects (Aggregation) and `Expense` objects (Composition).

---

## 💻 Tech Stack & Requirements

* **Language:** Java (JDK 8 or higher)
* **IDE:** Eclipse / NetBeans / IntelliJ IDEA / VS Code
* **Version Control:** Git & GitHub

---

## 🚀 How to Run

1. Clone the repository:
   ```bash
   git clone [https://github.com/Bys0o/CSC-Project.git](https://github.com/Bys0o/CSC-Project.git)

1. Open the project in your favorite Java IDE (Eclipse, IntelliJ IDEA, NetBeans, or VS Code).
2. Locate `Main.java`.
3. Run the file to launch the interactive console application and test the system.   
