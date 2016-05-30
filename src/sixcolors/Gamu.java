/**
* @author Louis
* Mecanique du jeu
*/
package sixcolors;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

import edu.princeton.cs.introcs.StdDraw;

/* Classe : Gamu
 * But : gérer la partie en cours que ce soit en mode solo ou en multi. Il s'occupe aussi
 * de l'éditeur.
 * 
 * */
public class Gamu {
	private int wh = 640 + 100;// window width in pixel
	private int hh = 640;// window height in pixel
	private Animation anim = new Animation();
	private int[] h;
	private boolean entourer = true;
	private int mapLoad = 0;
	static boolean graph = true;
	private boolean brouillard = false;
	private static int[] iaStock = { 0, 1, 1, 1 };

	public Gamu(int[] h) {
		this.anim.setPlayerHat(h);
		this.h = h;
	}

	private int forme = 1;
	private boolean diamond = false;

	public int won(int a, int[] t) {// check if won
		if (t[0] >= a / 2) {
			return 1;
		}
		if (t[1] >= a / 2) {
			return 2;
		}
		if (t[2] >= a / 2) {
			return 3;
		}
		if (t[3] >= a / 2) {
			return 4;
		}
		// condition si Board completremnt rempli à 4
		if (t[3] + t[2] + t[1] + t[0] >= a) {
			if (t[3] > Math.max(t[0], Math.max(t[1], t[2]))) {
				return 4;
			}
			if (t[2] > Math.max(t[0], Math.max(t[1], t[3]))) {
				return 3;
			}
			if (t[1] > Math.max(t[0], Math.max(t[3], t[2]))) {
				return 2;
			}
			if (t[0] > Math.max(t[3], Math.max(t[1], t[2]))) {
				return 1;
			}
		}
		return 0;
	}

	static public void choice(int c1, int c2, int c3, int c4) {// imprime les
																// choix
		// possibles
		for (int i = 0; i < 6; i++) {
			if (i != c1 && i != c2 && i != c3 && i != c4) {
				System.out.print(i);
			}
		}
	}

	static public int[] choiceTab(int c1, int c2, int c3, int c4) {// renvoie
																	// les choix
		// possibles
		int[] t = new int[4];
		int already = 0;
		for (int i = 0; i < 6; i++) {
			if (i != c1 && i != c2 && i != c3 && i != c4) {
				t[already] = i;
				already++;
			}
		}
		return t;
	}

	public void drawChoice(int c1, int c2, int c3, int c4, Board bb) {// affiche
																		// les
																		// choix
																		// sur
																		// fenetre
																		// (stddraw)
		int already = 0;
		StdDraw.setXscale(0, wh);
		StdDraw.setYscale(0, hh);
		StdDraw.setPenColor(StdDraw.BLACK);
		for (int i = 0; i < 6; i++) {
			if (i != c1 && i != c2 && i != c3 && i != c4) {
				StdDraw.picture(this.hh * 0.05 + this.hh * 0.36 * already, this.hh * 0.8 + this.hh * 0.01,
						bb.getColorim()[i], this.hh * 0.18, this.hh * 0.18, 0);
				already++;
			}
		}
	}

	// -----------------editor---------------------------------------------------------------------------

	public void editor(int w, int h, boolean loaded, int nbP) {
		StdDraw.setXscale(0, this.wh);
		StdDraw.setYscale(0, this.hh);
		StdDraw.clear(StdDraw.BLACK);
		Board b = new Board(w, h, nbP, this.forme);
		b.setDiamond(this.diamond);
		b.setEntourer(this.entourer);
		if (loaded) {
			b = b.readmyBoard(true, this.mapLoad);
			b.setWidth(b.getBigBoard().length);
			b.setHeight(b.getBigBoard()[0].getBox().length);
			w = b.getBigBoard().length;
			h = b.getBigBoard()[0].getBox().length;
			b.setPlayTurn(0);
		}
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < b.getBigBoard()[i].getBox().length; j++) {
				if (!(i == 0 && j == 0) && !(i == w - 1 && j == h - 1)
						&& (!(i == 0 && j == h - 1) && !(i == w - 1 && j == 0) && nbP == 4))
					b.getBigBoard()[i].getBox()[j].setProp(0);
			}

		}
		b.setPlayTurn(0);
		b.afficherTab();
		boolean sav = false;
		int col = 0;
		while (sav == false) {
			StdDraw.setXscale(0, this.wh);
			StdDraw.setYscale(0, this.hh);
			StdDraw.setPenColor(StdDraw.BLACK);
			double tailleCase = 0;
			if (b.getWidth() > b.getHeight()) {
				tailleCase = this.wh * 0.75 / b.getWidth();
			} else {
				tailleCase = this.hh * 0.80 / b.getHeight();
			}
			if (this.diamond && forme == 1) {
				tailleCase = tailleCase / 2;
			}
			double ecartV = (this.hh * 0.8 - b.getHeight() * tailleCase);
			double ecartH = (this.wh * 0.75 - b.getWidth() * tailleCase) / 6;
			if (this.forme == 1)
				ecartH = -tailleCase / 2;
			// dessiner boutons
			Font font = new Font("Arial", Font.BOLD, (int) (this.wh * 0.1 / 3));
			StdDraw.setFont(font);
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.rectangle(this.wh * 0.75 + this.wh * 0.125, this.hh * 0.5, this.wh * 0.1, this.hh * 0.03);

			StdDraw.text(this.wh * 0.75 + this.wh * 0.125, this.hh * 0.5, "Sauvegarder");
			StdDraw.rectangle(this.wh * 0.75 + this.wh * 0.125, this.hh * 0.5 - this.hh * 0.2, this.wh * 0.1,
					this.hh * 0.03);
			StdDraw.text(this.wh * 0.75 + this.wh * 0.125, this.hh * 0.5 - this.hh * 0.2, "Quitter");
			StdDraw.setPenColor(StdDraw.BLACK);
			double xn = StdDraw.mouseX();
			double yn = StdDraw.mouseY();

			for (int i = 0; i < 6; i++) {
				StdDraw.picture(this.hh * 0.05 + this.hh * 0.36 * i / 2, this.hh * 0.8 + this.hh * 0.1,
						b.getColorim()[i], this.hh * 0.18 / 2, this.hh * 0.18 / 2, 0);
				if (col == i) {
					StdDraw.rectangle(this.hh * 0.05 + this.hh * 0.36 * i / 2, this.hh * 0.8 + this.hh * 0.1,
							this.hh * 0.045 / 2, this.hh * 0.045 / 2);
				}
			}
			StdDraw.picture(this.wh * 0.75 + this.wh * 0.125, this.hh * 0.8 - this.hh * 0.1, ".\\images/hexadelete.png",
					this.hh * 0.18 / 2, this.hh * 0.18 / 2, 0);

			StdDraw.show(0);
			if (StdDraw.mousePressed()) {
				System.out.println(xn);
				if (yn < this.hh * 0.8 && xn > this.wh * 0.75) {
					if (xn > this.wh * 0.75 + this.wh * 0.125 - this.hh * 0.18 / 4
							&& xn < this.wh * 0.75 + this.wh * 0.125 + this.hh * 0.18 / 4
							&& yn < this.hh * 0.8 - this.hh * 0.1 + this.hh * 0.18 / 4
							&& yn > this.hh * 0.8 - this.hh * 0.1 - this.hh * 0.18 / 4) {
						col = 6;
					}

					if (xn > this.wh * 0.75 + this.wh * 0.125 - this.wh * 0.1
							&& xn < this.wh * 0.75 + this.wh * 0.125 + this.wh * 0.1
							&& yn < this.hh * 0.5 + this.hh * 0.05 && yn > this.hh * 0.5 - this.hh * 0.05) {
						b.savebonly(true);
						sav = true;
						System.out.println("save");
					} else if (xn > this.wh * 0.75 + this.wh * 0.125 - this.wh * 0.1
							&& xn < this.wh * 0.75 + this.wh * 0.125 + this.wh * 0.1
							&& yn < this.hh * 0.5 + this.hh * 0.05 - this.hh * 0.2
							&& yn > this.hh * 0.5 - this.hh * 0.05 - this.hh * 0.2) {
						// aller au menu
						System.out.println("menuuuu");
						sav = true;

					}
				} else if (yn < this.hh * 0.8) {
					for (int i = 0; i < w; i++) {
						for (int j = 0; j < b.getBigBoard()[i].getBox().length; j++) {
							if (this.forme == 3) {
								if (xn < tailleCase * (i + 1) + ecartH - tailleCase * (i + 1) * 0.25 + tailleCase / 2
										&& xn > tailleCase * (i + 1) + ecartH - tailleCase * (i + 1) * 0.25
												- tailleCase / 2
										&& (i != 0 || j != 0) && (i != w - 1 || j != h - 1)
										&& (!(i == 0 && j == h - 1) || nbP == 2)
										&& (!(i == w - 1 && j == 0) || nbP == 2)) {
									if (b.getBigBoard()[i].getid() % 2 != 0
											&& yn < tailleCase * j + tailleCase / 2 + ecartV + tailleCase / 2
											&& yn > tailleCase * j + tailleCase / 2 + ecartV - tailleCase / 2) {
										b.getBigBoard()[i].getBox()[j].setColor(col);
										b.getBigBoard()[i].getBox()[j].setProp(0);
										StdDraw.picture(tailleCase * (i + 1) + ecartH - tailleCase * (i + 1) * 0.25,
												tailleCase * j + tailleCase / 2 + ecartV,
												b.getColorim()[b.getBigBoard()[i].getBox()[j].getColor()], tailleCase,
												tailleCase, 0);
									} else if (b.getBigBoard()[i].getid() % 2 == 0
											&& yn < tailleCase * j + tailleCase + ecartV + tailleCase / 2
											&& yn > tailleCase * j + tailleCase + ecartV - tailleCase / 2
											&& (i != 0 || j != 0) && (i != w - 1 || j != h - 1)) {
										b.getBigBoard()[i].getBox()[j].setColor(col);
										b.getBigBoard()[i].getBox()[j].setProp(0);
										StdDraw.picture(tailleCase * (i + 1) + ecartH - tailleCase * (i + 1) * 0.25,
												tailleCase * j + tailleCase + ecartV,
												b.getColorim()[b.getBigBoard()[i].getBox()[j].getColor()], tailleCase,
												tailleCase, 0);
									}
								}
							} else if (this.forme == 1 && this.diamond == false) {
								if (xn < tailleCase * (i + 1) + ecartH + tailleCase / 2
										&& xn > tailleCase * (i + 1) + ecartH - tailleCase / 2 && (i != 0 || j != 0)
										&& (i != w - 1 || j != h - 1) && (!(i == 0 && j == h - 1) || nbP == 2)
										&& (!(i == w - 1 && j == 0) || nbP == 2)) {
									if (yn < tailleCase * (j + 1) + ecartV + tailleCase / 2
											&& yn > tailleCase * (j + 1) + ecartV - tailleCase / 2) {
										b.getBigBoard()[i].getBox()[j].setColor(col);
										b.getBigBoard()[i].getBox()[j].setProp(0);
										StdDraw.picture(tailleCase * (i + 1) + ecartH, tailleCase * (j + 1) + ecartV,
												b.getColorimsq()[b.getBigBoard()[i].getBox()[j].getColor()], tailleCase,
												tailleCase, 0);
									}
								}
							} else if (this.forme == 1 && this.diamond == true) {
								if (xn < tailleCase * (i + 1) + ecartH + tailleCase * (j) + tailleCase / 2
										&& xn > tailleCase * (i + 1) + ecartH + tailleCase * (j) - tailleCase / 2
										&& (i != 0 || j != 0) && (i != w - 1 || j != h - 1)
										&& (!(i == 0 && j == h - 1) || nbP == 2)
										&& (!(i == w - 1 && j == 0) || nbP == 2)) {
									if (yn < tailleCase * (j + 1) + ecartV - tailleCase * (i) + tailleCase / 2
											&& yn > tailleCase * (j + 1) + ecartV - tailleCase * (i) - tailleCase / 2) {
										b.getBigBoard()[i].getBox()[j].setColor(col);
										b.getBigBoard()[i].getBox()[j].setProp(0);
										StdDraw.picture(tailleCase * (i + 1) + ecartH + tailleCase * (j),
												tailleCase * (j + 1) + ecartV - tailleCase * (i),
												b.getColorimsq()[b.getBigBoard()[i].getBox()[j].getColor()], tailleCase,
												tailleCase, 45);
									}
								}
							}
						}
					}
				} else {
					for (int i = 0; i < 6; i++) {
						if (xn > this.hh * 0.05 + this.hh * 0.36 * i / 2 - this.hh * 0.18 / 4
								&& xn < this.hh * 0.05 + this.hh * 0.36 * i / 2 + this.hh * 0.18 / 4) {
							col = i;
						}
					}
				}

			}
		}
		// quit
		String[] t = { "Solo", "Multijoueur", "Editeur" };
		int[] i = { 2, 3, 17 };
		Menu aMain = new Menu("Menu Principal", t, i);
		aMain.showMenu();

	}

	public int lengthT(Player[] t) {
		int n = 0;
		for (int i = 0; i < t.length; i++) {
			if (t[i] != null)
				n++;
		}
		return n;
	}

	public int lengthT2(int[] t) {
		int n = 0;
		for (int i = 0; i < t.length; i++) {
			if (t[i] != -1)
				n++;
		}
		return n;
	}

	// ----------------------------General function
	// IA------------------------------------------------------

	public void startiaGen(int w, int h, int[] IA, boolean loaded, boolean loadsave) {
		int wh = 640 + 100;
		int hh = 640;
		boolean wons = false;
		if (this.graph)
			StdDraw.clear(StdDraw.BLACK);
		Scanner sca = new Scanner(System.in);
		Board b = new Board(w, h, lengthT2(IA), this.forme);
		b.setDiamond(this.diamond);
		System.out.print(lengthT2(IA));
		if (lengthT2(IA) == 4) {
			b.getBigBoard()[w - 1].getBox()[0].setColor(3);
			b.getBigBoard()[w - 1].getBox()[0].setProp(3);
			b.getBigBoard()[0].getBox()[h - 1].setColor(4);
			b.getBigBoard()[0].getBox()[h - 1].setProp(4);
		}
		Player[] players = new Player[4];
		for (int i = 0; i < IA.length; i++) {
			if (IA[i] == 0) {
				players[i] = new HumanBoard(b, i, this.h, false);
			} else if (IA[i] > 0) {
				players[i] = new IA(b, IA[i], i, this.h);
			} else if (IA[i] == -1) {
				players[i] = null;
			}
		}
		if (loaded) {
			b = b.readmyBoard(true, this.mapLoad);
			if (b.getPlayTurn() < 1) {
				b.setPlayTurn(1);
			}
			b.setNbPlayer(lengthT2(IA));
			b.luckyUp(w, h);
		} else if (loadsave) {
			b = b.readmyBoard(false, this.mapLoad);
			b.setWidth(b.getBigBoard().length);
			b.setHeight(b.getBigBoard()[0].getBox().length);
			w = b.getBigBoard().length;
			h = b.getBigBoard()[0].getBox().length;
			if (b.getPlayTurn() < 1) {
				b.setPlayTurn(1);
			}

		} else {// no change of rules when loading save
			b.setEntourer(this.entourer);
		}
		b.setNbPlayer(lengthT(players));// set 4 players
		if (this.graph) {
			b.afficherTab();
		} else {
			System.out.println("");
			b.drawBoard();// couleurs
			System.out.println("");
			b.drawBoardp();// proprietaire
		}
		int toachieve = 13 * 13 / 2 + 13 + 12 * (13 / 2);
		toachieve = w * h - w / 2;// points à obtenir pour 50%
		toachieve = toachieve - b.countHole();// enlever les obstacles
		int fp = 0;
		int im = 0;
		boolean lef = true;
		int ang = 0;
		Font font = new Font("Arial", Font.BOLD, (640) * 12 / (7 * (b.getHeight() * 6 + 18)));
		while (!wons) {// boucle principale
			int ent = b.getColor1();
			int[] transi;
			wons = !(won(toachieve, b.getScores()) == 0);
			System.out.println("Tour du joueur " + b.getPlayTurn());
			System.out.println("Qui a " + b.getScores()[b.getPlayTurn() - 1] + " points");
			ent = players[b.getPlayTurn() - 1].play(b, ent, ang, lef, fp, im, lengthT(players), b.getPlayTurn());
			System.out.println(ent);
			nextTurn(b, ent, lengthT(players));
			b.upscore(1);
			b.upscore(2);
			b.upscore(3);
			b.upscore(4);
			if (this.graph) {
				StdDraw.show(1);
			} else {
				System.out.println("");
				System.out.println("Couleurs :");
				b.drawBoard();// couleurs
				System.out.println("");
				System.out.println("Propriétaires :");
				b.drawBoardp();// proprietaire
			}

		}
		if (this.graph) {
			WinScreen ww = new WinScreen(b, won(toachieve, b.getScores()), this.h);
			ww.afficher();
			drawin(b, toachieve, b.getscore());
		}
		System.out.println("");
		System.out.print("Player " + won(toachieve, b.getScores()));
		System.out.println(" won !");
		b.setPlayTurn(0);
		sca.close();
		return;
	}

	public void nextTurn(Board b, int ent, int nb) {
		if (b.getPlayTurn() == 1) {
			b.setColor1(ent);
			b.updateBoard();
			b.setPlayTurn(2);
			if (Gamu.graph)
				b.afficherTab();
		} else if (b.getPlayTurn() == 2 && nb > 2) {
			b.setColor2(ent);
			b.updateBoard();
			b.setPlayTurn(3);
			if (Gamu.graph)
				b.afficherTab();
		} else if (b.getPlayTurn() == 2 && nb < 3) {
			b.setColor2(ent);
			b.updateBoard();
			b.setPlayTurn(1);
			if (Gamu.graph)
				b.afficherTab();
		} else if (b.getPlayTurn() == 3 && nb > 3) {
			b.setColor3(ent);
			b.updateBoard();
			b.setPlayTurn(4);
			if (Gamu.graph)
				b.afficherTab();
		} else if (b.getPlayTurn() == 3 && nb < 4) {
			b.setColor3(ent);
			b.updateBoard();
			b.setPlayTurn(1);
			if (Gamu.graph)
				b.afficherTab();
		} else if (b.getPlayTurn() == 4 && nb > 3) {
			b.setColor4(ent);
			b.updateBoard();
			b.setPlayTurn(1);
			if (Gamu.graph)
				b.afficherTab();
		}
	}

	// ----------------------------MUlti function
	// Multiplayer------------------------------------------------------

	public void startiaMult(int w, int h, int[] IA, boolean loaded, boolean loadsave, boolean servor, boolean saving,
			boolean ld, Thread r, Thread s) {
		int wh = 640 + 100;
		int hh = 640;
		int tours = 1;// special multijoueur pour repaire commun
		boolean wons = false;
		if (this.graph)
			StdDraw.clear(StdDraw.BLACK);
		Scanner sca = new Scanner(System.in);
		Board b = new Board(w, h, lengthT2(IA), this.forme);
		b.setDiamond(this.diamond);
		b.setBrouillard(this.brouillard);
		System.out.print(lengthT2(IA));
		if (lengthT2(IA) == 4) {
			b.getBigBoard()[w - 1].getBox()[0].setColor(3);
			b.getBigBoard()[w - 1].getBox()[0].setProp(3);
			b.getBigBoard()[0].getBox()[h - 1].setColor(4);
			b.getBigBoard()[0].getBox()[h - 1].setProp(4);
		}
		if (!servor) {// si client, on s'en fou des valeurs et on veu juste le
						// 2ieme en humain (nous)
			IA[0] = 0;
			IA[1] = 0;
			IA[2] = 0;
			IA[3] = 0;
		}

		Player[] players = new Player[4];
		for (int i = 0; i < IA.length; i++) {
			if (IA[i] == 0) {
				players[i] = new HumanBoard(b, i, this.h, true);
			} else if (IA[i] > 0) {
				players[i] = new IA(b, IA[i], i, this.h);
			} else if (IA[i] == -1) {
				players[i] = null;
			}
		}
		if (loaded) {
			b = b.readmyBoard(true, this.mapLoad);
			if (b.getPlayTurn() < 1) {
				b.setPlayTurn(1);
			}
			b.setNbPlayer(lengthT2(IA));
			b.luckyUp(w, h);
		} else if (loadsave) {
			b = b.readmyBoard(false, this.mapLoad);
			b.setWidth(b.getBigBoard().length);
			b.setHeight(b.getBigBoard()[0].getBox().length);
			w = b.getBigBoard().length;
			h = b.getBigBoard()[0].getBox().length;
			if (b.getPlayTurn() < 1) {
				b.setPlayTurn(1);
			}
		} else if (!servor) {// on load la map de multi si on est Client
			b = b.readMulti(false);
			this.h = b.getHat();
			if (b.getNbPlayer() == 2) {
				IA[2] = -1;
				IA[3] = -1;
			}
			b.setWidth(b.getBigBoard().length);
			b.setHeight(b.getBigBoard()[0].getBox().length);
			w = b.getBigBoard().length;
			h = b.getBigBoard()[0].getBox().length;
			if (b.getNbPlayer() == 2) {
				IA[2] = -1;
				IA[3] = -1;
			}
			if (b.getPlayTurn() < 1) {
				b.setPlayTurn(1);
			}

		} else {// no change of rules when loading save
			b.setEntourer(this.entourer);
		}

		b.setNbPlayer(lengthT(players));// set 4 players
		if (this.graph) {
			b.afficherTab();
		} else {
			System.out.println("");
			b.drawBoard();// couleurs
			System.out.println("");
			b.drawBoardp();// proprietaire
		}
		int toachieve = 13 * 13 / 2 + 13 + 12 * (13 / 2);
		toachieve = w * h - w / 2;// points à obtenir pour 50%
		toachieve = toachieve - b.countHole();// enlever les obstacles
		int fp = 0;
		int im = 0;
		boolean lef = true;
		int ang = 0;
		Font font = new Font("Arial", Font.BOLD, (640) * 12 / (7 * (b.getHeight() * 6 + 18)));
		if (servor && !saving) {

			try {
				FileServor.main(true, true);
			} catch (IOException e) {
			}

		}
		if (servor && saving && ld) {
			b = b.readMulti(true);
			this.h = b.getHat();
			if (b.getNbPlayer() == 2) {
				IA[2] = -1;
				IA[3] = -1;
			}
			b.setWidth(b.getBigBoard().length);
			b.setHeight(b.getBigBoard()[0].getBox().length);
			w = b.getBigBoard().length;
			h = b.getBigBoard()[0].getBox().length;
			if (b.getPlayTurn() < 1) {
				b.setPlayTurn(1);
			}
		}
		if (s != null && r != null) {
			System.out.print(s.isAlive());
			System.out.print(s.getState());
			System.out.print(r.isAlive());
			System.out.print(r.getState());
		}

		Animation ano = new Animation();
		ano.setPlayerHat(this.h);
		for (int i = 0; i < IA.length; i++) {
			if (IA[i] == 0) {
				players[i] = new HumanBoard(b, i, ano.getPlayerHat(), true);
			} else if (IA[i] > 0) {
				players[i] = new IA(b, IA[i], i, ano.getPlayerHat());
			} else if (IA[i] == -1) {
				players[i] = null;
			}
		}
		// -----------------------------------------------------Boucle win
		while (!wons) {// boucle principale
			System.out.println("nombre joueur " + b.getNbPlayer());
			System.out.println("IA " + IA[2] + "other one " + IA[3]);

			if (saving && !ld) {
				System.out.println("IAss " + Gamu.iaStock[2] + "other oness " + Gamu.iaStock[3]);
				Gamu.iaStock = IA;
				System.out.println("IAssyy " + Gamu.iaStock[2] + "other onessyy " + Gamu.iaStock[3]);
				b.setHat(this.h);
				b.saveMulti(true);
				if (b.getNbPlayer() == 2) {
					IA[2] = -1;
					IA[3] = -1;
				}
				this.startiaMult(w, h, IA, false, false, true, false, false, r, s);
				return;
			}
			if (servor) {
				IA = Gamu.iaStock;
				for (int i = 0; i < IA.length; i++) {
					if (IA[i] == 0) {
						players[i] = new HumanBoard(b, i, this.h, true);
					} else if (IA[i] > 0) {
						players[i] = new IA(b, IA[i], i, this.h);
					} else if (IA[i] == -1) {
						players[i] = null;
					}
				}
			}
			int ent = b.getColor1();
			int[] transi;
			wons = !(won(toachieve, b.getScores()) == 0);
			System.out.println("Tour du joueur " + b.getPlayTurn());
			System.out.println("Qui a " + b.getScores()[b.getPlayTurn() - 1] + " points");

			// -------------------------tour des joueur
			if (!servor) {// ----------------------------------client
				if (b.getPlayTurn() == 2) {// si notre tour
					b.updateBoard();
					b.drawBoard();
					StdDraw.show(1);
					System.out.println("client joue");
					ent = players[b.getPlayTurn() - 1].play(b, ent, ang, lef, fp, im, lengthT(players),
							b.getPlayTurn());
					if (b.getPlayTurn() == 1) {
						b.setColor1(ent);
					}
					if (b.getPlayTurn() == 2) {
						b.setColor2(ent);
					}
					if (b.getPlayTurn() == 3) {
						b.setColor3(ent);
					}
					if (b.getPlayTurn() == 4) {
						b.setColor4(ent);
					}
					b.saveMulti(false);
					try {
						if (FileServor.servsock != null)
							FileServor.servsock.close();
					} catch (IOException e1) {
					}
					try {
						FileServor.main(false, false);
					} catch (IOException e) {
						e.printStackTrace();
					}

				} else {// attente du joueur ou de l'ia
					boolean gotIt = false;
					b.updateBoard();
					b.drawBoard();
					ano.drawPlayer(b.getPlayTurn(), 0, b, true);// draw
					StdDraw.show(1);
					System.out.println("client attend");
					FileClient.main(false, false);
					b = b.readMulti(false);
					this.h = b.getHat();
					ano.setPlayerHat(this.h);
					if (b.getNbPlayer() == 2) {
						IA[2] = -1;
						IA[3] = -1;
					}
					b.setNbPlayer(lengthT2(IA));
					for (int i = 0; i < IA.length; i++) {
						if (IA[i] == 0) {
							players[i] = new HumanBoard(b, i, this.h, true);
						} else if (IA[i] > 0) {
							players[i] = new IA(b, IA[i], i, this.h);
						} else if (IA[i] == -1) {
							players[i] = null;
						}
					}
					if (b.getPlayTurn() == 1) {
						ent = b.getColor1();
					}
					if (b.getPlayTurn() == 2) {
						ent = b.getColor2();
					}
					if (b.getPlayTurn() == 3) {
						ent = b.getColor3();
					}
					if (b.getPlayTurn() == 4) {
						ent = b.getColor4();
					}
					b.setWidth(b.getBigBoard().length);
					b.setHeight(b.getBigBoard()[0].getBox().length);
					w = b.getBigBoard().length;
					h = b.getBigBoard()[0].getBox().length;
					toachieve = w * h - w / 2;// points à obtenir pour 50%
					toachieve = toachieve - b.countHole();// enlever les
															// obstacles
					b.updateBoard();
					b.drawBoard();
					StdDraw.show(1);
				}
			} else {// si c'est le serveur
				if (b.getPlayTurn() != 2) {// si notre tour
					b.afficherTab();
					StdDraw.show(1);
					System.out.println("serveur joue");
					ent = players[b.getPlayTurn() - 1].play(b, ent, ang, lef, fp, im, lengthT(players),
							b.getPlayTurn());
					if (b.getPlayTurn() == 1) {
						b.setColor1(ent);
					}
					if (b.getPlayTurn() == 2) {
						b.setColor2(ent);
					}
					if (b.getPlayTurn() == 3) {
						b.setColor3(ent);
					}
					if (b.getPlayTurn() == 4) {
						b.setColor4(ent);
					}
					b.saveMulti(true);
					try {
						if (FileServor.servsock != null)
							FileServor.servsock.close();
					} catch (IOException e1) {
					}
					try {
						FileServor.main(false, true);
					} catch (IOException e) {
						e.printStackTrace();
					}
					// }

				} else {
					b.updateBoard();
					b.drawBoard();
					ano.drawPlayer(b.getPlayTurn(), 0, b, true);// draw
					StdDraw.show(1);
					boolean gotIt = false;
					System.out.println("serveur attend");
					FileClient.main(true, false);
					b = b.readMulti(true);
					if (b.getPlayTurn() == 1) {
						ent = b.getColor1();
					}
					if (b.getPlayTurn() == 2) {
						ent = b.getColor2();
					}
					if (b.getPlayTurn() == 3) {
						ent = b.getColor3();
					}
					if (b.getPlayTurn() == 4) {
						ent = b.getColor4();
					}
					b.setWidth(b.getBigBoard().length);
					b.setHeight(b.getBigBoard()[0].getBox().length);
					w = b.getBigBoard().length;
					h = b.getBigBoard()[0].getBox().length;
					toachieve = w * h - w / 2;// points à obtenir pour 50%
					toachieve = toachieve - b.countHole();// enlever les
															// obstacles
					b.updateBoard();
					b.drawBoard();
					StdDraw.show(1);

				}
			}

			System.out.println(ent);
			wons = !(won(toachieve, b.getScores()) == 0);
			nextTurn(b, ent, b.getNbPlayer());
			b.upscore(1);
			b.upscore(2);
			b.upscore(3);
			b.upscore(4);
			tours++;// ------------------augmente le tour pour repaire
			System.out.println(tours);
			if (this.graph) {
				StdDraw.show(1);
			} else {
				System.out.println("");
				System.out.println("Couleurs :");
				b.drawBoard();// couleurs
				System.out.println("");
				System.out.println("Propriétaires :");
				b.drawBoardp();// proprietaire
			}

		}
		if (this.graph) {
			WinScreen ww = new WinScreen(b, won(toachieve, b.getScores()), ano.getPlayerHat());
			ww.afficher();
			drawin(b, toachieve, b.getscore());
		}
		System.out.println("");
		System.out.print("Player " + won(toachieve, b.getScores()));
		System.out.println(" won !");
		b.setPlayTurn(0);
		sca.close();
		return;
	}

	// --------------------------------win
	// animation-------------------------------------------
	public void drawin(Board bb, int toachieve, int[] score) {
		StdDraw.setXscale(0, this.wh);
		StdDraw.setYscale(0, this.hh);
		StdDraw.setPenColor(StdDraw.YELLOW);
		StdDraw.filledRectangle(this.wh / 2, this.hh / 2, this.wh / 2 * 0.8, this.hh / 2 * 0.8);
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.text(this.wh / 2, this.hh / 2, "Player " + won(toachieve, score) + " won !");
		StdDraw.show(1);
	}

	public boolean isEntourer() {
		return entourer;
	}

	public void setEntourer(boolean entourer) {
		this.entourer = entourer;
	}

	public int getMapLoad() {
		return mapLoad;
	}

	public void setMapLoad(int mapLoad) {
		this.mapLoad = mapLoad;
	}

	public int getForme() {
		return forme;
	}

	public void setForme(int forme) {
		this.forme = forme;
	}

	public boolean isDiamond() {
		return diamond;
	}

	public void setDiamond(boolean diamond) {
		this.diamond = diamond;
	}

	public boolean isBrouillard() {
		return brouillard;
	}

	public void setBrouillard(boolean brouillard) {
		this.brouillard = brouillard;
	}

}
