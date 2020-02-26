package org.example.zhambyl.mailbox;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/***
 * MailboxServer is http server.
 * MailboxServer listens on port specified in MailboxAddress and notifies Mailbox whenever messages arrive
 */
public class MailboxServer {

    private HttpServer server;
    private ExecutorService httpThreadPool;

    public void start(Mailbox mailbox) {
        try {
            this.httpThreadPool = Executors.newFixedThreadPool(2);

            server = HttpServer.create(new InetSocketAddress(mailbox.getAddress().getPort()), 0);
            server.createContext("/mailbox", new MailboxHandler(mailbox));
            server.setExecutor(httpThreadPool);
            server.start();

            System.out.println("Started mailbox server");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        System.out.println("Closing mailbox server");
        server.stop(1);
        httpThreadPool.shutdownNow();
    }
}


