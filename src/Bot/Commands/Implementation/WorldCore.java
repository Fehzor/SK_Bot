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
import Game.UserGate;
import sx.blah.discord.handle.obj.IChannel;

/**
 *
 * @author FF6EB4
 */
public class WorldCore extends Command{
    
    public WorldCore(){
        this.category = -1;
        this.signature = new String[]{"!create"};
        this.description = "Creates a new gate!";
    }
    
    public void execute(String params, long ID){
        try{
            UserData UD = UserData.getUD(ID);
            
            int[] mins = UD.minerals.getData();
            
            String [] splt = params.split(" ");

            int a = Integer.parseInt(splt[0]);
            if(mins[0] < a){
                Launcher.send("NOT ENOUGH RED");
                return;
            }
            int b = Integer.parseInt(splt[1]);
            if(mins[1] < b){
                Launcher.send("NOT ENOUGH GREEN");
                return;
            }
            int c = Integer.parseInt(splt[2]);
            if(mins[2] < c){
                Launcher.send("NOT ENOUGH BLUE");
                return;
            }
            int d = Integer.parseInt(splt[3]);
            if(mins[3] < d){
                Launcher.send("NOT ENOUGH YELLOW");
                return;
            }
            int e = Integer.parseInt(splt[4]);
            if(mins[4] < e){
                Launcher.send("NOT ENOUGH PURPLE");
            }
            
            int total = a+b+c+d+e;
            if(total < 10000){
                Launcher.send("NEED AT LEAST 10K MINERALS");
                return;
            }
        
            if(UD.crowns.getData() < total*10){
                Launcher.send("NEED AT LEAST "+total*10+" CROWNS");
                return;
            }
            
            UD.crowns.append(total*10*-1);
            mins[0] -= a;
            mins[1] -= b;
            mins[2] -= c;
            mins[3] -= d;
            mins[4] -= e;
            UD.minerals.write();
            
            int [] send = new int [] {
                a,b,c,d,e
            };
            
            UserGate UG = new UserGate(send);
        
        }catch (Exception E){
            E.printStackTrace();
        }
    }
}