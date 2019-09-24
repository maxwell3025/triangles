package vectors;

public class Point4D {
	public float x;
	public float y;
	public float z;
	public float w;
	public static final Point4D Origin = new Point4D(0, 0, 0, 0);

	public Point4D(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public static float dotProduct(Point4D a, Point4D b) {
		return a.x * b.x + a.y * b.y + a.z * b.z + a.w * b.w;
	}

	public Point4D add(Point4D a) {
		return new Point4D(a.x + x, a.y + y, a.z + z, a.w + w);
	}

	public Point4D sub(Point4D a) {
		return new Point4D(x - a.x, y - a.y, z - a.z, w - a.w);
	}

	public Point4D scale(float scale) {
		return new Point4D(x * scale, y * scale, z * scale, w * scale);
	}

	public float dist() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	@Override
	public boolean equals(Object Obj) {
		if (Obj.getClass() == Point4D.class) {
			Point4D obj = (Point4D) Obj;
			return (x == obj.x) && (y == obj.y) && (z == obj.z) && (w == obj.w);
		}
		return false;
	}

}
