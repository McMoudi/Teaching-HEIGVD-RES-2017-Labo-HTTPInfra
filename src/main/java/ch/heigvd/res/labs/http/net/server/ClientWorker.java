package ch.heigvd.res.labs.http.net.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientWorker implements Runnable {
    static final Logger LOG = Logger.getLogger(ClientWorker.class.getName());

    private ClientHandler handler;
    private Socket clientSocket;
    private HttpServer server;

    private InputStream in;
    private OutputStream out;

    private boolean done;

    public ClientWorker(Socket clientSocket, ClientHandler handler, HttpServer server) {
        done = false;
        this.clientSocket = clientSocket;
        this.handler = handler;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            handler.handleClientConnection(clientSocket.getInputStream(), clientSocket.getOutputStream());
        } catch(IOException ex) {
            LOG.log(Level.SEVERE, "Exception in client handler: {0}", ex.getMessage());
        } finally {
            done = true;
            server.notifyClientWorkerDone(this);
            try {
                clientSocket.close();
            } catch(IOException ex) {
                LOG.log(Level.INFO, "Exception while closing socket on the server: {0}", ex.getMessage());
            }
        }
    }

    public boolean isDone() {
        return done;
    }

    public void notifyServerShutdown() {
        try {
            clientSocket.close();
        } catch(IOException ex) {
            LOG.log(Level.INFO, "Exception while closing socket on the server: {0}", ex.getMessage());
        }
    }
}
