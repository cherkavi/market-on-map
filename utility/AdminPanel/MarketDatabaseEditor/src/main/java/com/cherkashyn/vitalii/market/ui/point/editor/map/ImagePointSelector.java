package com.cherkashyn.vitalii.market.ui.point.editor.map;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.commons.lang3.StringUtils;

import com.cherkashyn.vitalii.market.ui.exception.UIException;
import com.cherkashyn.vitalii.swing_utility.ModalPanel;

public class ImagePointSelector extends ModalPanel{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH=1024;
	public static final int HEIGHT=1024;
	
	private Image image;
	private static final String PATH_DEFAULT="/home/user/temp/Map_main.png";
	
	public ImagePointSelector() throws UIException{
		this(PATH_DEFAULT);
	}
	
	public ImagePointSelector(String pathToImage) throws UIException{
		image=getImage( (StringUtils.trimToNull(pathToImage)==null?PATH_DEFAULT:pathToImage) );
		this.setLayout(new GridLayout(1,1));
		JLabel label=new JLabel(new ImageIcon(image));
//				new JPanel(){
//			@Override
//			protected void paintComponent(Graphics g) {
//				super.paintComponent(g);
//				g.drawImage(image, 0, 0, null);
//			}
//		};
		this.add(new JScrollPane(label));
		
		label.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent event) {
				onEvent(new Dimension(event.getX(), event.getY()));
			}
		});
		this.validate();
	}
	
	private Image getImage(String pathToImage) throws UIException{
		try{
			return ImageIO.read(new File(pathToImage));
		}catch(IOException ex){
			throw new UIException(ex);
		}
	}
	
	private void onEvent(Dimension dimension){
		this.closeModal(dimension);
	}
	
	
	
}
