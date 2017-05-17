package ch.heigvd.res.labs.http.net.server;

import ch.heigvd.res.labs.http.utils.MIMEType;
import ch.heigvd.res.labs.http.utils.ReadHttpFilterInputStream;

import java.io.*;
import java.util.logging.Logger;

public class ClientHandler {
    final static Logger LOG = Logger.getLogger(ClientHandler.class.getName());

    private ReadHttpFilterInputStream rh;
    private PrintWriter pw;

    public void handleClientConnection(InputStream is, OutputStream os) throws IOException {
        rh = new ReadHttpFilterInputStream(is);
        pw = new PrintWriter(new OutputStreamWriter(os));

        boolean goodRequest = true;
        // On lit la requête
        String request = rh.readLine();
        //TODO vérifier la requête (bad request?)
        String[] requestElems = request.split(" ");
        if(requestElems.length < 2) respondBadRequest();
        String requestType = requestElems[0];
        String ressource = requestElems[1];
        // On lit les headers
        String host;
        MIMEType accept = MIMEType.TEXT_ALL;
        String acceptCharset = "UTF-8";
        int contentLength = 0;
        String line = rh.readLine();
        while(line != null && !line.equals("")) {
            if(line.indexOf(':') == -1) {
                goodRequest = false;
                break;
            }
            switch(line.substring(0, line.indexOf(':'))) {
                case "Host":
                    host = line.substring(line.indexOf(':') + 2, line.length());
                    break;
                case "Accept":
                    accept = MIMEType.getMimeType(line.substring(line.indexOf(':') + 2, line.length()));
                    if(accept == null)
                        accept = MIMEType.TEXT_ALL;
                    break;
                case "Accept-charset":
                    acceptCharset = line.substring(line.indexOf(':') + 2, line.length());
                    break;
                case "Content-length":
                    try {
                        contentLength = Integer.valueOf(line.substring(line.indexOf(':') + 2, line.length()));
                    } catch(NumberFormatException e) {
                        goodRequest = false;
                        break;
                    }
                    break;
                default:
            }
            System.out.println(line);
            line = rh.readLine();
        }
        if(!goodRequest) {
            respondBadRequest();
        } else {
            switch (requestType) {
                case "GET":
                    // TODO
                    break;
                case "HEAD":
                    // TODO
                    break;
                case "POST":
                    respondNotImplemented();
                    break;
                case "PUT":
                    respondNotImplemented();
                    break;
                case "DELETE":
                    respondNotImplemented();
                    break;
                case "CONNECT":
                    respondNotImplemented();
                    break;
                case "OPTIONS":
                    respondNotImplemented();
                    break;
                case "TRACE":
                    respondNotImplemented();
                    break;
                case "PATCH":
                    respondNotImplemented();
                    break;
                default:
                    respondBadRequest();

            }
        }
    }

    private void respondOK() {

    }

    private void respondBadRequest() {

    }

    private void respondNotImplemented() {

    }
}
