package com.example.stocks;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PriceFetcher {
    private RequestQueue _queue;
    public static String stockName;
    public final static String REQUEST_URL = "http://127.0.0.10:8080/" + stockName;

    public class StockResponse {

        public double stockPrice;
        public StockResponse(double n) {
            this.stockPrice = n;
        }

    }

    public interface StockResponseListener {
        void onResponse(StockResponse response);
    }

    public PriceFetcher(Context context, String s) {

        _queue = Volley.newRequestQueue(context);
        stockName = s;
    }

    private StockResponse createErrorResponse() {
        return new StockResponse(130.03);
    }

    public void dispatchRequest(final StockResponseListener listener) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, REQUEST_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            StockResponse res = new StockResponse(response.getDouble("price"));
                            listener.onResponse(res);
                        }
                        catch (JSONException e) {
                            listener.onResponse(createErrorResponse());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponse(createErrorResponse());
            }
        });

        _queue.add(req);
    }
}
