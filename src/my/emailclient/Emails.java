/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.emailclient;

import com.sun.mail.imap.IMAPFolder;

import java.io.IOException;

import javax.mail.*;

/**
 * Used to get properties from the folder such as the
 * email subjects or the message flags
 * @author dxj452
 */
public class Emails {
    
    private IMAPFolder folder;
    
    /**
     * @param store the store
     * @param folder the folder that contains email messages
     */
    public Emails(IMAPFolder folder) {
        this.folder = folder;
    }
    
    /**
     * @return the subjects in the folder
     * @throws IOException
     * @throws MessagingException
     */
    public String[] getSubjects() throws IOException, MessagingException {
        String[] subjects = null;
        try {
                int count = 0;
                Message messages[] = folder.getMessages();
                subjects = new String[messages.length];
                // Get all messages
                for(Message message:messages) {
                        subjects[count] = message.getSubject();
                        count++;
                }	
                
        } catch (NoSuchProviderException e) {
        	throw new RuntimeException(e);
        } catch (MessagingException e) {
        	throw new RuntimeException(e);
        }
        return subjects;
    }
    
    /**
     * @return the messages in the folder
     * @throws MessagingException
     */
    public Message[] getMessages() throws MessagingException {
    	return folder.getMessages();
    }
    
    /**
     * @param index the index of the message
     * @return the flags of a message
     * @throws MessagingException
     */
    public Flags getFlags(int index) throws MessagingException {
    	Message[] messages = folder.getMessages();
    	Flags messageFlags = messages[index].getFlags();
		return messageFlags;
    }
}
