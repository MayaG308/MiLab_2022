package com.example.stocks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText stockInput;
    TextView resultOutput;
    public static String stockNameHolder;
    Button Fetch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fetch = (Button) findViewById(R.id.Fetch);
        stockInput = (EditText) findViewById(R.id.stockInput);
        resultOutput = (TextView) findViewById(R.id.StockPrice);

        Fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(v.getContext(), "Calculating stock price...", Toast.LENGTH_LONG);
                toast.show();
                stockNameHolder = (String) stockInput.getText().toString();
                fetchPrice(v);
            }

            private void fetchPrice(final View v) {

                final PriceFetcher fetcher = new PriceFetcher(v.getContext());

                fetcher.dispatchRequest(new PriceFetcher.StockResponseListener() {
                    @Override
                    public void onResponse(PriceFetcher.StockResponse response) {

                        if (response.isError){
                            Toast.makeText(v.getContext(), "ERROR FOUND", Toast.LENGTH_LONG);
                        }
                        resultOutput.setText(response.stockPrice);
                    }
                });
            }
        });
    }
}