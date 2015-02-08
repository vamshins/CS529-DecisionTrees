package edu.unm.cs529.ml.dt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class ID3 {
	/**
	 * Main Class
	 * Makes calls to Build Decision tree
	 * Display validation output
	 * 
	 * @param args[0] // trainingfile
	 * @param args[2] // validationfile
	 */

	public static void main(String[] args) {
		Constants.impurity_selection = 1;

		if (args.length == 2) {
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			int chiSquareSelect = 0;
			String trainingTxtFile = null;
			String validationTxtFile = null;
			try {
				trainingTxtFile = args[0];
				validationTxtFile = args[1];
				System.out.println("Please select one option : \n"
									+ "1) Information Gain (Entropy Impurity Function) \n" 
									+ "2) Accuracy (Misclassification Impurity Function) \n");
				Constants.impurity_selection = Integer.parseInt(bufferRead.readLine());

				System.out.println("Please select confidence for chi square statistics : \n"
									+ "1) 0%  \n"
									+ "2) 95% \n"
									+ "3) 99% \n");
				chiSquareSelect = Integer.parseInt(bufferRead.readLine());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			List<Row> trainingExampleList = CollectionsUtil.getListOfExamples(trainingTxtFile);
			if (chiSquareSelect == 1)
				Constants.selectedChiSquareValue = Constants.CHISQ00;
			else if (chiSquareSelect == 2)
				Constants.selectedChiSquareValue = Constants.CHISQ95;
			else if (chiSquareSelect == 3)
				Constants.selectedChiSquareValue = Constants.CHISQ99;
			else
				System.out.println("Invalid Selection");

			DecisionTree root = new DecisionTree(-1, "root");

			// Build Decision Tree
			DecisionTreeUtil.buildTree(trainingExampleList, root);
						
			// Validate Training Set using the Decision Tree created above.
			DecisionTreeUtil.validateDecisionTree(trainingTxtFile, root);

			// Validate Validation Set using the Decision Tree created above.
			DecisionTreeUtil.validateDecisionTree(validationTxtFile, root);
		} else {
			System.out.println("Please input the file names in command line arguments.");
			System.out.println("ID3 <trainingTxt> <validationTxt>");
		}
	}
}
