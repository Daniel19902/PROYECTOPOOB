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
    private static int seleccionRow;
    private static int seleccionColumn;
    private Rectangle[] listaDePosicionlesNegras;
    private char movimiento;
    /**
     * Checkers realiza el tablero de configuracion y de juego
     * @param width un entero para determinar el tamaño del tablero (width*width)
     */
   
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
    /**
     * selecciona una ficha del tablero
     * @param row fila del tablero
     * @param column columna del tablero
     */
    public void select(int row, int column){
        seleccionRow = row;
        seleccionColumn = column;
        Rectangle casillaSeleccionada = zonaConfiguracion[row][column];
        Ficha fichaSeleccionada = casillaSeleccionada.getFicha();
        fichaSeleccionada.changeColor("red");
    }
    /**
     * realiza un movimiento en diagonal
     * @param top si top == false la ficha va asi arriva y si no va asi abajo
     * @param right si right == false la ficha va asi la izquierda si no va asi la derecha
     */
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
    /**
     * realiza saltos que elimina fichas del juego
     * @param top si top == false la ficha va asi arriva y si no va asi abajo
     * @param right si right == false la ficha va asi la izquierda si no va asi la derecha
     */
    public void jump(boolean top, boolean right){
        System.out.println("yo jump");
        Rectangle casillaSeleccionada = zonaConfiguracion[seleccionRow][seleccionColumn];
        Rectangle fichaAMatar = null;
        Rectangle casillaALlegar = null;
        if (top && right){
            fichaAMatar = zonaConfiguracion[seleccionRow+1][seleccionColumn+1];
            casillaALlegar = zonaConfiguracion[seleccionRow+2][seleccionColumn+2];
            seleccionRow = seleccionRow+2;seleccionColumn = seleccionColumn+2;
        }else if (top && !right){
            fichaAMatar = zonaConfiguracion[seleccionRow+1][seleccionColumn-1];
            casillaALlegar = zonaConfiguracion[seleccionRow+2][seleccionColumn-2];
            seleccionRow = seleccionRow+2;seleccionColumn = seleccionColumn-2;
        }else if (!top && right){
            fichaAMatar = zonaConfiguracion[seleccionRow-1][seleccionColumn+1];
            casillaALlegar = zonaConfiguracion[seleccionRow-2][seleccionColumn+2];
            seleccionRow = seleccionRow-2;seleccionColumn = seleccionColumn+2;
        }else if (!top && !right){
            fichaAMatar = zonaConfiguracion[seleccionRow-1][seleccionColumn-1];
            casillaALlegar = zonaConfiguracion[seleccionRow-2][seleccionColumn-2];
            seleccionRow = seleccionRow-2;seleccionColumn = seleccionColumn-2;
        }
        if (fichaAMatar.getFicha()!=null){
                fichaAMatar.getFicha().makeInvisible();
                fichaAMatar.asignarFicha(null);
                System.out.println(casillaSeleccionada.getFicha());
                casillaSeleccionada.getFicha().moveAXyMoverAY(casillaALlegar.getPosicionX(),casillaALlegar.getPosicionY());
                casillaALlegar.asignarFicha(casillaSeleccionada.getFicha());
                casillaSeleccionada.asignarFicha(null);
                casillaALlegar.getFicha().changeColor("blue");
            }
    }
    /**
     * es la combinacion del shift y el jump
     * @param notacion representa las posciones y el tipo de movimiento que se va arelaizar
     */
    
    public void move(String notacion){
        boolean top = true;
        ArrayList<Integer> move = passToStringToInt(notacion);
        boolean right = true;
        if (this.movimiento == '-'){
            right = move(move.get(0),move.get(1));
            if (move.get(0) > move.get(1))top = false;
            else top = true;
            shift(top,right);
        }
        else{
            for (int i = 0; (int) i<(move.size()-1); i++){
                if (move.get(i) > move.get(i+1)) top = false;
                else top = true;
                right = jump(move.get(i),move.get(i+1));
                System.out.println("yo"+" "+top);
                jump(top,right);
            }
        }
    }
    /**
     * ase el tablero configuracion y juego visible
     */
    public void makeVisible(){
        for (int i = 0; i < zonaConfiguracion.length; i++){
            for (int j = 0; j < zonaConfiguracion.length; j++){
                zonaConfiguracion[i][j].makeVisible();
                zonaJuego[i][j].makeVisible();
            }
        }
    }
    /**
     * añade fichas 
     * @param men una matriz de posiciones
     */
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
    /**
     * añade una ficha "rey" o "reina"
     * @param king si king es false es rey y si no es reina
     * @param row una fila del tablero
     * @param coulm una columna del tablero
     */
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
    /**
     * remoeve una ficha del tablero
     * @param row una fila del tablero
     * @param coulm una columna del tablero
     */
    public void remove(int row, int column){
        zonaConfiguracion[row][column].getFicha().makeInvisible();
        zonaConfiguracion[row][column].asignarFicha(null);
    }
    /**
     * remueve fichas del tablero
     * @param pieces una matriz de posiciones
     */
    public void remove(int[][] pieces){
        for(int i = 0; i< pieces.length; i++){
            remove(pieces[i][0],pieces[i][1]);
        }
    }
    /**
     * pasa de String a entero
     * @param notacion una cadena para covertir de String a entero
     */
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
        if (letra == '-' || letra == 'x') this.movimiento = letra;
            else this.movimiento = letra2;
        return move;
        //fin de convertir String a int
    }
    /**
     * determina el movimiento shift
     * @param shift un entero que representa una cuadrado del tablero
     * @param shift2 un entero que representa una cuadrado del tablero
     */
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
    /**
     * determina el movimiento jump
     * @param jump un entero que representa una cuadrado del tablero
     * @param jump2 un entero que representa una cuadrado del tablero
     */
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
