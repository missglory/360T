package org.example.zhambyl.mailbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.zhambyl.Message;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * MailboxHandler is a http controller that notifies a Mailbox when message is received
 */
class MailboxHandler implements HttpHandler {
    private final Mailbox mailbox;
    private final ObjectMapper mapper = new ObjectMapper();

    MailboxHandler(Mailbox mailbox) {
        this.mailbox = mailbox;
    }

    @Override
    public void handle(HttpExchange httpExchange) {
        try (InputStream requestBody = httpExchange.getRequestBody()) {

            Message message = mapper.readValue(requestBody, Message.class);
            mailbox.receive(message);
            String response = "This is the response";
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}