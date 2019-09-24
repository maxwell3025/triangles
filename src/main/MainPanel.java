package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import static java.lang.Math.*;

import vectors.Matrix3D;
import vectors.Matrix4D;
import vectors.Point4D;

public class MainPanel extends JPanel implements Runnable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	int width, height;
	RenderContext raster;
	BufferedImage frame;
	private int threadnum = 0;
	long time = 0;
	Vertex a = new Vertex(0, 0.5f, 0, new Point4D(0.5f, 1, 0, 0));
	Vertex b = new Vertex((float) sin(PI / 1.5) * 0.5f, (float) cos(PI / 1.5) * 0.5f, 0, new Point4D(0, 0, 0, 0));
	Vertex c = new Vertex((float) -sin(PI / 1.5) * 0.5f, (float) cos(PI / 1.5) * 0.5f, 0, new Point4D(1, 0, 0, 0));
	Mesh mesh = new Mesh();
	boolean paintdone = false;

	public MainPanel(int width, int height) {
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));
		raster = new RenderContext(width, height);
		frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		BitMap texture = new BitMap(0,0);
		try {
			BufferedImage tex = ImageIO.read(new File("C:/Users/Administrator/Documents/donut.png"));
			texture = new BitMap(tex);
		} catch (IOException e) {
		}
		for(float x = 0;x<1;x+=1f/32){
			for(float y = 0;y<1;y+=1f/32){
				float step = 1f/32;
				Vertex oo = torus(x,y);
				Vertex oi = torus(x,y+step);
				Vertex io = torus(x+step,y);
				Vertex ii = torus(x+step,y+step);
				mesh.addFace(new Triangle(oo,io,oi,texture));
				mesh.addFace(new Triangle(ii,io,oi,texture));
			}
		}
	}
	public Vertex torus(float x, float y){
				float zRotX = (float)sin(x*PI*2);
				float zRotY = (float)cos(x*PI*2);
				float xRotX = (float)sin(y*PI*2)+2;
				float xRotY = (float)cos(y*PI*2);
				return new Vertex(zRotX*xRotX,zRotY*xRotX,xRotY, new Point4D(x,y,0,0));
	}

	public void init() {
		new Thread(this).start();
	}

	public void paint(Graphics g) {
		g.drawImage(frame, 0, 0, width, height, null);
		paintdone = true;
	}

	public void graphicsUpdate() {raster.fill(0xff000000);
		raster.clearZBuffer();
		Matrix3D rotationx = Matrix3D.rotx(System.nanoTime() / 1000000000f);
		Matrix3D rotationy = Matrix3D.roty(0*System.nanoTime() / 1000000000f);
		Matrix3D rotationz = Matrix3D.rotz(System.nanoTime() / 1000000000f);
		Matrix3D rotation = rotationx.transform(rotationy.transform(rotationz));
		Matrix4D rotationFinal = new Matrix4D(rotation);
		rotationFinal.w.z += 8;
		Matrix4D perspective = Matrix4D.initPerspective((float) Math.toRadians(60), (float) width / height, 0.01f,
				1000);
		// Vertex newA = a.transform(rotationFinal).transform(perspective);
		// Vertex newB = b.transform(rotationFinal).transform(perspective);
		// Vertex newC = c.transform(rotationFinal).transform(perspective);
		for (int i = 0; i < mesh.getFaces(); i++) {
			Triangle face = mesh.getFace(i).transform(rotationFinal).transform(perspective);
			raster.drawTriangle(face.a, face.b, face.c, face.tex);
		}
	}

	public void contentUpdate() {
		
	}

	@Override
	public void run() {
		switch (threadnum) {
		case 0: {
			threadnum++;
			new Thread(this).start();
			for (;;) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}
				System.out.println(1000 / (System.currentTimeMillis() - time));
				time = System.currentTimeMillis();
				graphicsUpdate();
				raster.copyToBufferedImage(frame);
				paintdone = false;
				repaint();
				while (!paintdone) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
					}
				}
			}

		}
		case 1: {
			threadnum++;
			for (;;) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}
				contentUpdate();
			}

		}
		}
	}
}
