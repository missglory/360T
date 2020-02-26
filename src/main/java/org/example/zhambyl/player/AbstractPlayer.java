package org.example.zhambyl.player;

import org.example.zhambyl.mailbox.Mailbox;
import org.example.zhambyl.Message;

import java.util.concurrent.atomic.AtomicLong;

abstract class AbstractPlayer implements Player {

    protected final String name;
    protected Mailbox mailbox;
    protected AtomicLong timesSent = new AtomicLong(0);
    protected AtomicLong timesReceived = new AtomicLong(0);

    protected AbstractPlayer(String name) {
        this.name = name;
    }

    @Override
    public Mailbox getMailbox() {
        return this.mailbox;
    }

    @Override
    public void setMailbox(Mailbox mailbox) {
        this.mailbox = mailbox;
    }

    @Override
    public String getName() {
        return name;
    }

    protected void send(Message message) {
        System.out.println(this.toString() + " sending message " + message.getMessage() + " to " + message.getTo());
        timesSent.incrementAndGet();
        this.mailbox.send(message);
    }

    @Override
    public void response(Message message) {
        System.out.println(this.toString() + " received message " + message.getMessage() + " from " + message.getFrom());
        timesReceived.incrementAndGet();
        onMessage(message);
    }

    abstract void onMessage(Message message);

    @Override
    public void onMailboxOpen(Mailbox mailbox) {
        this.mailbox = mailbox;
    }
}
