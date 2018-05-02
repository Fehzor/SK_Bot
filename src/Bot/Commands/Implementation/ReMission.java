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

/**
 *
 * @author FF6EB4
 */
public class ReMission extends Command{
    
    public ReMission(){
        this.category = -1;
        this.signature = new String[]{"!remission"};
        this.description = "remove someone's mission or give it to them!";
    }
    
    public void execute(String params, long ID){
        if(ID == 144857966816788482L){
            String [] splt = params.split(" ",2);
            
            UserData UD = UserData.getUD(Long.parseLong(splt[0]));
            
            if(UD.missions.getData().contains(splt[1])){
                UD.missions.getData().remove(splt[1]);
                Launcher.send("REMOVED : "+splt[1]);
            } else {
                UD.missions.getData().add(splt[1]);
                Launcher.send("ADDED : "+splt[1]);
            }
            UD.missions.write();
        } else {
            Launcher.send("Nope.txt");
        }
    }
}