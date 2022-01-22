package modele;

import javax.swing.JPanel;
import controleur.Controle;
import outils.connexion.Connection;

/**
 * Gestion du jeu côté client
 *
 */
public class JeuClient extends Jeu {
	private Connection connection;
	private boolean mursOk = false;
	
	/**
	 * Controleur
	 * @param controle contrôleur
	 */
	public JeuClient(Controle controle) {
		super.controle = controle;
	}
	
	/**
	 * Initialise la connexion (?)
	 * @param connection infos de connexion d'un joueur
	 */
	@Override
	public void connexion(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Réceptionne des données de (?)
	 * @param connection
	 * @param info
	 */
	@Override
	public void reception(Connection connection, Object info) {
		if(info instanceof JPanel) {
			if(!this.mursOk) {
				this.controle.evenementJeuClient("ajout panel mur", info);
				this.mursOk = true;
			}
			else {
				this.controle.evenementJeuClient("modif panel jeu", info);
			}		
		} else if (info instanceof String) {
			controle.evenementJeuClient("modif tchat", info);
		}
	}
	
	/**
	 * Déconnecte
	 */
	@Override
	public void deconnexion() {
	}

	/**
	 * Envoi d'une information vers le serveur
	 * fait appel une fois à l'envoi dans la classe Jeu
	 * @param message à transmettre
	 */
	public void envoi(String message) {
		super.envoi(this.connection, message);
	}

}
