package main;

import vectors.Matrix4D;
import vectors.Point4D;

public class Vertex {
	public Point4D pos;
	public Point4D texCoords;

	public Vertex(Point4D pos, Point4D texCoords) {
		this.pos = pos;
		this.texCoords = texCoords;
	}

	public Vertex(float x, float y, float z, Point4D texCoords) {
		pos = new Point4D(x, y, z, 1);
		this.texCoords = texCoords;
	}

	public Vertex(float x, float y, float z, float w, Point4D texCoords) {
		pos = new Point4D(x, y, z, w);
		this.texCoords = texCoords;
	}

	public Vertex perspectiveDivide() {
		return new Vertex(pos.x / pos.w, pos.y / pos.w, pos.z / pos.w, pos.w, texCoords);
	}

	public Vertex transform(Matrix4D matrix) {
		return new Vertex(matrix.transform(pos), texCoords);
	}

}
