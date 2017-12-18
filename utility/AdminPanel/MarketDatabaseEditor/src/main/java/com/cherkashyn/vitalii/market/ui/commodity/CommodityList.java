package com.cherkashyn.vitalii.market.ui.commodity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.market.datasource.FilterElement;
import com.cherkashyn.vitalii.market.datasource.FilterOperation;
import com.cherkashyn.vitalii.market.datasource.commodity.CommodityFinderNavigation;
import com.cherkashyn.vitalii.market.datasource.commodity.CommodityRepository;
import com.cherkashyn.vitalii.market.domain.Commodity;
import com.cherkashyn.vitalii.market.exception.StoreException;
import com.cherkashyn.vitalii.market.ui.commodity.editor.CommodityEditor;
import com.cherkashyn.vitalii.market.ui.common_elements.NavigatorTable;
import com.cherkashyn.vitalii.market.ui.common_elements.RecordSelectionListener;
import com.cherkashyn.vitalii.market.ui.exception.ValidatorException;
import com.cherkashyn.vitalii.swing_utility.ModalPanel;
import com.cherkashyn.vitalii.swing_utility.ModalResultListener;
import com.cherkashyn.vitalii.swing_utility.UIUtils;

/**
 * list of points for edit
 */
@Component
@Scope("prototype")
public class CommodityList extends ModalPanel implements ModalResultListener, RecordSelectionListener{
	public static final int WIDTH=500;
	public static final int HEIGHT=700;
	
	@Autowired
	ApplicationContext context;

	private static final long serialVersionUID = 1L;
	
	private NavigatorTable table;

	@Autowired(required=true)
	CommodityNavigator navigator;

	@Autowired
	CommodityRepository commodityRepository;

	@Autowired
	CommodityFinderNavigation commodityFinder;

	/** panel with visual components for add/remote  */
	private JPanel panelManager=null;
	
	/** behavior for DblClick/Enter on selected Commodity */
	private boolean modeSelector=false;

	public CommodityList() {
	}
	
	/** set state of this object:
	 * <ul>
	 * 	<li><b>true</b> - selector mode - DblClick/Enter - select commodity  </li>
	 * 	<li><b>false</b> - selector mode - DblClick/Enter - edit commodity </li>
	 * </ul>
	 *  */
	public void setModelSelector(boolean mode){
		this.modeSelector=mode;
	}
	
	@PostConstruct
	public void init(){
		this.table=new NavigatorTable(25, this.navigator);
		this.table.addNotifyListeners(this);
		this.navigator.setFilter(null);
		
		this.setLayout(new BorderLayout());
		JScrollPane pane=new JScrollPane();
		pane.setViewportView(this.table);
		this.add(pane, BorderLayout.CENTER);

		JPanel panelFilter=new JPanel(new FlowLayout(FlowLayout.CENTER));
		this.add(panelFilter, BorderLayout.NORTH);
		final JTextField filterField=new JTextField(20);
		panelFilter.add(filterField);
		filterField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent event) {
				if(event.getKeyChar()=='\n'){
					onFilter(filterField);
				}
			}
		});
		
		
		
		panelManager=new JPanel();
		this.add(panelManager, BorderLayout.SOUTH);
		panelManager.setLayout(new GridLayout(1, 2));

		
		JPanel panelButtonAdd=new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelManager.add(panelButtonAdd);
		
		JButton buttonAdd=new JButton("Add");
		panelButtonAdd.add(buttonAdd);
		buttonAdd.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onButtonAdd();
			}
		});

		JPanel panelButtonDel=new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelManager.add(panelButtonDel);
		JButton buttonDel=new JButton("Del");
		panelButtonDel.add(buttonDel);
		buttonDel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onButtonDel();
			}
		});
		
	}
	
	
	private void onFilter(JTextField textField){
		String filterText=StringUtils.trimToNull(textField.getText());
		if(filterText!=null){
			textField.setBackground(Color.RED);
			this.navigator.setFilter(new FilterElement[]{new FilterElement("name", FilterOperation.LIKE , "%"+filterText+"%")});
			this.table.refresh();
		}else{
			textField.setBackground(Color.WHITE);
			this.navigator.setFilter(null);
			this.table.refresh();
		}
	}
	

	/** 
	 * show/hide panel with add/delete buttons
	 * @param trigger
	 */
	public void setPanelMangerVisible(boolean trigger){
		this.panelManager.setVisible(trigger);
	}
	
	private Integer getCommodityIdFromSelection(String[] elements) {
		return Integer.parseInt(elements[0]);
	}
	
	private String getCommodityNameFromSelection(String[] elements){
		return elements[1];
	}
	
	@Override
	public void childWindowModalResult(Object value) {
		// System.out.println("Notify Point list about result: "+value);
		if(value instanceof ModalResultListener.Result){
			if(ModalResultListener.Result.Ok.equals( ((ModalResultListener.Result)value) ) ){
				refreshList();
			}
		}else{
		}
	}
	
	
	private void refreshList(){
		this.table.refresh(); 
	}

	
	@Override
	public void selectRecord(String[] elements) {
		if(!this.modeSelector){
			CommodityEditor editor=context.getBean(CommodityEditor.class);
			try {
				editor.setCommodity(this.getCommodityIdFromSelection(elements));
				UIUtils.showModal(this, editor);
			} catch (ValidatorException e) {
				JOptionPane.showMessageDialog(this, e.getCause().getMessage(),  "ValidationException", JOptionPane.WARNING_MESSAGE);
			}
		}else{
			Integer selectedCommodity=this.getCommodityIdFromSelection(elements);
			if(selectedCommodity!=null){
				this.closeModal(selectedCommodity);
			}
		}
	}

	private void onButtonAdd(){
		UIUtils.showModal(this, context.getBean(CommodityEditor.class));
	}
	
	private void onButtonDel(){
		if(JOptionPane.showConfirmDialog(this, "Are you sure to remove record: "+getCommodityNameFromSelection(this.table.getSelectedRow()), " record will be remove" , JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)==JOptionPane.YES_OPTION){
			Commodity valueForRemove;
			try {
				valueForRemove = this.commodityFinder.findById(this.getCommodityIdFromSelection(this.table.getSelectedRow()));
				if(valueForRemove==null){
					JOptionPane.showMessageDialog(this, "value was not found");
					return;
				}else{
					this.commodityRepository.remove(valueForRemove);
					this.refreshList();
				}
			} catch (StoreException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Store Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	
}
