package main;

import vectors.Point4D;

public class Edge {
public float x;
public float xStep;
public int yStart;
public int yEnd;
public float texCoordx;
public float texCoordxStep;
public float texCoordy;
public float texCoordyStep;
public float oneOverZ;
public float oneOverZStep;
public float depth;
public float depthStep;
	public Edge(Gradients gradients, Vertex start, Vertex end, int minVertIndex) {
		yStart = (int) Math.ceil(start.pos.y+0.5);
		yEnd = (int) Math.ceil(end.pos.y+0.5);
		
		float xDist = start.pos.x - end.pos.x;
		float yDist = start.pos.y - end.pos.y;
		float yPreStep = yStart-start.pos.y;
		xStep = xDist /  yDist;
		x = start.pos.x+xStep*yPreStep;
		float xPreStep = x-start.pos.x;
		oneOverZ = gradients.oneOverZ[minVertIndex]+
				gradients.oneOverZyStep*yPreStep+
				gradients.oneOverZxStep*xPreStep;
		oneOverZStep = gradients.oneOverZyStep+gradients.oneOverZxStep*xStep;
		
		depth = gradients.depth[minVertIndex]+
				gradients.depthyStep*yPreStep+
				gradients.depthxStep*xPreStep;
		depthStep = gradients.depthyStep+gradients.depthxStep*xStep;
		
		texCoordx = gradients.texCoordsx[minVertIndex]+
				gradients.texCoordsxyStep*yPreStep+
				gradients.texCoordsxxStep*xPreStep;
		texCoordxStep = gradients.texCoordsxyStep+gradients.texCoordsxxStep*xStep;
		
		texCoordy = gradients.texCoordsy[minVertIndex]+
				gradients.texCoordsyyStep*yPreStep+
				gradients.texCoordsyxStep*xPreStep;
		texCoordyStep = gradients.texCoordsyyStep+gradients.texCoordsyxStep*xStep;
	}
	public void step(){
		x+=xStep;
		texCoordx += texCoordxStep;
		texCoordy += texCoordyStep;
		oneOverZ += oneOverZStep;
		depth+=depthStep;
	}

}
