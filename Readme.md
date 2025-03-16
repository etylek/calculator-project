# Calculator with JShell and File Input Support

## Project Overview
This project is a command-line calculator developed in Java, using the JShell API to evaluate mathematical expressions dynamically. It supports custom mathematical functions, maintains a history of calculations, and includes the ability to process expressions from text files.

## Design Choices
- **Command Line Interface:** A simple and interactive way for users to evaluate expressions.
- **JShell API:** Enables the dynamic evaluation of expressions.
- **History Tracking:** Keeps track of all user calculations in the session for easy review.
- **File Input Support:** Processes multiple expressions from a .txt file for batch evaluation.
- **Custom Functions Support:** Adds custom functions like power(base, exponent), sqrt(value), abs(value), and round(value).

## Features
- Interactive mode for evaluating expressions directly in the console.
- Support for file input to evaluate expressions from a text file.
- History functionality for reviewing past calculations.
- Extensive error handling for issues like unmatched brackets or division by zero.
- Preprocessing of expressions to support custom functions mapped to Java Math equivalents.
- Cleaner result formatting to remove unnecessary .0 for whole numbers.

## Algorithms and Data Structures
1. **JShell Evaluation:** Utilizes the JShell API to evaluate user-provided mathematical expressions.
2. **Error Handling:**
    - Brackets validation using a stack-based algorithm.
    - Division by zero is detected and managed gracefully.
3. **History Management:** Uses a List<String> to store user calculations for quick reference.
4. **File Processing:** Reads and processes expressions line-by-line using BufferedReader.

## Improvements Made
- Added file input functionality to evaluate multiple expressions from a `.txt` file.
- Improved error handling for invalid syntax and mathematical errors.
- Introduced support for custom mathematical functions.
- Enhanced user guidance with helpful instructions.
- Refined output formatting for a cleaner display of results.

## Input and Output
- **Input:**
    - Accepts single expressions through the console in interactive mode.
    - Reads expressions from a **.txt** file in file input mode.
- **Output:**
    - Displays results of calculations in the console.
    - Stores successful calculations in a session history for reference.

### Example Input File
```text
34+(76-45)*2 - abs(-5)
```
## Challenges Encountered
- Ensuring that the code is able to solve complex expressions.

- Adding an ability to use sqrt(), abs(), round(), power() functions.

- Ensuring robust handling of invalid or malformed expressions.

- Managing and interpreting JShell output for different scenarios (syntax errors).

- Implementing efficient and accurate validation for balanced parentheses.

- Extending the program to seamlessly handle both interactive and file-based inputs.

## How to Use
### Interactive Mode
1. Run the program in your Java environment.

2. Enter mathematical expressions in the console to evaluate them.

3. Type history to view your calculation history.

4. Type exit to quit the program.

### File Input Mode
1. Create a .txt file with one expression per line.

2. Run the program in your Java environment. **In the same directory as the code.**

3. Enter file <filename> (file expressions.txt) to process and evaluate the file.
