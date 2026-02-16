package vroddon.victoria;

import jakarta.servlet.http.HttpServlet;

public class ChatHandler extends HttpServlet {

    private static String apiKey = null;
    private static String LLM = "https://api.openai.com/v1/chat/completions";

    DeepSeek chino = new DeepSeek();
    
    public ChatHandler() {
    }

    private String getAnswer(String question) {
        return DeepSeek.chat("You are a nice chat", quote(question));
    }

    private String quote(String s) {
        return "\"" + s.replace("\"", "\\\"") + "\"";
    }
}