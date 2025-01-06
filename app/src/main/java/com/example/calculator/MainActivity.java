package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    ImageButton delete_digit;
    Button ac;
    String operation = "";
    String firstNumber = "";
    String secondNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main);
        textView = findViewById(R.id.tex_vw);
        delete_digit = findViewById(R.id.delete_digit_img);
        ac = findViewById(R.id.ac);
    }

    public void onclickDigit(View view) {
        Button button = (Button) view;
        String text = textView.getText().toString();
        String input = button.getText().toString();
        if (input.equals(".")) {
            if (text.isEmpty()) {
                textView.setText("0.");
                return;
            }
            if (text.contains(".")) {
                return;
            }
        }
        text += input;
        textView.setText(text);
    }


    public void onclickOperation(View view) {
        Button button = (Button) view;
        if (textView.getText().toString().isEmpty()) return;

        if (operation.isEmpty()) {
            // Save first number and set the operation
            firstNumber = textView.getText().toString();
            operation = button.getText().toString();
            textView.setText(""); // Clear input for second number
        } else {
            // If operation already exists, calculate result
            secondNumber = textView.getText().toString();
            if (!secondNumber.isEmpty()) {
                String result = calc(operation, firstNumber, secondNumber);
                textView.setText(result);
                // Prepare for the next operation
                firstNumber = result;
                secondNumber = "";
                operation = button.getText().toString(); // Set new operation
            }
        }
    }

    public void onclickEqule(View view) {
        if (!operation.isEmpty() && !textView.getText().toString().isEmpty()) {
            secondNumber = textView.getText().toString();
            String result = calc(operation, firstNumber, secondNumber);
            textView.setText(result);
            firstNumber = result;
            secondNumber = "";
            operation = ""; // Reset operation after equals
        }
    }

    public void onclickRemove(View view) {
        if (view.getId() == R.id.delete_digit_img) {
            String text = textView.getText().toString();
            if (text.isEmpty()) return;
            String nText = text.substring(0, text.length() - 1);
            textView.setText(nText);
        } else if (view.getId() == R.id.ac) {
            textView.setText("");
            firstNumber = "";
            secondNumber = "";
            operation = "";
        }
    }

    public String calc(String operation, String num1, String num2) {
        double number1, number2;
        try {
            number1 = Double.parseDouble(num1);
            number2 = Double.parseDouble(num2);
        } catch (NumberFormatException e) {
            return "Error";
        }

        double result = 0;
        switch (operation) {
            case "+":
                result = number1 + number2;
                break;
            case "-":
                result = number1 - number2;
                break;
            case "*":
                result = number1 * number2;
                break;
            case "/":
                if (number2 == 0) return "Error";
                result = number1 / number2;
                break;
            default:
                return "Error";
        }
        return String.valueOf(result);
    }
}
