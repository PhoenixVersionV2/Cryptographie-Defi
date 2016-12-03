package impl.model;

import java.util.ArrayList;

public class CBCImpl {
	
	private ArrayList<Integer> cle;
	private ArrayList<Integer> iv = new ArrayList<Integer>();
	private ArrayList<Integer> iv2 = new ArrayList<Integer>();
	private byte[] tabByte;
	private byte[] tabByteCrypte;
	private byte[] tabByteDecrypte;
	
	public CBCImpl(ArrayList<Integer> listByte, String iv, ArrayList<Integer> cle)
	{
		tabByte = new byte[listByte.size()];
		for(int p = 0 ; p < listByte.size() ; p++)
		{
			if(listByte.get(p) == 1)
			{
				tabByte[p] = 1;
			}
			else if(listByte.get(p) == 0)
			{
				tabByte[p] = 0;
			}
		}
		for(int i = 0 ; i < 4 ; i++)
		{
			this.iv.add(Integer.parseInt(Character.toString(iv.charAt(i))));
		}
		this.cle = cle;
	}
	
	public ArrayList<Integer> crypterCBC()
	{
		tabByteCrypte = new byte[tabByte.length];
		for(int j = 0 ; j < tabByte.length ; j++)
		{
			if(tabByte[j] == 1)
			{
				if(1 == cle.get(j % (cle.size())))
				{
					if(iv.get(j % 4) == 1)
					{
						tabByteCrypte[j] = 1;
					}
					else if(iv.get(j % 4) == 0)
					{
						tabByteCrypte[j] = 0;
					}
				}
				else if(0 == cle.get(j % (cle.size())))
				{
					if(iv.get(j % 4) == 1)
					{
						tabByteCrypte[j] = 0;
					}
					else if(iv.get(j % 4) == 0)
					{
						tabByteCrypte[j] = 1;
					}
				}
				
			}
			else if(tabByte[j] == 0)
			{
				if(1 == cle.get(j % (cle.size())))
				{
					if(iv.get(j % 4) == 1)
					{
						tabByteCrypte[j] = 0;
					}
					else if(iv.get(j % 4) == 0)
					{
						tabByteCrypte[j] = 1;
					}
				}
				else if(0 == cle.get(j % (cle.size())))
				{
					if(iv.get(j % 4) == 1)
					{
						tabByteCrypte[j] = 1;
					}
					else if(iv.get(j % 4) == 0)
					{
						tabByteCrypte[j] = 0;
					}
				}
			}
			if(j % 4 == 0 && j != 0)
			{
				iv = new ArrayList<Integer>();
				for(int n = j - 4 ; n < j ; n++)
				{
					if(tabByteCrypte[n] == 1)
					{
						iv.add(1);
					}
					else if(tabByteCrypte[n] == 0)
					{
						iv.add(0);
					}
				}
			}
		}
		ArrayList<Integer> listByte = new ArrayList<Integer>();
		for(int p = 0 ; p < tabByte.length ; p++)
		{
			if(tabByte[p] == 1)
			{
				listByte.add(1);
			}
			else if(tabByte[p] == 0)
			{
				listByte.add(0);
			}
		}
		return listByte;
	}

	public ArrayList<Integer> decrypterCBC()
	{
		tabByteDecrypte = new byte[tabByte.length];
		for(int j = tabByte.length - 1; j >= 0 ; j--)
		{
			//Calcul de iv2
			if(j >= 4 && (j+1) % 4 == 0)
			{
				iv2 = new ArrayList<Integer>();
				for(int n = j - 7 ; n <= j-4 ; n++)
				{
					if(Integer.parseInt(Byte.toString(tabByte[n])) == 1)
					{
						iv2.add(1);
					}
					else if(Integer.parseInt(Byte.toString(tabByte[n])) == 0)
					{
						iv2.add(0);
					}
				}
			}
			else if(j == 4)
			{
				iv2 = iv;
			}
			System.out.println(iv2.get(0)+""+iv2.get(1)+""+iv2.get(2)+""+iv2.get(3));
			if(tabByte[j] == 1)
			{
				if(1 == cle.get(j % (cle.size())))
				{
					if(iv2.get(j % 4) == 1)
					{
						tabByteDecrypte[j] = 1;
					}
					else if(iv2.get(j % 4) == 0)
					{
						tabByteDecrypte[j] = 0;
					}
				}
				else if(0 == cle.get(j % (cle.size())))
				{
					if(iv2.get(j % 4) == 1)
					{
						tabByteDecrypte[j] = 0;
					}
					else if(iv2.get(j % 4) == 0)
					{
						tabByteDecrypte[j] = 1;
					}
				}
				
			}
			else if(tabByte[j] == 0)
			{
				if(1 == cle.get(j % (cle.size())))
				{
					if(iv2.get(j % 4) == 1)
					{
						tabByteDecrypte[j] = 0;
					}
					else if(iv2.get(j % 4) == 0)
					{
						tabByteDecrypte[j] = 1;
					}
				}
				else if(0 == cle.get(j % (cle.size())))
				{
					if(iv2.get(j % 4) == 1)
					{
						tabByteDecrypte[j] = 1;
					}
					else if(iv2.get(j % 4) == 0)
					{
						tabByteDecrypte[j] = 0;
					}
				}
			}
		}
		ArrayList<Integer> listByte = new ArrayList<Integer>();
		for(int p = 0 ; p < tabByte.length; p++)
		{
			if(tabByte[p] == 1)
			{
				listByte.add(1);
			}
			else if(tabByte[p] == 0)
			{
				listByte.add(0);
			}
		}
		return listByte;
	}
}
