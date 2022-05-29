import java.util.ArrayList;
import javax.swing.JFrame;

public class Runner {
	public static void main(String[] args) {
		/*ArrayList<Planet> planets = new ArrayList<Planet>();
		
		planets.add(new Planet((Math.random()*800),(Math.random()*600),100));
		planets.add(new Planet((Math.random()*800),(Math.random()*600),100));
		planets.add(new Planet((Math.random()*800),(Math.random()*600),100));
		planets.add(new Planet((Math.random()*800),(Math.random()*600),100));
		planets.add(new Planet((Math.random()*800),(Math.random()*600),100));
		planets.add(new Planet((Math.random()*800),(Math.random()*600),100));
		
		
		//loop starts here
		for (int i = 0; i < 1000; i++) {
			Node root = new Node(0,0,800,600);
		
			for (Planet p : planets) {
				root.add(p);
				p.finalizeAndReset();
			}
		
			for (Planet p : planets) {
				calculateForce(p,root);
			}
		}*/
		
		JFrame frame = new JFrame("Orbits-Display");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Create panel and add it to the frame
		Screen bt = new Screen();
		
		frame.add(bt);
		frame.pack();
		frame.setVisible(true);
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		
		bt.animate();
	}
	
	public static void calcGravity(Planet p, Planet v) {
		double distance = Math.pow(p.getX()-v.getX(),2)+Math.pow(p.getY()-v.getY(),2);
		double magnitude = 100 * p.getMass() * v.getMass() / distance;
		p.addForce(magnitude * (p.getX()-v.getX()) / Math.sqrt(distance),magnitude * (p.getY()-v.getY()) / Math.sqrt(distance));
	}
	
	public static void calcGravity(Planet p, Node v) {
		double distance = Math.pow(p.getX()-v.getX(),2)+Math.pow(p.getY()-v.getY(),2);
		double magnitude = 100 * p.getMass() * v.getMass() / distance;
		p.addForce(magnitude * (p.getX()-v.getX()) / Math.sqrt(distance),magnitude * (p.getY()-v.getY()) / Math.sqrt(distance));
	}
	
	public static void calculateForce(Planet p, Node root) {
		if (root.getNumPlanets() == 1) {
			if (root.getPlanet() != p) {
				calcGravity(p,root.getPlanet());
			}
		}
		else if (root.getWidth() / Math.pow(Math.pow(p.getX()-root.getX(),2)+Math.pow(p.getY()-root.getY(),2),0.5) < 0.5) {
			calcGravity(p,root);
		}
		else {
			if (root.getNW() != null) {
				calculateForce(p,root.getNW());
			}
			if (root.getSW() != null) {
				calculateForce(p,root.getSW());
			}
			if (root.getNE() != null) {
				calculateForce(p,root.getNE());
			}
			if (root.getSE() != null) {
				calculateForce(p,root.getSE());
			}
		}
	}
	
	public static void printNodes(Node root, int numTabs) {
		if (root != null) {
			for (int i = 0; i < numTabs; i++) {
				System.out.print("  ");
			}
			System.out.println(root);
		
			printNodes(root.getNW(),numTabs+1);
			printNodes(root.getNE(),numTabs+1);
			printNodes(root.getSW(),numTabs+1);
			printNodes(root.getSE(),numTabs+1);
		}
	}
}