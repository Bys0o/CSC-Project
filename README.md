# 💰 Split & Expense Tracker (نظام قَطَّة لتقسيم المصاريف)

> **CSC 113: Computer Science II — Project Phase 1**  
> **King Saud University | College of Computer and Information Sciences**

---

## 📌 About The Project (عن المشروع)

**Split & Expense Tracker** هو نظام تفاعلي متكامل بلغة Java مخصص لإدارة وتقسيم المصاريف المشتركة بين المجموعات (مثل الرحلات، الأنشطة، والمطاعم) بطريقة آمنة وسلسة، شبيهة بتطبيق *Splitwise*. 

يقوم النظام بحساب الحصص تلقائياً وتتبع المبالغ المستحقة وتسوية الحسابات بين المستخدمين بناءً على خوارزميات تقسيم مختلفة (متساوية، مخصصة، ومفصلة).

---

## 👥 Project Team & Course Info (فريق العمل ومعلومات المقرر)

| **Course** | CSC 113: Object-Oriented Programming II |
| :--- | :--- |
| **Instructor** | [اسم الدكتور / Dr. Name] |
| **Semester** | First Semester 1448 / 2026 |

### 👨‍💻 Group Members (أعضاء الفريق)
* **[اسمك هنا]** — Student ID: `[رقمك الجامعي]` *(Lead Architect: Core Infrastructure & Abstract Base Classes)*
* **[اسم صديقك هنا]** — Student ID: `[رقمك الجامعي]` *(Developer: Derived Expenses & Menu System)*

---

## 🛠️ Key Features & Technical Specifications (المميزات والمواصفات البرمجية)

تم تصميم هذا النظام وفقاً لكافة متطلبات **CSC 113 Project Phase 1** بأعلى معايير البرمجة الكائنية (OOP):

1. **Abstraction & Interfaces**:
   * `Expense` (Abstract Class): الفئة الأساسية المجردة لحساب حصص المستخدمين.
   * `Settlable` (Interface): واجهة برمجية لتعدد سلوكيات تسوية الحسابات (`settleBalance()`).

2. **Inheritance Levels (الوراثة بمستويات)**:
   * **Level 1**: `EqualExpense` & `CustomExpense` ورثة من `Expense`.
   * **Level 2**: `ItemizedExpense` وريث من `CustomExpense`.

3. **Polymorphism (تعدد الأشكال)**:
   * إمكانية حساب الحصة الشخصية عبر دالة `calculateUserShare(User u)` بشكل مختلف لكل نوع مصروف وقت التشغيل (Dynamic Binding).

4. **Object Array Management & Recursion**:
   * إدارة المصفوفات داخل `ExpenseGroup` لإضافة المصاريف وتصفيتها وحذفها مع معالجة الإزاحة.
   * دالة عودية (`calculateTotalExpensesRecursive`) لحساب إجمالي مصاريف المجموعة.

---

## 📐 Class Diagram (UML)

```text
               +-------------------+
               |  <<interface>>    |
               |     Settlable     |
               +-------------------+
               | + settleBalance() |
               +-------------------+
                 ^               ^
                 | implements    | implements
                 |               |
         +-------+-------+     +-+-----------------------------------+
         |     User      |     |            ExpenseGroup             |
         +---------------+     +-------------------------------------+
         | - userId      |     | - groupName: String                 |
         | - name        |<>---| - users: User[]                     |
         | - balance     |     | - expenses: Expense[]               |
         +---------------+     +-------------------------------------+
                 ^             | + addExpense(Expense e)             |
                 |             | + removeExpense(String desc)        |
                 |             | + searchExpenseByTitle(...)         |
                 |             | + calculateTotalExpensesRecursive() |
                 |             +-------------------------------------+
                 |                                ♦
                 |                                | composition
                 +--------------+                 |
                                |                 v
                       +----------------------------------+
                       |          <<abstract>>            |
                       |             Expense              |
                       +----------------------------------+
                       | - description: String            |
                       | - totalAmount: double            |
                       | - payer: User                    |
                       +----------------------------------+
                       | + calculateUserShare(User u)*    |
                       +----------------------------------+
                                  ^            ^
                                  |            |
                     +------------+            +------------+
                     |                                      |
         +-----------------------+              +-----------------------+
         |     EqualExpense      |              |     CustomExpense     |
         +-----------------------+              +-----------------------+
         | + calculateUserShare()|              | - customShares: Map   |
         +-----------------------+              +-----------------------+
                                                            ^
                                                            |
                                                +-----------------------+
                                                |    ItemizedExpense    |
                                                +-----------------------+
                                                | - itemDetails: List   |
                                                +-----------------------+
```

---

## 📁 Repository Structure (هيكل المجلدات)

```bash
├── Settlable.java     # Interface for balance settlement
├── Expense.java       # Abstract base class for all expenses
├── User.java          # Model representing a user in the system
├── ExpenseGroup.java  # Controller handling array operations & recursion
└── README.md          # Project documentation
```

---

## 🚀 How to Run (طريقة التشغيل)

1. قم بفتح المشروع في أي بيئة تطوير Java (مثل Eclipse, NetBeans, أو VS Code).
2. قم بتشغيل فئة الاختبار الرئيسية `PlannerTester.java` (أو `Main.java`).
3. اتّبع الخيارات الظاهرة في القائمة التفاعلية (Menu) لإضافة واستعراض المصاريف وتسويتها.