import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;


public class SplitFile {
	
	private final static String fileName = "/home/gshadrin/trigrams/rus_web_2002_300K-sentences";
	
	 public static void main(String[] args) throws Exception
	 {
		 BufferedReader reader = new BufferedReader(new FileReader(fileName));
		 String s;
		 
		 int filenum = 0;
		 int count = 0;
		 
		  //Объект, позволяющий осуществить запись в файл
		 PrintWriter wr = new PrintWriter(new File(fileName + "_" + filenum));
		 
		 
		 while ((s = reader.readLine()) != null) 
		 {
			 wr.println(s);
			 count ++;

			 if (count > 20000)
			 {
				 wr.close();
				 count = 0;
				 filenum++;
				 wr = new PrintWriter(new File(fileName + "_" + filenum));
				 System.out.println("file num: " + filenum);
			 }
		 } 
		 wr.close();
		 
	 }
}
