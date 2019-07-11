//Zhexin Chen (zhexinc)
package hw3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class Model {
	static ObservableMap<String,Product> productsMap=FXCollections.observableHashMap();
	static ObservableMap<String,Nutrient> nutrientsMap=FXCollections.observableHashMap();
	ObservableList<Product> searchResultsList = FXCollections.observableArrayList();
	
	void writeProfile(String filename) {
		
	}
	void readProducts(String filename) {
		try {
			// Use BufferedReader to read larger data
			BufferedReader br = new BufferedReader(new FileReader(filename));
			// load each line to a string
			String str = br.readLine();
			while (str != null) {
				str = br.readLine();
				if (str== null) break;
				//use a complex token "," to split data because every entry in the file was double quotes
				//therefore we need to handle first and last entry
				//and we should notice continuous empty entries in the line
				String[] s = str.substring(1, str.length()-1).split("\",\"");
//				if (s.length==7) {
//					String[] temp2=new String[8];
//					for (int i=0;i<s.length;i++)
//						temp2[i]=s[i];
//					temp2[7]="";
//					s=temp2;
//					}
				//put new entry into the products map
				if (s.length>=8) productsMap.put(s[0],new Product(s[0],s[1],s[4],s[7]));
				//in case the last entry was empty - it will not be recorded by my method so we need to assign an empty string to it
				else productsMap.put(s[0],new Product(s[0],s[1],s[4],""));
			}
			br.close();
		}
		catch(IOException e) {e.printStackTrace();}
	}
	
	void readNutrients(String filename) {
		try {
			// Use BufferedReader to read larger data
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String str = br.readLine();
			while (str != null) {
				str = br.readLine();
				if (str== null) break;
				//use a complex token "," to split data because every entry in the file was double quotes
				//therefore we need to handle first and last entry
				//and we should notice continuous empty entries in the line
				String[] s = str.substring(1, str.length()-1).split("\",\"");
				//put new entry into the nutrient map
				nutrientsMap.put(s[1],new Nutrient(s[1],s[2],s[5]));
				//Product.ProductNutrient pn = new Product.ProductNutrient(s[1],Float.parseFloat(s[4]));
				if(Float.parseFloat(s[4])>0)
					//add nutrient to product's product nutrient map - two s[1]s are key of the map and the member variable of the product nutrient
				productsMap.get(s[0]).addProductNutrients(s[1], s[1], Float.parseFloat(s[4]));
			}
			br.close();
		}
		catch(IOException e) {e.printStackTrace();}
		
	}
	
	void readServingSizes(String filename) {
		try {
			// Use BufferedReader to read larger data
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String str = br.readLine();
			while (str != null) {
				str = br.readLine();
				if (str== null) break;
				//unlike the methods above
				//I use , as the delimiter here because in the full data file
				//there are cases of continuous empty string
				//therefore I would not be able to capture the entries using complex delimiter
				String[] s = str.split(",");
				//the functions below is intended to get rid of the double quotes
				for (int i=0;i<s.length;i++) {
					if (s[i].equals("\"\"")) s[i]="";
					if (s[i].length()>2) s[i]=s[i].substring(1, s[i].length()-1);
				}
				//set products' serving sizes and Uoms - assign 0 when encountering empty entries
				productsMap.get(s[0]).setServingSize(s[1].isEmpty()?0f:Float.parseFloat(s[1]));
				productsMap.get(s[0]).setServingUom(s[2]);
				productsMap.get(s[0]).setHouseholdSize(s[3].isEmpty()?0f:Float.parseFloat(s[3]));
				productsMap.get(s[0]).setHouseholdUom(s[4].isEmpty()?"Not Avaliable":s[4]);
				
			}
			br.close();
		}
		catch(IOException e) {e.printStackTrace();}
	}
	
	boolean readProfiles(String filename) throws InvalidProfileException{
		
		//invoke CSVFiler when the profile file has the extension .csv
		if (filename.substring(filename.length()-3).toLowerCase().equals("csv")) {
			CSVFiler cF=new CSVFiler();
			return cF.readFile(filename);
		}
		
		//invoke CSVFiler when the profile file has the extension .xml
		if (filename.substring(filename.length()-3).toLowerCase().equals("xml")) {
			XMLFiler xF=new XMLFiler();
			return xF.readFile(filename);
		}
		return false;
	}
}
