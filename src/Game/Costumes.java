/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Bot.Fields.UserData;
import static Bot.SuperRandom.oRan;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author FF6EB4
 */
public class Costumes {
    public static ArrayList<String> costumes = new ArrayList<>();
    public static HashMap<String, Integer> cp = new HashMap<>();
    
    public static void loadCostumes(){
        String [] costumes1 = new String[]{
            "Culet",
            "Flak Jacket",
            "Fur Coat",
            "Cloak",
            "Brigandine",
            "Cuirass",
            "Draped Armor",
            "Scale Mail",
            "Raiment",
            "Crescent Helm",
            "Fur Cap",
            "Pith Helm",
            "Plate Helm",
            "Round Helm",
            "Sallet",
            "Scale Helm",
            "Tailed Helm",
            "Winged Helm",
            "Side Blade",
            "Bomb Bandolier",
            "Crest",
            "Parrying Blade",
            "Plume",
            "Ribbon",
            "Dapper Combo",
            "Power Scanner",
            "Glasses",
            "Com Unit",
            "Mecha Wings",
            "Bolted Vee",
            "Headband",
            "Toupee"
        };
        
        String [] costumes2 = new String[]{
            "Wings",
            "Scarf",
            "Round Shades"
        };
        
        String [] t1 = new String[]{
            "Hunter",
            "Hallow",
            "Frosty",
            "Glacial",
            "Verdant",
            "Dazed",
            "Electric",
            "Vile",
            "Wicked",
            "Ruby",
            "Peridot",
            "Blazing",
            "Slumber",
            "Sapphire",
            "Autumn",
            "Opal",
            "Citrine",
            "Garnet",
            "Amethyst",
            "Aquamarine",
            "Diamond",
            "Pearl",
            "Grey"
        };
        
        String [] t2 = new String[]{
            "Shadow",
            "Surge",
            "Lovely"
        };
        
        crossLoad(t1, costumes1, 1);
        crossLoad(t2, costumes1, 2);
        crossLoad(t1, costumes2, 2);
        crossLoad(t2, costumes2, 3);
        
        add("Flame Aura", 3);
        add("Ghostly Aura", 3);
        add("Twilight Aura", 4);
        add("Twinkle Aura", 3);
        add("Unclean Aura", 4);
        add("Snowfall Aura", 5);
        add("Love Aura",3);
        add("Sprinkle Aura",3);
        add("Sunshine Aura",4);
        add("Green Leafy Aura",4);
        add("Hacked Aura",4);
        add("Haunted Aura", 1);
        add("Fall Leafy Aura", 4);
        add("Polar Leafy Aura", 4);
        add("Gold Leafy Aura", 4);
        add("Baleful Aura", 4);
        add("Aggro Aura", 5);
        add("Orbit Aura",4);
        add("Blossom Aura",6);
        add("Dreadful Aura",5);
        add("Soaked Aura",4);
        add("Grasping Aura",2);
        add("Node Field", 4);
        add("Venomous Aura", 4);
        add("Rose Aura", 4);
        add("Dazed Aura", 4);
        add("Ruby Aura",4);
        add("Peridot Aura",4);
        add("Sapphire Aura",4);
        add("Autumn Aura",6);
        add("Opal Aura",4);
        add("Citrine Aura", 4);
        add("Snipe Aura", 5);
        add("Turquoise Aura", 4);
        add("Garnet Aura", 4);
        add("Amethyst Aura",4);
        add("Aquamarine Aura",4);
        add("Wicked Leafy Aura",4);
        add("Marsh Leafy Aura",4);
        add("Fiery Aura",6);
        add("Permafrost Aura",4);
        add("Black Feathered Aura", 4);
        add("Prismatic Feathered Aura", 5);
        add("Brown Feathered Aura", 4);
        add("Gold Feathered Aura", 4);
        add("Fiery Feathered Aura",5);
        add("Clover Aura",3);
        add("Lucky Aura",4);
        add("Rainbow Aura",5);
        add("Diamond Aura",5);
        add("Emerald Aura",4);
        add("Wild Aura",4);
        add("Garbage Slimed Aura",3);
        
        
        
    }
    
    private static void add(String s, int c){
        costumes.add(s);
        cp.put(s, c);
    }
    
    private static void crossLoad(String [] mod, String [] pieces, int star){
        for(String m : mod){
            for(String p : pieces){
                String nam = m+" "+p;
                add(nam,star);
            }
        }
    }
    
    public static String draw(UserData UD){
        int index = oRan.nextInt(costumes.size());
        String get = costumes.get(index);
        
        UD.costumes.getData().add(get);
        UD.costumes.write();
        UD.CP.append(cp.get(get));
        
        return get;
    }
    
    public static String top10(){
        ArrayList<Integer> nums = new ArrayList<>();
        HashMap<Integer, String> names = new HashMap<>();
        
        for(String S : UserData.IDList.getData()){
            UserData UD = UserData.getUD(Long.parseLong(S));
            nums.add(UD.CP.getData());
            if(names.containsKey(UD.CP.getData())){
                names.put(UD.CP.getData(),names.get(UD.CP.getData())+"+"+UD.name);
            } else {
                names.put(UD.CP.getData(),UD.name);
            }
        }
        
        Collections.sort(nums);
        
        String results = "**TOP 10 BEST LOOKING KNIGHTS ARE: **\n";
        
        int theNum = 1;
        for(int i = nums.size()-1; i >= nums.size() - 11; --i){
            try{
                results+="#"+(theNum)+": "+names.get(nums.get(i))+
                        " ("+nums.get(i)+" CP)\n";
                theNum++;
            }catch(Exception E){}
        }
        return results;
    }
}
