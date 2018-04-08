package net.gulernet.app.ipadresim;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {


    // Will show the string "data" that holds the results
    TextView ip;
    TextView ulke;
    TextView ulkekodu;
    TextView saglayici;
    TextView saglayicikodu;
    Button yenile;
    // URL of object to be parsed
    String JsonURL = "http://ip-api.com/json";
    // This string will hold the results
    String data = "";
    // Defining the Volley request queue that handles the URL request concurrently
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        yenile = (Button) findViewById(R.id.yenile);
        loadData();
        yenile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });



    }

    private void loadData() {
        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(this);

        // Casts results into the TextView found within the main layout XML with id jsonData
        ip = (TextView) findViewById(R.id.ip);
        ulke = (TextView) findViewById(R.id.ulke);
        ulkekodu = (TextView) findViewById(R.id.ulkekodu);
        saglayici = (TextView) findViewById(R.id.saglayici);
        saglayicikodu = (TextView) findViewById(R.id.saglayicikodu);
        // Creating the JsonObjectRequest class called obreq, passing required parameters:
        //GET is used to fetch data from the server, JsonURL is the URL to be fetched from.
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, JsonURL,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            // Retrieves the string labeled "colorName" and "description" from
                            //the response JSON Object
                            //and converts them into javascript objects
                            String jsonip = response.getString("query");
                            String jsonulke = response.getString("country");
                            String jsonulkekodu = response.getString("countryCode");
                            String jsonsaglayici = response.getString("isp");
                            String jsonsaglayicikodu = response.getString("as");

                            // Adds strings from object to the "data" string


                            // Adds the data string to the TextView "results"
                            ip.setText(jsonip);
                            ulke.setText(jsonulke);
                            ulkekodu.setText(jsonulkekodu);
                            saglayici.setText(jsonsaglayici);
                            saglayicikodu.setText(jsonsaglayicikodu);

                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );
        // Adds the JSON object request "obreq" to the request queue
        requestQueue.add(obreq);
    }

}