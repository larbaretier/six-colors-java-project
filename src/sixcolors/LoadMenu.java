package sixcolors;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import edu.princeton.cs.introcs.StdDraw;

/* Classe : LoadMenu
 * But : Charger une partie sauvegardée ou une carte fabriquée
 * 
 * 
 * */
public class LoadMenu {
	private int wh = 640 + 100;// window width in pixel
	private int hh = 640;// window height in pixel
	private int mw = 0;// map size (width)
	private int mh = 0;// map size (height)
	private int buttonSizeH = 40;// height
	private int buttonSizeW = 300;// width
	private boolean[] here = { false, false, false, false };
	private boolean[] diamond = { false, false, false, false };
	private int[] forme = { 1, 1, 1, 1 };
	private int[] hl = { 1, 1, 1, 1 };
	private int[] wl = { 1, 1, 1, 1 };
	private int[] nbP = { 2, 2, 2, 2 };
	private int start = 0;
	private String[] texture = { "images/back_texture.png", "images/button_texture.png", "images/button_texture2.png" };
	private boolean editor = false;
	private boolean lan = false;

	public LoadMenu(boolean e, boolean l) {
		this.editor = e;
		this.lan = l;
	}

	public void menu() {
		boolean act = false;
		int choice = 0;
		int buttonH = this.hh / 6;
		int buttonW = 3 * this.wh / 4;
		while (!act) {
			getSave();
			afficher();
			double xn = StdDraw.mouseX();
			double yn = StdDraw.mouseY();

			StdDraw.filledRectangle(buttonW / 4, buttonH / 3, buttonW / 8, buttonH / 6);
			if (yn > buttonH / 3 - buttonH / 6 && yn < buttonH / 3 + buttonH / 6 && xn > buttonW / 4 - buttonW / 8
					&& xn < buttonW / 4 + buttonW / 8) {// menu button
				if (StdDraw.mousePressed()) {
					String[] tf = { "Start" };
					int[] imf = { 1 };
					Menu mf = new Menu("", tf, imf);
					mf.actionEffect(1);
				}
			}
			if (yn > this.hh - this.hh / 12 && xn > this.wh / 2 - this.wh / 10 && xn < this.wh / 2 + this.wh / 10) {// up
																													// arrow
				if (StdDraw.mousePressed())
					this.start++;
			}
			if (this.start > 0 && yn < this.hh / 12 && xn > this.wh / 2 - this.wh / 10
					&& xn < this.wh / 2 + this.wh / 10) {// down arrow
				if (StdDraw.mousePressed())
					this.start--;
			}
			for (int i = 0; i < 4; i++) {
				if (xn < this.wh / 2 + buttonW / 2 && xn > this.wh / 2 - buttonW / 2
						&& yn < buttonH * (i + 1.5) + buttonH / 2 && yn > buttonH * (i + 1.5) - buttonH / 2) {
					if (StdDraw.mousePressed() && this.here[i]) {
						choice = i;
						act = true;
					}
				}
			}

		}
		Board b = new Board();
		int i = choice;
		try {
			String a = "";
			if (this.editor) {
				a = "myBoard";
			} else {
				a = "savedGame";
			}
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(a + (i + this.start) + ".object"));
			b = (Board) ois.readObject();
			ois.close();
			this.mw = b.getWidth();
			this.mh = b.getHeight();
			String[] t = { "Start" };
			int[] im = { 1 };
			Menu m = new Menu("", t, im);
			m.setMh(this.mh);
			m.setMw(this.mw);
			m.setMapLoad(i + this.start);
			if (this.editor) {
				if (b.getBigBoard()[0].getBox()[b.getBigBoard()[0].getBox().length - 1].getProp() == 4) {// if
																											// 4
																											// player
																											// map
					m.setNbPlayer(4);
					if (!lan) {
						m.actionEffect(18);
					} else {
						m.actionEffect(96);
					}

				} else {
					m.setNbPlayer(2);
					if (!lan) {
						m.actionEffect(19);
					} else {
						m.actionEffect(97);
					}

				}
			} else {
				if (b.getBigBoard()[0].getBox()[b.getBigBoard()[0].getBox().length - 1].getProp() == 4) {// if
																											// 4
																											// player
																											// map
					m.setNbPlayer(4);
					m.actionEffect(11);
				} else {
					m.setNbPlayer(2);
					m.actionEffect(12);
				}
			}

		} catch (ClassNotFoundException exception) {
			System.out.println("Impossible de lire l'objet : " + exception.getMessage());
		} catch (IOException exception) {
			System.out.println("Erreur lors de l'écriture : " + exception.getMessage());
		}

	}

	public void afficher() {
		Font font = new Font("Arial", Font.BOLD, this.buttonSizeH - 2);
		Font font2 = new Font("Arial", Font.BOLD, this.buttonSizeH / 2 - 2);
		StdDraw.setFont(font);
		int buttonH = this.hh / 6;
		int buttonW = 3 * this.wh / 4;
		StdDraw.setXscale(0, this.wh);
		StdDraw.setYscale(0, this.hh);
		StdDraw.picture(this.wh / 2, this.hh / 2, texture[0], this.wh, this.hh);
		StdDraw.setPenColor(StdDraw.WHITE);
		// draw arrows
		double[] x = { this.wh / 2, this.wh / 2 + this.wh / 10, this.wh / 2 - this.wh / 10 };
		double[] y = { this.hh, this.hh - this.hh / 12, this.hh - this.hh / 12 };
		StdDraw.filledPolygon(x, y);
		double[] y2 = { 0, this.hh / 12, this.hh / 12 };
		StdDraw.filledPolygon(x, y2);
		// draw menu button
		StdDraw.filledRectangle(buttonW / 4, buttonH / 3, buttonW / 8, buttonH / 6);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(buttonW / 4, buttonH / 3 - 3, "Menu");
		// draw square selections
		for (int i = 0; i < 4; i++) {
			StdDraw.setPenColor(StdDraw.BLUE);
			if (i % 2 == 0) {
				StdDraw.setPenColor(StdDraw.BOOK_BLUE);
			}
			StdDraw.filledRectangle(this.wh / 2, buttonH * (i + 1.5), buttonW / 2, buttonH / 2);
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.setFont(font);
			StdDraw.text(this.wh / 4, buttonH * (i + 1.5), "" + (i + this.start));
			if (this.here[i]) {
				StdDraw.setPenColor(StdDraw.WHITE);
				StdDraw.setFont(font2);
				String a = "";
				a += this.wl[i];
				a += "x";
				a += this.hl[i];
				a += " ";
				if (this.forme[i] == 1 && !this.diamond[i]) {
					a += "Square";
				}
				if (this.forme[i] == 1 && this.diamond[i]) {
					a += "Diamond";
				}
				if (this.forme[i] == 3) {
					a += "Hexa";
				}
				a += " for ";
				a += this.nbP[i];
				a += " players";
				StdDraw.textLeft(this.wh / 4 + this.wh / 10, buttonH * (i + 1.5), a);
				StdDraw.setPenColor(StdDraw.BOOK_RED);
				StdDraw.filledRectangle(this.wh / 2 + buttonW / 2 - buttonW / 10, buttonH * (i + 1.5), buttonW / 20,
						buttonW / 20);
				StdDraw.setPenColor(StdDraw.WHITE);
				StdDraw.text(this.wh / 2 + buttonW / 2 - buttonW / 10, buttonH * (i + 1.5), "Play");

			} else {
				StdDraw.setPenColor(StdDraw.WHITE);
				StdDraw.setFont(font);
				StdDraw.text(this.wh / 2, buttonH * (i + 1.5), "No data");

				StdDraw.setFont(font2);
				StdDraw.setPenColor(StdDraw.DARK_GRAY);
				StdDraw.filledRectangle(this.wh / 2 + buttonW / 2 - buttonW / 10, buttonH * (i + 1.5), buttonW / 20,
						buttonW / 20);
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.text(this.wh / 2 + buttonW / 2 - buttonW / 10, buttonH * (i + 1.5), "Play");
			}
		}

		StdDraw.show(0);
	}

	public void getSave() {
		for (int i = 0; i < 4; i++) {
			Board save = new Board();
			try {
				String a = "";
				if (this.editor) {
					a = "myBoard";
				} else {
					a = "savedGame";
				}
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(a + (i + this.start) + ".object"));
				save = (Board) ois.readObject();
				ois.close();
				this.here[i] = true;
				this.forme[i] = save.getForme();
				this.hl[i] = save.getHeight();
				this.wl[i] = save.getWidth();
				this.nbP[i] = save.getNbPlayer();
				this.diamond[i] = save.isDiamond();
			} catch (ClassNotFoundException exception) {
				this.here[i] = false;
			} catch (IOException exception) {
				this.here[i] = false;
			}
		}
	}

}
