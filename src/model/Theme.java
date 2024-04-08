package model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.image.Image;

public class Theme {
	private String name;
	private String background;
	private String endScreenBG;
	private String startScreenBG;
	private String cardBack;
	private List<String> images;
	private boolean light;
	private String music;
	private String prefix;

	public Theme(String n) {
		name = n;
	}

	public Theme(String n, String bg, String esbg, String ssbg, String cb, boolean l) {
		name = n;
		background = bg;
		endScreenBG = esbg;
		startScreenBG = ssbg;
		cardBack = cb;
		light = l;
	}

	public Theme(String n, String bg, String esbg, String ssbg, String cb, List<String> imgs, boolean l) {
		name = n;
		background = bg;
		endScreenBG = esbg;
		startScreenBG = ssbg;
		cardBack = cb;
		light = l;
		images = imgs;
	}

	public String getName() {
		return name;
	}

	public Image getBackground() {
		return new Image(prefix + background);
	}

	public void setBackground(String b) {
		background = b;
	}

	public Image getEndScreenBG() {
		return new Image(prefix + endScreenBG);
	}

	public void setEndScreenBG(String es) {
		endScreenBG = es;
	}

	public Image getStartScreenBG() {
		return new Image(prefix + startScreenBG);
	}

	public void setStartScreenBG(String ss) {
		startScreenBG = ss;
	}

	public Image getCardBack() {
		return new Image(prefix + cardBack);
	}

	public void setCardBack(String cb) {
		cardBack = cb;
	}

	public List<Image> getAllImages() {
		List<Image> imgs = new ArrayList<>();
		for (String s : images) {
			imgs.add(new Image(prefix + s));
		}
		return imgs;
	}

	public List<Image> getImages(int n) {
		Collections.shuffle(images);
		List<Image> imgs = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			imgs.add(new Image(prefix + images.get(i)));
		}
		return imgs;
	}
	
	public List<String> getImageStrings(int n) {
		Collections.shuffle(images);
		List<String> imgs = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			imgs.add(prefix + images.get(i));
		}
		return imgs;
	}

	public void setImages(List<String> i) {
		images = i;
	}

	public boolean lightmode() {
		return light;
	}

	public void setLightmode(boolean b) {
		light = b;
	}

}
