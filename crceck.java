import java.util.*;
import java.nio.file.*;

public class crceck {

    String crcPoly = "1010000001010011";
    public static void main(String[] args){
        String modeFlag = args[0];
        if(!modeFlag.equals("c") && !modeFlag.equals("v"));
            System.out.println("ERROR: UNSUPPRORTED MODE! KYS FGT");
        Path filePath = Paths.get(args[1]);
        Scanner fileScanner;
        try {
            fileScanner = new Scanner(filePath);
        } catch (Exception e) { //Should never reach this!
            System.out.println("Error: Could not open file(s)! EXIT OR DIE.");
            fileScanner = new Scanner(System.in);
        }


    }

    public static char[] getData(Scanner fileScanner){
        String temp = fileScanner.next();
        int fileLength = temp.length();
        int index = 0;
        char[] input = new char[512];
        for(index = 0; index < fileLength - 8; index++){
            input[index] = temp.charAt(index);
        }

        for(index = index; index < 504; index++){
            input[index] = '.';
        }
        return input;
    }

    public static int xor(Char bit1, Char bit2){
        return bit1 == bit2;
    }

    public static void crcCalculation(String input){
        String currentResult = "";
        for(int i = 0; i < 16; i++){
            currentResult += input.charAt(i);
        }
        int index = 0, returnIndex = 0;
        while(returnIndex + 16 < input.length()){
            index = returnIndex;
            boolean first1Found = false;
            String newString = "";
            for(int i = 0; i < 16; i++){
                int toAdd = xor(currentResult.charAt(index + i), crcPoly.charAt(i)); //might need a seperate counter for the crcPoly...
                if(!first1Found && toAdd == 0){
                    index++; returnIndex++;
                }
                else
                    first1Found = true;
                    newString += toAdd;
            }
            int i = 1;
            int returnIndexCopy = returnIndex;
            while(returnIndexCopy != 0){
                newString += input.charAt(index + i);
                index++;
                returnIndexCopy--; //Less shady
            }
            currentResult = newString;
        }
        System.out.println(Integer.decode(currentResult));

    }


}
