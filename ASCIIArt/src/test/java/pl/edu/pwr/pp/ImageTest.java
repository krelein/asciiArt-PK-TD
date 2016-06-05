package pl.edu.pwr.pp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ImageTest {
	@Mock
	private BufferedImage buffImg;
	private Image image;
	private ScaleOption option;
	private ImagePath path;
	private ImageQuality quality;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		path = new ImagePath();
		path.pathType = PathType.Dir;
		path.path = new java.io.File("").getAbsolutePath() + "\\src\\test\\resources\\kolorTest.jpg";
		Mockito.when(buffImg.getWidth()).thenReturn(300);
		Mockito.when(buffImg.getHeight()).thenReturn(150);
		image = new Image();
	}

	@Test
	public void WidhtShouldBeBig() {

		Mockito.when(buffImg.getType()).thenReturn(BufferedImage.TYPE_BYTE_GRAY);
		option = ScaleOption.Big;
		BufferedImage result = image.scale(buffImg, option);
		Assert.assertThat(result.getWidth(), Matchers.is(Matchers.equalTo(option.getWidth())));

	}

	@Test
	public void WidhtShouldBeNormal() {

		Mockito.when(buffImg.getType()).thenReturn(BufferedImage.TYPE_BYTE_GRAY);
		option = ScaleOption.Normal;
		BufferedImage result = image.scale(buffImg, option);
		Assert.assertThat(result.getHeight(), Matchers.is(150));
		Assert.assertThat(result.getWidth(), Matchers.is(300));
	}

	@Test
	public void SizeShouldBeXXX() throws IOException, URISyntaxException {
		image.loadImage(path);
		option = ScaleOption.Small;
		quality = ImageQuality.Low;
		image.convertToAscii(quality, option);
		Font font = new Font("Serif", Font.PLAIN, 20);
		BufferedImage result = image.textToImage(font);
		Assert.assertThat(result.getHeight(), Matchers.is(1444));
		Assert.assertThat(result.getWidth(), Matchers.is(1520));
	}

	@Test
	public void ShouldBeGray() throws IOException, URISyntaxException {
		image.loadImage(path);
		BufferedImage result = image.convertToGray();
		Assert.assertThat(result.getType(), Matchers.is(BufferedImage.TYPE_BYTE_GRAY));
	}

	@Test
	public void ShouldReadPGMFromUrl() throws IOException, URISyntaxException {
		path.path = "http://agata.migalska.staff.iiar.pwr.wroc.pl/PP2016/obrazy/ziemia.pgm";
		path.pathType = PathType.Url;

		image.loadImage(path);

		assertThat(image.getImage(), is(instanceOf(BufferedImage.class)));

	}
	
	@Test
	public void ShouldReadFromUrl() throws IOException, URISyntaxException {
		path.path = "http://i1.kwejk.pl/k/obrazki/2016/06/14d999a960bda90e74dc46bc02f2b28d.jpg";
		path.pathType = PathType.Url;

		image.loadImage(path);

		assertThat(image.getImage(), is(instanceOf(BufferedImage.class)));

	}

}
