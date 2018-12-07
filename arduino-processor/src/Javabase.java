import org.json.simple.JSONObject;

import javax.naming.MalformedLinkException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class Javabase {
    private static final Javabase instance = new Javabase();
    static URL DB_URL;
    static String BASE_URL = "https://d-guian.firebaseio.com/";

    static HttpURLConnection connection;
    final static String GET_REQ = "GET";
    final static String POST_REQ = "POST";
    final static String PUT_REQ = "PUT";

    private Javabase(){
        try{
            openConnection();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Javabase getJavabase(){
        if (connection == null){
            try {
                openConnection();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return instance;
    }

//    public

    private static void openConnection() throws Exception {
        DB_URL = new URL("https://d-guian.firebaseio.com/.json");
        connection = (HttpURLConnection) DB_URL.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod(PUT_REQ);
    }

    public void closeConnection(){
        connection.disconnect();
    }

    public void postData(HashMap<String, HashMap<String, String>> newDataSet){
        JSONObject payload = new JSONObject();
        for (String top : newDataSet.keySet()){
            HashMap<String, String> sublist = newDataSet.get(top);
            JSONObject subPayload = new JSONObject();
            subPayload.putAll(sublist);
            payload.put(top, subPayload);
        }
        try{
            OutputStreamWriter pusher = new OutputStreamWriter(connection.getOutputStream());
            pusher.write(payload.toString());
            pusher.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("JSON Post Complete.");
        try {
            StringBuilder sb = new StringBuilder();
            int HttpResult = connection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                System.out.println("" + sb.toString());
            } else {
                System.out.println(connection.getResponseMessage());
            }
        } catch (IOException e){
            System.out.println("Error Getting HTTP Response!");
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }
}