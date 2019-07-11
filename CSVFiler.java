//Zhexin Chen (zhexinc)
package hw3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVFiler extends DataFiler{

	@Override
	public void writeFile(String filename) {
		// No codes for HW2
		//I have moved this part to controller
	}
	
	Person validatePersonData(String data) throws InvalidProfileException{
		
		try {
		String[] str=data.split(",");
		// throw exception if gender is not correct
		if (!str[0].equals("Male") & !str[0].equals("Female")) throw (new InvalidProfileException("The profile must have gender: Female or Male as first word"));
		String gender = str[0].trim();
		float age, weight, height, pLevel = 0;
		
		// throw exception if age is not a number
		try {age=Float.parseFloat(str[1].trim());}
		catch (NumberFormatException | NullPointerException nfe) {
			throw (new InvalidProfileException("Invalid data for Age: "+str[1]+"\nAge must be a number"));		
		}
		
		// throw exception if age is not between 0 and 150
		if (age<0) throw (new InvalidProfileException("Invalid data for Age: "+str[1]+"\nAge must be a positive number"));
		if (age>150) throw (new InvalidProfileException("Invalid data for Age: "+str[1]+"\nAge must not exceed 150"));
		
		// throw exception if weight if not a number
		try{weight=Float.parseFloat(str[2].trim());}
		catch(NumberFormatException | NullPointerException nfe){
			throw (new InvalidProfileException("Invalid data for Weight: "+str[2]+"\nWeight must be a number"));
		}
		
		// throw exception if weight if a negative number
		if (weight<0) throw (new InvalidProfileException("Invalid data for Weight: "+str[1]+"\nWeight must be a positive number"));
		
		// throw exception if height if not a number
		try{height=Float.parseFloat(str[3].trim());}
		catch (NumberFormatException | NullPointerException nfe) {
			throw (new InvalidProfileException("Invalid data for Height: "+str[3]+"\nHeight must be a number"));
		}
		
		// throw exception if height if a negative number
		if (height<0) throw (new InvalidProfileException("Invalid data for Height: "+str[1]+"\nHeight must be a positive number"));
		
		try{pLevel=Float.parseFloat(str[4].trim());
		if (pLevel!=1.0f & pLevel!=1.1f & pLevel!=1.25f & pLevel!=1.48f )
		{
			// throw exception if physical activity level is not a valid number
			throw (new InvalidProfileException("Invalid physical activity level: "+str[4]+"\nPhysical activity level must 1.0, 1.1, 1.25, or 1.48"));}
		}
		catch (NumberFormatException | NullPointerException nfe){
			// throw exception if physical activity level is not a number
			throw (new InvalidProfileException("Invalid physical activity level: "+str[4]+"\nPhysical activity level must be a number"));
		}
		
		//use a string builder to handle ingredients
		StringBuilder sb=new StringBuilder();
		String ingredients;
		// length==6 indicates there is only one ingredient to watch
		if (str.length==6) 
			ingredients=str[5];
		
		// more than 1 ingredient case
		// append all ingredients with a comma except the last one
		// last one: append without comma
		else {
		for (int i=5;i<str.length-1;i++) {
			sb.append(str[i]+", ");
		}
		sb.append(str[str.length-1]);
		//convert string builder to string
		ingredients=sb.toString();
		}
		//Instantiate person according to the profile gender
		if (gender.equals("Female")) {
			return new Female(age, weight, height, pLevel, ingredients);
		}
		
		if (gender.equals("Male")) {
			return new Male(age, weight, height, pLevel, ingredients);
		}
		else
		return null;
		}
		catch (InvalidProfileException e) {
			return null;
		}
	}
	
	Product validateProductData(String data) {
		try {
		float sServingSize=0f;
		float sHouseholdSize=0f;
		
		String[] str=data.split(",");
		if (str.length!=3) {
			// if missing data, throw exception
			throw new InvalidProfileException("Cannot read: "+data+"\n"+
					"The data must be - String, number, number - for ndb number, serving size, household size");
		}
//		if (!Model.productsMap.containsKey(str[0].trim())) {return null;}
//		if (Model.productsMap.get(str[0]).getServingSize()!=Float.parseFloat(str[1].trim())) {return null;}
//		if (Model.productsMap.get(str[0]).getHouseholdSize()!=Float.parseFloat(str[2].trim())) {return null;}
		// if ndbnumber if not found
		// throw exception
		if (!Model.productsMap.containsKey(str[0].trim())) throw new InvalidProfileException("No product found with this code: "+str[0].trim());
		Product tempProduct = Model.productsMap.get(str[0].trim());
		try{
			sServingSize=Float.parseFloat(str[1].trim());
			sHouseholdSize=Float.parseFloat(str[2].trim());
		}
		// if serving sizes are not valid
		// throw exception
		catch (NumberFormatException e) {throw new InvalidProfileException("Cannot read: "+data+"\n"+
				"The data must be - String, number, number - for ndb number, serving size, household size");}
		
		tempProduct.setServingSize(sServingSize);
		tempProduct.setHouseholdSize(sHouseholdSize);
		
		return tempProduct;
		}
		catch (InvalidProfileException e) {
			return null;
		}
	}

	@Override
	public boolean readFile(String filename) {
		// TODO Auto-generated method stub
		try {
			//invoke buffered reader
			BufferedReader br= new BufferedReader(new FileReader(filename));
			String temp=br.readLine();
			NutriByte.person=validatePersonData(temp);
			
			while (temp!=null) {
				temp=br.readLine();
				if (temp==null) break;
				Product tempProduct=validateProductData(temp);
				NutriByte.person.dietProductsList.add(tempProduct);
			}
			NutriByte.person.populateDietNutrientMap();
			//close the buffered reader
			br.close();
			}
			
		catch(IOException | InvalidProfileException e) {
			e.printStackTrace();
			// if not able to open the file, return false as required
			
			return false;
		}
		return true;
	}
}
