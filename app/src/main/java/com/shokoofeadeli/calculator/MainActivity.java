package com.shokoofeadeli.calculator;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
  public String lastOperator;
  public double lastNumber;
  public boolean mustClear;
  public boolean mustOverrideOperator;

  public Typeface lcdfont;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    lcdfont = Typeface.createFromAsset(getAssets(), "lcd_font.ttf");
    final Button btn_0 = (Button) findViewById(R.id.btn_0);
    final Button btn_1 = (Button) findViewById(R.id.btn_1);
    final Button btn_2 = (Button) findViewById(R.id.btn_2);
    final Button btn_3 = (Button) findViewById(R.id.btn_3);
    final Button btn_4 = (Button) findViewById(R.id.btn_4);
    final Button btn_5 = (Button) findViewById(R.id.btn_5);
    final Button btn_6 = (Button) findViewById(R.id.btn_6);
    final Button btn_7 = (Button) findViewById(R.id.btn_7);
    final Button btn_8 = (Button) findViewById(R.id.btn_8);
    final Button btn_9 = (Button) findViewById(R.id.btn_9);
    final Button btn_dot = (Button) findViewById(R.id.btn_dot);
    final Button btn_minus = (Button) findViewById(R.id.btn_minus);
    final Button btn_multiply = (Button) findViewById(R.id.btn_multiply);
    final Button btn_division = (Button) findViewById(R.id.btn_division);
    final Button btn_plus = (Button) findViewById(R.id.btn_plus);
    final Button btn_equal = (Button) findViewById(R.id.btn_equal);
    final Button btn_clear = (Button) findViewById(R.id.btn_clear);
    final Button btn_clearEntity = (Button) findViewById(R.id.btn_clearEntity);
    final Button btn_back = (Button) findViewById(R.id.btn_back);
    final Button btn_sign = (Button) findViewById(R.id.btn_sign);

    final TextView txt_history = (TextView) findViewById(R.id.txt_history);
    final TextView txt_result = (TextView) findViewById(R.id.txt_result);
    txt_result.setTypeface(lcdfont);
    txt_history.setTypeface(lcdfont);

    View.OnClickListener resetClickListener = new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        Button button = (Button) view;
        String btnText = button.getText().toString();
        if (btnText.contains("CE")) {
          txt_result.setText("0");
          mustClear = true;
        } else if (btnText.contains("C")) {
          lastOperator = null;
          lastNumber = 0.0;
          txt_result.setText("0");
          txt_history.setText("");
        } else if (btnText.contains("«")) {
          String currentNumber = txt_result.getText().toString();
          txt_result.setText(currentNumber.substring(0, currentNumber.length() - 1));
        }
      }

      ;
    };

    View.OnClickListener operatorClickListener = new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        double currentNumber = Double.parseDouble(txt_result.getText().toString());
        String operator = view.getTag().toString();
        mustClear = true;
        if (mustOverrideOperator) {
          return;
        }
        if (lastOperator == null) {
          lastNumber = currentNumber;
          lastOperator = operator;
          txt_history.setText(lastNumber + " " + lastOperator);
        } else {
          double result = 0.0;
          if (lastOperator.equals("/")) {
            result = lastNumber / currentNumber;
          } else if (lastOperator.equals("*")) {
            result = lastNumber * currentNumber;
          } else if (lastOperator.equals("-")) {
            result = lastNumber - currentNumber;
          } else if (lastOperator.equals("+")) {
            result = lastNumber + currentNumber;
          }
          mustOverrideOperator = true;
          lastNumber = result;
          lastOperator = operator;
          txt_result.setText("" + result);
          txt_history.setText(txt_history.getText().toString() + " " + currentNumber + " " + lastOperator);
          if (operator.equals("=")) {
            lastOperator = null;
          }
        }
      }

      ;
    };

    View.OnClickListener numberClickListener = new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        mustOverrideOperator = false;
        int num = Integer.parseInt(view.getTag().toString());
        String resultText = txt_result.getText().toString();
        if (resultText.equals("0") || mustClear) {
          mustClear = false;
          txt_result.setText("" + num);
        } else if (resultText.length() < 10) {
          txt_result.setText(resultText + num);
        } else {
          txt_result.setText(resultText);
        }
      }
    };

    btn_dot.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String resultText = txt_result.getText().toString();
        if (mustClear) {
          mustClear = false;
          txt_result.setText("0");
        }
        if (resultText.contains(".")) {
          return;
        }
        if (resultText.length() > 9) {
          return;
        }
        txt_result.setText(resultText + ".");
      }
    });

    btn_sign.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String resultText = txt_result.getText().toString();
        if (resultText.contains("-")) {
          resultText = resultText.replace("-", "");
        } else {
          resultText = "-" + resultText;
        }
        txt_result.setText(resultText);
      }
    });

    btn_0.setOnClickListener(numberClickListener);
    btn_1.setOnClickListener(numberClickListener);
    btn_2.setOnClickListener(numberClickListener);
    btn_3.setOnClickListener(numberClickListener);
    btn_4.setOnClickListener(numberClickListener);
    btn_5.setOnClickListener(numberClickListener);
    btn_6.setOnClickListener(numberClickListener);
    btn_7.setOnClickListener(numberClickListener);
    btn_8.setOnClickListener(numberClickListener);
    btn_9.setOnClickListener(numberClickListener);

    btn_minus.setOnClickListener(operatorClickListener);
    btn_multiply.setOnClickListener(operatorClickListener);
    btn_division.setOnClickListener(operatorClickListener);
    btn_plus.setOnClickListener(operatorClickListener);
    btn_equal.setOnClickListener(operatorClickListener);

    btn_clear.setOnClickListener(resetClickListener);
    btn_clearEntity.setOnClickListener(resetClickListener);
    btn_back.setOnClickListener(resetClickListener);
  }
}
