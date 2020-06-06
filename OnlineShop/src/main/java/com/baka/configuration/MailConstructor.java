package com.baka.configuration;

import java.util.Locale;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.baka.models.Order;
import com.baka.models.User;

@Component
public class MailConstructor {

	@Autowired
	private TemplateEngine templateEngine;
	
	
	public MimeMessagePreparator  constructOrderInformationEmail(User user, Order order, Locale locale) {
		
		Context context = new Context();
		context.setVariable("order", order);
		context.setVariable("user", user);
		context.setVariable("cartItems", order.getCartItems());
		String text = templateEngine.process("orderConfirmationEmailTemplate", context);
		
		MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				
				MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
				email.setTo(user.getEmail());
				email.setSubject("Order confirmation - " + order.getId());
				email.setText(text, true);
				email.setFrom(new InternetAddress("onlineshop@live.com"));
			}
		};
		
		return mimeMessagePreparator;
	}
}
