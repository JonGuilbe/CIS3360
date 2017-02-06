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
                if(Character.isLetter(temp.charAt(i))){
                    inputCharacters[index] = Character.toLowerCase(temp.charAt(i));
                    index++;
            }
        }
        }

        while(index % keyMatrix.length != 0){
            inputCharacters[index] = 'x';
            //System.out.println("X is being added!");
            index++;
        }
        //System.out.println("Index is " + index);
        for(int i = 0; i < index; i++){
            System.out.print(inputCharacters[i]);
        }
        System.out.println();
        encrypt(keyMatrix, inputCharacters, index);
    }


public static void encrypt(int[][] keyMatrix, char[] inputCharacters, int size) {
    int index = 0;
    while(index < size){
    for(int i = 0; i < keyMatrix.length; i++){
        int sum = 0;
        for(int j = 0; j < keyMatrix.length; j++){
                //System.out.printf("Sum is %d * %d and mod 26 is %d\n", keyMatrix[i][j], (int)inputCharacters[index + j] - 97, keyMatrix[i][j] * ((int)inputCharacters[index + j] - 97));
                sum += keyMatrix[i][j] * (inputCharacters[index + j] -97); //Need to keep track of indicies...
        }
        //System.out.printf("Sum is %d\n", sum);
        System.out.print((char)((sum % 26) + 97));
        //System.out.println();
    }
    index += keyMatrix.length;
}
}

}