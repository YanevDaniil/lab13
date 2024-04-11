package gr313.yanev.lab13;

import android.app.Activity;
import android.util.Log;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {
    Activity ctx;

    public HttpRequest(Activity ctx) {
        this.ctx = ctx;
    }


    class Worker implements Runnable {
        String target;
        public Worker(String target) {
            this.target = target;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(target);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                BufferedInputStream inp = new BufferedInputStream(con.getInputStream());
                byte[] buf = new byte[100];
                String res = "";

                while (true) {
                    int n = inp.read(buf);
                    if(n <= 0) break;
                    Log.e("connection","read");
                    res += new String(buf,0,n);
                }
                con.disconnect();
                Log.e("connection","closed");
                final String res2 = res;
                ctx.runOnUiThread(() -> onRequestComplete(res2));
            }

            catch(Exception ex) {
                Log.e("error", ex.toString());
            }
        }
    }

    public void onRequestComplete(String response) {}

    public void makeRequest(String endpoint)
    {
        Worker w = new Worker(endpoint);
        Thread t = new Thread(w);
        t.start();
    }

}