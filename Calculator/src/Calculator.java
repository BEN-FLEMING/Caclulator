package com.bham.pij.assignments.calculator;

// Ben Fleming 2245367

import java.util.ArrayList;


public class Calculator {

	String expression;
	float result;
	float memval;
	int evaluateCount;
	ArrayList<Float> history = new ArrayList<Float>();
	int passThrough;

	public Calculator() {
		
		this.expression = "";
		this.result = 0;
		this.memval = 0;

	}
	
	public float evaluate(String expression) {		//evaluates the expression passed to this method 
		
		evaluateCount++;
		char space = ' ';
		char operator = ' ';
		boolean isExpressionValid = false;
		boolean characterValidity = false;
		boolean operatorFound = false;
		boolean isOperatorValid;
		int invalidCharCount = 0;
		int presentDigits = 0;
		int count = 0;
		int spaceCount = 0;
		int spacesLocated = 0;
		int spaceLoc1 = 0;
		int spaceLoc2 = 0;
		int operatorCount = 0;
		int operand1EndIndex = 0;
		int operand2StartIndex = 0;
		int spacesFound = 0;
		float operandOne = 0;
		float operandTwo = 0;
		float defaultResponse = Float.MIN_VALUE;
		float result = 0;
		
		
		StringBuilder operandOneStrB = new StringBuilder();
		StringBuilder operandTwoStrB = new StringBuilder();	
		
		int operatorCounter = 0;
		
		for (int oooo = 0; oooo < expression.length(); oooo++)	//counts number of operators		
		{
			if (expression.charAt(oooo) == '+' || expression.charAt(oooo) == '-' || expression.charAt(oooo) == '*' || expression.charAt(oooo) == '/' )
				{
				operatorCounter++;
				}
			if ((expression.charAt(oooo) == '+' || expression.charAt(oooo) == '-' || expression.charAt(oooo) == '*' || expression.charAt(oooo) == '/') && (expression.charAt(oooo+1) == '+' || expression.charAt(oooo+1) == '-' || expression.charAt(oooo+1) == '*' || expression.charAt(oooo+1) == '/') )
				{
				return defaultResponse;
				}
			if (Character.isDigit(expression.charAt(oooo)))
				{
				presentDigits++;
				}
		}
		
		if (presentDigits == 0)
		{
			return defaultResponse;
		}
		
		if (operatorCounter == 0)
		{
			return defaultResponse;
		}
		
		
		for (int hhhh = 1; hhhh < expression.length(); hhhh++)					//takes into account negative signs and prevents this from being added to operator count
		{
			if (Character.isDigit(expression.charAt(hhhh)) && expression.charAt(hhhh-1) == '-')
				{
					operatorCounter--;
				}
		}
			
		
		int bracketCounter = 0; 		
		
		for (int bbbb = 0; bbbb < expression.length(); bbbb++)	//counts number of brackets		
		{
			if (expression.charAt(bbbb) == '(' || expression.charAt(bbbb) == ')')
				{
				bracketCounter++;
				}
		}
		
		
		if (operatorCounter > 1 && bracketCounter == 0) 
		{
			float arbitraryLengthResult;
			
			ArrayList<String> tokenStack = new ArrayList<String>();
	
			String[] tokens  = expression.split(" ");
			
			int ttt = 0;
			int ccc = 0;
			int vvv = 0;
			int multCount = 0;
			int divCount = 0;
			int plusCount = 0;
			int subCount = 0;
			
		
			for (String rrr : tokens)
			{	
				tokenStack.add(tokens[ccc]);
				ccc++;
			}

			int multCounter = 0;
			int divCounter = 0;
			int	plusCounter = 0;
			int	subCounter = 0;
			
			passThrough = 1;
			
			while (tokenStack.size() > 1)
			{	
				for (multCounter = 0; multCounter < tokenStack.size(); multCounter++)
				{
					String comparisonStringMult = tokenStack.get(multCounter);
					
					if (comparisonStringMult.equals("*"))
						{
							tokenStack.set(multCounter-1, String.valueOf(evaluate(tokenStack.get(multCounter-1) + " " + tokenStack.get(multCounter) + " " + tokenStack.get(multCounter+1))));
							tokenStack.remove(multCounter+1);
							tokenStack.remove(multCounter);
						}
				}
				
				for (divCounter = 0; divCounter < tokenStack.size(); divCounter++)
				{
					String comparisonStringDiv = tokenStack.get(divCounter);
					
					if (comparisonStringDiv.equals("/"))
						{
							if (String.valueOf(tokenStack.get(divCounter+1)) == "0")
							{
								return defaultResponse;
							}
							tokenStack.set(divCounter-1, String.valueOf(evaluate(tokenStack.get(divCounter-1) + " " + tokenStack.get(divCounter) + " " + tokenStack.get(divCounter+1))));
							tokenStack.remove(divCounter+1);
							tokenStack.remove(divCounter);
						}
				}
				
				for (plusCounter = 0; plusCounter < tokenStack.size(); plusCounter++)
				{
					String comparisonString = tokenStack.get(plusCounter);
					
					if (comparisonString.equals("+"))
						{
							tokenStack.set(plusCounter-1, String.valueOf(evaluate(tokenStack.get(plusCounter-1) + " " + tokenStack.get(plusCounter) + " " + tokenStack.get(plusCounter+1))));
							tokenStack.remove(plusCounter+1);
							tokenStack.remove(plusCounter);
						}
				}
				
				for (multCounter = 0; subCounter < tokenStack.size(); subCounter++)
				{
					String comparisonString = tokenStack.get(subCounter);
					
					if (comparisonString.equals("-"))
						{
							tokenStack.set(subCounter-1, String.valueOf(evaluate(tokenStack.get(subCounter-1) + " " + tokenStack.get(subCounter) + " " + tokenStack.get(subCounter+1))));
							tokenStack.remove(subCounter+1);
							tokenStack.remove(subCounter);
						}
				}
				
			}
		
			arbitraryLengthResult = Float.parseFloat(tokenStack.get(0));
			history.add(arbitraryLengthResult);
			return arbitraryLengthResult;
		}
			
			
		
		if (bracketCounter >= 2)										//for expressions involving brackets (maybe change to if bracket count greater than 2)
		{
			ArrayList<Integer> spaceLocations = new ArrayList<Integer>();
			ArrayList<Integer> bracketLocations = new ArrayList<Integer>();
			
			StringBuilder bracketOperand1sb = new StringBuilder();
			StringBuilder bracketOperand2sb = new StringBuilder();
			StringBuilder bracketOperand3sb = new StringBuilder();
			StringBuilder bracketOperand4sb = new StringBuilder();
			
			float bracketOperand1;
			float bracketOperand2;
			float bracketOperand3;
			float bracketOperand4;
			
			char bracketOperator1;
			char bracketOperator2;
			char betweenBracketOperator;
			
			float bracketResult1 = 0;
			float bracketResult2 = 0;
			float overallBracketResult = 0;
			
			for (int r = 0; r < expression.length(); r++)
			{
				if (expression.charAt(r) == ' ')
				{
					spaceLocations.add(r); 
				}
				if (expression.charAt(r) == '(' || expression.charAt(r) == ')')
				{
					bracketLocations.add(r);
				}
			}
			
			for(int aa = (bracketLocations.get(0)+1); aa <= spaceLocations.get(0); aa++)       //find and store operand one
				{
					bracketOperand1sb.append(expression.charAt(aa));
				}
			for(int bb = (spaceLocations.get(1)+1); bb <= (bracketLocations.get(1)-1); bb++)       //find and store operand two
				{
					bracketOperand2sb.append(expression.charAt(bb));
				}
			for(int cc = (bracketLocations.get(2)+1); cc <= spaceLocations.get(4); cc++)       //find and store operand three
				{
					bracketOperand3sb.append(expression.charAt(cc));
				}
			for(int dd = (spaceLocations.get(5)+1); dd <= (bracketLocations.get(3)-1); dd++)       //find and store operand four
				{
					bracketOperand4sb.append(expression.charAt(dd));
				}
			
			String bracketOperand1Str = bracketOperand1sb.toString();
			String bracketOperand2Str = bracketOperand2sb.toString();
			String bracketOperand3Str = bracketOperand3sb.toString();
			String bracketOperand4Str = bracketOperand4sb.toString();
			
			bracketOperand1 = Float.parseFloat(bracketOperand1Str);
			bracketOperand2 = Float.parseFloat(bracketOperand2Str);
			bracketOperand3 = Float.parseFloat(bracketOperand3Str);
			bracketOperand4 = Float.parseFloat(bracketOperand4Str);
			
			bracketOperator1 = expression.charAt(spaceLocations.get(0)+1);
			bracketOperator2 = expression.charAt(spaceLocations.get(4)+1);
			betweenBracketOperator = expression.charAt(spaceLocations.get(2)+1);
			
			
			switch (bracketOperator1)				//to carry out operation for first bracket and set result
			{
				case('+'):
					bracketResult1 = (bracketOperand1 + bracketOperand2);
					break;
				case('/'):
					if (bracketOperand2 == 0)
					{
						return defaultResponse;
					}
					bracketResult1 = (bracketOperand1 / bracketOperand2);
					break;
				case('*'):
					bracketResult1 = (bracketOperand1 * bracketOperand2);
					break;
				case('-'):
					bracketResult1 = (bracketOperand1 - bracketOperand2);
					break;
			}
			
			switch (bracketOperator2)				//to carry out operation for second bracket and set result
			{
				case('+'):
					bracketResult2 = (bracketOperand3 + bracketOperand4);
					break;
				case('/'):
					if (bracketOperand4 == 0)
					{
						return defaultResponse;
					}
					bracketResult2 = (bracketOperand3 / bracketOperand4);
					break;
				case('*'):
					bracketResult2 = (bracketOperand3 * bracketOperand4);
					break;
				case('-'):
					bracketResult2 = (bracketOperand3 - bracketOperand4);
					break;
			}
			
			switch (betweenBracketOperator)				//to carry out operation for whole expression
			{
				case('+'):
					overallBracketResult = (bracketResult1 + bracketResult2);
					history.add(overallBracketResult);
					return overallBracketResult;
				case('/'):
					if (bracketResult2 == 0)
					{
						return defaultResponse;
					}
					overallBracketResult = (bracketResult1 / bracketResult2);
					history.add(overallBracketResult);
					return overallBracketResult;
				case('*'):
					overallBracketResult = (bracketResult1 * bracketResult2);
					history.add(overallBracketResult);
					return overallBracketResult;
				case('-'):
					overallBracketResult = (bracketResult1 - bracketResult2);
					history.add(overallBracketResult);
					return overallBracketResult;
			}
			
		}
		
		
		if (evaluateCount > 1 && (expression.charAt(0) == '+' || expression.charAt(0) == '-' || expression.charAt(0) == '*' || expression.charAt(0) == '/' ) && operatorCounter == 1 && expression.charAt(1) == ' ')
		{
			char newOperator = expression.charAt(0); 								//for special cases using memory
			StringBuilder specialOperandBuilder = new StringBuilder();
			float specialOperand;
			if (expression.charAt(1) == ' ') 
			{
				
				for (int w = 1; w < expression.length(); w++)			//creates operand builder for special case from memory
				{
					specialOperandBuilder.append(expression.charAt(w));
				}
				String specialOperandStr = specialOperandBuilder.toString();
				specialOperand = Float.parseFloat(specialOperandStr);
				switch (newOperator)				//to carry out operation and return result
				{
					case('+'):
						result = memval + specialOperand;
						history.add(result);
						return result;
					case('/'):
						result = memval / specialOperand;
						history.add(result);
						return result;
					case('*'):
						result = memval * specialOperand;
						history.add(result);
						return result;
					case('-'):
						result = memval - specialOperand;
						history.add(result);
						return result;
				}
				
			}
			else 
				return defaultResponse;
		}
		
		
		for (int y = 0; y < expression.length(); y++)			//check expression has two spaces
		{
			if (expression.charAt(y) == space)
				spaceCount++;
		}
		
		if (spaceCount != 2)
		{
			return defaultResponse;
		}
		
		boolean firstSpaceFound = false;
		
		for (int l = 0; l < expression.length(); l++)			//check spaces are 2 apart
		{
			if (spacesLocated == 0)
				if (expression.charAt(l) == space)
				{
					spaceLoc1 = l;
					spacesLocated++;
				}
			if (spacesLocated >= 1)
				if (expression.charAt(l) == space)
				{
					spaceLoc2 = l;
				}
		}	
		
		if ((spaceLoc2 - spaceLoc1) != 2)
		{
			return defaultResponse;
		}
		
		for (int p = 0; p < expression.length(); p++)			//check expression has only one operator
		{
			if (expression.charAt(p) == '+' || expression.charAt(p) == '-' || expression.charAt(p) == '*' || expression.charAt(p) == '/' )
				operatorCount++;
		}
		
		if (expression.charAt(0) == '-')
			{
				operatorCount--;
			}
		
		if (operatorCount == 0)
		{
			return defaultResponse;
		}
		
		for (int b = 0; b < expression.length(); b++)			//checks string is comprised of valid characters
			if (expression.charAt(count) == ' ' || expression.charAt(count) == '+' || expression.charAt(count) == '-' || expression.charAt(count) == '*' || expression.charAt(count) == '/' || Character.isDigit(expression.charAt(count)) || expression.charAt(count) == '.')
			{
				count++;
			}
				else
				{
					invalidCharCount++;
					count++;
				}	
		

		if (invalidCharCount != 0)
			{
				return defaultResponse;
			}
		
		
		if (!Character.isDigit(expression.charAt(expression.length()-1)))			//make sure the ending character is a digit
		{
			return defaultResponse;
		}
		
		while (operatorFound == false)   //to find the operator location
		{
			for (int x = 0; x < expression.length(); x++)
				{
					
					if (spacesFound == 0)
					{
						if (expression.charAt(x) == space)
						{
							operator = expression.charAt(x+1);
							operand1EndIndex = x-1;
							operand2StartIndex = x+2;
							spacesFound++;
							operatorFound = true; 	
						}
					}
					
				}
		}
		
		if (spacesFound == 0)
		{
			return defaultResponse;
		}
		
		for(int j = 0; j <= operand1EndIndex; j++)       //find and store operand one
		{
			operandOneStrB.append(expression.charAt(j));
		}
		
		String operandOneStr = operandOneStrB.toString();
		
		
		for(int c = operand2StartIndex + 1; c < expression.length(); c++)       //find and store operand two
		{
			operandTwoStrB.append(expression.charAt(c));
		}
		
		String operandTwoStr = operandTwoStrB.toString();
		
		
		operandOne = Float.parseFloat(operandOneStr);					//conversion of operands to float values
		operandTwo = Float.parseFloat(operandTwoStr);					
		
		
		switch (operator)				//to ensure operator is valid
		{
			case('+'):
				isOperatorValid = true;
			case('/'):
				isOperatorValid = true;
			case('*'):
				isOperatorValid = true;
			case('-'):
				isOperatorValid = true;
		}
		
		if (isOperatorValid = false)				//default return if operator not valid
		{
			return defaultResponse;
		}
		
		if (operator == '/' && operandTwo == 0)
		{
			return defaultResponse;
		}
		
		switch (operator)				//to carry out operation and return result
		{
			case('+'):
				result = operandOne + operandTwo;
				history.add(result);
				return result;
			case('/'):
				result = operandOne / operandTwo;
				history.add(result);
				return result;
			case('*'):
				result = operandOne * operandTwo;
				history.add(result);
				return result;
			case('-'):
				result = operandOne - operandTwo;
				history.add(result);
				return result;
		}
		return defaultResponse;
	}
	

	public float getCurrentValue() {		//gets the current resulting value
		return result;
	}

	public void setResult(float result) {		//sets the result
		this.result = result;
	}

	public float getMemoryValue() {			//gets the value most recently added to memory
		return memval;
	}
	
	public void setMemoryValue(float memval) {			//adds value to current memory
		this.memval = memval;
	}
		
	public void clearMemory() {			//clears memory
		this.memval = 0;
	}

	public float getHistoryValue(int index) {		// to get a history value at a specific index
		return history.get(index);
	}
	
	public void presentHistory() {			//to print all values stored in history
		
		StringBuilder historySb = new StringBuilder();
		
		ArrayList<String> historyPrintStack = new ArrayList<String>();
		
		for (int kkkk = 0; kkkk < history.size(); kkkk++ )
		{
			historyPrintStack.add(String.valueOf(history.get(kkkk)));
			historyPrintStack.add(" ");
		}
		
		for (String theString : historyPrintStack)
			{
				historySb.append(theString);
			}
		
		String formattedHistory = historySb.toString();
		
		System.out.println(formattedHistory);
	}
}

	