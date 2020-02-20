
/**
 * Write a description of class Ficha here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Ficha
{
    private Circle ficha;
    private int seleccionRow;
    private int seleccionColumn;
    public Ficha(int x, int y){
        ficha= new Circle(x,y);
    }
    public void setSeleccion(int row, int column){
        this.seleccionRow = row;
        this.seleccionColumn = column;
    }
    public void makeVisible(){
        ficha.makeVisible();
    }
    public void makeInvisible(){
        ficha.makeInvisible();
    }
    public void changeColor(String color){
        ficha.changeColor(color);
    }
    public void moveAXyMoverAY(int moveX,int moveY){
        ficha.moveHorizontal(moveX);
        ficha.moveVertical(moveY);
    }
}
