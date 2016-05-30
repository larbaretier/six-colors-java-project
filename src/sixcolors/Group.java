package sixcolors;

/* Classe : Group
 * But : appliquer la règle entourer=pris
 * 
 * 
 * */
public class Group {
	private Hexa[] group;
	private int start = 0;

	public Group(int f) {
		this.group = new Hexa[f];
	}

	public boolean inGroup(Hexa[] g, Hexa c) {
		if (g != null)
			for (int k = 0; k < g.length; k++) {
				if (g[k] != null && g[k] == c) {
					return true;
				}
			}
		return false;
	}

	public void getGroup(Hexa h, int color, int player) {// prendre ceux nayant
															// pas de prop et de
															// couleur
															// differentes
		Hexa[] v = new Hexa[7];
		v[0] = h.getVd();
		v[1] = h.getVdl();
		v[2] = h.getVdr();
		v[3] = h.getVu();
		v[4] = h.getVur();
		v[5] = h.getVul();
		v[6] = h;
		for (int k = 0; k < 7; k++) {
			if (v[k] != null && v[k].getColor() != 6 && (v[k].getProp() != player) && !inGroup(this.group, v[k])) {
				this.group[this.start] = v[k];
				this.start++;
				getGroup(v[k], color, player);
			}
		}
	}

	public boolean caseLibre(Hexa h, int color, int ce1, int ce2, int ce3) {// qui
																			// a
																			// une
																			// case
																			// a
																			// coté
																			// non
																			// prise
																			// par
																			// le
																			// joueur
		boolean res = false;
		if (h.getVd() != null && h.getVd().getColor() != 6) {
			if (h.getVd().getProp() == ce1 || h.getVd().getProp() == ce2 || h.getVd().getProp() == ce3) {
				res = true;
			}
		}
		if (h.getVdl() != null && h.getForme() != 1 && h.getVdl().getColor() != 6) {
			if (h.getVdl().getProp() == ce1 || h.getVdl().getProp() == ce2 || h.getVdl().getProp() == ce3) {
				res = true;
			}
		}
		if (h.getVdr() != null && h.getForme() != 1 && h.getVdr().getColor() != 6) {
			if (h.getVdr().getProp() == ce1 || h.getVdr().getProp() == ce2 || h.getVdr().getProp() == ce3) {
				res = true;
			}
		}
		if (h.getVu() != null && h.getVu().getColor() != 6) {
			if (h.getVu().getProp() == ce1 || h.getVu().getProp() == ce2 || h.getVu().getProp() == ce3) {
				res = true;
			}
		}
		if (h.getVul() != null && h.getVul().getColor() != 6) {
			if (h.getVul().getProp() == ce1 || h.getVul().getProp() == ce2 || h.getVul().getProp() == ce3) {
				res = true;
			}
		}
		if (h.getVur() != null && h.getVur().getColor() != 6) {
			if (h.getVur().getProp() == ce1 || h.getVur().getProp() == ce2 || h.getVur().getProp() == ce3) {
				res = true;
			}
		}
		return res;
	}

	public boolean groupLibre(Hexa[] g, int color, int ce1, int ce2, int ce3) {
		for (int k = 0; k < g.length; k++) {
			if (g[k] != null && g[k].getColor() != 6 && caseLibre(g[k], color, ce1, ce2, ce3)) {
				return true;
			}
		}
		return false;
	}

	public void deleteGroup(Hexa[] g, int color, int prop) {// set colors to the
															// group
		for (int k = 0; k < g.length; k++) {
			if (g[k] != null && g[k].getColor() != 6) {
				g[k].setColor(color);
				g[k].setProp(prop);
			}
		}
	}

	public Hexa[] getGroup() {
		return group;
	}

	public void setGroup(Hexa[] group) {
		this.group = group;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

}
