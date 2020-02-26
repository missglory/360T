package org.example.zhambyl;

import org.example.zhambyl.mailbox.*;
import org.example.zhambyl.player.InitiatorPlayer;
import org.example.zhambyl.player.Player;
import org.example.zhambyl.player.ReceiverPlayer;

public class Main {
    public static void main(String[] args) {
        if (args[0].equals("initiator")) {
            runInitiator();
        } else if (args[0].equals("receiver")) {
            runReceiver();
        }
    }

    public static void runInitiator() {
        System.out.println("Running Initiator");
        Player initiator = new InitiatorPlayer("Initiator", 10L);

        Mailbox mailbox = new HttpMailbox(new MailboxClient(), new MailboxServer(), new MailboxAddress("localhost", 8000));
        mailbox.registerLocalPlayer(initiator);
        mailbox.registerRemotePlayer("Receiver", new MailboxAddress("localhost", 8001));

        mailbox.open();
    }

    public static void runReceiver() {
        System.out.println("Running receiver");
        Player receiver = new ReceiverPlayer("Receiver", 10L);

        Mailbox mailbox = new HttpMailbox(new MailboxClient(), new MailboxServer(), new MailboxAddress("localhost", 8001));
        mailbox.registerLocalPlayer(receiver);
        mailbox.registerRemotePlayer("Initiator", new MailboxAddress("localhost", 8000));

        mailbox.open();
    }
}
