/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Bot.Fields.UserData;
import static Bot.SuperRandom.oRan;
import Game.Gate;
import sx.blah.discord.handle.obj.IChannel;

/**
 *
 * @author FF6EB4
 */
public class Effects {
    
    public static final int CROWNS_EFFECT = 0;
    public static final int MATERIAL_EFFECT = 1;
    public static final int MINERAL_EFFECT = 2;
    public static final int EXTRA_MATERIALS = 3;
    public static final int RANDOM_MINERALS = 4;
    
    public static void act(Gate G, UserData UD, int effect, int param1, int param2, int param3){
        if(effect == CROWNS_EFFECT){
            UD.crowns.append(param1);
        }
        
        if(effect == MATERIAL_EFFECT){
            UD.materials.getData()[param1] += param2;
            UD.materials.write();
        }
        
        if(effect == MINERAL_EFFECT){
            UD.minerals.getData()[param1] += param2;
            UD.minerals.write();
        }
        
        if(effect == RANDOM_MINERALS){
            UD.minerals.getData()[oRan.nextInt(5)] += param1;
            UD.minerals.write();
        }
        
        if(effect == EXTRA_MATERIALS){
            for(int i = 0; i < param1; ++i){
                G.giveMaterials();
            }
        }
    }
    
    public static String describe(int effect, int param1, int param2, int param3){
        if(effect == CROWNS_EFFECT){
            return "Grants the user "+param1+" crowns each tick.";
        }
        
        if(effect == MATERIAL_EFFECT){
            return "Grants the user "+param2+" "+Constants.getMaterialName(param1)+" material(s) each tick.";
        }
        
        if(effect == MINERAL_EFFECT){
            return "Grants the user "+param2+" "+Constants.getMineralName(param1)+" minerals each tick.";
        }
        
        if(effect == RANDOM_MINERALS){
            return "Grants the user "+param1+" random minerals each tick.";
        }
        
        if(effect == EXTRA_MATERIALS){
            return "Produces materials using minerals an additional "+param1+" times.";
        }
        
        return "Weapon Effect Unknown";
    }
}
