//Zhexin Chen (zhexinc)
package hw3;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RecommendedNutrient {
	StringProperty nutrientCode;
	FloatProperty nutrientQuantity;
	
	RecommendedNutrient(){
		//invoke default constructor
		this.nutrientCode=new SimpleStringProperty();
		this.nutrientQuantity=new SimpleFloatProperty();
	}
	
	RecommendedNutrient(String nutrientCode, Float nutrientQuantity){
		//invoke non-default constructor
		this.nutrientCode=new SimpleStringProperty(nutrientCode);
		this.nutrientQuantity=new SimpleFloatProperty(nutrientQuantity);
	}
	
	//all getters and setters
    public String getNutrientCode() {
        return this.nutrientCode.get();
    }
 
    public void setNutrientCode(String newNutrientCode) {
        this.nutrientCode = new SimpleStringProperty (newNutrientCode);
    }
    
    public float getNutrientQuantity() {
        return this.nutrientQuantity.get();
    }
    
    public void setNutrientQuantity(float newQuantity) {
    	this.nutrientQuantity = new SimpleFloatProperty (newQuantity);
    }
 
    public void setNutrientName(float newNutrientQuantity) {
        this.nutrientQuantity = new SimpleFloatProperty(newNutrientQuantity);
    }
    
    public void addNutrientQuantity(float addQuantity) {
    	float oldQuantity=this.nutrientQuantity.get();
    	this.nutrientQuantity=new SimpleFloatProperty(oldQuantity+addQuantity);
    }
    
}



