package impl.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
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
import impl.model.MessageClairImpl;

public class FenetreViewImpl implements FenetreView{
	
	private File fichier;
	
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
		fenetre.setSize(new Dimension(600,150));
		fenetre.setLocationRelativeTo(null);
		
		//Creation du panel nord
		JPanel panelNord=new JPanel();
		fenetre.add(panelNord, BorderLayout.NORTH);
				
		//Creation du panel centre
		JPanel panelCentre=new JPanel();
		fenetre.add(panelCentre, BorderLayout.CENTER);
						
		//Creation du panel sud
		JPanel panelSud=new JPanel();
		fenetre.add(panelSud, BorderLayout.SOUTH);
		
		//Creation du label avec les instructions
		JLabel instructionLabel=new JLabel();
		instructionLabel.setText("Ajouter le fichier à crypter ou décrypter, puis cliquez sur crypter ou décrypter");
		panelNord.add(instructionLabel);
				
		//Creation du label qui contiendra le fichier selectionne
		JLabel labelNomFichier = new JLabel("Aucun fichier sélectionné ...");
		panelCentre.add(labelNomFichier);
		
		//Creation du bouton parcourir
		JButton boutonParcourir=new JButton("Parcourir");
		panelCentre.add(boutonParcourir);
		
		//Creation du bouton pour crypter le fichier 
		JButton boutonCrypter=new JButton("Lancer le chiffrement du message");
		panelSud.add(boutonCrypter);
		
		//Creation du bouton pour decrypter le fichier
		JButton boutonDecrypter=new JButton("Lancer le déchiffrement du message");
		panelSud.add(boutonDecrypter);
		
		//Creation de l'evenement pour le bouton parcourir
		boutonParcourir.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
        			
            	EventBoutonParcourir(labelNomFichier);
            	
        	}
        });
		
		//Création de l'evenement pour le bouton de cryptage
		boutonCrypter.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
        			
            	EventBoutonCrypter();
            	
        	}
        });
	
		//La fenêtre devient visible
		fenetre.setVisible(true);
		
	}

	
	//Evénement du bouton parcourir
	public void EventBoutonParcourir(JLabel labelNomFichier) {
		
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
			}
			
		}
	}

	
	public void EventBoutonCrypter() {
		MessageClairImpl messageClair = new MessageClairImpl(fichier);
	}
	
}
