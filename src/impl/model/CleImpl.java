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
	
	public CleImpl(String fichier){
		ConversionCleEnBinaire(fichier);
	}
	
	
	public void ConversionCleEnBinaire(String fichier){
		byte[] bytes = fichier.getBytes();
        
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

	@Override
	public void creationMasque() {
		ArrayList<Integer> tableauInitial = new ArrayList<Integer>();
		for(int i=0;i<cleBinaire.size();i++){
			if((i%2)==0){
				tableauInitial.add(0);
			}else{
				tableauInitial.add(1);
			}
		}
	}

}
