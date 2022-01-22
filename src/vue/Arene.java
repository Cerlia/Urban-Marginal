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
import controleur.Controle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * frame de l'arène du jeu
 * @author emds
 */
public class Arene extends JFrame implements Global {
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
	// controle pour communiquer avec contrôleur
	private Controle controle;
	// indique si l'arène est celle d'un client ou du serveur
	private boolean client = false;
	// mémorise le code de la touche si c'est une flèche, -1 sinon
	private int touche;
	
	/**
	 * getter sur le panel des murs
	 */
	public JPanel getJpnMurs() {
		return jpnMurs;		
	}
	
	/**
	 * setter sur le panel des murs
	 */
	public void setJpnMurs(JPanel jpnMurs) {
		this.jpnMurs.add(jpnMurs);
		this.jpnMurs.repaint();
	}
	
	/**
	 * getter sur le panel du jeu
	 */
	public JPanel getJpnJeu() {
		return jpnJeu;
	}
	
	/**
	 * setter sur le panel du jeu
	 */
	public void setJpnJeu(JPanel jpnJeu) {
		this.jpnJeu.removeAll();
		this.jpnJeu.add(jpnJeu);
		this.jpnJeu.repaint();
		this.contentPane.requestFocus();
	}
	
	/**
	 * getter sur la zone d'affichage du tchat
	 */
	public String getTxtChat() {
		return txtChat.getText();
	}

	/**
	 * setter sur la zone d'affichage du tchat
	 */
	public void setTxtChat(String txtChat) {
		this.txtChat.setText(txtChat);
		this.txtChat.setCaretPosition(this.txtChat.getDocument().getLength());
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
	 * Ajoute une phrase dans la zone de tchat
	 */
	public void ajoutTchat(String phrase) {
		this.txtChat.setText(this.txtChat.getText() + phrase + "\r\n");
		this.txtChat.setCaretPosition(this.txtChat.getDocument().getLength());
	}


	/**
	 * Réagit à l'appui d'une flèche
	 * @param e touche appuyée
	 */
	public void reactionToucheFleche(KeyEvent e) {
		if(e.getKeyCode() >= KeyEvent.VK_LEFT && e.getKeyCode() <= KeyEvent.VK_DOWN) {
			touche = e.getKeyCode();						
		}
		else {
			touche = -1;
		}
		if (touche != -1) {
			controle.evenementArene(touche);
		}
	}
	
	public void reactionToucheEntree(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!txtSaisie.getText().equals("")) {
				controle.evenementArene(txtSaisie.getText());
				txtSaisie.setText("");
				contentPane.requestFocus();
			}					
		}
	}
	
	
	/**
	 * Constructeur
	 */
	public Arene(Controle controle, String typeArene) {
		// Dimension de la frame en fonction de son contenu
		this.getContentPane().setPreferredSize(new Dimension(800, 600 + 25 + 140));
	    this.pack();
	    // interdiction de changer la taille
		this.setResizable(false);	
		
		this.controle = controle;
		if (typeArene != "serveur") {
			client = true;
		}
		
		setTitle("Arène");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		if (client) {
			contentPane.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					reactionToucheFleche(e);
				}
			});
		}

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
		
		if (client) {
			txtSaisie = new JTextField();
			txtSaisie.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					reactionToucheEntree(e);						
				}
			});
			txtSaisie.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					reactionToucheFleche(e);
				}
			});
			txtSaisie.setBounds(0, 600, 800, 25);
			contentPane.add(txtSaisie);
			txtSaisie.setColumns(10);
		}
		
		JScrollPane jspChat = new JScrollPane();
		jspChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jspChat.setBounds(0, 625, 800, 140);
		contentPane.add(jspChat);
		
		txtChat = new JTextArea();
		jspChat.setViewportView(txtChat);
		txtChat.setEditable(false);
		
		JLabel lblFond = new JLabel("");
		String chemin = "fonds/fondarene.jpg";
		URL resource = getClass().getClassLoader().getResource(chemin);
		lblFond.setIcon(new ImageIcon(resource));		
		lblFond.setBounds(0, 0, LARGARN, HAUTARN);
		contentPane.add(lblFond);		
	}
}
