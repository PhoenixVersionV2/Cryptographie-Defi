package contract.model;

import java.io.File;

public interface Cle {
	
	public void ConversionFichierEnBinaire(File fichier);
	public void creationMasque();
	public void genererCle(int length);
	
}
