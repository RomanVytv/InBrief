# 📚 InBrief — Book Summary Player

Small Android app to listen to audio summaries of books.  
Currently features the book: **The Intelligent Investor**.

---
## 📸 Screenshots
![InBrief Screenshot](./Screnshot_1.png)
---

## ✨ Features
- Two tabs:
    - **Player** — listen to the audio summary, play/pause, skip, change speed.
    - **Chapters** — read the text of each chapter.
- Simple, clean UI focused on listening & reading side by side.
- Modular architecture with repository pattern & media service.

---

## 🧪 Testing
- Unit tests are located in `src/test/`.
- Currently, tests are under development and **do not pass**.
- Work is happening on a separate branch: `/tests`.

---

## 🛠 Tech Stack
- Kotlin
- Jetpack
- StateFlow
- Koin for dependency injection
- Coroutines
- (MediaPlayer-based) audio service

---

## 🚀 Getting started
1. Clone the repo:
   ```bash
   git clone https://github.com/yourusername/inbrief.git

