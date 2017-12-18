package com.cherkashyn.vitalii.market.ui.point.editor;

import javax.annotation.PostConstruct;
import javax.swing.JPanel;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.market.datasource.point2commodity.Point2CommodityFinder;
import com.cherkashyn.vitalii.market.datasource.point2commodity.Point2CommodityRepository;
import com.cherkashyn.vitalii.market.datasource.points.PointFinderNavigation;
import com.cherkashyn.vitalii.market.datasource.points.PointRepository;
import com.cherkashyn.vitalii.market.domain.Commodity;
import com.cherkashyn.vitalii.market.domain.Point;
import com.cherkashyn.vitalii.market.domain.Point2Commodity;
import com.cherkashyn.vitalii.market.exception.StoreException;
import com.cherkashyn.vitalii.market.ui.commodity.CommodityList;
import com.cherkashyn.vitalii.market.ui.exception.UIException;
import com.cherkashyn.vitalii.market.ui.exception.ValidatorException;
import com.cherkashyn.vitalii.market.ui.point.editor.map.ImagePointSelector;
import com.cherkashyn.vitalii.swing_utility.ModalPanel;
import com.cherkashyn.vitalii.swing_utility.ModalResultListener;
import com.cherkashyn.vitalii.swing_utility.UIUtils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@Component
@Scope("prototype")
public class PointEditor extends ModalPanel implements ModalResultListener{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH=600;
	public static final int HEIGHT=400;
	
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	Point2CommodityRepository repositoryPoint2Commodity;
	
	@Autowired
	Point2CommodityFinder finderPoint2Commodity;
	
	public PointEditor() {
	}

	@PostConstruct
	public void init(){
		setForeground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
		
		JPanel panelLabel = new JPanel();
		add(panelLabel, BorderLayout.NORTH);
		
		JPanel panelEditor = new JPanel();
		add(panelEditor, BorderLayout.CENTER);
		panelEditor.setForeground(Color.WHITE);
		panelEditor.setLayout(new BorderLayout(0, 0));
		
		JLabel lblCommodity = new JLabel("Commodity");
		panelEditor.add(lblCommodity);
		
		JLabel lblPointEditor = new JLabel(" Point editor");
		panelEditor.add(lblPointEditor, BorderLayout.NORTH);
		
		JPanel panel1 = new JPanel();
		panelEditor.add(panel1, BorderLayout.NORTH);
		panel1.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel panel11 = new JPanel();
		panel1.add(panel11);
		
		JLabel lblPointNumber = new JLabel("Point number");
		panel11.add(lblPointNumber);
		lblPointNumber.setForeground(Color.BLACK);
		
		textField_pointNumber = new JTextField(10);
		panel11.add(textField_pointNumber);
		textField_pointNumber.setForeground(Color.BLACK);
		textField_pointNumber.setColumns(10);
		
		JPanel panel12 = new JPanel();
		panel1.add(panel12);
		panel12.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel12.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton buttonMap = new JButton("Map");
		buttonMap.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onButtonMap();
			}
		});
		panel.add(buttonMap);
		
		JPanel panel_3 = new JPanel();
		panel12.add(panel_3);
		panel_3.setLayout(new GridLayout(2, 2, 0, 0));
		
		JLabel lblPosX = new JLabel("pos X");
		panel_3.add(lblPosX);
		lblPosX.setBackground(Color.WHITE);
		lblPosX.setForeground(Color.BLACK);
		
		textField_posX = new JTextField(10);
		panel_3.add(textField_posX);
		
		JLabel lblPosY = new JLabel("pos Y");
		panel_3.add(lblPosY);
		lblPosY.setForeground(Color.BLACK);
		
		textField_posY = new JTextField(10);
		panel_3.add(textField_posY);
		
		
		JPanel panel2 = new JPanel();
		panelEditor.add(panel2, BorderLayout.CENTER);
		panel2.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel panel21 = new JPanel();
		panel2.add(panel21);
		panel21.setLayout(new BorderLayout(0, 0));
		
		JLabel lblHtml = new JLabel("HTML");
		lblHtml.setHorizontalAlignment(SwingConstants.CENTER);
		panel21.add(lblHtml, BorderLayout.NORTH);
		lblHtml.setForeground(Color.BLACK);
		
		JScrollPane scrollPane = new JScrollPane();
		panel21.add(scrollPane);
		
		textArea_Html = new JTextArea();
		scrollPane.setViewportView(textArea_Html);
		
		JPanel panel22 = new JPanel();
		panel2.add(panel22);
		panel22.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel22.add(scrollPane_1, BorderLayout.CENTER);
		
		listModelCommodity=new DefaultListModel();
		listCommodity = new JList(listModelCommodity);
		listCommodity.setCellRenderer(new CommodityRenderer());
		listCommodity.setLayoutOrientation(JList.VERTICAL);
		listCommodity.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(listCommodity);
		
		JPanel panel221 = new JPanel();
		panel221.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel22.add(panel221, BorderLayout.NORTH);
		panel221.setLayout(new GridLayout(1, 2, 0, 0));
		
		JButton btnAdd = new JButton("Add");
		panel221.add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onButtonCommodityAdd();
			}
		});
		
		JButton btnRemove = new JButton("Del");
		panel221.add(btnRemove);
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				onButtonCommodityDelete();
			}
		});
		
		JPanel panelManager = new JPanel();
		add(panelManager, BorderLayout.SOUTH);
		panelManager.setLayout(new GridLayout(0, 2, 0, 0));
		panelManager.add(panel_1);
		
		JButton buttonSave = new JButton("Save");
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onButtonSave();
			}
		});
		panel_1.add(buttonSave);
		
		JPanel panel_2 = new JPanel();
		panelManager.add(panel_2);
		
		JButton buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onButtonCancel();
			}
		});
		buttonCancel.setForeground(Color.BLACK);
		panel_2.add(buttonCancel);
		
	}

	@Autowired
	PointRepository repositoryPoint;

	@Autowired
	PointFinderNavigation finderPoint;
	
	@Value("${path_to_image}")
	private String pathToImage;
	
	public void setImagePath(String pathToImage){
		this.pathToImage=pathToImage;
	}

	private final JPanel panel_1 = new JPanel();
	private JTextField textField_pointNumber;
	private JTextArea textArea_Html;
	private JList listCommodity;
	private DefaultListModel listModelCommodity;
	

	private Point point=null;
	private JTextField textField_posX;
	private JTextField textField_posY;
	
	public void setPoint(Point point){
		this.point=point;
		this.textField_pointNumber.setText(Integer.toString(point.getPointnum()));
		this.textField_posX.setText(Integer.toString(point.getPositionX()));
		this.textField_posY.setText(Integer.toString(point.getPositionY()));
		this.textArea_Html.setText(point.getHtml());
		this.refreshCommodity();
	}
	
	public void setPoint(Integer id) throws ValidatorException{
		try {
			this.point=this.finderPoint.findById(id);
		} catch (StoreException e) {
			throw new ValidatorException("can't find Point by Id: "+id);
		}
		this.setPoint(point);
	}	
	
	
	private Integer getIntegerFromTextField(JTextField field, String fieldName) throws ValidatorException{
		try{
			return Integer.parseInt(StringUtils.trim(field.getText()));
		}catch(RuntimeException re){
			throw new ValidatorException("check field:"+fieldName);
		}
	}
	
	
	private void fillFromUi(Point point) throws ValidatorException{
		if(point==null){
			return;
		}
		point.setPointnum(getIntegerFromTextField(this.textField_pointNumber, "pointNumber"));
		point.setPositionX(getIntegerFromTextField(this.textField_posX,"Position X"));
		point.setPositionY(getIntegerFromTextField(this.textField_posY, "Position Y"));
		point.setHtml(this.textArea_Html.getText());
	}
	
	private void onButtonSave(){
		if(point==null){
			// create
			Point point=new Point();
			try {
				this.fillFromUi(point);
				this.repositoryPoint.insert(point);
				this.closeModal(ModalResultListener.Result.Ok);
			} catch (StoreException ex) {
				JOptionPane.showMessageDialog(this, "insert Error:"+ex.getMessage());
				this.closeModal(ModalResultListener.Result.Cancel);
			} catch(ValidatorException ex){
				JOptionPane.showMessageDialog(this, "check fields:"+ex.getMessage());
				this.closeModal(ModalResultListener.Result.Cancel);
			}
		}else{
			// update;
			try {
				this.fillFromUi(this.point);
				this.repositoryPoint.update(point);
				this.closeModal(ModalResultListener.Result.Ok);
			} catch (StoreException ex) {
				JOptionPane.showMessageDialog(this, "update Error:"+ex.getMessage());
				this.closeModal(ModalResultListener.Result.Cancel);
			} catch(ValidatorException ex){
				JOptionPane.showMessageDialog(this, "check fields:"+ex.getMessage());
				this.closeModal(ModalResultListener.Result.Cancel);
			}
		}
	}
	
	private void onButtonCancel(){
		this.closeModal(ModalResultListener.Result.Cancel);
	}
	
	private void onButtonCommodityAdd(){
		CommodityList commodityList=context.getBean(CommodityList.class);
		commodityList.setPanelMangerVisible(true);
		commodityList.setModelSelector(true);
		UIUtils.showModal(this, commodityList, true);
	}
	
	private void onButtonCommodityDelete(){
		if(this.listCommodity.getSelectedValue()!=null){
			Commodity commodityForRemove=(Commodity)this.listCommodity.getSelectedValue();
			try {
				this.repositoryPoint2Commodity.remove(this.finderPoint2Commodity.find(point, commodityForRemove));
				this.refreshCommodity();
			} catch (StoreException e) {
				JOptionPane.showMessageDialog(this, "can't remove Commodity:"+e.getMessage(),"Remove Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	@Override
	public void childWindowModalResult(Object value) {
		if(value instanceof Integer){
			addCommodityToPoint((Integer)value);
			return;
		}
		if(value instanceof Dimension){
			Dimension dimension=(Dimension)value;
			this.textField_posX.setText(Integer.toString(dimension.width));
			this.textField_posY.setText(Integer.toString(dimension.height));
		}
	}
	
	private void addCommodityToPoint(Integer value) {
		Point2Commodity link=new Point2Commodity();
		link.setIdCommodity(value);
		link.setIdPoint(this.point.getId());
		try {
			this.repositoryPoint2Commodity.insert(link);
			this.refreshCommodity();
		} catch (StoreException e) {
			JOptionPane.showMessageDialog(this, e, "not saved", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void refreshCommodity(){
		this.listModelCommodity.removeAllElements();
		List<Commodity> commodities;
		try {
			commodities = new ArrayList<Commodity>(this.finderPoint2Commodity.getCommodity(this.point));
		} catch (StoreException e) {
			JOptionPane.showMessageDialog(this, "can't refresh list of commodity:"+e.getMessage()," Refresh Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		Collections.sort(commodities, new Comparator<Commodity>(){
			@Override
			public int compare(Commodity first, Commodity second) {
				return first.getId()-second.getId();
			}
		});
		for(Commodity eachCommodity:commodities){
			this.listModelCommodity.addElement(eachCommodity);
		}
	}
	
	private void onButtonMap(){
		try {
			UIUtils.showModal(this, new ImagePointSelector(this.pathToImage), true);
		} catch (UIException e) {
			JOptionPane.showMessageDialog(this, "Settings Error", "can't load image", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	private static class CommodityRenderer implements ListCellRenderer{
		private final static Color COLOR_FOCUS=new Color(135,206,250);
		private final static Color COLOR_SELECTED=new Color(176, 196, 222);
		private final static Color COLOR_DEFAULT=new Color(240,255,255);
		
		@Override
		public java.awt.Component getListCellRendererComponent(JList list, Object object, int index, boolean selected, boolean hasFocus) {
			JPanel panel=new JPanel();
			panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
			panel.setLayout(new FlowLayout(FlowLayout.LEFT));
			Commodity commodity=(Commodity)object;
			panel.add(new JLabel(commodity.getName()));
			if(hasFocus){
				panel.setBackground(COLOR_FOCUS);
			}else if(selected){
				panel.setBackground(COLOR_SELECTED);
			}else{
				panel.setBackground(COLOR_DEFAULT);
			}
			return panel;
		}
	}
	
}
