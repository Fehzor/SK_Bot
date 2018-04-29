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
import Game.Gear;
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
        //String [] splt = params.split(" ",2);
        
        String gear = WordUtils.capitalizeFully(params.toLowerCase());
        UserData UD = UserData.getUD(ID);
        
        int star = Gear.star.get(gear);
        int matA = Gear.mat1.get(gear);
        int matB = Gear.mat2.get(gear);
        
        int [] mats = UD.materials.getData();
        
        int craftingCost = 50;
        if(star == 3)craftingCost = 200;
        if(star == 4)craftingCost = 400;
        if(star == 5)craftingCost = 800;
        
        int matCost = 20;
        if(star == 3)matCost = 70;
        if(star == 4)matCost = 250;
        if(star == 5)matCost = 800;
        
        if(mats[matA] > matCost && mats[matB] > matCost && 
                                        UD.energy.getData() > craftingCost){
            if(Gear.prev.containsKey(gear)){
                if(UD.gear.getData().contains(Gear.prev.get(gear))){
                    UD.gear.getData().remove(gear);
                    UD.gear.write();
                } else {
                    Launcher.send("You need the prerequisite for that!");
                    return;
                }
            } 

            UD.gear.getData().add(gear);
            UD.gear.write();
            mats[matA] -= matCost;
            mats[matB] -= matCost;
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