# Ge'ez Calculator

## Description

This Android application is a unique calculator that performs arithmetic calculations using the Ge'ez numeral system. The Ge'ez numeral system is an ancient Ethiopian numeral system that is still used today, particularly in religious contexts within the Ethiopian and Eritrean Orthodox Tewahedo Churches.

This calculator supports basic arithmetic operations (addition, subtraction, multiplication, and division) and follows the BODMAS (order of operations) rule. It can handle numbers up to 1 trillion (1,000,000,000,000 or ፼፼፼ in Ge'ez).

## Features

*   **Ge'ez Numeral Input:**  Enter numbers using an on-screen keypad with Ge'ez numeral characters.
*   **Arithmetic Operations:** Perform addition (+), subtraction (-), multiplication (\*), and division (/).
*   **BODMAS/Order of Operations:** Calculations follow the correct order of operations (BODMAS/PEMDAS).
*   **Large Number Support:** Handles numbers up to 1 trillion (፼፼፼).
*   **Error Handling:** Basic error handling for invalid input and division by zero.
*   **Text-to-Speech:**  Hear the Ge'ez numbers spoken in Amharic when you press the number buttons (optional feature, currently under development).
*   **Splash Screen:** A simple splash screen with an animation.
*   **Info Page:** Learn more about the Ge'ez numeral system and how to use the calculator.
*   **Feedback Page:** Send feedback directly to the developer via email.
*   **Dark/Light Mode:** Switch between dark and light themes.
*   **Ethiopian Flag:** The Ethiopian flag is displayed in the header for cultural context.

## Technology Stack

*   **Language:** Kotlin
*   **UI Framework:** Jetpack Compose
*   **Build System:** Gradle
*   **Text-to-Speech:** Android TextToSpeech Engine
*   **Other Libraries:**
    *   `net.java.dev.jna:jna:5.12.1` (for TextToSpeech functionality)
    *   `androidx.compose:compose-bom:2023.03.00`

## Installation

1. **Prerequisites:**
    *   Android Studio (latest stable version recommended)
    *   Android SDK (API level 34 or higher recommended)
    *   An Android device or emulator for testing

2. **Clone the Repository:**

    ```bash
    git clone <your-repository-url>
    ```

3. **Open the Project in Android Studio:**

    *   Open Android Studio.
    *   Select "Open an existing project."
    *   Navigate to the cloned repository and select the `Ge'ezCalculator` directory.

4. **Build and Run:**

    *   Wait for Android Studio to sync the project with Gradle.
    *   Click the "Run" button (green play icon) and choose a target device or emulator.

## Usage

1. **Enter Numbers:** Tap the Ge'ez numeral buttons to enter numbers.
2. **Select Operator:** Tap the operator buttons (+, -, \*, /) to perform calculations.
3. **Calculate:** Tap the "=" button to calculate the result.
4. **Clear:** Tap the "AC" button to clear the input and result.
5. **Info:** Tap the "Info" button to learn more about the Ge'ez numeral system and how to use the calculator.
6. **Feedback:** Tap the "Feedback" button to send feedback to the developer.


## Contributing

Contributions are welcome! If you'd like to contribute to this project, please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes and commit them with clear commit messages.
4. Push your changes to your fork.
5. Submit a pull request to the main repository.





