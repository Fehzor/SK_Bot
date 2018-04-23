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
public class Gear {
    public static ArrayList<String> publicGear = new ArrayList<>();
    public static ArrayList<String> secrets = new ArrayList<>();
    
    public static HashMap<String,String> description = new HashMap<>();
    public static HashMap<String,Integer> star = new HashMap<>();
    
    public static final int WEAPON = 0;
    public static final int HELM = 1;
    public static final int ARMOR = 2;
    public static final int SHIELD = 3;
    public static HashMap<String,Integer> type = new HashMap<>();
    
    public static HashMap<String,Integer> effect = new HashMap<>();
    public static HashMap<String,Integer> param1 = new HashMap<>();
    public static HashMap<String,Integer> param2 = new HashMap<>();
    public static HashMap<String,Integer> param3 = new HashMap<>();
    
    public static HashMap<String,Integer> mat1 = new HashMap<>();
    public static HashMap<String,Integer> mat2 = new HashMap<>();
    
    public static HashMap<String,String> prev = new HashMap<>();
    
    public static HashMap<String,Integer> damageType = new HashMap<>();
    public static final int NONE = -1;
    public static final int NORMAL = 0;
    public static final int ELEMENTAL = 1;
    public static final int PIERCING = 2;
    public static final int SHADOW = 3;
    
    public static void loadAll(){
        
        hidden("Proto Sword",5,0,0,0,0,WEAPON);
        hidden("Proto Gun",5,0,0,0,0,WEAPON);
        hidden("Proto Bomb",5,0,0,0,0,WEAPON);
        hidden("Proto Shield",5,0,0,0,0,SHIELD);
        hidden("Spiral Culet",5,0,0,0,0,ARMOR);
        hidden("Spiral Tailed Helm",5,0,0,0,0,HELM);
        
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
        
        int [] f = new int[]{
            0,
            0,
            0,
            0
        };
        
        line(2,a,b,c,d,e,f, CONSTRUCT, FIRE,WEAPON);
        
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
        
        f = new int[]{
            0,
            0,
            0
        };
        
        line(3,a,b,c,d,e,f, FIRE,UNDEAD,WEAPON);
        
        ////////
        //////VALIANCE
        ////////
        
        a = new String[]{
            "Blaster",
            "Super Blaster",
            "Master Blaster",
            "Valiance"
        };
        
        b = new int[]{
            6,
            6,
            6,
            6
        };
        
        c = new int[]{
            1,
            2,
            3,
            4
        };
        
        d = new int[]{
            100,
            200,
            300,
            400
        };
        
        f = new int[]{
            0,0,0,0
        };
        
        line(2,a,b,c,d,e,f,GREMLIN,CONSTRUCT,WEAPON);
        
        
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
        
        line(2,a,b,c,d,e,f,SHOCK,GREMLIN,WEAPON);
        
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
        
        line(2,a,b,c,d,e,f,UNDEAD,SLIME,WEAPON);
        
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
        
        f = new int[]{
            2,2,2,2
        };
        
        line(2,a,b,c,d,e,f,BEAST,SLIME,WEAPON);
        
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
        
        line(2,a,b,c,d,e,f,UNDEAD,FIRE,WEAPON);
        
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
        
        f = new int[]{
            3,3,3,3
        };
        
        line(2,a,b,c,d,e,f,UNDEAD,FIEND,WEAPON);
        
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
        
        line(2,a,b,c,d,e,f,SLIME,FIEND,WEAPON);
        
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
        
        f = new int[]{
            1,1,1,1
        };
        
        line(2,a,b,c,d,e,f,UNDEAD,CONSTRUCT,WEAPON);
        
        a = new String[]{
            "Autogun",
            "Pepperbox",
            "Fiery Pepperbox",
            "Volcanic Pepperbox"
        };
        
        b = new int[]{
            7,7,7,7
        };
        
        c = new int[]{
            1,2,3,4
        };
        
        d = empty;
        
        f = new int[]{
            0,0,0,0
        };
        
        line(2,a,b,c,d,e,f,FIRE,CONSTRUCT,WEAPON);
        
        a = new String[]{
            "Autogun",
            "Needle Shot",
            "Strike Needle",
            "Blitz Needle"
        };
        
        b = new int[]{
            8,8,8,8
        };
        
        c = new int[]{
            0,2,3,5
        };
        
        d = empty;
        
        f = new int[]{
            0,2,2,2
        };
        
        line(2,a,b,c,d,e,f,FIRE,CONSTRUCT,WEAPON);
        
        a = new String[]{
            "Autogun",
            "Toxic Needle",
            "Blight Needle",
            "Plague Needle"
        };
        
        b = new int[]{
            8,8,8,8
        };
        
        c = new int[]{
            0,1,2,4
        };
        
        d = empty;
        
        f = new int[]{
            0,2,2,2
        };
        
        line(2,a,b,c,d,e,f,CONSTRUCT,SLIME,WEAPON);
        
        a = new String[]{
            "Autogun",
            "Dark Chaingun",
            "Black Chaingun",
            "Grim Repeater"
        };
        
        b = new int[]{
            9,9,9,9
        };
        
        c = new int[]{
            0,1,2,3
        };
        
        d = empty;
        
        f = new int[]{
            0,3,3,3
        };
        
        line(2,a,b,c,d,e,f,FIEND,CONSTRUCT,WEAPON);
        
        a = new String[]{
            "Ottogun",
            "Noodle Shat",
            "Stroke Noodle",
            "Blitz Noodle"
        };
        
        b = new int[]{
            8,8,8,8
        };
        
        c = new int[]{
            50,200,400,800
        };
        
        d = empty;
        
        f = new int[]{
            0,2,2,2
        };
        
        line(2,a,b,c,d,e,f,CONSTRUCT,SLIME,WEAPON);
        publicGear.remove("Ottogun");
        
        a = new String[]{
            "Cutter",
            "Striker",
            "Vile Striker",
            "Dread Venom Striker"
        };
        
        b = new int[]{
            10,10,10,10
        };
        
        c = new int[]{
            7500,15000,22500,30000
        };
        
        d = empty;
        
        f = new int[]{
            0,0,0,0
        };
        
        line(2,a,b,c,d,e,f,GREMLIN,POISON,WEAPON);
        
        a = new String[]{
            "Striker",
            "Hunting Blade",
            "Wild Hunting Blade"
        };
        
        b = new int[]{
            1,1,1
        };
        
        c = new int[]{
            BEAST,BEAST,BEAST
        };
        
        d = new int[]{
            0,3,4
        };
        
        f = new int[]{
            0,0,0
        };
        
        line(3,a,b,c,d,e,f,GREMLIN,BEAST,WEAPON);
        
        
        a = new String[]{
            "Brandish",
            "Fireburst Brandish",
            "Blazebrand",
            "Combuster"
        };
        
        b = new int[]{
            10,11,11,11
        };
        
        c = new int[]{
            5000,30000,45000,60000
        };
        
        d = new int[]{
            FIRE,FIRE,FIRE,FIRE
        };
        
        e = new int[]{
            1,2,3,4
        };
        
        f = new int[]{
            0,1,1,1
        };
        
        line(2,a,b,c,d,e,f,GREMLIN,FIRE,WEAPON);
        
        a = new String[]{
            "Brandish",
            "Iceburst Brandish",
            "Blizzbrand",
            "Glacius"
        };
        
        b = new int[]{
            10,11,11,11
        };
        
        c = new int[]{
            5000,30000,45000,60000
        };
        
        d = new int[]{
            FREEZE,FREEZE,FREEZE,FREEZE
        };
        
        e = new int[]{
            1,2,3,4
        };
        
        f = new int[]{
            0,1,1,1
        };
        
        line(2,a,b,c,d,e,f,GREMLIN,FREEZE,WEAPON);
        
        a = new String[]{
            "Brandish",
            "Shockburst Brandish",
            "Boltbrand",
            "Voltedge"
        };
        
        b = new int[]{
            10,11,11,11
        };
        
        c = new int[]{
            5000,30000,45000,60000
        };
        
        d = new int[]{
            SHOCK,SHOCK,SHOCK,SHOCK
        };
        
        e = new int[]{
            1,2,3,4
        };
        
        f = new int[]{
            0,1,1,1
        };
        
        line(2,a,b,c,d,e,f,GREMLIN,SHOCK,WEAPON);
        
        a = new String[]{
            "Brandish",
            "Nightblade",
            "Silent Nightblade",
            "Acheron"
        };
        
        b = new int[]{
            10,11,11,11
        };
        
        c = new int[]{
            5000,30000,45000,60000
        };
        
        d = new int[]{
            FIEND,FIEND,FIEND,FIEND
        };
        
        e = new int[]{
            1,2,3,4
        };
        
        f = new int[]{
            0,3,3,3
        };
        
        line(2,a,b,c,d,e,f,GREMLIN,FIEND,WEAPON);
        
        a = new String[]{
            "Brandish",
            "Cautery Sword",
            "Advanced Cautery Sword",
            "Amputator"
        };
        
        b = new int[]{
            10,1,1,1
        };
        
        c = new int[]{
            SLIME,SLIME,SLIME,SLIME
        };
        
        d = new int[]{
            1,2,3,4
        };
        
        e = empty;
        
        f = new int[]{
            0,0,0,0
        };
        
        line(2,a,b,c,d,e,f,GREMLIN,FIEND,WEAPON);
        
        a = new String[]{
            "Nightblade",
            "Noisy Nightblade",
            "Cacophony"
        };
        
        b = new int[]{
            11,11,11
        };
        
        c = new int[]{
            30000,45000,60000
        };
        
        d = new int[]{
            FIEND,UNDEAD,UNDEAD
        };
        
        e = new int[]{
            2,3,4
        };
        
        f = new int[]{
            3,3,3
        };
        
        line(3,a,b,c,d,e,f,GREMLIN,UNDEAD,WEAPON);
        
        
        /////////////ARMOR/////////////////
        
        a = new String[]{
            "Magic Cloak",
            "Elemental Cloak",
            "Miracle Cloak",
            "Grey Feather Mantle"
        };
        
        b = new int[]{
            6,6,6,6
        };
        
        c = new int[]{
            1,2,2,2
        };
        
        d = new int[]{
            100,200,250,300
        };
        
        e = empty;
        
        f = new int[]{
            0,0,0,0
        };
        
        line(2,a,b,c,d,e,f,UNDEAD,BEAST,ARMOR);
        
        a = new String[]{
            "Magic Hood",
            "Elemental Hood",
            "Miracle Hood",
            "Grey Feather Cowl"
        };
        
        b = new int[]{
            6,6,6,6
        };
        
        c = new int[]{
            1,2,2,2
        };
        
        d = new int[]{
            100,200,250,300
        };
        
        e = empty;
        
        f = new int[]{
            0,0,0,0
        };
        
        line(2,a,b,c,d,e,f,UNDEAD,BEAST,HELM);
        
        a = new String[]{
            "Owlite Shield",
            "Horned Owlite Shield",
            "Wise Owlite Shield",
            "Grey Owlite Shield"
        };
        
        b = new int[]{
            6,6,6,6
        };
        
        c = new int[]{
            1,2,2,2
        };
        
        d = new int[]{
            100,200,250,300
        };
        
        e = empty;
        
        f = new int[]{
            0,0,0,0
        };
        
        line(2,a,b,c,d,e,f,UNDEAD,BEAST,SHIELD);
        
        a = new String[]{
            "Spiral Plate Helm",
            "Boosted Plate Helm",
            "Heavy Plate Helm",
            "Ironmight plate helm"
        };
        
        b = new int[]{
            6,6,6,6
        };
        
        c = new int[]{
            2,4,4,4
        };
        
        d = new int[]{
            200,400,500,600
        };
        
        e = empty;
        
        f = new int[]{
            0,0,0,0
        };
        
        line(2,a,b,c,d,e,f,FIRE,SLIME,HELM);
        
        a = new String[]{
            "Spiral Plate Mail",
            "Boosted Plate Mail",
            "Heavy Plate Mail",
            "Ironmight Plate Mail"
        };
        
        b = new int[]{
            6,6,6,6
        };
        
        c = new int[]{
            2,4,4,4
        };
        
        d = new int[]{
            200,400,500,600
        };
        
        e = empty;
        
        f = new int[]{
            0,0,0,0
        };
        
        line(2,a,b,c,d,e,f,FIRE,SLIME,ARMOR);
        
        a = new String[]{
            "Plate Shield",
            "Boosted Plate Shield",
            "Heavy Plate Shield",
            "Ironmight Plate Shield"
        };
        
        b = new int[]{
            0,0,0,0
        };
        
        c = new int[]{
            50,100,150,200
        };
        
        d = empty;
        
        e = empty;
        
        f = new int[]{
            0,0,0,0
        };
        
        line(2,a,b,c,d,e,f,FIRE,BEAST,SHIELD);
        
    }
    
    private static void line(int starbase, String [] main, int [] effects, 
            int [] param1s, int [] param2s, int [] param3s, int [] damType,
            int mata, int matb, int t){
        
        for(int i = 0; i < main.length; ++i){
            String add = main[i];
            if(!publicGear.contains(add)){
                publicGear.add(add);
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
                damageType.put(add,damType[i]);
                type.put(add, t);
            }
        }
    }
    
    public static void hidden(String name, int e, int p1, int p2, int p3, int dam, int t){
        secrets.add(name);
        description.put(name, Effects.describe(e,p1,p2,p3));
        effect.put(name,e);
        param1.put(name, p1);
        param2.put(name, p2);
        param3.put(name, p3);
        damageType.put(name, dam);
        type.put(name,t);
        star.put(name, 1);
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
