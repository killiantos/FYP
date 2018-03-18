package piconode.visual;

import java.awt.Color;
import java.awt.Graphics2D;


/**
 * @author Thomas Darde
 *
 */

/**
 * SuperClass for NNNode and NNArc
 */
public abstract class NNShape {

	boolean selected;
	boolean inverseTempSelection;
	String name;
	int id;
	Color color;
	
	abstract void paint(Graphics2D g2);
	
	abstract String toXML();
	
	public boolean isSelected() {
		
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void inverseSelectionState() {
		selected=!selected;
		
	}

	public boolean isInverseTempSelection() {
		return inverseTempSelection;
	}

	public void setInverseTempSelection(boolean shiftSelected) {
		this.inverseTempSelection = shiftSelected;
	}	
}
