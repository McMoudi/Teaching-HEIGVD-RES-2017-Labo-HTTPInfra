package ch.heigvd.res.http.net.client;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class HttpClient {
    final static Logger LOG = Logger.getLogger(HttpClient.class.getName());

    private Socket clientSocket;

    public void connect(String server) throws IOException {
        clientSocket = new Socket(server, 80);
    }

    public void disconnect() throws IOException {
        clientSocket.close();
    }

    public boolean isConnected() {
        return clientSocket != null && !clientSocket.isClosed();
    }

    public void requestPage(String host) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        pw.write("GET / HTTP/1.1" + "\r\n");
        pw.write("Host: " + host + "\r\n");
        pw.write("\r\n");
        pw.flush();
        // First we read the headers
        int length = 0;
        String line = br.readLine();
        line = br.readLine();
        while(line != null && line != "\r\n") {
            /*
            switch(line.substring(0, line.indexOf(":"))) {
                case "Content-Length":
                    length = Integer.valueOf(line.substring(line.indexOf(":")+2, line.length()));
                break;
                default:
            }
            */
            System.out.println(line);
            line = br.readLine();
        }
    }
}
