/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package my.emailclient;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.sun.mail.imap.IMAPFolder;

import javax.mail.*;
import javax.swing.JFrame;


/**
 * @author dxj452
 * Initializes the client jframe and email jframe and sets their properties
 * Only supports gmail at the moment
 */
public class EmailClient {

	public static void main(String[] args) throws MessagingException {
            EmailConnection emailConn = new EmailConnection();
            IMAPFolder folder = emailConn.getFolder();
            Store store = emailConn.getStore();
            
            EmailJPanel emailJPanel = new EmailJPanel();
            JFrame frameEmail = new JFrame();
            frameEmail.add(emailJPanel);
            frameEmail.setTitle("Compose New Email");
            frameEmail.pack();
            
            ClientJPanel clientJPanel = new ClientJPanel(folder, frameEmail);
            JFrame frameClient = new JFrame();
            frameClient.add(clientJPanel);
            frameClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameClient.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {	
                	try {
                		if (folder != null && folder.isOpen()) { folder.close(true); }
        				if (store != null) { store.close(); }
                	} catch (MessagingException e1) {
                		throw new RuntimeException(e1);
                	}
                }
            });
            frameClient.setTitle("Email Client");
            frameClient.pack();
            frameClient.setVisible(true);
	}
}
