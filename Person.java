//Zhexin Chen (zhexinc)
package hw3;

import java.util.Iterator;

import hw3.NutriProfiler.AgeGroupEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

//import hw2.NutriProfiler.AgeGroupEnum;

public abstract class Person {

	float age, weight, height, physicalActivityLevel; //age in years, weight in kg, height in cm
	String ingredientsToWatch;
	float[][] nutriConstantsTable = new float[NutriProfiler.RECOMMENDED_NUTRI_COUNT][NutriProfiler.AGE_GROUP_COUNT];
	ObservableList<RecommendedNutrient> recommendedNutrientsList = FXCollections.observableArrayList();
	ObservableList<Product> dietProductsList = FXCollections.observableArrayList();
	ObservableMap<String,RecommendedNutrient> dietNutrientsMap=FXCollections.observableHashMap();

	NutriProfiler.AgeGroupEnum ageGroup;

	abstract void initializeNutriConstantsTable();
	abstract float calculateEnergyRequirement();
	
	//remove this default constructor once you have defined the child's constructor
	//Person() {}
	
	
	void populateDietNutrientMap() {

//		if (dietNutrientsMap.containsKey(NutriProfiler.ENERGY_NUTRIENT_CODE))	
//			dietNutrientsMap.get(NutriProfiler.ENERGY_NUTRIENT_CODE).setNutrientQuantity(0f);
//		if (dietNutrientsMap.containsKey(NutriProfiler.NutriEnum.PROTEIN.getNutrientCode()))
//			dietNutrientsMap.get(NutriProfiler.NutriEnum.PROTEIN.getNutrientCode()).setNutrientQuantity(0f);
//		if (dietNutrientsMap.containsKey(NutriProfiler.NutriEnum.CARBOHYDRATE.getNutrientCode()))
//			dietNutrientsMap.get(NutriProfiler.NutriEnum.CARBOHYDRATE.getNutrientCode()).setNutrientQuantity(0f);
//		if (dietNutrientsMap.containsKey(NutriProfiler.NutriEnum.FIBER.getNutrientCode()))
//			dietNutrientsMap.get(NutriProfiler.NutriEnum.FIBER.getNutrientCode()).setNutrientQuantity(0f);
		// every time it was invoked
		// first clear all quantities 
		// note: not deleting them but set them to zero
		if (dietNutrientsMap != null) {
			for (String key: dietNutrientsMap.keySet()) {
				dietNutrientsMap.get(key).setNutrientQuantity(0f);
				}
		}
		// iterate over the dietProductsList
		// add the nutrients one by one
		Iterator<Product> pIter = dietProductsList.iterator();
		while (pIter.hasNext()) {
			Product pTemp=pIter.next();
			for (String nCode: pTemp.productNutrients.keySet()) {
				// if the nutrient already there
				// add the quantity
				if (dietNutrientsMap.containsKey(nCode)) {
					dietNutrientsMap.get(nCode).addNutrientQuantity(pTemp.getServingSize()*pTemp.productNutrients.get(nCode).getNutrientQuantity()/100);
				}
				else {
					// if the nutrient is not there
					// put the new entry to the map
					dietNutrientsMap.put(nCode, new RecommendedNutrient(nCode,pTemp.getServingSize()*pTemp.productNutrients.get(nCode).getNutrientQuantity()/100));
				}
			}
		}
	}

	Person(float age, float weight, float height, float physicalActivityLevel, String ingredientsToWatch) {
		//write your code here
		//non-default constructor
		this.age=age;
		this.weight=weight;
		this.height=height;
		this.physicalActivityLevel=physicalActivityLevel;
		this.ingredientsToWatch=ingredientsToWatch;
		//assign age group by determining the profile age is falling in which interval
		for (AgeGroupEnum aG: AgeGroupEnum.values()) {
			if (aG.getAge()>this.age) {
				this.ageGroup=aG;
				break;
			}		
		}
		NutriProfiler.createNutriProfile(this);
		
	}

	//returns an array of nutrient values of size NutriProfiler.RECOMMENDED_NUTRI_COUNT. 
	//Each value is calculated as follows:
	//For Protein, it multiples the constant with the person's weight.
	//For Carb and Fiber, it simply takes the constant from the 
	//nutriConstantsTable based on NutriEnums' nutriIndex and the person's ageGroup
	//For others, it multiples the constant with the person's weight and divides by 1000.
	//Try not to use any literals or hard-coded values for age group, nutrient name, array-index, etc. 
	
	float[] calculateNutriRequirement() {
		//write your code here
		//instantiate age group index and result array
		int aIndex=ageGroup.getAgeGroupIndex();
		float[] result=new float[NutriProfiler.RECOMMENDED_NUTRI_COUNT];
		
		//apply table 3 formulae to calculate nutrient requirement
		for (int i=0;i<nutriConstantsTable.length;i++ ) {
			switch(i) {
			
			//constant*weight
			case 0: result[i]=nutriConstantsTable[i][aIndex]*this.weight;
				break;
			
			// constants
			case 1: case 2: result[i]=nutriConstantsTable[i][aIndex];
				break;
			
			//constant*weight/1000
			case 3: case 4: case 5: case 6: case 7: case 8: case 9: case 10: case 11: case 12: case 13:
				result[i]=nutriConstantsTable[i][aIndex]*weight/1000;
				break;
			}
		}
		return result;
	}
}
