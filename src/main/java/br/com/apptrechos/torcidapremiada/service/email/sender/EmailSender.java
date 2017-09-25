package br.com.apptrechos.torcidapremiada.service.email.sender;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.apptrechos.torcidapremiada.model.Usuario;

public class EmailSender {
	
	private Properties properties;
	private Session session;

	public EmailSender() {
		this.properties = new Properties();
		carregarPropriedades();
	}

	private void carregarPropriedades() {
		this.properties.put("mail.smtp.host", "smtp.gmail.com");
		this.properties.put("mail.smtp.socketFactory.port", "465");
		this.properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		this.properties.put("mail.smtp.auth", "true");
		this.properties.put("mail.smtp.port", "465");
		
		iniciarSessao();
	}

	private void iniciarSessao() {
		
		 this. session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
                      protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("leandrofreires@anapolis.go.gov.br", "683783gg");
                      }
         });

		 session.setDebug(true);		
	}
	
	public void enviarMensagem(Usuario usuario) throws MessagingException {
		MimeMessage  message = new MimeMessage(this.session);

		message.setFrom(new InternetAddress("leandrofreires@anapolis.go.gov.br"));
		Address[] toUser = InternetAddress.parse(usuario.getEmail());  
		message.setRecipients(Message.RecipientType.TO, toUser);
		message.setSubject("Recuperação de senha");
		message.setText("Olá " + usuario.getNome() + ", <br /><br /> "
				+ "<a href=\"http://192.168.0.105:8080/torcida-premiada/recuperacao/consulta/" + usuario.getTokenSenha() + "\" >"
				+ "Clique aqui</a> para alterar sua senha de acesso. <br /><br />" 
				+ "A equipe de suporte agradece o seu contato!", "utf-8", "html");
			
		Transport.send(message);
	}
	
}
