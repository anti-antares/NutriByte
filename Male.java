//Zhexin Chen (zhexinc)
package hw3;

public class Male extends Person{
	float[][] nutriConstantsTableMale = new float[][]{
		//AgeGroups: 3M, 6M, 1Y, 3Y, 8Y, 13Y, 18Y, 30Y, 50Y, ABOVE 
		{1.52f, 1.52f, 1.2f, 1.05f, 0.95f, 0.95f, 0.73f, 0.8f, 0.8f, 0.8f}, //Protein
		{60, 60, 95, 130, 130, 130, 130, 130, 130, 130}, //Carbohydrate
		{19, 19, 19, 19, 25, 31, 38, 38, 38, 30},       //Fiber 
		{36, 36, 32, 21, 16, 17, 15, 14, 14, 14	},  //Histidine
		{88, 88, 43, 28, 22, 22, 21, 19, 19, 19	}, 	//isoleucine
		{156, 156, 93, 63, 49, 49, 47, 42, 42, 42},//leucine
		{107, 107, 89, 58, 46, 46, 43, 38, 38, 38 },//lysine
		{59, 59, 43, 28, 22, 22, 21, 19, 19, 19	}, 	//methionine 
		{59, 59, 43, 28, 22, 22, 21, 19, 19, 19	}, 	//cysteine
		{135, 135, 84, 54, 41, 41, 38, 33, 33, 33 },//phenylalanine 
		{135, 135, 84, 54, 41, 41, 38, 33, 33, 33 },//tyrosine
		{73, 73, 49, 32, 24, 24, 22, 20, 20, 20}, 	//threonine
		{28, 28, 13, 8, 6, 6, 6, 5, 5, 5}, 			//tryptophan
		{87, 87, 58, 37, 28, 28, 27, 24, 24, 24}  	//valine
	};

	Male(float age, float weight, float height, float physicalActivityLevel, String ingredientsToWatch) {
		//write your code here
		super(age, weight, height, physicalActivityLevel, ingredientsToWatch);
		//copy the constant table
		initializeNutriConstantsTable();
	}
	@Override
	float calculateEnergyRequirement() {
		//write your code here
		int i=ageGroup.getAgeGroupIndex();
		
		//calculate energy requirement according to age group index and table 2 formulae
		switch(i) {
		
		//1st formulae - children
		case 0: return 89*weight-(-75);
		
		case 1: return 89*weight-44;
		
		case 2: return 89*weight-78;
		
		case 3: return 89*weight-80;
		
		//2nd formulae - adult
		case 4: return (float) (88.5-(61.9*age)+physicalActivityLevel*(26.7*weight+903*height/100)+20);
		
		case 5: return (float) (88.5-(61.9*age)+physicalActivityLevel*(26.7*weight+903*height/100)+20);
		
		case 6: return (float) (88.5-(61.9*age)+physicalActivityLevel*(26.7*weight+903*height/100)+20);
		
		//3rd formulae - elder		
		case 7: return (float) (662-(9.53*age)+physicalActivityLevel*(15.91*weight+539.6*height/100));
			
		case 8:	return (float) (662-(9.53*age)+physicalActivityLevel*(15.91*weight+539.6*height/100));
		
		case 9: return (float) (662-(9.53*age)+physicalActivityLevel*(15.91*weight+539.6*height/100));
			
		}
		return 0;
	}

	@Override
	void initializeNutriConstantsTable() {
		//write your code here
		//copy the constant table
		this.nutriConstantsTable=this.nutriConstantsTableMale;
	}
}
