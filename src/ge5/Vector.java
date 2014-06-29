package ge5;

public class Vector {
	
	public float x;
	public float y;
	
	public static final Vector zero = new Vector();
	public static final Vector one = new Vector(1.0f, 1.0f);
	public static final Vector up = new Vector(0.0f, 1.0f);
	public static final Vector down = new Vector(0.0f, -1.0f);
	public static final Vector right = new Vector(1.0f, 0.0f);
	public static final Vector left = new Vector(-1.0f, 0.0f);
	
	public Vector() {
		this(0.0f, 0.0f);
	}
	
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public boolean equals(Object other) {
		if (other == null)
			return false;
	    
		if (other == this)
			return true;
		
		if (!(other instanceof Vector))
			return false;
		
		Vector otherVector = (Vector) other;
		if (otherVector.x == x && otherVector.y == y)
			return true;
		
		return false;
	}
	
	public Vector add(Vector vector) {
		return new Vector(x + vector.x, y + vector.y);
	}
	
	public Vector sub(Vector vector) {
		return new Vector(x - vector.x, y - vector.y);
	}
	
	public Vector mul(float scalar) {
		return new Vector(x * scalar, y * scalar);
	}
	
	public float sqrMagnitude() {
		return x * x + y * y;
	}
	
	public float magnitude() {
		return (float) Math.sqrt(sqrMagnitude());
	}
	
	public Vector normalize() {
		float invMagnitude = magnitude();
		return mul(invMagnitude);
	}
	
	public static float dot(Vector v1, Vector v2) {
		return v1.x * v2.x + v1.y * v2.y;
	}
	
	public Vector project(Vector vector) {
		float projection = dot(this, vector);
		return vector.normalize().mul(projection);
	}

}