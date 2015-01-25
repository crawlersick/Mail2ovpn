/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail2ovpn;

import NetTool.HTTPReachableTest;
import RegTool.FetchString;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;

/**
 *
 * @author sadpanda
 */
public class GetattchmentFromMail1 {
    private static final Logger log = Logger.getLogger(GetattchmentFromMail1.class.getName());
    private String IMapHost;
    private String MailId;
    private String MailPassword;
    private String ValidVGHost=null;
    int totalmailcount=0;
    String fromadd="";
    String sentdate;
    String Content;
    String subject;
    byte validvgbinary[];
    
    public GetattchmentFromMail1(String imap,String mid,String mpd)
    {
        this.IMapHost=imap;
        this.MailId=mid;
        this.MailPassword=mpd;
    }
    
    public byte[]  getValidVGattch(){
    return validvgbinary;
    }
    
    public boolean fetchmailforattch() throws IOException, MessagingException
    {
        boolean fetchtest=false;
        
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
               
        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect(IMapHost, MailId, MailPassword);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            totalmailcount=inbox.getMessageCount();
            
            Message msg =null;
            for(int i = totalmailcount;i>0;i--)
            {
                fromadd="";
                msg = inbox.getMessage(i);
                Address[] in = msg.getFrom();
                    for (Address address : in) {
                        fromadd=address.toString()+fromadd;
                        //System.out.println("FROM:" + address.toString());
                    }
                if(fromadd.matches("admin@cronmailservice.appspotmail.com")&&
                        msg.getSubject().matches
                        ("ThanksToTsukuba_World-on-my-shoulders-as-I-run-back-to-this-8-Mile-Road_cronmailservice"))
                    break;
            }
            
            if(fromadd.equals("'")){
                log.log(Level.SEVERE,"Error: no related mail found!" + this.MailId);
                return fetchtest;
            }
            
            
            

        //    Multipart mp = (Multipart) msg.getContent();
          //  BodyPart bp = mp.getBodyPart(0);
            sentdate=msg.getSentDate().toString();

            subject=msg.getSubject();

            Content=msg.getContent().toString();

            
            log.log(Level.INFO,Content);
            log.log(Level.INFO,sentdate);
            
            
            Multipart multipart = (Multipart) msg.getContent();
            for (int i = 0; i < multipart.getCount(); i++) {
            BodyPart bodyPart = multipart.getBodyPart(i);
            if(!Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) &&
                       (bodyPart.getFileName()==null||!bodyPart.getFileName().equals("dataforvgendwithudp.gzip"))
                    ) {
              continue; // dealing with attachments only
            }
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            InputStream is = bodyPart.getInputStream();
            //validvgbinary = IOUtils.toByteArray(is);
            int nRead;
            byte[] data = new byte[5000000];

            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
                    }

        buffer.flush();

        validvgbinary= buffer.toByteArray();
            break;
            }
            
            

            
            
            
            
            
            
            
            
            
            
            fetchtest=true;
        } catch (Exception mex) {
            mex.printStackTrace();

        }
        
        return fetchtest;
    }
    
    
    public static void main(String args[])
    {
        GetattchmentFromMail1 ghfm=new GetattchmentFromMail1("imap-mail.outlook.com", "temp1q2w3e@hotmail.com", "Testing123");
        try {
            ghfm.fetchmailforattch();
            System.out.println(ghfm.getValidVGattch().length);
         //   System.out.println(new String(ghfm.getValidVGattch(),"UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(GetattchmentFromMail1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(GetattchmentFromMail1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
