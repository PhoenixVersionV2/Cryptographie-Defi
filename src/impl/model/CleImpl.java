package impl.model;

import java.io.File;
import java.util.ArrayList;

import contract.model.Cle;

public class CleImpl implements Cle{
	private ArrayList<Byte> cleBinaire=new ArrayList<Byte>();
	
	public CleImpl(File fichier){
		ConversionBinaireCle(fichier);
	}
	
	public void ConversionBinaireCle(File fichier){
		
	}
}
