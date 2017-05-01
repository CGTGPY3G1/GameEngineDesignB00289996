package b00289996.Components;

public class MathOperation implements Comparable<MathOperation> {
	private char operator; private int value;

	public MathOperation(char operator, int value) {
		this.operator = operator; this.value = value;
	}
	
	public boolean isValid() { return ( operator == '+' || operator == '-' || operator == '*'); }
	public char getOperator() { return operator; }
	public void setOperator(char operator) { this.operator = operator; }
	public int getValue() { return value; }
	public void setValue(int value) { this.value = value; }
	
	public int applyOperation(int value) {
		if (operator == '+')
			value += this.value;
		else if (operator == '-')
			value -= this.value;
		else if (operator == '*') 
			value *= this.value;
		return value;
	}

	/* compare math operations placing multiplication at the end */
	@Override
	public int compareTo(MathOperation other) {
		return operator == other.operator || (operator != '*' && other.operator != '*') ? 0 : operator == '*' ? 1 : other.operator == '*' ? -1 : 0;
	}
}
