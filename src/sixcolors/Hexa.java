/**
* @author Louis
* Cases (pour le plateau de jeu)
*/
package sixcolors;

import java.io.Serializable;

/* Classe : Hexa
 * But : cases dont sont constituées les colonnes du tableau
 * 
 * 
 * */
public class Hexa implements Serializable {
	// neighbors
	static Hexa[] tabG;
	private Hexa vu = null;// up
	private Hexa vul = null;// up left
	private Hexa vur = null;// up right
	private Hexa vd = null;// down
	private Hexa vdl = null;// down left
	private Hexa vdr = null;// down right
	private int color = 0;
	private int prop = 0;
	private Column colum = null;
	private int row = 0;// from 0 to length-1
	private int forme = 3;// 1:square,2:diamond,3:hexa

	public Hexa(Hexa tocopy, Column cc, int f) {
		this.vu = null;// up
		this.vul = null;// up left or l for square
		this.vur = null;// up right or r for square
		this.vd = null;// down
		this.vdl = null;// down left
		this.vdr = null;// down right
		this.color = tocopy.color;
		this.prop = tocopy.prop;
		this.row = tocopy.row;
		this.forme = f;
		this.colum = cc;
	}

	Hexa(int r, Column c, int f) {
		int rannu = (int) (Math.random() * 6);// random color
		this.color = rannu;
		this.row = r;
		this.colum = c;
		this.forme = f;
	}

	Hexa(int r, Column c, int colorss, int player, int f) {
		this.color = colorss;
		this.prop = player;
		this.row = r;
		this.colum = c;
		this.forme = f;
	}

	public boolean isequal(Hexa a) {
		return this == a;
	}

	public boolean isin(Hexa[] tab, Hexa a) {
		int lg = tab.length;
		for (int k = 0; k < lg; k++) {
			if (a.isequal(tab[k])) {
				return true;
			}
		}
		return false;
	}

	public void changecolorplayer(int col, int player) {
		this.color = col;
		this.prop = player;
	}

	public void setneigh(Column[] bigBoard) {// set neighbors
		if (this.colum.getid() % 2 == 0 || this.forme == 1) {// if in even
																// Column
			if (this.row != 0) {// not first row
				this.vu = this.colum.getBox()[this.row - 1];
			}
			if (this.colum.getid() != 1) {// not on first Column
				this.vul = (bigBoard[this.colum.getid() - 2].getBox())[this.row];
				if (this.forme == 3)
					this.vdl = (bigBoard[this.colum.getid() - 2].getBox())[this.row + 1];
			}
			if (this.colum.getid() != bigBoard.length) {// not on last Column
				this.vur = (bigBoard[this.colum.getid()].getBox())[this.row];
				if (this.forme == 3)
					this.vdr = (bigBoard[this.colum.getid()].getBox())[this.row + 1];
			}
			if (this.row != this.colum.getBox().length - 1) {// not last row
				this.vd = this.colum.getBox()[this.row + 1];
			}
		} else {// if in odd Column
			if (this.row != 0) {// not first row
				this.vu = this.colum.getBox()[this.row - 1];
			}
			if (this.colum.getid() != 1) {
				if (this.row != 0) {
					this.vul = (bigBoard[this.colum.getid() - 2].getBox())[this.row - 1];
				}
				if (this.row != this.colum.getBox().length - 1) {
					if (this.forme == 3)
						this.vdl = (bigBoard[this.colum.getid() - 2].getBox())[this.row];
				}
			}
			if (this.colum.getid() != bigBoard.length) {// not on last Column
				if (this.row != 0) {
					this.vur = (bigBoard[this.colum.getid()].getBox())[this.row - 1];
				}
				if (this.row != this.colum.getBox().length - 1) {
					if (this.forme == 3)
						this.vdr = (bigBoard[this.colum.getid()].getBox())[this.row];
				}
			}
			if (this.row != this.colum.getBox().length - 1) {// not last row
				this.vd = this.colum.getBox()[this.row + 1];
			}
		}
	}

	public int getColor() {
		return this.color;
	}

	public void checkNeigh(int cc, int player, Hexa[] tab, int p, int playturn, Board b) {
		if (playturn == 0) {
			playturn = 1;
		} // avoid bug
		tab[p] = this;
		Hexa.tabG = tab;// rendre 'public' les cases qui changent
		p++;
		if (this.prop == player) {
			this.color = cc;
		}
		// check the neighbors
		if (this.vu != null && (this.vu.getColor() == cc || this.vu.getProp() == player)
				&& !(this.vu.isin(tab, this.vu))) {
			if (this.vu.getProp() != player) {
				int[] s = b.getScores();
				s[playturn - 1] += 1;
				b.setScores(s);
			}
			this.vu.setProp(player);
			this.vu.checkNeigh(cc, player, tab, p, playturn, b);
		}
		if (this.vul != null && (this.vul.getColor() == cc || this.vul.getProp() == player)
				&& !(this.vul.isin(tab, this.vul))) {
			if (this.vul.getProp() != player) {
				int[] s = b.getScores();
				s[playturn - 1] += 1;
				b.setScores(s);
			}
			this.vul.setProp(player);
			this.vul.checkNeigh(cc, player, tab, p, playturn, b);
		}
		if (this.vur != null && (this.vur.getColor() == cc || this.vur.getProp() == player)
				&& !(this.vur.isin(tab, this.vur))) {
			if (this.vur.getProp() != player) {
				int[] s = b.getScores();
				s[playturn - 1] += 1;
				b.setScores(s);
			}
			this.vur.setProp(player);
			this.vur.checkNeigh(cc, player, tab, p, playturn, b);
		}
		if (this.vd != null && (this.vd.getColor() == cc || this.vd.getProp() == player)
				&& !(this.vd.isin(tab, this.vd))) {
			if (this.vd.getProp() != player) {
				int[] s = b.getScores();
				s[playturn - 1] += 1;
				b.setScores(s);
			}
			this.vd.setProp(player);
			this.vd.checkNeigh(cc, player, tab, p, playturn, b);
		}
		if (this.forme == 3 && this.vdl != null && (this.vdl.getColor() == cc || this.vdl.getProp() == player)
				&& !(this.vdl.isin(tab, this.vdl))) {
			if (this.vdl.getProp() != player) {
				int[] s = b.getScores();
				s[playturn - 1] += 1;
				b.setScores(s);
			}
			this.vdl.setProp(player);
			this.vdl.checkNeigh(cc, player, tab, p, playturn, b);
		}
		if (this.forme == 3 && this.vdr != null && (this.vdr.getColor() == cc || this.vdr.getProp() == player)
				&& !(this.vdr.isin(tab, this.vdr))) {
			if (this.vdr.getProp() != player) {
				int[] s = b.getScores();
				s[playturn - 1] += 1;
				b.setScores(s);
			}
			this.vdr.setProp(player);
			this.vdr.checkNeigh(cc, player, tab, p, playturn, b);
		}

	}

	static public void entourerF(int couleur, int prop, int size, int ce1, int ce2, int ce3) {
		Hexa[] t = Hexa.tabG;
		Group g = new Group(size);
		for (int i = 0; i < t.length; i++) {
			if (t[i] != null) {
				Hexa h = t[i];
				Hexa[] v = new Hexa[6];
				v[0] = h.getVd();
				v[1] = h.getVdl();
				v[2] = h.getVdr();
				v[3] = h.getVu();
				v[4] = h.getVur();
				v[5] = h.getVul();
				for (int k = 0; k < 6; k++) {
					// reset each time
					g.setStart(0);
					g.setGroup(new Hexa[size]);
					// get group
					if (v[k] != null) {
						g.getGroup(v[k], couleur, t[0].getProp());
						if (g.getGroup()[0] != null && !g.groupLibre(g.getGroup(), couleur, ce1, ce2, ce3)) {
							g.deleteGroup(g.getGroup(), couleur, prop);
						}
					}
				}

			}
		}
	}

	public boolean haveN(int player) {
		boolean r = false;
		if (this.prop == player) {
			r = true;
		}
		if (this.vd != null && this.vd.getProp() == player) {
			r = true;
		}
		if (this.vu != null && this.vu.getProp() == player) {
			r = true;
		}
		if (this.vur != null && this.vur.getProp() == player) {
			r = true;
		}
		if (this.vul != null && this.vul.getProp() == player) {
			r = true;
		}
		if (this.vdr != null && this.vdr.getProp() == player) {
			r = true;
		}
		if (this.vdl != null && this.vdl.getProp() == player) {
			r = true;
		}

		return r;
	}

	public Hexa getVu() {
		return vu;
	}

	public void setVu(Hexa vu) {
		this.vu = vu;
	}

	public Hexa getVul() {
		return vul;
	}

	public void setVul(Hexa vul) {
		this.vul = vul;
	}

	public Hexa getVur() {
		return vur;
	}

	public void setVur(Hexa vur) {
		this.vur = vur;
	}

	public Hexa getVd() {
		return vd;
	}

	public void setVd(Hexa vd) {
		this.vd = vd;
	}

	public Hexa getVdl() {
		return vdl;
	}

	public void setVdl(Hexa vdl) {
		this.vdl = vdl;
	}

	public Hexa getVdr() {
		return vdr;
	}

	public void setVdr(Hexa vdr) {
		this.vdr = vdr;
	}

	public int getProp() {
		return prop;
	}

	public void setProp(int prop) {
		this.prop = prop;
	}

	public Column getColum() {
		return colum;
	}

	public void setColum(Column colum) {
		this.colum = colum;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getForme() {
		return forme;
	}

	public void setForme(int forme) {
		this.forme = forme;
	}

}
