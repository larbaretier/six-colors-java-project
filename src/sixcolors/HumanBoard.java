package sixcolors;

import java.awt.Font;
import java.util.Scanner;

import edu.princeton.cs.introcs.StdDraw;

/* Classe : HumanBoard
 * But : faire jouer un joueur humain avec la souris
 * 
 * 
 * */
public class HumanBoard extends Player {
	private int[] hat;
	private Animation ano = new Animation();
	private int wh = 640 + 100;// window width in pixel
	private int hh = 640;// window height in pixel
	public static boolean multi = false;

	public HumanBoard(Board b, int c, int[] h, boolean m) {
		this.setType(1);
		this.setColor(c);
		this.hat = h;
		this.ano.setPlayerHat(h);
		HumanBoard.multi = m;
	}

	public int play(Board b, int ent, int ang, boolean lef, int fp, int im, int nbp, int playerNb) {
		if (Gamu.graph) {
			Font font = new Font("Arial", Font.BOLD, (640) * 12 / (7 * (b.getHeight() * 6 + 18)));
			// afficher les boutons de choix
			int[] tch;
			if (nbp == 4) {
				drawchoice(b.getColor1(), b.getColor2(), b.getColor3(), b.getColor4(), b);
				tch = choicetab(b.getColor1(), b.getColor2(), b.getColor3(), b.getColor4());
			} else {
				drawchoice(b.getColor1(), b.getColor2(), 19, 19, b);
				tch = choicetab(b.getColor1(), b.getColor2(), 19, 19);
			}
			while (ent == b.getColor1() || ent == b.getColor2()) {
				// voir position de souris
				StdDraw.setXscale(0, this.wh);
				StdDraw.setYscale(0, this.hh);
				double xn = StdDraw.mouseX();
				double yn = StdDraw.mouseY();
				int[] oo = this.ano.animia(b, im, fp, lef, ang, playerNb, false);
				ang = oo[3];
				lef = (oo[2] == 1);
				fp = oo[1];
				im = oo[0];
				if (StdDraw.mousePressed()) {
					if (xn > this.wh * 0.75 + this.wh * 0.125 - this.wh * 0.1
							&& xn < this.wh * 0.75 + this.wh * 0.125 + this.wh * 0.1 && !this.multi) {
						if (yn < this.hh - this.hh * 0.03 + this.hh * 0.03
								&& yn > this.hh - this.hh * 0.03 - this.hh * 0.03) {
							// save
							b.savebonly(false);
							try {
								Thread.sleep(150);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						} else if (yn < this.hh - this.hh * 0.03 - this.hh * 0.03 * 2 + this.hh * 0.03
								&& yn > this.hh - this.hh * 0.03 - this.hh * 0.03 * 2 - this.hh * 0.03) {
							// quit
							String[] t = { "Solo", "Multijoueur", "Editeur" };
							int[] i = { 2, 3, 17 };
							Menu aMain = new Menu("Menu Principal", t, i);
							aMain.showMenu();
						}
					} else if (yn > this.hh * 0.8 + this.hh * 0.01) {
						if (xn < this.hh * 0.09 + this.hh * 0.18 * 4 && xn > 0) {
							if (xn < this.hh * 0.18) {
								ent = tch[0];
							} else if (xn < this.hh * 0.18 + this.hh * 0.18 * 1) {
								ent = tch[1];
							} else if (tch.length - 1 >= 2 && xn < this.hh * 0.18 + this.hh * 0.18 * 2) {
								ent = tch[2];
							} else if (tch.length - 1 >= 3 && xn < this.hh * 0.18 + this.hh * 0.18 * 3) {
								ent = tch[3];
							}
						}
					}
				}
			}
			// 1.5sec d'attente pour pas de clicks non voulus
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// cacher bouton quitter
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.filledRectangle(b.getWidth() * 6 + 6 + 6 + 1, 5, 6, 3);
			StdDraw.setPenColor(StdDraw.BLACK);
			int lefi = 0;
			if (lef) {
				lefi = 1;
			}
			int[] res = { ent, ang, fp, im, lefi };
			return res[0];
		} else {
			if (b.getNbPlayer() == 2) {
				b.setColor3(18);
				b.setColor4(18);
			}
			return playKey(Gamu.choiceTab(b.getColor1(), b.getColor2(), b.getColor3(), b.getColor4()), b);
		}
	}

	public int playKey(int[] ch, Board bb) {
		Scanner scan = new Scanner(System.in);
		int choice = 19;// valeur absurde
		boolean b = false;
		while (!b) {
			System.out.println("Choisir une de ces couleurs :");
			Gamu.choice(bb.getColor1(), bb.getColor2(), bb.getColor3(), bb.getColor4());
			String s = scan.next();
			choice = Integer.parseInt(s);
			for (int i = 0; i < ch.length; i++) {
				if (ch[i] == choice)
					b = true;
			}
		}
		return choice;
	}

}
