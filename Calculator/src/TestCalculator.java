package src;

import java.util.Scanner;

public class TestCalculator {

	public static void main(String[] args) {

		String nextInstructor;
		float result;
		String expression;
		String input = "";
		Calculator myCalculator = new Calculator();
		Scanner inputDevice = new Scanner(System.in);
		while (!input.equals("99999")) {
			System.out.println("Please input a calculator expression:  press 99999 to exit");
			expression = inputDevice.nextLine();
			input = expression;
			inputDevice.nextLine();
			myCalculator.expression = expression;
			result = myCalculator.evaluate(expression);
			myCalculator.setResult(result);
			if (result == Float.MIN_VALUE)
				{
				System.out.println("Invalid input");
				}
			System.out.println("The result of your expression is " + result);
			myCalculator.memval = myCalculator.getCurrentValue();
			System.out.println("Next instruction: m - to save to memory, mr - to print what is in memory, c - to clear memory, h - to view history");
			nextInstructor = inputDevice.nextLine();
			inputDevice.nextLine();
			if (nextInstructor.equals("m") || nextInstructor.equals("M"))
			{
				myCalculator.setMemoryValue(myCalculator.memval);
			}
			if (nextInstructor.equals("mr") || nextInstructor.equals("MR"))
			{
				System.out.println(myCalculator.memval);
			}
			if (nextInstructor.equals("c") || nextInstructor.equals("C"))
			{
				myCalculator.clearMemory();;
			}
			if (nextInstructor.equals("h") || nextInstructor.equals("H"))
			{
				myCalculator.presentHistory();
				int index;
				System.out.println("Please input an index");
				index = inputDevice.nextInt();
				inputDevice.nextLine();
				System.out.println("The item at index " + index + " is " + myCalculator.getHistoryValue(index));
			}
		}

	}

}
