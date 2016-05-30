package sixcolors;

import java.awt.Font;

import edu.princeton.cs.introcs.StdDraw;

/* Classe : Player
 * But : classe racine d'un joueur (rassemble ia et humain)
 * 
 * 
 * */
public class Player {
	private int type;// 0 ia, 1 player on cmp, 2 LAN server, 3 LAN client
	private int score = 0;// the player's score
	private int color = 0;
	private Animation an = new Animation();
	private int wh = 640 + 100;// window width in pixel
	private int hh = 640;// window height in pixel

	public void drawchoice(int c1, int c2, int c3, int c4, Board bb) {
		int already = 0;
		StdDraw.setXscale(0, wh);
		StdDraw.setYscale(0, hh);
		StdDraw.setPenColor(StdDraw.BLACK);
		for (int i = 0; i < 6; i++) {
			if (i != c1 && i != c2 && i != c3 && i != c4) {
				StdDraw.picture(this.hh * 0.09 + this.hh * 0.18 * already, this.hh * 0.8 + this.hh * 0.10,
						bb.getColorim()[i], this.hh * 0.18, this.hh * 0.18, 0);
				already++;
			}
		}
	}

	public int[] choicetab(int c1, int c2, int c3, int c4) {// renvoie les choix
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

	public int play(Board b, int ent, int ang, boolean lef, int fp, int im, int nbp, int playerNbBoard) {
		return 0;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public Animation getAn() {
		return an;
	}

	public void setAn(Animation an) {
		this.an = an;
	}

	public int getWh() {
		return wh;
	}

	public void setWh(int wh) {
		this.wh = wh;
	}

	public int getHh() {
		return hh;
	}

	public void setHh(int hh) {
		this.hh = hh;
	}

}
