package com.cherkashyn.vitalii.market.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.cherkashyn.vitalii.market.ui.commodity.CommodityList;
import com.cherkashyn.vitalii.market.ui.point.PointList;
import com.cherkashyn.vitalii.swing_utility.ModalPanel;
import com.cherkashyn.vitalii.swing_utility.ModalResultListener;
import com.cherkashyn.vitalii.swing_utility.UIUtils;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame implements ModalResultListener{
	
	@Autowired
	ApplicationContext context;
	
	private static final long serialVersionUID = 1L;

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
		UIUtils.setToCenterOfScreen(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100,450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final ModalPanel panel = new ModalPanel();
		panel.setBorder(new EmptyBorder(20, 20, 20, 20));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JButton btnPoints = new JButton("Points");
		btnPoints.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				UIUtils.showModal(panel, context.getBean(PointList.class), true);
			}
		});
		panel_1.add(btnPoints);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JButton btnCommodity = new JButton("Commodity");
		panel_2.add(btnCommodity);
		btnCommodity.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				UIUtils.showModal(panel, context.getBean(CommodityList.class), true);
			}
			
		});
	}

	@Override
	public void childWindowModalResult(Object value) {
		if(value instanceof ModalResultListener.Result){
			// check result 
			ModalResultListener.Result result=(ModalResultListener.Result)value;
			switch(result){
			case Ok:{
					System.out.println("Ok ");
					}; break;
			case Cancel:{
					System.out.println("Cancel");
					};break; 
			}
			
		}
		
	}

}
