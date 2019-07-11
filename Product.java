//Zhexin Chen (zhexinc)
package hw3;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class Product {
	StringProperty ndbNumber;
	StringProperty productName;
	StringProperty manufacturer;
	StringProperty ingredients;
	FloatProperty servingSize;
	StringProperty servingUom;
	FloatProperty householdSize;
	StringProperty householdUom;
	ObservableMap<String,ProductNutrient> productNutrients= FXCollections.observableHashMap();
	
	//default constructor
	Product(){	
		this.ndbNumber=new SimpleStringProperty();
		this.productName=new SimpleStringProperty();
		this.manufacturer=new SimpleStringProperty();
		this.ingredients=new SimpleStringProperty();
	}
	
	//Non-default constructor
	Product(String ndbNumber, String productName, String manufacturer, String ingredients){
		this.ndbNumber=new SimpleStringProperty(ndbNumber);
		this.productName=new SimpleStringProperty(productName);
		this.manufacturer=new SimpleStringProperty(manufacturer);
		this.ingredients=new SimpleStringProperty(ingredients);
	}
	
	//override toString()
	@Override
	public String toString() {
		return productName.get();
	}
	
	
	//getters and setters
	public String getNdbNumber() {
	     return this.ndbNumber.get();
	    }
	 
	public void setNdbNumber(String newNdbNumber) {
	     this.ndbNumber = new SimpleStringProperty(newNdbNumber);
	    }
	 
	public String getProductName() {
	     return this.productName.get();
	    }
	 
	public void setProductName(String newProductName) {
	     this.productName = new SimpleStringProperty(newProductName);
	    }
	 
	public String getManufacturer() {
	     return this.manufacturer.get();
	    }
	 
	public void setManufacturer(String newManufacturer) {
	     this.manufacturer = new SimpleStringProperty(newManufacturer);
	    }
	 
	public String getIngredients() {
	     return this.ingredients.get();
	    }
	 
	public void setIngredients(String newIngredients) {
	     this.ingredients = new SimpleStringProperty(newIngredients);
	    }
	 
	public Float getServingSize() {
	     return this.servingSize.get();
	    }
	 
	public void setServingSize(float newServingSize) {
	     this.servingSize = new SimpleFloatProperty(newServingSize);
	    }
	 
	public String getServingUom() {
	     return this.servingUom.get();
	    }
	 
	public void setServingUom(String newServingUom) {
	     this.servingUom = new SimpleStringProperty(newServingUom);
	    }
	 
	public Float getHouseholdSize() {
	     return this.householdSize.get();
	    }
	 
	public void setHouseholdSize(Float newHouseholdSize) {
	     this.householdSize = new SimpleFloatProperty(newHouseholdSize);
	    }
	 
	public String getHouseholdUom() {
	     return this.householdUom.get();
	    }
	 
	public void setHouseholdUom(String newHouseholdUom) {
	     this.householdUom = new SimpleStringProperty(newHouseholdUom);
	    }
	 
	public ObservableMap<String, ProductNutrient> getProductNutrients() {
			return this.productNutrients;
		}
	
	public ObservableList<ProductNutrient> getProductNutrientsList() {
		ObservableList<ProductNutrient> temp = FXCollections.observableArrayList();
		for (String s: this.productNutrients.keySet()) {
			temp.add(this.productNutrients.get(s));
		}
		return temp;
	}
		
	public void setProductNutrients(ObservableMap<String, ProductNutrient> newProductNutrients) {
			this.productNutrients=newProductNutrients;		
		}	    
		
	// an extra addProductNutrients() method to help Model class add productNutrients
	// when iterating over the product nutrient file
	public void addProductNutrients(String key, String nutrientCode,float nutrientQuantity) {
			this.productNutrients.put(key, new ProductNutrient(nutrientCode,nutrientQuantity));
		}
	
	//inner class - productNutrient
	public class ProductNutrient{
		StringProperty nutrientCode;
		FloatProperty nutrientQuantity;
		
		//Default constructor
		ProductNutrient(){
			this.nutrientCode=new SimpleStringProperty();
			this.nutrientQuantity=new SimpleFloatProperty();
		}
		
		//Non-default constructor
		ProductNutrient(String nutrientCode,float nutrientQuantity){
			this.nutrientCode=new SimpleStringProperty(nutrientCode);
			this.nutrientQuantity=new SimpleFloatProperty(nutrientQuantity);
		}
		
		//setters and getters
		public String getNutrientCode() {
		     return this.nutrientCode.get();
		    }
		 
		public void setNutrientCode(StringProperty newNutrientCode) {
		     this.nutrientCode = newNutrientCode;
		    }
		 
		public Float getNutrientQuantity() {
		     return this.nutrientQuantity.get();
		    }
		 
		public void setNutrientQuantity(FloatProperty newNutrientQuantity) {
		     this.nutrientQuantity = newNutrientQuantity;
		    }
		
		public ObservableValue<String> nutrientQuantityProperty() {
			// TODO Auto-generated method stub
			String temp1=String.format("%.2f", this.nutrientQuantity.get());
			return new SimpleStringProperty(temp1);
			
		}
	}




}
