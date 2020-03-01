package Files;

import java.io.*;
import java.util.Scanner;

public class HighScore
{
    private static String[] names;
    private static int[] scores;
    private static int[] turns;
    private Scanner scanner;
    private String filename = "Highscore.txt";
    public HighScore() throws IOException
    {
        File highscore = new File(filename);
        if(highscore.createNewFile())
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
            writer.write(" \n0\n0\n  \n0\n0\n \n0\n0");
            writer.close();
        }
        scanner = new Scanner(highscore);
        names = new String[3];
        scores = new int[3];
        turns = new int[3];
        getdata();
        scanner.close();
    }

    private void getdata()
    {
        int i = 0;
        while(scanner.hasNextLine())
        {
            names[i] = scanner.nextLine();
            scores[i] = Integer.parseInt(scanner.nextLine());
            turns[i] = Integer.parseInt(scanner.nextLine());
            i++;
        }
    }

    public void setnewscore(String name, int score, int turn) throws IOException {
        if(score > scores[0])
        {
            change(2, 1);
            change(1, 0);
            newscore(name, score, turn, 0);
        }
        else if(score > scores[1])
        {
            change(2, 1);
            newscore(name, score, turn, 1);
        }
        else if(score > scores[2])
        {
            newscore(name, score, turn, 2);
        }
        else return;
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(names[0] + "\n" + scores[0] + "\n" + turns[0] + "\n" + names[1]
                + "\n" + scores[1] + "\n" + turns[1] + "\n" + names[2] + "\n" + scores[2] + "\n" + turns[2]);
        writer.close();
    }
    public boolean checknewscore(int score){
        return score > scores[2];
    }

    private void change(int a, int b)
    {
        names[a] = names[b];
        scores[a] = scores[b];
        turns[a] = turns[b];
    }

    private void newscore(String name, int score, int turn, int old)
    {
        names[old] = name;
        scores[old] = score;
        turns[old] = turn;
    }

    public static String getline(Integer a)
    {
        a++;
        return a.toString() + ". " + names[a - 1] + " " + scores[a - 1] + " " + turns[a - 1];
    }
}
