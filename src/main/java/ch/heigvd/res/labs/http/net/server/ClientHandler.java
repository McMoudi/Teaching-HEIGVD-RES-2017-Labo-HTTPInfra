package ch.heigvd.res.labs.http.net.server;

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

        String request = rh.readLine();
        //TODO vérifier la requête (bad request?)
        String[] requestElems = request.split(" ");
        if(requestElems.length < 2) respondBadRequest();
        String ressource = requestElems[1];
        switch(requestElems[0]) {
            case "GET":
                break;
            case "HEAD":
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

    private void respondOK() {

    }

    private void respondBadRequest() {

    }

    private void respondNotImplemented() {
        
    }
}
