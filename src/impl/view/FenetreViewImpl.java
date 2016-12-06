package impl.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import contract.view.FenetreView;
import impl.model.MessageClairImpl;
import impl.model.MessageCrypteImpl;

public class FenetreViewImpl implements FenetreView, ActionListener{
	
	private File fichier, message;
	private JTextField textFieldIV=new JTextField();
	private JTextField textFieldCle1=new JTextField();
	private JTextField textFieldCle2=new JTextField();
	private JLabel labelNomFichier = new JLabel("Aucun fichier sélectionné ...");
	
	
	public FenetreViewImpl(){
		
		//Creation de la Frame, base de l’interface utilisateur.
		JFrame fenetre=new JFrame();
		//Mise en place des options de la frame
		fenetre.pack();
		JFrame.setDefaultLookAndFeelDecorated(true);
		fenetre.setExtendedState(Frame.MAXIMIZED_BOTH);
		fenetre.setTitle("Cryptographie - Défi");
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setResizable(false);
		fenetre.setLayout(new BorderLayout());
		fenetre.setSize(new Dimension(600,300));
		fenetre.setLocationRelativeTo(null);
		
		//Creation du panel nord
		JPanel panelNord=new JPanel();
		fenetre.add(panelNord, BorderLayout.NORTH);
				
		//Creation du panel centre
		JPanel panelCentre=new JPanel();
		panelCentre.setLayout(new GridLayout(4,1));
		fenetre.add(panelCentre, BorderLayout.CENTER);
						
		//Creation du panel sud
		JPanel panelSud=new JPanel();
		fenetre.add(panelSud, BorderLayout.SOUTH);
		
		//Creation du label avec les instructions
		JLabel instructionLabel=new JLabel();
		instructionLabel.setText("<html>1. Ajouter le fichier à chiffrer ou déchiffrer.<br>2. Ajouter la clé n°1.<br>3. Ajouter le vecteur d'initialisation (4 bits)<br>4. Ajouter la clé n°2 <br>5. Cliquez sur crypter ou décrypter selon ce que vous souhaitez faire</html>");
		panelNord.add(instructionLabel);
		
		//Création du panel pour obtenir le fichier
		JPanel panelFichier=new JPanel();
		panelCentre.add(panelFichier);

		//Création du panel pour obtenir la clé 1
		JPanel panelCle1=new JPanel();
		panelCentre.add(panelCle1);

		//Création du panel pour obtenir le vecteur d'initialisation
		JPanel panelIV=new JPanel();
		panelCentre.add(panelIV);

		//Création du panel pour obtenir la clé 2
		JPanel panelCle2=new JPanel();
		panelCentre.add(panelCle2);
		
		//Ajout du label qui contiendra le fichier selectionne
		panelFichier.add(labelNomFichier);
		
		//Creation du bouton parcourir pour le fichier
		JButton boutonParcourirFichier=new JButton("Parcourir");
		panelFichier.add(boutonParcourirFichier);
		
		//Ajout du label qui de la clé 1

		JLabel labelNomCle1 = new JLabel("Clé n°1 :");
		panelCle1.add(labelNomCle1);
		
		//Creation du JTextField pour la clé 1
		textFieldCle1.setPreferredSize(new Dimension(385,30));
		panelCle1.add(textFieldCle1);
		
		//Ajout du label pour le vecteur d'initialisation
		JLabel labelIV = new JLabel("Vecteur d'Initialisation :");
		panelIV.add(labelIV);
		
		//Creation du JTextField pour le vecteur d'initialisation
		textFieldIV.setPreferredSize(new Dimension(300,30));
		panelIV.add(textFieldIV);
		
		//Ajout du label pour la clé 2
		JLabel labelNomCle2 = new JLabel("Clé n°2 :");
		panelCle2.add(labelNomCle2);
		
		//Creation du  JTextField pour la clé 2
		textFieldCle2.setPreferredSize(new Dimension(385,30));
		panelCle2.add(textFieldCle2);
		
		//Creation du bouton pour crypter le fichier 
		JButton boutonCrypter=new JButton("Lancer le chiffrement du message");
		panelSud.add(boutonCrypter);
		
		//Creation du bouton pour decrypter le fichier
		JButton boutonDecrypter=new JButton("Lancer le déchiffrement du message");
		panelSud.add(boutonDecrypter);

		//Creation de l'evenement pour le bouton parcourir du fichier
		boutonParcourirFichier.setActionCommand("parcourirFichier");
		boutonParcourirFichier.addActionListener(this);
		
		//Creation de l'evenement pour le IV
		textFieldIV.addKeyListener(new KeyAdapter() {
		        public void keyTyped(KeyEvent e) {
		        	EventJTextFieldIV(e);
		        }
		});
		
		//Création de l'evenement pour le bouton de cryptage
		boutonCrypter.setActionCommand("boutonCrypter");
		boutonCrypter.addActionListener(this);
		
		//Création de l'evenement pour le bouton de decryptage
		boutonDecrypter.setActionCommand("boutonDecrypter");
		boutonDecrypter.addActionListener(this);
		
		//La fenêtre devient visible
		fenetre.setVisible(true);
		
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand()=="parcourirFichier"){
			EventBoutonParcourir();
		}else if(arg0.getActionCommand()=="boutonCrypter"){
			EventBoutonCrypter();
		}else if(arg0.getActionCommand()=="boutonDecrypter"){
			EventBoutonDecrypter();
		}
	}
	
	//Evénement des boutons parcourir
	public void EventBoutonParcourir() {
		
		//Creation de la boite de dialogue pour recuperer le fichier
		JFileChooser dialogue = new JFileChooser(".");
		
		//Mise en place d'un filtre pour selectionner seulement les fichiers textes (txt)
		FileNameExtensionFilter ff = new FileNameExtensionFilter("Fichiers texte", "txt");
		dialogue.setFileFilter(ff);
		
		//Seuls les fichiers peuvent être selectionnable
		dialogue.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		//Par defaut on demarre la recherche à la base de l'explorateur de fichier
		dialogue.setCurrentDirectory(new File("/")); 
		dialogue.changeToParentDirectory(); 
		
		//Affichage de la boite de dialogue
		int returnVal=dialogue.showOpenDialog(null);
		
		if(returnVal != JOptionPane.NO_OPTION && returnVal != JOptionPane.CANCEL_OPTION && returnVal != JOptionPane.CLOSED_OPTION){
			
			//Recuperation du fichier
			fichier = dialogue.getSelectedFile();
			
			//Variable pour terminer le while de verification
			boolean continuer=true;
			
			//while de verification. Il demarre si l'extension du fichier n'est pas '.txt'
			while(continuer && !fichier.getPath().substring(fichier.getPath().length()-4, fichier.getPath().length()).equals(".txt")){
				
				JOptionPane.showMessageDialog(null, "Nous n'acceptons que les fichiers texte ('.txt').", "Erreur Fichier", JOptionPane.INFORMATION_MESSAGE);
				int returnVal2=dialogue.showOpenDialog(null);
				if(returnVal2 == JOptionPane.NO_OPTION || returnVal2 == JOptionPane.CANCEL_OPTION || returnVal2 == JOptionPane.CLOSED_OPTION){
					continuer=false;
				}
				
			}
			
			//Si le fichier est bien un fichier texte alors on affiche qu'un fichier a bien ete selectionne. 
			if(continuer == true){
					labelNomFichier.setText("Fichier sélectionné : "+fichier.getPath());
					labelNomFichier.repaint();
					message=fichier;
			}
			
		}
	}

	public void EventJTextFieldIV(KeyEvent e){
		String keyCode=e.getKeyChar()+"";
		String texte=textFieldIV.getText();
		if((keyCode.equals("1")||keyCode.equals("0"))&&texte.length()<4){
		}else{
			e.consume();
		}
	}
	
	public void EventBoutonCrypter() {
		if(!textFieldCle1.getText().equals("")&&!textFieldCle2.getText().equals("")&&!textFieldCle1.getText().equals(textFieldCle2.getText())){
			if(!textFieldIV.getText().equals("")){
				if(message!=null){
					MessageClairImpl messageClair = new MessageClairImpl(message, textFieldCle1, textFieldCle2, textFieldIV);
				}else{
					JOptionPane.showMessageDialog(null, "Il nous manque le fichier afin de mener à bien le cryptage/décryptage.", "Erreur Fichier", JOptionPane.INFORMATION_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(null, "Il manque une le vecteur d'initialisation, veuillez le compléter svp (4 bits).", "Erreur Vecteur Initialisation", JOptionPane.INFORMATION_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(null, "Il manque une clé ou les deux clés sont identiques, veuillez corriger cela svp.", "Erreur Clé", JOptionPane.INFORMATION_MESSAGE);
		}
	}


	@Override
	public void EventBoutonDecrypter() {
		if(!textFieldCle1.getText().equals("")&&!textFieldCle2.getText().equals("")&&!textFieldCle1.getText().equals(textFieldCle2.getText())){
			if(!textFieldIV.getText().equals("")){
				if(message!=null){
					MessageCrypteImpl messageCrypte = new MessageCrypteImpl(fichier, textFieldCle1, textFieldCle2, textFieldIV);	
				}else{
					JOptionPane.showMessageDialog(null, "Il nous manque le fichier afin de mener à bien le decryptage.", "Erreur Fichier", JOptionPane.INFORMATION_MESSAGE);	
				}
			}else{
				JOptionPane.showMessageDialog(null, "Il manque une le vecteur d'initialisation, veuillez le compléter svp (4 bits).", "Erreur Vecteur Initialisation", JOptionPane.INFORMATION_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(null, "Il manque une clé ou les deux clés sont identiques, veuillez corriger cela svp.", "Erreur Clé", JOptionPane.INFORMATION_MESSAGE);
		}
	}

}
