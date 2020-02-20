import javax.swing.JOptionPane;
import java.util.ArrayList;
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
    private int seleccionRow;
    private int seleccionColumn;
    private Rectangle[] listaDePosicionlesNegras;
    
    public Checkers(int width){
        listaDeFichas = new Ficha[33];
        listaDePosicionlesNegras = new Rectangle[33];
        zonaConfiguracion = new Rectangle[width][width];
        zonaJuego = new Rectangle[width][width];
        String colorActual =  "black";
        int posicion = 1;
        for (int i = 0; i < width; i++){
            for (int j = 0; j < width; j++  ){
                String color ="";
                if (width%2 == 0 && j ==0 && i>0)color = colorActual;
                else if (colorActual.equals("black"))color = "white";
                else if (colorActual.equals("white"))color = "black";
                zonaConfiguracion[i][j] = new Rectangle(30,30,30*j,30*i,color);
                if (color == "black"){
                    listaDePosicionlesNegras[posicion] = zonaConfiguracion[i][j];
                    posicion+=1;
                }
                zonaJuego[i][j] = new Rectangle(30,30,width*30+40+30*j,30*i,color);
                colorActual = color;
            }
        }
    }
    public void select(int row, int column){
        seleccionRow = row;
        seleccionColumn = column;
        Rectangle casillaSeleccionada = zonaConfiguracion[row][column];
        Ficha fichaSeleccionada = casillaSeleccionada.getFicha();
        fichaSeleccionada.changeColor("red");
    }
    public void shift(boolean top, boolean right){
        Rectangle casillaSeleccionada = zonaConfiguracion[seleccionRow][seleccionColumn];
        Ficha fichaSeleccionada = casillaSeleccionada.getFicha();
        Rectangle casillaAMover = null;
        if (top && right){
            casillaAMover = zonaConfiguracion[seleccionRow+1][seleccionColumn+1];
        }else if (top && !right){
            casillaAMover = zonaConfiguracion[seleccionRow+1][seleccionColumn-1];
        }
        else if (!top && right){
            casillaAMover = zonaConfiguracion[seleccionRow-1][seleccionColumn+1];
        }
        else if (!top && !right){
            casillaAMover = zonaConfiguracion[seleccionRow-1][seleccionColumn-1];
        }
        fichaSeleccionada.moveAXyMoverAY(casillaAMover.getPosicionX(),casillaAMover.getPosicionY());
        casillaAMover.asignarFicha(fichaSeleccionada);fichaSeleccionada.changeColor("blue");
        casillaSeleccionada.asignarFicha(null);
    }
    public void jump(boolean top, boolean right){
        System.out.println("yo jump");
        Rectangle casillaSeleccionada = zonaConfiguracion[seleccionRow][seleccionColumn];
        Rectangle fichaAMatar = null;
        Rectangle casillaALlegar = null;
        if (top && right){
            fichaAMatar = zonaConfiguracion[seleccionRow+1][seleccionColumn+1];
            casillaALlegar = zonaConfiguracion[seleccionRow+2][seleccionColumn+2];
        }else if (top && !right){
            fichaAMatar = zonaConfiguracion[seleccionRow+1][seleccionColumn-1];
            casillaALlegar = zonaConfiguracion[seleccionRow+2][seleccionColumn-2];
        }else if (!top && right){
            fichaAMatar = zonaConfiguracion[seleccionRow-1][seleccionColumn+1];
            casillaALlegar = zonaConfiguracion[seleccionRow-2][seleccionColumn+2];
        }else if (!top && !right){
            fichaAMatar = zonaConfiguracion[seleccionRow-1][seleccionColumn-1];
            casillaALlegar = zonaConfiguracion[seleccionRow-2][seleccionColumn-2];
        }
        if (fichaAMatar.getFicha()!=null){
                fichaAMatar.getFicha().makeInvisible();
                fichaAMatar.asignarFicha(null);
                casillaSeleccionada.getFicha().moveAXyMoverAY(casillaALlegar.getPosicionX(),casillaALlegar.getPosicionY());
                casillaALlegar.asignarFicha(casillaSeleccionada.getFicha());
                casillaALlegar.getFicha().changeColor("blue");
        }
    }
    public void move(String notacion){
        boolean top = true;
        ArrayList<Integer> move = passToStringToInt(notacion);
        boolean right = true;
        //right = move(move.get(0),move.get(1));
        //if (move.get(0) > move.get(1))top = false;
        //else top = true;
        //shift(top,right);
        for (int i = 0; (int) i<(move.size()/2); i++){
            if(i%2 == 0){
                if (move.get(i) > move.get(i+1)) top = false;
                else top = true;
                jump(move.get(i),move.get(i+1));
            }
            System.out.println("yo"+" "+top);
            jump(top,right);
        }
    }
    
    public void makeVisible(){
        for (int i = 0; i < zonaConfiguracion.length; i++){
            for (int j = 0; j < zonaConfiguracion.length; j++){
                zonaConfiguracion[i][j].makeVisible();
                zonaJuego[i][j].makeVisible();
            }
        }
    }
    public void add(int[][] men){
        for (int i = 0; i< men.length; i++){
            Rectangle asignarFicha = zonaConfiguracion[men[i][0]][men[i][1]];
            //Rectangle asignarFicha2 = listaDePosicionlesNegras
            if (asignarFicha.esColocarFicha()){
                listaDeFichas[i] = new Ficha(asignarFicha.getPosicionX(),asignarFicha.getPosicionY());
                listaDeFichas[i].makeVisible();
                asignarFicha.asignarFicha(listaDeFichas[i]);
            }else{
                JOptionPane.showMessageDialog(null,"ya hay una ficha en esa posición");
            }   
        }
    }
    public void add(boolean king, int row, int column){
        Rectangle asignarFicha = zonaConfiguracion[row][column];
        if (asignarFicha.esColocarFicha()){
            listaDeFichas[row] = new Ficha(asignarFicha.getPosicionX(),asignarFicha.getPosicionY());
            listaDeFichas[row].makeVisible();
            asignarFicha.asignarFicha(listaDeFichas[row]);
        }else{
            JOptionPane.showMessageDialog(null,"ya hay una ficha en esa posición");
        }
    }
    public void remove(int row, int column){
        zonaConfiguracion[row][column].getFicha().makeInvisible();
        zonaConfiguracion[row][column].asignarFicha(null);
    }
    public void remove(int[][] pieces){
        for(int i = 0; i< pieces.length; i++){
            remove(pieces[i][0],pieces[i][1]);
        }
    }
    private ArrayList<Integer> passToStringToInt(String notacion){
        ArrayList<Integer> move;
        move = new ArrayList<Integer>();
        String[] jumps = notacion.split("x");
        String[] shift = notacion.split("-");
        //inicio de convertir String a int
        char letra = notacion.charAt(1);
        char letra2 = notacion.charAt(2);
        if (letra == '-' || letra2 == '-'){
            move.add(Integer.parseInt(shift[0]));
            move.add(Integer.parseInt(shift[1]));
        }else{
            for(int i = 0; i< jumps.length; i++){
                 move.add(Integer.parseInt(jumps[i]));
            }
        }
        return move;
        //fin de convertir String a int
    }
    private int getPositionRectangleY(int row, int column){
        Rectangle posicionY = zonaConfiguracion[row][column];
        int y = posicionY.getPosicionY();
        return y;
    }
    private boolean move(int shift, int shift2){
        boolean right;
        double decimal = (double)shift/4;
        double decimal2 = decimal;
        int entero = (int)decimal2;
        if ( decimal - entero != 0){
            if (entero%2 == 0){
                System.out.println("entreyo"+" "+decimal+" "+entero);
                if(shift-shift2 == -4 || shift-shift2 == 4)right = false;
                else right = true;
            }else{
                System.out.println("entreyo"+" "+decimal+" "+entero);
                if(shift-shift2 == -4 || shift-shift2 == 4)right = true;
                else right = false;
            }
        }else{
            System.out.println("entre2"+" "+decimal+" "+entero);
            entero -=1;
            if (entero%2 == 0){
                if(shift-shift2 == -4 || shift-shift2 == 4)right = false;
                else right = true;
            }else{
                if(shift-shift2 == -4 || shift-shift2 == 4)right = true;
                else right = false;
            }
        }
        return right;
    }
    private boolean jump(int jump, int jump2){
        System.out.println("yo");
        boolean right = true;
        if (jump - jump2 == -9 || jump - jump2 == 7){
            right = true;
        }
        else if (jump -jump2 == 9 || jump - jump2 == -7){
            right = false;
        }
        System.out.println("right"+" "+right);
        return right;
    }
}
