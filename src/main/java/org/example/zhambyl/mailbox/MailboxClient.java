package org.example.zhambyl.mailbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.zhambyl.Message;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/***
 * MailboxClient is a http client.
 * MailboxClient communicates with MailboxServer via http in order to deliver a message
 */
public class MailboxClient {

    private final HttpClient httpClient = java.net.http.HttpClient.newBuilder()
            .version(java.net.http.HttpClient.Version.HTTP_2)
            .build();

    private final ObjectMapper mapper = new ObjectMapper();

    void post(MailboxAddress mailboxAddress, Message body) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://" + mailboxAddress.getHost() + ":" + mailboxAddress.getPort() + "/mailbox"))
                    .headers("Content-Type", "application/json;charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofByteArray(mapper.writeValueAsBytes(body)))
                    .build();

             httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
