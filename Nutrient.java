//Zhexin Chen (zhexinc)
package hw3;

import java.util.Objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class Nutrient implements Comparable<Nutrient>{
	StringProperty nutrientCode;
	StringProperty nutrientName;
	StringProperty nutrientUom;
	
	//default constructor
	Nutrient(){
		this.nutrientCode=new SimpleStringProperty();
		this.nutrientName=new SimpleStringProperty();
		this.nutrientUom=new SimpleStringProperty();
	}
	
	//non-default constructor
	Nutrient(String nutrientCode, String nutrientName, String nutrientUom){
		this.nutrientCode=new SimpleStringProperty(nutrientCode);
		this.nutrientName=new SimpleStringProperty(nutrientName);
		this.nutrientUom=new SimpleStringProperty(nutrientUom);		
	}
	
	//getters and setters
    public String getNutrientCode() {
        return this.nutrientCode.get();
    }
 
    public void setNutrientCode(StringProperty newNutrientCode) {
        this.nutrientCode = newNutrientCode;
    }
    
    public String getNutrientName() {
        return this.nutrientName.get();
    }
 
    public void setNutrientName(StringProperty newNutrientName) {
        this.nutrientName = newNutrientName;
    }
    
    public StringProperty getNutrientUom() {
        return this.nutrientUom;
    }
 
    public void setNutrientUom(StringProperty newNutrientUom) {
        this.nutrientUom = newNutrientUom;
    }
    
    //nutrientNameProperty getter
	public ObservableValue<String> nutrientNameProperty() {
		
		return this.nutrientName;
	}
	
	//override hashCode() to utilize comparing according to contents
	@Override
	public int hashCode() {
		return  Objects.hash(nutrientCode, nutrientName, nutrientUom);
	}
	
	//override equals() to utilize comparing according to contents
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (this == o) return true;
		if (getClass() != o.getClass()) return false;
		Nutrient n=(Nutrient) o;
		return this.nutrientCode.get().equals(n.nutrientCode.get())
				&&this.nutrientName.get().equals(n.nutrientName.get())
				&&this.nutrientUom.get().equals(n.nutrientUom.get());
	}
	
	//override compareTo() to utilize comparing according to contents
	@Override
	public int compareTo(Nutrient o) {
		return this.nutrientCode.get().compareTo(o.nutrientCode.get());
	}
	
	//nutrientUomProperty getter
	public ObservableValue<String> nutrientUomProperty() {
		// TODO Auto-generated method stub
		return this.nutrientUom;
	}
	
}
