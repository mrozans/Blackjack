package Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprites
{
    private static final int WIDTH = 72;
    private static final int HEIGHT = 96;
    private BufferedImage img = null;
    public BufferedImage[] deck;
    public Sprites()
    {
        try
        {
            img = ImageIO.read(Sprites.class.getResource("/cards.png"));
        }
        catch (
                IOException e) { System.exit(-1);
        }
        deck = new BufferedImage[53];
        assign();
    }
    private void assign()
    {
        int j = 0;
        for(int i = 0; i < 52; i++)
        {
            deck[i] = img.getSubimage((i % 13) * 72, j, WIDTH , HEIGHT);
            if(i == 13) j = 96;
            if(i == 13 * 2) j = 96 * 2;
            if(i == 13 * 3) j = 96 * 3;
        }
        deck[52] = img.getSubimage(72 * 13, 0, WIDTH , HEIGHT);
    }
}
