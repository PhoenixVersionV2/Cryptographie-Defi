package contract.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface Cle {
	
	public void ConversionFichierEnBinaire(File fichier);
	public void creationMasque();
	public void genererCle(int length, String zoneEnregistrement) throws IOException;
	
}
