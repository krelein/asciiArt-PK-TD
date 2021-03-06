package pl.edu.pwr.pp;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.imageio.ImageIO;

public class Image {

	private BufferedImage image = null;
	private char[][] asciiImage = null;

	public BufferedImage getImage() {
		return this.image;
	}

	public char[][] getAsciiImage() {
		return this.asciiImage;
	}

	public void loadImage(ImagePath imagePath) throws IOException, URISyntaxException {

		String[] extenion = imagePath.path.split("\\.");
		if (extenion[extenion.length - 1].equals("pgm"))
			image = PgmImageReader.loadImage(imagePath);
		else if (imagePath.pathType == PathType.Dir)
			image = ImageIO.read(new File(imagePath.path));
		else if (imagePath.pathType == PathType.Url)
			image = ImageIO.read(new URL(imagePath.path));
	}

	public BufferedImage convertToGray() {
		BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = grayImage.getRaster();
		int grayIntensity = 0;
		for (int y = 0; y < image.getWidth(); ++y) {
			for (int x = 0; x < image.getHeight(); ++x) {
				Color color = new Color(image.getRGB(y, x));
				grayIntensity = (int) (0.2989 * color.getRed() + 0.5870 * color.getGreen() + 0.1140 * color.getBlue());
				raster.setSample(y, x, 0, grayIntensity);
			}
		}
		return grayImage;
	}

	public void convertToAscii(ImageQuality quality, ScaleOption scaleOption) {

		// konwersja do szarości i skalowanie
		WritableRaster raster;
		if (image.getType() == BufferedImage.TYPE_BYTE_GRAY) {
			raster = scale(image, scaleOption).getRaster();
		} else {
			raster = scale(convertToGray(), scaleOption).getRaster();
		}

		// konwersja do intów
		int[][] intensities = new int[raster.getHeight()][raster.getWidth()];
		for (int y = 0; y < raster.getHeight(); ++y) {
			for (int x = 0; x < raster.getWidth(); ++x) {
				intensities[y][x] = raster.getSample(x, y, 0);
			}
		}

		// konwersja do ASCII
		asciiImage = ImageConverter.intensitiesToAscii(intensities, quality);
	}

	public BufferedImage scale(BufferedImage buffImage, ScaleOption option) {
		int width, height;
		// sprawdzanie szerokości w zależności od wybranej opcji
		if (option == ScaleOption.ScreenSize) {
			width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		} else if (option != ScaleOption.Normal)
			width = option.getWidth();
		else
			return buffImage;
		// wyznaczanie wysokości
		height = (width * buffImage.getHeight()) / buffImage.getWidth();
		// skalowanie
		BufferedImage resizedImage = new BufferedImage(width, height, buffImage.getType());
		Graphics2D g = resizedImage.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(buffImage, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}

	private static final HashMap<RenderingHints.Key, Object> RenderingProperties = new HashMap<>();

	static{
	    RenderingProperties.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    RenderingProperties.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
	    RenderingProperties.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	}

	public BufferedImage textToImage(Font f) throws IOException{
	
	    //Calculate size of buffered image.
	    BufferedImage img = new BufferedImage(asciiImage[0].length*(f.getSize()-1), asciiImage.length*(f.getSize()-1), BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = img.createGraphics();
	    g2d.setRenderingHints(RenderingProperties);

	    g2d.setBackground(Color.WHITE);
	    g2d.setColor(Color.BLACK);

	    g2d.clearRect(0, 0, img.getWidth(), img.getHeight());
	    g2d.setFont(f);

	    for(int i=0; i<asciiImage.length; ++i){
	    	for(int j=0; j<asciiImage[0].length; ++j)
	    		g2d.drawString( String.valueOf(asciiImage[i][j]), j*(f.getSize()-1),i*(f.getSize()-1));
	    }
	    
	    g2d.dispose();

	   
	    return img;
	}
}
