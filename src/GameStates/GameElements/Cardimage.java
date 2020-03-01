package GameStates.GameElements;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.abs;

class Cardimage
{
    private float x;
    private float y;
    private int card;
    private int posx;
    private int posy;
    private float velx = 0;
    private float vely = 0;
    private boolean samey;
    private boolean hidden;
    private BufferedImage img;
    private BufferedImage back;

    Cardimage(int card, int posx, int posy, BufferedImage img, BufferedImage back)
    {
        this.card = card;
        x = 1100f;
        y = 50f;
        setpos(posx, posy);
        this.img = img;
        this.back = back;
    }

    void setstart(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    void setpos(int x, int y)
    {
        posx = x;
        posy = y;
        if(posy == this.y)
        {
            if(posx >= this.x) velx = 3;
            else velx = -3;
            samey = true;
            return;
        }
        samey = false;
        if(posy >= this.y) vely = 3;
        else vely = -3;
        velx = abs((posx - this.x) * 3 / (posy - this.y));
        if(posx < this.x) velx *= -1;
    }

    void render(Graphics g)
    {
        if(vely != 0 || velx != 0) move();
        if(hidden) g.drawImage(back, (int)x, (int)y, null);
        else g.drawImage(img, (int)x, (int)y, null);
    }

    private void move()
    {
        if(samey)
        {
            if((x < posx + velx) && (x > posx - velx) || (x < posx - velx) && (x > posx + velx))
            {
                x = posx;
                velx = 0;
                return;
            }
        }
        y += vely;
        x += velx;
        if((y < posy + vely) && (y > posy - vely) || (y < posy - vely) && (y > posy + vely))
        {
            y = posy;
            vely = 0;
            x = posx;
            velx = 0;
        }
    }

    int getcard()
    {
        return card;
    }

    void sethidden()
    {
        hidden = true;
    }

    void show()
    {
        hidden = false;
    }

    public void stop()
    {
        velx = 0;
        vely = 0;
    }
}
