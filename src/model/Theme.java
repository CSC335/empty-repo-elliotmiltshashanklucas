package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javafx.scene.image.Image;
import view.Observer;

public class Theme {
	private static Theme currentTheme;

	public static Theme getTheme() {
		if (currentTheme == null) {
			setUpThemes();
		}
		return currentTheme;
	}
	
	public static void setTheme(String newTheme) {
		// Starter Themes: Cats, Dogs, Planets, Fish, Jungle
		if(allThemes.get(newTheme) != null) {
			currentTheme = allThemes.get(newTheme);
		}
		notifyListeners();
	}

	private String name;
	private String background;
	private String cardBack;
	private List<String> images = new ArrayList<>();
	private boolean light = false;
	private String music;
	private String prefix = "file:images/";
	private static HashMap<String, Theme> allThemes = new HashMap<String, Theme>();
	private static List<Observer> observerList = new ArrayList<>();

	protected Theme(String themeName) {
		name = themeName;
	}

	public static void addObserver(Observer o) {
		observerList.add(o);
	}
	
	private static void notifyListeners() {
		for(Observer o : observerList) {
			o.update();
		}
	}
	/*
	 * protected Theme(String n, String bg, String esbg, String ssbg, String cb,
	 * boolean l, Theme t) { name = n; background = bg; endScreenBG = esbg;
	 * startScreenBG = ssbg; cardBack = cb; light = l; }
	 * 
	 * protected Theme(String n, String bg, String esbg, String ssbg, String cb,
	 * List<String> imgs, boolean l, Theme t) { name = n; background = bg;
	 * endScreenBG = esbg; startScreenBG = ssbg; cardBack = cb; light = l; images =
	 * imgs; Theme theme = t; }
	 */

	public String getName() {
		return name;
	}

	public Image getBackground() {
		return new Image(prefix + background);
	}

	public void setBackground(String b) {
		background = b;
		notifyListeners();
	}

	public Image getCardBack() {
		return new Image(prefix + cardBack);
	}

	public void setCardBack(String cb) {
		cardBack = cb;
		notifyListeners();
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

	public void addCard(String s) {
		images.add(s);
		notifyListeners();
	}

	public void setImages(List<String> i) {
		images = i;
		notifyListeners();
	}

	public boolean lightmode() {
		return light;
	}

	public void setLightmode(boolean b) {
		light = b;
		notifyListeners();
	}

	private static void setUpThemes() {
		// Cats Theme
		Theme t = new Theme("Cats");
		t.setBackground("backgroundImg.png");
		t.addCard("cat1.png");
		t.addCard("cat2.png");
		t.addCard("cat3.png");
		t.addCard("cat4.png");
		t.addCard("cat5.png");
		t.addCard("cat6.png");
		t.setCardBack("dogs10.png");
		allThemes.put("Cats", t);

		// Dogs Theme
		t = new Theme("Dogs");
		t.setBackground("backgroundImg.png");
		t.addCard("dogs11.png");
		t.addCard("dogs12.png");
		t.addCard("dogs13.png");
		t.addCard("dogs14.png");
		t.addCard("dogs15.png");
		t.addCard("dogs16.png");
		t.setCardBack("cat21.png");
		allThemes.put("Dogs", t);
		
		// Planets Theme
		t = new Theme("Planets");
		t.setBackground("backgroundImg.png");
		t.addCard("mercury.png");
		t.addCard("venus.png");
		t.addCard("earth.png");
		t.addCard("mars.png");
		t.addCard("jupiter.png");
		t.addCard("saturn.png");
		t.addCard("uranus.png");
		t.addCard("neptune.png");
		t.addCard("pluto.png");
		t.addCard("moon.png");
		t.setCardBack("planetsCardBack.png");
		allThemes.put("Planets", t);
		
		// Fish Theme
		t = new Theme("Fish");
		t.setBackground("backgroundImg.png");
		for(int i = 1; i<=10; i++) {
			t.addCard("fish"+i+".jpg");
		}
		t.setCardBack("fishCardBack.png");
		allThemes.put("Fish", t);
		
		// Jungle Theme
		t = new Theme("Jungle");
		t.setBackground("jungleBackground.png");
		for(int i = 1; i<=10; i++) {
			t.addCard("jungle"+i+".jpg");
		}
		t.setCardBack("jungleCardBack.png");
		allThemes.put("Jungle", t);
		
		currentTheme = allThemes.get("Cats");
	}

}
