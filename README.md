# 📱 Covenal - Android Technical Challenge

**Android Frontend Developer Take-Home Challenge**

This is a simple Android wellness app built for the *Android Frontend Developer* position. The app displays a list of wellness sessions (yoga poses), allows users to view details, and mark their favorites.

---

## 📸 Screenshots

| List Screen (Light Vibrant Theme) | Detail Screen |
| :---: | :---: |
| `![List Screenshot](docs/list-screen.png)` | `![Detail Screenshot](docs/detail-screen.png)` |

---

## 🎯 Features

* **List Screen:** Displays a scrollable list of yoga poses.
* **Detail Screen:** Tapping a pose navigates to a detail screen with more information.
* **Favorite System:** Users can add/remove poses from a favorites list. The state is shared between both screens.
* **Themed UI:**
    * Uses a custom "Light Vibrant" color palette (`AppCardColors.kt`) for all list items.
    * Uses the custom `NunitoSans` variable font for all text.
* **Robust Data Strategy:** The app uses a **remote-first, local-fallback** approach.
    * It first tries to fetch data from the live API.
    * If the network fails (timeout or offline), it shows a user-friendly Toast message and seamlessly loads the data from a local `poses.json` file.
* **Polished Error States:** A custom "Something snapped!" screen (`ErrorStateView`) is shown if the local data file fails to load.

---

## ⚙️ Technical Stack & Architectural Decisions

This project was built using **100% Kotlin** and follows modern Android development practices.

| Component | Technology | Rationale |
| :--- | :--- | :--- |
| **Architecture** | **MVVM** (Model-View-ViewModel) | Standard architecture for Android. Ensures a clean separation of concerns between the UI (Views), UI logic (ViewModels), and data (Repositories). |
| **UI** | **Jetpack Compose** | The modern, declarative UI toolkit for Android. Allows for building UI faster and with less boilerplate code. |
| **Asynchronicity** | **Kotlin Coroutines & Flow** | Used for all asynchronous operations, from network calls (`suspend`) to managing live data streams (`StateFlow`) for favorites and UI state. |
| **Dependency Injection** | **Hilt** | Manages the creation and injection of dependencies (e.g., providing Repositories to ViewModels, providing `Context` to the ViewModel). |
| **Navigation** | **Jetpack Navigation (Compose)** | A type-safe, single-source-of-truth (`Screen.kt`) approach to managing navigation between composables. |
| **Networking** | **Retrofit & OkHttp** | The industry standard for type-safe REST API calls. An OkHttp interceptor is used to log network traffic, and timeouts are set to 30 seconds to handle the "spin-up" time of the free API. |
| **Data Parsing** | **Moshi** | A modern, efficient JSON parser that works well with Kotlin data classes. |
| **Image Loading** | **Coil** | A modern, Kotlin-first image loading library that integrates perfectly with Jetpack Compose. |
| **Data Source** | **Hybrid (Remote/Local)** | The `SessionRepository` acts as the single source of truth, first attempting a network call. On failure (e.g., `SocketTimeoutException`), it automatically falls back to a bundled `poses.json` file in `res/raw`, ensuring the app is **always functional**. |
| **Theming** | **Static Theme** | A custom, consistent color palette (`lightVibrant`) is defined in `AppPalettes.kt` and applied to all cards via `AppCardColors.kt` for a clean, branded feel. |
| **Font** | **`NunitoSans` (Variable Font)** | A custom variable font (`.ttf`) is bundled in `res/font` and defined in `Type.kt` to give the app a unique, serene feel. |

---

## 🚀 How to Build and Run

1.  Clone this repository:
    ```bash
    git clone https://github.com/aghmnl/covenal-wellness-challenge
    ```
2.  Open the project in a recent version of Android Studio (e.g., Iguana or newer).
3.  Let Gradle sync all dependencies.
4.  Build and run the app on an emulator or physical device.

---

## 🧪 How to Test Offline Mode

1.  Ensure the app is fully closed.
2.  Turn on **Airplane Mode** on your device or emulator.
3.  Open the app.
4.  Observe the **"Could not reach server. Loading local data."** Toast message.
5.  The app will load the list instantly from the local `poses.json` file.