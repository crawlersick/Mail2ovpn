/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail2ovpn;

import NetTool.Base64Coder;
import RegTool.ChapterItem;
import RegTool.FetchAnyWeb;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.mail.MessagingException;
//import sun.misc.BASE64Decoder;

/**
 *
 * @author sadpanda
 */
public class Mail2ovpn {
private static final Logger logger = Logger.getLogger(Mail2ovpn.class.getName());
static String  folder_tar="/home/sadpanda/VPN/";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
                

        if(args != null&&args.length==1)
        {
           folder_tar=args[0];   
        }
        
           File tempfile;
           tempfile=new File(folder_tar);
           if(!tempfile.isDirectory())
           {
     //          System.out.println("output folder "+folder_tar+" is not a valid folder!");
               logger.log(Level.SEVERE, "output folder "+folder_tar+" is not a valid folder!");
               System.exit(1);
           }
        
        
        	try {
                    
                            GetattchmentFromMail1 ghfm=new GetattchmentFromMail1("imap-mail.outlook.com", "temp1q2w3e@hotmail.com", "Testing123");
            try {
                ghfm.fetchmailforattch();
                byte downloaded[] = ghfm.getValidVGattch();
                String downloadedtext=decompress(downloaded);
                 int delaynum=120;
                int speednum=2500000;
                String targetoutputfolder=folder_tar;
                Mail2ovpn m2o=new Mail2ovpn();
                
                
                
                m2o.resultAnalyst1(downloadedtext,delaynum,speednum,targetoutputfolder);
                /*
                GetHostFromMail ghfm=new GetHostFromMail("imap-mail.outlook.com", "temp1q2w3e@hotmail.com", "Testing123");
                ghfm.fetchmailforhost();
                System.out.println(ghfm.getValidVGHost());
                logger.log(Level.INFO, "Got host:  "+ghfm.getValidVGHost());
                
                //String reg="<td class='vg_table_row_[0-9]+'<b><span style='font-size: 9pt;'>([0-9a-zA-Z]+).opengw.net.+?UDP: ([0-9]+).+?</span></b></td></tr>";
                String reg="<td class='vg_table_row_[0-9]+'><b><span style='font-size: 9pt;'>([0-9a-zA-Z]+).opengw.net.+?UDP: ([0-9]+).+?</span></b></td></tr>";
                
                //  FetchAnyWeb faw=new FetchAnyWeb(ghfm.getValidVGHost()+"en/",reg);
                FetchAnyWeb faw=new FetchAnyWeb("http://www.vpngate.net/"+"en/",reg);
                faw.initreq();
                ArrayList<ChapterItem> mappingforudp=faw.getIndex();
                StringBuilder s = new StringBuilder();
                for(int i=0;i<mappingforudp.size();i++)
                {
                
                s.append(mappingforudp.get(i).geturl());
                s.append(",");
                s.append(mappingforudp.get(i).getdesc());
                s.append(",");
                
                }
                System.out.println(s);
                StringBuilder sd=FetchWEB.getasstring(ghfm.getValidVGHost()+"api/iphone/");
                
                int delaynum=120;
                int speednum=2500000;
                String targetoutputfolder=folder_tar;
                Mail2ovpn m2o=new Mail2ovpn();
                m2o.resultAnalyst(sd.toString(),s.toString(),delaynum,speednum,targetoutputfolder);
                */
            } catch (MessagingException ex) {
                Logger.getLogger(Mail2ovpn.class.getName()).log(Level.SEVERE, null, ex);
            }
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
        
        // TODO code application logic here
    }
    
    
    
      public void resultAnalyst(String restr1,String restr2,int delaynum,int speednum,String targetoutputfolder) throws IOException
    {
        
                 //  String []tempgetudplist=restr.split("sickjohnsisick1122356l112355iaaaoss");
            
 
            
            String []tempstrs=restr1.split("\\r\\n");
            

 
            
            
            for(int i=10;i<tempstrs.length;i++)
            {

                String []tempstrsxxxx=tempstrs[i].split(",");
              //vpn539246233|182.216.181.220|508611|35|41230804|Korea Republic of|KR|13|  
              //#HostName|IP|Score|Ping|Speed|CountryLong|CountryShort|NumVpnSessions|Uptime|TotalUsers|TotalTraffic|LogType|Operator|Message|OpenVPN_ConfigData_Base64|\
               
               if(tempstrsxxxx.length>14)
               {
                   //BASE64Decoder decoder = new BASE64Decoder();
                  
                   
                   
                   //byte[] decodedBytes = decoder.decodeBuffer(tempstrsxxxx[14]);
                   byte[] decodedBytes = Base64Coder.decode(tempstrsxxxx[14]);
                   
                   tempstrsxxxx[14]=new String(decodedBytes,"UTF-8");
                   tempstrsxxxx[14]=tempstrsxxxx[14].replaceAll("#.+?\r\n", "");
                   
                                       // System.out.println
                                                logger.info(tempstrsxxxx[0]+"|"+tempstrsxxxx[1]+"|"+tempstrsxxxx[2]+
                       "|"+tempstrsxxxx[3]+"|"+tempstrsxxxx[4]+"|"+tempstrsxxxx[5]+"|"+tempstrsxxxx[6]
                       +"|"+tempstrsxxxx[7]+"|"+tempstrsxxxx[8]+"|"+tempstrsxxxx[9]+"|"+tempstrsxxxx[10]+"|"
                       +tempstrsxxxx[11]+"|"+tempstrsxxxx[12]+"|"+tempstrsxxxx[13]+"|"
                               // +udplist.get(tempudpportnum+1)+"|" //+tempstrsxxxx[14]
                           );
                   
                   
                   if ( isNumericInt(tempstrsxxxx[7])&&isNumericInt(tempstrsxxxx[3])&&isNumericInt(tempstrsxxxx[4])&&
                           //tempstrsxxxx[14].indexOf("proto udp")!=-1 &&  
                          // Integer.valueOf(tempstrsxxxx[7])>0        &&
                             Integer.valueOf(tempstrsxxxx[3])<delaynum
                           &&  Integer.valueOf(tempstrsxxxx[4])>speednum
                      )
                   {   
                   
                     //  tempgetudplist[1].split(",");
                       List<String> udplist = Arrays.asList(restr2.split(","));
                       
                       int tempudpportnum=udplist.indexOf(tempstrsxxxx[0]);
                       
                       if(tempudpportnum!=-1)
                       {
                          
                           tempstrsxxxx[14]=tempstrsxxxx[14].replace("proto tcp", "proto udp");
                           tempstrsxxxx[14]=tempstrsxxxx[14].replaceFirst("remote [0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+ [0-9]+", 
                                   "remote "+tempstrsxxxx[1]+" "+udplist.get(tempudpportnum+1));
                           
                           
                        int performrank=Integer.valueOf(tempstrsxxxx[2])/10000;

                        File tempfile=new File(targetoutputfolder+tempstrsxxxx[1]+"_"+tempstrsxxxx[6]+"_udp_"+"Rank"+performrank+".ovpn");
                        FileOutputStream osss =new FileOutputStream(tempfile);
                        osss.write(tempstrsxxxx[14].getBytes("UTF-8"));
                        osss.close(); 
                        
                        //System.out.println
                                logger.info(tempfile.getAbsoluteFile().toString());
                        tempfile=null;   
                           
   
                        
                        
                       }
                           //+tempstrsxxxx[14]+"|");
               }
                   
               }
               
 
            }

    }
    
      
          public void resultAnalyst1(String restr,int delaynum,int speednum,String targetoutputfolder) throws IOException
    {
        
                   String []tempgetudplist=restr.split("sickjohnsisick1122356l112355iaaaoss");
            
       //     System.out.println(tempgetudplist[1]);
            
            String []tempstrs=tempgetudplist[0].split("\\r\\n");
            

 
            
            
            for(int i=10;i<tempstrs.length;i++)
            {

                String []tempstrsxxxx=tempstrs[i].split(",");
              //vpn539246233|182.216.181.220|508611|35|41230804|Korea Republic of|KR|13|  
              //#HostName|IP|Score|Ping|Speed|CountryLong|CountryShort|NumVpnSessions|Uptime|TotalUsers|TotalTraffic|LogType|Operator|Message|OpenVPN_ConfigData_Base64|\
               
               if(tempstrsxxxx.length>14)
               {
               //    BASE64Decoder decoder = new BASE64Decoder();
                   
                  //byte[] decodedBytes = decoder.decodeBuffer(tempstrsxxxx[14]);
                                      byte[] decodedBytes = Base64Coder.decode(tempstrsxxxx[14]);

                   
                   tempstrsxxxx[14]=new String(decodedBytes,"UTF-8");
                   tempstrsxxxx[14]=tempstrsxxxx[14].replaceAll("#.+?\r\n", "");
                   
                                       // System.out.println
                                                logger.info(tempstrsxxxx[0]+"|"+tempstrsxxxx[1]+"|"+tempstrsxxxx[2]+
                       "|"+tempstrsxxxx[3]+"|"+tempstrsxxxx[4]+"|"+tempstrsxxxx[5]+"|"+tempstrsxxxx[6]
                       +"|"+tempstrsxxxx[7]+"|"+tempstrsxxxx[8]+"|"+tempstrsxxxx[9]+"|"+tempstrsxxxx[10]+"|"
                       +tempstrsxxxx[11]+"|"+tempstrsxxxx[12]+"|"+tempstrsxxxx[13]+"|"
                               // +udplist.get(tempudpportnum+1)+"|" //+tempstrsxxxx[14]
                           );
                   
                   
                   if ( isNumericInt(tempstrsxxxx[7])&&isNumericInt(tempstrsxxxx[3])&&isNumericInt(tempstrsxxxx[4])&&
                           //tempstrsxxxx[14].indexOf("proto udp")!=-1 &&  
                          // Integer.valueOf(tempstrsxxxx[7])>0        &&
                             Integer.valueOf(tempstrsxxxx[3])<delaynum
                           &&  Integer.valueOf(tempstrsxxxx[4])>speednum
                      )
                   {   
                   
                     //  tempgetudplist[1].split(",");
                       List<String> udplist = Arrays.asList(tempgetudplist[1].split(","));
                       
                       int tempudpportnum=udplist.indexOf(tempstrsxxxx[0]);
                       
                       if(tempudpportnum!=-1)
                       {
                          
                           tempstrsxxxx[14]=tempstrsxxxx[14].replace("proto tcp", "proto udp");
                           tempstrsxxxx[14]=tempstrsxxxx[14].replaceFirst("remote [0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+ [0-9]+", 
                                   "remote "+tempstrsxxxx[1]+" "+udplist.get(tempudpportnum+1));
                           
                           
                        int performrank=Integer.valueOf(tempstrsxxxx[2])/10000;

                        File tempfile=new File(targetoutputfolder+tempstrsxxxx[1]+"_"+tempstrsxxxx[6]+"_udp_"+"Rank"+performrank+".ovpn");
                        FileOutputStream osss =new FileOutputStream(tempfile);
                        osss.write(tempstrsxxxx[14].getBytes("UTF-8"));
                        osss.close(); 
                        
                        //System.out.println
                                logger.info(tempfile.getAbsoluteFile().toString());
                        tempfile=null;   
                           
   
                        
                        
                       }
                           //+tempstrsxxxx[14]+"|");
               }
                   
               }
               
 
            }

    }
    
    public static boolean isNumericInt(String str)  
{  
  try  
  {  
    Integer d = Integer.parseInt(str);  
  }  
  catch(NumberFormatException nfe)  
  {  
    return false;  
  }  
  return true;  
}
    
           public static byte[] compress(String string) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream(string.length());
        GZIPOutputStream gos = new GZIPOutputStream(os);
        gos.write(string.getBytes());
        gos.close();
        byte[] compressed = os.toByteArray();
        os.close();
        return compressed;
    }

    public static String decompress(byte[] compressed) throws IOException {
        final int BUFFER_SIZE = 32;
        ByteArrayInputStream is = new ByteArrayInputStream(compressed);
        GZIPInputStream gis = new GZIPInputStream(is, BUFFER_SIZE);
        StringBuilder string = new StringBuilder();
        byte[] data = new byte[BUFFER_SIZE];
        int bytesRead;
        while ((bytesRead = gis.read(data)) != -1) {
            string.append(new String(data, 0, bytesRead));
        }
        gis.close();
        is.close();
        return string.toString();
    }
    
}
