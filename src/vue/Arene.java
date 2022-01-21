package vue;

import java.awt.Dimension;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import controleur.Global;

/**
 * frame de l'arène du jeu
 * @author emds
 */
public class Arene extends JFrame implements Global{
	// Panel général
	private JPanel contentPane;
	// Zone de saisie du t'chat
	private JTextField txtSaisie;
	// Zone d'affichage du tchat
	private JTextArea txtChat;
	// Panel des murs
	private JPanel jpnMurs;
	// Panel des personnages
	private JPanel jpnJeu;
	
	/**
	 * getter sur le panel des murs
	 * @return
	 */
	public JPanel getJpnMurs() {
		return jpnMurs;		
	}
	
	/**
	 * setter sur le panel des murs
	 * @param jpnMurs
	 */
	public void setJpnMurs(JPanel jpnMurs) {
		this.jpnMurs.add(jpnMurs);
		this.jpnMurs.repaint();
	}
	
	/**
	 * getter sur le panel du jeu
	 * @return
	 */
	public JPanel getJpnJeu() {
		return jpnJeu;
	}
	
	/**
	 * setter sur le panel du jeu
	 * @param jpnJeu
	 */
	public void setJpnJeu(JPanel jpnJeu) {
		this.jpnJeu.removeAll();
		this.jpnJeu.add(jpnJeu);
		this.jpnJeu.repaint();
	}
	
	
	/**
	 * Ajoute un mur au panel jpnMurs
	 */
	public void ajoutMurs(Object unMur) {
		jpnMurs.add((JLabel)unMur);
		jpnMurs.repaint();
	}
	
	/**
	 * Ajoute un élément JLabel au panel jpnJeu
	 * @param unJLabel
	 */
	public void ajoutJLabelJeu(JLabel unJLabel) {
		this.jpnJeu.add(unJLabel);
		this.jpnJeu.repaint();		
	}	

	/**
	 * Constructeur
	 */
	public Arene() {
		// Dimension de la frame en fonction de son contenu
		this.getContentPane().setPreferredSize(new Dimension(800, 600 + 25 + 140));
	    this.pack();
	    // interdiction de changer la taille
		this.setResizable(false);		
		
		setTitle("Arène");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		jpnMurs = new JPanel();
		jpnMurs.setBounds(0, 0, LARGARN, HAUTARN);
		jpnMurs.setOpaque(false);
		jpnMurs.setLayout(null);		
		contentPane.add(jpnMurs);
		
		jpnJeu = new JPanel();
		jpnJeu.setBounds(0, 0, LARGARN, HAUTARN);
		jpnJeu.setOpaque(false);
		jpnJeu.setLayout(null);		
		contentPane.add(jpnJeu);
	
		txtSaisie = new JTextField();
		txtSaisie.setBounds(0, 600, 800, 25);
		contentPane.add(txtSaisie);
		txtSaisie.setColumns(10);
		
		JScrollPane jspChat = new JScrollPane();
		jspChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jspChat.setBounds(0, 625, 800, 140);
		contentPane.add(jspChat);
		
		txtChat = new JTextArea();
		jspChat.setViewportView(txtChat);
		
		JLabel lblFond = new JLabel("");
		String chemin = "fonds/fondarene.jpg";
		URL resource = getClass().getClassLoader().getResource(chemin);
		lblFond.setIcon(new ImageIcon(resource));		
		lblFond.setBounds(0, 0, LARGARN, HAUTARN);
		contentPane.add(lblFond);		
	}
}
