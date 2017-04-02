import java.util.*;

public class testCRC{

    static String crcPoly = "1010000001010011";
    //static String crcPoly = "1000000000000101";
    static int inputCounter = 15;
    public static void main(String[] args){
        Scanner stdin = new Scanner(System.in);
        String test = "";
        //while(stdin.hasNextLine()){
          //test += stdin.nextLine();
          //System.out.println("Line read! Current length: " +  test.length());
        //}

        test = stdin.nextLine();

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
        //for(int i = 0; i < 15; i++){
          //input.append('0');
        //}


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
        while(inputCounter < input.length() - 15){
            index = 0;
            returnIndex = 0;
            boolean first1Found = false;
            String newString = "";
            int temp = Integer.parseInt(currentResult, 2);
            /* if((inputCounter + 1) % 512 == 0) */    System.out.println("Current string: " + currentResult + " " + Integer.toString(temp,16));
            for(int i = 0; i < 16; i++){
                //System.out.printf("i is %d index is %d and returnIndex is %d\n", i, index, returnIndex);
                int toAdd = xor(currentResult.charAt(index), crcPoly.charAt(i)); //might need a seperate counter for the crcPoly...
                if(!first1Found && toAdd == 0){
                    index++; 
                    returnIndex++;
                    inputCounter++;
                    /*if((inputCounter + 1) % 512 == 0) */   System.out.println("Current string: " + currentResult + " " + Integer.toString(temp,16));
                    //System.out.println("returnIndex getting incremented! Input counter at " + inputCounter + " & input length is at " + input.length());
                }
                else{
                    first1Found = true;
                    newString += toAdd;
                    index++;
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
        System.out.println(Integer.toString(result,16));
    }
        public static int xor(char bit1, char bit2){
        if(bit1 == bit2)
        return 0;
        else
        return 1;
    }

}