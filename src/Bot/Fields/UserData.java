package Bot.Fields;

import Bot.Fields.Field;
import Bot.Launcher;
import static Bot.SuperRandom.oRan;
import Game.Constants;
import static Game.Constants.getMaterialName;
import Game.Mission;
import com.vdurmont.emoji.Emoji;
import java.awt.Color;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.RoleBuilder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author FF6EB4
 */
public class UserData {
    public static Field<ArrayList<String>> IDList = new Field<>("USERDATA","IDLIST",new ArrayList<>());
    private static HashMap<String,UserData> UserList = new HashMap<>();
    
    public static UserData getUD(IUser user){
        if(!UserList.containsKey(user.getStringID())){
            if(!IDList.getData().contains(user.getStringID())){
                IDList.getData().add(user.getStringID());
            }
            UserList.put(user.getStringID(),new UserData(user));
        }
        
        IDList.writeData(IDList.getData());
        
        //if(UserList.get(user.getID()).name.equals("Clint Eastwood's Character")){
        //    UserList.get(user.getID()).name = user.getName();
        //}
        
        return UserList.get(user.getStringID());
    }
    
    public static UserData getUD(long ID){
        return getUD(Launcher.client.getUserByID(ID));
    }
    
    
    public String name = "Nameless Hero Of Legend";
    public String ID = "00002";
    
    public Field<Integer> lols;
    public Field<HashMap<String,Integer>> emoji;
    public Field<Integer> blocks;
    
    public Field<Long> role;
    
    public Field<Long> lastMessage;
    
    //GAME FIELDS
    public Field<Integer> crowns;
    public Field<Integer> energy;
    public Field<Integer> slimeCoins;
    
    public Field<int[]> minerals;
    public Field<int[]> materials;
    
    public Field<ArrayList<String>> weapons;
    public Field<String> A;
    public Field<String> B;
    public Field<String> C;
    
    public Field<ArrayList<String>> missions;
    public Field<Integer> tier;
    
    public UserData(IUser user){
        instantiateFields(user);
    }
    
    
    public String toString(){
        try{
            String s = "**"+name+"**\n\n";
            
            if(Mission.knights.getData().contains(Long.parseLong(ID))){
                s+="**CURRENTLY IN MISSION: "+Mission.whichMission.getData().get(Long.parseLong(ID)).toUpperCase()+"**\n\n";
            }

            //s+="Lols: "+lols.getData()+"\n";
            //s+="Chat Blocks: "+blocks.getData()+"\n";
            //s+="Emoji: "+emoji.getData()+"\n";

            s+="**CROWNS:** "+crowns.getData()+"\n";
            s+="**ENERGY:** "+energy.getData()+"\n";
            s+="**SLIME COINS:** "+slimeCoins.getData()+"\n\n";

            s+="**MINERALS:** \n"+minerals.getData()[0]+" Red, "
                    + minerals.getData()[1]+" Green, "
                    + minerals.getData()[2]+" Blue, "
                    + minerals.getData()[3]+" Yellow, "
                    + minerals.getData()[4]+" Purple\n\n";
            s+="**MATERIALS:** \n"+materials.getData()[0]+" "+getMaterialName(0)+", "
                    +materials.getData()[1]+" "+getMaterialName(1)+", "
                    +materials.getData()[2]+" "+getMaterialName(2)+", "
                    +materials.getData()[3]+" "+getMaterialName(3)+", "
                    +materials.getData()[4]+" "+getMaterialName(4)+", "
                    +materials.getData()[5]+" "+getMaterialName(5)+", "
                    +materials.getData()[6]+" "+getMaterialName(6)+", "
                    +materials.getData()[7]+" "+getMaterialName(7)+", "
                    +materials.getData()[8]+" "+getMaterialName(8)+", "
                    +materials.getData()[9]+" "+getMaterialName(9)+"\n\n";

            s+="**WEAPONS:** \n";
            s+="**A:** "+A.getData()+"\n";
            s+="**B:** "+B.getData()+"\n";
            s+="**C:** "+C.getData()+"\n\n";
            s+="**ARSENAL:** \n";
            for(String S : weapons.getData()){
                s+=S+"\n";
            }
            return s;
        } catch (Exception E){
            E.printStackTrace();
        }
        return "Bad data!";
    }
    
    private void instantiateFields(IUser user){
        
        this.name = user.getName();
        this.ID = user.getStringID();
        
        lols = new Field<>(this.ID,"lols",0);
        emoji = new Field<>(this.ID,"emoji",new HashMap<>());
        blocks = new Field<>(this.ID,"blocks",0);
        
        role = new Field<>(this.ID,"role",this.getRoleIfApplicable(ID).getLongID());
        
        crowns = new Field<>(this.ID,"crowns",0);
        energy = new Field<>(this.ID,"ce",100);
        slimeCoins = new Field<>(this.ID,"slimecoins",0);
        minerals = new Field<>(this.ID,"minerals",new int[]{0,0,0,0,0});
        materials = new Field<>(this.ID,"materials",new int[]{0,0,0,0,0,0,0,0,0,0,0});
        
        weapons = new Field<>(this.ID,"weapons",new ArrayList<>());
        A = new Field<>(this.ID,"A","Proto Sword");
        B = new Field<>(this.ID,"B","Proto Gun");
        C = new Field<>(this.ID,"C","Proto Bomb");
        
        missions = new Field<>(this.ID,"missions",new ArrayList<>());
        missions.getData().add("Begging In Haven");
        missions.write();
        tier = new Field<>(this.ID,"tier",1);
        
        lastMessage = new Field<>(this.ID,"lastMessage",System.currentTimeMillis());
        
        IRole theRole = Launcher.client.getRoleByID(role.getData());
        user.addRole(theRole);
    }
    
    private IRole getRoleIfApplicable(String ID){
        List<IRole> roles = Launcher.client.getRoles();
        
        for(IRole R : roles){
            if(R.getName().equals(""+ID)){
                return R;
            }
        }
        
        //Otherwise, create a role for the new friend!
        
        //GETS THE FIRST GUILD POSSIBLE FOR THIS
        RoleBuilder RB = new RoleBuilder(Launcher.client.getGuilds().get(0));
        
        RB.withName(ID);
        RB.withColor(new Color(oRan.nextInt(255),oRan.nextInt(255),oRan.nextInt(255)));
        
        IRole role = RB.build();
        return role;
    }
}
