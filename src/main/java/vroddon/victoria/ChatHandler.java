package vroddon.victoria;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;

import org.apache.hc.core5.http.io.entity.EntityUtils;

public class ChatHandler extends HttpServlet {

    private static String apiKey = null;

    public ChatHandler(String clave) {
        loadKey();
    }

    private void loadKey() {
        if (apiKey != null) return;  // already loaded

        apiKey = "000";  // default fallback

        try {
            Properties props = new Properties();

            // Looks for mcpdemo.properties in the working directory
            FileInputStream fis = new FileInputStream("mcpdemo.properties");
            props.load(fis);

            String key = props.getProperty("OPEN_APIKEY");
            if (key != null && key.trim().length() > 0) {
                apiKey = key.trim();
            }

            fis.close();
        } catch (Exception ex) {
            // ignore, default will be used
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();

        String line = reader.readLine();
        while (line != null) {
            sb.append(line);
            line = reader.readLine();
        }
        String question = sb.toString();

        String json = getAnswer(question);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }

    private String getAnswer(String question) {
        if (true)
            return "yes, " + question;
        String json = "";
        try {
            String jsonRequest =
                    "{ \"model\": \"gpt-4.1-mini\", "
                    + "  \"messages\": [ { \"role\": \"user\", \"content\": "
                    + quote(question) + " } ] }";

            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost("https://api.openai.com/v1/chat/completions");

            post.setHeader("Content-Type", "application/json");
            post.setHeader("Authorization", "Bearer " + apiKey);

            post.setEntity(new StringEntity(jsonRequest));

            ClassicHttpResponse aiResp = (ClassicHttpResponse) client.execute(post);
            HttpEntity entity = aiResp.getEntity();
            json = EntityUtils.toString(entity);

            client.close();

        } catch (Exception e) {
            // silent fail, as requested
        }
        return json;
    }

    private String quote(String s) {
        return "\"" + s.replace("\"", "\\\"") + "\"";
    }
}