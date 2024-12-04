package org.rgoussey;

import java.util.List;
import org.rgoussey.shared.FileUtils;

public class Day4 {


  public static void main(String[] args) {
    List<String> lines = FileUtils.getLines(4);
    long count = new WordFinder(lines).countValid("XMAS".toCharArray());
    System.out.println("Part 1 " + count);
    long xCount = new WordFinder(lines).xmasCountFinder();
    System.out.println("Part 2 " + xCount);
  }



  record WordFinder(List<String> text){

    public long xmasCountFinder(){
      long count=0;
      for(int y=0;y<text.size();y++){
        for(int x=0;x<text.get(y).length();x++){
          if (isXmas(x, y)) {
            count += 1;
          }
        }
      }
      return count;
    }

    private boolean isXmas(int x, int y) {
      boolean diagonal1 = (foundNorthEastSide(x, y, "AM".toCharArray()) && foundSouthWestSide(x, y, "AS".toCharArray()))
          || (foundNorthEastSide(x, y, "AS".toCharArray()) && foundSouthWestSide(x, y, "AM".toCharArray()));
      boolean diagonal2 = (foundNorthWestSide(x, y, "AM".toCharArray()) && foundSouthEastSide(x, y, "AS".toCharArray()))
          || (foundNorthWestSide(x, y, "AS".toCharArray()) && foundSouthEastSide(x, y, "AM".toCharArray()));
      return diagonal1 && diagonal2;
    }

    public long countValid(char[] word){
      long count=0;
      for(int y=0;y<text.size();y++){
        for(int x=0;x<text.get(y).length();x++){
          count+=countValidAt(x, y, word);
        }
      }
      return count;
    }

    public int countValidAt(int x, int y, char[] word){
      int count=0;
      if(foundNorthSide(x, y, word)){
        count++;
      }
      if(foundSouthSide(x, y, word)){
        count++;
      }
      if(foundWestSide(x, y, word)){
        count++;
      }
      if(foundEastSide(x, y, word)){
        count++;
      }
      if(foundNorthWestSide(x, y, word)){
        count++;
      }
      if(foundNorthEastSide(x, y, word)){
        count++;
      }
      if(foundSouthWestSide(x, y, word)){
        count++;
      }
      if(foundSouthEastSide(x, y, word)){
        count++;
      }
      return count;
    }

    public boolean foundNorthSide(int x, int y, char[] word){
      if(y<word.length-1){
        return false;
      }
      for(int i=0;i<word.length;i++){
        if(text.get(y-i).charAt(x)!=word[i]){
          return false;
        }
      }
      return true;
    }

    public boolean foundSouthSide(int x, int y, char[] word){
      if(y+word.length>text.size()){
        return false;
      }
      for(int i=0;i<word.length;i++){
        if(text.get(y+i).charAt(x)!=word[i]){
          return false;
        }
      }
      return true;
    }

    public boolean foundWestSide(int x, int y, char[] word){
      if(x<word.length-1){
        return false;
      }
      for(int i=0;i<word.length;i++){
        if(text.get(y).charAt(x-i)!=word[i]){
          return false;
        }
      }
      return true;
    }

    public boolean foundEastSide(int x, int y, char[] word){
      if(x+word.length>text.get(y).length()){
        return false;
      }
      for(int i=0;i<word.length;i++){
        if(text.get(y).charAt(x+i)!=word[i]){
          return false;
        }
      }
      return true;
    }

    public boolean foundNorthWestSide(int x, int y, char[] word){
      if(x<word.length-1 || y<word.length-1){
        return false;
      }
      for(int i=0;i<word.length;i++){
        if(text.get(y-i).charAt(x-i)!=word[i]){
          return false;
        }
      }
      return true;
    }

    public boolean foundNorthEastSide(int x, int y, char[] word){
      if(x+word.length>text.get(y).length() || y<word.length-1){
        return false;
      }
      for(int i=0;i<word.length;i++){
        if(text.get(y-i).charAt(x+i)!=word[i]){
          return false;
        }
      }
      return true;
    }

    public boolean foundSouthWestSide(int x, int y, char[] word){
      if(x<word.length-1 || y+word.length>text.size()){
        return false;
      }
      for(int i=0;i<word.length;i++){
        if(text.get(y+i).charAt(x-i)!=word[i]){
          return false;
        }
      }
      return true;
    }

    public boolean foundSouthEastSide(int x, int y, char[] word){
      if(x+word.length>text.get(y).length() || y+word.length>text.size()){
        return false;
      }
      for(int i=0;i<word.length;i++){
        if(text.get(y+i).charAt(x+i)!=word[i]){
          return false;
        }
      }
      return true;
    }


  }

}
