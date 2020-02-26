package org.example.zhambyl.player;

import org.example.zhambyl.mailbox.Mailbox;
import org.example.zhambyl.Message;

/**
 * Player represents a person that can send and receive messages
 * Player is bound to mailbox
 */
public interface Player {

    String getName();

    void response(Message message);

    Mailbox getMailbox();

    void setMailbox(Mailbox mailbox);

    void onMailboxOpen(Mailbox mailbox);
}
