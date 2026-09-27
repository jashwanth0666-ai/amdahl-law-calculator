# Amdahl's Law Calculator and Parallel Speedup Analyzer
 
A desktop Java Swing application that calculates and visualizes the **theoretical speedup** of a program using **Amdahl's Law** — a fundamental formula in parallel computing used to estimate the performance gain from parallelizing a workload across multiple processors.
 
Enter a serial fraction and parallel fraction, and instantly get a speedup table, a live-rendered graph, and step-by-step worked examples for 1, 2, 4, 8, and 16 processors.
 
![Java](https://img.shields.io/badge/Java-Swing-orange?logo=openjdk)
![License](https://img.shields.io/badge/License-MIT-blue)
![Status](https://img.shields.io/badge/Status-Active-brightgreen)
 
---
 
## 📖 What is Amdahl's Law?
 
Amdahl's Law is used in **parallel computing** and **computer architecture** to predict the maximum theoretical speedup achievable when part of a program is parallelized across multiple processors.
 
```
Speedup = 1 / (S + P / N)
```
 
Where:
- **S** = Serial Fraction (portion of the program that must run sequentially)
- **P** = Parallel Fraction (portion of the program that can run in parallel)
- **N** = Number of Processors
- **S + P = 1**
As **N → ∞**, speedup approaches its upper bound:
 
```
Maximum Speedup = 1 / S
```
 
This project implements that formula as an interactive Java desktop calculator, making it a useful reference for students learning **parallel computing**, **Amdahl's Law**, **speedup analysis**, and **computer architecture performance metrics**.
 
---
 
## ✨ Features
 
- 🖥️ **Java Swing GUI** — clean, tabbed desktop interface
- 🧮 **Live Amdahl's Law calculator** — enter serial (S) and parallel (P) fractions
- ✅ **Input validation** — ensures `S + P = 1` and rejects negative values
- 📊 **Speedup table** — auto-generated for 1, 2, 4, 8, and 16 processors
- 📈 **Custom-drawn speedup graph** — Processors (N) vs Theoretical Speedup, plotted with `Graphics2D`
- 📐 **Maximum theoretical speedup** — computed as `1 / S`
- 📝 **Worked numerical examples** — step-by-step calculation breakdown for every processor count
- ⚠️ **Diminishing returns explanation** — shows how gains shrink as the serial portion dominates
---
 
 
## 🛠️ Tech Stack
 
| Component | Technology |
|---|---|
| Language | Java |
| GUI Framework | Java Swing (`javax.swing`) |
| Graphics | `java.awt.Graphics2D` (custom-rendered chart, no external plotting library) |
| Formatting | `java.text.DecimalFormat` |
 
No external dependencies or build tools are required — just a standard JDK.
 
---
 
## 🚀 Getting Started
 
### Prerequisites
- [JDK 8 or later](https://www.oracle.com/java/technologies/downloads/) installed
- `java` and `javac` available on your system PATH
### Clone the repository
 
```bash
git clone https://github.com/jashwanth0666-ai/amdahl-law-calculator.git
cd amdahl-law-calculator
```
 
### Compile and run
 
```bash
javac AmdahlSpeedupAnalyzer.java
java AmdahlSpeedupAnalyzer
```
 
The application window will open with default values (S = 0.10, P = 0.90) already calculated.
 
---
 
## 📋 How to Use
 
1. Enter the **Serial Fraction (S)** and **Parallel Fraction (P)** — they must add up to `1`.
2. Click **Calculate Speedup**.
3. View results across two tabs:
   - **Speedup Analysis** — the speedup table and graph side by side, for N = 1, 2, 4, 8, 16.
   - **Worked Examples** — full step-by-step substitution and calculation for each processor count.
4. The **Maximum Theoretical Speedup** (`1 / S`) is displayed at the bottom of the window.
### Example
 
For **S = 0.10**, **P = 0.90**:
 
| Processors (N) | Speedup |
|---|---|
| 1 | 1.0000 |
| 2 | 1.8182 |
| 4 | 3.0769 |
| 8 | 4.7059 |
| 16 | 6.4000 |
 
Maximum Theoretical Speedup = `1 / 0.10` = **10.0**
 
---
 
## 🎓 Use Cases
 
- Parallel computing mini-projects / academic assignments
- Demonstrating Amdahl's Law in computer architecture coursework
- Understanding diminishing returns in multi-core / multiprocessor scaling
- Quick reference tool for estimating theoretical speedup before parallelizing real workloads
---
 
## 🤝 Contributing
 
Contributions, issues, and feature requests are welcome. Feel free to open an issue or submit a pull request.
 
## 📄 License
 
This project is licensed under the [MIT License](LICENSE).
 
## 👤 Author
 
**Jashwanth**
GitHub: [@jashwanth0666-ai](https://github.com/jashwanth0666-ai)
 
---
 
⭐ If you find this project useful, consider giving it a star on GitHub!
 
