import java.util.*;
import javax.mail.*;

public class ReadingEmail {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        int totalmailcount=0;
        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect("imap-mail.outlook.com", "temp1q2w3e@hotmail.com", "Testing123");
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            totalmailcount=inbox.getMessageCount();
            System.out.println("TOTOAL MAILS:"+totalmailcount);
            Message msg = inbox.getMessage(totalmailcount);
            Address[] in = msg.getFrom();
            for (Address address : in) {
                System.out.println("FROM:" + address.toString());
            }
        //    Multipart mp = (Multipart) msg.getContent();
          //  BodyPart bp = mp.getBodyPart(0);
            
            System.out.println("SENT DATE:" + msg.getSentDate());
            System.out.println("SUBJECT:" + msg.getSubject());
            System.out.println("CONTENT:" + msg.getContent());//bp.getContent());
        } catch (Exception mex) {
            mex.printStackTrace();
        }
    }
}