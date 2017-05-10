package ch.heigvd.res.labs.http.net.client;

import ch.heigvd.res.labs.http.utils.ReadResponseFilterInputStream;
import ch.heigvd.res.labs.http.utils.Utils;

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

    public void requestPage(String host, Utils.MIMETypes mimeType) throws IOException {
        ReadResponseFilterInputStream fr = new ReadResponseFilterInputStream(clientSocket.getInputStream());
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        pw.write("GET / HTTP/1.1" + "\r\n");
        pw.write("Host: " + host + "\r\n");
        pw.write("Accept: " + mimeType.getFormatStr());
        //pw.write("Connection: close\r\n");
        pw.write("\r\n");
        pw.flush();
        int length = -1;
        String response = "";
        String line = fr.readLine();
        switch(line.substring(9, line.length())) {
            case "200 OK":
                // On lit les headers
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
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] data = new byte[1024];
                    int read = 0;
                    int total = 0;
                    while(total < length && (read = clientSocket.getInputStream().read(data, 0, data.length)) != -1) {
                        baos.write(data, 0, read);
                        total += read;
                    }
                    baos.flush();
                    LOG.log(Level.INFO, "Bytes read: " + total);
                    response = new String(baos.toByteArray(), "UTF-8");
                }
                break;
            default:
                response = line;
        }
        System.out.println(response);
    }
}
