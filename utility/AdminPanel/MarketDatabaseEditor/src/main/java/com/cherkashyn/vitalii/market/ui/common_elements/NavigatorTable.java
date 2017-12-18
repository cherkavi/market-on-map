package com.cherkashyn.vitalii.market.ui.common_elements;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang3.ObjectUtils;

import com.cherkashyn.vitalii.market.exception.StoreException;

public class NavigatorTable extends JPanel{

	private static final long serialVersionUID = 1L;

	private JTable table;
	private DefaultTableModel model;
	private JButton buttonMoveLeft;
	private JButton buttonMoveRight; 
	private RecordsNavigator navigator;
	private int recordPerPage;
	private int currentCursor=0;
	private Set<RecordSelectionListener> selectListener=new HashSet<RecordSelectionListener>();
	
	public NavigatorTable(int recordPerPage, RecordsNavigator navigator){
		if(navigator==null){
			throw new IllegalArgumentException("check navigator, it is NULL ");
		}
		if(recordPerPage<=0){
			throw new IllegalArgumentException("check record size per page ");
		}
		this.recordPerPage=recordPerPage;
		this.navigator=navigator;
		this.setLayout(new BorderLayout());
		
		this.add(createPanelNavigator(), BorderLayout.NORTH);

		this.add(createPanelData(this.navigator, this.recordPerPage), BorderLayout.CENTER);
		
		this.showRecords(this.currentCursor);
		
		// add listeners for event
		this.table.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent event) {
				if(event.getClickCount()>=2){
					JTable eventTable=(JTable)event.getSource();
					int selectedRow=eventTable.getSelectedRow();
					List<String> row=new ArrayList<String>(eventTable.getModel().getColumnCount());
					for(int index=0;index<eventTable.getModel().getColumnCount();index++){
						row.add(String.valueOf(eventTable.getModel().getValueAt(selectedRow, index)));
					}
					notifyListeners(row);
				}
			}
		});
		this.table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if(event.getKeyCode()=='\n'){
					JTable eventTable=(JTable)event.getSource();
					int selectedRow=eventTable.getSelectedRow();
					if(selectedRow>=0){
						List<String> row=new ArrayList<String>(eventTable.getModel().getColumnCount());
						for(int index=0;index<eventTable.getModel().getColumnCount();index++){
							row.add(String.valueOf(eventTable.getModel().getValueAt(selectedRow, index)));
						}
						notifyListeners(row);
					}
				}
			}
		});
	}

	public void addNotifyListeners(RecordSelectionListener listener){
		this.selectListener.add(listener);
	}
	
	private void notifyListeners(List<String> row){
		String[] valueForNotification=row.toArray(new String[row.size()]);
		for(RecordSelectionListener listener:this.selectListener){
			listener.selectRecord(valueForNotification);
		}
	}
	
	private Component createPanelData(RecordsNavigator navigator, int recordsPerPage) {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		table = new JTable(getTableModel(navigator, recordsPerPage)){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// table.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(20);
		// table.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(120);
		// table.setPreferredScrollableViewportSize(new Dimension(400,300));
		scrollPane.setViewportView(table);
		return scrollPane;
	}

	private JPanel createPanelNavigator(){
		JPanel panel_5 = new JPanel();
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JLabel label_show_range = new JLabel("");
		panel_5.add(label_show_range);
		
		buttonMoveLeft = new JButton("<<<");
		panel_5.add(buttonMoveLeft, BorderLayout.WEST);
		buttonMoveLeft.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				NavigatorTable.this.showRecords(NavigatorTable.this.currentCursor-NavigatorTable.this.recordPerPage);
			}
		});
		
		buttonMoveRight = new JButton(">>>");
		buttonMoveRight.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				NavigatorTable.this.showRecords(NavigatorTable.this.currentCursor+NavigatorTable.this.recordPerPage);
			}
		});
		panel_5.add(buttonMoveRight, BorderLayout.EAST);

		return panel_5;
	}
	
	private AbstractTableModel getTableModel(RecordsNavigator navigator, int recordsPerPage){
		model=new DefaultTableModel();
		for(String eachColumn:navigator.getColumns()){
			model.addColumn(eachColumn);
		}
		
		model.fireTableStructureChanged();
		
		this.buttonMoveLeft.setEnabled(false);
		this.buttonMoveRight.setEnabled(false);
		return model;
	}
	
	public void refresh(){
		this.showRecords(this.currentCursor);
	}
	
	private void showRecords(int begin){
		this.currentCursor=begin;
		while(this.model.getRowCount()>0){
			this.model.removeRow(0);
		}
		List<String[]> records;
		try {
			records = navigator.getRecords(this.currentCursor, this.recordPerPage);
		} catch (StoreException e) {
			showError("can't get records count "+e.getMessage());
			return;
		}
		for(String[] eachRecord:records){
			this.model.addRow(eachRecord);
		}
		this.model.fireTableDataChanged();
		
		if(this.currentCursor>0){
			this.buttonMoveLeft.setEnabled(true);
		}else{
			this.buttonMoveLeft.setEnabled(false);
		}
		
		try {
			if(  (this.currentCursor+this.recordPerPage)<this.navigator.getRecordSize()){
				this.buttonMoveRight.setEnabled(true);
			}else{
				this.buttonMoveRight.setEnabled(false);
			}
		} catch (StoreException e) {
			showError("can't display records: "+e.getMessage());
			return;
		}
	}

	private void showError(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
	
	public String[] getSelectedRow(){
		String[] returnValue=new String[this.table.getModel().getColumnCount()];
		int selectedRow=this.table.getSelectedRow();
		if(selectedRow<0){
			return null;
		}
		for(int index=0;index<this.table.getModel().getColumnCount(); index++){
			returnValue[index]=ObjectUtils.toString(this.table.getModel().getValueAt(selectedRow, index));
		}
		return returnValue;
	}
}
