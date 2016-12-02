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
	private ArrayList<Integer> masqueBinaire = new ArrayList<Integer>();
	private ArrayList<Integer> tableauInitial;
	private int tailleTexte=0;
	
	public CleImpl(String cle, int tailleFichier){
		ConversionCleEnBinaire(cle);
		this.tailleTexte=tailleFichier;
	}
	
	
	public void ConversionCleEnBinaire(String fichier){
		System.out.println(fichier);
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
	
	public void creationMasque(){
		creationTableauInitial();
		insertionCleDansTableauInitial();
		generationMasque();
	}

	
	@Override
	public void creationTableauInitial() {
		tableauInitial = new ArrayList<Integer>();
		for(int i=0;i<cleBinaire.size();i++){
			if((i%2)==0){
				tableauInitial.add(0);
			}else{
				tableauInitial.add(1);
			}
		}
	}
	
	
	public void insertionCleDansTableauInitial(){
		for(int i=0;i<cleBinaire.size();i++){
			int dernierBit = tableauInitial.get(tableauInitial.size()-1);
			int extraction1 = tableauInitial.get((tableauInitial.size()*7/8)-1);
			int extraction2 = tableauInitial.get((tableauInitial.size()/2)-1);
			int extraction3 = tableauInitial.get((tableauInitial.size()/4)-1);
			int resultat1=dernierBit^extraction1;
			int resultat2=resultat1^extraction2;
			int resultat3=resultat2^extraction3;
			int resultat=cleBinaire.get(i)^resultat3;
			tableauInitial.remove(tableauInitial.size()-1);
			tableauInitial.add(0, resultat);
		}
	}
	
	
	public void generationMasque(){
		for(int i=0;i<tailleTexte;i++){
			masqueBinaire.add(tableauInitial.get(tableauInitial.size()-1));
			int dernierBit = tableauInitial.get(tableauInitial.size()-1);
			int extraction1 = tableauInitial.get((tableauInitial.size()*7/8)-1);
			int extraction2 = tableauInitial.get((tableauInitial.size()/2)-1);
			int extraction3 = tableauInitial.get((tableauInitial.size()/4)-1);
			int resultat1=dernierBit^extraction1;
			int resultat2=resultat1^extraction2;
			int resultat3=resultat2^extraction3;
			tableauInitial.remove(tableauInitial.size()-1);
			tableauInitial.add(0, resultat3);
		}
		System.out.println("Masque généré");
	}


	public ArrayList<Integer> getMasqueBinaire() {
		return masqueBinaire;
	}


	public void setMasqueBinaire(ArrayList<Integer> masqueBinaire) {
		this.masqueBinaire = masqueBinaire;
	}

}
