/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import static Game.Constants.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author FF6EB4
 */
public class Weapons {
    public static ArrayList<String> names = new ArrayList<>();
    public static HashMap<String,String> description = new HashMap<>();
    public static HashMap<String,Integer> star = new HashMap<>();
    
    public static HashMap<String,Integer> effect = new HashMap<>();
    public static HashMap<String,Integer> param1 = new HashMap<>();
    public static HashMap<String,Integer> param2 = new HashMap<>();
    public static HashMap<String,Integer> param3 = new HashMap<>();
    
    public static HashMap<String,Integer> mat1 = new HashMap<>();
    public static HashMap<String,Integer> mat2 = new HashMap<>();
    
    public static HashMap<String,String> prev = new HashMap<>();
    
    public static void loadAll(){
        
        int [] empty = new int[]{
            0,
            0,
            0,
            0
        };
        
        //LEVI
        String [] a = new String[]{
            "Calibur",
            "Tempered Calibur",
            "Ascended Calibur",
            "Leviathan Blade"
        };
        
        int [] b = empty;
        
        int [] c = new int[]{
            50,
            100,
            150,
            200
        };
        
        int [] d = empty;
        int [] e = empty;
        
        line(2,a,b,c,d,e, CONSTRUCT, FIRE);
        
        a = new String[]{
            "Tempered Calibur",
            "Cold Iron Carver",
            "Cold Iron Vanquisher"
        };
        
        b = new int[]{
            0,
            1,
            1
        };
        
        c = new int[]{
            0,
            9,
            9
        };
        
        d = new int[]{
            0,
            1,
            2
        };
        
        line(3,a,b,c,d,e,FIRE,UNDEAD);
        
        ////////
        //////BLAST BOMB
        ////////
        
        a = new String[]{
            "Blast Bomb",
            "Super Blast Bomb",
            "Master Blast Bomb",
            "Nitronome"
        };
        
        b = new int[]{
            3,
            3,
            3,
            3
        };
        
        c = new int[]{
            1,
            2,
            3,
            4
        };
        
        d = empty;
        
        line(2,a,b,c,d,e,SHOCK,GREMLIN);
        
    }
    
    private static void line(int starbase, String [] main, int [] effects, 
            int [] param1s, int [] param2s, int [] param3s,
            int mata, int matb){
        
        for(int i = 0; i < main.length; ++i){
            String add = main[i];
            if(!names.contains(add)){
                names.add(add);

                if(i>0){
                    prev.put(add,main[i-1]);
                }

                effect.put(add,effects[i]);
                param1.put(add,param1s[i]);
                param2.put(add,param2s[i]);
                param3.put(add,param3s[i]);

                star.put(add, i+starbase);
                
                mat1.put(add, mata);
                mat2.put(add, matb);
            }
        }
    }
}
