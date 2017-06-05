package br.com.enviromentbox.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kloss Teles on 05/06/2017.
 */
@Component
public class PushyAPI {
    public static ObjectMapper mapper = new ObjectMapper();

    // Insert your Secret API Key here
    public static final String SECRET_API_KEY = "SECRET_API_KEY";

    public static void sendPush(PushyPushRequest req) throws Exception {
        // Get custom HTTP client
        DefaultHttpClient client = new DefaultHttpClient();

        // Create POST request
        HttpPost request = new HttpPost("https://api.pushy.me/push?api_key=" + SECRET_API_KEY);

        // Set content type to JSON
        request.addHeader("Content-Type", "application/json");

        // Convert post data to JSON
        byte[] json = mapper.writeValueAsBytes(req);

        // Send post data as byte array
        request.setEntity(new ByteArrayEntity(json));

        // Execute the request
        HttpResponse response = client.execute(request, new BasicHttpContext());

        // Get response JSON as string
        String responseJSON = EntityUtils.toString(response.getEntity());

        // Convert JSON response into HashMap
        Map<String, Object> map = mapper.readValue(responseJSON, Map.class);

        // Got an error?
        if (map.containsKey("error")) {
            // Throw it
            throw new Exception(map.get("error").toString());
        }
    }

    public void sendSamplePush() {
        // Prepare list of target device tokens
        List<String> deviceTokens = new ArrayList<>();

        // Add your device tokens here
        deviceTokens.add("cdd92f4ce847efa5c7f");

        // Set payload (any object, it will be serialized to JSON)
        Map<String, String> payload = new HashMap<>();

        // Add "message" parameter to payload
        payload.put("message", "Hello World!");

        // iOS notification fields
        Map<String, Object> notification = new HashMap<>();

        notification.put("badge", 1);
        notification.put("sound", "ping.aiff");
        notification.put("body", "Hello World \u270c");

        // Prepare the push request
        PushyPushRequest push = new PushyPushRequest(payload, deviceTokens.toArray(new String[deviceTokens.size()]), notification);

        try {
            // Try sending the push notification
            sendPush(push);
        }
        catch (Exception exc) {
            // Error, print to console
            System.out.println(exc.toString());
        }
    }
}
