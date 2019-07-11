//Zhexin Chen (zhexinc)
package hw3;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import hw3.NutriProfiler.PhysicalActivityEnum;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller {

	class RecommendNutrientsButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			//write your code here
			try {
			String nGender=NutriByte.view.genderComboBox.getValue();
			//loading variable values
			//if age, weight and (or) height is missing, assign zero float to them
			float nAge, nHeight, nWeight = 0;
			String nPLevel, nIngredients = null;
			try {
				// throw "missing age information" if the textfield is empty
			if(NutriByte.view.ageTextField.getText().isEmpty()) throw (new InvalidProfileException("Missing age information"));
			// try parseFloat - throw not number exception if unsuccessful
			nAge=Float.parseFloat(NutriByte.view.ageTextField.getText().isEmpty()?"0.0":NutriByte.view.ageTextField.getText());
			// if the number is negative - throw negative exception
			if (nAge<0) throw (new InvalidProfileException("Age must be a positive number"));
			// if age exceed 150 - throw exceed exception
			if (nAge>150) throw (new InvalidProfileException("Age must not exceed 150"));
			}
			catch (NumberFormatException e) {
				throw (new InvalidProfileException("Incorrect age input: age must be a number"));
			}
			
			try {
				// throw "missing weight information" if the textfield is empty
				if(NutriByte.view.weightTextField.getText().isEmpty()) throw (new InvalidProfileException("Missing weight information"));
				// try parseFloat - throw not number exception if unsuccessful
				nWeight=Float.parseFloat(NutriByte.view.weightTextField.getText().isEmpty()?"0.0":NutriByte.view.weightTextField.getText());
				// if the number is negative - throw negative exception
				if (nWeight<0) throw (new InvalidProfileException("Weight must be a positive number"));
				}
				catch (NumberFormatException e) {
					throw (new InvalidProfileException("Incorrect weight input: must be a number"));
				}

			try {
				// throw "missing height information" if the textfield is empty
			if(NutriByte.view.heightTextField.getText().isEmpty()) throw (new InvalidProfileException("Missing height information"));
			// try parseFloat - throw not number exception if unsuccessful
			nHeight=Float.parseFloat(NutriByte.view.heightTextField.getText().isEmpty()?"0.0":NutriByte.view.heightTextField.getText());
			// if the number is negative - throw negative exception
			if (nHeight<0) throw (new InvalidProfileException("Height must be a positive number"));
			}
			catch (NumberFormatException e) {
				throw (new InvalidProfileException("Incorrect height input: must be a number"));
			}
			
			nPLevel=NutriByte.view.physicalActivityComboBox.getValue();
			nIngredients=NutriByte.view.ingredientsToWatchTextArea.getText();
			
			//set default physical activity level as 1
			float nPLevelNum=1f;
			
			//get the physical activity level number
			for (PhysicalActivityEnum pAE: PhysicalActivityEnum.values()) {
				if (pAE.getName().equals(nPLevel)) {
					nPLevelNum=pAE.getPhysicalActivityLevel();
				}
			}
			
			Person tempPerson = null;
			if (nGender==null) {
				throw (new InvalidProfileException("Missing gender information"));
			} //if gender is missing, do not create person and nutriProfile and throw the missing exception
			
			//create person according to gender
			else{if (nGender.equals("Male"))
				tempPerson=new Male(nAge, nWeight, nHeight, nPLevelNum, nIngredients);
			if (nGender.equals("Female"))
				tempPerson=new Female(nAge, nWeight, nHeight, nPLevelNum, nIngredients);
			
			//copy the member variables to tempPerson, reserved for the static person
			tempPerson.dietProductsList=NutriByte.person.dietProductsList;
			tempPerson.dietNutrientsMap=NutriByte.person.dietNutrientsMap;
			//update the static person
			NutriByte.person=tempPerson;
			//update recommended nutrients
			NutriProfiler.createNutriProfile(NutriByte.person);
//			NutriByte.view.recommendedNutrientQuantityColumn.setCellValueFactory(new PropertyValueFactory<RecommendedNutrient,String>("nutrientQuantity"));
			NutriByte.view.recommendedNutrientsTableView.setItems(NutriByte.person.recommendedNutrientsList);}
//			NutriByte.view.recommendedNutrientsTableView.getItems().setAll(elements);
			}
			catch (InvalidProfileException e) {}

		}
		
		// Copy the method without parameter using polymorphism, designed to help exception handling when users enter profile data by hand
		public void handle() {
			//write your code here
			try {
			String nGender=NutriByte.view.genderComboBox.getValue();
			//loading variable values
			//if age, weight and (or) height is missing, assign zero float to them
			float nAge, nHeight, nWeight = 0;
			String nPLevel, nIngredients = null;
			try {
			// throw "missing age information" if the textfield is empty
			if(NutriByte.view.ageTextField.getText().isEmpty()) throw (new InvalidProfileException("Missing age information"));
			// try parseFloat - throw not number exception if unsuccessful
			nAge=Float.parseFloat(NutriByte.view.ageTextField.getText().isEmpty()?"0.0":NutriByte.view.ageTextField.getText());
			// if the number is negative - throw negative exception
			if (nAge<0) {
				NutriByte.view.ageTextField.setStyle("-fx-text-inner-color: red;");
				throw (new InvalidProfileException("Age must be a positive number"));
			}
			// if age exceed 150 - throw exceed exception
			if (nAge>150) {
				NutriByte.view.ageTextField.setStyle("-fx-text-inner-color: red;");
				throw (new InvalidProfileException("Age must not exceed 150"));
			}
			}
			catch (NumberFormatException e) {
				NutriByte.view.ageTextField.setStyle("-fx-text-inner-color: red;");
				throw (new InvalidProfileException("Incorrect age input: age must be a number"));
			}
			
			try {
				// throw "missing weight information" if the textfield is empty
				if(NutriByte.view.weightTextField.getText().isEmpty()) throw (new InvalidProfileException("Missing weight information"));
				// try parseFloat - throw not number exception if unsuccessful
				nWeight=Float.parseFloat(NutriByte.view.weightTextField.getText().isEmpty()?"0.0":NutriByte.view.weightTextField.getText());
				// if the number is negative - throw negative exception
				if (nWeight<0) {
					NutriByte.view.weightTextField.setStyle("-fx-text-inner-color: red;");
					throw (new InvalidProfileException("Weight must be a positive number"));
					}
				}
				catch (NumberFormatException e) {
					NutriByte.view.weightTextField.setStyle("-fx-text-inner-color: red;");
					throw (new InvalidProfileException("Incorrect weight input: must be a number"));
				}
					
			try {
			// throw "missing height information" if the textfield is empty
			if(NutriByte.view.heightTextField.getText().isEmpty()) throw (new InvalidProfileException("Missing height information"));
			// try parseFloat - throw not number exception if unsuccessful
			nHeight=Float.parseFloat(NutriByte.view.heightTextField.getText().isEmpty()?"0.0":NutriByte.view.heightTextField.getText());
			// if the number is negative - throw negative exception
			if (nHeight<0) {
				NutriByte.view.heightTextField.setStyle("-fx-text-inner-color: red;");
				throw (new InvalidProfileException("Height must be a positive number"));
			}
			}
			catch (NumberFormatException e) {
				NutriByte.view.heightTextField.setStyle("-fx-text-inner-color: red;");
				throw (new InvalidProfileException("Incorrect height input: must be a number"));
			}
			
			nPLevel=NutriByte.view.physicalActivityComboBox.getValue();
			nIngredients=NutriByte.view.ingredientsToWatchTextArea.getText();
			
			//set default physical activity level as 1
			float nPLevelNum=1f;
			
			for (PhysicalActivityEnum pAE: PhysicalActivityEnum.values()) {
				if (pAE.getName().equals(nPLevel)) {
					nPLevelNum=pAE.getPhysicalActivityLevel();
				}
			}
			
			Person tempPerson = null;
			if (nGender==null) {
				throw (new InvalidProfileException("Missing gender information"));
			} //if gender is missing, do not create person and nutriProfile and throw missing gender exception
			
			//after all conditions are checked, create person according to gender
			else{if (nGender.equals("Male"))
				tempPerson=new Male(nAge, nWeight, nHeight, nPLevelNum, nIngredients);
			if (nGender.equals("Female"))
				tempPerson=new Female(nAge, nWeight, nHeight, nPLevelNum, nIngredients);
			
			if (NutriByte.person != null) {
			tempPerson.dietProductsList=NutriByte.person.dietProductsList;
			tempPerson.dietNutrientsMap=NutriByte.person.dietNutrientsMap;}
			//pdate the static person
			NutriByte.person=tempPerson;
			//update the recommended nutrients
			NutriProfiler.createNutriProfile(NutriByte.person);
			//update the recommendedNutrientsTableView
			NutriByte.view.recommendedNutrientsTableView.setItems(NutriByte.person.recommendedNutrientsList);}
			}
			catch (InvalidProfileException e) {}

		}
	}

	class OpenMenuItemHandler implements EventHandler<ActionEvent>  {
		@Override
		public void handle(ActionEvent event) throws InvalidProfileException{
			//write your code here

			FileChooser fileChooser = new FileChooser();	//open a new filechooser
			fileChooser.setTitle("Select file");		//set filechooser title
			File file =fileChooser.showOpenDialog(new Stage());
			String filename=null;
			NutriByte.view.root.setCenter(NutriByte.view.nutriTrackerPane);
			// invoke initializePrompts method
			NutriByte.view.initializePrompts();
			try{filename=file.getAbsolutePath();}
			catch (NullPointerException e ) {}
			//get the file full path
			try{try{
				NutriByte.model.readProfiles(filename);
			//get ingredient to watch and pass to GUI
			NutriByte.view.ingredientsToWatchTextArea.setText(NutriByte.person.ingredientsToWatch);
			//set GUI according to male or female instance
			if (NutriByte.person instanceof Female) NutriByte.view.genderComboBox.setValue("Female");
			if (NutriByte.person instanceof Male) NutriByte.view.genderComboBox.setValue("Male");
			
			//set age, weight and height according to the profile record
			NutriByte.view.ageTextField.setText(Float.toString(NutriByte.person.age));
			NutriByte.view.weightTextField.setText(Float.toString(NutriByte.person.weight));
			NutriByte.view.heightTextField.setText(Float.toString(NutriByte.person.height));
			
			//search physical activity name according to its numeric level and pass to the GUI
			for (PhysicalActivityEnum pAE: PhysicalActivityEnum.values()) {
			if (NutriByte.person.physicalActivityLevel==pAE.getPhysicalActivityLevel()) {
				NutriByte.view.physicalActivityComboBox.setValue(pAE.getName());
				break;
				}
			}
			

			
			//create nutrient profile according to the profile record
			NutriProfiler.createNutriProfile(NutriByte.person);
			NutriByte.view.dietProductsTableView.setItems(NutriByte.person.dietProductsList);
			//update nutrichart for new person
			NutriByte.view.nutriChart.updateChart();
			}
		catch (InvalidProfileException | NullPointerException e) {
			throw (new InvalidProfileException("Could not read the profile data"));
		}}
		catch (InvalidProfileException | NullPointerException e) {}
		
		
	}
	}

	class NewMenuItemHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			//write your code here
			// switch to nutrient profile scene
			NutriByte.view.root.setCenter(NutriByte.view.nutriTrackerPane);
			// invoke initializePrompts method
			NutriByte.view.initializePrompts();
			// clear recommendedNutrientsTableView
			if (NutriByte.view.recommendedNutrientsTableView.getItems()!=null)
				NutriByte.view.recommendedNutrientsTableView.getItems().clear();
			//Add in homework 3
			//Clear charts, textareas, tableviews
			NutriByte.view.nutriChart.clearChart();
			NutriByte.view.productIngredientsTextArea.clear();
			NutriByte.view.productNutrientsTableView.getItems().clear();
			NutriByte.view.dietProductsTableView.getItems().clear();
			//Below comes from clear search button handler
			NutriByte.model.searchResultsList.clear();
			NutriByte.view.productsComboBox.setItems(null);
			NutriByte.view.productSearchTextField.clear();
			NutriByte.view.nutrientSearchTextField.clear();
			NutriByte.view.ingredientSearchTextField.clear();
			NutriByte.view.searchResultSizeLabel.setText("");
			NutriByte.view.dietServingUomLabel.setText("");
			NutriByte.view.dietHouseholdUomLabel.setText("");
			NutriByte.view.servingSizeLabel.setText("");
			NutriByte.view.householdSizeLabel.setText("");
			//Below one is duplicated
			//NutriByte.view.productNutrientsTableView.setItems(null);
		}
	}

	class AboutMenuItemHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("About");
			alert.setHeaderText("NutriByte");
			alert.setContentText("Version 3.0 \nRelease 1.0\nCopyleft Java Nerds\nThis software is designed purely for educational purposes.\nNo commercial use intended");
			Image image = new Image(getClass().getClassLoader().getResource(NutriByte.NUTRIBYTE_IMAGE_FILE).toString());
			ImageView imageView = new ImageView();
			imageView.setImage(image);
			imageView.setFitWidth(300);
			imageView.setPreserveRatio(true);
			imageView.setSmooth(true);
			alert.setGraphic(imageView);
			alert.showAndWait();
		}
	}
	
	class SaveMenuItemHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {

			StringBuilder sb = new StringBuilder();
				try {
					if(NutriByte.view.ageTextField.getText().isEmpty()) {
						// throw missing exception if empty
						throw (new InvalidProfileException("Missing age information"));
					}
					sb.append(NutriByte.view.genderComboBox.getValue()+",");
					
					try {
						if(NutriByte.view.ageTextField.getText().isEmpty()) {
							throw (new InvalidProfileException("Missing age information"));
						}
						float nAge=Float.parseFloat(NutriByte.view.ageTextField.getText().isEmpty()?"0.0":NutriByte.view.ageTextField.getText());
						if (nAge<0) {
							throw (new InvalidProfileException("Age must be a positive number"));
						}
						if (nAge>150) {
							throw (new InvalidProfileException("Age must not exceed 150"));
						}
						// check age is a number and between 0 and 150, then add it to string builder
						sb.append(NutriByte.view.ageTextField.getText()+",");
						}
						catch (NumberFormatException e) {
							throw (new InvalidProfileException("Incorrect age input: age must be a number"));
						}
					
					try {
						// similaryly, check weight is a number and nonnegative
						// then add to the string builder
						if(NutriByte.view.weightTextField.getText().isEmpty()) {
							throw (new InvalidProfileException("Missing weight information"));
						}
						float nWeight=Float.parseFloat(NutriByte.view.weightTextField.getText().isEmpty()?"0.0":NutriByte.view.weightTextField.getText());
						if (nWeight<0) {							
							throw (new InvalidProfileException("Weight must be a positive number"));
						}
						sb.append(NutriByte.view.weightTextField.getText()+",");
						}
						catch (NumberFormatException e) {
							throw (new InvalidProfileException("Incorrect weight input: must be a number"));
						}
					
					try {
						// similaryly, check height is a number and nonnegative
						// then add to the string builder
						if(NutriByte.view.heightTextField.getText().isEmpty()) throw (new InvalidProfileException("Missing height information"));
						float nHeight=Float.parseFloat(NutriByte.view.heightTextField.getText().isEmpty()?"0.0":NutriByte.view.heightTextField.getText());
						if (nHeight<0) {
							
							throw (new InvalidProfileException("Height must be a positive number"));
						}
						sb.append(NutriByte.view.heightTextField.getText()+",");
						}
						catch (NumberFormatException e) {
							throw (new InvalidProfileException("Incorrect height input: must be a number"));
						}
					
					// check physical activity combobox, if it's empty
					// throw missing exception
					if (NutriByte.view.physicalActivityComboBox.getValue() == null | NutriByte.view.physicalActivityComboBox.getValue().isEmpty())
						{						
						throw (new InvalidProfileException("Missing physical activity level information"));
						}
					String nPLevel=NutriByte.view.physicalActivityComboBox.getValue();
					float nPLevelNum=0f;
					
					// if not null
					// add to the string builder
					for (PhysicalActivityEnum pAE: PhysicalActivityEnum.values()) {
						if (pAE.getName().equals(nPLevel)) {
							nPLevelNum=pAE.getPhysicalActivityLevel();
						}
					}
					
					// if not default value
					// throw invalid physical activity level exception
					// although not likely appear here
					if (nPLevelNum!=1.0f & nPLevelNum!=1.1f & nPLevelNum!=1.25f & nPLevelNum!=1.48f )
					{	
						throw (new InvalidProfileException("Invalid physical activity level: "+nPLevel+"\nPhysical activity level must 1.0, 1.1, 1.25, or 1.48"));}
					sb.append(nPLevelNum+",");		
					// add ingredients to watch
					sb.append(NutriByte.view.ingredientsToWatchTextArea.getText());
					
					// do not check dietProductsTableView
					// because the dietProducts should be valid if appear
					Iterator<Product> iter=NutriByte.view.dietProductsTableView.getItems().iterator();
					// iterate over the products and add them to the string builder
					while (iter.hasNext()) {
						Product temp=iter.next();
						String s = temp.getProductName();
						if (s.contains(",")) s="\""+s+"\"";
						sb.append("\n"+s+",");
						sb.append(temp.getServingSize()+",");						
						sb.append(temp.getHouseholdSize());
					}
					
					// after the string builder has been set
					// open a new filechooser
					FileChooser fileChooser = new FileChooser();
					// set title and default file name
					fileChooser.setTitle("Save profile file");
					fileChooser.setInitialFileName("my profile.csv");
					// get the file name
					File savedFile = fileChooser.showSaveDialog(new Stage());
					// get the file absolute path
					String filename=savedFile.getAbsolutePath();
					
					// if the extension is *.csv
					// use print writer to write the file
					if (savedFile != null &filename.substring(filename.length()-3).toLowerCase().equals("csv")) {
						try {
						PrintWriter pw = new PrintWriter(savedFile);
						pw.write(sb.toString());
						pw.close();
						}
						catch (IOException e) {
							e.getMessage();
						}
					}
					
					// if the extension is *.xml
					// get the XMLFiler to write the file
					// no dietProduct is handled
					
					if (savedFile != null &filename.substring(filename.length()-3).toLowerCase().equals("xml")) {
						XMLFiler xmlf=new XMLFiler();
						xmlf.writeFile(filename);
					}
				}
				catch ( InvalidProfileException e) {
				}
		}	
	}
	
	// 
	class SearchButtonHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			// clear the searchResultsList every time search for results
			NutriByte.model.searchResultsList.clear();
			
			// instantiate the variables
			String searchProduct=NutriByte.view.productSearchTextField.getText();
			String searchNutri=NutriByte.view.nutrientSearchTextField.getText();
			String searchIngredients=NutriByte.view.ingredientSearchTextField.getText();
			// NutriCode will be looked up according to Nutri search
			String searchNutriCode="";
			
			//Iterate over the nutrientsMap and get the searchNutriCode
			Iterator<Nutrient> nIter=Model.nutrientsMap.values().iterator();
			while (nIter.hasNext()) {
				Nutrient tempN=nIter.next();
				if (tempN.getNutrientName().toLowerCase().contains(searchNutri.toLowerCase())) {
					searchNutriCode=tempN.getNutrientCode();
				}
			}
			
			// iterate over the productsMap and check whether each product matches the conditions
			Iterator<Product> iter=Model.productsMap.values().iterator();
			while (iter.hasNext()) {
				Product temp = iter.next();
				// if nutriCode is "", we will not limit this condition
				// if matches, add to the searchResultsList
				if (temp.getProductName().toLowerCase().contains(searchProduct.toLowerCase()) &
						temp.getIngredients().toLowerCase().contains(searchIngredients.toLowerCase())&
						(temp.productNutrients.containsKey(searchNutriCode) | searchNutri.equals("")))  {
					NutriByte.model.searchResultsList.add(temp);
				}
			}
			
			// after the searchResultsList is set, update the table views and labels
			NutriByte.view.productsComboBox.setItems(NutriByte.model.searchResultsList);
			// set the first value in the combobox
			if (NutriByte.model.searchResultsList.size()>0)
				NutriByte.view.productsComboBox.setValue(NutriByte.model.searchResultsList.get(0));
			NutriByte.view.searchResultSizeLabel.setText(NutriByte.model.searchResultsList.size()+" product(s) found");
		}
		
	}
	
	class ClearButtonHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			// clear all tableviews, comboboxes, labels, textfields
			NutriByte.model.searchResultsList.clear();
			NutriByte.view.productsComboBox.setItems(null);
			NutriByte.view.productSearchTextField.clear();
			NutriByte.view.nutrientSearchTextField.clear();
			NutriByte.view.ingredientSearchTextField.clear();
			NutriByte.view.searchResultSizeLabel.setText("");
			NutriByte.view.dietServingUomLabel.setText("");
			NutriByte.view.dietHouseholdUomLabel.setText("");
			NutriByte.view.servingSizeLabel.setText("0.00");
			NutriByte.view.householdSizeLabel.setText("0.00");
			NutriByte.view.productNutrientsTableView.setItems(null);
		}
		
	}
	
	class AddDietButtonHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			if (NutriByte.person!=null & NutriByte.view.productsComboBox.getValue()!=null) {
			Product addedProduct = NutriByte.view.productsComboBox.getValue();
//			Product tempProduct = NutriByte.model.productsMap.get(addedProduct.getNdbNumber());
			Product tempProduct = new Product();
			// copy the addedProduct to a tempProduct
			tempProduct.setProductName(addedProduct.getProductName());
			tempProduct.setNdbNumber(addedProduct.getNdbNumber());
			tempProduct.setServingUom(addedProduct.getServingUom());
			tempProduct.setHouseholdUom(addedProduct.getHouseholdUom());
			tempProduct.setProductNutrients(addedProduct.getProductNutrients());
			
			// get the standard values
			float pServingSize=Model.productsMap.get(addedProduct.getNdbNumber()).getServingSize();
			float pHouseholdSize=Model.productsMap.get(addedProduct.getNdbNumber()).getHouseholdSize();
			
			// get the custom value strings
			String dietSS=NutriByte.view.dietServingSizeTextField.getText();
			String dietHS=NutriByte.view.dietHouseholdSizeTextField.getText();
			
			
			if (dietSS==null | dietSS.isEmpty()) {
				// 1st scenario: user enters nothing
				// use standard sizes
				if (dietHS==null | dietHS.isEmpty()) {
					NutriByte.person.dietProductsList.add(addedProduct);
					NutriByte.person.populateDietNutrientMap();
					NutriByte.view.dietProductsTableView.setItems(NutriByte.person.dietProductsList);
					NutriByte.view.nutriChart.updateChart();
				}
				
				else {
					// 2nd scenario: user enters household sizes
					// use the household sizes			
					tempProduct.setHouseholdSize(Float.parseFloat(dietHS));
					tempProduct.setServingSize(pServingSize*Float.parseFloat(dietHS)/pHouseholdSize);
					
					// update the map and chart
					NutriByte.person.dietProductsList.add(tempProduct);
					NutriByte.person.populateDietNutrientMap();
					NutriByte.view.dietProductsTableView.setItems(NutriByte.person.dietProductsList);
					NutriByte.view.nutriChart.updateChart();
				}
			}
			
			// 3nd scenario: user enters serving sizes
			// use the serving sizes and overwrite household sizes
			else {
				tempProduct.setServingSize(Float.parseFloat(dietSS));
				tempProduct.setHouseholdSize(pHouseholdSize*Float.parseFloat(dietSS)/pServingSize);
				
				// update the map and chart
				NutriByte.person.dietProductsList.add(tempProduct);
				NutriByte.person.populateDietNutrientMap();
				NutriByte.view.dietProductsTableView.setItems(NutriByte.person.dietProductsList);
				NutriByte.view.nutriChart.updateChart();
			}
		}
		
	}
}
	class RemoveDietButtonHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			if (NutriByte.person!=null & NutriByte.view.productsComboBox.getValue()!=null) {
			Product removedProduct = NutriByte.view.productsComboBox.getValue();
			//Product tempProduct = new Product();
			//tempProduct.setProductName(removedProduct.getProductName());
			Iterator<Product> iter = NutriByte.person.dietProductsList.iterator();
			// iterate over the dietProduct list and remove the selected product
			while (iter.hasNext()) {
				if (iter.next().getProductName().equals(removedProduct.getProductName()))
					iter.remove();
			}
			
			//update the map and chart
			NutriByte.person.populateDietNutrientMap();
			NutriByte.view.dietProductsTableView.setItems(NutriByte.person.dietProductsList);
			NutriByte.view.nutriChart.updateChart();
			}
		}
		
	}
	
	class CloseMenuItemHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			// clear all views
			if (NutriByte.view.recommendedNutrientsTableView.getItems()!=null)
				NutriByte.view.recommendedNutrientsTableView.getItems().clear();
			//Add in homework 3
			NutriByte.view.nutriChart.clearChart();
			NutriByte.view.productIngredientsTextArea.clear();
			NutriByte.view.productNutrientsTableView.setItems(null);
			NutriByte.view.dietProductsTableView.getItems().clear();
			//Below comes from clear search button handler
			NutriByte.model.searchResultsList.clear();
			NutriByte.view.productsComboBox.setItems(null);
			NutriByte.view.productSearchTextField.clear();
			NutriByte.view.nutrientSearchTextField.clear();
			NutriByte.view.ingredientSearchTextField.clear();
			NutriByte.view.searchResultSizeLabel.setText("");
			NutriByte.view.dietServingUomLabel.setText("");
			NutriByte.view.dietHouseholdUomLabel.setText("");
			NutriByte.view.servingSizeLabel.setText("");
			NutriByte.view.householdSizeLabel.setText("");
			// come back to welcome scene
			NutriByte.view.root.setCenter(NutriByte.view.setupWelcomeScene());
		}
		
	}
}
