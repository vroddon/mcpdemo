package vroddon.victoria;

import org.apache.commons.cli.*;

public class Main {


    public static void main(String[] args) throws Exception {

        Options options = new Options();

        Option portOpt = new Option("p", "port", true, "Port to listen on (default 8078)");
        portOpt.setRequired(false);
        options.addOption(portOpt);

        Option keyOpt = new Option("k", "api-key", true, "OpenAI API key (default 000)");
        keyOpt.setRequired(false);
        options.addOption(keyOpt);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        int port = 8078;     // default
        String apiKey = "000"; // default

        if (cmd.hasOption("port")) {
            port = Integer.parseInt(cmd.getOptionValue("port"));
        }

        if (cmd.hasOption("api-key")) {
            apiKey = cmd.getOptionValue("api-key");
        }

        System.out.println("Starting server on port: " + port);
        System.out.println("Using API key: " + apiKey);

        WebServer server = new WebServer(port, apiKey);
        server.start();
    }

}
