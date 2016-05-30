/**
* @author Louis
* Menus
*/
package sixcolors;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.IOException;

import edu.princeton.cs.introcs.StdDraw;

/* Classe : Menu
 * But : Afficher les menus et gérer leurs actions
 * 
 * 
 * */

public class Menu {
	private String[] texture = { "images/back_texture.png", "images/button_texture.png", "images/button_texture2.png" };
	private String[] tScreen = { "images/logod1.png", "images/logod2.png", "images/logod3.png", "images/logod4.png",
			"images/logod5.png", "images/logod6.png" };
	private String title;
	private String[] name;// name of the buttons
	private int[] action;// their action
	private String image;
	private String background;
	private int wh = 640 + 100;// window width in pixel
	private int hh = 640;// window height in pixel
	private int buttonSizeH = 40;// height
	private int buttonSizeW = 300;// width
	private int space = 15;
	private int[] hat;
	private int[] being = { -1, -1, -1, -1 };
	private int nbPlayer = 2;
	private int mh = 7;
	private int mw = 7;
	private int forme = 1;// 1:square,2:diamond,3:hexa
	private boolean diamond = false;
	private boolean entourer = true;// regle entourer
	private int mapLoad = 0;
	public static boolean servor = false;
	private boolean brouillard = false;

	public Menu(String t, String[] n, int[] a) {
		this.action = a;
		this.name = n;
		this.title = t;
	}

	public Menu(String t, String[] n, int[] a, int bw) {
		this.action = a;
		this.name = n;
		this.title = t;
		this.buttonSizeW = bw;
	}

	public void showMenu() {
		if (Game.song != 0) {
			StdDraw.setXscale(0, this.wh);
			StdDraw.setYscale(0, this.hh);
			StdDraw.picture(this.wh / 2, this.hh / 2, texture[0], this.wh, this.hh);
			int konami = 0;
			// game title if start
			if (this.name[0] == "Start") {
				for (int i = 0; i < this.tScreen.length; i++) {
					StdDraw.picture(this.wh / 2, this.hh / 2 + this.hh / 4, this.tScreen[i], this.wh / 2, this.wh / 4);
				}

			}
			// title
			Font fontT = new Font("Arial", Font.BOLD, (this.buttonSizeH - 2) + (this.buttonSizeH - 2) / 2);
			StdDraw.setFont(fontT);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.text(this.wh / 2, this.hh - (this.buttonSizeH - 2) * 1.5, this.title);
			// buttons
			Font font = new Font("Arial", Font.BOLD, this.buttonSizeH - 2);
			int l = this.name.length;
			int start = this.hh / 2 + l / 2 * buttonSizeH + space * (l / 2 - 1);// where
																				// to
																				// start
																				// to
																				// draw
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.setFont(font);
			for (int i = 0; i < l; i++) {
				StdDraw.picture(this.wh / 2, start - i * this.buttonSizeH - i * space, texture[1], this.buttonSizeW,
						this.buttonSizeH);
				StdDraw.rectangle(this.wh / 2, start - i * this.buttonSizeH - i * space, this.buttonSizeW / 2,
						this.buttonSizeH / 2);
				StdDraw.text(this.wh / 2, start - i * this.buttonSizeH - i * space - 2, this.name[i]);
			}

			// overlay and click
			boolean acted = false;
			int stopped = 0;
			while (!acted && Game.song != 0) {
				if (this.name[0] == "Start") {// konami code
					konami = konamiC(konami);
					if (konami == 10) {
						StdDraw.picture(this.wh / 2, this.hh * 0.25, "images/cake.png", this.hh * 0.2, this.hh * 0.2);
						StdDraw.text(this.wh / 2, this.hh * 0.15, "The cake is a lie");
					}
				}
				double xn = StdDraw.mouseX();
				double yn = StdDraw.mouseY();
				for (int i = 0; i < l; i++) {
					if (xn < this.wh / 2 + this.buttonSizeW / 2 && xn > this.wh / 2 - this.buttonSizeW / 2
							&& yn > start - (i + 0.7) * this.buttonSizeH - i * space
							&& yn < start - (i - 0.7) * this.buttonSizeH - i * space) {
						StdDraw.setPenColor(StdDraw.RED);
						StdDraw.picture(this.wh / 2, start - i * this.buttonSizeH - i * space, texture[2],
								this.buttonSizeW, this.buttonSizeH);
						StdDraw.rectangle(this.wh / 2, start - i * this.buttonSizeH - i * space, this.buttonSizeW / 2,
								this.buttonSizeH / 2);
						StdDraw.text(this.wh / 2, start - i * this.buttonSizeH - i * space - 2, this.name[i]);
						if (StdDraw.mousePressed()) {// if pressed
							StdDraw.setPenColor(StdDraw.ORANGE);
							StdDraw.rectangle(this.wh / 2, start - i * this.buttonSizeH - i * space,
									this.buttonSizeW / 2, this.buttonSizeH / 2);
							// make action
							stopped = i;
							acted = true;
						}
					} else {
						StdDraw.setPenColor(StdDraw.BLUE);
						StdDraw.picture(this.wh / 2, start - i * this.buttonSizeH - i * space, texture[1],
								this.buttonSizeW, this.buttonSizeH);
						StdDraw.rectangle(this.wh / 2, start - i * this.buttonSizeH - i * space, this.buttonSizeW / 2,
								this.buttonSizeH / 2);
						StdDraw.text(this.wh / 2, start - i * this.buttonSizeH - i * space - 2, this.name[i]);
					}
				}
				StdDraw.show(0);
			}
			// action made
			if (Game.song != 0) {
				StdDraw.show(0);
				this.actionEffect(this.action[stopped]);
			}
		}
	}

	public void actionEffect(int act) {
		// 1.5sec d'attente pour pas de clicks non voulus
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		switch (act) {
		case 1:// main menu
			Game.song = 2;
			String[] t = { "Solo", "Multijoueur", "Editeur" };
			int[] i = { 2, 3, 17 };
			Menu aMain = new Menu("Menu Principal", t, i);
			aMain.showMenu();
			break;
		case 2:// Solo
			String[] ts = { "Nouvelle Partie", "Sauvegarde", "Carte Crée", "Retour" };
			int[] is = { 15, 9, 8, 1 };
			Menu aSolo = new Menu("Mode de Jeu", ts, is);
			aSolo.showMenu();
			break;
		case 3:// Multijoueur
			String[] tm = { "1 Ordinateur", "LAN", "Retour" };
			int[] im = { 15, 4, 1 };
			Menu mMain = new Menu("Mode de Multi", tm, im);
			mMain.showMenu();
			break;
		case 4:// LAN
			String[] tl = { "Hôte", "Client", "Retour" };
			int[] il = { 100, 101, 3 };
			Menu lMain = new Menu("Mode de Multi", tl, il);
			lMain.showMenu();
			break;
		case 5:// Multi choix hote
			String[] th = { "Nouvelle Carte", "Carte Crée", "Retour" };
			int[] ih = { 91, 95, 4 };
			Menu hMain = new Menu("Mode de Multi", th, ih);
			hMain.showMenu();
			break;
		case 7:// jouer 1vs1 IA ou vs3
			this.title = "";
			Gamu g = new Gamu(this.hat);
			g.setEntourer(this.entourer);
			g.setForme(this.forme);
			g.setDiamond(this.diamond);
			if (this.nbPlayer == 2) {
				g.startiaGen(this.mw, this.mh, this.being, false, false);
			} else {
				g.startiaGen(this.mw, this.mh, this.being, false, false);
			}
			break;
		case 8:// jouer game editor
			LoadMenu l2 = new LoadMenu(true, false);
			l2.menu();

			break;
		case 9:// jouer sauvegarde
			LoadMenu l = new LoadMenu(false, false);
			l.menu();

			break;
		case 10:// jouer 1vs3 IA
			Gamu gss = new Gamu(this.hat);
			gss.setEntourer(this.entourer);
			gss.setForme(this.forme);
			gss.setDiamond(this.diamond);
			break;
		case 11:// option sauvegarde a 4
			String[] tad = { "P1", "P2", "P3", "P4" };
			this.name = tad;
			this.buttonSizeW = 150;
			this.title = "Joueurs";
			this.nbPlayer = 4;
			this.showPlayer(4, true, 3, true, false, false);
			break;
		case 12:// option sauvegarde a 2
			String[] tac = { "P1", "P2", "P3", "P4" };
			this.name = tac;
			this.buttonSizeW = 150;
			this.title = "Joueurs";
			this.showPlayer(2, true, 3, true, false, false);
			break;
		case 13:// jouer sauvegarde a 4
			Gamu gss1 = new Gamu(this.hat);
			gss1.setForme(this.forme);
			gss1.setDiamond(this.diamond);
			gss1.setMapLoad(this.mapLoad);
			gss1.startiaGen(this.mw, this.mh, this.being, false, true);
			break;
		case 14:// jouer sauvegarde a 2
			Gamu gss2 = new Gamu(this.hat);
			gss2.setForme(this.forme);
			gss2.setDiamond(this.diamond);
			gss2.setMapLoad(this.mapLoad);
			gss2.startiaGen(this.mw, this.mh, this.being, false, true);
			break;
		// ----coler menus
		case 15:// map pour vs IA
			this.buttonSizeW = 150;
			this.title = "Options";
			this.showMap(true, true, false, false);
			break;
		case 16:// char pour vs IA
			String[] tab = { "P1", "P2", "P3", "P4" };
			this.name = tab;
			this.buttonSizeW = 150;
			this.title = "Joueurs";
			this.showPlayer(this.nbPlayer, true, 3, false, false, false);
			break;
		case 17:// map pour editor
			this.buttonSizeW = 150;
			this.title = "Options";
			this.showMap(true, true, true, false);
			break;
		case 18:// option editor a 4
			String[] taa = { "P1", "P2", "P3", "P4" };
			this.name = taa;
			this.buttonSizeW = 150;
			this.title = "Joueurs";
			this.nbPlayer = 4;
			this.showPlayer(4, true, 3, false, true, false);
			break;
		case 19:// option editor a 2
			String[] ta = { "P1", "P2", "P3", "P4" };
			this.name = ta;
			this.buttonSizeW = 150;
			this.title = "Joueurs";
			this.nbPlayer = 2;
			this.showPlayer(2, true, 3, false, true, false);
			break;
		case 20:// jouer editor a 4
			Gamu gss1d = new Gamu(this.hat);
			gss1d.setForme(this.forme);
			gss1d.setDiamond(this.diamond);
			gss1d.setMapLoad(this.mapLoad);
			gss1d.startiaGen(7, 7, this.being, true, false);
			break;
		case 21:// jouer editor a 2
			Gamu gss2d = new Gamu(this.hat);
			gss2d.setForme(this.forme);
			gss2d.setDiamond(this.diamond);
			gss2d.setMapLoad(this.mapLoad);
			gss2d.startiaGen(this.mw, this.mh, this.being, true, false);
			break;
		case 22:// creer editor
			Gamu gss2de = new Gamu(this.hat);
			gss2de.setForme(this.forme);
			gss2de.setDiamond(this.diamond);
			gss2de.setEntourer(this.entourer);
			gss2de.editor(this.mw, this.mh, false, this.nbPlayer);
			break;
		case 90:// hote
			this.title = "";
			Gamu gs = new Gamu(this.hat);
			gs.setEntourer(this.entourer);
			gs.setForme(this.forme);
			gs.setDiamond(this.diamond);
			gs.setBrouillard(this.brouillard);
			if (this.nbPlayer == 2) {
				gs.startiaMult(this.mw, this.mh, this.being, false, false, true, true, false, null, null);
			} else {
				gs.startiaMult(this.mw, this.mh, this.being, false, false, true, true, false, null, null);
			}
			break;
		case 91:// map pour multi vs IA
			this.buttonSizeW = 150;
			this.title = "Options";
			this.showMap(true, true, false, true);
			break;
		case 92:// char pour vs IA
			String[] tabs = { "P1", "P2", "P3", "P4" };
			this.name = tabs;
			this.buttonSizeW = 150;
			this.title = "Joueurs";
			this.showPlayer(this.nbPlayer, true, 3, false, false, true);
			break;
		case 93:// jouer editor a 4
			Gamu gss1ds = new Gamu(this.hat);
			gss1ds.setForme(this.forme);
			gss1ds.setDiamond(this.diamond);
			gss1ds.setMapLoad(this.mapLoad);
			gss1ds.setBrouillard(this.brouillard);
			gss1ds.startiaMult(this.mw, this.mh, this.being, true, false, true, true, false, null, null);
			break;
		case 94:// jouer editor a 2
			Gamu gss2ds = new Gamu(this.hat);
			gss2ds.setForme(this.forme);
			gss2ds.setDiamond(this.diamond);
			gss2ds.setMapLoad(this.mapLoad);
			gss2ds.setBrouillard(this.brouillard);
			gss2ds.startiaMult(this.mw, this.mh, this.being, true, false, true, true, false, null, null);
			break;
		case 95:// jouer game editor multi
			LoadMenu l2s = new LoadMenu(true, true);
			l2s.menu();

			break;
		case 96:// option editor a 4
			String[] taas = { "P1", "P2", "P3", "P4" };
			this.name = taas;
			this.buttonSizeW = 150;
			this.title = "Joueurs";
			this.nbPlayer = 4;
			this.showPlayer(4, true, 3, false, true, true);
			break;
		case 97:// option editor a 2
			String[] tas = { "P1", "P2", "P3", "P4" };
			this.name = tas;
			this.buttonSizeW = 150;
			this.title = "Joueurs";
			this.nbPlayer = 2;
			this.showPlayer(2, true, 3, false, true, true);
			break;
		case 99:// client
			FileClient.main(false, true);
			int[] hat = { 1, 1, 1, 1 };
			Gamu gss2ss = new Gamu(hat);
			int[] being = { 0, 0, 0, 0 };
			gss2ss.startiaMult(7, 7, being, false, false, false, false, false, null, null);

			break;
		case 100:// host
			servor = true;
			IpSet ipp = new IpSet();
			ipp.mainSet(true);
			break;
		case 101:// client
			servor = false;
			IpSet ipps = new IpSet();
			ipps.mainSet(false);
			break;
		default:
			break;
		}
	}

	public void showPlayer(int nbp, boolean allOption, int IA, boolean save, boolean editor, boolean lan) {
		StdDraw.clear(StdDraw.GRAY);
		StdDraw.setXscale(0, this.wh);
		StdDraw.setYscale(0, this.hh);
		// Player variable
		int[] playerHat = { -1, 5, 2, 0 };
		int[] playerBeing = { 0, 2, -1, -1 };// humain si 0 sinon IA
		if (this.nbPlayer == 4) {
			playerBeing[3] = 4;
			playerBeing[2] = 3;
		}
		String[] titleS = { "Humain", "IA Débile", "IA Agressive", "IA Sournoise", "IA Douée" };
		// title
		Font fontT = new Font("Arial", Font.BOLD, (this.buttonSizeH - 2) + (this.buttonSizeH - 2) / 2);
		StdDraw.setFont(fontT);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(this.wh / 2, this.hh - (this.buttonSizeH - 2) * 1.5, this.title);

		// player buttons
		Font font = new Font("Arial", Font.BOLD, this.buttonSizeH - 2);
		Font font2 = new Font("Arial", Font.BOLD, this.buttonSizeH / 2 + 2);
		int start = this.wh / 2 - nbp / 2 * buttonSizeW + space * (nbp / 2 - 1);// where
																				// to
																				// start
																				// to
																				// draw
		StdDraw.setPenColor(StdDraw.BLUE);
		String[] t = { "images/avatarbigre.png", "images/avatarbigre.png", "images/avatarbigre.png" };
		String[] h = Animation.h;
		double xd = 0;
		double yd = 0;
		for (int i = 0; i < nbp; i++) {
			if (i == 0) {
				StdDraw.setPenColor(StdDraw.BLUE);
			} else if (i == 1) {
				StdDraw.setPenColor(StdDraw.RED);
			} else if (i == 2) {
				StdDraw.setPenColor(StdDraw.GREEN);
			} else if (i == 3) {
				StdDraw.setPenColor(StdDraw.MAGENTA);
			}
			// image
			xd = start + i * this.buttonSizeW + i * space;
			yd = this.hh / 2 + this.buttonSizeH + this.buttonSizeH * 2;
			StdDraw.picture(xd, yd, t[0], this.buttonSizeW / 2, this.buttonSizeH * 2, 0);

			if (playerHat[i] != -1) {
				StdDraw.picture(xd, yd, h[playerHat[i]], this.buttonSizeW / 2, this.buttonSizeH * 2, 0);
			}
			if ((i != 1 && i != 0) || !lan) {
				// text niveau IA/humain
				StdDraw.setFont(font2);
				StdDraw.text(xd, this.hh / 2 + this.buttonSizeH, titleS[playerBeing[i]]);
			}
			// text P1...P4
			StdDraw.setFont(font);
			StdDraw.rectangle(xd, this.hh / 2, this.buttonSizeW / 2, this.buttonSizeH / 2);
			StdDraw.text(xd, this.hh / 2 - 5, this.name[i]);
			// arrows
			double[] xx = { xd - this.buttonSizeW / 4 - 10, xd - this.buttonSizeW / 4, xd - this.buttonSizeW / 4 };
			double[] yy = { yd, yd - 10, yd + 10 };
			StdDraw.polygon(xx, yy);
			double[] xx2 = { xd + this.buttonSizeW / 4 + 10, xd + this.buttonSizeW / 4, xd + this.buttonSizeW / 4 };
			StdDraw.polygon(xx2, yy);
		}

		// start button
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(this.wh / 2, this.buttonSizeH, this.buttonSizeW / 2, this.buttonSizeH / 2);
		StdDraw.text(this.wh / 2, this.buttonSizeH - 5, "Start");

		// overlay and click
		boolean acted = false;
		while (!acted) {
			StdDraw.setFont(font2);
			double xn = StdDraw.mouseX();
			double yn = StdDraw.mouseY();
			for (int i = 0; i < nbp; i++) {

				if (i == 0) {
					StdDraw.setPenColor(StdDraw.BLUE);
				} else if (i == 1) {
					StdDraw.setPenColor(StdDraw.RED);
				} else if (i == 2) {
					StdDraw.setPenColor(StdDraw.GREEN);
				} else if (i == 3) {
					StdDraw.setPenColor(StdDraw.MAGENTA);
				}
				xd = start + i * this.buttonSizeW + i * space;
				yd = this.hh / 2 + this.buttonSizeH + this.buttonSizeH * 2;
				// text P1...P4
				if ((i != 1 && i != 0) || !lan) {
					if (xn < xd + this.buttonSizeW / 2 && xn > xd - this.buttonSizeW / 2
							&& yn < this.hh / 2 + this.buttonSizeH / 2 && yn > this.hh / 2 - this.buttonSizeH / 2) {
						if (StdDraw.mousePressed()) {// if pressed
							playerBeing[i] = playerBeing[i] + 1;
							if (playerBeing[i] == titleS.length) {
								playerBeing[i] = 0;
							}
							StdDraw.setPenColor(StdDraw.GRAY);
							StdDraw.filledRectangle(xd, this.hh / 2 + this.buttonSizeH, this.buttonSizeW / 2,
									this.buttonSizeH / 2);// cover old text
							if (i == 0) {
								StdDraw.setPenColor(StdDraw.BLUE);
							} else if (i == 1) {
								StdDraw.setPenColor(StdDraw.RED);
							} else if (i == 2) {
								StdDraw.setPenColor(StdDraw.GREEN);
							} else if (i == 3) {
								StdDraw.setPenColor(StdDraw.MAGENTA);
							}
							StdDraw.text(xd, this.hh / 2 + this.buttonSizeH, titleS[playerBeing[i]]);
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}

				StdDraw.rectangle(xd, this.hh / 2, this.buttonSizeW / 2, this.buttonSizeH / 2);
				// arrows
				double[] xx = { xd - this.buttonSizeW / 4 - 10, xd - this.buttonSizeW / 4, xd - this.buttonSizeW / 4 };
				double[] yy = { yd, yd - 10, yd + 10 };
				StdDraw.polygon(xx, yy);
				double[] xx2 = { xd + this.buttonSizeW / 4 + 10, xd + this.buttonSizeW / 4, xd + this.buttonSizeW / 4 };
				StdDraw.polygon(xx2, yy);

				if (xn < xd - this.buttonSizeW / 4 + 5 && xn > xd - this.buttonSizeW / 4 - 10 - 5 && yn < yd + 10
						&& yn > yd - 20) {// left arrow
					if (StdDraw.mousePressed()) {// if pressed
						playerHat[i] = playerHat[i] - 1;
						if (playerHat[i] == -2) {
							playerHat[i] = h.length - 1;
						}
						StdDraw.setPenColor(StdDraw.GRAY);
						StdDraw.filledRectangle(xd, yd, this.buttonSizeW / 4, this.buttonSizeH);// cover
																								// old
																								// image
						StdDraw.picture(xd, yd, t[0], this.buttonSizeW / 2, this.buttonSizeH * 2, 0);
						if (playerHat[i] != -1) {
							StdDraw.picture(xd, yd, h[playerHat[i]], this.buttonSizeW / 2, this.buttonSizeH * 2, 0);
						}
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

				if (xn < xd + this.buttonSizeW / 4 + 10 + 5 && xn > xd + this.buttonSizeW / 4 - 5 && yn < yd + 10
						&& yn > yd - 20) {// right arrow
					if (StdDraw.mousePressed()) {// if pressed
						playerHat[i] = playerHat[i] + 1;
						if (playerHat[i] == h.length) {
							playerHat[i] = -1;
						}
						StdDraw.setPenColor(StdDraw.GRAY);
						StdDraw.filledRectangle(xd, yd, this.buttonSizeW / 4, this.buttonSizeH);// cover
																								// old
																								// image
						StdDraw.picture(xd, yd, t[0], this.buttonSizeW / 2, this.buttonSizeH * 2, 0);
						if (playerHat[i] != -1) {
							StdDraw.picture(xd, yd, h[playerHat[i]], this.buttonSizeW / 2, this.buttonSizeH * 2, 0);
						}
						try {
							Thread.sleep(70);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				if (xn < this.wh / 2 + this.buttonSizeH / 2 && xn > this.wh / 2 - this.buttonSizeH / 2
						&& yn < this.buttonSizeH + this.buttonSizeH / 2
						&& yn > this.buttonSizeH - this.buttonSizeH / 2) {// Start
																			// button
					if (StdDraw.mousePressed()) {// if pressed
						acted = true;
					}
				}

			}
			StdDraw.show(1);
		}
		this.hat = playerHat;
		this.being = playerBeing;
		// ----start
		if (save == true && nbp == 4 && !lan) {
			this.nbPlayer = 4;
			this.actionEffect(13);
		} else if (save == true && nbp == 2 && !lan) {
			this.nbPlayer = 2;
			this.actionEffect(14);
		} else if (editor == true && nbp == 2 && !lan) {
			this.nbPlayer = 2;
			this.actionEffect(21);
		} else if (editor == true && nbp == 4 && !lan) {
			this.nbPlayer = 4;
			this.actionEffect(20);
		} else if (!lan) {
			this.nbPlayer = nbp;
			this.actionEffect(7);// start
		} else if (save == true && nbp == 4 && lan) {
			this.nbPlayer = 4;
			this.actionEffect(13);
		} else if (save == true && nbp == 2 && lan) {
			this.nbPlayer = 2;
			this.actionEffect(14);
		} else if (editor == true && nbp == 2 && lan) {
			this.nbPlayer = 2;
			this.actionEffect(94);
		} else if (editor == true && nbp == 4 && lan) {
			this.nbPlayer = 4;
			this.actionEffect(93);
		} else if (lan) {
			this.nbPlayer = nbp;
			this.actionEffect(90);// start
		}
	}

	// ----------------------Map
	// editor--------------------------------------------

	public void showMap(boolean size, boolean player, boolean editor, boolean lan) {
		StdDraw.clear(StdDraw.GRAY);
		StdDraw.setXscale(0, this.wh);
		StdDraw.setYscale(0, this.hh);
		boolean goodbye = false;
		int w = 7;
		int h = 7;
		int[] nb = { 2, 4 };
		int choice = 0;
		// title
		Font fontT = new Font("Arial", Font.BOLD, (this.buttonSizeH - 2) + (this.buttonSizeH - 2) / 2);
		StdDraw.setFont(fontT);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(this.wh / 2, this.hh - (this.buttonSizeH - 2) * 1.5, this.title);
		// map
		if (size) {
			StdDraw.rectangle(this.wh / 4, this.hh / 2, this.wh / 4, this.hh / 4);
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.text(0 + (this.buttonSizeH - 2) + (this.buttonSizeH - 2) / 220, this.hh / 2, "" + h);
			StdDraw.text(this.wh / 4, this.hh / 4 + 20, "" + w);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.picture(this.wh / 4 - 40, this.hh / 2 - 40, ".\\images/sq1.png", 40, 40);
			StdDraw.picture(this.wh / 4 + 40, this.hh / 2 + 40, ".\\images/sq6.png", 40, 40);
			StdDraw.picture(this.wh / 4, this.hh / 2 + 40, ".\\images/sq2.png", 40, 40);
			StdDraw.picture(this.wh / 4, this.hh / 2 - 40, ".\\images/sq3.png", 40, 40);
			StdDraw.picture(this.wh / 4 + 40, this.hh / 2, ".\\images/sq4.png", 40, 40);
			StdDraw.picture(this.wh / 4 - 40, this.hh / 2, ".\\images/sq5.png", 40, 40);
			StdDraw.picture(this.wh / 4 + 40, this.hh / 2 - 40, ".\\images/sq1.png", 40, 40);
			StdDraw.picture(this.wh / 4 - 40, this.hh / 2 + 40, ".\\images/sq6.png", 40, 40);
			if (this.forme == 3) {
				StdDraw.picture(this.wh / 4, this.hh / 2 - 40, ".\\images/hexagotestcolor3.png", 40, 40);
				StdDraw.picture(this.wh / 4, this.hh / 2 + 40, ".\\images/hexagotestcolor2.png", 40, 40);
				StdDraw.picture(this.wh / 4 + 30, this.hh / 2 + 20, ".\\images/hexagotestcolor4.png", 40, 40);
				StdDraw.picture(this.wh / 4 - 30, this.hh / 2 + 20, ".\\images/hexagotestcolor5.png", 40, 40);
				StdDraw.picture(this.wh / 4 + 30, this.hh / 2 - 20, ".\\images/hexagotestcolor1.png", 40, 40);
				StdDraw.picture(this.wh / 4 - 30, this.hh / 2 - 20, ".\\images/hexagotestcolor.png", 40, 40);
			}

			// arrow width
			double[] xx = { this.wh / 4 + 40 + 20, this.wh / 4 + 40 + 20, this.wh / 4 + 60 + 20 };
			double[] yy = { this.hh / 4 + 20 + 25, this.hh / 4 - 20 + 25, this.hh / 4 + 25 };
			StdDraw.polygon(xx, yy);
			double[] xx2 = { this.wh / 4 - 40 - 20, this.wh / 4 - 40 - 20, this.wh / 4 - 60 - 20 };
			StdDraw.polygon(xx2, yy);
			// arrow height
			double[] dxx = { (this.buttonSizeH - 2) + (this.buttonSizeH - 2) / 220 + 25,
					(this.buttonSizeH - 2) + (this.buttonSizeH - 2) / 220 - 25,
					(this.buttonSizeH - 2) + (this.buttonSizeH - 2) / 220 };
			double[] dyy = { this.hh / 2 + 40, this.hh / 2 + 40, this.hh / 2 + 40 + 40 };
			StdDraw.polygon(dxx, dyy);
			double[] dyy2 = { this.hh / 2 - 40 + 10, this.hh / 2 - 40 + 10, this.hh / 2 - 40 - 40 + 10 };
			StdDraw.polygon(dxx, dyy2);
		}
		if (player) {
			// number of players
			StdDraw.rectangle(this.wh / 2 + 40, this.hh / 2 + this.hh / 4 - 10, this.buttonSizeH / 2,
					this.buttonSizeH / 2 + 7);
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.text(this.wh / 2 + 40, this.hh / 2 + this.hh / 4 - 17, "" + nb[choice]);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.textLeft(this.wh / 2 + 40 + this.buttonSizeH / 2 + 25, this.hh / 2 + this.hh / 4 - 17, "joueurs");
		}
		// capture rule
		StdDraw.rectangle(this.wh / 2 + 40, this.hh / 2 + this.hh / 4 - 10 - (this.buttonSizeH) * 2,
				this.buttonSizeH / 2, this.buttonSizeH / 2);
		StdDraw.setPenColor(StdDraw.BLUE);
		StdDraw.setPenColor(StdDraw.BLACK);
		if (this.entourer) {
			StdDraw.filledRectangle(this.wh / 2 + 40, this.hh / 2 + this.hh / 4 - 10 - (this.buttonSizeH) * 2,
					this.buttonSizeH / 3, this.buttonSizeH / 3);
		}
		StdDraw.textLeft(this.wh / 2 + 40 + this.buttonSizeH / 2 + 25,
				this.hh / 2 + this.hh / 4 - 17 - (this.buttonSizeH) * 2, "Capture");

		// brouillard
		if (lan) {
			StdDraw.rectangle(this.wh / 2 + 40, this.hh / 2 + this.hh / 4 - 10 - (this.buttonSizeH) * 4,
					this.buttonSizeH / 2, this.buttonSizeH / 2);
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.setPenColor(StdDraw.BLACK);
			if (this.brouillard) {
				StdDraw.filledRectangle(this.wh / 2 + 40, this.hh / 2 + this.hh / 4 - 10 - (this.buttonSizeH) * 4,
						this.buttonSizeH / 3, this.buttonSizeH / 3);
			}
			StdDraw.textLeft(this.wh / 2 + 40 + this.buttonSizeH / 2 + 25,
					this.hh / 2 + this.hh / 4 - 17 - (this.buttonSizeH) * 4, "Brume");
		}
		// next button
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(this.wh / 2, this.buttonSizeH, this.buttonSizeW / 4, this.buttonSizeH / 2 + 10);
		StdDraw.text(this.wh / 2, this.buttonSizeH - 5, "ok");
		// back button
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(this.wh / 2 - 200, this.buttonSizeH, this.buttonSizeW / 4 + 50, this.buttonSizeH / 2);
		StdDraw.text(this.wh / 2 - 200, this.buttonSizeH - 5, "menu");

		// overlay and click
		boolean acted = false;
		while (!acted) {
			double xn = StdDraw.mouseX();
			double yn = StdDraw.mouseY();
			if (size) {
				if (xn < this.wh / 4 + 30 + 20 && xn > this.wh / 4 - 30 - 40 && yn < this.hh / 2 + 40 + 20
						&& yn > this.hh / 2 - 40 - 20) {
					if (StdDraw.mousePressed()) {
						StdDraw.setPenColor(StdDraw.GRAY);
						StdDraw.filledRectangle(this.wh / 4, this.hh / 2, 70, 80);
						if (this.forme == 1 && this.diamond == false) {
							this.forme = 1;
							this.diamond = true;
						} else if (this.forme == 1 && this.diamond == true) {
							this.forme = 3;
							this.diamond = false;
						} else {
							this.forme = 1;
							this.diamond = false;
						}
						if (this.forme == 3 && h % 2 == 0) {
							h = h - 1;
						}
						if (this.forme == 3 && w % 2 == 0) {
							w = w - 1;
						}
						StdDraw.setPenColor(StdDraw.GRAY);
						StdDraw.filledRectangle(this.wh / 4, this.hh / 4 + 20 + 10, 30, 25);
						StdDraw.filledRectangle(0 + (this.buttonSizeH - 2) + (this.buttonSizeH - 2) / 220, this.hh / 2,
								30, 30);
						StdDraw.setPenColor(StdDraw.BLUE);
						StdDraw.text(0 + (this.buttonSizeH - 2) + (this.buttonSizeH - 2) / 220, this.hh / 2, "" + h);
						StdDraw.text(this.wh / 4, this.hh / 4 + 20, "" + w);
						if (this.forme == 3) {
							StdDraw.picture(this.wh / 4, this.hh / 2 - 40, ".\\images/hexagotestcolor3.png", 40, 40);
							StdDraw.picture(this.wh / 4, this.hh / 2 + 40, ".\\images/hexagotestcolor2.png", 40, 40);
							StdDraw.picture(this.wh / 4 + 30, this.hh / 2 + 20, ".\\images/hexagotestcolor4.png", 40,
									40);
							StdDraw.picture(this.wh / 4 - 30, this.hh / 2 + 20, ".\\images/hexagotestcolor5.png", 40,
									40);
							StdDraw.picture(this.wh / 4 + 30, this.hh / 2 - 20, ".\\images/hexagotestcolor1.png", 40,
									40);
							StdDraw.picture(this.wh / 4 - 30, this.hh / 2 - 20, ".\\images/hexagotestcolor.png", 40,
									40);
						} else if (this.forme == 1 && this.diamond == false) {
							StdDraw.picture(this.wh / 4 - 40, this.hh / 2 - 40, ".\\images/sq1.png", 40, 40);
							StdDraw.picture(this.wh / 4 + 40, this.hh / 2 + 40, ".\\images/sq6.png", 40, 40);
							StdDraw.picture(this.wh / 4, this.hh / 2 + 40, ".\\images/sq2.png", 40, 40);
							StdDraw.picture(this.wh / 4, this.hh / 2 - 40, ".\\images/sq3.png", 40, 40);
							StdDraw.picture(this.wh / 4 + 40, this.hh / 2, ".\\images/sq4.png", 40, 40);
							StdDraw.picture(this.wh / 4 - 40, this.hh / 2, ".\\images/sq5.png", 40, 40);
							StdDraw.picture(this.wh / 4 + 40, this.hh / 2 - 40, ".\\images/sq1.png", 40, 40);
							StdDraw.picture(this.wh / 4 - 40, this.hh / 2 + 40, ".\\images/sq6.png", 40, 40);
						} else {
							StdDraw.picture(this.wh / 4, this.hh / 2 + 40, ".\\images/sq2.png", 40, 40, 45);
							StdDraw.picture(this.wh / 4, this.hh / 2 - 40, ".\\images/sq3.png", 40, 40, 45);
							StdDraw.picture(this.wh / 4 + 40, this.hh / 2, ".\\images/sq4.png", 40, 40, 45);
							StdDraw.picture(this.wh / 4 - 40, this.hh / 2, ".\\images/sq5.png", 40, 40, 45);
						}
						// 1.5sec d'attente pour pas de clicks non voulus
						try {
							Thread.sleep(150);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				if (xn < this.wh / 4 + 60 + 20 && xn > this.wh / 4 + 40 + 20 && yn < this.hh / 4 + 20 + 25
						&& yn > this.hh / 4 - 20 + 25) {// right arrow
					if (StdDraw.mousePressed()) {// if pressed
						if (this.forme == 3) {
							w = w + 2;
						} else {
							w = w + 1;
						}
						if (w > 29) {
							w = 7;
						}
						StdDraw.setPenColor(StdDraw.GRAY);
						StdDraw.filledRectangle(this.wh / 4, this.hh / 4 + 20 + 10, 30, 25);
						StdDraw.setPenColor(StdDraw.BLUE);
						StdDraw.text(this.wh / 4, this.hh / 4 + 20, "" + w);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				if (xn > this.wh / 4 - 60 - 20 && xn < this.wh / 4 - 40 - 20 && yn < this.hh / 4 + 20 + 25
						&& yn > this.hh / 4 - 20 + 25) {// left arrow
					if (StdDraw.mousePressed()) {// if pressed
						if (this.forme == 3) {
							w = w - 2;
						} else {
							w = w - 1;
						}
						if (w < 7) {
							w = 29;
						}
						StdDraw.setPenColor(StdDraw.GRAY);
						StdDraw.filledRectangle(this.wh / 4, this.hh / 4 + 20 + 10, 30, 25);
						StdDraw.setPenColor(StdDraw.BLUE);
						StdDraw.text(this.wh / 4, this.hh / 4 + 20, "" + w);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				if (xn < (this.buttonSizeH - 2) + (this.buttonSizeH - 2) / 220 + 25
						&& xn > (this.buttonSizeH - 2) + (this.buttonSizeH - 2) / 220 - 25 && yn < this.hh / 2 + 40 + 40
						&& yn > this.hh / 2 + 40) {// UP arrow
					if (StdDraw.mousePressed()) {// if pressed
						if (this.forme == 3) {
							h = h + 2;
						} else {
							h = h + 1;
						}
						if (h > 29) {
							h = 7;
						}
						StdDraw.setPenColor(StdDraw.GRAY);
						StdDraw.filledRectangle(0 + (this.buttonSizeH - 2) + (this.buttonSizeH - 2) / 220, this.hh / 2,
								30, 30);
						StdDraw.setPenColor(StdDraw.BLUE);
						StdDraw.text(0 + (this.buttonSizeH - 2) + (this.buttonSizeH - 2) / 220, this.hh / 2, "" + h);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				if (xn < (this.buttonSizeH - 2) + (this.buttonSizeH - 2) / 220 + 25
						&& xn > (this.buttonSizeH - 2) + (this.buttonSizeH - 2) / 220 - 25
						&& yn > this.hh / 2 - 40 - 40 + 10 && yn < this.hh / 2 - 40 + 10) {// Down
																							// arrow
					if (StdDraw.mousePressed()) {// if pressed
						if (this.forme == 3) {
							h = h - 2;
						} else {
							h = h - 1;
						}
						if (h < 7) {
							h = 29;
						}
						StdDraw.setPenColor(StdDraw.GRAY);
						StdDraw.filledRectangle(0 + (this.buttonSizeH - 2) + (this.buttonSizeH - 2) / 220, this.hh / 2,
								30, 30);
						StdDraw.setPenColor(StdDraw.BLUE);
						StdDraw.text(0 + (this.buttonSizeH - 2) + (this.buttonSizeH - 2) / 220, this.hh / 2, "" + h);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			if (player) {
				// player nb
				if (xn < this.wh / 2 + 40 + this.buttonSizeH / 2 && xn > this.wh / 2 + 40 - this.buttonSizeH / 2
						&& yn > this.hh / 2 + this.hh / 4 - 10 - (this.buttonSizeH / 2 + 7)
						&& yn < this.hh / 2 + this.hh / 4 - 10 + (this.buttonSizeH / 2 + 7)) {
					if (StdDraw.mousePressed()) {// if pressed
						choice = choice + 1;
						if (choice == 2) {
							choice = 0;
						}
						StdDraw.setPenColor(StdDraw.GRAY);
						StdDraw.filledRectangle(this.wh / 2 + 40, this.hh / 2 + this.hh / 4 - 17 + 5, 16, 25);
						StdDraw.setPenColor(StdDraw.BLUE);
						StdDraw.text(this.wh / 2 + 40, this.hh / 2 + this.hh / 4 - 17, "" + nb[choice]);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

				// entourer
				if (xn < this.wh / 2 + 40 + this.buttonSizeH / 2 && xn > this.wh / 2 + 40 - this.buttonSizeH / 2
						&& yn > this.hh / 2 + this.hh / 4 - 10 - (this.buttonSizeH) * 2 - this.buttonSizeH / 2
						&& yn < this.hh / 2 + this.hh / 4 - 10 - (this.buttonSizeH) * 2 + this.buttonSizeH / 2) {
					if (StdDraw.mousePressed()) {// if pressed
						this.entourer = !this.entourer;
						StdDraw.setPenColor(StdDraw.GRAY);
						if (this.entourer) {
							StdDraw.setPenColor(StdDraw.BLACK);
						}
						StdDraw.filledRectangle(this.wh / 2 + 40,
								this.hh / 2 + this.hh / 4 - 10 - (this.buttonSizeH) * 2, this.buttonSizeH / 3,
								this.buttonSizeH / 3);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						StdDraw.setPenColor(StdDraw.BLACK);
					}
				}
				// brouillard
				if (lan && xn < this.wh / 2 + 40 + this.buttonSizeH / 2 && xn > this.wh / 2 + 40 - this.buttonSizeH / 2
						&& yn > this.hh / 2 + this.hh / 4 - 10 - (this.buttonSizeH) * 4 - this.buttonSizeH / 2
						&& yn < this.hh / 2 + this.hh / 4 - 10 - (this.buttonSizeH) * 4 + this.buttonSizeH / 2) {
					if (StdDraw.mousePressed()) {// if pressed
						this.brouillard = !this.brouillard;
						StdDraw.setPenColor(StdDraw.GRAY);
						if (this.brouillard) {
							StdDraw.setPenColor(StdDraw.BLACK);
						}
						StdDraw.filledRectangle(this.wh / 2 + 40,
								this.hh / 2 + this.hh / 4 - 10 - (this.buttonSizeH) * 4, this.buttonSizeH / 3,
								this.buttonSizeH / 3);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						StdDraw.setPenColor(StdDraw.BLACK);
					}
				}

			}
			// ok
			if (xn < this.wh / 2 + this.buttonSizeW / 4 && xn > this.wh / 2 - this.buttonSizeW / 4
					&& yn > this.buttonSizeH - (this.buttonSizeH / 2 + 10)
					&& yn < this.buttonSizeH + (this.buttonSizeH / 2 + 10)) {
				if (StdDraw.mousePressed()) {// if pressed
					acted = true;
				}
			}
			// back
			if (xn < this.wh / 2 - 200 + this.buttonSizeW / 4 + 50
					&& xn > this.wh / 2 - 200 - (this.buttonSizeW / 4 + 50)
					&& yn > this.buttonSizeH - this.buttonSizeH / 2 && yn < this.buttonSizeH + this.buttonSizeH / 2) {
				if (StdDraw.mousePressed()) {// if pressed
					acted = true;
					goodbye = true;
				}
			}
			StdDraw.rectangle(this.wh / 2 - 200, this.buttonSizeH, this.buttonSizeW / 4 + 50, this.buttonSizeH / 2);
			StdDraw.show(0);
		}
		// if clicked back
		if (goodbye == true) {
			String[] t = { "Solo", "Multijoueur", "Editeur" };
			int[] i = { 2, 3, 17 };
			Menu aMain = new Menu("Menu Principal", t, i);
			aMain.showMenu();
		}
		// if clicked on ok
		this.mw = w;
		this.mh = h;
		this.nbPlayer = nb[choice];
		if (editor) {
			this.actionEffect(22);
		} else {
			if (!lan) {
				this.actionEffect(16);
			} else {
				this.actionEffect(92);
			}
		}

	}

	// --------------------------------------Bonus-----------------------------------
	public int konamiC(int konami) {
		if (StdDraw.isKeyPressed(KeyEvent.VK_UP) && konami < 2) {
			konami++;
			try {
				Thread.sleep(70);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN) && konami < 4 && konami >= 2) {
			konami++;
			try {
				Thread.sleep(70);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT) && (konami == 4 || konami == 6)) {
			konami++;
			try {
				Thread.sleep(70);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT) && (konami == 5 || konami == 7)) {
			konami++;
			try {
				Thread.sleep(70);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_B) && konami == 8) {
			konami++;
			try {
				Thread.sleep(70);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_A) && konami == 9) {
			konami++;
			try {
				Thread.sleep(70);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return konami;
	}

	public int getNbPlayer() {
		return nbPlayer;
	}

	public void setNbPlayer(int nbPlayer) {
		this.nbPlayer = nbPlayer;
	}

	public int getMapLoad() {
		return mapLoad;
	}

	public void setMapLoad(int mapLoad) {
		this.mapLoad = mapLoad;
	}

	public int getMh() {
		return mh;
	}

	public void setMh(int mh) {
		this.mh = mh;
	}

	public int getMw() {
		return mw;
	}

	public void setMw(int mw) {
		this.mw = mw;
	}

}
