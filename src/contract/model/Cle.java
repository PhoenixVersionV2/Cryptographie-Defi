package contract.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface Cle {
	
	public void ConversionCleEnBinaire(String cle);
	public void creationMasque();
	void creationTableauInitial();
	void insertionCleDansTableauInitial();
	void generationMasque();
}
