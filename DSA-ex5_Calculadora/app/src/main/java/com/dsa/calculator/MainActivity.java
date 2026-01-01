package com.dsa.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        List<Button> inputButtons = new ArrayList<>();
        inputButtons.add(findViewById(R.id.button_0));
        inputButtons.add(findViewById(R.id.button_1));
        inputButtons.add(findViewById(R.id.button_2));
        inputButtons.add(findViewById(R.id.button_3));
        inputButtons.add(findViewById(R.id.button_4));
        inputButtons.add(findViewById(R.id.button_5));
        inputButtons.add(findViewById(R.id.button_6));
        inputButtons.add(findViewById(R.id.button_7));
        inputButtons.add(findViewById(R.id.button_8));
        inputButtons.add(findViewById(R.id.button_9));
        inputButtons.add(findViewById(R.id.button_add));
        inputButtons.add(findViewById(R.id.button_sub));
        inputButtons.add(findViewById(R.id.button_mult));
        inputButtons.add(findViewById(R.id.button_div));
        inputButtons.add(findViewById(R.id.button_cos));
        inputButtons.add(findViewById(R.id.button_sin));
        inputButtons.add(findViewById(R.id.button_tan));
        Button equalButton = findViewById(R.id.button_eq);
        Button clearButton = findViewById(R.id.button_ac);

        TextView formulaOutput = findViewById(R.id.formulaOutput);
        EditText formulaInput = findViewById(R.id.formulaInput);

        for (Button botton: inputButtons) {
            botton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button) v;
                    formulaInput.setText(formulaInput.getText().toString() + b.getText().toString());
                }
            });
        }

        equalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float result = 0;
                String currentNumber = "";
                String currentOp = "";
                boolean firstNumber = true;
                String formula = formulaInput.getText().toString();

                for (int i = 0; i < formula.length(); i++) {
                    char c = formula.charAt(i);
                    // Si es un operador
                    if (c == '+' || c == '-' || c == 'x' || c == '%') {
                        if (firstNumber) {
                            result = Float.parseFloat(currentNumber);
                            firstNumber = false;
                        } else {
                            float num = Float.parseFloat(currentNumber);
                            result = aplicarOperacion(result, num, currentOp);
                        }
                        currentOp = String.valueOf(c);
                        currentNumber = "";
                    }


                   // Si es un número
                    else if (Character.isDigit(c)) {
                        currentNumber += c;
                    }

                    // Detectar letras para funciones trigonométricas
                    else if (Character.isLetter(c)) {
                        StringBuilder func = new StringBuilder();
                        while (i < formula.length() && Character.isLetter(formula.charAt(i))) {
                            func.append(formula.charAt(i));
                            i++;
                        }
                        i--; // retroceder índice

                        // Leer número después de la función
                        StringBuilder num = new StringBuilder();
                        i++;
                        while (i < formula.length() && Character.isDigit(formula.charAt(i))) {
                            num.append(formula.charAt(i));
                            i++;
                        }
                        i--;

                        int value = Integer.parseInt(num.toString());
                        double radians = Math.toRadians(value); // convierte a radianes
                        float trigValue = 0;

                        switch (func.toString()) {
                            case "sin":
                                trigValue = (float) Math.sin(radians);
                                break;
                            case "cos":
                                trigValue = (float) Math.cos(radians);
                                break;
                            case "tan":
                                trigValue = (float) Math.tan(radians);
                                break;
                        }

                        currentNumber = String.valueOf(trigValue);
                    }
                }

                // Última operación
                if (!currentNumber.isEmpty()) {
                    float num = Float.parseFloat(currentNumber);
                    if (firstNumber) result = num;
                    else result = aplicarOperacion(result, num, currentOp);
                }

                formulaOutput.setText(String.valueOf(result));

            }

        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formulaInput.setText("");
                formulaOutput.setText("");
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public float aplicarOperacion(float a, float b, String op) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "x": return a * b;
            case "%": return b != 0 ? (float) a / b : 0; // evitar división entre cero
            default: return b;
        }
    }


}