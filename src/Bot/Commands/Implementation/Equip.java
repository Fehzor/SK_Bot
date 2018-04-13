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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author FF6EB4
 */
public class Equip extends Command{
    
    public Equip(){
        this.category = 2;
        this.signature = new String[]{"!equip","!eq"};
        this.description = "Equip a weapon to slot A, B or C. E.G.- !equip A Dark Retribution";
    }
    
    public void execute(String params, long ID){
        String [] splt = params.split(" ",2);
        
        String wep = WordUtils.capitalizeFully(splt[1].toLowerCase());
        UserData UD = UserData.getUD(ID);
        if(!UD.weapons.getData().contains(wep)){
            Launcher.send("You don't have or spelled incorrectly \""+wep+"\"");
            return;
        }
        
        if(UD.A.getData().equals(wep)){
            Launcher.send("Weapon is already equipped in slot A! (cannot equip duplicates)");
            return;
        }
        if(UD.B.getData().equals(wep)){
            Launcher.send("Weapon is already equipped in slot B! (cannot equip duplicates)");
            return;
        }
        if(UD.C.getData().equals(wep)){
            Launcher.send("Weapon is already equipped in slot C! (cannot equip duplicates)");
            return;
        }
        
        if(splt[0].toLowerCase().equals("a")){
            UD.A.writeData(wep);
            Launcher.send(wep+" has been equipped to slot A");
        }
        
        if(splt[0].toLowerCase().equals("b")){
            UD.B.writeData(wep);
            Launcher.send(wep+" has been equipped to slot B");
        }
        
        if(splt[0].toLowerCase().equals("c")){
            UD.C.writeData(wep);
            Launcher.send(wep+" has been equipped to slot C");
        }
    }
}