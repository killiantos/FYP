package piconode.visual;




import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.QuadCurve2D;


/**
 * 
 */

/**
 * @author lri
 *
 */


public class NNArc extends NNShape{

	private NNNode nodeFrom,nodeTo;
	private double weight;
	private double minimum, maximum;
	private int _decalage;

	private int x,y;
	private int demiCote=5;
	

	 /**
     * abscisse du vecteur IC , I milieu du segment compose reliant les ectremite de l'arc, C milieu de l'arc
     */
    int _vx=0;

    /**
     * ordonne du vecteur IC , I milieu du segment compose reliant les ectremite de l'arc, C milieu de l'arc
     */
    int _vy=0;
	
    
    
    
	NNArc (NNNode from_arg,NNNode to_arg){
		if(from_arg!=null && to_arg!=null ){
		nodeFrom=from_arg;
		nodeTo=to_arg;
		nodeFrom.outputArcs.add(this);
		nodeTo.inputArcs.add(this);
		weight=0;
		color=Color.BLACK;
		_decalage=20;
		setSelected(false);
		minimum=maximum=0;
		}
		else {
			nodeFrom=new NNNode();
			nodeTo=new NNNode();
			
			System.err.println("Node is null :"+to_arg.hashCode());
		}
	}
	
	NNArc (NNArc arc_arg,NNNode from_arg,NNNode to_arg){
	
		nodeFrom=from_arg;
		nodeTo=to_arg;
		
		nodeFrom.outputArcs.add(this);
		nodeTo.inputArcs.add(this);
		
		weight=arc_arg.getWeight();
		minimum=arc_arg.getMinimum();
		maximum=arc_arg.getMaximum();
		color=arc_arg.getColor();
		_decalage=arc_arg._decalage;
		setSelected(false);
	
	}
	
	NNArc(NNNode nodeFrom_arg,NNNode nodeTo_arg
			,String weight_arg,String minimum_arg,String maximum_arg
			,String decalage_arg,String colorRed_arg,String colorGreen_arg,String colorBlue_arg){
		nodeFrom=nodeFrom_arg;
		nodeTo=nodeTo_arg;
		nodeFrom.outputArcs.add(this);
		nodeTo.inputArcs.add(this);
		weight=Double.parseDouble(weight_arg);
		minimum=Double.parseDouble(minimum_arg);
		maximum=Double.parseDouble(maximum_arg);
		_decalage=Integer.parseInt(decalage_arg);
		color=new Color(Integer.parseInt(colorRed_arg),Integer.parseInt(colorGreen_arg),Integer.parseInt(colorBlue_arg));
	}
	
	
	
	
	
	/*
	 * USEFUL????
	 */
	protected void deleteNodeRegistration(){
		nodeFrom.outputArcs.remove(this);
		nodeTo.inputArcs.remove(this);
	
	}
	
	public NNNode getNodeFrom() {
		return nodeFrom;
	}

	public void setNodeFrom(NNNode nodeFrom) {
		this.nodeFrom = nodeFrom;
	}

	public NNNode getNodeTo() {
		return nodeTo;
	}

	public void setNodeTo(NNNode nodeTo) {
		this.nodeTo = nodeTo;
	}

	void paint(Graphics2D g2) {
	
	    
	    //Setting Color
	    if(isSelected()!=isInverseTempSelection())
			g2.setColor(NNFrame._selectionColor );
			
			
		else g2.setColor(color);
	    
	    int [] xx=new int[3];
		int [] yy=new int[3];	
		int r1=NNNode._RAYON+1;
		int r2=NNNode._RAYON+1;
		int x1=nodeFrom.getPositionX();
		int y1=nodeFrom.getPositionY();
		int x2=nodeTo.getPositionX();
		int y2=nodeTo.getPositionY();
	

			
		try{//a revoir
		    double dist=Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
		    double tmp1=r1/dist;
		    double tmp2=r2/dist;
		    
		    int dx1=(new Double(tmp1*(x2-x1))).intValue();
		    int dy1=(new Double(tmp1*(y2-y1))).intValue();
		    
		    int dx2=(new Double(tmp2*(x2-x1))).intValue();
		    int dy2=(new Double(tmp2*(y2-y1))).intValue();
		    
		  
		    //milieu du segment P1P2
		    int x_I=getCentreSegmentX();
		    int y_I=getCentreSegmentY();
		    
		    
		    
		    _vx= _decalage * rotateX (Math.PI/2, x2-x1 , y2-y1 ) / new Double(dist).intValue();
		    _vy= _decalage * rotateY (Math.PI/2, x2-x1 , y2-y1 ) / new Double(dist).intValue();
		    
		    int pcx= x_I + produitScalaire(1,0, 2*_vx,2*_vy);
		    int pcy= y_I + produitScalaire(0,1, 2*_vx,2*_vy);
		    x= x_I+_vx;
		    y= y_I+_vy;
		    QuadCurve2D qc2d=new QuadCurve2D.Double(
							    x1+dx1,y1+dy1,
							    pcx,
							    pcy,
							    x2-dx2,y2-dy2
							    );
		   

		    
		    
		    g2.fillOval(x-demiCote, 
				       y-demiCote,  
				       demiCote*2,
				       demiCote*2);
		
		    g2.draw(qc2d);	
		    
		    
		    //et on ecrit le poid sous le carre.
		    FontMetrics fontMetrics = g2.getFontMetrics();
		    String poid=new Double(getWeight()).toString();
		    if(poid.length()>4)poid=poid.substring(0,5);//troncature
		    int longueur = fontMetrics.stringWidth(poid);
		    int hauteur = fontMetrics.getHeight();
		    //on calcule les coordonnees du coin inferieur gauche du texte
		    int tx=x -longueur/2;
		    int ty=y+NNNode._RAYON+hauteur;
		    g2.drawString(poid,tx,ty);
		    
		    //on trace la fleche
		 
		    xx[0]=x2-dx2;
		    yy[0]=y2-dy2;
		    
		    double ux=(x2- ( pcx + x )/2 )/( Math.sqrt( Math.pow( x2-pcx,2 )+Math.pow( y2-pcy,2 ) )  );
		    double uy=(y2- ( pcy + y )/2 )/( Math.sqrt( Math.pow( x2-pcx,2 )+Math.pow( y2-pcy,2 ) )  );
		    int x0= xx[0] - new Double(ux*5).intValue();
		    int y0= yy[0] - new Double(uy*5).intValue();
		    xx[1]=x0 - rotateX(Math.PI/8,10*ux,10*uy) ;
		    yy[1]=y0 - rotateY(Math.PI/8,10*ux,10*uy) ;
		    xx[2]=x0 - rotateX(-Math.PI/8,10*ux,10*uy) ;
		    yy[2]=y0 - rotateY(-Math.PI/8,10*ux,10*uy) ;
		    
		    g2.fillPolygon(xx,yy,3);
		    
		}
		catch(java.lang.ArithmeticException ae)
		    {//meme coordonnees:on trace une boucle
			xx[0]=x1-5;
			yy[0]=y1-r1-4;
			xx[1]=x1-7;
			yy[1]=y1-r1-7;
			xx[2]=x1-2;
			yy[2]=y1-r2-7;
			g2.drawOval(x1-5,y1-r1-10,10,10);
			g2.fillPolygon(xx,yy,3);
		
			x= x1+5;
			y= y1-r1-5;
		
			
			
		    g2.fillOval(x-demiCote, 
				       y-demiCote,  
				       demiCote*2,
				       demiCote*2);
			
			
			
			
			//et on ecrit le poid sous le carre.
			String poid=new Double(getWeight()).toString();
			if(poid.length()>4)poid=poid.substring(0,5);
			//on calcule les coordonnees du coin inferieur gauche du texte
			int tx=x +5;
			int ty=y;
			g2.drawString(poid,tx,ty);
		    }
		g2.setColor(Color.black);
		
	    
	}
	
	

    /**
     * composante gauche du resultat du produit de matrice: (matrice de rotaion d'angle 'angle') (Vecteur ('x','y'))
     * @param angle l'angle de rotation
     * @param x abscisse du vecteur
     * @param y ordonne du vecteur
     * @return composante gauche du resultat du produit de matrice: (matrice de rotaion d'angle 'angle') (Vecteur ('x','y'))
     */
    private int rotateX(double angle, double x, double y){
	return  (new Double(x*Math.cos(angle)-y*Math.sin(angle))).intValue();
    }
    /**
     * composante droite du resultat du produit de matrice: (matrice de rotaion d'angle 'angle') (Vecteur ('x','y'))
     * @param angle l'angle de rotation
     * @param x abscisse du vecteur
     * @param y ordonne du vecteur
     * @return composante droite du resultat du produit de matrice: (matrice de rotaion d'angle 'angle') (Vecteur ('x','y'))
     */
    private int rotateY(double angle, double x, double y){
	return  (new Double(y*Math.cos(angle)+x*Math.sin(angle))).intValue();
    }
    
    
    
    /**
     * @return retourne l'abscisse du centre du segment forme par les deux extremites de l'arc
     */
    protected int getCentreSegmentX()
    {
	return ( (nodeTo.getPositionX()+nodeFrom.getPositionX())/2 );
    }
    /**
     * @return retourne l'ordonnee du centre du segment forme par les deux extremites de l'arc
     */
    protected int getCentreSegmentY()
    {
	return ( (nodeTo.getPositionY()+nodeFrom.getPositionY())/2   );
    }
    
    /**
     * @return retourne l'abscisse du centre de l'arc
     */
    protected int getCentreArcX()
    {
	if (nodeFrom.getPositionX()==nodeTo.getPositionX() && nodeFrom.getPositionY()==nodeTo.getPositionY())
		x=(nodeFrom.getPositionX()+5);
	return x;
    }
    /**
     * @return retourne l'ordonnee du centre de l'arc
     */
    protected int getCentreArcY()
    {
	if (nodeFrom.getPositionX()==nodeTo.getPositionX() && nodeFrom.getPositionY()==nodeTo.getPositionY())
		y=(nodeFrom.getPositionY() -NNNode._RAYON -5);
	return y;
    }
    
    
    /**
     * (x1,y1) vecteur unitaire
     */
    
    private int produitScalaire(double x1, double y1,double x2, double y2){
	
	return (
		new Double(
			   (x1*x2 + y1*y2) 
			   ).intValue()
		);
    }
 
	
    /**
    *
    */
   protected double distance()
   {
	return ( Math.sqrt( Math.pow(nodeTo.getPositionX()-nodeFrom.getPositionX(),2)+Math.pow(nodeTo.getPositionY()-nodeFrom.getPositionY(),2) ) );
   }
   
   /**
    *
    */
   protected void mooveTo(int x, int y)
   {
	double dist=distance();
	
	int ux_x=(nodeTo.getPositionX()-nodeFrom.getPositionX());
	int ux_y=(nodeTo.getPositionY()-nodeFrom.getPositionY());
	
	double uy_x= rotateX( Math.PI/2 ,ux_x, ux_y)/dist;
	double uy_y= rotateY( Math.PI/2 ,ux_x, ux_y)/dist;
	
	int x_I=  getCentreSegmentX();
	int y_I=  getCentreSegmentY();
	
	int x_IC= x - x_I;
	int y_IC= y - y_I;
	
	int scaly=new Double(
			     produitScalaire( 
					     uy_x,
					     uy_y,
					     x_IC,
					     y_IC
					     )
			     ).intValue();
	
	_decalage=scaly;
	
	_vx= new Double(
			produitScalaire( 
					1,
					0,
					_decalage*uy_x,
					_decalage*uy_y
					)
			).intValue();
	
	_vy= new Double(
			produitScalaire( 
					0,
					1,
					_decalage*uy_x,
					_decalage*uy_y
					)
			).intValue();
	x= x_I+_vx;
	y= y_I+_vy;
   }
	
	
	
	
	

	boolean isInRect(Rectangle rect){
		return (x>=rect.x && x<=rect.x+rect.width
				&& y>=rect.y && y<=rect.y+rect.height);
			
	}
	
	boolean contains(int x_arg, int y_arg)
	{
		if ( x_arg>=x-demiCote 
				&& y_arg>=y-demiCote
				&& x_arg<=x+demiCote 
				&& y_arg<=y+demiCote)
			return true;
		return false;
	}
	
	
	public double getWeight() {
		return weight;
	}

	public void setWeight(double poids) {
		this.weight = poids;
	}

	String toXML() {
		
		String retour ="\t\t<link idFrom=\"" + nodeFrom.getMyId() + "\""
		+ " idTo=\"" + nodeTo.getMyId() + "\">";

		retour +="<weight at=\"" + getWeight() + "\"/>";
		retour +="<minimum at=\"" + getMinimum() + "\"/>";
		retour +="<maximum at=\"" + getMaximum() + "\"/>";
		retour +="<decalage at=\"" + _decalage + "\"/>";
		
		
		retour +="<color"
 		+ " red=\"" + color.getRed() + "\""
 		+ " green=\"" + color.getGreen() + "\""
 		+ " blue=\"" + color.getBlue() + "\"/>";
 				
		retour +="</link>";
		
		return retour;
	}

	public double getMaximum() {
		return maximum;
	}

	public void setMaximum(double maximum) {
		this.maximum = maximum;
	}

	public double getMinimum() {
		return minimum;
	}

	public void setMinimum(double minimum) {
		this.minimum = minimum;
	}

	public int get_decalage() {
		return _decalage;
	}

	public void set_decalage(int _decalage) {
		this._decalage = _decalage;
	}
	
	
}
