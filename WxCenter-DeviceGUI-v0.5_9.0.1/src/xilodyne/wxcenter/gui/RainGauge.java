package xilodyne.wxcenter.gui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import xilodyne.wxcenter.gui.components.JPanelRainGauge;

public class RainGauge extends JPanel {
	

	private static final long serialVersionUID = -5575239106976327281L;
	JLabel title, top, scale, base;
	JPanelRainGauge rainGaugePanel;

	public RainGauge() {
		setBackground(Color.DARK_GRAY);
		setBorder(null);
		setOpaque(true);

		setLayout(null);
		title = new JLabel ("\u00B7\u00B7\u00B7");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(10, 0, 75, 25);
		this.add(title);
		
		rainGaugePanel = new JPanelRainGauge();
		rainGaugePanel.setBorder(null);
		rainGaugePanel.setBounds(10, 21, 75, 260);
		rainGaugePanel.setOpaque(true);
		this.add(rainGaugePanel);
		
		scale = new JLabel("\u00B7\u00B7\u00B7");
		scale.setHorizontalAlignment(SwingConstants.CENTER);
		scale.setBounds(10, 286, 75, 14);
		this.add(scale);
		
		top = new JLabel("\u00B7\u00B7\u00B7");
		top.setHorizontalAlignment(SwingConstants.LEFT);
		top.setBounds(89, 24, 26, 14);
		add(top);
		
		base = new JLabel("\u00B7\u00B7\u00B7");
		base.setHorizontalAlignment(SwingConstants.LEFT);
		base.setBounds(95, 267, 20, 14);
		add(base);
		
		
	}
	
	public void setTitle(String value) {
		this.title.setText(value);
	}
	public void setBase(String value) {
		this.base.setText(value);
	}
	public void setTop(String value){
		this.top.setText(value);
	}
	public void setScale(String value) {
		this.scale.setText(value);
	}
	
	public void setRainMM(float value) {
		this.rainGaugePanel.setRainMM(value);
	}
	
}
