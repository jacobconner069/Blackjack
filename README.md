# Java Graphical Blackjack

A desktop implementation of the classic casino card game **Blackjack**, built in Java. This project features a graphical user interface complete with custom playing card assets and betting chips.

---

## 📋 Table of Contents
- [Features](#features)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [How to Play](#how-to-play)
- [Assets](#assets)

---

## ✨ Features
- **Interactive GUI:** Visual representation of the dealer's hand, player's hand, and table actions.
- **Betting System:** Place wagers using visual chip denominations (`Red`, `Green`, `Black`).
- **Complete Deck Graphics:** Includes a full 52-card deck, card back artwork (`BACK.png`), and joker cards (`J-B.png`, `J-R.png`).
- **Classic Rules:** Standard Blackjack mechanics including Hit, Stand, dealer draw rules, and Ace value adjustments (1 or 11).

---

## 📂 Project Structure

```text
Blackjack-main/
├── App.java            # Application entry point (main method)
├── BlackJack.java      # Core game logic, UI rendering, and event handling
├── README.md           # Project documentation
├── cards/              # High-resolution PNG images for the deck
│   ├── 2-C.png ... A-S.png
│   ├── BACK.png        # Face-down card graphic
│   └── J-B.png, J-R.png
└── chips/              # Betting chip graphics
    ├── Red.png
    ├── Green.png
    └── Black.png
```

---

## ⚙️ Prerequisites
To compile and run this project, you need:
- **Java Development Kit (JDK):** Version 8 or higher installed on your system.
- An IDE (such as VS Code, IntelliJ IDEA, or Eclipse) or a standard command-line terminal.

---

## 🚀 Getting Started

### Running via Terminal / Command Prompt

1. **Clone or extract** the project repository and navigate into the root directory:
   ```bash
   cd Blackjack-main
   ```

2. **Compile** the Java source files:
   ```bash
   javac App.java BlackJack.java
   ```

3. **Run** the application:
   ```bash
   java App
   ```

> **Note:** Ensure you run the program from the `Blackjack-main/` root directory so the application can properly locate the relative paths to the `cards/` and `chips/` asset folders.

---

## 🃏 How to Play
1. **Place Your Bet:** Select your desired wager using the available betting chips before the round begins.
2. **The Deal:** Both you and the dealer are dealt two cards. One of the dealer's cards remains face down.
3. **Your Turn:**
   - **Hit:** Request another card to get closer to 21.
   - **Stand:** Keep your current hand and end your turn.
4. **Dealer's Turn:** Once you stand, the dealer reveals their hidden card and draws until reaching at least 17.
5. **Winning:** Beat the dealer's hand without exceeding 21 (busting).

---

## 🎨 Assets
- **Cards (`cards/`):** Named using the convention `[Rank]-[Suit].png` (e.g., `A-S.png` for Ace of Spades, `10-H.png` for 10 of Hearts).
- **Chips (`chips/`):** Graphical assets representing different betting tiers (`Red.png`, `Green.png`, `Black.png`)
