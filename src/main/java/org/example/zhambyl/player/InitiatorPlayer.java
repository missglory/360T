package org.example.zhambyl.player;

import org.example.zhambyl.mailbox.Mailbox;
import org.example.zhambyl.Message;

/***
 * InitiatorPlayer is a player that initiates a communication with a player named Receiver
 * To every delivered message initiator replies with a simple message
 *
 * When sent message count reaches threshold initiator closes mailbox
 */
public class InitiatorPlayer extends AbstractPlayer {

    private final Long threshold;

    public InitiatorPlayer(String name, Long threshold) {
        super(name);
        this.threshold = threshold;
    }

    @Override
    public void onMessage(Message message) {
        send(new Message(getName(), message.getFrom(), "Hi"));
    }

    @Override
    public void onMailboxOpen(Mailbox mailbox) {
        super.onMailboxOpen(mailbox);

        mailbox.setMessageSentPostProcessor(message -> {
            if (timesSent.longValue() > threshold) {
                try {
                    mailbox.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        send(new Message(getName(), "Receiver", "Hi"));
    }
}
