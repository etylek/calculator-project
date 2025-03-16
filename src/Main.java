import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Create a JShell instance
        JShell js = JShell.builder().build();
        Scanner input = new Scanner(System.in);
        List<String> history = new ArrayList<>(); // History of calculations

        System.out.println("Welcome to the Calculator!");
        System.out.println("You can use mathematical functions like sqrt(), abs(), power(base, exponent), and round().");
        System.out.println("If you want a double type answer, input your numbers like 1.0. ");
        System.out.println("Type 'history' to view your calculation history.");
        System.out.println("Type 'file <filename>' to evaluate expressions from a file.");

        while (true) {
            System.out.println("Enter the expression (or type 'exit' to quit): ");
            String exp = input.nextLine();

            // Exit condition
            if (exp.equalsIgnoreCase("exit")) {
                System.out.println("Thank you for using the Calculator!");
                break;
            }

            // Display history
            if (exp.equalsIgnoreCase("history")) {
                if (history.isEmpty()) {
                    System.out.println("History is empty.");
                } else {
                    System.out.println("Calculation History:");
                    for (String record : history) {
                        System.out.println(record);
                    }
                }
                continue;
            }

            // File input command
            if (exp.toLowerCase().startsWith("file ")) {
                String filename = exp.substring(5); // Extract the filename
                processFileInput(js, filename, history); // Process the file
                continue;
            }

            try {
                // Preprocess the input to handle custom functions
                exp = preprocessExpression(exp);

                // Custom error checks for unmatched brackets or dividing by zero
                if (!areBracketsBalanced(exp)) {
                    throw new IllegalArgumentException("ERROR! Close all of the brackets.");
                }
                if (exp.contains("/0") || exp.contains("%0")) {
                    throw new ArithmeticException("ERROR! Division by zero is not allowed.");
                }

                // Evaluate the expression using JShell
                List<SnippetEvent> events = js.eval(exp);

                // Handle JShell returning no value
                if (events.isEmpty() || events.get(0).value() == null) {
                    throw new Exception("Invalid expression or unsupported syntax.");
                }

                String res = events.get(0).value();

                // Format the result to remove ".0" for whole numbers
                if (res.matches("-?\\d+\\.0")) { // Matches numbers ending with .0
                    res = res.substring(0, res.length() - 2); // Remove the .0
                }

                // Save to history and display the result
                String record = exp + " = " + res;
                history.add(record); // Add to history
                System.out.println("Result: " + res);

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage()); // Specific error message for unmatched brackets
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage()); // Specific error message for division by zero
            } catch (Exception e) {
                System.out.println("Error in evaluating the expression: " + e.getMessage());
            }
        }

        input.close();
    }

    // Process input from a text file
    private static void processFileInput(JShell js, String filename, List<String> history) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Processing: " + line);
                try {
                    line = preprocessExpression(line); // Preprocess expression
                    if (!areBracketsBalanced(line)) {
                        throw new IllegalArgumentException("ERROR! Close all of the brackets.");
                    }
                    if (line.contains("/0") || line.contains("%0")) {
                        throw new ArithmeticException("ERROR! Division by zero is not allowed.");
                    }
                    List<SnippetEvent> events = js.eval(line);
                    if (events.isEmpty() || events.get(0).value() == null) {
                        throw new Exception("Invalid expression or unsupported syntax.");
                    }
                    String res = events.get(0).value();
                    if (res.matches("-?\\d+\\.0")) {
                        res = res.substring(0, res.length() - 2);
                    }
                    String record = line + " = " + res;
                    history.add(record);
                    System.out.println("Result: " + res);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    // Checks if brackets/parentheses are balanced in the input expression
    private static boolean areBracketsBalanced(String exp) {
        Stack<Character> stack = new Stack<>();
        for (char ch : exp.toCharArray()) {
            if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                if (stack.isEmpty()) {
                    return false; // Closing parenthesis without a matching opening one
                }
                stack.pop();
            }
        }
        return stack.isEmpty(); // Ensure no unmatched opening parentheses remain
    }

    // Preprocesses the input to replace custom functions with Math equivalents
    private static String preprocessExpression(String exp) {
        exp = exp.replaceAll("\\s", ""); // Remove all spaces for simplicity
        exp = exp.replaceAll("power\\(([^,]+),([^\\)]+)\\)", "Math.pow($1,$2)"); // Replace power() with Math.pow()
        exp = exp.replaceAll("abs\\(([^\\)]+)\\)", "Math.abs($1)"); // Replace abs() with Math.abs()
        exp = exp.replaceAll("sqrt\\(([^\\)]+)\\)", "Math.sqrt($1)"); // Replace sqrt() with Math.sqrt()
        exp = exp.replaceAll("round\\(([^\\)]+)\\)", "(double)Math.round($1)"); // Replace round() with Math.round()
        return exp;
    }
}
