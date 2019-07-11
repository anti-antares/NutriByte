//Zhexin Chen (zhexinc)
package hw3;


import hw3.NutriProfiler.PhysicalActivityEnum;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class NutriByte extends Application{
	static Model model = new Model();  	//made static to make accessible in the controller
	static View view = new View();		//made static to make accessible in the controller
	static Person person;				//made static to make accessible in the controller
	
	
	Controller controller = new Controller();	//all event handlers 

	/**Uncomment the following three lines if you want to try out the full-size data files */
//	static final String PRODUCT_FILE = "data/Products.csv";
//	static final String NUTRIENT_FILE = "data/Nutrients.csv";
//	static final String SERVING_SIZE_FILE = "data/ServingSize.csv";
	
	/**The following constants refer to the data files to be used for this application */
	static final String PRODUCT_FILE = "data/Nutri2Products.csv";
	static final String NUTRIENT_FILE = "data/Nutri2Nutrients.csv";
	static final String SERVING_SIZE_FILE = "data/Nutri2ServingSize.csv";
	
	static final String NUTRIBYTE_IMAGE_FILE = "NutriByteLogo.png"; //Refers to the file holding NutriByte logo image 

	static final String NUTRIBYTE_PROFILE_PATH = "profiles";  //folder that has profile data files

	static final int NUTRIBYTE_SCREEN_WIDTH = 1015;
	static final int NUTRIBYTE_SCREEN_HEIGHT = 675;
	
	static Product selectedProduct = null;
	
	// add a dynamic binding to three text fields
	ObjectBinding<Person> personBinding = new ObjectBinding<>() {

		{
			super.bind(view.ageTextField.textProperty(), view.weightTextField.textProperty(), view.heightTextField.textProperty());
		}

		@Override
		protected Person computeValue() {
			float age = 0f, weight = 0f, height = 0f;
			TextField textField = view.ageTextField;

			try {
				textField.setStyle("-fx-text-inner-color: black;");
				// try parseFloat - set the font red if unsuccessful
				age = Float.parseFloat(textField.getText().trim());
				// check age: if it's over 150
				// set the font red
				if (age>150) {
					textField.setStyle("-fx-text-inner-color: red;");					
				}

				textField = view.weightTextField;
				textField.setStyle("-fx-text-inner-color: black;");
				// try parseFloat - set the font red if unsuccessful
				weight = Float.parseFloat(textField.getText().trim());

				textField = view.heightTextField;
				textField.setStyle("-fx-text-inner-color: black;");
				// try parseFloat - set the font red if unsuccessful
				height = Float.parseFloat(textField.getText().trim());
				
				Person tempPerson=null;
				// if gender and physical activity level is not set yet, do not continue
				if (view.genderComboBox.getValue()==null | view.physicalActivityComboBox.getValue()==null) return null;
				
				// else, continue to create the new person using validated data
				// male
				else if (view.genderComboBox.getValue().equals("Male") & age<=150 & age>=0)  {
					float nPLevelNum=1f;
					
					for (PhysicalActivityEnum pAE: PhysicalActivityEnum.values()) {
						if (pAE.getName().equals(view.physicalActivityComboBox.getValue())) {
							nPLevelNum=pAE.getPhysicalActivityLevel();
						}
					}
					tempPerson=new Male(age,weight,height,nPLevelNum,view.ingredientSearchTextField.getText());}
				
				// female
				else if (view.genderComboBox.getValue().equals("Female") & age<=150 & age>=0)  {
					float nPLevelNum=1f;
					
					for (PhysicalActivityEnum pAE: PhysicalActivityEnum.values()) {
						if (pAE.getName().equals(view.physicalActivityComboBox.getValue())) {
							nPLevelNum=pAE.getPhysicalActivityLevel();
						}
					}
					tempPerson=new Female(age,weight,height,nPLevelNum,view.ingredientSearchTextField.getText());}
				

				return tempPerson;
			} catch (NumberFormatException e) {
				// catch parseFloat exception and set the textField font red
				textField.setStyle("-fx-text-inner-color: red;");
				return null;
			} 
		}
	};

	@Override
	public void start(Stage stage) throws Exception {
		model.readProducts(PRODUCT_FILE);
		model.readNutrients(NUTRIENT_FILE);
		model.readServingSizes(SERVING_SIZE_FILE);
		view.setupMenus();
		view.setupNutriTrackerGrid();
		view.root.setCenter(view.setupWelcomeScene());
		Background b = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
		view.root.setBackground(b);
		Scene scene = new Scene (view.root, NUTRIBYTE_SCREEN_WIDTH, NUTRIBYTE_SCREEN_HEIGHT);
		view.root.requestFocus();  //this keeps focus on entire window and allows the textfield-prompt to be visible
		setupBindings();
		stage.setTitle("NutriByte 3.0");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	void setupBindings() {
		view.newNutriProfileMenuItem.setOnAction(controller.new NewMenuItemHandler());
		view.openNutriProfileMenuItem.setOnAction(controller.new OpenMenuItemHandler());
		view.saveNutriProfileMenuItem.setOnAction(controller.new SaveMenuItemHandler()); //add save button binding
		view.closeNutriProfileMenuItem.setOnAction(controller.new CloseMenuItemHandler());
		view.exitNutriProfileMenuItem.setOnAction(event -> Platform.exit());
		view.aboutMenuItem.setOnAction(controller.new AboutMenuItemHandler());
		
		// set up recommendNutrient cell values
		view.recommendedNutrientNameColumn.setCellValueFactory(recommendedNutrientNameCallback);
		view.recommendedNutrientQuantityColumn.setCellValueFactory(recommendedNutrientQuantityCallback);
		view.recommendedNutrientUomColumn.setCellValueFactory(recommendedNutrientUomCallback);
		
		// set button actions
		view.createProfileButton.setOnAction(controller.new RecommendNutrientsButtonHandler());
		view.searchButton.setOnAction(controller.new SearchButtonHandler());
		view.clearButton.setOnAction(controller.new ClearButtonHandler());
		view.addDietButton.setOnAction(controller.new AddDietButtonHandler());
		view.removeDietButton.setOnAction(controller.new RemoveDietButtonHandler());
		//controller.new RecommendNutrientsButtonHandler().handle();
		
		// add listener, if new person is not null
		// recommend nutrients for the new person
		// and update the table view
		personBinding.addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				controller.new RecommendNutrientsButtonHandler().handle();
			} else  view.recommendedNutrientsTableView.setItems(null);
		});
		
		// add gender combobox listener
		view.genderComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			
		// check if all data are valid
		// if so, recommend nutrients for the new person
		if (newValue != null & view.physicalActivityComboBox.getValue() != null & check3Dimension()) {
			controller.new RecommendNutrientsButtonHandler().handle();
			if (newValue.equals("Male")) {
			controller.new RecommendNutrientsButtonHandler().handle();
			}
			if (newValue.equals("Female")) {
				controller.new RecommendNutrientsButtonHandler().handle();
				}
	} //else  view.recommendedNutrientsTableView.setItems(null);
});
		// add physical activity level combobox listener
		view.physicalActivityComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			
			// check if all data are valid
			// if so, recommend nutrients for the new person
			if (newValue != null & view.genderComboBox.getValue() != null & check3Dimension()) {
				controller.new RecommendNutrientsButtonHandler().handle();
				if (view.genderComboBox.getValue().equals("Male")) {
				controller.new RecommendNutrientsButtonHandler().handle();
				}
				if (view.genderComboBox.getValue().equals("Female")) {
					controller.new RecommendNutrientsButtonHandler().handle();
					}
		} else  view.recommendedNutrientsTableView.setItems(null);
	});
		
		// add products combobox listener
		// update all serving sizes / uoms labels according to the new product
		// update ingredients
		// update table views
		view.productsComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			selectedProduct=null;
			if (newValue != null) {
				selectedProduct = newValue;
				view.productIngredientsTextArea.setText(selectedProduct.getIngredients());
				view.productNutrientsTableView.setItems(selectedProduct.getProductNutrientsList());
				view.servingSizeLabel.setText(Float.toString(selectedProduct.getServingSize())+" "+selectedProduct.getServingUom());
				view.householdSizeLabel.setText(Float.toString(selectedProduct.getHouseholdSize())+" "+selectedProduct.getHouseholdUom());
				view.dietServingUomLabel.setText(selectedProduct.getServingUom());
				view.dietHouseholdUomLabel.setText(selectedProduct.getHouseholdUom());
		} else  view.productIngredientsTextArea.setText("");
	});
		
		// set up productsTableView cell values using call back
		view.productNutrientNameColumn.setCellValueFactory(productNutrientNameCallback);
		view.productNutrientQuantityColumn.setCellValueFactory(productNutrientQuantityCallback);
		view.productNutrientUomColumn.setCellValueFactory(productNutrientUomCallback);
	}
	
	Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>> recommendedNutrientNameCallback = new Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<RecommendedNutrient, String> arg0) {
			Nutrient nutrient = Model.nutrientsMap.get(arg0.getValue().getNutrientCode()); //search nutrientName from nutrientsMap
			return nutrient.nutrientNameProperty();
		}
	};
	
	Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>> recommendedNutrientQuantityCallback = new Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<RecommendedNutrient, String> arg0) {
			//write your code here
			float quantity=arg0.getValue().getNutrientQuantity();
			String sQuantity=String.format("%.2f",quantity);		//Control format to two decimal places
			return new SimpleStringProperty(sQuantity);				//Return String Property
		}
	};
	
	Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>> recommendedNutrientUomCallback = new Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<RecommendedNutrient, String> arg0) {
			Nutrient nutrient = Model.nutrientsMap.get(arg0.getValue().getNutrientCode()); //search nutrientUom from nutrientsMap
			return nutrient.nutrientUomProperty();
		}
	};
	
	// Three new callbacks for products table view
	// search nutrient name according to nutrient code
	Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>> productNutrientNameCallback = new Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<Product.ProductNutrient, String> arg0) {
			Nutrient nutrient = Model.nutrientsMap.get(arg0.getValue().getNutrientCode()); //search nutrientName from nutrientsMap
			return nutrient.nutrientNameProperty();
		}
	};
	
	// search nutrient quantity according to nutrient code
	Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>> productNutrientQuantityCallback = new Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<Product.ProductNutrient, String> arg0) {
//			Product product = Model.productsMap.get(arg0.getValue().getNutrientCode()); //search nutrientUom from nutrientsMap
			return arg0.getValue().nutrientQuantityProperty();
		}
	};
	
	// search nutrient uomaccording to nutrient code
	Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>> productNutrientUomCallback = new Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<Product.ProductNutrient, String> arg0) {
			Nutrient nutrient = Model.nutrientsMap.get(arg0.getValue().getNutrientCode()); //search nutrientUom from nutrientsMap
			return nutrient.nutrientUomProperty();
		}
	};
	
	// a helper method to check whether age, weight and height data are valid
	// return false if not number
	// or out of valid boundary
	boolean check3Dimension () {
		String age=view.ageTextField.getText();
		String weight=view.weightTextField.getText();
		String height=view.heightTextField.getText();
		if (age == null | weight == null | height == null | age.isEmpty() | weight.isEmpty() | height.isEmpty()) return false;
		try {
			
			float agef = Float.parseFloat(age);
			float weightf = Float.parseFloat(weight);
			float heightf = Float.parseFloat(height);
			if (agef>150 | agef<0) return false;
			if (heightf<0) return false;
			if (weightf<0) return false;
			return true;
			
		}
		catch(NumberFormatException e) {return false;}
	}
}
