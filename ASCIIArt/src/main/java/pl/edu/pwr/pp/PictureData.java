package pl.edu.pwr.pp;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.imageio.ImageIO;

public class PictureData {
	static public String path;
	static public String Name = "wynik.txt";
	static public BufferedImage image;
	static public PictureType pictureType;
	static boolean canBeConverted = false;

	public static void LoadImage() throws IOException, URISyntaxException {
		canBeConverted = false;
		if (pictureType == PictureType.Dir) {
			loadImaeFromDrive();
		} else if (pictureType == PictureType.Url)
			image = ImageIO.read(new URL(path));
	}

	private static void loadImaeFromDrive() throws URISyntaxException, IOException {
		String[] ex = path.split("\\.");

		if (ex[ex.length - 1].equals("pgm")) // Jeśli obraz to PGM
		{
			ImageFileReader imageFileReader = new ImageFileReader();
			int[][] intensities = imageFileReader.readPgmFile(path);
			image = new BufferedImage(intensities[0].length, intensities.length, BufferedImage.TYPE_BYTE_GRAY);
			WritableRaster raster = image.getRaster();
			for (int y = 0; y < intensities.length; y++) {
				for (int x = 0; (x < intensities[0].length); x++) {
					raster.setSample(x, y, 0, intensities[y][x]);
				}
			}
			canBeConverted = true;
		} else {
			File file = new File(path);
			image = ImageIO.read(file);
		}
	}

}
