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
    String stockNameHolder;
    Button Fetch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fetch = (Button) findViewById(R.id.Fetch);
        stockInput = (EditText) findViewById(R.id.stockInput);

        Fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(v.getContext(), "Calculating stock price...", Toast.LENGTH_LONG);
                toast.show();
                stockNameHolder = stockInput.getText().toString();
                fetchPrice(v, stockNameHolder);
            }

            private void fetchPrice(final View v, String s) {

                final PriceFetcher fetcher = new PriceFetcher(v.getContext(), s);

                fetcher.dispatchRequest(new PriceFetcher.StockResponseListener() {
                    @Override
                    public void onResponse(PriceFetcher.StockResponse response) {
                        ((TextView)MainActivity.this.findViewById(R.id.StockPrice)).setText(String.valueOf(response.stockPrice));
                    }
                });
            }
        });
    }
}