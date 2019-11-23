package com.example.cps731_project.logic_layer;
import java.util.Random;
import java.lang.Integer;


//The equation class creates the questions for the game
public class Equation {

    private final int AMOUNTOFNUMBERS = 5;
    private Random rand = new Random();
    private int [] numbers = new int [AMOUNTOFNUMBERS];
    private char [] modifiers;
    private int answer;

    //default constructor for testing
    public Equation(int [] numbers){
        this.numbers = numbers;
        setModifiers(new char [] {'+','-'});
        generateAnswer();
    }

    //main constructor for app
    public Equation(int [] numbers, char [] modifiers){
        setNumbers(numbers);
        setModifiers(modifiers);
        generateAnswer();
    }

    //setter for numbers
    public void setNumbers(int[] numbers){
        this.numbers = numbers;
    }

    //getter for numbers
    public int [] getNumbers(){
        return numbers;
    }

    //setter for modifiers
    public void setModifiers(char[] modifiers) {
        this.modifiers = modifiers;
    }

    //getter for modifiers
    public char [] getModifiers(){
        return modifiers;
    }

    //generates a random answer based on  the number array and available modifers
    public int generateAnswer(){
        numbers = getNumbers();
        int randInt = rand.nextInt(numbers.length);

        answer = numbers[randInt];
        int [] newNumbers = new int[numbers.length-1];

        for(int i = 0; i<newNumbers.length; i++){
            if(i>=randInt)
                newNumbers[i]=numbers[i+1];
            else
                newNumbers [i]= numbers[i];
        }

        answer = iterateAnswer(answer, newNumbers);
        return answer;
    }

    //recursive case for generate answer
    private int iterateAnswer(int answer, int[]numbers){
        if(numbers.length==0)
            return answer;
        else{
            modifiers=getModifiers();
            int randInt = rand.nextInt(numbers.length);
            int operand = numbers[randInt];
            randInt = rand.nextInt(modifiers.length);
            char operator = modifiers[randInt];

            answer = calculate(answer, operator, operand);

            int [] newNumbers = new int[numbers.length-1];

            for(int i = 0; i<newNumbers.length; i++){
                if(i>=randInt)
                    newNumbers[i]=numbers[i+1];
                else
                    newNumbers [i]= numbers[i];
            }
            answer = iterateAnswer(answer, newNumbers);
            return answer;
        }
    }

    //getter for answer
    public int getAnswer(){
        return answer;
    }

    //calculates an operation when given 2 operands and an operator
    private int calculate (int operand1, char modifier, int operand2){
        int calculation=operand1;

        switch(modifier) {
            case '+':
                calculation+=operand2;
                break;
            case '-':
                calculation-=operand2;
                break;
            case 'x':
                calculation= calculation*operand2;
                break;
            case '^':
                calculation= calculation^operand2;
        }
        return calculation;
    }

    //generates a solution based on the given parameters
    public int partialSolution (int [] numbers, char [] modifiers){
        if (numbers.length<=1||modifiers.length<=0)
            return 0;

        int partAnswer = numbers[0];
        int modIndex = 0;
        for(int i=1; i<numbers.length-1; i++){
            partAnswer = calculate(partAnswer, modifiers[modIndex], numbers[i]);
            modIndex++;
        }

        return partAnswer;
    }
}