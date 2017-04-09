import java.util.*;
import java.nio.file.*;

public class crceck {

    static String crcPoly = "1010000001010011";
    static int inputCounter = 15;
    public static void main(String[] args){
        String modeFlag = args[0];
        if(!modeFlag.equals("c") && !modeFlag.equals("v")){
            System.out.println("ERROR: UNSUPPRORTED MODE!");
            System.exit(0);
        }
        Path filePath = Paths.get(args[1]);
        Scanner fileScanner;
        try {
            fileScanner = new Scanner(filePath);
        } catch (Exception e) { //Should never reach this!
            System.out.println("Error: Could not open file(s)!");
            System.exit(0);
            fileScanner = new Scanner(System.in);
        }
        System.out.printf("CRC16 Input text from file\n\n");
        crcCalc(modeFlag, fileScanner);

    }

        public static void crcCalc(String modeFlag, Scanner scan){
        String test = "";
        String[] rawInput = new String[8];
        int inputLine = 0;
        while(scan.hasNextLine() && inputLine != 8){
            rawInput[inputLine] = scan.nextLine();
            if(inputLine != 7 && rawInput[inputLine].length() != 64){
                while(rawInput[inputLine].length() != 64){
                    rawInput[inputLine] += '.';
                    //System.out.println("a");
                }
            }
            else if(inputLine == 7 && rawInput.length != 56){
                while(rawInput[inputLine].length() != 56){
                    rawInput[inputLine] += '.';
                    //System.out.println("b");
                }
            }
         //System.out.println("c");
          test += rawInput[inputLine];
          System.out.println(rawInput[inputLine]);
          inputLine++;
        }
        while(inputLine < 7){
            rawInput[inputLine] = "................................................................";
            System.out.println(rawInput[inputLine]);
            inputLine++;
           //System.out.println("d");
        }
        if(rawInput[7] == null){
            rawInput[7] = "........................................................";
            //System.out.println("e");
        }
         String crcCode = "";
        if(modeFlag.equals("v")){
           crcCode = rawInput[7].substring(56);
           rawInput[7] = rawInput[7].substring(0,56);
        }
        System.out.printf("\nCRC 15 Calculation progress: \n\n");
        //test = stdin.nextLine();

        int sum = 0;
        int xorSum = 0;
        String test2 = "abcd" + 1  + "ab";
        test2 += " c d";
        //System.out.println(test2);

        byte[] bytes = test.getBytes();
        StringBuilder input = new StringBuilder();
        for( byte b : bytes){
            int val = b;
            for(int i = 0; i < 8; i++){
                input.append((val & 128) == 0 ? 0: 1);
                val <<= 1;
            }
        }
        for(int i = 0; i < 15; i++){
          input.append('0');
        }


        //System.out.println("Testing getBytes: " + input);
        //for(int i = 0; i < test.length(); i++){
           // sum += (int)test.charAt(i)<<8;
            /*sum += (int)test.charAt(i+1);
            System.out.println(sum);
            System.out.println(Integer.toHexString(sum^41043));
            sum = sum^41043;
            i++; */
        //}
        //System.out.println(sum);
        //System.out.println(sum^41043);
        //System.out.println("XOR Test: " + bytes[0] ^ 1 + " " + bytes[0] ^ 0);

        /* NEW STUFF HERE
        int total = 0;
        for(int i = 0; i < test.length(); i++){
            System.out.printf("Adding %d to %d\n", (int)test.charAt(i), total);
            total += (int)test.charAt(i);
        }
        String input = Integer.toBinaryString(total);
        System.out.println("Input is : " + input);
        
        
        END NEW STUFF */ 
        

        String currentResult = "";
        int n = 0;
        boolean oneFound = false;
        while(currentResult.length() != 16){
            if(!oneFound && input.charAt(n) == '0'){
                n++;
                inputCounter++;
            }
            else{
                oneFound = true;
                currentResult += input.charAt(n);
                n++;
            }
        }
        
        int index = 0, returnIndex = 0;
        boolean visited[] = new boolean[8];
        while(inputCounter < input.length() - 15){
            index = 0;
            returnIndex = 0;
            boolean first1Found = false;
            String newString = "";
            int temp = Integer.parseInt(currentResult, 2);
            //if((inputCounter + 1) % 512 == 0)
                //System.out.println(rawInput[(inputCounter + 1) / 512] + " - 0000" + Integer.toString(temp,16));
            for(int i = 0; i < 16; i++){
                //System.out.printf("i is %d index is %d and returnIndex is %d\n", i, index, returnIndex);
                int toAdd = xor(currentResult.charAt(index), crcPoly.charAt(i)); //might need a seperate counter for the crcPoly...
                if(!first1Found && toAdd == 0){
                    index++; 
                    returnIndex++;
                    inputCounter++;
                    /*if((inputCounter + 1) % 512 == 0) */   //System.out.println("Current string: " + currentResult + " " + Integer.toString(temp,16));
                    //System.out.println("returnIndex getting incremented! Input counter at " + inputCounter + " & input length is at " + input.length());
                }
                else{
                    first1Found = true;
                    newString += toAdd;
                    index++;
                }
                if((inputCounter / 512)-1 != -1 && !visited[(inputCounter / 512)-1] && (inputCounter) % 512 == 0){
                    visited[(inputCounter / 512)-1] = true;
                    System.out.println(rawInput[((inputCounter) / 512)-1] + " - 0000" + Integer.toString(temp,16));
                }
            }
            int i = 1;
            int returnIndexCopy = returnIndex;
            while(returnIndexCopy != 0){
                newString += input.charAt(inputCounter + i);
                returnIndexCopy--; //Less shady
                i++;
            }
            currentResult = newString;
        }
        int result = Integer.parseInt(currentResult, 2);
        System.out.println(rawInput[7] + "0000" + Integer.toString(result,16) + " - 0000" + Integer.toString(result,16));
        System.out.println("");
        System.out.println("CRC15 Result: 0000" + Integer.toString(result,16));
        if(modeFlag.equals("v")){
            System.out.println("");
            if(crcCode.equals(Integer.toString(result,16)))
                System.out.println("CRC 15 Verification passed!");
            else    
                System.out.println("CRC 15 Verification failed!");
            }
        }
        public static int xor(char bit1, char bit2){
        if(bit1 == bit2)
        return 0;
        else
        return 1;
    }


}
