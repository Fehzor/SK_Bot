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
    public static final int PROTO_EFFECT = 5;
    public static final int GIVE_AWAY_MATS = 6;
    public static final int AUTODEPOSIT_MINERALS = 7;
    public static final int AUTODEPOSIT_MATERIALS = 8;
    public static final int AUTOSTEAL_MATERIALS = 9;
    public static final int START_EARLIER = 10;
    public static final int START_EARLIER_MATS = 11;
    
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
        
        if(effect == PROTO_EFFECT){
            UD.minerals.getData()[0] += 1;
            UD.minerals.getData()[1] += 1;
            UD.minerals.getData()[2] += 1;
            UD.minerals.getData()[3] += 1;
            UD.minerals.getData()[4] += 1;
            UD.minerals.write();
            
            UD.crowns.append(-15);
        }
        
        if(effect == GIVE_AWAY_MATS){
            int ran = oRan.nextInt(10);
            if(UD.materials.getData()[ran] < param1){
                return;
            }
            UD.materials.getData()[ran]-=param1;
            UD.materials.write();
            UserData other = UserData.getUD(G.activeUsers.get(oRan.nextInt(G.activeUsers.size())));
            other.materials.getData()[ran]+=param1;
            
            UD.crowns.append(param2);
        }
        
        if(effect == AUTODEPOSIT_MATERIALS){
            int ran = oRan.nextInt(10);
            if(UD.materials.getData()[ran] < param1){
                return;
            }
            UD.materials.getData()[ran]-=param1;
            UD.materials.write();
            
            G.minerals.getData()[0] += param1*5;
            G.minerals.getData()[1] += param1*5;
            G.minerals.getData()[2] += param1*5;
            G.minerals.getData()[3] += param1*5;
            G.minerals.getData()[4] += param1*5;
            G.minerals.write();
        }
        
        if(effect == AUTOSTEAL_MATERIALS){
            int ran = oRan.nextInt(10);
            if(G.minerals.getData()[0] < param1 ||
                G.minerals.getData()[1] < param1 ||
                G.minerals.getData()[2] < param1 ||
                G.minerals.getData()[3] < param1 ||
                G.minerals.getData()[4] < param1){
                return;
            }
            UD.materials.getData()[ran]-=param1*3;
            UD.materials.write();
            
            
            
            G.minerals.getData()[0] -= param1;
            G.minerals.getData()[1] -= param1;
            G.minerals.getData()[2] -= param1;
            G.minerals.getData()[3] -= param1;
            G.minerals.getData()[4] -= param1;
            G.minerals.write();
        }
        
        if(effect == AUTODEPOSIT_MINERALS){
            for(int i = 0; i < 5; ++i){
                if(UD.minerals.getData()[i] >= param1){
                    UD.minerals.getData()[i] -= param1;
                    G.minerals.getData()[i] += param1;
                    G.minerals.write();
                    UD.minerals.write();
                }
            }
        }
        
        if(effect == START_EARLIER){
            Long ID = Long.parseLong(UD.ID);
            
            if(Mission.knights.getData().contains(ID)){
                Long start = Mission.start.getData().get(ID);
                
                start = start - param1;
                
                Mission.start.getData().put(ID, start);
                Mission.start.write();
            }
        }
        
        if(effect == START_EARLIER_MATS){
            if(UD.materials.getData()[param2] < param3){
                return;
            } else {
                UD.materials.getData()[param2] -= param3;
                UD.materials.write();
            }
            
            Long ID = Long.parseLong(UD.ID);
            
            if(Mission.knights.getData().contains(ID)){
                Long start = Mission.start.getData().get(ID);
                
                start = start - param1;
                
                Mission.start.getData().put(ID, start);
                Mission.start.write();
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
        
        if(effect == GIVE_AWAY_MATS){
            return "Gives away "+param1+" mats in exchange for "+param2+" crowns.";
        }
        
        if(effect == AUTODEPOSIT_MINERALS){
            return "Automatically deposits "+param1+" of each mineral into the gate you chat in!";
        }
        
        if(effect == AUTODEPOSIT_MATERIALS){
            return "Automatically deposits "+param1+" random material as "+
                    param1*5+" of each mineral into the gate you chat in!";
        }
        
        if(effect == START_EARLIER){
            return "Takes "+(param1/1000)+" seconds off of your current mission.";
        }
        
        if(effect == START_EARLIER_MATS){
            return "Takes "+(param1/1000)+" seconds off of your current mission. "
                    + "Also lose "+param3+" "+Constants.getMaterialName(param2)+" materials.";
        }
        
        return "Weapon Effect Unknown";
    }
}
