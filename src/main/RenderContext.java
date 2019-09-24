package main;

import java.util.Arrays;

import vectors.Matrix4D;
import vectors.Point2D;
import vectors.Point4D;

public class RenderContext extends BitMap {
	float[] zBuffer;

	public RenderContext(int width, int height) {
		super(width, height);
		zBuffer = new float[width * height];
	}
	public void clearZBuffer(){
		Arrays.fill(zBuffer, Float.POSITIVE_INFINITY);
	}

	public void drawTriangle(Vertex v1, Vertex v2, Vertex v3, BitMap texture) {
		Matrix4D screenTransform = Matrix4D.screenTransform(height / 2, width / 2);
		Vertex min = v1.transform(screenTransform).perspectiveDivide();
		Vertex mid = v2.transform(screenTransform).perspectiveDivide();
		Vertex max = v3.transform(screenTransform).perspectiveDivide();
		if (max.pos.y < mid.pos.y) {
			Vertex buf = max;
			max = mid;
			mid = buf;
		}
		if (mid.pos.y < min.pos.y) {
			Vertex buf = mid;
			mid = min;
			min = buf;
		}
		if (max.pos.y < mid.pos.y) {
			Vertex buf = max;
			max = mid;
			mid = buf;
		}
		Point2D a = new Point2D(mid.pos.x - min.pos.x, mid.pos.y - min.pos.y);
		Point2D b = new Point2D(max.pos.x - min.pos.x, max.pos.y - min.pos.y);
		convertTriangle(min, mid, max, Point2D.crossProduct(a, b) < 0, texture);
	}

	private void convertTriangle(Vertex min, Vertex mid, Vertex max, boolean handedness, BitMap texture) {
		Gradients gradients = new Gradients(min, mid, max);
		Edge topToBottom = new Edge(gradients, min, max, 0);
		Edge topToMiddle = new Edge(gradients, min, mid, 0);
		Edge middleToBottom = new Edge(gradients, mid, max, 1);
		Edge left = topToBottom;
		Edge right = topToMiddle;
		if (handedness) {
			Edge buf = left;
			left = right;
			right = buf;
		}
		int yStart = topToMiddle.yStart;
		int yEnd = topToMiddle.yEnd;
		for (int i = yStart; i < yEnd; i++) {
			drawScanLine(gradients, left, right, i, texture);
			left.step();
			right.step();
		}
		left = topToBottom;
		right = middleToBottom;
		if (handedness) {
			Edge buf = left;
			left = right;
			right = buf;
		}
		yStart = middleToBottom.yStart;
		yEnd = middleToBottom.yEnd;
		for (int i = yStart; i < yEnd; i++) {
			drawScanLine(gradients, left, right, i, texture);
			left.step();
			right.step();
		}
	}

	private void drawScanLine(Gradients gradients, Edge left, Edge right, int y, BitMap texture) {
		int xMin = (int) Math.ceil(left.x);
		int xMax = (int) Math.ceil(right.x);

		float xDist = right.x - left.x;
		float texCoordXXStep = (right.texCoordx - left.texCoordx) / xDist;
		float texCoordYXStep = (right.texCoordy - left.texCoordy) / xDist;
		float oneOverZXStep = (right.oneOverZ - left.oneOverZ) / xDist;
		float depthXStep = (right.depth - left.depth) / xDist;

		float xPreStep = xMin - left.x;
		float texCoordX = left.texCoordx + texCoordXXStep * xPreStep;
		float texCoordY = left.texCoordy + texCoordYXStep * xPreStep;
		float oneOverZ = left.oneOverZ + oneOverZXStep * xPreStep;
		float depth = left.depth + depthXStep * xPreStep;
		for (int x = xMin; x < xMax; x++) {
			int index = x + y * width;
			float z = 1.0f / oneOverZ;
			if (z < zBuffer[index]) {
				zBuffer[index] = z;
				int srcX = (int) ((texCoordX * z) * (texture.width));
				int srcY = (int) ((texCoordY * z) * (texture.height));
				setRGB(x, y, srcX, srcY, texture);
				texCoordX += texCoordXXStep;
				texCoordY += texCoordYXStep;
				oneOverZ += oneOverZXStep;
			}
		}
	}

}
