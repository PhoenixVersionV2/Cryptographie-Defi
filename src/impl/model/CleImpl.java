package impl.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import contract.model.Cle;

public class CleImpl implements Cle{
	private ArrayList<Integer> cleBinaire = new ArrayList<Integer>();
	
	public CleImpl(String zoneEnregistrement){
		try {
			genererCle(256, zoneEnregistrement);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//creationMasque();
	}
	
	
	public CleImpl(File fichier){
		ConversionFichierEnBinaire(fichier);
	}
	
	
	public void ConversionFichierEnBinaire(File fichier){
		// On instancie nos objets :
        // fis va lire le fichier
        FileInputStream fis=null;
		try {
			fis = new FileInputStream(fichier);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // On crée un tableau de byte pour indiquer le nombre de bytes lus à
        // chaque tour de boucle
        byte[] buf = new byte[8];

        // On crée une variable de type int pour y affecter le résultat de
        // la lecture
        // Vaut -1 quand c'est fini
        int n = 0;

        // Tant que l'affectation dans la variable est possible, on boucle
        // Lorsque la lecture du fichier est terminée l'affectation n'est
        // plus possible !
        // On sort donc de la boucle

        ArrayList<Byte> bytes = new ArrayList<Byte>();
        try {
			while ((n = fis.read(buf)) >= 0) {
			   for (int bit=0; bit<buf.length-1;bit++) {
				   bytes.add(buf[bit]);
			   }
			   
			   //Nous réinitialisons le buffer à vide
			   //au cas où les derniers byte lus ne soient pas un multiple de 8
			   //Ceci permet d'avoir un buffer vierge à chaque lecture et ne pas avoir de doublon en fin de fichier
			   buf = new byte[8];

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //Nous transformons la liste de bytes en un StringBuilder correspondant à la clé en bit.
        StringBuilder binary=new StringBuilder();
        for (byte b : bytes)
		  {
		     int val = b;
		     for (int i = 0; i < 8; i++)
		     {
		    	 binary.append((val & 128) == 0 ? 0 : 1);
		        val <<= 1;
		     }
		     //binary.append(' ');
		 }
        
        //Les bits du StringBuilder sont stockés dans un tableau de int. Ces int correspondront à la clé en bit.
        for(int i=0; i<binary.length();i++){
        	cleBinaire.add(Integer.parseInt(binary.charAt(i)+""));
        }
        System.out.println("Copie clé terminée !");
	}
	
	
	public void genererCle(int length, String zoneEnregistrement) throws IOException{
			int nombreCaractere=length/8;
		    String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; // On peut supprimer les lettres dont on ne veut pas
		    String pass = "";
		    FileOutputStream fos = new FileOutputStream(new File(zoneEnregistrement+"/Cle2.txt"));
		    for(int x=0;x<nombreCaractere;x++)
		    {
		       int caractere = (int)Math.floor(Math.random() * 62); // Si on supprime des lettres on diminue ce nombre
		       pass += chars.charAt(caractere);
		       fos.write(chars.charAt(caractere));
		       
		    }
		    fos.close();
		    
		    //pass correspond à la clé en String. On la transforme ensuite en un tableau de bytes.
		    byte[] bytes = pass.getBytes();
		    
		    //Ensuite, nous transformons ce tableau de bytes en un StringBuilder qui correspondra aux bits.
		    StringBuilder binary = new StringBuilder();
			  for (byte b : bytes)
			  {
			     int val = b;
			     for (int i = 0; i < 8; i++)
			     {
			    	 binary.append((val & 128) == 0 ? 0 : 1);
			        val <<= 1;
			     }
			     
			     //binary.append(' ');
			 }
			  
			  //Finalement nous transformons le StringBuilder en une liste de int les int de la liste correspondant aux int.
			  for(int i=0; i<binary.length();i++){
		        	cleBinaire.add(Integer.parseInt(binary.charAt(i)+""));
		        }
			     System.out.println("clé 2 créée");
	}
	

	@Override
	public void creationMasque() {
		ArrayList<Integer> tableauInitial = new ArrayList<Integer>();
		
	}

}
