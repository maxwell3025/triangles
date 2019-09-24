package main;

public class Gradients {
	public float[] texCoordsx = new float[3];
	public float[] texCoordsy = new float[3];
	public float[] oneOverZ = new float[3];
	public float[] depth = new float[3];
	public float texCoordsxxStep;
	public float texCoordsxyStep;
	public float texCoordsyxStep;
	public float texCoordsyyStep;
	public float oneOverZxStep;
	public float oneOverZyStep;
	public float depthxStep;
	public float depthyStep;

	public Gradients(Vertex min, Vertex mid, Vertex max) {
		oneOverZ[0] = 1.0f/min.pos.w;
		oneOverZ[1] = 1.0f/mid.pos.w;
		oneOverZ[2] = 1.0f/max.pos.w;
		
		depth[0] = min.pos.z;
		depth[1] = mid.pos.z;
		depth[2] = max.pos.z;
		
		texCoordsx[0] = min.texCoords.x*oneOverZ[0];
		texCoordsx[1] = mid.texCoords.x*oneOverZ[1];
		texCoordsx[2] = max.texCoords.x*oneOverZ[2];
		
		texCoordsy[0] = min.texCoords.y*oneOverZ[0];
		texCoordsy[1] = mid.texCoords.y*oneOverZ[1];
		texCoordsy[2] = max.texCoords.y*oneOverZ[2];
		
		oneOverZxStep= xStep(oneOverZ[0],oneOverZ[1],oneOverZ[2],min,mid,max);
		oneOverZyStep= yStep(oneOverZ[0],oneOverZ[1],oneOverZ[2],min,mid,max);	
		
		depthxStep= xStep(depth[0],depth[1],depth[2],min,mid,max);
		depthyStep= yStep(depth[0],depth[1],depth[2],min,mid,max);
		
		texCoordsxxStep= xStep(texCoordsx[0],texCoordsx[1],texCoordsx[2],min,mid,max);
		texCoordsxyStep= yStep(texCoordsx[0],texCoordsx[1],texCoordsx[2],min,mid,max);	

		texCoordsyxStep= xStep(texCoordsy[0],texCoordsy[1],texCoordsy[2],min,mid,max);
		texCoordsyyStep= yStep(texCoordsy[0],texCoordsy[1],texCoordsy[2],min,mid,max);	
		}
	public float xStep(float v0, float v1, float v2, Vertex min, Vertex mid, Vertex max){
		float oneOverdX = 1.0f / ((mid.pos.x - max.pos.x) * (min.pos.y - max.pos.y) - (min.pos.x - max.pos.x) * (mid.pos.y - max.pos.y)); 
		return ((v1-v2)*(min.pos.y-max.pos.y)-
			(v0-v2)*(mid.pos.y-max.pos.y))*(oneOverdX);
	}
	public float yStep(float v0, float v1, float v2, Vertex min, Vertex mid, Vertex max){
		float oneOverdY = -1.0f / ((mid.pos.x - max.pos.x) * (min.pos.y - max.pos.y) - (min.pos.x - max.pos.x) * (mid.pos.y - max.pos.y)); 
		return ((v1-v2)*(min.pos.x-max.pos.x)-
			(v0-v2)*(mid.pos.x-max.pos.x))*(oneOverdY);
	}
}
