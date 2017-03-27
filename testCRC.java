import java.util.*;

public class testCRC{

    static String crcPoly = "1010000001010011";
    public static void main(String[] args){
        Scanner stdin = new Scanner(System.in);
        String test = stdin.nextLine();
        int sum = 0;
        int xorSum = 0;
        String test2 = "abcd" + 1  + "ab";
        test2 += " c d";
        System.out.println(test2);
        byte[] bytes = test.getBytes();
        StringBuilder input = new StringBuilder();
        for( byte b : bytes){
            int val = b;
            for(int i = 0; i < 8; i++){
                input.append((val & 128) == 0 ? 0: 1);
                val <<= 1;
            }
        }
        System.out.println("Testing getBytes: " + input);
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
                System.out.printf("i is %d index is %d\n", i, index);
                int toAdd = xor(currentResult.charAt(index), crcPoly.charAt(i)); //might need a seperate counter for the crcPoly...
                if(!first1Found && toAdd == 0){
                    index++; returnIndex++;
                }
                else
                    first1Found = true;
                    newString += toAdd;
                    index++;
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
        public static int xor(char bit1, char bit2){
        if(bit1 == bit2)
        return 0;
        else
        return 1;
    }

}