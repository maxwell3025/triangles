package main;

import vectors.Matrix4D;

public class Triangle {
Vertex a;
Vertex b;
Vertex c;
BitMap tex;
	public Triangle(Vertex v1, Vertex v2, Vertex v3, BitMap texture) {
		tex = texture;
		a = v1;
		b = v2;
		c = v3;
	}
	public Triangle transform(Matrix4D mat){
		return new Triangle(a.transform(mat), b.transform(mat), c.transform(mat),tex);
	}

}
