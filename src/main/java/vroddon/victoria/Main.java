package vroddon.victoria;

import org.apache.commons.cli.*;

public class Main {


    public static void main(String[] args) throws Exception {
        int port = 8078;     // default

        Options options = new Options();

        Option portOpt = new Option("p", "port", true, "Port to listen on (default 8078)");
        portOpt.setRequired(false);
        options.addOption(portOpt);


        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);


        if (cmd.hasOption("port")) {
            port = Integer.parseInt(cmd.getOptionValue("port"));
        }

        System.out.println("Starting server on port: " + port);

        WebServer server = new WebServer(port);
        server.start();
    }

}
