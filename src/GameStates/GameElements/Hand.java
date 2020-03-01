package GameStates.GameElements;

import java.awt.*;
import java.util.ArrayList;
import Graphics.Sprites;

public class Hand
{
    private boolean blackjack;
    private ArrayList<Integer> hand;
    private ArrayList<Cardimage> hand2;
    private Sprites sp = new Sprites();
    private int type;
    private int start;
    private static final int PLAYER_HAND = 0;
    private static final int PLAYER2_HAND = 1;
    private static final int DEALER_HAND = 2;
    private static final int PLAYER_START = 500;
    private static final int PLAYER2_START = 450;
    private static final int DEALER_START = 50;
    private Cardimage usedcards = null;
    private static final int deackxpos = 1100;
    private static final int deackypos = 50;
    private static final int usedcardsxpos = 400;

    public Hand(int type)
    {
        hand = new ArrayList<>();
        hand2 = new ArrayList<>();
        blackjack = false;
        this.type = type;
        if(type == PLAYER_HAND) start = PLAYER_START;
        else if(type == PLAYER2_HAND) start = PLAYER2_START;
        else start = DEALER_START;
        if(type == DEALER_HAND) usedcards = new Cardimage(0, deackxpos, deackypos, sp.deck[52], sp.deck[52]);
    }

    public int getscore()
    {
        int score = 0;
        for (Integer integer : hand)
        {
            score += integer;
        }
        if(score > 21)
        {
            boolean ace = aceswapvalue();
            if(!ace) return score;
            else return getscore();
        }
        else return score;
    }

    private boolean aceswapvalue()
    {
        int i = 0;
        for (Integer integer : hand)
        {
            if(integer == 11)
            {
                hand.set(i, 1);
                return true;
            }
            i++;
        }
        return false;
    }

    private int getvalue(int card)
    {
        int value = card % 13;
        if(value != 0 && value != 1 && value != 12) return value;
        else return 10;
    }

    public boolean chceckiface()
    {
        return hand.get(0) == 11;
    }

    public boolean bust()
    {
        int score = getscore();
        return score > 21;
    }

    public void add(int card)
    {
        int size = hand2.size();
        Cardimage ci = new Cardimage(card, 900 - 30 * size, start, sp.deck[card], sp.deck[52]);
        if(type == DEALER_HAND && size == 1) ci.sethidden();
        hand2.add(ci);
        Integer value = getvalue(card);
        hand.add(value);
        blackjack = hand.size() == 2 && getscore() == 21;
    }

    public boolean isnoteven()
    {
        return !hand.get(0).equals(hand.get(1));
    }

    public void reset()
    {
        hand.clear();
        hand2.clear();
    }

    public boolean isBlackjack()
    {
        return blackjack;
    }

    public void render(Graphics g)
    {
        if(type == DEALER_HAND) g.drawImage(sp.deck[52], deackxpos, deackypos, null);
        for(Cardimage ci: hand2)
        {
            ci.render(g);
        }
        if(type == DEALER_HAND) usedcards.render(g);
    }
    public int splited1()
    {
        return (hand2.get(0)).getcard();
    }

    public int splited2()
    {
        return (hand2.get(1)).getcard();
    }

    public void setsplit()
    {
        if(type == PLAYER_HAND) hand2.get(0).setstart(900, PLAYER_START);
        else hand2.get(0).setstart(900, PLAYER2_START);
        hand2.get(0).stop();
    }

    public void show()
    {
        hand2.get(1).show();
    }

    public void putaside()
    {
        if(type == DEALER_HAND) usedcards.setstart(usedcardsxpos, deackypos);
        for(Cardimage ci: hand2)
        {
            ci.setpos(usedcardsxpos, deackypos);
        }
    }

    public void putback()
    {
        usedcards.setpos(deackxpos, deackypos);
    }
}
