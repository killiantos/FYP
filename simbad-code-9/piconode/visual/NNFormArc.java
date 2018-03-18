package piconode.visual;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class NNFormArc extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private NNArc arc;
	JTextField weightTF,minimumTF,maximumTF;
	
	JLabel title;
	private NNCanvas canvas;
	NNColor colorNNColor;
	JButton _applyButton;
	JCheckBox weightCh,minimumCh,maximumCh,colorCh;

	NNFormArc(NNCanvas canvas_arg){
		super();
		canvas=canvas_arg;
		setLayout(new GridBagLayout());
		GridBagConstraints c= new GridBagConstraints();
		c.fill=GridBagConstraints.BOTH;
		setBackground(NNFrame._bgcolor);
		
		_applyButton=new JButton("Apply");
		_applyButton.addActionListener(this);
		_applyButton.setBackground(NNFrame._bgcolor);
		_applyButton.setEnabled(false);

		colorNNColor=new NNColor();
		weightTF=new JTextField(10);
		minimumTF=new JTextField(10);
		maximumTF=new JTextField(10);
		
		weightCh=new JCheckBox();
		weightCh.setBackground(NNFrame._bgcolor);
		weightCh.setToolTipText("If checked, modification will affect all nodes of the selection");
		
		minimumCh=new JCheckBox();
		minimumCh.setBackground(NNFrame._bgcolor);
		minimumCh.setToolTipText("If checked, modification will affect all nodes of the selection");
		
		maximumCh=new JCheckBox();
		maximumCh.setBackground(NNFrame._bgcolor);
		maximumCh.setToolTipText("If checked, modification will affect all nodes of the selection");
		
		colorCh=new JCheckBox();
		colorCh.setBackground(NNFrame._bgcolor);
		colorCh.setToolTipText("If checked, modification will affect all nodes of the selection");
		
		weightCh.setEnabled(false);
		minimumCh.setEnabled(false);
		maximumCh.setEnabled(false);
		colorCh.setEnabled(false);
		
		weightTF.setEnabled(false);
		minimumTF.setEnabled(false);
		maximumTF.setEnabled(false);
		
		title=new JLabel("<HTML><h2>Arc</h2></HTML>");
		
		c.gridx=0;
		c.gridy=0;
		c.insets=new Insets(0,5,5,5);
		
		c.gridwidth=3;
		add(title,c);
		
c.gridwidth=1;
		
		c.gridy++;
		c.gridx=0;
		add(new JLabel("Weight : ")  ,c);
		c.gridx=1;
		add(weightTF,c);
		c.gridx=2;
		add(weightCh,c);
	
		c.gridy++;
		c.gridx=0;
		add(new JLabel("Minimum : ")  ,c);
		c.gridx=1;
		add(minimumTF,c);
		c.gridx=2;
		add(minimumCh,c);
		
		c.gridy++;
		c.gridx=0;
		add(new JLabel("Maximum : ")  ,c);
		c.gridx=1;
		add(maximumTF,c);
		c.gridx=2;
		add(maximumCh,c);

		
		c.gridy++;
		c.gridx=0;
		add(new JLabel("Color : ")  ,c);
		c.gridx=1;
		add(colorNNColor,c);
		c.gridx=2;
		add(colorCh,c);
		
		
		c.gridy++;
		c.gridx=1;
		add(_applyButton,c);
	}
	
	void setArc(NNArc arc_arg){
		
		
		arc=arc_arg;
		title.setText("<HTML><h2>Arc : "+arc.getNodeFrom().getName()+" -> "+arc.getNodeTo().getName()+"</h2></HTML>");
		colorNNColor.set_color(arc.getColor());
		weightTF.setText(arc.getWeight()+"");
		minimumTF.setText(arc.getMinimum()+"");
		maximumTF.setText(arc.getMaximum()+"");
		_applyButton.setEnabled(true);
	}
	
	
	
	void update(){
		
		//Si pas de neurone, on desactive apply et nom
		
		if(canvas.selectionNNShape.noArc())
		{
			
			unsetArc();
			return;
		}
		
		
		//Si un neurone on active nom et on copie les valeurs de champs
		else if (canvas.selectionNNShape.arcAlone() !=null){
			weightTF.setEnabled(true);
			minimumTF.setEnabled(true);
			maximumTF.setEnabled(true);
			colorNNColor.setEnabled(true);
			
			
			arc=canvas.selectionNNShape.arcAlone();
			title.setText("<HTML><h2>Arc : "+arc.getNodeFrom().getName()+" -> "+arc.getNodeTo().getName()+"</h2></HTML>");
			colorNNColor.set_color(arc.getColor());
			weightTF.setText(arc.getWeight()+"");
			minimumTF.setText(arc.getMinimum()+"");
			maximumTF.setText(arc.getMaximum()+"");
			colorCh.setEnabled(false);
			weightCh.setEnabled(false);
			minimumCh.setEnabled(false);
			maximumCh.setEnabled(false);
			
			_applyButton.setEnabled(true);
		
		}
		
		
		else {
			title.setText("<HTML><h2>Arcs : Multiple</h2></HTML>");
			
			weightTF.setEnabled(true);
			minimumTF.setEnabled(true);
			maximumTF.setEnabled(true);
			colorNNColor.setEnabled(true);
			
	
			colorCh.setSelected(false);
			weightCh.setSelected(false);
			minimumCh.setSelected(false);
			maximumCh.setSelected(false);

			colorCh.setEnabled(true);
			weightCh.setEnabled(true);
			minimumCh.setEnabled(true);
			maximumCh.setEnabled(true);

			
			_applyButton.setEnabled(true);
			
			
		}
	
	}
	
	
	
	
	
	
	void unsetArc(){
		arc=null;
		title.setText("<HTML><h2>Arcs : Empty</h2></HTML>");

		
		weightTF.setText("");
		minimumTF.setText("");
		maximumTF.setText("");
		
		weightTF.setEnabled(false);
		minimumTF.setEnabled(false);
		maximumTF.setEnabled(false);
		
		colorCh.setEnabled(false);
		weightCh.setEnabled(false);
		minimumCh.setEnabled(false);
		maximumCh.setEnabled(false);
		
		
		_applyButton.setEnabled(false);
		
	}
	public void actionPerformed(ActionEvent e) {
		if(canvas.selectionNNShape.arcAlone() !=null){

		arc.setWeight( Double.parseDouble(weightTF.getText())  );
		arc.setMinimum(Double.parseDouble(minimumTF.getText()));
		arc.setMaximum(Double.parseDouble(maximumTF.getText()));

		arc.setColor(colorNNColor.get_color());
		}
		else {
			Enumeration arcEnumeration=canvas.selectionNNShape.getArcs();
			
			while(arcEnumeration.hasMoreElements())
			{
				NNArc nextArc =(NNArc) arcEnumeration.nextElement();
				
				if(colorCh.isEnabled())
					nextArc.setColor(colorNNColor.get_color());

				if(minimumCh.isSelected())
					nextArc.setWeight( Double.parseDouble(minimumTF.getText())  );
				if(weightCh.isSelected())
					nextArc.setWeight( Double.parseDouble(weightTF.getText())  );
				if(maximumCh.isSelected())
					nextArc.setWeight(Double.parseDouble(maximumTF.getText())  );

				
		
			}
			
			colorCh.setSelected(false);
			weightCh.setSelected(false);
			minimumCh.setSelected(false);
			maximumCh.setSelected(false);

		}
		canvas.repaint();
	}


}
