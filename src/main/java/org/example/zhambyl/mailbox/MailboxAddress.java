package org.example.zhambyl.mailbox;

/**
 * MailboxAddress represents address of a mailbox
 */
public class MailboxAddress {
    private final String host;
    private final int port;

    public MailboxAddress(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }
}
