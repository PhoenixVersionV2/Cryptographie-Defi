package impl.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import contract.model.MessageCrypte;

public class MessageCrypteImpl implements MessageCrypte{
	
	private ArrayList<Byte> messageCrypteBinaire= new ArrayList<Byte>();
	
	public MessageCrypteImpl(File file){
		ConversionBinaireMessage(file);
	}
	
	
	public void ConversionBinaireMessage(File fichier) {
		// On instancie nos objets :
        // fis va lire le fichier
        FileInputStream fis=null;
		try {
			fis = new FileInputStream(fichier);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // On cr�e un tableau de byte pour indiquer le nombre de bytes lus �
        // chaque tour de boucle
        byte[] buf = new byte[8];

        // On cr�e une variable de type int pour y affecter le r�sultat de
        // la lecture
        // Vaut -1 quand c'est fini
        int n = 0;

        // Tant que l'affectation dans la variable est possible, on boucle
        // Lorsque la lecture du fichier est termin�e l'affectation n'est
        // plus possible !
        // On sort donc de la boucle
        try {
			while ((n = fis.read(buf)) >= 0) {
			   for (int bit=0; bit<buf.length-1;bit++) {
			      messageCrypteBinaire.add(buf[bit]);
			   }
			   
			   //Nous r�initialisons le buffer � vide
			   //au cas o� les derniers byte lus ne soient pas un multiple de 8
			   //Ceci permet d'avoir un buffer vierge � chaque lecture et ne pas avoir de doublon en fin de fichier
			   buf = new byte[8];

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println("Copie termin�e !");
	}

}
