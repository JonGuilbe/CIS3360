import java.util.*;
import java.nio.file.*;
public class hillcipher{
    public static void main(String[] args){
        Path keyPath = Paths.get(args[0]);
        Path filePath = Paths.get(args[1]);
        Scanner keyScanner, fileScanner;
        try{
        keyScanner = new Scanner(keyPath);
        fileScanner = new Scanner(filePath);
        char[] inputCharacters = new char[10000];
        }
        catch(Exception e){
        keyScanner = new Scanner(System.in);
        fileScanner = new Scanner(System.in);
    }
        int matrixSize = keyScanner.nextInt();
        int[][] keyMatrix = new int[matrixSize][matrixSize];

        for(int i = 0; i < matrixSize; i++){
            for(int j = 0; j < matrixSize; j++){
                keyMatrix[i][j] = keyScanner.nextInt();
                System.out.print(keyMatrix[i][j]);
                if(j != matrixSize -1)
                    System.out.print(" ");
            }
            System.out.println();
        }

        char[] inputCharacters = new char[10000];
        int index = 0;
        while(fileScanner.hasNext()){
            String temp = fileScanner.next();
            for(int i = 0; i < temp.length(); i++){
                if(Chacter.isLetter(temp.charAt(i))){
                    inputCharacters[index] = temp.charAt(i);
                    index++;
            }
        }
        for(int i = 0; i < index; i++){
            System.out.print(inputCharacters[i]);
        }
    }
}