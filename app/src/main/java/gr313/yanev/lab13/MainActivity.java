package gr313.yanev.lab13;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etA;
    EditText etB;
    TextView result;
    Switch switchDegree;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etA = findViewById(R.id.etA);
        etB = findViewById(R.id.etB);
        result = findViewById(R.id.tvRes);
        switchDegree = findViewById(R.id.switchDegree);

        url = "https://blazedenshinobu.pythonanywhere.com/";
    }

    public boolean isInputsValid() {
        if (etA.getText().toString().isEmpty()) return false;
        if (etB.getText().toString().isEmpty()) return false;
        return true;
    }

    private boolean isInputValid() {
        return !etA.getText().toString().isEmpty();
    }

    public void btnAddClick(View v) {
        performBiOperation("+");
    }

    public void btnSubtractClick(View v) {
        performBiOperation("-");
    }

    public void btnMultiplyClick(View v) {
        performBiOperation("*");
    }

    public void btnDivideClick(View v) {
        performBiOperation(":");
    }

    public void btnPowerClick(View v) {
        performBiOperation("**");
    }

    public void btnSqrtClick(View v) {
        performBiOperation("sqrt");
    }

    public void btnRemainderClick(View v) {
        performBiOperation("!");
    }

    public void btnIntDivideClick(View v) {
        performBiOperation("::");
    }

    public void btnSinClick(View v) {
        performUnaryOperation("sin");
    }

    public void btnAsinClick(View v) {
        performUnaryOperation("asin");
    }

    public void btnCosClick(View v) {
        performUnaryOperation("cos");
    }

    public void btnAcosClick(View v) {
        performUnaryOperation("acos");
    }

    public void btnTgClick(View v) {
        performUnaryOperation("tg");
    }

    public void btnAtgClick(View v) {
        performUnaryOperation("atg");
    }

    private void performBiOperation(String oper) {
        if (!isInputsValid()) {
            showMessage("Введите значения!");
            result.setText("");
            return;
        }

        String a = etA.getText().toString();
        String b = etB.getText().toString();

        HttpRequest request = new HttpRequest(this) {
            @Override
            public void onRequestComplete(String response) {
                Log.e("result", response);

                String[] responseArray = response.split(" = ");
                String resultString = responseArray[responseArray.length - 1];
                result.setText(resultString);
            }
        };
        request.makeRequest(url + "arithmetic/" + a + oper + b);
    }

    private void performUnaryOperation(String oper) {
        if (!isInputValid()) {
            showMessage("Введите первое число!");
            etB.setText("");
            result.setText("");
            return;
        }

        String a = etA.getText().toString();

        HttpRequest request = new HttpRequest(this) {
            @Override
            public void onRequestComplete(String response) {
                Log.e("result", response);

                String[] responseArray = response.split(" = ");
                String resultString = responseArray[responseArray.length - 1];
                etB.setText("");
                result.setText(resultString);
            }
        };

        if (switchDegree.isChecked()) {
            request.makeRequest(url + "trigonometric/deg/" + oper + a);
        } else {
            request.makeRequest(url + "trigonometric/" + oper + a);
        }
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}