/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Bot.Fields.Field;
import Bot.Fields.UserData;
import static Bot.SuperRandom.oRan;
import java.util.ArrayList;
import java.util.HashMap;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

/**
 *
 * @author FF6EB4
 */
public class Gate{
    public static HashMap<IChannel, Gate> gates = new HashMap<>();
    
    IChannel chan;
    
    public ArrayList<Long> activeUsers;
    public Field<int[]> minerals;
    
    public Gate(IChannel chan){
        this.chan = chan;
        this.activeUsers = new ArrayList<>();
        this.minerals = new Field<>(chan.getStringID(),"minerals",new int[]{0,0,0,0,0});
        
        Gate.gates.put(chan, this);
    }
    
    public static void instantiateGates(IGuild G){
        for(IChannel chan : G.getChannels()){
            new Gate(chan);
        }
    }
    
    public int getMaterialAndDeduct(){
        int indexA = 1;
        int indexB = 0;
        int max = 0;
        for(int i=0; i <minerals.getData().length;++i){
            if(minerals.getData()[i] > max){
                max = minerals.getData()[i];
                indexB = indexA;
                indexA = i;
            }
        }
        
        if(minerals.getData()[indexA] <= 0 || minerals.getData()[indexB] <= 0){
            return -1;
        }
        
        int ret = Constants.mineralsToMaterial(indexA, indexB);
        deductMinerals(indexA,indexB);
        
        return ret;
    }
    
    public void deductMinerals(int a, int b){
        minerals.getData()[a] = (int)Math.floor(minerals.getData()[a] * .93);
        minerals.getData()[b] = (int)Math.floor(minerals.getData()[b] * .93);
        minerals.write();
    }
    
    public void pruneActiveUsers(){
        for(int i = activeUsers.size()-1; i >= 0; i-=1){
            Long L = activeUsers.get(i);
            UserData UD = UserData.getUD(L);
            if(System.currentTimeMillis() - UD.lastMessage.getData() > 120000){
                activeUsers.remove(L);
            }
        }
    }
    
    public void tick(){
        pruneActiveUsers();
        
        int mat = getMaterialAndDeduct();
        giveMaterials();
        
        for( Long L : activeUsers ){
            UserData UD = UserData.getUD(L);
            UD.crowns.append(50+oRan.nextInt(100));
            UD.minerals.getData()[oRan.nextInt(5)]+=1;
            
            String A = UD.A.getData();
            
            Effects.act(this, UD, Weapons.effect.get(A), Weapons.param1.get(A),
                     Weapons.param2.get(A), Weapons.param3.get(A));
        }
    }
    
    public void giveMaterials(){
        pruneActiveUsers();
        
        int mat = getMaterialAndDeduct();
        
        for( Long L : activeUsers ){
            UserData UD = UserData.getUD(L);
            if(mat != -1){
                UD.materials.getData()[mat] += 1;
                UD.materials.write();
            }
        }
    }
}
