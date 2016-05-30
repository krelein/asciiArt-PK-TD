package pl.edu.pwr.pp;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.net.URISyntaxException;

public class PgmImageReader{

	static public BufferedImage loadImage(ImagePath imagePath) throws URISyntaxException
	{
		int [][] intensities;
		ImageFileReader imageFileReader = new ImageFileReader();
		intensities = imageFileReader.readPgmFile(imagePath);
		BufferedImage image = new BufferedImage(intensities[0].length, intensities.length, BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = image.getRaster();
		for (int y = 0; y < intensities.length; y++) {
			for (int x = 0; (x < intensities[0].length); x++) {
				raster.setSample(x, y, 0, intensities[y][x]);
			}
		}
		return image;
	}
	
}
