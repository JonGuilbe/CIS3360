import java.util.*;
import java.nio.file.*;

public class crcheck {

    static String crcPoly = "1010000001010011";
    static int inputCounter = 15;

    public static void main(String[] args) {
        String modeFlag = args[0];
        if (!modeFlag.equals("c") && !modeFlag.equals("v")) {
            System.out.println("ERROR: UNSUPPRORTED MODE!");
            System.exit(0);
        }
        Path filePath = Paths.get(args[1]);
        Scanner fileScanner;
        try {
            fileScanner = new Scanner(filePath);
        } catch (Exception e) {
            System.out.println("Error: Could not open file!");
            System.exit(0);
            fileScanner = new Scanner(System.in);
        }
        System.out.printf("CRC16 Input text from file\n\n");
        crcCalc(modeFlag, fileScanner);

    }

    public static void crcCalc(String modeFlag, Scanner scan) {
        String inLine = "";
        //Take in input, accouting for spaces counting as "lines" in Scanner
        while (scan.hasNextLine()) {
            inLine += scan.nextLine();
        }
        //Spit the input back out, adding a new line every 64 characters.
        int inLineIndex = 0;
        for (int i = 0; i < inLine.length(); i++) {
            System.out.print(inLine.charAt(i));
            inLineIndex++;
            if (inLineIndex % 64 == 0) {
                System.out.println();
                inLineIndex = 0;
            }
        }
        //Split the input into the 8 lines for easy output in the CRC Calculation portion
        String test = "";
        String[] rawInput = new String[8];
        int inputLine = 0;
        while (inLine.length() < 504) {
            inLine += ".";
        }
        while (inputLine != 8) {
            if (modeFlag.equals("c") && inputLine == 7) {
                rawInput[inputLine] = inLine.substring(0, 56);
            } else {
                rawInput[inputLine] = inLine.substring(0, 64);
                inLine = inLine.substring(64);
            }
            inputLine++;
        }
        //Mark what the CRC is for Verification
        String crcCode = "";
        if (modeFlag.equals("v")) {
            crcCode = rawInput[7].substring(56);
        }
        System.out.printf("\n\nCRC 15 Calculation progress: \n\n");

        for (int i = 0; i < 7; i++) {
            test += rawInput[i];
        }
        //Convert the input into bytes using a StringBuilder
        byte[] bytes = test.getBytes();
        StringBuilder input = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                input.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        //Add the extra 0's at the end to complete the CRC setup
        for (int i = 0; i < 15; i++) {
            input.append('0');
        }

        String currentResult = "";
        int n = 0;
        boolean oneFound = false;
        //Find out where to begin the CRC
        while (currentResult.length() != 16) {
            if (!oneFound && input.charAt(n) == '0') {
                n++;
                inputCounter++;
            } else {
                oneFound = true;
                currentResult += input.charAt(n);
                n++;
            }
        }
        //This simulates how doing a CRC on paper works. We line up the 1's, and then copy every XOR into our answers. We mark where to start next and keep going until we
        //hit the padded 0's. Not sure why this isn't working...
        int index = 0, returnIndex = 0;
        boolean visited[] = new boolean[8];
        while (inputCounter < input.length() - 15) {
            index = 0;
            returnIndex = 0;
            boolean first1Found = false;
            String newString = "";
            int temp = Integer.parseInt(currentResult, 2);
            for (int i = 0; i < 16; i++) {
                int toAdd = xor(currentResult.charAt(index), crcPoly.charAt(i));
                if (!first1Found && toAdd == 0) {
                    index++;
                    returnIndex++;
                    inputCounter++;
                } else {
                    first1Found = true;
                    newString += toAdd;
                    index++;
                }
                if ((inputCounter / 512) - 1 != -1 && !visited[(inputCounter / 512) - 1] && (inputCounter) % 512 == 0) {
                    visited[(inputCounter / 512) - 1] = true;
                    System.out.println(rawInput[((inputCounter) / 512) - 1] + " - 0000" + Integer.toString(temp, 16));
                }
            }
            int i = 1;
            int returnIndexCopy = returnIndex;
            while (returnIndexCopy != 0) {
                newString += input.charAt(inputCounter + i);
                returnIndexCopy--;
                i++;
            }
            currentResult = newString;
        }
        int result = Integer.parseInt(currentResult, 2);
        if (modeFlag.equals("c"))
            System.out.print(rawInput[7] + "0000" + Integer.toString(result, 16));
        else
            System.out.print(rawInput[7]);
        System.out.println(" - 0000" + Integer.toString(result, 16));
        System.out.println("");
        System.out.println("CRC15 Result: 0000" + Integer.toString(result, 16));
        if (modeFlag.equals("v")) {
            System.out.println("");
            if (crcCode.equals(Integer.toString(result, 16)))
                System.out.println("CRC 15 Verification passed!");
            else
                System.out.println("CRC 15 Verification failed!");
        }
    }

    public static int xor(char bit1, char bit2) {
        if (bit1 == bit2)
            return 0;
        else
            return 1;
    }

}
