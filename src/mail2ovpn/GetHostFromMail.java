/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail2ovpn;

import RegTool.FetchString;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

/**
 *
 * @author sadpanda
 */
public class GetHostFromMail {
    private static final Logger log = Logger.getLogger(GetHostFromMail.class.getName());
    private String IMapHost;
    private String MailId;
    private String MailPassword;
    private String ValidVGHost=null;
    int totalmailcount=0;
    String fromadd="";
    String sentdate;
    String Content;
    String subject;
    
    public GetHostFromMail(String imap,String mid,String mpd)
    {
        this.IMapHost=imap;
        this.MailId=mid;
        this.MailPassword=mpd;
    }
    
    public String getValidVGHost(){
    return ValidVGHost;
    }
    
    public boolean fetchmailforhost()
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
                if(fromadd.matches("VPN Gate Daily Mirrors <vpngate-daily@vpngate\\.net>"))
                    break;
            }
            
            if(fromadd.equals("'")){
                log.log(Level.SEVERE,"Error: no related mail found!" + this.MailId);
                return fetchtest;
            }
            
            
            

        //    Multipart mp = (Multipart) msg.getContent();
          //  BodyPart bp = mp.getBodyPart(0);
            sentdate=msg.getSentDate().toString();
            //System.out.println("SENT DATE:" + msg.getSentDate());
            subject=msg.getSubject();
            //System.out.println("SUBJECT:" + msg.getSubject());
            Content=msg.getContent().toString();
            //System.out.println("CONTENT:" + msg.getContent());//bp.getContent());
            
            //System.out.println(Content);
            
            log.log(Level.INFO,Content);
            
        //    FetchString fs=new FetchString(Content,".*(http://[0-9a-zA-Z\\.]+:[0-9]+/).*");
         //   FetchString fs2;
            ArrayList<String> fetchhostlist=FetchString.getmatchstr(Content,".*(http://[0-9a-zA-Z\\.]+:[0-9]+/).*");
            
            
            
            for(String tempstr:fetchhostlist)
            {
                log.log(Level.INFO, "got url: "+tempstr);
          //      fs2=new FetchString(tempstr,"http://([0-9a-zA-Z\\.]+):[0-9]+/).*");
                //ValidVGHost
                
            }
            
            fetchtest=true;
        } catch (Exception mex) {
            mex.printStackTrace();

        }
        
        return fetchtest;
    }
    
    
    public static void main(String args[])
    {
        GetHostFromMail ghfm=new GetHostFromMail("imap-mail.outlook.com", "temp1q2w3e@hotmail.com", "Testing123");
        ghfm.fetchmailforhost();
    }    
}
