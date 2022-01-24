package modele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import javax.swing.JLabel;

import controleur.Controle;
import controleur.Global;
import outils.connexion.Connection;

/**
 * Gestion du jeu côté serveur
 */
public class JeuServeur extends Jeu implements Global{
	/**
	 *  Collection de murs
	 */
	private ArrayList<Mur> lesMurs = new ArrayList<Mur>() ;
	/**
	 *  Collection de joueurs
	 */
	private Hashtable<Connection, Joueur> lesJoueurs = new Hashtable<Connection, Joueur>() ;
	
	public Collection getLesJoueurs() {
		return lesJoueurs.values();
	}
	
	/**
	 * Constructeur
	 * @param controle contrôleur
	 */
	public JeuServeur(Controle controle) {
		super.controle = controle;
	}
	
	/**
	 * Ajoute une connexion à la liste des joueurs
	 * @param connection informations de connexion du joueur
	 */
	@Override
	public void connexion(Connection connection) {
		this.lesJoueurs.put(connection, new Joueur(this));
	}

	/**
	 * Reçoit des données de (?)
	 * @param connection informations de connexion d'un joueur
	 * @param info transmise 
	 */
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
			controle.evenementJeuServeur("ajout phrase", "*** " + pseudo + " vient de se connecter ***");
			break;
		case "tchat" :
			String nomJoueur = lesJoueurs.get(connection).getPseudo();
			String phrase = nomJoueur + " > " + infos[1];
			controle.evenementJeuServeur("ajout phrase", phrase);
			break;
		case "action" :
			this.lesJoueurs.get(connection).action((Integer.parseInt(infos[1])), this.lesJoueurs.values(), this.lesMurs);
			break;
		}
	}
	
	@Override
	public void deconnexion() {
	}
	
	/**
	 * Ajoute des éléments au lblJeu de l'arène
	 * @param jLabel JLabel à ajouter
	 */
	public void ajoutJLabelJeuArene(JLabel jlabel) {
		this.controle.evenementJeuServeur("ajout jlabel jeu", jlabel);		
	}
	
	/**
	 * Envoi d'une information vers tous les clients
	 * fait appel plusieurs fois à l'envoi de la classe Jeu
	 * @param info à transmettre
	 */	
	public void envoi(Object info) {
		for(Connection connection : this.lesJoueurs.keySet()) {
			super.envoi(connection, info);
		}
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
