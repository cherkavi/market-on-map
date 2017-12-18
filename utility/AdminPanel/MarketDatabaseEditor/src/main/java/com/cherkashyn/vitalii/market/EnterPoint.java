package com.cherkashyn.vitalii.market;

import java.awt.EventQueue;

import javax.swing.JFrame;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EnterPoint {
	private final static String[] SPRING_CONTEXT_PATH=new String[]{
																   "classpath:context-editor.xml",
																   "classpath:context-sql.xml"
																   };
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		final ApplicationContext context=new ClassPathXmlApplicationContext(SPRING_CONTEXT_PATH);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					((JFrame)context.getBean("MainWindow")).setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
