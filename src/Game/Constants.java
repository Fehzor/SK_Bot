/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

/**
 *
 * @author FF6EB4
 */
public class Constants {

    /*
    0 = red + green = beast
    1 = red + blue = gremlin
    2 = red + yellow = fire
    3 = red + purple = fiend
    4 = green + blue = freeze
    5 = green + yellow = slime
    6 = green + purple = poison
    7 = blue + yellow = construct
    8 = blue + purple = shock
    9 = purple + yellow = undead
    */
    public static int mineralsToMaterial(int a, int b){
        if(a > b){
            int temp = a;
            a = b;
            b = temp;
        }
        
        if(a == 0){// 0 - 3
            return b-1;
        }
        
        if(a == 1){// 4 - 6
            return 2 + b;
        }
        
        if(a == 2){ // 7, 8
            return 4 + b;
        }
        
        return 9;
    }
    
    public static final int BEAST = 0;
    public static final int GREMLIN = 1;
    public static final int FIRE = 2;
    public static final int FIEND = 3;
    public static final int FREEZE = 4;
    public static final int SLIME = 5;
    public static final int POISON = 6;
    public static final int CONSTRUCT = 7;
    public static final int SHOCK = 8;
    public static final int UNDEAD = 9;
    public static String getMaterialName(int mat){
        switch (mat){
            case 0:
                return "Beast";
            case 1:
                return "Gremlin";
            case 2: 
                return "Fire";
            case 3:
                return "Fiend";
            case 4:
                return "Freeze";
            case 5:
                return "Slime";
            case 6:
                return "Poison";
            case 7:
                return "Construct";
            case 8:
                return "Shock";
            case 9:
                return "Undead";
        }
        return "NOT A MATERIAL";
    }
    
    public static String getMaterialName(int a, int b){
        return getMaterialName(mineralsToMaterial(a,b));
    }
}
