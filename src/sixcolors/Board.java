/**
* @author Louis
* Plateau de jeu
*/
package sixcolors;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.Math;
import java.util.Scanner;

import edu.princeton.cs.introcs.StdDraw;

/* Classe : Board
 * But : décrit le tableau de jeu
 * 
 * 
 * */
public class Board implements Serializable {

	private int width = 13;// must be an odd number like 13
	private int height = 13;// must also be an odd number
	private String[] colorst = { "vert", "bleu", "rouge", "jaune", "violet", "rose" };// color
	// name
	private String[] colorim = { ".\\images/Hexagotestcolor.png", ".\\images/Hexagotestcolor1.png",
			".\\images/Hexagotestcolor2.png", ".\\images/Hexagotestcolor3.png", ".\\images/Hexagotestcolor4.png",
			".\\images/Hexagotestcolor5.png", ".\\images/Hexagotestcolor6.png" };// image
																					// path
																					// that
																					// is
																					// used

	private String[] colorimhex = { ".\\images/Hexagotestcolor.png", ".\\images/Hexagotestcolor1.png",
			".\\images/Hexagotestcolor2.png", ".\\images/Hexagotestcolor3.png", ".\\images/Hexagotestcolor4.png",
			".\\images/Hexagotestcolor5.png", ".\\images/Hexagotestcolor6.png" };// image
																					// paths
																					// for
																					// colors
	private String[] colorimsq = { ".\\images/sq1.png", ".\\images/sq2.png", ".\\images/sq3.png", ".\\images/sq4.png",
			".\\images/sq5.png", ".\\images/sq6.png", ".\\images/sq7.png" };
	private String[] wallimSq = { ".\\images/sqwalll.png", ".\\images/sqwalld.png" };
	private String[] wallim = { ".\\images/wallul.png", ".\\images/wallu.png", ".\\images/wallur.png",
			".\\images/hexhouse.png", ".\\images/walldl.png", ".\\images/walld.png", ".\\images/walldr.png" };
	private String[] wallimD = { ".\\images/rightu2.png", ".\\images/leftu2.png" };
	private int color1 = 1;
	private int color2 = 2;
	private int color3 = 3;
	private int color4 = 4;
	private int nbPlayer = 2;
	private int[] scores = new int[4];
	private int playTurn = 1;
	private int forme = 3;// 1:square,1+diamondtrue:diamond,3:hexa
	private boolean diamond = true;// TODO set false
	private int wh = 640 + 100;// window width in pixel
	private int hh = 640;// window height in pixel
	private boolean entourer = true;// regle entourer
	private int[] hat;// chapeaux pour multi
	private boolean brouillard = false;

	public int getColorP(int p) {
		if (p == 1) {
			return color1;
		}
		if (p == 2) {
			return color2;
		}
		if (p == 3) {
			return color3;
		}
		if (p == 4) {
			return color4;
		}
		return 0;
	}

	public Board() {

	}

	// clonage
	public Board(Board tocopy) {
		this.width = tocopy.width;
		this.height = tocopy.height;
		this.nbPlayer = tocopy.nbPlayer;
		this.bigBoard = new Column[tocopy.bigBoard.length];

		for (int i = 0; i < tocopy.bigBoard.length; i++) {
			this.bigBoard[i] = new Column(tocopy.bigBoard[i]);
		}

		// make neighbors
		for (int k = 0; k < this.width; k++) {
			int l = this.bigBoard[k].getBox().length;
			for (int m = 0; m < l; m++) {
				this.bigBoard[k].getBox()[m].setneigh(bigBoard);
			}
		}
		this.color1 = tocopy.color1;
		this.color2 = tocopy.color2;
		this.color3 = tocopy.color3;
		this.color4 = tocopy.color4;
		this.playTurn = tocopy.playTurn;
		this.forme = tocopy.forme;
	}

	// ---------------------------creation Board
	private Column[] bigBoard;

	Board(int w, int h, int nbp, int f) {
		this.width = w;
		this.forme = f;
		this.height = h;
		this.nbPlayer = nbp;
		this.bigBoard = new Column[w];
		this.scores[0] = 1;
		this.scores[1] = 1;
		this.scores[2] = 1;
		this.scores[3] = 1;
		// build collumns
		for (int j = 0; j < w; j++) {
			if ((j + 1) % 2 == 0 && this.forme == 3) {
				this.bigBoard[j] = new Column(h - 1, j + 1, w, this.forme, this.nbPlayer);
			} // even
			else {
				this.bigBoard[j] = new Column(h, j + 1, w, this.forme, this.nbPlayer);
			} // odd
		}
		if (this.nbPlayer == 4) {
			this.bigBoard[0].getBox()[h - 1].setColor(4);
			this.bigBoard[0].getBox()[h - 1].setProp(4);
			this.bigBoard[w - 1].getBox()[0].setColor(3);
			this.bigBoard[w - 1].getBox()[0].setProp(3);

		}
		// make neighbors
		for (int k = 0; k < w; k++) {
			int l = this.bigBoard[k].getBox().length;
			for (int m = 0; m < l; m++) {
				this.bigBoard[k].getBox()[m].setneigh(bigBoard);
			}
		}

		// if you're lucky you get the ones near you with same color
		Hexa[] list = new Hexa[120];
		this.bigBoard[0].getBox()[0].checkNeigh(1, 1, list, 0, this.playTurn, this);
		this.playTurn = 2;// count the points for p2
		list = new Hexa[w * h];
		this.bigBoard[w - 1].getBox()[h - 1].checkNeigh(2, 2, list, 0, this.playTurn, this);
		if (this.nbPlayer == 4) {
			// System.out.println("four players confirmed");
			this.playTurn = 3;// count the points for p3
			list = new Hexa[120];
			this.bigBoard[w - 1].getBox()[0].checkNeigh(3, 3, list, 0, this.playTurn, this);
			this.playTurn = 4;// count the points for p4
			list = new Hexa[120];
			this.bigBoard[0].getBox()[h - 1].checkNeigh(4, 4, list, 0, this.playTurn, this);
			this.upscore(3);
			this.upscore(4);
		}
		playTurn = 1;// returns to p1
		// update the scores
		this.upscore(1);
		this.upscore(2);

	}

	public void luckyUp(int w, int h) {
		for (int k = 0; k < w; k++) {
			int l = this.bigBoard[k].getBox().length;
			for (int m = 0; m < l; m++) {
				this.bigBoard[k].getBox()[m].setneigh(bigBoard);
			}
		}
		// if you're lucky you get the ones near you with same color
		Hexa[] list = new Hexa[w * h];
		this.bigBoard[0].getBox()[0].checkNeigh(1, 1, list, 0, playTurn, this);
		playTurn = 2;// count the points for p2
		list = new Hexa[120];
		this.bigBoard[w - 1].getBox()[h - 1].checkNeigh(2, 2, list, 0, playTurn, this);
		if (this.nbPlayer == 4) {
			playTurn = 3;// count the points for p3
			list = new Hexa[120];
			this.bigBoard[w - 1].getBox()[0].checkNeigh(3, 3, list, 0, playTurn, this);
			playTurn = 4;// count the points for p4
			list = new Hexa[120];
			this.bigBoard[0].getBox()[h - 1].checkNeigh(4, 4, list, 0, playTurn, this);
			this.upscore(3);
			this.upscore(4);

		}
		for (int k = 0; k < w; k++) {
			int l = this.bigBoard[k].getBox().length;
			for (int m = 0; m < l; m++) {
				this.bigBoard[k].getBox()[m].setneigh(bigBoard);
			}
		}
		playTurn = 1;// returns to p1
	}

	public int countHole() {
		int c = 0;
		for (int k = 0; k < this.width; k++) {
			int l = this.bigBoard[k].getBox().length;
			for (int m = 0; m < l; m++) {
				if (this.bigBoard[k].getBox()[m].getColor() == 6) {
					c++;
				}
			}
		}
		return c;
	}

	// ------------afficher terminal------------------------
	public void drawBoard() {
		for (int k = 0; k < this.width; k++) {
			this.bigBoard[k].drawcol();
		}
	}

	public void drawBoardp() {
		for (int k = 0; k < this.width; k++) {
			this.bigBoard[k].drawcolp();
		}
		System.out.println(this.bigBoard[1].getBox()[0].getVd().getColor());
	}

	// ------------afficher fenetre -------------------
	public void afficherMur(double tailleCase) {
		double ecartV = (this.hh * 0.8 - this.height * tailleCase);
		double ecartH = (this.wh * 0.75 - this.width * tailleCase) / 6;
		if (this.forme == 1)
			ecartH = -tailleCase / 2;
		if (this.forme == 3) {
			for (int i = 0; i < this.width; i++) {
				for (int j = 0; j < this.height; j++) {
					if (this.forme == 3 && j != this.height - 1 || this.bigBoard[i].getid() % 2 != 0) {
						// add walls and house
						if (this.playTurn != 0) {
							if (this.bigBoard[i].getid() % 2 != 0) {

								if (this.bigBoard[i].getBox()[j].getVd() != null && this.bigBoard[i].getBox()[j].getVd()
										.getProp() != this.bigBoard[i].getBox()[j].getProp()) {
									StdDraw.picture(tailleCase * (i + 1) + ecartH - tailleCase * (i + 1) * 0.25,
											tailleCase * j + tailleCase / 2 + ecartV, this.wallim[1], tailleCase * 1.5,
											tailleCase * 1.5, 0);
								}
								if (this.bigBoard[i].getBox()[j].getVul() != null && this.bigBoard[i].getBox()[j]
										.getVul().getProp() != this.bigBoard[i].getBox()[j].getProp()) {
									StdDraw.picture(tailleCase * (i + 1) + ecartH - tailleCase * (i + 1) * 0.25,
											tailleCase * j + tailleCase / 2 + ecartV, this.wallim[4], tailleCase,
											tailleCase, 0);
								}
								if (this.bigBoard[i].getBox()[j].getVdl() != null && this.bigBoard[i].getBox()[j]
										.getVdl().getProp() != this.bigBoard[i].getBox()[j].getProp()) {
									StdDraw.picture(tailleCase * (i + 1) + ecartH - tailleCase * (i + 1) * 0.25,
											tailleCase * j + tailleCase / 2 + ecartV, this.wallim[0], tailleCase * 1.5,
											tailleCase * 1.5, 0);
								}

							} else {// ------------------------------------------------------------------
								if (this.bigBoard[i].getBox()[j].getVdl() != null && this.bigBoard[i].getBox()[j]
										.getVdl().getProp() != this.bigBoard[i].getBox()[j].getProp()) {
									StdDraw.picture(tailleCase * (i + 1) + ecartH - tailleCase * (i + 1) * 0.25,
											tailleCase * j + tailleCase + ecartV, this.wallim[0], tailleCase * 1.5,
											tailleCase * 1.5, 0);
								}
								if (this.bigBoard[i].getBox()[j].getVd() != null && this.bigBoard[i].getBox()[j].getVd()
										.getProp() != this.bigBoard[i].getBox()[j].getProp()) {
									StdDraw.picture(tailleCase * (i + 1) + ecartH - tailleCase * (i + 1) * 0.25,
											tailleCase * j + tailleCase + ecartV, this.wallim[1], tailleCase * 1.5,
											tailleCase * 1.5, 0);
								}
								if (this.bigBoard[i].getBox()[j].getVul() != null && this.bigBoard[i].getBox()[j]
										.getVul().getProp() != this.bigBoard[i].getBox()[j].getProp()) {
									StdDraw.picture(tailleCase * (i + 1) + ecartH - tailleCase * (i + 1) * 0.25,
											tailleCase * j + tailleCase + ecartV, this.wallim[4], tailleCase,
											tailleCase, 0);
								}

							}
						}
					}
				}
			}
		} else if (this.forme == 1) {
			if (!this.diamond) {
				for (int i = 0; i < this.width; i++) {
					for (int j = this.height - 1; j >= 0; j--) {
						if (this.playTurn != 0) {
							// ------------------------------------------------------------------
							if (this.bigBoard[i].getBox()[j].getVd() != null && this.bigBoard[i].getBox()[j].getVd()
									.getProp() != this.bigBoard[i].getBox()[j].getProp()) {
								StdDraw.picture(tailleCase * (i + 1) + ecartH, tailleCase * (j + 2) + ecartV,
										this.wallimSq[1], tailleCase, tailleCase, 0);
							}
							if (this.bigBoard[i].getBox()[j].getVul() != null && this.bigBoard[i].getBox()[j].getVul()
									.getProp() != this.bigBoard[i].getBox()[j].getProp()) {
								StdDraw.picture(tailleCase * (i + 1) + ecartH, tailleCase * (j + 1 + 0.24) + ecartV,
										this.wallimSq[0], tailleCase, tailleCase * 1.375, 0);
							}

						}
					}
				}
			} else {
				for (int i = 0; i < this.width; i++) {
					for (int j = this.height - 1; j >= 0; j--) {
						if (this.playTurn != 0) {
							// ------------------------------------------------------------------
							if (this.bigBoard[i].getBox()[j].getVd() != null && this.bigBoard[i].getBox()[j].getVd()
									.getProp() != this.bigBoard[i].getBox()[j].getProp()) {/////////// ::::::lfd\
								StdDraw.picture(tailleCase * (i + 1 / 2 + 1) + ecartH + tailleCase * (j + 1 / 2),
										tailleCase * (j + 1) + ecartV - tailleCase * (i), this.wallimD[0],
										tailleCase * 3, tailleCase * 3, 0);
							}
							if (this.bigBoard[i].getBox()[j].getVul() != null && this.bigBoard[i].getBox()[j].getVul()
									.getProp() != this.bigBoard[i].getBox()[j].getProp()) {/////////// ::::::lfu/
								StdDraw.picture(tailleCase * (i + 1) + ecartH + tailleCase * (j + 1 / 2 + 1 / 4),
										tailleCase * (j + 1 + 1 / 2) + ecartV - tailleCase * (i), this.wallimD[1],
										tailleCase * 3, tailleCase * 3, 0);
							}

						}
					}
				}
			}
		}
	}

	public void afficherTab() {// afficher dans fenetre
		StdDraw.setXscale(0, this.wh);
		StdDraw.setYscale(0, this.hh);
		StdDraw.setPenColor(StdDraw.BLACK);
		double tailleCase = 0;
		if (this.width > this.height) {
			tailleCase = this.wh * 0.75 / this.width;
		} else {
			tailleCase = this.hh * 0.80 / this.height;
		}
		if (this.diamond && forme == 1) {
			tailleCase = tailleCase / 2;
		}
		double ecartV = (this.hh * 0.8 - this.height * tailleCase);
		double ecartH = (this.wh * 0.75 - this.width * tailleCase) / 6;
		if (this.forme == 1)
			ecartH = -tailleCase / 2;
		StdDraw.filledRectangle(this.wh * 0.375, this.hh * 0.4, this.wh * 0.375, this.hh * 0.6);
		Font font = new Font("Arial", Font.BOLD, (int) (tailleCase / 2));
		StdDraw.setFont(font);
		if (this.forme == 3) {
			// cases
			for (int i = 0; i < this.width; i++) {
				for (int j = 0; j < this.height; j++) {
					if (j != this.height - 1 || this.bigBoard[i].getid() % 2 != 0) {

						if (this.bigBoard[i].getid() % 2 != 0) {
							StdDraw.picture(tailleCase * (i + 1) + ecartH - tailleCase * (i + 1) * 0.25,
									tailleCase * j + tailleCase / 2 + ecartV,
									this.colorim[this.bigBoard[i].getBox()[j].getColor()], tailleCase, tailleCase, 0);
							if (brouillard && ((Menu.servor && !this.bigBoard[i].getBox()[j].haveN(1))
									|| (!Menu.servor && !this.bigBoard[i].getBox()[j].haveN(2)))) {
								StdDraw.picture(tailleCase * (i + 1) + ecartH - tailleCase * (i + 1) * 0.25,
										tailleCase * j + tailleCase / 2 + ecartV, this.colorim[6], tailleCase,
										tailleCase, 0);
								StdDraw.setPenColor(StdDraw.RED);
								StdDraw.text(tailleCase * (i + 1) + ecartH - tailleCase * (i + 1) * 0.25,
										tailleCase * j + tailleCase / 2 + ecartV, "?");
							}
						} else {// Column less numerous
							StdDraw.picture(tailleCase * (i + 1) + ecartH - tailleCase * (i + 1) * 0.25,
									tailleCase * j + tailleCase + ecartV,
									this.colorim[this.bigBoard[i].getBox()[j].getColor()], tailleCase, tailleCase, 0);
							if (brouillard && ((Menu.servor && !this.bigBoard[i].getBox()[j].haveN(1))
									|| (!Menu.servor && !this.bigBoard[i].getBox()[j].haveN(2)))) {
								StdDraw.picture(tailleCase * (i + 1) + ecartH - tailleCase * (i + 1) * 0.25,
										tailleCase * j + tailleCase + ecartV, this.colorim[6], tailleCase, tailleCase,
										0);
								StdDraw.setPenColor(StdDraw.RED);
								StdDraw.text(tailleCase * (i + 1) + ecartH - tailleCase * (i + 1) * 0.25,
										tailleCase * j + tailleCase + ecartV, "?");
							}
						}
					}
				}
			}
			// murs
			afficherMur(tailleCase);
			// maisons
			StdDraw.picture(tailleCase * (0 + 1) + ecartH - tailleCase * (0 + 1) * 0.25,
					tailleCase * (-0.5) + tailleCase + ecartV, this.wallim[3], tailleCase, tailleCase, 0);// m1
			StdDraw.picture(tailleCase * (this.width - 1 + 1) + ecartH - tailleCase * (this.width - 1 + 1) * 0.25,
					tailleCase * (this.height - 1.5) + tailleCase + ecartV, this.wallim[3], tailleCase, tailleCase, 0);// m2
			if (this.nbPlayer == 4) {
				StdDraw.picture(tailleCase * (this.width - 1 + 1) + ecartH - tailleCase * (this.width - 1 + 1) * 0.25,
						tailleCase * (-0.5) + tailleCase + ecartV, this.wallim[3], tailleCase, tailleCase, 0);// m3
				StdDraw.picture(tailleCase * (0 + 1) + ecartH - tailleCase * (0 + 1) * 0.25,
						tailleCase * (this.height - 1.5) + tailleCase + ecartV, this.wallim[3], tailleCase, tailleCase,
						0);// m4
			}

		} else if (this.forme == 1) {// squares
			if (!this.diamond) {
				for (int i = 0; i < this.width; i++) {
					for (int j = 0; j < this.height; j++) {
						StdDraw.picture(tailleCase * (i + 1) + ecartH, tailleCase * (j + 1) + ecartV,
								this.colorimsq[this.bigBoard[i].getBox()[j].getColor()], tailleCase, tailleCase, 0);
						if (brouillard && ((Menu.servor && !this.bigBoard[i].getBox()[j].haveN(1))
								|| (!Menu.servor && !this.bigBoard[i].getBox()[j].haveN(2)))) {
							StdDraw.picture(tailleCase * (i + 1) + ecartH, tailleCase * (j + 1) + ecartV,
									this.colorimsq[6], tailleCase, tailleCase, 0);
							StdDraw.setPenColor(StdDraw.RED);
							StdDraw.text(tailleCase * (i + 1) + ecartH, tailleCase * (j + 1) + ecartV, "?");
						}
					}
				}
			} else {
				for (int i = 0; i < this.width; i++) {
					for (int j = 0; j < this.height; j++) {
						StdDraw.picture(tailleCase * (i + 1) + ecartH + tailleCase * (j),
								tailleCase * (j + 1) + ecartV - tailleCase * (i),
								this.colorimsq[this.bigBoard[i].getBox()[j].getColor()], tailleCase, tailleCase, 45);
						if (brouillard && ((Menu.servor && !this.bigBoard[i].getBox()[j].haveN(1))
								|| (!Menu.servor && !this.bigBoard[i].getBox()[j].haveN(2)))) {
							StdDraw.picture(tailleCase * (i + 1) + ecartH + tailleCase * (j),
									tailleCase * (j + 1) + ecartV - tailleCase * (i), this.colorimsq[6], tailleCase,
									tailleCase, 45);
							StdDraw.setPenColor(StdDraw.RED);
							StdDraw.text(tailleCase * (i + 1) + ecartH + tailleCase * (j),
									tailleCase * (j + 1) + ecartV - tailleCase * (i), "?");
						}
					}
				}
			}

			// maisons
			if (!this.diamond) {
				StdDraw.picture(tailleCase * (0 + 1) + ecartH,
						tailleCase * (-0.5) + tailleCase + ecartV + tailleCase * 0.5, this.wallim[3], tailleCase,
						tailleCase, 0);// m1
				StdDraw.picture(tailleCase * (this.width - 1 + 1) + ecartH,
						tailleCase * (this.height - 1.5) + tailleCase + ecartV + tailleCase * 0.5, this.wallim[3],
						tailleCase, tailleCase, 0);// m2
				if (this.nbPlayer == 4) {
					StdDraw.picture(tailleCase * (this.width - 1 + 1) + ecartH,
							tailleCase * (-0.5) + tailleCase + ecartV + tailleCase * 0.5, this.wallim[3], tailleCase,
							tailleCase, 0);// m3
					StdDraw.picture(tailleCase * (0 + 1) + ecartH,
							tailleCase * (this.height - 1.5) + tailleCase + ecartV + tailleCase * 0.5, this.wallim[3],
							tailleCase, tailleCase, 0);// m4
				}
			} else {
				StdDraw.picture(tailleCase * (0 + 1) + ecartH + tailleCase * (0),
						tailleCase * (0 + 1) + ecartV - tailleCase * (0), this.wallim[3], tailleCase, tailleCase, 0);// m1
				StdDraw.picture(tailleCase * (this.width - 1 + 1) + ecartH + tailleCase * (this.height - 1),
						tailleCase * (this.height - 1 + 1) + ecartV - tailleCase * (this.width - 1), this.wallim[3],
						tailleCase, tailleCase, 0);// m2
				if (this.nbPlayer == 4) {
					StdDraw.picture(tailleCase * (this.width - 1 + 1) + ecartH + tailleCase * (0),
							tailleCase * (0 + 1) + ecartV - tailleCase * (this.width - 1), this.wallim[3], tailleCase,
							tailleCase, 0);// m3
					StdDraw.picture(tailleCase * (0 + 1) + ecartH + tailleCase * (this.height - 1),
							tailleCase * (this.height - 1 + 1) + ecartV - tailleCase * (0), this.wallim[3], tailleCase,
							tailleCase, 0);// m4
				}
			}
			// murs
			afficherMur(tailleCase);
		}

	}

	public void updateBoard() {
		if (this.nbPlayer == 2) {
			this.color3 = 9;// valeurs absurdes
			this.color4 = 9;
		}

		if (playTurn == 1) {
			Hexa[] list = new Hexa[this.width * this.height];
			this.bigBoard[0].getBox()[0].checkNeigh(color1, 1, list, 0, playTurn, this);
			if (this.entourer)
				Hexa.entourerF(this.color1, this.playTurn, this.width * this.height, 2, 3, 4);
		} else if (playTurn == 2) {
			Hexa[] list = new Hexa[this.width * this.height];
			this.bigBoard[width - 1].getBox()[height - 1].checkNeigh(color2, 2, list, 0, playTurn, this);
			if (this.entourer)
				Hexa.entourerF(this.color2, this.playTurn, this.width * this.height, 1, 3, 4);
		} else if (playTurn == 3) {
			Hexa[] list = new Hexa[this.width * this.height];
			this.bigBoard[width - 1].getBox()[0].checkNeigh(color3, 3, list, 0, playTurn, this);
			if (this.entourer)
				Hexa.entourerF(this.color3, this.playTurn, this.width * this.height, 2, 1, 4);
		} else if (playTurn == 4) {
			Hexa[] list = new Hexa[this.width * this.height];
			this.bigBoard[0].getBox()[height - 1].checkNeigh(color4, 4, list, 0, playTurn, this);
			if (this.entourer)
				Hexa.entourerF(this.color4, this.playTurn, this.width * this.height, 2, 3, 1);
		}

	}

	public int[] getscore() {
		return scores;
	}

	public void upscore(int pp) {
		int sc = 0;
		for (int k = 0; k < this.width; k++) {
			int l = this.bigBoard[k].getBox().length;
			for (int m = 0; m < l; m++) {
				if (this.bigBoard[k].getBox()[m].getProp() == pp) {
					sc++;
				}
			}
		}
		this.scores[pp - 1] = sc;

	}

	// ---------------save and
	// load----------------------------------------------------------------------
	public void save() {// sauvegarder le fichier
		File f = new File("box.object");

		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(this);
			oos.close();
		} catch (IOException exception) {
			System.out.println("Erreur lors de l'écriture : " + exception.getMessage());
		}

	}

	public void saveMulti(boolean servor) {// sauvegarder le fichier
		Board save = new Board();
		String a = "bin/multiMap.object";
		if (servor) {
			a = "src/multiMap.object";
		}
		File f = new File(a);

		try {
			System.out.println("saving");
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(this);
			oos.close();
		} catch (IOException exception) {
			System.out.println("Erreur lors de l'écriture (saveMulti) : " + exception.getMessage());
		}

	}

	public Board readMulti(boolean servor) {
		Board save = new Board();
		String a = "bin/multiMap.object";
		if (servor) {
			a = "src/multiMap.object";
		}
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(a));
			save = (Board) ois.readObject();
			ois.close();
			return save;

		} catch (ClassNotFoundException exception) {
			System.out.println("Impossible de lire l'objet : " + exception.getMessage());
		} catch (IOException exception) {
			System.out.println("Erreur lors de l'écriture (readMulti): " + exception.getMessage());
		}
		return save;
	}

	public void savebonly(boolean editor) {// sauvegarder le fichier
		boolean found = false;
		int i = 0;
		while (!found) {
			Board save = new Board();
			try {
				String a = "";
				if (editor) {
					a = "myBoard";
				} else {
					a = "savedGame";
				}
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(a + (i) + ".object"));
				save = (Board) ois.readObject();
				ois.close();
				i++;
			} catch (ClassNotFoundException exception) {
				found = true;
			} catch (IOException exception) {
				found = true;
			}
		}
		String a = "";
		if (editor) {
			a = "myBoard";
		} else {
			a = "savedGame";
		}
		File f = new File(a + (i) + ".object");

		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(this);
			oos.close();
		} catch (IOException exception) {
			System.out.println("Erreur lors de l'écriture : " + exception.getMessage());
		}

	}

	public Board readit(boolean aut) {
		Board save = new Board();
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("box.object"));
			save = (Board) ois.readObject();
			ois.close();
			return save;

		} catch (ClassNotFoundException exception) {
			System.out.println("Impossible de lire l'objet : " + exception.getMessage());
		} catch (IOException exception) {
			System.out.println("Erreur lors de l'écriture : " + exception.getMessage());
		}
		return save;
	}

	public Board readmyBoard(boolean editor, int i) {
		Board save = new Board();
		try {
			String a = "";
			if (editor) {
				a = "myBoard" + i + ".object";
			} else {
				a = "savedgame" + i + ".object";
			}
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(a));
			save = (Board) ois.readObject();
			ois.close();
			return save;

		} catch (ClassNotFoundException exception) {
			System.out.println("Impossible de lire l'objet : " + exception.getMessage());
		} catch (IOException exception) {
			System.out.println("Erreur lors de l'écriture : " + exception.getMessage());
		}
		return save;
	}

	// --------------------------get and
	// set--------------------------------------------------------------------
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String[] getColorst() {
		return colorst;
	}

	public void setColorst(String[] colorst) {
		this.colorst = colorst;
	}

	public String[] getColorim() {
		return colorim;
	}

	public void setColorim(String[] colorim) {
		this.colorim = colorim;
	}

	public String[] getWallim() {
		return wallim;
	}

	public void setWallim(String[] wallim) {
		this.wallim = wallim;
	}

	public int getColor1() {
		return color1;
	}

	public void setColor1(int color1) {
		this.color1 = color1;
	}

	public int getColor2() {
		return color2;
	}

	public void setColor2(int color2) {
		this.color2 = color2;
	}

	public int getColor3() {
		return color3;
	}

	public void setColor3(int color3) {
		this.color3 = color3;
	}

	public int getColor4() {
		return color4;
	}

	public void setColor4(int color4) {
		this.color4 = color4;
	}

	public int getNbPlayer() {
		return nbPlayer;
	}

	public void setNbPlayer(int nbPlayer) {
		this.nbPlayer = nbPlayer;
	}

	public int[] getScores() {
		return scores;
	}

	public void setScores(int[] scores) {
		this.scores = scores;
	}

	public int getPlayTurn() {
		return playTurn;
	}

	public void setPlayTurn(int playTurn) {
		this.playTurn = playTurn;
	}

	public Column[] getBigBoard() {
		return bigBoard;
	}

	public void setBigBoard(Column[] bigBoard) {
		this.bigBoard = bigBoard;
	}

	public String[] getColorimsq() {
		return colorimsq;
	}

	public void setColorimsq(String[] colorimsq) {
		this.colorimsq = colorimsq;
	}

	public boolean isEntourer() {
		return entourer;
	}

	public void setEntourer(boolean entourer) {
		this.entourer = entourer;
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

	public int[] getHat() {
		return hat;
	}

	public void setHat(int[] hat) {
		this.hat = hat;
	}

	public boolean isBrouillard() {
		return brouillard;
	}

	public void setBrouillard(boolean brouillard) {
		this.brouillard = brouillard;
	}

}
