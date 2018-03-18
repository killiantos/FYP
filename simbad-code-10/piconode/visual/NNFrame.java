package piconode.visual;



import javax.swing.*;
import javax.swing.filechooser.FileFilter;






import java.awt.event.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import java.text.DateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;

/**
 * @author lri
 *
 */
public class NNFrame extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final Color _bgcolor=new Color (210,210,210);
	static final Color _selectionColor=new Color (210,0,0);
	static final Color _infoColor=new Color (0,210,0);
	static final Color _defaultColor=Color.BLACK;
	
	static final int MIDGRIDWIDTH=30;
	
	JMenuBar _menuBar;
	GridBagConstraints cg;
	JPanel _infoPanel;
	JPanel _commandPanel;
	JRadioButton selectRadioButton;
	JRadioButton nodeRadioButton;
	
	JRadioButton arcRadioButton;
	
	boolean picomenu;
	
	NNScrollPane _canvasScroll;
	NNCanvas _canvas;
	NNFormArc formArc;
	NNFormNode formNode;
	File file;
	boolean grid;
	
	/*
	 * Constructor
	 */
	
	public NNFrame(){
		super();
		System.err.println("Launching App...");
		setBounds(30,30,800,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		file=null;
		picomenu=false;
		grid=true;
		
		_setMenuBar();
		_setLayout();
		_setCommandPanel();
		_setNNCanvas();
		_setInfoPanel();
		
		setVisible(true);
		System.err.println("App is ready !");
	}
	
	/*
	 * Interface
	 */
	
	private void _setLayout() {
		System.err.println("\tSetting Layout");
		//Setting internal Layout
		getContentPane().setLayout(new GridBagLayout());
		cg = new GridBagConstraints();
		cg.fill = GridBagConstraints.BOTH;
	}
	
	void _setCommandPanel() {
		System.err.println("\tCreating Command Panel");
		
		cg.gridx=0;
		cg.gridy=0;
		cg.weightx=0;
		cg.weighty=0;
		cg.gridwidth=2;
		_commandPanel=new JPanel();
		_commandPanel.setBackground(_bgcolor);
		
		_commandPanel.setLayout(new FlowLayout());
		
		ButtonGroup group = new ButtonGroup();
		
		selectRadioButton=new JRadioButton("Select");
		selectRadioButton.setBackground(_bgcolor);
		selectRadioButton.setName("SelectButton");
		selectRadioButton.setToolTipText("Selection tool allows to adjust the network");
		selectRadioButton.addActionListener(this);
		group.add(selectRadioButton);
		_commandPanel.add(selectRadioButton);
		
		nodeRadioButton=new JRadioButton("Node");
		nodeRadioButton.setBackground(_bgcolor);
		nodeRadioButton.setName("NodeButton");
		nodeRadioButton.setSelected(true);
		nodeRadioButton.setToolTipText("Add nodes to your network");
		nodeRadioButton.addActionListener(this);
		group.add(nodeRadioButton);
		_commandPanel.add(nodeRadioButton);
		
		arcRadioButton=new JRadioButton("Arc");
		arcRadioButton.setBackground(_bgcolor);
		arcRadioButton.setName("ArcButton");
		arcRadioButton.setToolTipText("Add links to your network");

		arcRadioButton.addActionListener(this);
		group.add(arcRadioButton);
		_commandPanel.add(arcRadioButton);
		

		
		
		
		
		
		getContentPane().add(_commandPanel,cg);
		
	}
	
	void _setInfoPanel() {
		System.err.println("\tCreating InfoPanel");
		cg.gridx=1;
		cg.gridy=1;
		cg.gridwidth=1;
		cg.weightx=0;
		cg.weighty=0;
		
		_infoPanel=new JPanel();
		_infoPanel.setBackground(_bgcolor);
		//Setting Layout
		_infoPanel.setLayout(new GridBagLayout());
		GridBagConstraints  cg2 = new GridBagConstraints();
		
		cg2.fill = GridBagConstraints.HORIZONTAL;
		cg2.insets=new Insets(1,5,0,2);
		
		cg2.gridx=0;
		cg2.gridy=0;
		formArc=new NNFormArc(_canvas);
		_infoPanel.add(formArc,cg2);
		
		cg2.gridy++;
		formNode=new NNFormNode(_canvas);
		_infoPanel.add(formNode,cg2);
	
	
		
		
		getContentPane().add(_infoPanel,cg);
	}
	
	void _setNNCanvas() {
		System.err.println("\tCreating NNCanvas");
		cg.gridx=0;
		cg.gridy=1;
		cg.gridwidth=1;
		cg.weightx=1;
		cg.weighty=1;
		
		_canvas=new NNCanvas(this);
		_canvas.setPreferredSize(new Dimension(2000,2000));
		_canvas.setBackground(Color.white);
		_canvasScroll=new NNScrollPane(_canvas); 
		getContentPane().add(_canvasScroll,cg);

	}

	void _setMenuBar(){
		System.err.println("\tCreating MenuBar");
		
		JMenu menu;
		JMenuItem menuItem;
		JCheckBoxMenuItem cbMenuItem;
		
		//Create the menu bar.
		_menuBar = new JMenuBar();
		
		//Build the first menu.
		menu = new JMenu("File");
		menu.getAccessibleContext().setAccessibleDescription("File Operations Menu");
		_menuBar.add(menu);
		
		
		menuItem = new JMenuItem("New",KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.META_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Create a new File");
		menuItem.setName("New");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Open..",KeyEvent.VK_O);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.META_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Open a File");
		menuItem.setName("Open");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Save",KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.META_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Save actual File");
		menuItem.setName("Save");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Save As...");
		menuItem.getAccessibleContext().setAccessibleDescription("Save as this File");
		menuItem.setName("SaveAs");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		
		menu.addSeparator();
		
		menuItem = new JMenuItem("Quit",KeyEvent.VK_Q);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.META_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Quit this application");
		menuItem.setName("Quit");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		

		
		//Build the 2nd menu.
		menu = new JMenu("Edit");
		menu.getAccessibleContext().setAccessibleDescription("Edition Menu");
		_menuBar.add(menu);
		
		menuItem = new JMenuItem("Undo");
		menuItem.setEnabled(false);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.META_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Undo last action");
		menuItem.setName("Undo");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Redo");
		menuItem.setEnabled(false);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.META_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Redo last action");
		menuItem.setName("Redo");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menu.addSeparator();
		
		
		menuItem = new JMenuItem("Duplicate");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.META_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Duplicate Selection");
		menuItem.setName("Duplicate");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Delete");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Delete Selection");
		menuItem.setName("Delete");
		menuItem.addActionListener(this);
		menu.add(menuItem);


		
		//		Build the 3rd menu.
		menu = new JMenu("Tools");
		menu.getAccessibleContext().setAccessibleDescription("Tools Selection Menu");
		_menuBar.add(menu);
		
		menuItem = new JMenuItem("Selection");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Use the Selection Tool");
		menuItem.setName("SelectionTool");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Node");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Use the Node Tool");
		menuItem.setName("NodeTool");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Arcs");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Use the Arc Tool");
		menuItem.setName("ArcTool");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		
		
		//Build the 4th menu.
		menu = new JMenu("Presentation");
		menu.getAccessibleContext().setAccessibleDescription("Presentation Operations Menu");
		_menuBar.add(menu);
		
		cbMenuItem = new JCheckBoxMenuItem("Activate Grid");
		cbMenuItem.setName("GridMenuItem");
		cbMenuItem.setState(true);
		cbMenuItem.setMnemonic(KeyEvent.VK_G);
		cbMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		cbMenuItem.addActionListener(this);
		menu.add(cbMenuItem);

		menu.addSeparator();

		menuItem = new JMenuItem("Rearrange network");
		menuItem.getAccessibleContext().setAccessibleDescription("Rearrange the whole network");
		menuItem.setName("Rearrange");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Reset Weigth for selected arcs");
		menuItem.getAccessibleContext().setAccessibleDescription("Reset weight for arcs.");
		menuItem.setName("ResetWeigth");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		

		
		setJMenuBar(_menuBar);
	}
	
	
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() instanceof JMenuItem){
			JMenuItem src = (JMenuItem)e.getSource();
			if(src.getName().equals("New"))
			{
				System.out.println("Creating a File");
				prepareToOpenDocument();
				file=null;
				setNodeMode();
			}
			else if(src.getName().equals("Open"))
			{
				System.out.println("Opening a File");
				openNetwork();
			}
			else if(src.getName().equals("Save"))
			{
				System.out.println("Saving a File");
				saveNetwork(false);
			}
			else if(src.getName().equals("SaveAs"))
			{
				System.out.println("Saving as a File");
				saveNetwork(true);
			}

			else if(src.getName().equals("Quit"))
			{
				
				quitApp();
			}
			else if(src.getName().equals("ResetWeigth"))
			{
				
				System.out.println("Reseting...");
				_canvas.selectionNNShape.resetWeigthSelection(_canvas.listNNShape);
				_canvas.repaint();
			}
			else if(src.getName().equals("Delete"))
			{
				System.out.println("Delete Selection");
				_canvas.selectionNNShape.deleteSelection(_canvas.listNNShape);
				_canvas.repaint();
			}
			else if(src.getName().equals("Duplicate"))
			{
				//Duplicate selection and set Selection Mode without emptying selection.
				System.out.println("Duplicate Selection");
				_canvas.selectionNNShape.duplicateSelection(_canvas.listNNShape);
				_canvas._GeneralMode=NNCanvas.SELECTIONMODE;
				_canvas._MiniMode=NNCanvas.RECTSELECTION;
				_canvas.tmpNode=null;
				_canvas.tmpArc=null;
				selectRadioButton.setSelected(true);
				
				_canvas.repaint();
			}
			else if(src.getName().equals("SelectionTool"))
			{
				setSelectionMode();
			}
			else if(src.getName().equals("ArcTool"))
			{
				setArcMode();
			}
			else if(src.getName().equals("NodeTool"))
			{
				setNodeMode();
			}
			else if(src.getName().equals("GridMenuItem"))
			{
				setGrid( ((JCheckBoxMenuItem)src).isSelected() );
				_canvas.activateGrid();
				repaint();
			}
			else if(src.getName().equals("Rearrange"))
			{
				_canvas.listNNShape.rearrange();
				_canvas.repaint();
				
			}
			else if(src.getName().equals("returnPico"))
			{
				NNGui.returnOK=true;
			}
			
		}
		
		if(e.getSource() instanceof JRadioButton){
			JRadioButton src = (JRadioButton)e.getSource();
			if(src.getName().equals("SelectButton"))
			{
				setSelectionMode();
			}
			else if(src.getName().equals("NodeButton"))
			{
				setNodeMode();
			}
			else if(src.getName().equals("ArcButton"))
			{
				setArcMode();
			}
		}
	}

	/*
	 * Actions
	 */
	
	void quitApp(){
		System.err.println("Leaving the app");
		this.dispose();
	}
	
	void saveNetwork(boolean newFile){
		//System.out.println("\tSaving");
		//Choose the file
		if(newFile || file==null)
		{
			//choose a new file name
			JFileChooser myChooser=new JFileChooser();
			
			//Test de la selection d'un fichier
			if(JFileChooser.APPROVE_OPTION!=myChooser.showSaveDialog(this))
				return;
			String filename=myChooser.getSelectedFile().getAbsolutePath();
			if (!filename.endsWith(".xml"))
				filename=filename + ".xml";
			
			file=new File(filename);
			System.err.println(""+file.getName());
		}
		
		try {
			Date today = new Date();
			DateFormat df = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.FRANCE);
			DateFormat df2 = DateFormat.getDateInstance(DateFormat.FULL, Locale.FRANCE);
			PrintWriter output = new PrintWriter( new FileOutputStream(file), true );

			//Write general info
			output.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			
			output.print("<file date=\"");
			output.print(df.format(today) +" " + df2.format(today));

			output.print("\" filetitle=\"");
			output.print(file);
			output.print("\">\n");
			
			output.print("<nneditordescription grid=\"");
			output.print(isGrid());
			output.print("\"/>\n");
			
			
			//Write Network	
			
			Enumeration enumeration;
			
			output.print("<nodes>\n");
			enumeration = _canvas.listNNShape.getNodes();
			while(enumeration.hasMoreElements()) 
			{ 
				output.println(  ((NNNode)(enumeration.nextElement())).toXML() );
			}
			
			output.print("</nodes>\n");
			output.print("<links>\n");
			enumeration = _canvas.listNNShape.getArcs();
			while(enumeration.hasMoreElements()) 
			{
				output.println( ((NNArc)(enumeration.nextElement())).toXML() );	
					
			}
			output.print("</links>\n");
			output.print("</file>\n");
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	void openSubNetwork(String subnetwork){
		
		
		//le parser en sax et creer les objets
		prepareToOpenDocument();
		
		NNSaxDocument.subOpen(subnetwork,this);
		
	}
	
	void openNetwork(){
		System.err.println("Creating network from File");
		//TODO Verification que le precedent doc nest pas sauve etc.....
		
		//recuperer le fichier
		JFileChooser myChooser=new JFileChooser();
		if(JFileChooser.APPROVE_OPTION!=myChooser.showOpenDialog(this))
			return;
		String filename=myChooser.getSelectedFile().getAbsolutePath();
		
		
		//le parser en sax et creer les objets
		prepareToOpenDocument();
		
		NNSaxDocument.open(filename,this);
		
	}
	
	void prepareToOpenDocument(){
		
		//_setNNCanvas();
		_canvas.listNNShape=new NNList();
		_canvas.selectionNNShape=new NNSelection();
		NNNode.resetNbNode();
		_canvas._GeneralMode=NNCanvas.NODEMODE;
		setSelectionMode();
		formArc.update();
		formNode.update();
	}
	
	void setSelectionMode(){
		System.err.println("\tSelection Mode is choosen");
		if(_canvas._GeneralMode!=NNCanvas.SELECTIONMODE){
			_canvas._GeneralMode=NNCanvas.SELECTIONMODE;
			_canvas._MiniMode=NNCanvas.RECTSELECTION;
			_canvas.selectionNNShape.emptySelection();
				_canvas.tmpNode=null;
				_canvas.tmpArc=null;
				selectRadioButton.setSelected(true);
			repaint();
		}
	}
	
	void setNodeMode(){
		System.err.println("\tNode Mode is choosen");
		if(_canvas._GeneralMode!=NNCanvas.NODEMODE){
		_canvas._GeneralMode=NNCanvas.NODEMODE;
		_canvas.selectionNNShape.emptySelection();
		_canvas.tmpNode=null;
		_canvas.tmpArc=null;
		nodeRadioButton.setSelected(true);
		repaint();
		}
	}
	
	void setArcMode(){
		System.err.println("\tArc Mode is choosen");
		if(_canvas._GeneralMode!=NNCanvas.ARCMODE){
			_canvas._GeneralMode=NNCanvas.ARCMODE;
			_canvas._MiniMode=NNCanvas.NODE0ARCMODE;
			_canvas.selectionNNShape.emptySelection();
			_canvas.tmpNode=null;
			_canvas.tmpArc=null;
			arcRadioButton.setSelected(true);
			repaint();
		}
	}
	
	void addPicoMenu(){
		if (picomenu)
			return;
		else {
			JMenu menu;
			JMenuItem menuItem;
			picomenu=true;
			
			//Build the first menu.
			menu = new JMenu("Piconode");
			menu.getAccessibleContext().setAccessibleDescription("Piconode Interface Menu");
			_menuBar.add(menu);
			
			
			menuItem = new JMenuItem("Return to Pico");
			//	menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
			menuItem.getAccessibleContext().setAccessibleDescription("End visualisation");
			menuItem.setName("returnPico");
			menuItem.addActionListener(this);
			menu.add(menuItem);
			
			setJMenuBar(_menuBar);			
		}
		
	}
	
	void removePicoMenu(){
		if (!picomenu)
			return;
		else {
			picomenu=false;
			
			_menuBar.remove(4);
			
			
			
			setJMenuBar(_menuBar);			
		}
		
	}
	
	
	public boolean isGrid() {
		return grid;
	}


	public void setGrid(boolean grid) {
		this.grid = grid;
	}

	public void setPicoMode() {
		addPicoMenu();
	}
	
	public void unSetPicoMode() {
		removePicoMenu();
	}
	
	
	
}


class XmlFileFilter extends FileFilter {
	
    //Accept all directories and all gif, jpg, tiff, or png files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
		
        String extension = getExtension(f);
        if (extension != null) {
            if (extension.equals("xml") ||
                extension.equals("XML")) {
				return true;
            } else {
                return false;
            }
        }
		
        return false;
    }
	
    //The description of this filter
    public String getDescription() {
        return "Just xml files";
    }
	
	public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');
		
        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
	
}






