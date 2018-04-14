/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot.Commands.Implementation;

import Bot.Commands.Command;
import Bot.Commands.CommandParser;
import Bot.Fields.UserData;
import Bot.Launcher;
import static Bot.SuperRandom.oRan;
import Game.Weapons;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author FF6EB4
 */
public class Craft extends Command{
    
    public Craft(){
        this.category = 2;
        this.signature = new String[]{"!craft"};
        this.description = "Craft a weapon! E.G.- !craft Calibur";
    }
    
    public void execute(String params, long ID){
        String [] splt = params.split(" ",2);
        
        String wep = WordUtils.capitalizeFully(splt[1].toLowerCase());
        UserData UD = UserData.getUD(ID);
        
        int star = Weapons.star.get(wep);
        int matA = Weapons.mat1.get(wep);
        int matB = Weapons.mat2.get(wep);
        
        int [] mats = UD.materials.getData();
        
        int craftingCost = 50;
        if(star == 3)craftingCost = 200;
        if(star == 4)craftingCost = 400;
        if(star == 5)craftingCost = 800;
        
        if(mats[matA] > craftingCost && mats[matB] > craftingCost && 
                                        UD.energy.getData() > craftingCost){
            if(Weapons.prev.containsKey(wep)){
                if(UD.weapons.getData().contains(Weapons.prev.get(wep))){
                    UD.weapons.getData().remove(wep);
                    UD.weapons.write();
                } else {
                    Launcher.send("You need the prerequisite for that!");
                    return;
                }
            } 

            UD.weapons.getData().add(wep);
            mats[matA] -= craftingCost;
            mats[matB] -= craftingCost;
            UD.materials.writeData(mats);
            UD.energy.append(-craftingCost);

            Launcher.send("CRAFTING SUCCESS!");
            return;
        } else {
            Launcher.send("Not enough resources!");
            return;
        }
    }
}