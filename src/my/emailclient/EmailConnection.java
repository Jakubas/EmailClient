/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.emailclient;

import com.sun.mail.imap.IMAPFolder;

import java.util.Properties;

import javax.mail.*;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 * Tries to establish a connection with the email service provider
 * @author dxj452
 */
public class EmailConnection {
    private Store store;
    private IMAPFolder folder;
    private String username;
    private String password;
    
    /**
     * @throws MessagingException
     * runs the connection method that connects with the email server
     */
    public EmailConnection() throws MessagingException {
        Connection();
    }
    
    /**
     * @return the store
     */
    public Store getStore() {
        return store;
    }
    
    /**
     * @return the folder
     */
    public IMAPFolder getFolder() {
        return folder;
    }
    
    /**
     * @return the username
     */
    public String getUsername() {
    	return username;
    }
    
    /**
     * @return the password
     */
    public String getPassword() {
    	return password;
    }
    
    /**
     * @throws MessagingException
     * starts a session with the host
     * only supports gmail at the moment
     */
    private void Connection() throws MessagingException {
        username = "";
        password = "";	        

        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        
		username = JOptionPane.showInputDialog(null, username, "Enter Username", JOptionPane.OK_CANCEL_OPTION); 
		JPasswordField pwd = new JPasswordField(10);   
		int action = JOptionPane.showConfirmDialog(null, pwd,"Enter Password",JOptionPane.OK_CANCEL_OPTION);  
		if(action < 0) {
			JOptionPane.showMessageDialog(null,"Cancel, X or escape key selected"); 
			System.exit(0); 
		}
		else 
			password = new String(pwd.getPassword());  
		
        props.setProperty("mail.user", username);
        props.setProperty("mail.password", password);
        Session session = Session.getDefaultInstance(props);

            store = session.getStore("imaps");
            store.connect("imap.googlemail.com",username, password);
            folder = (IMAPFolder) store.getFolder("inbox"); 
            if(!folder.isOpen()) 
                folder.open(Folder.READ_WRITE);
    }
}
