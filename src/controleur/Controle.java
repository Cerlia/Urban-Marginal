package controleur;

import vue.Arene;
import vue.ChoixJoueur;
import vue.EntreeJeu;
import outils.connexion.*;
import modele.Jeu;
import modele.JeuServeur;
import modele.JeuClient;

import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.Global;


/**
 * Contrôleur et point d'entrée de l'applicaton 
 * @author emds
 */
public class Controle implements AsyncResponse, Global {
	/**
	 * Communication avec la frame EntreeJeu
	 */
	private EntreeJeu frmEntreeJeu ;
	/**
	 * Communication avec la frame Arene
	 */
	private Arene frmArene;
	/**
	 * Communication avec la frame ChoixJoueur
	 */
	private ChoixJoueur frmChoixJoueur;
	/**
	 * Communication avec leJeu, de type JeuClient ou JeuServeur
	 */
	private Jeu leJeu;

	/**
	 * Méthode de démarrage
	 * @param args non utilisé
	 */
	public static void main(String[] args) {
		new Controle();
	}
	
	/**
	 * Constructeur
	 */
	private Controle() {
		this.frmEntreeJeu = new EntreeJeu(this) ;
		this.frmEntreeJeu.setVisible(true);
	}
	
	/**
	 * Déclenche la création d'un serveur ou le rejoignement d'un serveur existant
	 * @param info transmise par la frame EntreeJeu
	 */
	public void evenementEntreeJeu(String info) {	
		if (info.equals("serveur")) {
			new ServeurSocket(this, PORT);
			this.leJeu = new JeuServeur(this);
			this.frmEntreeJeu.dispose();
			this.frmArene = new Arene(this, "serveur");
			((JeuServeur)this.leJeu).constructionMurs();
			this.frmArene.setVisible(true);
		}
		else {
			new ClientSocket(this, info, PORT);
		}
	}
	
	/**
	 * Reçoit des informations de la frame ChoixJoueur
	 * @param pseudo transmis par la frame ChoixJoueur
	 * @param numPerso transmis par la frame ChoixJoueur
	 */
	public void evenementChoixJoueur(String pseudo, int numPerso) {
		this.frmChoixJoueur.dispose();
		this.frmArene.setVisible(true);
		((JeuClient)this.leJeu).envoi("pseudo" + SEP + pseudo + SEP + numPerso);	
	}
	
	/**
	 * Reçoit des informations de leJeu de type JeuServeur
	 * @param ordre action à exécuter
	 * @param info paramètre
	 */
	public void evenementJeuServeur(String ordre, Object info) {
		switch (ordre) {
		case ("ajout mur") :
			frmArene.ajoutMurs(info);
			break;
		case ("ajout panel mur") :
			this.leJeu.envoi((Connection)info, this.frmArene.getJpnMurs());
			break;
		case ("ajout jlabel jeu") :
			this.frmArene.ajoutJLabelJeu((JLabel)info);
			break;
		case ("modif panel jeu") :
			this.leJeu.envoi((Connection)info, this.frmArene.getJpnJeu());
			break;
		case ("ajout phrase") :
			this.frmArene.ajoutTchat((String)info);
			((JeuServeur)leJeu).envoi(frmArene.getTxtChat());
			break;
		}
	}
	
	/**
	 * Reçoit des informations de leJeu de type JeuClient
	 * @param ordre action à exécuter
	 * @param info paramètre
	 */
	public void evenementJeuClient(String ordre, Object info) {
		switch (ordre) {
		case ("ajout panel mur") :
			this.frmArene.setJpnMurs((JPanel)info);
			break;
		case ("modif panel jeu") :
			this.frmArene.setJpnJeu((JPanel)info);
			break;
		case ("modif tchat") :
			this.frmArene.setTxtChat((String)info);
			break;
		case ("jouer son") :
			this.frmArene.joueSon((Integer)info);
			break;
		}
	}
	
	/**
	 * Reçoit des informations de frmArene
	 * @param info transmise par la frame Arene
	 */	
	public void evenementArene(Object info) {
		if (info instanceof String) {
			((JeuClient)leJeu).envoi("tchat" + SEP + (String)info);
		}
		else if (info instanceof Integer) {
			((JeuClient)leJeu).envoi("action" + SEP + (Integer)info);
		}
	}	
	
	/**
	 * Envoie des informations au serveur (?)
	 * @param connection informations de connexion d'un joueur
	 * @param info à transmesttre au serveur
	 */
	public void envoi(Connection connection, Object info) {
		connection.envoi(info);
	}
	
	/**
	 * Réceptionne un ordre relatif à une connexion (?)
	 * @param connection informations de connexion d'un joueur
	 * @param ordre à exécuter
	 * @param info à transmettre (?)
	 */
	@Override
	public void reception(Connection connection, String ordre, Object info) {
		switch(ordre) {
		case "connexion" :
			if(!(this.leJeu instanceof JeuServeur)) {
				this.leJeu = new JeuClient(this);
				this.leJeu.connexion(connection);
				this.frmEntreeJeu.dispose();
				this.frmArene = new Arene(this, "client");
				this.frmChoixJoueur = new ChoixJoueur(this);
				this.frmChoixJoueur.setVisible(true);	
			}
			else {
				this.leJeu.connexion(connection);
			}
			break;
		case "réception" :
			this.leJeu.reception(connection, info);
			break;
		case "déconnexion" :
			leJeu.deconnexion(connection);
			break;
		}			
	}
}
