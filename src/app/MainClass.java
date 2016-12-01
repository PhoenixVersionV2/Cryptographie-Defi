package app;

import impl.model.CleImpl;
import impl.view.FenetreViewImpl;

public class MainClass {

	public static void main(String[] args) {
		//FenetreViewImpl fenetre= new FenetreViewImpl();
		CleImpl cle=new CleImpl("EUGENIE", 5000);
		cle.creationMasque();
	}

}
