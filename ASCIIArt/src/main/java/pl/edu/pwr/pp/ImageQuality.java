package pl.edu.pwr.pp;

public enum ImageQuality {
	Low(10, 25), High(70, 3);

	private final int charNumber;
	private final int step;

	ImageQuality(int x, int s) {
		this.charNumber = x;
		this.step = s;
	}

	public int getCharNumber() {
		return charNumber;
	}

	public int getStep() {
		return step;
	}
}
