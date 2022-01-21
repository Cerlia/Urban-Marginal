package modele;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JLabel;

import controleur.Controle;
import controleur.Global;
import outils.connexion.Connection;

/**
 * Gestion du jeu côté serveur
 *
 */
public class JeuServeur extends Jeu implements Global{

	// Collection de murs
	private ArrayList<Mur> lesMurs = new ArrayList<Mur>() ;
	// Collection de joueurs
	private Hashtable<Connection, Joueur> lesJoueurs = new Hashtable<Connection, Joueur>() ;
	
	/**
	 * Constructeur
	 */
	public JeuServeur(Controle controle) {
		super.controle = controle;
	}
	
	@Override
	public void connexion(Connection connection) {
		this.lesJoueurs.put(connection, new Joueur(this));
	}

	@Override
	public void reception(Connection connection, Object info) {
		String[] infos = ((String)info).split(SEP);
		String ordre = infos[0]; 
		switch (ordre) {
		case "pseudo" :
			controle.evenementJeuServeur("ajout panel mur", connection);
			String pseudo = infos[1];
			int numPerso = Integer.parseInt(infos[2]);
			this.lesJoueurs.get(connection).initPerso(pseudo, numPerso, this.lesJoueurs.values(), this.lesMurs);
			break;
		}
	}
	
	@Override
	public void deconnexion() {
	}
	
	/**
	 * Ajoute des éléments au lblJeu de l'arène
	 */
	public void ajoutJLabelJeuArene(JLabel jlabel) {
		this.controle.evenementJeuServeur("ajout jlabel jeu", jlabel);		
	}
	
	/**
	 * Envoi d'une information vers tous les clients
	 * fais appel plusieurs fois à l'envoi de la classe Jeu
	 */	
	public void envoi() {
	}
	
	/**
	 * Envoi du panel jpnJeu à tous les joueurs
	 */
	public void envoiJeuATous() {
		for (Connection connection : this.lesJoueurs.keySet()) {
			this.controle.evenementJeuServeur("modif panel jeu", connection);
		}
	}
	
	/**
	 * Génération des murs
	 */
	public void constructionMurs() {
		for(int k = 0; k < NBMURS; k++) {
			Mur unMur = new Mur();
			this.lesMurs.add(unMur);
			this.controle.evenementJeuServeur("ajout mur", unMur.getJLabel());
		}
	}
}
