package edu.unm.cs529.ml.dt;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DecisionTreeUtil {
	
	/**
	 * Calculate classified and misclassified results in the form of percentages
	 */
	static void validateDecisionTree(String fileName, DecisionTree root) {
		System.out.println("Validation/Classification of " + fileName + " started.");
		List<Row> validationRecords = CollectionsUtil.getListOfExamples(fileName);
		int classified = 0, misclassified = 0;
		for (Row record : validationRecords) {
			if (parseRecordInTree(record, root)) {
				classified++;
			} else {
				misclassified++;
			}
		}
		
		System.out.println("Results: ");
		
		System.out.println("Classified:" + classified);
		System.out.println("Misclassified:" + misclassified);
		double total = classified + misclassified;
		System.out.println("Accuracy of classified : " + (classified / total * 100) + "%");
	}

	/**
	 * Traverse through the decision tree and check if the row is classified.
	 */
	private static boolean parseRecordInTree(Row row, DecisionTree root) {
		DecisionTree parent = root;
		while (true) {
			boolean flag = true;
			for (DecisionTree decisionNode : parent.nextNode) {
				 // if child's Class matches records attribute class
				if (row.getValue(decisionNode.getAttId()).equals(decisionNode.getValue())) {
					flag = false;
					if (decisionNode.nextNode.size() == 0) {
						if (decisionNode.getOutput() == row.getSet()) {
							return true;
						} else {
							return false;
						}
					} else {
						parent = decisionNode;
					}
					break;
				}
			}
			if (flag) {
				return false;
			}
		}
	}

	/**
	 * This method builds a decision tree for the given Moledular Biology dataset 
	 * using recursion.
	 */
	static void buildTree(List<Row> trainingExampleList, DecisionTree parent) {
		double entropy;
		if (Constants.impurity_selection == Constants.ENTROPY_IMPURITY) {
			// Find Entropy of corresponding datasets
			entropy = Entropy.calculateEntropy(trainingExampleList);
		} else {
			// Find misclassification entropy rate of corresponding dataset
			entropy = Entropy.misclassification(trainingExampleList);
		}
		if (entropy == 0) {
			parent.setOutput(trainingExampleList.get(0).getSet());
			return;
		} else {
			int attId = CollectionsUtil.selectAttribute(trainingExampleList);
			if (CollectionsUtil.getStats(trainingExampleList, attId) > Constants.selectedChiSquareValue) {
				Map<String, List<Row>> map = CollectionsUtil.removeAttributeFromDataset(trainingExampleList, attId);
				Iterator<Entry<String, List<Row>>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String, List<Row>> pairs = (Map.Entry<String, List<Row>>) it.next();
					List<Row> recordClass = (List<Row>) pairs.getValue();
					DecisionTree child = new DecisionTree(attId, pairs.getKey().toString());
					parent.addNextNode(child);
					buildTree(recordClass, child);
				}
			} else {
				parent.setOutput(CollectionsUtil.majorityClass(trainingExampleList));
				return;
			}
		}
	}
}
