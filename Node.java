public class Node {
	private Node ne,nw,se,sw;
	private Node parent;
	
	private double totalMass;
	private int numPlanets;
	private double xCOM, yCOM;
	private double x_min, x_max, y_min, y_max;
	
	private Planet firstPlanet; //just to remember the pointer to the first planet
	private String path;
	
	public Node(double x_min, double y_min, double x_max, double y_max) {
		totalMass = 0;
		numPlanets = 0;
		xCOM = 0;
		yCOM = 0;
		ne = null;
		nw = null;
		se = null;
		sw = null;
		parent = null;
		this.x_min = x_min;
		this.x_max = x_max;
		this.y_min = y_min;
		this.y_max = y_max;
		firstPlanet = null;
		path = "";
	}
	
	public Node(double x_min, double y_min, double x_max, double y_max, Node parent, String path) {
		totalMass = 0;
		numPlanets = 0;
		xCOM = 0;
		yCOM = 0;
		ne = null;
		nw = null;
		se = null;
		sw = null;
		this.parent = parent;
		this.x_min = x_min;
		this.x_max = x_max;
		this.y_min = y_min;
		this.y_max = y_max;
		firstPlanet = null;
		this.path = path;
	}
	
	
	public void add(Planet planet) {
		if (numPlanets == 0) {
			xCOM = planet.getX();
			yCOM = planet.getY();
			totalMass = planet.getMass();
			numPlanets = 1;
			firstPlanet = planet;
		}
		else {
			if (numPlanets == 1) {
				addToQuad(firstPlanet);
			}
			addToQuad(planet);
			
			xCOM = (xCOM * totalMass + (planet.getX() * planet.getMass())) / (totalMass + planet.getMass());
			yCOM = (yCOM * totalMass + (planet.getY() * planet.getMass())) / (totalMass + planet.getMass());
			totalMass += planet.getMass();
			numPlanets++;
		}
	}
	
	private void addToQuad(Planet planet) {
		if (/*planet.getX() > x_min && */planet.getX() < (x_min + x_max) / 2/* && planet.getY() > y_min*/ && planet.getY() < (y_min + y_max) / 2) {
			if (nw == null) {
				nw = new Node(x_min,y_min,(x_min + x_max) / 2,(y_min + y_max) / 2,this,path + "-nw");
			}
			nw.add(planet);
		}
		else if (/*planet.getX() > x_min &&*/ planet.getX() < (x_min + x_max) / 2 && planet.getY() > (y_min + y_max) / 2/* && planet.getY() < y_max*/) {
			if (sw == null) {
				sw = new Node(x_min,y_min,(x_min + x_max) / 2,(y_min + y_max) / 2,this,path + "-sw");
			}
			sw.add(planet);
		}
		else if (planet.getX() > (x_min + x_max) / 2 /*&& planet.getX() < x_max*/ && planet.getY() > (y_min + y_max) / 2 /*&& planet.getY() < y_max*/) {
			if (se == null) {
				se = new Node((x_min + x_max) / 2,(y_min + y_max) / 2,x_max,y_max,this,path + "-se");
			}
			se.add(planet);
		}
		else if (planet.getX() > (x_min + x_max) / 2/* && planet.getX() < x_max && planet.getY() > y_min*/ && planet.getY() < (y_min + y_max) / 2 ) {
			if (ne == null) {
				ne = new Node((x_min + x_max) / 2,y_min,x_max,(y_min + y_max) / 2,this,path + "-ne");
			}
			ne.add(planet);
		}
	}
	
	public double getWidth() {
		return x_max - x_min;
	}
	
	public String toString() {
		return "xCom : " + xCOM + ", yCom : " + yCOM + ", totalMass : " + totalMass + ", num planets : " + numPlanets + " path: " + path;
	}
	
	public Node getNW() {
		return nw;
	}
	
	public Node getSW() {
		return sw;
	}
	
	public Node getNE() {
		return ne;
	}
	
	public Node getSE() {
		return se;
	}
	
	public int getNumPlanets() {
		return numPlanets;
	}
	
	public Planet getPlanet() {
		if (numPlanets == 1) {
			return firstPlanet;
		}
		return null;
	}
	
	public double getX() {
		return xCOM;
	}
	
	public double getY() {
		return yCOM;
	}
	
	public double getMass() {
		return totalMass;
	}
}