package ch.heigvd.res.labs.http.net.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpServer {
    final static Logger LOG = Logger.getLogger(HttpServer.class.getName());

    private ServerSocket serverSocket;

    List<ClientWorker> clientWorkers = new CopyOnWriteArrayList<>();

    private boolean shouldRun = false;

    public void startServer() throws IOException {
        if(serverSocket == null || serverSocket.isBound() == false) {
            serverSocket = new ServerSocket(80);
        }

        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(shouldRun) {
                    try {
                        LOG.log(Level.INFO, "Listening for client connection on {0}", serverSocket.getLocalSocketAddress());
                        Socket clientSocket = serverSocket.accept();
                        LOG.info("New client has arrived...");
                        ClientWorker worker = new ClientWorker(clientSocket, new ClientHandler(), HttpServer.this);
                        clientWorkers.add(worker);
                        LOG.info("Delegating work to client worker...");
                        Thread clientThread = new Thread(worker);
                        clientThread.start();
                    } catch(IOException ex) {
                        LOG.log(Level.SEVERE, "IOException in main server thread, exit: {0}", ex.getMessage());
                        shouldRun = false;
                    }
                }
            }
        });
        serverThread.start();
    }

    public boolean isRunning() {
        return serverSocket.isBound();
    }

    public void stopServer() throws IOException {
        shouldRun = false;
        serverSocket.close();
        for(ClientWorker worker : clientWorkers)
            worker.notifyServerShutdown();
    }

    public void notifyClientWorkerDone(ClientWorker worker) {
        clientWorkers.remove(worker);
    }
}
