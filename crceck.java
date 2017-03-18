import java.util.*;
import java.nio.file.*;

public class crceck {

    int crcPoly = 41043;
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

    public static int xor(int number1, int number2){
        return number1^number2;
    }

    public static void crcCalculation(char[] data){
        int runningResult = xor();

    }


}
