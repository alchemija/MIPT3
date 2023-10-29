package com.example.calculatorapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private StringBuilder input;
    private double number1, number2;
    private String operator;
    private boolean newCalculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        input = new StringBuilder();
        operator = "";
        newCalculation = true;

        // Set click listeners for buttons
        setButtonListeners();
    }

    private void setButtonListeners() {
        // Numeric buttons
        for (int i = 0; i <= 9; i++) {
            int buttonId = getResources().getIdentifier("btn" + i, "id", getPackageName());
            Button button = findViewById(buttonId);
            int finalI = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (newCalculation) {
                        clear();
                        newCalculation = false;
                    }
                    input.append(finalI);
                    updateDisplay();
                }
            });
        }

        // Operator buttons
        Button btnPlus = findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operator = "+";
                saveNumber();
            }
        });

        Button btnMinus = findViewById(R.id.btnMinus);
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operator = "-";
                saveNumber();
            }
        });

        Button btnMultiply = findViewById(R.id.btnMultiply);
        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operator = "*";
                saveNumber();
            }
        });

        Button btnDivide = findViewById(R.id.btnDivide);
        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operator = "/";
                saveNumber();
            }
        });

        Button btnEqual = findViewById(R.id.btnEqual);
        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!newCalculation) {
                    if (operator.equals("+")) {
                        calculate('+');
                    } else if (operator.equals("-")) {
                        calculate('-');
                    } else if (operator.equals("*")) {
                        calculate('*');
                    } else if (operator.equals("/")) {
                        calculate('/');
                    }
                    operator = "";
                    newCalculation = true;
                }
            }
        });

        // Additional buttons
        Button btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        Button btnSignChange = findViewById(R.id.btnSignChange);
        btnSignChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSign();
            }
        });

        Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLastCharacter();
            }
        });

        Button btnDot = findViewById(R.id.btnDot);
        btnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.length() > 0 && !input.toString().contains(".")) {
                    input.append(".");
                    updateDisplay();
                }
            }
        });

        Button btnSqrt = findViewById(R.id.btnSqrt);
        btnSqrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.length() > 0) {
                    double value = Double.parseDouble(input.toString());
                    value = Math.sqrt(value);
                    input.setLength(0);
                    input.append(value);
                    updateDisplay();
                }
            }
        });
    }

    private void saveNumber() {
        if (input.length() > 0) {
            number1 = Double.parseDouble(input.toString());
            input.setLength(0);
        }
    }

    private void calculate(char op) {
        if (input.length() > 0) {
            number2 = Double.parseDouble(input.toString());
            input.setLength(0);

            double result = 0;

            switch (op) {
                case '+':
                    result = number1 + number2;
                    break;
                case '-':
                    result = number1 - number2;
                    break;
                case '*':
                    result = number1 * number2;
                    break;
                case '/':
                    if (number2 != 0) {
                        result = number1 / number2;
                    } else {
                        clear();
                        input.append("Error");
                        updateDisplay();
                        newCalculation = true;
                        return;
                    }
                    break;
            }

            input.append(result);
            updateDisplay();
        }
    }

    private void clear() {
        input.setLength(0);
        number1 = 0;
        number2 = 0;
        operator = "";
        updateDisplay();
    }

    private void changeSign() {
        if (input.length() > 0) {
            double value = Double.parseDouble(input.toString());
            value = -value;
            input.setLength(0);
            input.append(value);
            updateDisplay();
        }
    }

    private void deleteLastCharacter() {
        if (input.length() > 0) {
            input.deleteCharAt(input.length() - 1);
            updateDisplay();
        }
    }

    private void updateDisplay() {
        textView.setText(input.toString());
    }
}
