import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Screen extends JPanel implements KeyListener {
	private ArrayList<Planet> planets;
	private boolean goBack;
	private int i;
	private double energy;
	
	public Screen() {
		planets = new ArrayList<Planet>();
		goBack = false;
		
		//planets.add(new Planet(100,120,500,10,10));
		//planets.add(new Planet(120,100,500,15,-10));
		
		Random r = new Random(1);
		
		for (int i = 0; i < 10; i++) {
			planets.add(new Planet((r.nextDouble()*2048),(r.nextDouble()*2048),500, (r.nextDouble()*20)-10,(r.nextDouble()*20)-10));
		}
		
		
		setFocusable(true);
		addKeyListener(this);
	}
	
	public void calcGravity(Planet p, Planet v) {
		double distance = Math.pow(p.getX()-v.getX(),2)+Math.pow(p.getY()-v.getY(),2);
		double magnitude = 100 * p.getMass() * v.getMass() / distance;
		p.addForce((magnitude * (v.getX()-p.getX()) / Math.sqrt(distance)) * (1 - 20.0/distance),(magnitude * (v.getY()-p.getY()) / Math.sqrt(distance)) * (1 - 20.0/distance));
		energy -= (100 * p.getMass() * v.getMass() / Math.sqrt(distance)) * (1 - 20.0/(3*distance)) / 2.0;
	}
	
	public void calcGravity(Planet p, Node v) {
		double distance = Math.pow(p.getX()-v.getX(),2)+Math.pow(p.getY()-v.getY(),2);
		double magnitude = 100.0 * p.getMass() * v.getMass() / distance;
		p.addForce((magnitude * (v.getX()-p.getX()) / Math.sqrt(distance)) * (1 - 20.0/distance),(magnitude * (v.getY()-p.getY()) / Math.sqrt(distance)) * (1 - 20.0/distance));
		energy -= (100 * p.getMass() * v.getMass() / Math.sqrt(distance)) * (1 - 20.0/(3*distance)) / 2.0;
	}
	
	public void calculateForce(Planet p, Node root) {
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
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(512,512);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		for (Planet p : planets) {
			g.fillOval((int)((p.getX())-(2))/4,(int)((p.getY())-(2))/4,(int)(4),(int)(4));
		}
	}
	
	public synchronized void animate() {
		while(true){
			try {
			    Thread.sleep(1);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			    System.out.println(ex);
			}
			
			energy = 0;
			
			Node root = new Node(0,0,2048,2048);
			if (i != 0) {
				System.out.println(i);
			}
			for (Planet p : planets) {
				if (i != 0) {
					System.out.println(p);
				}
				root.add(p);
				p.finalizeAndReset();
			}
		
			for (int i = 0; i < planets.size(); i++) {
				calculateForce(planets.get(i),root);
				if (goBack) {
					i--;
					goBack = false;
				}
				energy += planets.get(i).getMass() * (Math.pow(planets.get(i).getXV(),2) + Math.pow(planets.get(i).getYV(),2)) / 2.0;
			}
			
			if (i != 0) {
				i--;
			}
			System.out.println(energy);
			
			repaint();
		}
	}
	
	public void keyPressed(KeyEvent e){
		int code = e.getKeyCode();
		if (code == 32) {//space
			i = 50;
		}
	}
	
	public void keyReleased(KeyEvent e) {}
	
	public void keyTyped(KeyEvent e){}
}