package org.example.zhambyl.player;


import org.example.zhambyl.mailbox.Mailbox;
import org.example.zhambyl.Message;

/***
 * ReceiverPlayer is a player which responds to callee with the same message and concatenated count of received messages
 *
 * When received message count reaches threshold receiver closes mailbox
 */
public class ReceiverPlayer extends AbstractPlayer {

    private final Long threshold;

    public ReceiverPlayer(String name, Long threshold) {
        super(name);
        this.threshold = threshold;
    }

    @Override
    public void onMessage(Message message) {
        send(new Message(getName(), message.getFrom(), message.getMessage() + timesReceived.longValue()));
    }

    @Override
    public void onMailboxOpen(Mailbox mailbox) {
        super.onMailboxOpen(mailbox);

        mailbox.setMessageReceivedPostProcessor(message -> {
            if (timesReceived.longValue() >= threshold) {
                try {
                    mailbox.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
