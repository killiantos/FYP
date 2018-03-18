package piconode.visual;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NNFormNode extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	private NNNode node;
	JTextField nameTF,valeurTF;
	JComboBox typeCB,functionCB;
	JLabel title;
	NNColor colorNNColor;
	JButton _applyButton;
	JCheckBox typeCh,functionCh,colorCh;
	
	private NNCanvas canvas;
	
	NNFormNode(NNCanvas canvas_arg){
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
		

		nameTF=new JTextField(10);
		valeurTF=new JTextField(10);
		nameTF.setEnabled(false);
		
		
		typeCB=new JComboBox(NNNode.types);
		functionCB=new JComboBox(NNNode.functions);
		typeCB.setBackground(NNFrame._bgcolor);
		typeCB.setEnabled(false);
		functionCB.setBackground(NNFrame._bgcolor);
		functionCB.setEnabled(false);
		colorNNColor=new NNColor();
		
		
		functionCh=new JCheckBox();
		functionCh.setBackground(NNFrame._bgcolor);
		functionCh.setToolTipText("If checked, modification will affect all nodes of the selection");
		typeCh=new JCheckBox();
		typeCh.setBackground(NNFrame._bgcolor);
		typeCh.setToolTipText("If checked, modification will affect all nodes of the selection");
		colorCh=new JCheckBox();
		colorCh.setBackground(NNFrame._bgcolor);
		colorCh.setToolTipText("If checked, modification will affect all nodes of the selection");
		functionCh.setEnabled(false);
		colorCh.setEnabled(false);
		typeCh.setEnabled(false);
	
		c.insets=new Insets(0,5,5,5);
		
		c.gridx=0;
		c.gridy=0;
		
		c.gridwidth=3;
		title=new JLabel("<HTML><h2>Node</h2></HTML>");
		add(title,c);
		
		c.gridwidth=1;
		
		c.gridy++;
		
		add(new JLabel("Name : ")  ,c);
		c.gridx=1;
		add(nameTF,c);
		
		
		c.gridy++;
		c.gridx=0;
		add(new JLabel("Function : ")  ,c);
		c.gridx=1;
		add(functionCB,c);
		c.gridx=2;
		add(functionCh,c);
	
		c.gridy++;
		c.gridx=0;
		add(new JLabel("Type : ")  ,c);
		c.gridx=1;
		add(typeCB,c);
		c.gridx=2;
		add(typeCh,c);
		
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
	
	void update(){
	
		//Si pas de neurone, on desactive apply et nom
		
		if(canvas.selectionNNShape.noNode())
		{
			unsetNode();
			return;
		}
		
		
		//Si un neurone on active nom et on copie les valeurs de champs
		else if (canvas.selectionNNShape.nodeAlone() !=null){
			_applyButton.setEnabled(true);
			node=canvas.selectionNNShape.firstNode();
			title.setText("<HTML><h2>Node : "+node.getName()+"</h2></HTML>");
			
			colorNNColor.set_color(node.getColor());
			functionCB.setSelectedItem(node.getFunction());
			typeCB.setSelectedItem(node.getType());
			nameTF.setText(node.getName()+"");
			functionCh.setEnabled(false);
			colorCh.setEnabled(false);
			typeCh.setEnabled(false);
			nameTF.setEnabled(true);
			functionCB.setEnabled(true);
			typeCB.setEnabled(true);
			_applyButton.setEnabled(true);
		}
		
		
		//Si plusieurs neurones on desactive nom
		else {
			title.setText("<HTML><h2>Nodes : Multiple</h2></HTML>");
			nameTF.setText("");
			nameTF.setEnabled(false);
			functionCh.setEnabled(true);
			colorCh.setEnabled(true);
			typeCh.setEnabled(true);
			
			functionCB.setEnabled(true);
			typeCB.setEnabled(true);
			_applyButton.setEnabled(true);
				
		}
	
	}
	
	
	void unsetNode(){
		
		node=null;
		title.setText("<HTML><h2>Node: Empty</h2></HTML>");
		functionCB.setEnabled(false);
		typeCB.setEnabled(false);
		nameTF.setText("");

		functionCh.setEnabled(false);
		colorCh.setEnabled(false);
		typeCh.setEnabled(false);
		
		_applyButton.setEnabled(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(canvas.selectionNNShape.nodeAlone() !=null){
			node.setName( nameTF.getText() );
			title.setText("<HTML><h2>Node : "+node.getName()+"</h2></HTML>");
			node.setColor(colorNNColor.get_color());
			node.setFunction(NNNode.functions[functionCB.getSelectedIndex()]);
			node.setType(NNNode.types[typeCB.getSelectedIndex()]);
		}
		
		else {
			Enumeration nodeEnumeration=canvas.selectionNNShape.getNodes();
			
			while(nodeEnumeration.hasMoreElements())
			{
				NNNode nextNode =(NNNode) nodeEnumeration.nextElement();
				
				if(colorCh.isSelected())
					nextNode.setColor(colorNNColor.get_color());
				if(functionCh.isSelected())
					nextNode.setFunction(NNNode.functions[functionCB.getSelectedIndex()]);
				
				if(typeCh.isSelected())
					nextNode.setType(NNNode.types[typeCB.getSelectedIndex()]);
			}
			
			
			colorCh.setSelected(false);
			functionCh.setSelected(false);
			typeCh.setSelected(false);
		
		}
			
		canvas.repaint();
				
	}


}
