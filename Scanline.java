import java.awt.Image;
import java.awt.Toolkit;
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import static java.lang.Math.round;

public class Scanline extends JFrame implements Runnable{
    private BufferedImage buffer, animacion;
    Color cool = new Color(17, 39, 88);
    int Nx =1500, Ny =1000, Nz =-300;
    int [][] Mtrz;
    Thread hilo;
    public Scanline(){
        setTitle("Relleno 3D");
        setSize(730, 730);
        setLayout(null);

        setVisible(true);
        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        animacion = new BufferedImage(5000, 5000, BufferedImage.TYPE_INT_RGB);
        int x=250;
        int y=450;
        int z1=250;
        int z2=450;
        Mtrz = new int[3][8];
        Mtrz[0][0]=x;
        Mtrz[0][1]=y;
        Mtrz[0][2]=x;
        Mtrz[0][3]=y;
        Mtrz[0][4]=x;
        Mtrz[0][5]=y;
        Mtrz[0][6]=x;
        Mtrz[0][7]=y;
        Mtrz[1][0]=x;
        Mtrz[1][1]=y;
        Mtrz[1][2]=y;
        Mtrz[1][3]=x;
        Mtrz[1][4]=x;
        Mtrz[1][5]=y;
        Mtrz[1][6]=y;
        Mtrz[1][7]=x;
        Mtrz[2][0]=z1;
        Mtrz[2][1]=z1;
        Mtrz[2][2]=z1;
        Mtrz[2][3]=z1;
        Mtrz[2][4]=z2;
        Mtrz[2][5]=z2;
        Mtrz[2][6]=z2;
        Mtrz[2][7]=z2;
    }
    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
        animacion.getGraphics().drawImage(buffer, x, y, this);
    }
    public static void main(String[] args) {
        Scanline t = new Scanline();
        t.setVisible(true);
        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void Relleno3d(int x, int y, Color c)
    {
        int IntDelColorOld = animacion.getRGB(x, y);
        Color ColorOld = new Color(IntDelColorOld);
        Color ColorOld2 = new Color(IntDelColorOld);

        for(int i = y; ColorOld.equals(ColorOld2); i++) {
            for(int j = x; ColorOld.equals(ColorOld2); j++)
            {
                ColorOld2 = new Color(animacion.getRGB(j+1, i));
                putPixel(j, i, c);
            }
            ColorOld2 = new Color(animacion.getRGB(x-1, i));
            for(int j = x; ColorOld.equals(ColorOld2); j--)
            {
                ColorOld2 = new Color(animacion.getRGB(j-1, i));
                putPixel(j, i, c);
            }
            ColorOld2 = new Color(animacion.getRGB(x, i+1));
        }

        ColorOld2 = new Color(animacion.getRGB(x, y-1));
        for(int i=y-1; ColorOld.equals(ColorOld2); i--) {
            for(int j = x; ColorOld.equals(ColorOld2); j++)
            {
                ColorOld2 = new Color(animacion.getRGB(j+1, i));
                putPixel(j, i, c);
            }
            ColorOld2 = new Color(animacion.getRGB(x-1, i));
            for(int j = x; ColorOld.equals(ColorOld2); j--)
            {
                ColorOld2 = new Color(animacion.getRGB(j-1, i));
                putPixel(j, i, c);
            }
            ColorOld2 = new Color(animacion.getRGB(x,i-1));
        }
    }



    public void lineaPuntoM(int x1, int y1, int x2, int y2, Color c){
        float x, y, dx, dy, incx = 1, incy = 1, incE, incNE, p = 0;
        x = x1;
        y = y1;
        dx = x2 - x1;
        dy = y2 - y1;

        if(dx < 0){
            dx = -dx;
            incx = -1;
        }
        if(dy < 0){
            dy = -dy;
            incy = -1;
        }
        if(Math.abs(dx) > Math.abs(dy)){
            incE = 2 * dy;
            incNE = 2 * (dy - dx);
            while(x != x2){
                putPixel((int)Math.round(x), (int)Math.round(y), cool);
                x = x + incx;
                if(2 * (p + dy) < dx){
                    p = p + incE;
                }else{
                    p = p + incNE;
                    y = y + incy;
                }}
        }else{
            incE = 2 * dx;
            incNE = 2 * (dx - dy);
            while(y != y2){
                putPixel((int)Math.round(x), (int)Math.round(y), cool);
                y = y + incy;
                if(2 * (p + dx) < dy){
                    p = p + incE;
                }else{
                    p = p + incNE;
                    x = x + incx;
                } } } }

    public void perspectiva(){
        double[] x;
        x=new double[8];
        double[] y;
        y=new double[8];
        for(int i=0;i<4;i++){
            x[i]=round(Nx +(Mtrz[0][i]- Nx)*1.1);
            y[i]=round(Ny +(Mtrz[1][i]- Ny)*1.1);
        }
        for(int i=4;i<8;i++){
            x[i]=round(Nx +(Mtrz[0][i]- Nx)*1);
            y[i]=round(Ny +(Mtrz[1][i]- Ny)*1);
        }
        presp((int)x[0],(int)y[0],(int)x[1],(int)y[1],(int)x[2],(int)y[2],(int)x[3],(int)y[3],(int)x[4],(int)y[4],(int)x[5],(int)y[5],(int)x[6],(int)y[6],(int)x[7],(int)y[7]);
    }
    void presp(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4,int x5, int y5, int x6, int y6, int x7, int y7, int x8, int y8)
    {

        lineaPuntoM(x1,y1,x3,y3,cool);
        lineaPuntoM(x3,y3,x2,y2,cool);
        lineaPuntoM(x1,y1,x4,y4,cool);
        lineaPuntoM(x4,y4,x2,y2,cool);
        lineaPuntoM(x5,y5,x7,y7,cool);
        lineaPuntoM(x7,y7,x6,y6,cool);
        lineaPuntoM(x5,y5,x8,y8,cool);
        lineaPuntoM(x8,y8,x6,y6,cool);
        lineaPuntoM(x1,y1,x5,y5,cool);
        lineaPuntoM(x2,y2,x6,y6,cool);
        lineaPuntoM(x3,y3,x7,y7,cool);
        lineaPuntoM(x4,y4,x8,y8,cool);
        Relleno3d(200,200,cool);
        Relleno3d(200,300,cool);
        Relleno3d(300,400,cool);
        Relleno3d(300,350,cool);
        Relleno3d(300,320,cool);
        Relleno3d(380,320,cool);
        Relleno3d(380,320,cool);
        Relleno3d(445,430,cool);
        Relleno3d(380,240,cool);
        Relleno3d(280,240,cool);
        Relleno3d(240,442,cool);
        Relleno3d(130,186,cool);
        Relleno3d(350,200,cool);


    }


    public void paint(Graphics g) {
        perspectiva();
    }


    @Override
    public void run() {
        while(true){
            try{
                repaint();
                Thread.sleep(80);
            }catch(InterruptedException e){}
        }
    }
}
