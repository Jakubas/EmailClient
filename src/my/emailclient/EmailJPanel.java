package my.emailclient;

import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.SystemColor;
import java.awt.Label;

import javax.swing.JTextArea;

import java.awt.Dimension;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * @author dxj452
 * This panel is used for sending an email message with attachments
 */
public class EmailJPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldTo;
	private JTextField textFieldCC;
	private JTextField textFieldSubject;
	private JTextArea textAreaContent;
	private JTextField textFieldAtt;
	/**
	 * Create the panel.
	 */
	public EmailJPanel() {
		setPreferredSize(new Dimension(1120, 800));
		setBackground(SystemColor.activeCaption);
		setLayout(null);
		
		textFieldTo = new JTextField();
		textFieldTo.setBounds(54, 11, 1056, 35);
		add(textFieldTo);
		textFieldTo.setColumns(10);
		
		Label labelTo = new Label("To:");
		labelTo.setBounds(2, 11, 29, 35);
		add(labelTo);
		
		Label labelCC = new Label("CC:");
		labelCC.setBounds(2, 58, 29, 35);
		add(labelCC);
		
		Label labelSubject = new Label("Subject:");
		labelSubject.setBounds(2, 105, 46, 35);
		add(labelSubject);
		
		textFieldCC = new JTextField();
		textFieldCC.setColumns(10);
		textFieldCC.setBounds(54, 58, 1056, 35);
		add(textFieldCC);
		
		textFieldSubject = new JTextField();
		textFieldSubject.setColumns(10);
		textFieldSubject.setBounds(54, 105, 1056, 35);
		add(textFieldSubject);
		
		textAreaContent = new JTextArea();
		textAreaContent.setBounds(12, 166, 1098, 584);
		add(textAreaContent);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					sendEmail();
			}
		});
		btnSend.setBounds(925, 762, 183, 26);
		add(btnSend);
		
		textFieldAtt = new JTextField();
		textFieldAtt.setColumns(10);
		textFieldAtt.setBounds(78, 761, 837, 28);
		add(textFieldAtt);
		
		Label labelAtt = new Label("Attachments:");
		labelAtt.setBounds(2, 762, 78, 26);
		add(labelAtt);

	}

	/**
	 * Sends an email based on the information provided in the jpanel
	 */
	private void sendEmail() {
		try {
			EmailConnection emailConn = new EmailConnection();
		
			String reciever = textFieldTo.getText();
			String cc 		= textFieldCC.getText();
			String subject 	= textFieldSubject.getText();
			String content 	= textAreaContent.getText();
			
			String username = emailConn.getUsername() + "@gmail.com";
			String password = emailConn.getPassword();
			String smtphost = "smtp.gmail.com";
			
			cc = cc.replaceAll("//s", "");
			
			Properties props = System.getProperties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", smtphost);
			props.put("mail.smtp.port", "587");
			props.setProperty("mail.user", username);
			props.setProperty("mail.password", password);
			Session session = Session.getDefaultInstance(props);
			
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(reciever));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
			message.setSubject(subject);
			
			if (textFieldAtt.getText().length() <= 0) {
				message.setText(content);
			} else {
				MimeBodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setText(content);
				Multipart multipart = new MimeMultipart();
			    multipart.addBodyPart(messageBodyPart);
			    
			    String strAttNames = textFieldAtt.getText();
			    strAttNames = strAttNames.replaceAll("\\s", "");
			    String[] attNames = strAttNames.split(",");
			    
				String path = null;
			    for (String attName : attNames) {
			    	File file = new File(attName);
			    	if (file.exists()) {
			    		path = "";
			    	} else {
			    		path = System.getProperty("user.home") + "\\";
			    	}
					messageBodyPart = new MimeBodyPart();
					messageBodyPart.attachFile(path + attName);
				    multipart.addBodyPart(messageBodyPart);
			    }
			    message.setContent(multipart);
			}
					    	
			Transport tr = session.getTransport("smtp");	
			tr.connect(smtphost, username, password);
			tr.sendMessage(message, message.getAllRecipients());
		} catch (MessagingException | IOException e) {
			throw new RuntimeException(e);
		}
	}
}
