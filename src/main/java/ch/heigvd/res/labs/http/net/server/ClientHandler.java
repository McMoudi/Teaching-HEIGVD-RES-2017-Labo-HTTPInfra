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
        switch(requestElems[0]) {
            case "GET":
                String ressource = requestElems[1];
                break;
            default:
                returnNotImplemented();

        }
    }

    private void returnOK() {

    }

    private void returnBadRequest() {

    }

    private void returnNotImplemented() {

    }
}
