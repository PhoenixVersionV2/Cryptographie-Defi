package impl.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import contract.model.MessageClair;

public class MessageClairImpl implements MessageClair{

	private ArrayList<Integer> messageClairBinaire= new ArrayList<Integer>();
	private CleImpl premiereCle;
	private CleImpl secondeCle;
	private String iv;
	private File fichier;
	
	public MessageClairImpl(File file, JTextField textFieldCle1, JTextField textFieldCle2, JTextField textFieldIV){
		 this.fichier=file;
		 ConversionBinaireMessage(file);
		 iv = textFieldIV.getText();
		 premiereCle = new CleImpl(textFieldCle1.getText(), messageClairBinaire.size());
		 secondeCle = new CleImpl(textFieldCle2.getText(), messageClairBinaire.size());
		 Boolean reponse=cryptageMessage();
		 if(reponse){
			 JOptionPane.showMessageDialog(null, "Message Crypté.", "Réussite", JOptionPane.INFORMATION_MESSAGE);
		 }
	}

	
	public void ConversionBinaireMessage(File fichier) {
		 	long filesize = fichier.length();
		 	byte data[] =  new byte[(int)filesize];
		 	try {
		 		DataInputStream in = new DataInputStream(new FileInputStream(fichier));
				in.readFully(data);
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	
            //Nous transformons la liste de bytes en un StringBuilder correspondant à la clé en bit.
            StringBuilder binary=new StringBuilder();
            for (byte b : data)
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
            	messageClairBinaire.add(Integer.parseInt(binary.charAt(i)+""));
            }
            
        System.out.println("Copie message clair terminée !");
	}
	
	public boolean cryptageMessage(){
			CBCImpl cbc = new CBCImpl(messageClairBinaire, iv, premiereCle.getCleBinaire());
			ArrayList<Integer> valeurIntermediaire=cbc.crypterCBC();
			secondeCle.creationMasque();
			ArrayList<Integer> masqueBinaire = secondeCle.getMasqueBinaire();
			ArrayList<Integer> resultat=new ArrayList<Integer>();
			DataOutputStream fos=null;
			try {
				fos = new DataOutputStream(new FileOutputStream(new File(fichier.getPath().substring(0, fichier.getPath().length()-4)+"Crypte.txt")));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			
			for(int i=0;i<valeurIntermediaire.size();i++){
				int transformation = valeurIntermediaire.get(i)^masqueBinaire.get(i);
				resultat.add(transformation);
			}
			
			StringBuilder binary = new StringBuilder();
			for(int i = 0;i < resultat.size();i++){
				binary.append(resultat.get(i));
			}

			byte tabByte[]= new byte[binary.length()/8];
		    for (int i = 0; i < binary.length()/8; i++) {
		        tabByte[i] = (byte) Integer.parseInt(binary.substring(8*i,(i+1)*8),2);
		    }
		    
		    try {
		    	for(int j=0;j<tabByte.length;j++){
		    		fos.write(tabByte[j]);
		    	}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		    System.out.println("Message crypté !");
		    return true;
		    
	}
}
