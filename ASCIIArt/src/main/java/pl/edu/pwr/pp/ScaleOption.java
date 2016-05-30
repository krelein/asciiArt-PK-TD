package pl.edu.pwr.pp;

public enum ScaleOption {
	Small(80), Big(160), ScreenSize(0), Normal(0);

	private final int width;

	ScaleOption(int x) {
		this.width = x;
	}

	public int getWidth() {
		return width;
	}
}
