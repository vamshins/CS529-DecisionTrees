package edu.unm.cs529.ml.dt;

import java.util.ArrayList;
import java.util.List;

/**
 * Object of this Class represents a node of the decision tree
 */
class DecisionTree {
	List<DecisionTree> nextNode;	// Array of Reference to child nodes
	int attId;						// Attributeindex of Attribute used classify
	String value;					// Class at Attribute at Attributeindex
	int output;						// Output Label number, 1 for '+' and 0 for '-'
	public DecisionTree( int attId, String value) {
		super();
		nextNode = new ArrayList<DecisionTree>();
		this.attId = attId;
		this.value = value;
		output = -1;
	}
	public int getAttId() {
		return attId;
	}
	public void setAttId(int attId) {
		this.attId = attId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public DecisionTree getNextNode(int index) {
		return nextNode.get(index);
	}
	public void addNextNode(DecisionTree nextNode) {
		this.nextNode.add(nextNode);
	}
	public int getOutput() {
		return output;
	}
	public void setOutput(int value) {
		this.output = value;
	}
	public String toString() {
		return "DecisionNode [nextNode=" + nextNode + ", attId=" + attId
				+ ", value=" + value + ", output=" + output + "]";
	}	
}
