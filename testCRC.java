import java.util.*;

public class testCRC{
    public static void main(String[] args){
        Scanner stdin = new Scanner(System.in);
        String test = stdin.nextLine();
        int sum = 0;
        for(int i = 0; i < test.length(); i++){
            sum += (int)test.charAt(i);
        }
        System.out.println(sum);
        System.out.println(sum^41043);


    }
}