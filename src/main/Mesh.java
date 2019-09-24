package main;

import java.util.ArrayList;

import vectors.Point4D;

public class Mesh {
	ArrayList<Triangle> faces = new ArrayList<Triangle>();
	public Mesh() {
	}
	public int getFaces(){
		return faces.size();
	}
	public Triangle getFace(int index){
		return faces.get(index);
	}
	public static Mesh cube(float x, float y, float z, BitMap texture){
		Mesh out = new Mesh();
		Vertex ii1 = new Vertex(1+x,1+y,1+z,new Point4D(0,0,0,0));
		Vertex oi1 = new Vertex(-1+x,1+y,1+z,new Point4D(0,1,0,0));
		Vertex io1 = new Vertex(1+x,-1+y,1+z,new Point4D(1,0,0,0));
		Vertex oo1 = new Vertex(-1+x,-1+y,1+z,new Point4D(1,1,0,0));
		Vertex ii2 = new Vertex(1+x,1+y,-1+z,new Point4D(0,0,0,0));
		Vertex oi2 = new Vertex(-1+x,1+y,-1+z,new Point4D(0,1,0,0));
		Vertex io2 = new Vertex(1+x,-1+y,-1+z,new Point4D(1,0,0,0));
		Vertex oo2 = new Vertex(-1+x,-1+y,-1+z,new Point4D(1,1,0,0));
		Vertex ii3 = new Vertex(1+x,1+y,1+z,new Point4D(0,0,0,0));
		Vertex oi3 = new Vertex(-1+x,1+y,1+z,new Point4D(0,1,0,0));
		Vertex io3 = new Vertex(1+x,1+y,-1+z,new Point4D(1,0,0,0));
		Vertex oo3 = new Vertex(-1+x,1+y,-1+z,new Point4D(1,1,0,0));
		Vertex ii4 = new Vertex(1+x,-1+y,1+z,new Point4D(0,0,0,0));
		Vertex oi4 = new Vertex(-1+x,-1+y,1+z,new Point4D(0,1,0,0));
		Vertex io4 = new Vertex(1+x,-1+y,-1+z,new Point4D(1,0,0,0));
		Vertex oo4 = new Vertex(-1+x,-1+y,-1+z,new Point4D(1,1,0,0));
		Vertex ii5 = new Vertex(1+x,1+y,1+z,new Point4D(0,0,0,0));
		Vertex oi5 = new Vertex(1+x,-1+y,1+z,new Point4D(0,1,0,0));
		Vertex io5 = new Vertex(1+x,1+y,-1+z,new Point4D(1,0,0,0));
		Vertex oo5 = new Vertex(1+x,-1+y,-1+z,new Point4D(1,1,0,0));
		Vertex ii6 = new Vertex(-1+x,1+y,1+z,new Point4D(0,0,0,0));
		Vertex oi6 = new Vertex(-1+x,-1+y,1+z,new Point4D(0,1,0,0));
		Vertex io6 = new Vertex(-1+x,1+y,-1+z,new Point4D(1,0,0,0));
		Vertex oo6 = new Vertex(-1+x,-1+y,-1+z,new Point4D(1,1,0,0));
		out.addFace(new Triangle(ii1, io1, oi1, texture));
		out.addFace(new Triangle(oo1, io1, oi1, texture));
		out.addFace(new Triangle(ii2, io2, oi2, texture));
		out.addFace(new Triangle(oo2, io2, oi2, texture));
		out.addFace(new Triangle(ii3, io3, oi3, texture));
		out.addFace(new Triangle(oo3, io3, oi3, texture));
		out.addFace(new Triangle(ii4, io4, oi4, texture));
		out.addFace(new Triangle(oo4, io4, oi4, texture));
		out.addFace(new Triangle(ii5, io5, oi5, texture));
		out.addFace(new Triangle(oo5, io5, oi5, texture));
		out.addFace(new Triangle(ii6, io6, oi6, texture));
		out.addFace(new Triangle(oo6, io6, oi6, texture));
		return out;
	}
	public void addFace(Triangle t){
		faces.add(t);
	}
	public void add(Mesh a){
		faces.addAll(a.faces);
	}

}
