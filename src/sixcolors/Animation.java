package sixcolors;

import java.awt.Font;
import edu.princeton.cs.introcs.StdDraw;

/* Classe : Animation
 * But : fait l'affichage des joueurs dont l'animation gauche droite
 * 
 * 
 * */
public class Animation {
	public static String[] h = { "images/hat1.png", "images/hat2.png", "images/hat3.png", "images/hat4.png",
			"images/hat5.png", "images/hat6.png", "images/hat7.png", "images/hat8.png", "images/hat9.png",
			"images/hat10.png", "images/hat11.png" };
	private String[] t = { "images/avatarbigre.png", "images/avatarbigre.png", "images/avatarbigre.png" };
	private int[] playerHat = { -1, -1, -1, -1 };
	private int wh = 640 + 100;// window width in pixel
	private int hh = 640;// window height in pixel
	private String[] visage = { "images/eye1.png", "images/eye2.png", "images/eye3.png", "images/eye4.png",
			"images/eye5.png", "images/eye6.png" };

	public void afficherNom(Board b) {
		StdDraw.setPenColor(StdDraw.BLUE);
		StdDraw.text(6, 0.5, "Louis");
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.text(4 + 5 * b.getWidth() - 1 + 12, b.getHeight() * 6 - 5.5, "IA1");// -------------------------------amodifier
	}

	public void drawHat(double x, double y, int player, double w, double lh, int a) {
		if (this.playerHat[player - 1] > -1) {
			StdDraw.picture(x, y, this.h[this.playerHat[player - 1]], w, lh, a);
		}
	}

	public void drawPlayer(int player, int angle, Board b, boolean ia) {
		// clear
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.filledRectangle(this.wh * 0.75 + this.wh * 0.125, this.hh * 0.4, this.wh * 0.125, this.hh * 0.5);
		StdDraw.picture(this.wh * 0.75 + this.wh * 0.05, this.hh - (this.hh * 0.2 + (player - 1) * this.wh * 0.18),
				b.getColorim()[b.getColorP(player)], this.wh * 0.10, this.wh * 0.10, 0);
		StdDraw.picture(this.wh * 0.75 + this.wh * 0.125, this.hh - (this.hh * 0.2 + (player - 1) * this.wh * 0.18),
				t[0], this.wh * 0.16, this.wh * 0.16, angle);
		StdDraw.picture(this.wh * 0.75 + this.wh * 0.125, this.hh - (this.hh * 0.2 + (player - 1) * this.wh * 0.18),
				visage[0], this.wh * 0.16, this.wh * 0.16, angle);
		drawHat(this.wh * 0.75 + this.wh * 0.125, this.hh - (this.hh * 0.2 + (player - 1) * this.wh * 0.18), player,
				this.wh * 0.16, this.wh * 0.16, angle);
		Font fontTf = new Font("Arial", Font.BOLD, (int) (this.hh * 0.05));
		StdDraw.setFont(fontTf);
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.text(this.wh * 0.75 + this.wh * 0.125,
				this.hh - (this.hh * 0.2 + (player - 1) * this.wh * 0.18) - this.wh * 0.09,
				"P" + player + " : " + b.getScores()[player - 1]);
		// other players
		for (int i = 0; i < b.getNbPlayer(); i++) {
			if (i != player - 1) {
				StdDraw.picture(this.wh * 0.75 + this.wh * 0.05, this.hh - (this.hh * 0.2 + (i) * this.wh * 0.18),
						b.getColorim()[b.getColorP(i + 1)], this.wh * 0.10, this.wh * 0.10, 0);
				StdDraw.picture(this.wh * 0.75 + this.wh * 0.125, this.hh - (this.hh * 0.2 + (i) * this.wh * 0.18),
						t[0], this.wh * 0.16, this.wh * 0.16, 0);
				StdDraw.picture(this.wh * 0.75 + this.wh * 0.125, this.hh - (this.hh * 0.2 + (i) * this.wh * 0.18),
						visage[1], this.wh * 0.16, this.wh * 0.16, 0);
				drawHat(this.wh * 0.75 + this.wh * 0.125, this.hh - (this.hh * 0.2 + (i) * this.wh * 0.18), i + 1,
						this.wh * 0.16, this.wh * 0.16, 0);
				StdDraw.setFont(fontTf);
				StdDraw.setPenColor(StdDraw.BLUE);
				StdDraw.text(this.wh * 0.75 + this.wh * 0.125,
						this.hh - (this.hh * 0.2 + (i) * this.wh * 0.18) - this.wh * 0.09,
						"P" + (i + 1) + " : " + b.getScores()[i]);
			}
		}
		// draw buttons if player non-ia
		if (!ia) {
			Font font = new Font("Arial", Font.BOLD, (int) (this.wh * 0.1 / 3));
			StdDraw.setFont(font);
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.rectangle(this.wh * 0.75 + this.wh * 0.125, this.hh - this.hh * 0.03, this.wh * 0.1,
					this.hh * 0.03);

			if (!HumanBoard.multi) {
				StdDraw.text(this.wh * 0.75 + this.wh * 0.125, this.hh - this.hh * 0.03, "Sauvegarder");
				StdDraw.rectangle(this.wh * 0.75 + this.wh * 0.125, this.hh - this.hh * 0.03 - this.hh * 0.03 * 2,
						this.wh * 0.1, this.hh * 0.03);
				StdDraw.text(this.wh * 0.75 + this.wh * 0.125, this.hh - this.hh * 0.03 - this.hh * 0.03 * 2,
						"Quitter");
			}
			StdDraw.setPenColor(StdDraw.BLACK);
		} else {
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.filledRectangle(this.wh * 0.75 + this.wh * 0.125, this.hh - this.hh * 0.03 * 2, this.wh * 0.15,
					this.hh * 0.06);
		}

		StdDraw.show(1);
	}

	public int[] getPlayerHat() {
		return playerHat;
	}

	public void setPlayerHat(int[] playerHat) {
		this.playerHat = playerHat;
	}

	public int[] animia(Board bb, int i, int f, boolean left, int angle, int playerNb, boolean ia) {
		if (f % 200000 == 0) {// angle change
			if (angle < 15) {
				if (left == false) {
					angle++;
				} else {
					if (angle > -15) {
						angle--;
					} else {
						left = !left;
						angle++;
					}
				}
			} else {
				left = !left;
				angle--;
			}
		}
		if (f > 700060) {// fps animation
			if (i < 2) {
				i++;
				drawPlayer(playerNb, angle, bb, ia);
			} else {
				i = 0;
				drawPlayer(playerNb, angle, bb, ia);
			}
			f = 0;
		} else {
			f++;
		}
		int le;
		if (left == true) {
			le = 0;
		} else {
			le = 1;
		}
		return new int[] { i, f, le, angle };
	}

}
