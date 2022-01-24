package controleur;

public interface Global {
	/**
	 * port d'�coute du serveur jeu
	 */
	int PORT = 6666;
	/**
	 * s�parateur de cha�ne
	 */
	String SEP = "~";
	/**
	 * num�ro de personnage max
	 */
	int PERSOMAX = 3;
	/**
	 * largeur de l'ar�ne
	 */
	int LARGARN = 800;
	/**
	 * hauteur de l'ar�ne
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
	 * valeur du pas pour un d�placement
	 */
	int PAS = 10;
	/**
	 * largeur moyenne d'un sprite de personnage
	 */	
	int LARGPERS = 33;
	/**
	 * hauteur moyenne d'un sprite de personnage
	 */	
	int HAUTPERS = 43;
	/**
	 * chemins des trois sons utilis�s dans l'ar�ne
	 */
	String[] PTHSONS = {"sons/fight.wav", "sons/hurt.wav", "sons/death.wav"};
}
