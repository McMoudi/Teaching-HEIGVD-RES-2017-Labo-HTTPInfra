package ch.heigvd.res.http.net.client;

import ch.heigvd.res.http.utils.ReadResponseFilterReader;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
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
        ReadResponseFilterReader fr = new ReadResponseFilterReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        pw.write("GET / HTTP/1.1" + "\r\n");
        pw.write("Host: " + host + "\r\n");
        pw.write("\r\n");
        pw.flush();
        // First we read the headers
        int length = -1;
        String line = fr.readLine();
        // TODO traiter les codes de réponse http
        line = fr.readLine();
        while(line != null && line != "") {
            switch(line.substring(0, line.indexOf(":"))) {
                case "Content-Length":
                    length = Integer.valueOf(line.substring(line.indexOf(":") + 2, line.length()));
                    break;
                default:
            }
            System.out.println(line);
            line = fr.readLine();
        }

        if(length != -1) {
            LOG.log(Level.INFO, "Reading length: " + length);
            byte[] data = new byte[length];
            int bytesRead = clientSocket.getInputStream().read(data);
            LOG.log(Level.INFO, "Bytes read: " + bytesRead);
            String response = new String(data, "UTF-8");
            System.out.println(response);
        }
    }
}
