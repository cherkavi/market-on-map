package com.cherkashyn.vitalii.market.ui.point;

import javax.annotation.PostConstruct;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.market.datasource.FilterElement;
import com.cherkashyn.vitalii.market.datasource.FilterOperation;
import com.cherkashyn.vitalii.market.datasource.points.PointFinderNavigation;
import com.cherkashyn.vitalii.market.datasource.points.PointRepository;
import com.cherkashyn.vitalii.market.domain.Point;
import com.cherkashyn.vitalii.market.exception.StoreException;
import com.cherkashyn.vitalii.market.ui.common_elements.NavigatorTable;
import com.cherkashyn.vitalii.market.ui.common_elements.RecordSelectionListener;
import com.cherkashyn.vitalii.market.ui.exception.ValidatorException;
import com.cherkashyn.vitalii.market.ui.point.editor.PointEditor;
import com.cherkashyn.vitalii.swing_utility.ModalPanel;
import com.cherkashyn.vitalii.swing_utility.ModalResultListener;
import com.cherkashyn.vitalii.swing_utility.UIUtils;

/**
 * list of points for edit
 */
@Component
@Scope("prototype")
public class PointList extends ModalPanel implements ModalResultListener{
	public static final int WIDTH=500;
	public static final int HEIGHT=400;
	
	@Autowired
	ApplicationContext context;

	private static final long serialVersionUID = 1L;
	
	private NavigatorTable table;
	
	@Autowired(required=true)
	private PointNavigator navigator;

	@Autowired
	PointRepository pointRepository;

	@Autowired
	PointFinderNavigation pointFinder;

	private JTextField filterText;
	
	public PointList() {
	}
	
	
	@PostConstruct
	public void init() {
		setForeground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
		this.setBorder(BorderFactory.createTitledBorder("Point editor"));
		
		JPanel panelFilter = new JPanel();
		panelFilter.setForeground(Color.WHITE);
		add(panelFilter, BorderLayout.NORTH);
		
		panelFilter.setLayout(new FlowLayout(FlowLayout.CENTER));
		filterText=new JTextField(10);
		panelFilter.add(filterText);
		filterText.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar()=='\n'){
					onFilter();
				}
			}
		});
		
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		
		JButton buttonAddNew = new JButton("Add new");
		panel_2.add(buttonAddNew);
		buttonAddNew.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				PointList.this.onButtonInsert();
			}
		});
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		
		JButton buttonRemove = new JButton("Remove");
		panel_3.add(buttonRemove);
		buttonRemove.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onButtonRemove();
			}
		});
		
		this.table=new NavigatorTable(10, this.navigator);
		add(this.table, BorderLayout.CENTER);
		this.table.addNotifyListeners(new RecordSelectionListener() {
			@Override
			public void selectRecord(String[] elements) {
				PointList.this.updateExisting(getPointIdFromSelection(elements));
			}
		});
	}
	
	private Integer getPointIdFromSelection(String[] elements) {
		return Integer.parseInt(elements[0]);
	}

	
	private void onButtonInsert(){
		PointEditor editor=context.getBean(PointEditor.class);
		UIUtils.showModal(this, editor);
	}
	
	private void onButtonRemove(){
		Point point;
		String[] selection=this.table.getSelectedRow();
		if(selection==null){
			JOptionPane.showMessageDialog(this, "Select the record");
			return;
		}
		try {
			point = this.pointFinder.findById(this.getPointIdFromSelection(selection));
		} catch (StoreException e) {
			JOptionPane.showMessageDialog(this, "can't find selected point");
			return;
		}
		if(point!=null){
			if(JOptionPane.showConfirmDialog(this, "Are you sure to remove selected point ?","Remove Point:"+point.getId(), JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
				try {
					this.pointRepository.remove(point);
				} catch (StoreException e) {
					JOptionPane.showMessageDialog(this, "can't remove point: "+point.getId());
					return;
				}
				this.refreshList();
			}
		}
	}
	
	private void updateExisting(Integer id){
		PointEditor editor=context.getBean(PointEditor.class);
		try{
			editor.setPoint(id);
			UIUtils.showModal(this, editor);
		}catch(ValidatorException ex){
			JOptionPane.showMessageDialog(this, "");
		}
	}

	
	
	private void refreshList(){
		this.table.refresh(); 
	}

	@Override
	public void childWindowModalResult(Object value) {
		if(value instanceof ModalResultListener.Result){
			if(ModalResultListener.Result.Ok.equals( ((ModalResultListener.Result)value) ) ){
				refreshList();
			}
			return;
		}
	}
	
	/** color FILTER SET  */
	private final static Color FILTER_SET_COLOR=Color.RED;
	/** color FILTER UNSET */
	private final static Color FILTER_CLEAR_COLOR=Color.WHITE;
	
	private void onFilter(){
		String value=StringUtils.trimToNull(this.filterText.getText());
		FilterElement[] filters=null;
		if(value==null){
			this.filterText.setBackground(FILTER_CLEAR_COLOR);
			filters=null;
		}else{
			this.filterText.setBackground(FILTER_SET_COLOR);
			filters=new FilterElement[]{new FilterElement("pointnum", FilterOperation.LIKE, value)};
		}
		
		try {
			this.navigator.setFilter(filters);
			this.refreshList();
		} catch (StoreException e) {
			JOptionPane.showMessageDialog(this, "can't set Filter: "+e.getMessage());
		}
	}
}
