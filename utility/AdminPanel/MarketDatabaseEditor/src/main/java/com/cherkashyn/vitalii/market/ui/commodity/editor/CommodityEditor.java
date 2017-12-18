package com.cherkashyn.vitalii.market.ui.commodity.editor;

import com.cherkashyn.vitalii.market.datasource.commodity.CommodityFinderNavigation;
import com.cherkashyn.vitalii.market.datasource.commodity.CommodityRepository;
import com.cherkashyn.vitalii.market.domain.Commodity;
import com.cherkashyn.vitalii.market.exception.StoreException;
import com.cherkashyn.vitalii.market.ui.exception.ValidatorException;
import com.cherkashyn.vitalii.swing_utility.ModalPanel;
import com.cherkashyn.vitalii.swing_utility.ModalResultListener;

import java.awt.BorderLayout;

import javax.annotation.PostConstruct;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CommodityEditor extends ModalPanel {
	private static final long serialVersionUID = 1L;
	
	final static int WIDTH=320;
	final static int HEIGHT=120;
	
	@Autowired
	CommodityRepository repository;

	@Autowired
	CommodityFinderNavigation finder;
	
	public CommodityEditor() {
	}
	
	@PostConstruct
	public void init(){
		setLayout(new BorderLayout(0, 0));
		
		JPanel panelContent = new JPanel();
		add(panelContent, BorderLayout.CENTER);
		panelContent.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("Name:");
		panelContent.add(lblNewLabel);
		
		fieldName = new JTextField();
		panelContent.add(fieldName);
		fieldName.setColumns(20);
		fieldName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar()=='\n'){
					onButtonSave();
				}
				if(e.getKeyChar()==27){
					onButtonCancel();
				}
			}
		});
		
		JPanel panelManager = new JPanel();
		add(panelManager, BorderLayout.SOUTH);
		panelManager.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel panelSave = new JPanel();
		panelManager.add(panelSave);
		
		JButton buttonSave = new JButton("Save");
		panelSave.add(buttonSave);
		buttonSave.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				onButtonSave();
			}
		});
		
		JPanel panelCancel = new JPanel();
		panelManager.add(panelCancel);
		
		JButton buttonCancel = new JButton("Cancel");
		panelCancel.add(buttonCancel);
		buttonCancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				onButtonCancel();
			}
			
		});
	}

	private JTextField fieldName;
	private Commodity value;
	
	
	public void setCommodity(Integer commodityId) throws ValidatorException{
		try{
			this.value=this.finder.findById(commodityId);
			this.fieldName.setText(this.value.getName());
		}catch(StoreException ex){
			throw new ValidatorException(ex);
		}
	}
	

	private void onButtonSave(){
		if(this.value!=null){
			// update
			value.setName(StringUtils.trim(this.fieldName.getText()));
			try {
				this.repository.update(value);
				this.closeModal(ModalResultListener.Result.Ok);
			} catch (StoreException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(),"Save Error", JOptionPane.ERROR_MESSAGE);
			}
		}else{
			// insert
			Commodity value=new Commodity();
			value.setName(StringUtils.trim(this.fieldName.getText()));
			try {
				this.repository.insert(value);
				this.closeModal(ModalResultListener.Result.Ok);
			} catch (StoreException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(),"Save Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void onButtonCancel(){
		this.closeModal(ModalResultListener.Result.Cancel);
	}
}
