package com.lifetools.commons.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtils {
	public static final String HOST = "smtp.exmail.qq.com";
	public static final int PORT = 465;
	public static final String FROM = "zheng_ke@gowild.cn";// 发件人的email
	public static final String PWD = "gp646253052";// 发件人密码

	/**
	 * 获取Session
	 */
	private static Session getSession() {
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", HOST);
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.port", "" + PORT);
		props.put("mail.smtp.auth", "true");

		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(FROM, PWD);
			}

		};
		Session session = Session.getDefaultInstance(props, authenticator);

		return session;
	}

	public static boolean send(String toEmail, String content, String subjectText) {
		if (StringUtils.isBlankOrEmpty(toEmail)) {
			return false;
		}
		Session session = getSession();
		try {
			// Instantiate a message
			Message msg = new MimeMessage(session);

			// Set message attributes
			msg.setFrom(new InternetAddress(FROM));
			String[] tos = toEmail.split(";");
			InternetAddress[] address = new InternetAddress[tos.length];
			for (int i = 0; i < tos.length; i++) {
				address[i] = new InternetAddress(tos[i]);
			}
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subjectText);
			msg.setSentDate(new Date());
			msg.setContent(content, "text/html;charset=utf-8");

			// Send the message
			Transport.send(msg);
			return true;
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
		return false;
	}

}