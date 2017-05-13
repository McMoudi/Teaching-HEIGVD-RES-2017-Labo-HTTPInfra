package ch.heigvd.res.labs.http.net.client;

import ch.heigvd.res.labs.http.utils.ReadHttpFilterInputStream;
import ch.heigvd.res.labs.http.utils.MIMEType;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpClient {
    final static Logger LOG = Logger.getLogger(HttpClient.class.getName());

    private Socket clientSocket;

    private ReadHttpFilterInputStream rh;
    private PrintWriter pw;

    public void connect(String server) throws IOException {
        clientSocket = new Socket(server, 80);
        rh = new ReadHttpFilterInputStream(clientSocket.getInputStream());
        pw = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    }

    public void disconnect() throws IOException {
        clientSocket.close();
    }

    public boolean isConnected() {
        return clientSocket != null && !clientSocket.isClosed();
    }

    public void requestPage(String host, MIMEType mimeType) throws IOException {
        pw.write("GET / HTTP/1.1" + "\r\n");
        pw.write("Host: " + host + "\r\n");
        pw.write("Accept: " + mimeType.getFormat() + "\r\n");
        //pw.write("Connection: close\r\n");
        pw.write("\r\n");
        pw.flush();
        String line = rh.readLine();
        System.out.println(line);
        switch(line.substring(9, line.length())) {
            case "200 OK":
                // On lit les headers
                int length = -1;
                line = rh.readLine();
                while(line != null && line != "") {
                    switch(line.substring(0, line.indexOf(":"))) {
                        case "Content-Length":
                            length = Integer.valueOf(line.substring(line.indexOf(":") + 2, line.length()));
                            break;
                        default:
                    }
                    System.out.println(line);
                    line = rh.readLine();
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
                    String response = new String(baos.toByteArray(), "UTF-8");
                    System.out.println(response);
                }
                break;
            case "301 Moved permanently":
                String location;
                line = rh.readLine();
                while(line != null && line != "") {
                    switch(line.substring(0, line.indexOf(":"))) {
                        case "Location":
                            location = line.substring(line.indexOf(":") + 2, line.length());
                            break;
                        default:
                    }
                    System.out.println(line);
                    line = rh.readLine();
                }
                // TODO redirection
                break;
            default:
        }
    }
}
