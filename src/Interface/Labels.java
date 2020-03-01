package Interface;

import Files.HighScore;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Labels
{
    private static JLabel Chips;
    private static JLabel Bet;
    private static JLabel Score;
    private static JLabel Score2;
    private static JLabel DealerScore;
    private static JLabel Text;
    private static JLabel Text2;
    private static JLabel Question;
    private static JLabel LeaderBoard0;
    private static JLabel LeaderBoard1;
    private static JLabel LeaderBoard2;
    private static JLabel LeaderBoard3;

    public Labels(JFrame frame)
    {
        Chips = addlabel("Chips: ", 0, 320, 100, 15, frame);
        Bet = addlabel("Bet: ", 0, 300, 100, 15, frame);
        Score = addlabel("Score: ", 500, 532, 100, 10, frame);
        Score2 = addlabel("Score 2nd hand: ", 500, 480, 120, 10, frame);
        DealerScore = addlabel("Dealer Score: ", 500, 80, 100, 10, frame);
        Text = addlabel(" ", 500, 300, 400, 25, frame);
        Text2 = addlabel(" ", 500, 330, 400, 25, frame);
        Question = addlabel(" ", 500, 360, 400, 25, frame);
        LeaderBoard0 = addlabel("Place/Name/Score/Turns ", 500, 320, 300, 15, frame);
        LeaderBoard1 = addlabel("", 500, 335, 300, 15, frame);
        LeaderBoard2 = addlabel("", 500, 350, 300, 15, frame);
        LeaderBoard3 = addlabel("", 500, 365, 300, 15, frame);
        Text.setFont((new Font(Text.getFont().getName(), Font.PLAIN, 20)));
        Text2.setFont((new Font(Text.getFont().getName(), Font.PLAIN, 20)));
        reset();
    }

    private JLabel addlabel(String name, int x, int y, int width, int height, JFrame frame)
    {
        JLabel label = new JLabel(name);
        label.setBounds(x,y,width, height);
        frame.add(label);
        return label;
    }

    private static void reset()
    {
        Chips.setVisible(false);
        Bet.setVisible(false);
        Score.setVisible(false);
        Score2.setVisible(false);
        DealerScore.setVisible(false);
        Text.setVisible(false);
        Text2.setVisible(false);
        Question.setVisible(false);
        LeaderBoard0.setVisible(false);
        LeaderBoard1.setVisible(false);
        LeaderBoard2.setVisible(false);
        LeaderBoard3.setVisible(false);
    }

    public static void setMenu()
    {
        reset();
    }

    public static void setLeaderBoard()
    {
        try {
            HighScore highScore = new HighScore();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LeaderBoard0.setVisible(true);
        LeaderBoard1.setVisible(true);
        LeaderBoard2.setVisible(true);
        LeaderBoard3.setVisible(true);
        LeaderBoard1.setText(HighScore.getline(0));
        LeaderBoard2.setText(HighScore.getline(1));
        LeaderBoard3.setText(HighScore.getline(2));
    }

    public static void setGame()
    {
        Chips.setVisible(true);
        Bet.setVisible(true);
        Score.setVisible(true);
        DealerScore.setVisible(true);
    }

    public static void setScore2()
    {
        Score2.setVisible(true);
    }

    public static void setText(String text)
    {
        Text.setText(text);
        Text.setVisible(true);

    }

    public static void setText2(String text)
    {
        Text2.setText(text);
        Text2.setVisible(true);
    }

    public static void setQuestion(String text)
    {
        Question.setText(text);
        Question.setVisible(true);
    }

    public static void hideText()
    {
        Text.setVisible(false);
    }

    public static void hideText2()
    {
        Text2.setVisible(false);
    }

    public static void hideQuestion()
    {
        Question.setVisible(false);
    }

    public static void hideScore2()
    {
        Score2.setVisible(false);
    }

    public static void setScore(int score)
    {
        Score.setText("Score: " + score);
    }

    public static void setScore2(int score)
    {
        Score2.setText("Score 2nd hand: " + score);
    }

    public static void setBet(int bet)
    {
        Bet.setText("Bet: " + bet);
    }

    public static void setChips(int chips)
    {
        Chips.setText("Chips: " + chips);
    }

    public static void setDscore(int score)
    {
        DealerScore.setText("Dealer Score:  " + score);
    }
}
