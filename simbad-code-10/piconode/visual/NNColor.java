package piconode.visual;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JColorChooser;
import javax.swing.JPanel;


class NNColor extends JPanel implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static int largeur=80;
	private final static int hauteur=30;
	Color _color;
	
	NNColor(){
	setSize(35,35);
	_color=Color.WHITE;
	addMouseListener(this);
	
	}
	
	public void mouseClicked(MouseEvent e) {
		_color=JColorChooser.showDialog(this,"Choose a Color",_color);
		repaint();
	}

	public void paint(Graphics g){
		g.clearRect(0,0,	largeur,hauteur);
	 	g.setColor(_color);
		g.fillRect(0,0,	largeur,hauteur);
		
	}
	
	public Color get_color() {
		return _color;
	}
	public void set_color(Color _color) {
		this._color = _color;
	}
	
	public void mousePressed(MouseEvent e) {
		
	}
	public void mouseReleased(MouseEvent e) {
		
	}
	public void mouseEntered(MouseEvent e) {
		
	}
	public void mouseExited(MouseEvent e) {
		
	}
	
	
	
}