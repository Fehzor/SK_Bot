/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Bot.Fields.Field;
import Bot.Launcher;
import static Bot.SuperRandom.oRan;
import java.util.ArrayList;
import java.util.EnumSet;
import sx.blah.discord.handle.obj.Permissions;

/**
 *
 * @author FF6EB4
 */
public class UserGate extends Gate{
    
    
    public static void loadUGs(){
        ArrayList<Long> IDs = Ugates.getData();
        
        for(long ID : IDs){
            new UserGate(ID);
        }
    }
    public static Field<ArrayList<Long>> Ugates = new Field<>("UG","GATES",new ArrayList<>());
    
    public Field<int[]> toGive;
    
    public UserGate(int [] minerals){
        super(Launcher.client.getGuilds().get(0).createChannel(name()));
        
        this.chan.changeCategory(Launcher.client.getGuilds().get(0).getCategoryByID(440024534565912576L));
        
        toGive = new Field<>("TEMPGATE",""+chan.getLongID(), minerals);
        
        this.minerals.writeData(new int[]{(int)(.7*minerals[0]),(int)(.7*minerals[1]),(int)(.7*minerals[2]),(int)(.7*minerals[3]),(int)(.7*minerals[4])});
        
        Ugates.getData().add(chan.getLongID());
        Ugates.write();
    }
    
    public UserGate(long ID){
        super(Launcher.client.getChannelByID(ID));

        toGive = new Field<>("TEMPGATE",""+chan.getLongID(), null);
    }
    
    public void tick(){
        super.tick();
        super.tick();
        
        int[] giv = toGive.getData();
        int[] mins = minerals.getData();
        
        for(int i = 0; i < 5; ++i){
            if(mins[i] <= 10 && giv[i] >= 100){
                mins[i]+=100;
                giv[i]-=100;
            } else if(mins[i] <= 10){
                mins[i]+=giv[i];
                giv[i]-=0;
            } else if(giv[i] >= 10){
                mins[i]+=10;
                giv[i]-=10;
            } else if(giv[i] >= 1){
                mins[i]+=1;
                giv[i]-=1;
            }
        }
        
        toGive.writeData(giv);
        minerals.writeData(mins);
        
        boolean delete = true;
        for(int i = 0; i < 5; ++i){
            if(mins[i] > 10)delete = false;
        }
        if(delete){
            
            Launcher.send("NOT ENOUGH MINERALS_GATE DESTRUCTION IN 3 MINUTES");
            
            
            Thread T = new Thread(){
                public void run(){
                    try{
                        Thread.sleep(1000*60*3);
                        
                        Ugates.getData().remove(chan.getLongID());
                        Ugates.write();
                        chan.delete();
                    }catch (Exception E){}
                }
            }; T.start();
            
            
        }
    }
    
    public static String name(){
        String [] s1 = new String [] {
            "Amber",
            "Copper",
            "Coral",
            "Dark",
            "Diamond",
            "Emerald",
            "Golden",
            "Iron",
            "Jade",
            "Onyx",
            "Ruby",
            "Sapphire",
            "Silver",
            "Sunburnt",
            "Gay",
            "Black"
        };
        
        String [] s2 = new String [] {
            "Bishop",
            "Clover",
            "Falcon",
            "King",
            "Knight",
            "Lion",
            "Pawn",
            "Phantom",
            "Queen",
            "Rook",
            "Serpent",
            "Skull",
            "Sun",
            "Titan",
            "Cock",
            "Monkey",
            "Taco"
        };
        
        return (s1[oRan.nextInt(s1.length)] +"-"+ s2[oRan.nextInt(s2.length)]+"-Gate").toLowerCase();
    }
}
