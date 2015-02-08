package edu.unm.cs529.ml.dt;

import java.util.ArrayList;
import java.util.List;

/**
 * Object of this Class represents a node of the decision tree
 */
class DecisionTree {
	List<DecisionTree> nextNode;
	int attrId;
	String value;
	int output;
	public DecisionTree( int attId, String value) {
		super();
		nextNode = new ArrayList<DecisionTree>();
		this.attrId = attId;
		this.value = value;
		output = -1;
	}
	public int getAttId() {
		return attrId;
	}
	public void setAttId(int attId) {
		this.attrId = attId;
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
		return "DecisionNode [nextNode=" + nextNode + ", attId=" + attrId
				+ ", value=" + value + ", output=" + output + "]";
	}	
}
