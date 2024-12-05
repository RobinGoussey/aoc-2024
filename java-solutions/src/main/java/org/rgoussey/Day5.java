package org.rgoussey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.rgoussey.Day4.WordFinder;
import org.rgoussey.shared.FileUtils;

public class Day5 {


  public static void main(String[] args) {
    List<String> lines = FileUtils.getLines(5);
    pageOrderValidator pageOrderValidator = new pageOrderValidator(lines);
    System.out.println(pageOrderValidator.getSumOfMiddleNumbersOfUpdatesInOrder());
  }

  static class pageOrderValidator{

    private List<int[]> updates = new ArrayList<>();
    private List<PageOrderRule> pageOrderRules = new ArrayList<>();
    public pageOrderValidator(List<String> lines){
      String line = lines.get(0);
      int counter = 0;
      while (!"".equals(line)){
        String[] split = line.split("\\|");
        int firstPage = Integer.parseInt(split[0]);
        int secondPage = Integer.parseInt(split[1]);
        int max = Math.max(firstPage, secondPage);
        if(pageOrderRules.size()< max){
          for (int i = pageOrderRules.size(); i < max; i++) {
            pageOrderRules.add(new PageOrderRule());
          }
        }
        pageOrderRules.get(firstPage-1).addPageAfter(secondPage);
        pageOrderRules.get(secondPage-1).addPageBefore(firstPage);
        line = lines.get(counter++);
      }
      for (int i = counter; i < lines.size(); i++) {
        String[] split = lines.get(i).split(",");
        int[] update = Arrays.stream(split).mapToInt(Integer::parseInt).toArray();
        updates.add(update);
      }
    }

    List<int[]> getUpdatesInOrder(){
      return updates.stream()
          .filter(this::isInOrder)
          .toList();
    }

    long getSumOfMiddleNumbersOfUpdatesInOrder(){
      return updates.stream()
          .filter(this::isInOrder)
          .map(array -> array[array.length / 2]).mapToLong(i -> i).sum();
    }

    boolean isInOrder(int[] pages){
      for (int i = 0; i < pages.length; i++) {
        for (int j = i+1; j < pages.length; j++) {
          if(pageOrderRules.get(pages[i]-1).isBefore(pages[j])){
            return false;
          }
        }
      }
      return true;
    }

  }

  private static class PageOrderRule {
    private final List<Integer> pagesAfter = new ArrayList<>();
    private final List<Integer> pagesBefore = new ArrayList<>();
    public void addPageAfter(int page){
      pagesAfter.add(page);
    }

    public void addPageBefore(int page){
      pagesBefore.add(page);
    }

    public boolean isBefore(int page){
      return pagesBefore.contains(page) || ! pagesAfter.contains(page);
    }
  }
}
