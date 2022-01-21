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
	 */
	public JeuClient(Controle controle) {
		super.controle = controle;
	}
	
	@Override
	public void connexion(Connection connection) {
		this.connection = connection;
	}

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
		}			
	}
	
	@Override
	public void deconnexion() {
	}

	/**
	 * Envoi d'une information vers le serveur
	 * fais appel une fois à l'envoi dans la classe Jeu
	 */
	public void envoi(String message) {
		super.envoi(this.connection, message);
	}

}
