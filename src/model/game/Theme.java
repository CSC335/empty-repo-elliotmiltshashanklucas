package model.game;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import interfaces.Observer;
import javafx.scene.image.Image;

/**
 * The Theme class holds GUI information associated with various themes
 */
public class Theme {
	private static Theme currentTheme;

	 /**
     * Retrieves the current theme.
     *
     * @return the current theme
     */
	public static Theme getTheme() {
		if (currentTheme == null) {
			setUpThemes();
		}
		return currentTheme;
	}
	
	/**
     * Sets the theme to the specified one.
     *
     * @param newTheme - the name of the new theme
     */
	public static void setTheme(String newTheme) {
		// Starter Themes: Cats, Dogs, Planets, Fish, Jungle
		if (allThemes.get(newTheme) != null) {
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
	private String prefix = "file:";
	private static HashMap<String, Theme> allThemes = new HashMap<String, Theme>();
	private static List<Observer> observerList = new ArrayList<>();

	/**
     * Constructs a Theme object with the specified name.
     *
     * @param themeName - the name of the theme
     */
	protected Theme(String themeName) {
		name = themeName;
	}

	/**
     * Adds an observer to the list of observers.
     *
     * @param o - the observer to add
     */
	public static void addObserver(Observer o) {
		observerList.add(o);
	}

	
	/**
     * Notifies theme listeners, called when theme changes
     */
	private static void notifyListeners() {
		for (Observer o : observerList) {
			o.update();
		}
	}

	/**
     * Retrieves the name of the theme.
     *
     * @return the name of the theme
     */
	public String getName() {
		return name;
	}

	/**
     * Retrieves the background image of the theme.
     *
     * @return the background image
     */
	public Image getBackground() {
		return new Image(prefix + background);
	}

	/**
     * Sets the background image of the theme.
     *
     * @param b - the filename of the background image
     */
	public void setBackground(String b) {
		background = b;
		notifyListeners();
	}

	/**
     * Retrieves the card back image of the theme.
     *
     * @return the card back image
     */
	public Image getCardBack() {
		return new Image(prefix + cardBack);
	}

	/**
     * Sets the card back image of the theme.
     *
     * @param cb - the filename of the card back image
     */
	public void setCardBack(String cb) {
		cardBack = cb;
		notifyListeners();
	}

	/**
     * Retrieves all images associated with the theme.
     *
     * @return a list of all images
     */
	public List<Image> getAllImages() {
		List<Image> imgs = new ArrayList<>();
		for (String s : images) {
			imgs.add(new Image(prefix + s));
		}
		return imgs;
	}

	/**
     * Retrieves a specified number of random images associated with the theme.
     *
     * @param n - the number of images to retrieve
     * @return a list of random images
     */
	public List<Image> getImages(int n) {
		Collections.shuffle(images);
		List<Image> imgs = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			imgs.add(new Image(prefix + images.get(i)));
		}
		return imgs;
	}

	/**
     * Retrieves a specified number of random image filenames associated with the theme.
     *
     * @param n - the number of image filenames to retrieve
     * @return a list of random image filenames
     */
	public List<String> getImageStrings(int n) {
		Collections.shuffle(images);
		List<String> imgs = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			imgs.add(prefix + images.get(i));
		}
		return imgs;
	}

	/**
     * Adds a new image filename to the theme.
     *
     * @param s - the filename of the image to add
     */
	public void addCard(String s) {
		images.add(s);
		notifyListeners();
	}
	
	/**
     * Sets the list of image filenames for the theme.
     *
     * @param i - the list of image filenames to set
     */
	public void setImages(List<String> i) {
		images = i;
		notifyListeners();
	}

	/**
     * Checks if the theme is in light mode.
     *
     * @return true if the theme is in light mode, false otherwise
     */
	public boolean lightmode() {
		return light;
	}

	/**
     * Sets the light mode of the theme.
     *
     * @param b - the light mode to set
     */
	public void setLightmode(boolean b) {
		light = b;
		notifyListeners();
	}

	public static Set<String> getThemes() {
		return allThemes.keySet();
	}
	public static Theme getTheme(String s) {
		return allThemes.get(s);
	}

	private static void setUpThemes() {
		File dir = new File("Images");
		for(File folder : dir.listFiles()) {
			if(!folder.isDirectory()) continue;
			Theme t = new Theme(folder.getName());
			for(File f : folder.listFiles()) {
				switch(f.getName().toUpperCase()) {
				case "BACKGROUND":
					t.setBackground(f.listFiles()[0].getPath());
					break;
				case "CARDBACK":
					t.setCardBack(f.listFiles()[0].getPath());
					break;
				case "FACES":
					for(File face : f.listFiles())
						t.addCard(face.getPath());
				default:
					continue;
				}
				
				
			}
			allThemes.put(t.getName(), t);
		}
		currentTheme = allThemes.get("Cats");
	}

}
