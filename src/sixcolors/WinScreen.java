package sixcolors;

import java.awt.Font;

import edu.princeton.cs.introcs.StdDraw;

/* Classe : WinScreen
 * But : affiche l'écran de victoire
 * 
 * 
 * */
public class WinScreen {
	private Board b;
	private int wh = 640 + 100;// window width in pixel
	private int hh = 640;// window height in pixel
	private int buttonSizeH = 40;// height
	private int buttonSizeW = 300;// width
	private int[] hP;// hats
	private int won;
	private String[] texture = { "images/back_texture.png", "images/button_texture.png", "images/button_texture2.png" };
	private String[] visage = { "images/eye1.png", "images/eye2.png", "images/eye3.png", "images/eye4.png",
			"images/eye5.png", "images/eye6.png" };
	private String[] h = { "images/hat1.png", "images/hat2.png", "images/hat3.png", "images/hat4.png",
			"images/hat5.png", "images/hat6.png", "images/hat7.png", "images/hat8.png", "images/hat9.png",
			"images/hat10.png", "images/hat11.png" };
	private String[] t = { "images/avatarbigre.png", "images/avatarbigre.png", "images/avatarbigre.png" };

	public WinScreen(Board bb, int wonP, int[] hats) {
		this.b = bb;
		this.hP = hats;
		this.won = wonP;
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
		boolean click = false;
		// bouton menu
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(buttonW / 4, buttonH / 3, buttonW / 8, buttonH / 6);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(buttonW / 4, buttonH / 3 - 3, "Menu");
		// won
		StdDraw.picture(this.wh / 4, this.hh / 2, this.t[0], this.wh * 0.16, this.wh * 0.16);
		StdDraw.picture(this.wh / 4, this.hh / 2, this.visage[5], this.wh * 0.16, this.wh * 0.16);
		if (this.hP[this.won - 1] != -1)
			StdDraw.picture(this.wh / 4, this.hh / 2, this.h[this.hP[this.won - 1] ], this.wh * 0.16,
					this.wh * 0.16);
		StdDraw.picture(this.wh / 4, this.hh / 2 - hh * 0.07, "images/cake.png", this.hh * 0.2, this.hh * 0.2);
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.text(this.wh / 2, this.hh / 4, "Player " + this.won + " won !");

		for (int i = 0; i < this.b.getNbPlayer(); i++) {
			if (i != this.won - 1) {
				StdDraw.picture(3 * this.wh / 4 + i * this.wh * 0.04, this.hh / 2, this.t[0], this.wh * 0.16,
						this.wh * 0.16, 90 * i);
				StdDraw.picture(3 * this.wh / 4 + i * this.wh * 0.04, this.hh / 2, this.visage[4], this.wh * 0.16,
						this.wh * 0.16, 90 * i);
				if (this.hP[i] != -1)
					StdDraw.picture(3 * this.wh / 4 + i * this.wh * 0.04, this.hh / 2, this.h[this.hP[i]],
							this.wh * 0.16, this.wh * 0.16, 90 * i);
			}
		}
		StdDraw.show(0);
		while (!click) {
			double xn = StdDraw.mouseX();
			double yn = StdDraw.mouseY();

			if (yn > buttonH / 3 - buttonH / 6 && yn < buttonH / 3 + buttonH / 6 && xn > buttonW / 4 - buttonW / 8
					&& xn < buttonW / 4 + buttonW / 8) {// menu button
				if (StdDraw.mousePressed()) {
					click = true;
				}
			}
		}
		// quit
		String[] t = { "Solo", "Multijoueur", "Editeur" };
		int[] i = { 2, 3, 17 };
		Menu aMain = new Menu("Menu Principal", t, i);
		aMain.showMenu();
	}

}
