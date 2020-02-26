package org.example.zhambyl.mailbox;


import org.example.zhambyl.Message;
import org.example.zhambyl.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static java.lang.System.exit;

public class HttpMailbox implements Mailbox {

    private final MailboxClient mailboxClient;
    private final MailboxServer mailboxServer;
    private final MailboxAddress mailboxAddress;
    private Consumer<Message> messageSentPostProcessor;
    private Consumer<Message> messageReceivedPostProcessor;
    private final List<Player> localPlayers = new ArrayList<>();
    private final Map<String, MailboxAddress> remotePlayers = new HashMap<>();

    public HttpMailbox(MailboxClient mailboxClient, MailboxServer mailboxServer, MailboxAddress mailboxAddress) {
        this.mailboxClient = mailboxClient;
        this.mailboxServer = mailboxServer;
        this.mailboxAddress = mailboxAddress;
    }

    @Override
    public MailboxAddress getAddress() {
        return this.mailboxAddress;
    }

    @Override
    public void registerLocalPlayer(Player player) {
        localPlayers.add(player);
    }

    @Override
    public void registerRemotePlayer(String name, MailboxAddress mailboxAddress) {
        remotePlayers.put(name, mailboxAddress);
    }

    @Override
    public void send(Message message) {
        MailboxAddress address = remotePlayers.get(message.getTo());
        if (address == null) {
            throw new IllegalArgumentException("Unknown player " + message.getTo());
        }
        mailboxClient.post(address, message);

        if (messageSentPostProcessor != null) {
            messageSentPostProcessor.accept(message);
        }
    }

    @Override
    public void receive(Message message) {
        localPlayers.stream()
                .filter(player -> player.getName().equals(message.getTo()))
                .forEach(player -> player.response(message));

        if (messageReceivedPostProcessor != null) {
            messageReceivedPostProcessor.accept(message);
        }
    }

    @Override
    public void open() {
        System.out.println("Opening mailbox");
        if (mailboxServer != null) {
            this.mailboxServer.start(this);
        }
        localPlayers.forEach(player -> player.onMailboxOpen(this));
    }

    @Override
    public void setMessageSentPostProcessor(Consumer<Message> consumer) {
        messageSentPostProcessor = consumer;
    }

    @Override
    public void setMessageReceivedPostProcessor(Consumer<Message> consumer) {
        messageReceivedPostProcessor = consumer;
    }

    @Override
    public void close() {
        System.out.println("Closing mailbox");
        if (mailboxServer != null) {
            mailboxServer.close();
        }
        System.out.println("Exit");
        exit(0);
    }
}
