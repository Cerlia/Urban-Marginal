package controleur;

public interface Global {
	/**
	 * port d'écoute du serveur jeu
	 */
	int PORT = 6666;
	/**
	 * séparateur de chaîne
	 */
	String SEP = "~";
	/**
	 * numéro de personnage max
	 */
	int PERSOMAX = 3;
	/**
	 * largeur de l'arène
	 */
	int LARGARN = 800;
	/**
	 * hauteur de l'arène
	 */
	int HAUTARN = 600;
	/**
	 *  largeur du mur
	 */
	int LARGMUR = 34;
	/**
	 *  hauteur du mur
	 */
	int HAUTMUR = 35;
	/**
	 * nombre de murs
	 */
	int NBMURS = 20;
	/**
	 *  largeur et hauteur de la boule
	 */
	int TAILLEBL = 17;
	/**
	 * valeur du pas pour un déplacement
	 */
	int PAS = 10;
}
