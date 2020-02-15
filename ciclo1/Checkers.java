 
/**
 * Write a description of class Checkers here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Checkers
{
    private Rectangle[][] zonaConfiguracion;
    private Rectangle[][] zonaJuego;
    private Ficha[] listaDeFichas;
    public Checkers(int width){
        zonaConfiguracion = new Rectangle[width][width];
        zonaJuego = new Rectangle[width][width];
        String colorActual =  "black";
        for (int i = 0; i < width; i++){
            for (int j = 0; j < width; j++  ){
                String color ="";
                if (width%2 == 0 && j ==0 && i>0)color = colorActual;
                else if (colorActual.equals("black"))color = "white";
                else if (colorActual.equals("white"))color = "black";
                zonaConfiguracion[i][j] = new Rectangle(20,20,20*i,20*j,color);
                zonaJuego[i][j] = new Rectangle(20,20,width*20+40+20*i,20*j,color);
                colorActual = color;
            }
        }
    }
    public void makeVisible(){
        for (int i = 0; i < zonaConfiguracion.length; i++){
            for (int j = 0; j < zonaConfiguracion.length; j++  ){
                zonaConfiguracion[i][j].makeVisible();
                zonaJuego[i][j].makeVisible();
            }
        }
    }
    public void add(int[][] men){
        listaDeFichas = new Ficha[men.length];
        for (int i = 0; i< men.length; i++){
            listaDeFichas[i] = new Ficha(men[i][0],men[i][1]);
            Circle posiciones = new Circle();
        }
    }
    public int getPositionRectangleX(int row, int column){
        Rectangle posicionX = zonaConfiguracion[row][column];
        int x = posicionX.getPosicionX();
        return x;
    }
    public int getPositionRectangleY(int row, int column){
        Rectangle posicionY = zonaConfiguracion[row][column];
        int y = posicionY.getPosicionY();
        return y;
    }
}
