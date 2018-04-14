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
        
        //////////
        ////////Shard Bombs
        /////////
        
        a = new String[]{
            "Shard Bomb",
            "Super Shard Bomb",
            "Heavy Shard Bomb",
            "Deadly Shard Bomb"
        };
        
        b = new int[]{
            2,
            2,
            2,
            2
        };
        
        c = new int[]{
            0,
            0,
            0,
            0
        };
        
        d = new int[]{
            3,
            6,
            9,
            12
        };
        
        line(2,a,b,c,d,e,UNDEAD,SLIME);
        
        a = new String[]{
            "Splinter Bomb",
            "Super Splinter Bomb",
            "Heavy Splinter Bomb",
            "Deadly Splinter Bomb"
        };
        
        b = new int[]{
            2,
            2,
            2,
            2
        };
        
        c = new int[]{
            3,
            3,
            3,
            3
        };
        
        d = new int[]{
            3,
            6,
            9,
            12
        };
        
        line(2,a,b,c,d,e,BEAST,SLIME);
        
        a = new String[]{
            "Splinter Bomb",
            "Sun Shards",
            "Radiant Sun Shards",
            "Scintillating Sun Shards"
        };
        
        b = new int[]{
            4,
            4,
            4,
            4
        };
        
        c = new int[]{
            0,
            7,
            11,
            15
        };
        
        d = empty;
        
        line(2,a,b,c,d,e,UNDEAD,FIRE);
        
        a = new String[]{
            "Dark Matter Bomb",
            "Super Dark Matter Bomb",
            "Heavy Dark Matter Bomb",
            "Deadly Dark Matter Bomb"
        };
        
        b = new int[]{
            2,
            2,
            2,
            2
        };
        
        c = new int[]{
            4,
            4,
            4,
            4
        };
        
        d = new int[]{
            3,
            6,
            9,
            12
        };
        
        line(2,a,b,c,d,e,UNDEAD,FIEND);
        
        a = new String[]{
            "Dark Matter Bomb",
            "Rock Salt Bomb",
            "Ionized Salt Bomb",
            "Shocking Salt Bomb"
        };
        
        b = new int[]{
            2,
            2,
            2,
            2
        };
        
        c = new int[]{
            2,
            2,
            2,
            2
        };
        
        d = new int[]{
            3,
            6,
            9,
            12
        };
        
        line(2,a,b,c,d,e,SLIME,FIEND);
        
        a = new String[]{
            "Crystal Bomb",
            "Super Crystal Bomb",
            "Heavy Crystal Bomb",
            "Deadly Crystal Bomb"
        };
        
        b = new int[]{
            2,
            2,
            2,
            2
        };
        
        c = new int[]{
            1,
            1,
            1,
            1
        };
        
        d = new int[]{
            3,
            6,
            9,
            12
        };
        
        line(2,a,b,c,d,e,UNDEAD,CONSTRUCT);
    }
    
    private static void line(int starbase, String [] main, int [] effects, 
            int [] param1s, int [] param2s, int [] param3s,
            int mata, int matb){
        
        for(int i = 0; i < main.length; ++i){
            String add = main[i];
            if(!names.contains(add)){
                names.add(add);
                description.put(add, Effects.describe(effects[i], 
                                                        param1s[i], 
                                                        param2s[i], 
                                                        param3s[i]));

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
    
    public static String cost(String wep){
        int num = star.get(wep);
        if(num == 2)num = 50;
        if(num == 3)num = 200;
        if(num == 4)num = 400;
        if(num == 5)num = 800;
        
        String ret = "Cost: "+num+" "+Constants.getMaterialName(mat1.get(wep));
        ret+= ", "+num+" "+Constants.getMaterialName(mat2.get(wep));
        ret+= " and "+num+" energy.";
        
        return ret;
    }
}
