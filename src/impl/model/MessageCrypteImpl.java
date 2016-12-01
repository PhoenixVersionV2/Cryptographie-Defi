package impl.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextField;

import contract.model.MessageCrypte;

public class MessageCrypteImpl implements MessageCrypte {

	private ArrayList<Integer> messageCrypteBinaire = new ArrayList<Integer>();
	private CleImpl premiereCle;
	private CleImpl secondeCle;
	private File fichier;

	public MessageCrypteImpl(File file, JTextField textFieldCle1, JTextField textFieldCle2) {
		this.fichier=file;
		 ConversionBinaireMessage(file);
		 premiereCle = new CleImpl(textFieldCle1.getText(), messageCrypteBinaire.size());
		 secondeCle = new CleImpl(textFieldCle2.getText(), messageCrypteBinaire.size());
		 decryptageMessage();
	}

	public void ConversionBinaireMessage(File fichier) {
		FileInputStream fis = null;
        String str = "";

        try {
            fis = new FileInputStream(fichier.getPath());
            int content;
            while ((content = fis.read()) != -1) {
                // convert to char and display it
                str += (char) content;
            }

            System.out.println("Fin de lecture du message");
            System.out.println(str);
            
            byte[] bytes = str.getBytes();
            
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
            	messageCrypteBinaire.add(Integer.parseInt(binary.charAt(i)+""));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Copie message crypte terminée !");
	}

	
	public void decryptageMessage() {
		secondeCle.creationMasque();
		ArrayList<Integer> masqueBinaire = secondeCle.getMasqueBinaire();
		ArrayList<Integer> resultat=new ArrayList<Integer>();
		FileOutputStream fos=null;
		try {
			fos = new FileOutputStream(new File(fichier.getPath().substring(0, fichier.getPath().length()-4)+"Decrypte.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0;i<messageCrypteBinaire.size();i++){
			int transformation = messageCrypteBinaire.get(i)^masqueBinaire.get(i);
			resultat.add(transformation);
		}
		
		StringBuilder binary = new StringBuilder();
		for(int i = 0;i < resultat.size();i++){
			binary.append(resultat.get(i));
		}

	    for (int i = 0; i < binary.length()/8; i++) {
	        int a = Integer.parseInt(binary.substring(8*i,(i+1)*8),2);
	        
	        try {
				fos.write((char)a);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    System.out.println("Message décrypté !");
	    
	}

}
