package org.rgoussey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.rgoussey.shared.FileUtils;

public class Day7 {

  public static void main(String[] args) {
    List<String> lines = FileUtils.getLines(7);
    List<EquationPermutationHolder> equationPermutationHolders = parseLines(lines, List.of(new Addition(), new Multiplication()));
    long count = sumSolvableEquations(equationPermutationHolders);
    System.out.println(count);
    equationPermutationHolders = parseLines(lines, List.of(new Addition(), new Multiplication(), new Concatenate()));
    count = sumSolvableEquations(equationPermutationHolders);
    System.out.println(count);
  }

  public static long sumSolvableEquations(List<EquationPermutationHolder> equationPermutationHolders){
    return equationPermutationHolders.stream().filter(EquationPermutationHolder::canBeSolved)
        .map(EquationPermutationHolder::desiredResult)
        .mapToLong(Long::longValue).sum();
  }

  public static List<EquationPermutationHolder> parseLines(List<String> lines, List<Operation> operations) {
    return lines.stream().map((String line) -> parseLine(line,operations)).toList();
  }



  private static EquationPermutationHolder parseLine(String line, List<Operation> operations){
    String[] split = line.split(":");
    long desiredResult = Long.parseLong(split[0]);
    long[] numbers = Arrays.stream(split[1].trim().split(" ")).mapToLong(Long::parseLong).toArray();
    List<Equation> equations = new ArrayList<>();
    equations.add(new Equation(numbers[0]));
    for (int i = 1; i < numbers.length; i++) {
      List<Equation> newEquations = new ArrayList<>();
      for (Equation equation : equations) {
        for (Operation operation : operations) {
          Equation newEquation = new Equation(equation);
          newEquation.apply(operation, numbers[i]);
          newEquations.add(newEquation);
        }
      }
      equations = newEquations;
    }
    return new EquationPermutationHolder(equations, desiredResult);
  }

  record EquationPermutationHolder(List<Equation> equations, long desiredResult) {

    boolean canBeSolved() {
      return equations.stream().anyMatch(equation -> equation.solve() == desiredResult);
    }
  }

  static class Equation {

    List<Long> numbers = new ArrayList<>();
    List<Operation> operations = new ArrayList<>();

    Equation(long startingNumber){
      numbers.add(startingNumber);
    }

    Equation(Equation equation){
      numbers.addAll(equation.numbers);
      operations.addAll(equation.operations);
    }

    void apply(Operation operation, long number){
      operations.add(operation);
      numbers.add(number);
    }

    long solve(){
      long result = numbers.getFirst();
      for(int i = 0; i < operations.size(); i++){
        result = operations.get(i).apply(result, numbers.get(i+1));
      }
      return result;
    }

  }



  interface Operation {
    long apply(long a, long b);
  }

  static class Addition implements Operation {
    @Override
    public long apply(long a, long b) {
      return a + b;
    }
  }

  static class Multiplication implements Operation {
    @Override
    public long apply(long a, long b) {
      return a * b;
    }
  }

  static class Concatenate implements Operation {
    @Override
    public long apply(long a, long b) {
      return Long.parseLong(a + "" + b);
    }
  }
}
