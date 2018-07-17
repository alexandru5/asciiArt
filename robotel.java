import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Solution {
    private static int currentLine = -1, currentCol = -1;
    private static char currentDir = 'S';
    private static int t1x = -1, t2x = -1, t1y = -1, t2y = -1;
    private static char directions[] = {'S', 'E', 'N', 'W'};
    private static boolean reversePrio = false;
    private static boolean shouldTele = false;
    private static boolean dead = false;
    private static boolean pathModified = false;
    private static int drunkLevel = 0;
    private static String steps = "";
    private static String result = "";
    
    public static final Pattern LOOP_REGEX =
            Pattern.compile("[A-Z]+", Pattern.CASE_INSENSITIVE);

    private static char[][] map;
    
    public static char getDirection(int priority) {
        if (reversePrio)
            return directions[3 - priority];
        return directions[priority];
    }
        
    public static String getStringDirection(char direction) {
        switch(direction) {
            case 'N':
                return "NORTH\n";
            case 'S':
                return "SOUTH\n";
            case 'E':
                return "EAST\n";
            case 'W':
                return "WEST\n";
        }
        return "";
    }
    
    public static boolean canMove(char direction) {
        int x=0, y=0;
        switch(direction) {
            case 'N':
                x = currentLine-1;
                y = currentCol;
                break;
            case 'S':
                x = currentLine+1;
                y = currentCol;
                break;
            case 'E':
                x = currentLine;
                y = currentCol+1;
                break;
            case 'W':
                x = currentLine;
                y = currentCol-1;
                break;
        }
        return map[x][y] != '#' && (map[x][y] != 'X' || drunkLevel % 2 == 1);
    }
    
    public static void checkCurrentState() {
        char x = map[currentLine][currentCol];
        pathModified = false;
        switch(x){
            case '$':
                dead = true;
                break;
            case 'T':
                shouldTele ^= true;
                break;
            case 'I':
                reversePrio ^= true;
                break;
            case 'B':
                drunkLevel++;
                break;
            case 'N':
                pathModified = true;
                currentDir = 'N';
                break;
            case 'S':
                pathModified = true;
                currentDir = 'S';
                break;
            case 'E':
                pathModified = true;
                currentDir = 'E';
                break;
            case 'W':
                pathModified = true;
                currentDir = 'W';
                break;
                
        }
    }
    public  static void makeMove() {
        if (shouldTele) {
            if (currentLine == t1x && currentCol == t1y) {
                currentLine = t2x;
                currentCol = t2y;
            } else {
                currentLine = t1x;
                currentCol = t1y;
            }
        } else {
            switch(currentDir) {
                case 'N':
                    currentLine--;
                    break;
                case 'S':
                    currentLine++;
                    break;
                case 'E':
                    currentCol++;
                    break;
                case 'W':
                    currentCol--;
                    break;
            }
        }
    }
    
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int L = in.nextInt();
        int C = in.nextInt();
        
        if (in.hasNextLine()) {
            in.nextLine();
        }
        map = new char[L][C];

        for (int i = 0; i < L; i++) {
            String row = in.nextLine();
            map[i] = row.toCharArray();
            
            int startCol = row.indexOf('@');
            if (startCol != -1) {
                currentLine = i;
                currentCol = startCol;
                continue;
            }
            
            if (t1y == -1) {
                t1y = row.indexOf('T');
                t1x = i;
                t2y = row.lastIndexOf('T');
                if (t2y == t1y)
                    t2y = -1;
            } else {
                if (t2y == -1) {
                    t2y = row.indexOf('T');
                    t2x = i;
                }
            }
        }

        while (true) {
            checkCurrentState();
            if (dead)
                break;
            if (canMove(currentDir)) {
                makeMove();
                if (map[currentLine][currentCol] == 'X')
                        map[currentLine][currentCol] = ' ';
            } else {
                for (int i = 0; i < 4; i++) {
                    if (canMove(getDirection(i))) {
                        currentDir = getDirection(i);
                        makeMove();
                        if (map[currentLine][currentCol] == 'X')
                            map[currentLine][currentCol] = ' ';
                        break;
                    }
                }
            }
            
            if (!shouldTele) {
                steps += currentDir;
                result += getStringDirection(currentDir);
            }
            
            
            //System.out.println(currentLine + " -> " + currentCol);
        }

        System.out.println(result);
    }
}