package main;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class BitMap {
	int width;
	int height;
	int[] raster;

	public BitMap(int width, int height) {
		raster = new int[width * height];
		this.width = width;
		this.height = height;
		Arrays.fill(raster, -1);
	}

	public BitMap(BufferedImage b) {
		width = b.getWidth();
		height = b.getHeight();
		raster = new int[width * height];
		raster=b.getRGB(0, 0, width, height, raster, 0, width);
	}

	public void setRGB(int x, int y, int color) {
		if ((x < 0 != x < width) & (y < 0 != y < height))
			raster[x + y * width] = color;
	}

	public void setRGB(int x, int y, int r, int g, int b, int a) {
		if ((x < 0 != x < width) & (y < 0 != y < height)) {
			int color = b + (g << 8) + (r << 16) + (a << 24);
			raster[x + y * width] = color;
		}
	}

	public int getRGB(int x, int y) {
		if ((x < 0 != x < width) & (y < 0 != y < height)) {
			return raster[x + y * width];
		} else {
			return 0;
		}
	}

	public void copyToBufferedImage(BufferedImage b) {
		b.setRGB(0, 0, width, height, raster, 0, width);
	}

	public void fill(int color) {
		Arrays.fill(raster, color);
	}

	public void fill(int a, int r, int g, int b) {
		int color = b + (g << 8) + (r << 16) + (a << 24);
		Arrays.fill(raster, color);
	}
	public void setRGB(int x,int y, int srcX, int srcY, BitMap src){
		setRGB(x,y,src.getRGB(srcX, srcY));
	}
}
