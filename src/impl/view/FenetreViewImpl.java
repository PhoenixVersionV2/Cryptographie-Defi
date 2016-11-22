package impl.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import contract.view.FenetreView;
import impl.model.CleImpl;
import impl.model.MessageClairImpl;
import impl.model.MessageCrypteImpl;

public class FenetreViewImpl implements FenetreView, ActionListener{
	
	private File fichier, message, cle1, cle2;
	private JLabel labelNomFichier = new JLabel("Aucun fichier s�lectionn� ...");
	private JLabel labelNomCle1 = new JLabel("Cl� n�1 non s�lectionn�e ...");
	private JLabel labelNomCle2 = new JLabel("Cl� n�2 non s�lectionn�e ...");
	
	
	public FenetreViewImpl(){
		
		//Creation de la Frame, base de l�interface utilisateur.
		JFrame fenetre=new JFrame();
		//Mise en place des options de la frame
		fenetre.pack();
		JFrame.setDefaultLookAndFeelDecorated(true);
		fenetre.setExtendedState(Frame.MAXIMIZED_BOTH);
		fenetre.setTitle("Cryptographie - D�fi");
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setResizable(false);
		fenetre.setLayout(new BorderLayout());
		fenetre.setSize(new Dimension(600,250));
		fenetre.setLocationRelativeTo(null);
		
		//Creation du panel nord
		JPanel panelNord=new JPanel();
		fenetre.add(panelNord, BorderLayout.NORTH);
				
		//Creation du panel centre
		JPanel panelCentre=new JPanel();
		panelCentre.setLayout(new GridLayout(3,1));
		fenetre.add(panelCentre, BorderLayout.CENTER);
						
		//Creation du panel sud
		JPanel panelSud=new JPanel();
		fenetre.add(panelSud, BorderLayout.SOUTH);
		
		//Creation du label avec les instructions
		JLabel instructionLabel=new JLabel();
		instructionLabel.setText("<html>1. Ajouter le fichier � crypter ou d�crypter.<br>2. Ajouter la cl� n�1.<br>3. Ajouter la cl� n�2 seulement si c'est pour d�crypter.<br>4.cliquez sur crypter ou d�crypter</html>");
		panelNord.add(instructionLabel);
		
		//Cr�ation du panel pour obtenir le fichier
		JPanel panelFichier=new JPanel();
		panelCentre.add(panelFichier);

		//Cr�ation du panel pour obtenir la cl� 1
		JPanel panelCle1=new JPanel();
		panelCentre.add(panelCle1);

		//Cr�ation du panel pour obtenir la cl� 2
		JPanel panelCle2=new JPanel();
		panelCentre.add(panelCle2);
		
		//Ajout du label qui contiendra le fichier selectionne
		panelFichier.add(labelNomFichier);
		
		//Creation du bouton parcourir pour le fichier
		JButton boutonParcourirFichier=new JButton("Parcourir");
		panelFichier.add(boutonParcourirFichier);
		
		//Ajout du label qui contiendra le nom de la cl� 1 selectionne
		panelCle1.add(labelNomCle1);
		
		//Creation du bouton parcourir pour la cl� 1
		JButton boutonParcourirCle1=new JButton("Parcourir");
		panelCle1.add(boutonParcourirCle1);
		
		//Ajout du label qui contiendra le nom de la cl� 2 selectionne
		panelCle2.add(labelNomCle2);
		
		//Creation du bouton parcourir pour la cl� 2
		JButton boutonParcourirCle2=new JButton("Parcourir");
		panelCle2.add(boutonParcourirCle2);
		
		//Creation du bouton pour crypter le fichier 
		JButton boutonCrypter=new JButton("Lancer le chiffrement du message");
		panelSud.add(boutonCrypter);
		
		//Creation du bouton pour decrypter le fichier
		JButton boutonDecrypter=new JButton("Lancer le d�chiffrement du message");
		panelSud.add(boutonDecrypter);

		//Creation de l'evenement pour le bouton parcourir du fichier
		boutonParcourirFichier.setActionCommand("parcourirFichier");
		boutonParcourirFichier.addActionListener(this);
		
		//Creation de l'evenement pour le bouton parcourir de la cl� 1
		boutonParcourirCle1.setActionCommand("parcourirCle1");
		boutonParcourirCle1.addActionListener(this);
		
		//Creation de l'evenement pour le bouton parcourir de la cl� 2
		boutonParcourirCle2.setActionCommand("parcourirCle2");
		boutonParcourirCle2.addActionListener(this);
		
		//Cr�ation de l'evenement pour le bouton de cryptage
		boutonCrypter.setActionCommand("boutonCrypter");
		boutonCrypter.addActionListener(this);
		
		//Cr�ation de l'evenement pour le bouton de decryptage
		boutonDecrypter.setActionCommand("boutonDecrypter");
		boutonDecrypter.addActionListener(this);
		
		//La fen�tre devient visible
		fenetre.setVisible(true);
		
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand()=="parcourirFichier"){
			EventBoutonParcourir("message");
		}else if(arg0.getActionCommand()=="parcourirCle1"){
			EventBoutonParcourir("cle1");
		}else if(arg0.getActionCommand()=="parcourirCle2"){
			EventBoutonParcourir("cle2");
		}else if(arg0.getActionCommand()=="boutonCrypter"){
			EventBoutonCrypter();
		}else if(arg0.getActionCommand()=="boutonDecrypter"){
			EventBoutonDecrypter();
		}
	}
	
	//Ev�nement des boutons parcourir
	public void EventBoutonParcourir(String nomFichier) {
		
		//Creation de la boite de dialogue pour recuperer le fichier
		JFileChooser dialogue = new JFileChooser(".");
		
		//Mise en place d'un filtre pour selectionner seulement les fichiers textes (txt)
		FileNameExtensionFilter ff = new FileNameExtensionFilter("Fichiers texte", "txt");
		dialogue.setFileFilter(ff);
		
		//Seuls les fichiers peuvent �tre selectionnable
		dialogue.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		//Par defaut on demarre la recherche � la base de l'explorateur de fichier
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
				if(nomFichier=="message"){
					labelNomFichier.setText("Fichier s�lectionn� : "+fichier.getPath());
					labelNomFichier.repaint();
					message=fichier;
				}else if(nomFichier=="cle1"){
					labelNomCle1.setText("Cl� 1 s�lectionn�e : "+fichier.getPath());
					labelNomCle1.repaint();
					cle1=fichier;
				}else if(nomFichier=="cle2"){
					labelNomCle2.setText("Cl� 2 s�lectionn�e : "+fichier.getPath());
					labelNomCle2.repaint();
					cle2=fichier;
				}
			}
			
		}
	}

	
	public void EventBoutonCrypter() {
		if(cle1!=null&&message!=null){
			CleImpl premiereCle=new CleImpl(cle1);
			MessageClairImpl messageClair = new MessageClairImpl(message);
		}else{
			JOptionPane.showMessageDialog(null, "Il nous manque un fichier afin de mener � bien le cryptage du fichier.", "Erreur Fichier", JOptionPane.INFORMATION_MESSAGE);
		}
	}


	@Override
	public void EventBoutonDecrypter() {
		if(cle1!=null&&cle2!=null&&message!=null){
			CleImpl premiereCle=new CleImpl(cle1);
			CleImpl secondeCle= new CleImpl(cle2);
			MessageCrypteImpl messageCrypte = new MessageCrypteImpl(fichier);	
		}else{
			JOptionPane.showMessageDialog(null, "Il nous manque un fichier afin de mener � bien le decryptage du fichier.", "Erreur Fichier", JOptionPane.INFORMATION_MESSAGE);	
		}
	}
	
}
