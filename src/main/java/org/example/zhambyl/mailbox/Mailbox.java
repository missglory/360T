package org.example.zhambyl.mailbox;

import org.example.zhambyl.Message;
import org.example.zhambyl.player.Player;

import java.util.function.Consumer;

/***
 * Mailbox in an abstraction of messaging system
 *
 * Players register themself and register remote players along with their address
 * Mailbox performs lookups based on message **to** field in order to decide where message should be routed
 *
 * Mailbox notifies local players when it is started in order to them to start messaging
 */
public interface Mailbox extends AutoCloseable {

    MailboxAddress getAddress();

    void registerLocalPlayer(Player player);

    void registerRemotePlayer(String name, MailboxAddress mailboxAddress);

    void send(Message message);

    void receive(Message message);

    void open();

    void setMessageSentPostProcessor(Consumer<Message> consumer);

    void setMessageReceivedPostProcessor(Consumer<Message> consumer);
}
