package vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controleur.Controle;
import controleur.Global;
import outils.son.Son;

import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.SwingConstants;

/**
 * Frame du choix du joueur
 * @author emds
 */
public class ChoixJoueur extends JFrame implements Global {
	/**
	 * panel g�n�ral
	 */
	private JPanel contentPane;
	/**
	 * Zone de saisie du pseudo
	 */
	private JTextField txtPseudo;
	/**
	 * pour communication avec controle
	 */
	private Controle controle;
	/**
	 *  pour l'affichage du personnage au moment du choix
	 */
	private JLabel lblPersonnage;
	/**
	 * num�ro du personnage (1, 2 ou 3)
	 */
	private int numPerso;
	/**
	 * son jou� � l'ouverture de la fen�tre
	 */
	private Son bienvenue;
	/**
	 * son jou� au clic sur la fl�che pr�c�dent
	 */
	private Son precedent;
	/**
	 * son jou� au clic sur la fl�che suivant
	 */
	private Son suivant;
	/**
	 * son jou� au clic sur le bouton Go
	 */
	private Son go;

	/**
	 * Clic sur la fl�che "pr�c�dent" pour afficher le personnage pr�c�dent
	 */
	private void lblPrecedent_clic() {
		numPerso--;
		if (numPerso == 0) {
			numPerso = 3;
		}
		affichePerso();
		precedent.play();
	}
	
	/**
	 * Clic sur la fl�che "suivant" pour afficher le personnage suivant
	 */
	private void lblSuivant_clic() {
		numPerso++;
		if (numPerso > PERSOMAX) {
			numPerso = 1;
		}
		affichePerso();
		suivant.play();
	}
	
	/**
	 * Clic sur GO pour envoyer les informations
	 */
	private void lblGo_clic() {
		if (txtPseudo.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "La saisie du pseudo est obligatoire");
			// positionnement sur la zone de saisie
			txtPseudo.requestFocus();
		}
		else {
			controle.evenementChoixJoueur(txtPseudo.getText(), numPerso);			
		}
		go.play();
	}

	/**
	 * Affiche un personnage dans la zone centrale
	 */
	private void affichePerso() {
		String chemin = "personnages/perso" + numPerso + "marche1d1.gif";
		URL resource = getClass().getClassLoader().getResource(chemin);
		lblPersonnage.setIcon(new ImageIcon(resource));		
	}
	
	/**
	 * Change le curseur au survol d'une zone cliquable
	 */
	private void sourisDoigt() {
		contentPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	/**
	 * Change le curseur au survol d'une zone non cliquable
	 */
	private void sourisNormale() {
		contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * Constructeur
	 */
	public ChoixJoueur(Controle controle) {
		// Dimension de la frame en fonction de son contenu
		this.getContentPane().setPreferredSize(new Dimension(400, 275));
	    this.pack();
	    // interdiction de changer la taille
		this.setResizable(false);
		 
		setTitle("Choice");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblPersonnage = new JLabel("");
		lblPersonnage.setHorizontalAlignment(SwingConstants.CENTER);
		lblPersonnage.setBounds(180, 156, 39, 44);
		contentPane.add(lblPersonnage);
		
		JLabel lblPrecedent = new JLabel("");
		lblPrecedent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				lblPrecedent_clic();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				sourisNormale();
			}
		});
		
		JLabel lblSuivant = new JLabel("");
		lblSuivant.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblSuivant_clic();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				sourisNormale();
			}
		});
		
		JLabel lblGo = new JLabel("");
		lblGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblGo_clic();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				sourisNormale();
			}
		});
		
		txtPseudo = new JTextField();
		txtPseudo.setBounds(142, 245, 120, 20);
		contentPane.add(txtPseudo);
		txtPseudo.setColumns(10);
		
		lblGo.setBounds(311, 202, 65, 61);
		contentPane.add(lblGo);
		lblSuivant.setBounds(301, 145, 25, 46);
		contentPane.add(lblSuivant);
		lblPrecedent.setBounds(65, 146, 31, 45);
		contentPane.add(lblPrecedent);
		
		JLabel lblFond = new JLabel("");
		lblFond.setBounds(0, 0, 400, 275);
		String chemin = "fonds/fondchoix.jpg";
		URL resource = getClass().getClassLoader().getResource(chemin);
		lblFond.setIcon(new ImageIcon(resource));		
		contentPane.add(lblFond);
		// positionnement sur la zone de saisie
		txtPseudo.requestFocus();
		// initialisation du num�ro de personnage puis affichage
		numPerso = 1;
		affichePerso();
		// initialisation des sons
		bienvenue = new Son(getClass().getClassLoader().getResource("sons/welcome.wav"));
		precedent = new Son(getClass().getClassLoader().getResource("sons/precedent.wav"));
		suivant = new Son(getClass().getClassLoader().getResource("sons/suivant.wav"));
		go = new Son(getClass().getClassLoader().getResource("sons/go.wav"));
		// lancement du son de bienvenue
		bienvenue.play();
		this.controle = controle;
	}
}
