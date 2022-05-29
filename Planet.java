public class Planet {
	
	private double x,y;
	private double mass;
	private double xV,yV;
	private double xA,yA;
	private double radius;
	
	public Planet(double x, double y, double mass, double xV, double yV) {
		this.x = x;
		this.y = y;
		this.mass = mass;
		this.xV = xV;
		this.yV = yV;
		xA = 0;
		yA = 0;
		radius = 1;
	}
	
	public void finalizeAndReset() {
		xA /= mass;
		yA /= mass;
		xV += xA / 1000.0;
		yV += yA / 1000.0;
		x += xV / 1000.0;
		y += yV / 1000.0;
		if (x < 0) {
			x = 0;
			xV *= -1;
		}
		else if (x > 2048) {
			x = 2048;
			xV *= -1;
		}
		
		if (y < 0) {
			y = 0;
			yV *= -1;
		}
		else if (y > 2048) {
			y = 2048;
			yV *= -1;
		}
		xA = 0;
		yA = 0;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getMass() {
		return mass;
	}
	
	public String toString() {
		return "x: " + x + ", y" + y + ", mass " + mass + ", " + xV + "|" + yV + ", " + xA + "|" + yA;
	}
	
	public void addForce(double x, double y) {
		xA += x;
		yA += y;
	}
	
	public double getXV() {
		return xV;
	}
	
	public double getYV() {
		return yV;
	}
}