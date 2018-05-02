/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Bot.Fields.Field;
import Bot.Fields.UserData;
import Bot.Launcher;
import static Bot.SuperRandom.oRan;
import static Game.Constants.BEAST;
import static Game.Constants.CONSTRUCT;
import static Game.Constants.FIEND;
import static Game.Constants.FIRE;
import static Game.Constants.FREEZE;
import static Game.Constants.GREMLIN;
import static Game.Constants.POISON;
import static Game.Constants.SHOCK;
import static Game.Constants.SLIME;
import static Game.Constants.UNDEAD;
import static Game.Gear.ELEMENTAL;
import static Game.Gear.NONE;
import static Game.Gear.NORMAL;
import static Game.Gear.PIERCING;
import static Game.Gear.SHADOW;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FF6EB4
 */
public class Mission {
    public static Field<ArrayList<Long>> knights = new Field<>("MISSION","KNIGHTS",new ArrayList<>());
    public static Field<HashMap<Long, String>> whichMission = new Field<>("MISSION","WHICH",new HashMap<>());
    public static Field<HashMap<Long, Long>> start = new Field<>("MISSION","START",new HashMap<>());
    
    public static ArrayList<String> missionNames = new ArrayList<>();
    public static HashMap<String, Mission> missions = new HashMap<>();
    
    String name;
    public long time;
    public int e;
    public int tier;
    boolean once = false;
    
    public String flavor;
    
    public int good;
    public int bad;
    
    public Mission(String name, long time, int e, int tier, int good, int bad, boolean one, String flavor){
        this.name = name;
        this.time = time;
        this.e = e;
        this.tier = tier;
        
        this.flavor = flavor;
        
        this.good = good;
        this.bad = bad;
        this.once = one;
    }
    
    public static void check(){
        
        for(int i = knights.getData().size()-1; i >= 0; i--){
            long how = System.currentTimeMillis() - start.getData().get(knights.getData().get(i));
            long time = missions.get(whichMission.getData().get(knights.getData().get(i))).time;
            
            if(how > time){
                missions.get(whichMission.getData().get(knights.getData().get(i))).reward(UserData.getUD(knights.getData().get(i)));
            }
        }
    }
    
    private void reward(UserData UD){
        knights.getData().remove(Long.parseLong(UD.ID));
        knights.write();
        
        
        
        int points = 0;
        
        String A = UD.A.getData();
        String B = UD.B.getData();
        String C = UD.C.getData();
        
        if(Gear.damageType.get(A) == this.good){
            points += 2;
        } else if(Gear.damageType.get(A) == this.bad){
            points += -1;
        } else {
            points += 1;
        }
        
        if(Gear.damageType.get(B) == this.good){
            points += 2;
        } else if(Gear.damageType.get(A) == this.bad){
            points += -1;
        } else {
            points += 1;
        }
        
        if(Gear.damageType.get(C) == this.good){
            points += 2;
        } else if(Gear.damageType.get(A) == this.bad){
            points += -1;
        } else {
            points += 1;
        }
        
        if(Gear.damageType.get(A)==Gear.damageType.get(B) && Gear.damageType.get(A)==Gear.damageType.get(C)){
            points -= 2;
        }
        
        if(points >= 5){
            UD.energy.append(e+15);
            Launcher.PM("You've completed your mission perfectly! +"+(e+15)+" energy!", Long.parseLong(UD.ID));
            bonus(UD);
            checkRank(UD);
        } else if(points >= 2){
            UD.energy.append(e+5);
            Launcher.PM("You've completed your mission! +"+(e+5)+" energy!", Long.parseLong(UD.ID));
            if(oRan.nextInt(1000) < 750)bonus(UD);
            checkRank(UD);
        } else if(points >= -1){
            UD.energy.append(e-5);
            Launcher.PM("You've completed your mission!.....barely! +"+(e)+" energy!", Long.parseLong(UD.ID));
            if(oRan.nextInt(1000) < 250)bonus(UD);
        } else {
            Launcher.PM("Mission failed! Next time, try bringing different gear..", Long.parseLong(UD.ID));
        }
        
        
        int times = 3+(int)Math.sqrt(time / (5 * 60 * 1000));
        int num = 1+(int)Math.sqrt(UD.missions.getData().size());
        
        for(int i = 0; i < times; ++i){
            if(oRan.nextInt(num) == 0){
                grantMission(UD);
            }
        }
        
        if(once){
            UD.missions.getData().remove(this.name);
            UD.missions.write();
        }
    }
    
    public void bonus(UserData UD){
        return;
    }
    
    public static void startMissions(){
        
        //Create some missions here :D
        
        register(new Mission(
                "Begging In Haven",
                60 * 60 * 1000,
                0,
                1,
                NONE,
                NONE,
                false,
                "Stand in haven asking other knights for energy like the trash you are."
        ){
            public void bonus(UserData UD){
                if(oRan.nextInt(1000) == 23){
                    UD.energy.append(10000);
                    Launcher.PM("...someone hands you 10,000 ce and walks away.",Long.parseLong(UD.ID));
                } else if(oRan.nextInt(1000) < 100){
                    UD.energy.append(100);
                    Launcher.PM("...A few people bite... +100 e",Long.parseLong(UD.ID));
                } else {
                    Launcher.PM("...no one gave you anything :(",Long.parseLong(UD.ID));
                }
                
            }
        });
        
        register(new Mission(
                "Scrubbing Toilets In Haven",
                180 * 60 * 1000,
                25,
                1,
                ELEMENTAL,
                SHADOW,
                false,
                "The bathroom in Haven is a disgusting place and it's your job to clean it."
        ){
            public void bonus(UserData UD){
                if(oRan.nextInt(100) == 52){
                    Launcher.PM("As you unclog the toilet, an old sarong floats up..... you're definitely depserate enough to wear it around Haven...",Long.parseLong(UD.ID));
                    UD.artifacts.getData().add("Old Sarong");
                }
            }
        });
        
        register(new Mission(
            "Prostitution",
            135*60*1000,
            300,
            1,
            NONE,
            NONE,
            false,
            "You've been propositioned by King Krogmo himself due to your nasty looking sarong?"
        ){
            public boolean eligible(UserData UD){
                if(UD.artifacts.getData().contains("Old Sarong")){
                    return true;
                }
                return false;
            }
        });
        
        register(new Mission(
                "Supply Delivery- Haven Guards",
                200 * 60 * 1000,
                30,
                1,
                NORMAL,
                SHADOW,
                false,
                "Letters, food and.. other.. supplies must be delivered to the guards guarding Haven!"
        ){
            public void bonus(UserData UD){
                if(oRan.nextInt(500) == 52){
                    Launcher.PM("A letter falls out and you can't help but read it... it seems the Spiral Order isn't as honest as they seem..",Long.parseLong(UD.ID));
                    UD.artifacts.getData().add("Deceitful Letter");
                }
                if(oRan.nextInt(100) < 20){
                    Launcher.PM("The Haven Guardian seems to like you.... he gives you his blessings",Long.parseLong(UD.ID));
                    UD.artifacts.getData().add("Haven Guardian's Blessings");
                }
            }
        });
        
        register(new Mission(
                "Supply Delivery- Moorcraft",
                4 * 60 * 60 * 1000,
                100,
                3,
                NORMAL,
                SHADOW,
                false,
                "Deliver rations and letters to Moorcraft!"
        ){
            public boolean eligible(UserData UD){
                if(UD.artifacts.getData().contains("Haven Guardian's Blessings")){
                    return true;
                }
                return false;
            }
            
            public void bonus(UserData UD){
                if(oRan.nextInt(300) == 52){
                    Launcher.PM("A letter falls out and you can't help but read it... it seems the Spiral Order isn't as honest as they seem..",Long.parseLong(UD.ID));
                    UD.artifacts.getData().add("Deceitful Letter");
                }
                if(oRan.nextInt(100) < 10){
                    Launcher.PM("The Moorcraft crew seems to like you.... they give you their blessings",Long.parseLong(UD.ID));
                    UD.artifacts.getData().add("Moorcraft Crew's Blessings");
                }
            }
        });
        
        register(new Mission(
                "Supply Delivery- Emberlight",
                6 * 60 * 60 * 1000,
                200,
                4,
                NORMAL,
                SHADOW,
                false,
                "Deliver rations and letters to Emberlight!"
        ){
            public boolean eligible(UserData UD){
                if(UD.artifacts.getData().contains("Moorcraft Crew's Blessings")){
                    return true;
                }
                return false;
            }
            
            public void bonus(UserData UD){
                if(oRan.nextInt(200) == 52){
                    Launcher.PM("A letter falls out and you can't help but read it... it seems the Spiral Order isn't as honest as they seem..",Long.parseLong(UD.ID));
                    UD.artifacts.getData().add("Deceitful Letter");
                }
                if(oRan.nextInt(100) < 5){
                    Launcher.PM("The crew at Emberlight seems to like you.... they give you their blessings",Long.parseLong(UD.ID));
                    UD.artifacts.getData().add("Emberlight's Blessings");
                }
            }
        });
        
        register(new Mission(
                "Supply Delivery- Core",
                8 * 60 * 60 * 1000,
                300,
                3,
                NORMAL,
                SHADOW,
                false,
                "Deliver rations and letters to the core!"
        ){
            public boolean eligible(UserData UD){
                if(UD.artifacts.getData().contains("Emberlight's Blessings")){
                    return true;
                }
                return false;
            }
            
            public void bonus(UserData UD){
                if(oRan.nextInt(50) == 23){
                    Launcher.PM("A letter falls out and you can't help but read it... it seems the Spiral Order isn't as honest as they seem..",Long.parseLong(UD.ID));
                    UD.artifacts.getData().add("Deceitful Letter");
                }
            }
        });
        
        register(new Mission(
                "Supply Delivery- Sanctuary",
                13 * 60 * 60 * 1000,
                1000,
                5,
                NORMAL,
                SHADOW,
                false,
                "Deliver rations and letters to the Sanctuary!"
        ){
            public boolean eligible(UserData UD){
                if(UD.artifacts.getData().contains("Deceitful Letter")){
                    return true;
                }
                return false;
            }
            
            public void bonus(UserData UD){
                if(oRan.nextInt(200) == 52){
                    Launcher.PM("A letter falls out and you can't help but read it... the spiral order knows a way into the core and doesn't say!",Long.parseLong(UD.ID));
                    UD.artifacts.getData().add("Truthful Letter");
                }
            }
        });
        

        
        register(new Mission(
                "Carrying Boxes",
                145 * 60 * 1000,
                20,
                1,
                NORMAL,
                PIERCING,
                false,
                "Carry boxes around the clockworks and in Haven with the other knights."
        ));
        
        register(new Mission(
                "Taking Out Trash-- Moorcraft",
                150 * 60 * 1000,
                25,
                3,
                SHADOW,
                ELEMENTAL,
                false,
                "Moorcraft is a scary place... with lots of garbage to be taken out. Dump it off the side in the clockworks."
        ));
        
        register(new Mission(
                "Cleaning the ooze-- Moorcraft",
                72 * 60 * 1000,
                15,
                3,
                ELEMENTAL,
                SHADOW,
                false,
                "Puddles of ectoplam rise through the floor.... it's your job to clean these up."
        ));
        
        register(new Mission(
                "Night Shift Trainee",
                6 * 60 * 60 * 1000,
                200,
                3,
                ELEMENTAL,
                SHADOW,
                false,
                "Help keep watch over Haven. All night long. Sleep during the day like a vampire."
        ){
            public void bonus(UserData UD){
                if(oRan.nextInt(1000)<100){
                    Launcher.PM("At the end of the night Feron himself hands you an honorary trophy of sorts- a night baton",Long.parseLong(UD.ID));
                    UD.artifacts.getData().add("Night Baton");
                    UD.artifacts.write();
                }
            }
            public boolean eligible(UserData UD){
                if(UD.artifacts.getData().contains("Wolver Skull") && UD.artifacts.getData().contains("Red Stapler")){
                    return true;
                }
                return false;
            }
        });
        
        register(new Mission(
                "Night Shift",
                6 * 60 * 60 * 1000,
                600,
                5,
                ELEMENTAL,
                SHADOW,
                false,
                "They were born in the darkness... you simply adopted it."
        ){
            public boolean eligible(UserData UD){
                if(UD.artifacts.getData().contains("Night Baton")){
                    return true;
                }
                return false;
            }
        });
        
        register(new Mission(
                "Geotech Intern",
                120 * 60 * 1000,
                0,
                1,
                NONE,
                NONE,
                false,
                "Work for free helping the geology department. How kind of you."
        ){
            public void bonus(UserData UD){
                Launcher.PM("Your hard work as an intern pays off- your superior lets you take home 200 of each mineral.",Long.parseLong(UD.ID));
                UD.minerals.getData()[0]+=200;
                UD.minerals.getData()[1]+=200;
                UD.minerals.getData()[2]+=200;
                UD.minerals.getData()[3]+=200;
                UD.minerals.getData()[4]+=200;
            }
            
            public boolean eligible(UserData UD){
                if(UD.gear.getData().contains("Shard Bomb"))return true;
                if(UD.gear.getData().contains("Dark Matter Bomb"))return true;
                if(UD.gear.getData().contains("Splinter Bomb"))return true;
                if(UD.gear.getData().contains("Crystal Bomb"))return true;
                
                return false;
            }
        });
        
        register(new Mission(
                "Geotech",
                180 * 60 * 1000,
                50,
                3,
                NONE,
                NONE,
                false,
                "Study the minerals out in the field. Get paid. Get minerals."
        ){
            public void bonus(UserData UD){
                Launcher.PM("Your hard work as a geotech pays off- your superior lets you take home 500 of each mineral.",Long.parseLong(UD.ID));
                UD.minerals.getData()[0]+=500;
                UD.minerals.getData()[1]+=500;
                UD.minerals.getData()[2]+=500;
                UD.minerals.getData()[3]+=500;
                UD.minerals.getData()[4]+=500;
            }
            
            public boolean eligible(UserData UD){
                if(UD.missions.getData().contains("Geotech Intern")){
                    if(UD.gear.getData().contains("Heavy Shard Bomb"))return true;
                    if(UD.gear.getData().contains("Heavy Dark Matter Bomb"))return true;
                    if(UD.gear.getData().contains("Heavy Splinter Bomb"))return true;
                    if(UD.gear.getData().contains("Heavy Crystal Bomb"))return true;
                    if(UD.gear.getData().contains("Shocking Salt Bomb"))return true;
                    if(UD.gear.getData().contains("Radiant Sun Shards"))return true;
                }
                return false;
            }
        });
        
        register(new Mission(
                "Den Defaunation",
                60 * 60 * 1000,
                35,
                1,
                PIERCING,
                ELEMENTAL,
                true,
                "Go deep into a Wolver Den and kill them all. Alphas, Wolvers, even the children."
        ){
            public void bonus(UserData UD){
                if(oRan.nextInt(1000) < 100){
                    UD.materials.getData()[BEAST]+=10;
                    UD.materials.write();
                    Launcher.PM("With unfettered glee, you kill every single wolver in the den, delighting in their cries of terror and pain. You get 10 beast mats.",Long.parseLong(UD.ID));
                } else if(oRan.nextInt(1000) < 300){
                    UD.materials.getData()[BEAST]+=3;
                    UD.materials.write();
                    Launcher.PM("The wolvers manage to fight you off their den, but not without you killing a respectable number of them. You get 3 beast mats.",Long.parseLong(UD.ID));
                } else {
                    Launcher.PM("Your bleeding heart gets the better of you, and you pet the wolvers. The wolvers proceed to make that idiom literal... you barely survive, and are given the energy out of pitty..",Long.parseLong(UD.ID));
                }
                
                if(oRan.nextInt(1000) < 50){
                    UD.artifacts.getData().add("Wolver Skull");
                    UD.artifacts.write();
                    Launcher.PM("You did however manage to hang onto a trophy of sorts- the skull of a slain wolver.", Long.parseLong(UD.ID));
                }
            }
            
            public boolean eligible(UserData UD){
                if(UD.artifacts.getData().contains("Wolver Skull")){
                    return false;
                }
                
                if(Gear.damageType.get(UD.A.getData()) == Gear.PIERCING){
                    return true;
                }
                
                if(Gear.damageType.get(UD.B.getData()) == Gear.PIERCING){
                    return true;
                }
                
                if(Gear.damageType.get(UD.C.getData()) == Gear.PIERCING){
                    return true;
                }
                
                return false;
            }
        });
        
        register(new Mission(
                "Fiendish Friends",
                60 * 60 * 1000,
                35,
                1,
                PIERCING,
                SHADOW,
                true,
                "Explore the fiendish office space and slay anything you find in the name of the Spiral Order."
        ){
            public void bonus(UserData UD){
                if(oRan.nextInt(1000) < 100){
                    UD.materials.getData()[FIEND]+=10;
                    UD.materials.write();
                    Launcher.PM("A lone stapler, fallen from a nearby desk lies on the floor silently. The only sign of life. +10 fiend mats.",Long.parseLong(UD.ID));
                } else if(oRan.nextInt(1000) < 300){
                    UD.materials.getData()[FIEND]+=3;
                    UD.materials.write();
                    Launcher.PM("The fiendish foes hurl stapler after chair after mug at you.. you're able to slay most of them, but a few get away. +3 fiend mats",Long.parseLong(UD.ID));
                } else {
                    Launcher.PM("You run into the middle of the pack only to realize your grim mistake... you manage to make it out alive, but not without consequence.",Long.parseLong(UD.ID));
                }
                
                if(oRan.nextInt(1000) < 50){
                    UD.artifacts.getData().add("Red Stapler");
                    UD.artifacts.write();
                    Launcher.PM("You did however manage to hang onto a trophy of sorts- A certain fiendish stapler.", Long.parseLong(UD.ID));
                }
            }
            
            public boolean eligible(UserData UD){
                if(UD.artifacts.getData().contains("Red Stapler")){
                    return false;
                }
                
                if(Gear.damageType.get(UD.A.getData()) == Gear.PIERCING){
                    return true;
                }
                
                if(Gear.damageType.get(UD.B.getData()) == Gear.PIERCING){
                    return true;
                }
                
                if(Gear.damageType.get(UD.C.getData()) == Gear.PIERCING){
                    return true;
                }
                
                return false;
            }
        });
        
        register(new Mission(
                "Gremlin Massacre",
                90 * 60 * 1000,
                50,
                2,
                SHADOW,
                ELEMENTAL,
                true,
                "The Spiral Order has captured a good many fugitive gremlins, but cannot contain them. You've been instructed to \"fix\" their little issue..."
        ){
            public void bonus(UserData UD){
                    UD.materials.getData()[GREMLIN]+=5;
                    UD.materials.write();
                    Launcher.PM("The unarmed gremlins plead for their lives...you shock them, and inject bits of lichen into them.. after they die you take their mats.. +5 Gremlin Mats!",Long.parseLong(UD.ID));
                
                
                if(oRan.nextInt(1000) < 77){
                    UD.artifacts.getData().add("Child's Skull");
                    UD.artifacts.write();
                    Launcher.PM("You did however manage to hang onto a trophy of sorts- The skull of a gremlin child..", Long.parseLong(UD.ID));
                }
                
                if(oRan.nextInt(1000) < 7){
                    UD.gear.getData().add("Teddy Bear Buckler");
                    Launcher.PM("You pick up a fallen teddy bear....", Long.parseLong(UD.ID));
                }
                
                if(oRan.nextInt(1000) < 7){
                    UD.gear.getData().add("Tech Orange Teddy Bear Buckler");
                    Launcher.PM("You pick up a fallen teddy bear....", Long.parseLong(UD.ID));
                }
                
                if(oRan.nextInt(1000) < 7){
                    UD.gear.getData().add("Tech Blue Teddy Bear Buckler");
                    Launcher.PM("You pick up a fallen teddy bear....", Long.parseLong(UD.ID));
                }
                
                if(oRan.nextInt(1000) < 7){
                    UD.gear.getData().add("Tech Green Teddy Bear Buckler");
                    Launcher.PM("You pick up a fallen teddy bear....", Long.parseLong(UD.ID));
                }
                
                if(oRan.nextInt(1000) < 7){
                    UD.gear.getData().add("Tech Pink Teddy Bear Buckler");
                    Launcher.PM("You pick up a fallen teddy bear....", Long.parseLong(UD.ID));
                }
            }
            
            public boolean eligible(UserData UD){
                int ret = 0;
                if(UD.artifacts.getData().contains("Red Stapler")){
                    ret+=5;
                }
                
                if(UD.artifacts.getData().contains("Gremlin Goggles")){
                    ret+=10;
                }
                
                if(UD.artifacts.getData().contains("Wolver Skull")){
                    ret+=5;
                }
                
                if(UD.artifacts.getData().contains("Generic Shock Artifact")){
                    ret+=5;
                }

                
                return ret >= 15;
            }
        });
        
        register(new Mission(
                "Garish Gremlins",
                60 * 60 * 1000,
                35,
                1,
                SHADOW,
                ELEMENTAL,
                true,
                "We've recently made contact with some gremlins. Rather than negotiate, we've deced best to assassinate. Targeted Sanctions."
        ){
            public void bonus(UserData UD){
                if(oRan.nextInt(1000) < 100){
                    UD.materials.getData()[GREMLIN]+=10;
                    UD.materials.write();
                    Launcher.PM("The gremlins plead. Then fight. Then die. +10 gremlin mats.",Long.parseLong(UD.ID));
                } else if(oRan.nextInt(1000) < 300){
                    UD.materials.getData()[GREMLIN]+=3;
                    UD.materials.write();
                    Launcher.PM("The gremlins notice your weapons and are ready to fight as you arrive.. only one escapes. +3 gremlin mats",Long.parseLong(UD.ID));
                } else {
                    Launcher.PM("These gremlins came prepared! You quickly pull out your weapons but it's too late- you barely survive to kill a third of your attackers before they vanish into the clockworks.",Long.parseLong(UD.ID));
                }
                
                if(oRan.nextInt(1000) < 50){
                    UD.artifacts.getData().add("Gremlin Goggles");
                    UD.artifacts.write();
                    Launcher.PM("You did however manage to hang onto a trophy of sorts- A pair of gremlin goggles.", Long.parseLong(UD.ID));
                }
            }
            
            public boolean eligible(UserData UD){
                if(UD.artifacts.getData().contains("Gremlin Goggles")){
                    return false;
                }
                
                if(Gear.damageType.get(UD.A.getData()) == Gear.SHADOW){
                    return true;
                }
                
                if(Gear.damageType.get(UD.B.getData()) == Gear.SHADOW){
                    return true;
                }
                
                if(Gear.damageType.get(UD.C.getData()) == Gear.SHADOW){
                    return true;
                }
                
                return false;
            }
        });
        
        register(new Mission(
                "Guarded Gravestones",
                60 * 60 * 1000,
                35,
                1,
                ELEMENTAL,
                SHADOW,
                true,
                "The undead know of nothing but death, and we shall swiftly deliver them to their graves.. after summoning them from their graves!"
        ){
            public void bonus(UserData UD){
                if(oRan.nextInt(1000) < 100){
                    UD.materials.getData()[UNDEAD]+=10;
                    UD.materials.write();
                    Launcher.PM("You stab your sword into the ground as the dust zombie rises from beneath... the last of them, defeated. +10 undead mats",Long.parseLong(UD.ID));
                } else if(oRan.nextInt(1000) < 300){
                    UD.materials.getData()[UNDEAD]+=3;
                    UD.materials.write();
                    Launcher.PM("Just as you begin to leave a phantom draws its blade on you... you manage to escape, but take heavy damage. +3 undead mats",Long.parseLong(UD.ID));
                } else {
                    Launcher.PM("The zombies surround you and you fade to black... suddenly a team mate lifts you up, granting you with half their life force. You are saved.",Long.parseLong(UD.ID));
                }
                
                if(oRan.nextInt(1000) < 50){
                    UD.artifacts.getData().add("Thwack Hammer");
                    UD.artifacts.write();
                    Launcher.PM("You did however manage to hang onto a trophy of sorts- a thwack hammer", Long.parseLong(UD.ID));
                }
            }
            
            public boolean eligible(UserData UD){
                if(UD.artifacts.getData().contains("Thwack Hammer")){
                    return false;
                }
                
                if(Gear.damageType.get(UD.A.getData()) == Gear.ELEMENTAL){
                    return true;
                }
                
                if(Gear.damageType.get(UD.B.getData()) == Gear.ELEMENTAL){
                    return true;
                }
                
                if(Gear.damageType.get(UD.C.getData()) == Gear.ELEMENTAL){
                    return true;
                }
                
                return false;
            }
        });
        
        register(new Mission(
                "Autonomous Automations",
                60 * 60 * 1000,
                35,
                1,
                ELEMENTAL,
                PIERCING,
                true,
                "A factory that produces factory members works through the night without sleep. Intelligent or not, sleep is all that awaits it."
        ){
            public void bonus(UserData UD){
                if(oRan.nextInt(1000) < 100){
                    UD.materials.getData()[CONSTRUCT]+=10;
                    UD.materials.write();
                    Launcher.PM("The last of the machines stops. The last whir dies down. A hive mind has died today. +10 construct mats",Long.parseLong(UD.ID));
                } else if(oRan.nextInt(1000) < 300){
                    UD.materials.getData()[CONSTRUCT]+=3;
                    UD.materials.write();
                    Launcher.PM("The last machine's beauty captures you. You could kill it. Or you could leave it.... +3 construct mats",Long.parseLong(UD.ID));
                } else {
                    Launcher.PM("The machines seem to be talking toghether.. their conversation overwhelms you..... just as the blade of a mecha knight pierces your chest.",Long.parseLong(UD.ID));
                }
                
                if(oRan.nextInt(1000) < 50){
                    UD.artifacts.getData().add("Clockwork Crest");
                    UD.artifacts.write();
                    Launcher.PM("You did however manage to hang onto a trophy of sorts- a certain heart shaped crest.", Long.parseLong(UD.ID));
                }
            }
            
            public boolean eligible(UserData UD){
                if(UD.artifacts.getData().contains("Clockwork Crest")){
                    return false;
                }
                
                if(Gear.damageType.get(UD.A.getData()) == Gear.ELEMENTAL){
                    return true;
                }
                
                if(Gear.damageType.get(UD.B.getData()) == Gear.ELEMENTAL){
                    return true;
                }
                
                if(Gear.damageType.get(UD.C.getData()) == Gear.ELEMENTAL){
                    return true;
                }
                
                return false;
            }
        });
        
        register(new Mission(
                "Slimy Showdown",
                60 * 60 * 1000,
                35,
                1,
                SHADOW,
                PIERCING,
                true,
                "They ooze up from the ground and drip from the ceiling to consume. We must stop them before they stop us."
        ){
            public void bonus(UserData UD){
                if(oRan.nextInt(1000) < 100){
                    UD.materials.getData()[SLIME]+=10;
                    UD.materials.write();
                    Launcher.PM("The ground smells dead. Arid. Unfit for life. +10 slime mats!",Long.parseLong(UD.ID));
                } else if(oRan.nextInt(1000) < 300){
                    UD.materials.getData()[SLIME]+=3;
                    UD.materials.write();
                    Launcher.PM("Most of the slimes were slain but some remain in hiding... if only there were more time... +3 slime mats",Long.parseLong(UD.ID));
                } else {
                    Launcher.PM("As you approach the slimes begin to combine into a monstrous figure... the hair on the back of your neck stands up as a tremendous spike shoots out of the ground near you..",Long.parseLong(UD.ID));
                }
                
                if(oRan.nextInt(1000) < 50){
                    UD.artifacts.getData().add("Digested Idol");
                    UD.artifacts.write();
                    Launcher.PM("You did however manage to hang onto a trophy of sorts- a fiendish idol, partly digested by a slime.", Long.parseLong(UD.ID));
                }
            }
            
            public boolean eligible(UserData UD){
                if(UD.artifacts.getData().contains("Digested Idol")){
                    return false;
                }
                
                if(Gear.damageType.get(UD.A.getData()) == Gear.SHADOW){
                    return true;
                }
                
                if(Gear.damageType.get(UD.B.getData()) == Gear.SHADOW){
                    return true;
                }
                
                if(Gear.damageType.get(UD.C.getData()) == Gear.SHADOW){
                    return true;
                }
                
                return false;
            }
        });
        
        register(new Mission(
                "Fiery Flamewar",
                60 * 60 * 1000,
                35,
                2,
                SHADOW,
                PIERCING,
                true,
                "It's april fools and the gremlin scorchers are yelling insults at us with oilers at their sides!"
        ){
            public void bonus(UserData UD){
                if(oRan.nextInt(1000) < 100){
                    UD.materials.getData()[FIRE]+=10;
                    UD.materials.write();
                    Launcher.PM("Sticks and stones may break your bones but fire failed to do so. +10 fire mats!",Long.parseLong(UD.ID));
                } else if(oRan.nextInt(1000) < 300){
                    UD.materials.getData()[FIRE]+=3;
                    UD.materials.write();
                    Launcher.PM("The gremlin's flamethrowers brushed by your leg, leaving third degree burns. A fair trade for their life. !3 fire mats.",Long.parseLong(UD.ID));
                } else {
                    Launcher.PM("The flamer's gay harassment got through to you! You tripped directly into the line of fire.. good thing a friend was there to pull you out!",Long.parseLong(UD.ID));
                }
                
                if(oRan.nextInt(1000) < 50){
                    UD.artifacts.getData().add("Trash Talk");
                    UD.artifacts.write();
                    Launcher.PM("You did however manage to hang onto a trophy of sorts- the manifestation of the gremlins talking.", Long.parseLong(UD.ID));
                }
            }
            
            
            public boolean eligible(UserData UD){
                if(UD.artifacts.getData().contains("Trash Talk")){
                    return false;
                }
                
                return true;
            }
        });
        
        register(new Mission(
                "Generic Shock Mission",
                60 * 60 * 1000,
                35,
                2,
                SHADOW,
                PIERCING,
                true,
                "!"
        ){
            public void bonus(UserData UD){
                if(oRan.nextInt(1000) < 100){
                    UD.materials.getData()[SHOCK]+=10;
                    UD.materials.write();
                    Launcher.PM("The generic shock monsters were no match for your shadowy weapons! +10 shock mats!",Long.parseLong(UD.ID));
                } else if(oRan.nextInt(1000) < 300){
                    UD.materials.getData()[FIRE]+=3;
                    UD.materials.write();
                    Launcher.PM("Suddenly a generic shock enemy appears to fight! You slash at it but it's too fast! +3 shock mats!",Long.parseLong(UD.ID));
                } else {
                    Launcher.PM("Shock crumbles your stance long enough for a shock themed enemy to get in a hit! And another! you barely escape with your life.",Long.parseLong(UD.ID));
                }
                
                if(oRan.nextInt(1000) < 50){
                    UD.artifacts.getData().add("Generic Shock Artifact");
                    UD.artifacts.write();
                    Launcher.PM("You did however manage to hang onto a trophy of sorts- some kind of generic electric thing.", Long.parseLong(UD.ID));
                }
            }
            
            
            public boolean eligible(UserData UD){
                if(UD.artifacts.getData().contains("Generic Shock Artifact")){
                    return false;
                }
                
                return true;
            }
        });
        
        register(new Mission(
                "Poisonous Peril",
                60 * 60 * 1000,
                35,
                2,
                PIERCING,
                ELEMENTAL,
                true,
                "The wolvers! They're rabid! How? The Spiral Order won't say!"
        ){
            public void bonus(UserData UD){
                if(oRan.nextInt(1000) < 100){
                    UD.materials.getData()[POISON]+=10;
                    UD.materials.write();
                    Launcher.PM("The wolvers move around you in an aggressive, rabid, haze. You move around them in a sober state of mind. +10 poison mats!",Long.parseLong(UD.ID));
                } else if(oRan.nextInt(1000) < 300){
                    UD.materials.getData()[POISON]+=3;
                    UD.materials.write();
                    Launcher.PM("One bite was all it took to send you to the med bay... severa dozen slashes were all it took to slay the pack. +3 poison mats!",Long.parseLong(UD.ID));
                } else {
                    Launcher.PM("Even through their rabid haze the wolvers are fine beasts. They had you beat, though your party took care of them for you...",Long.parseLong(UD.ID));
                }
                
                if(oRan.nextInt(1000) < 50){
                    UD.artifacts.getData().add("Rabies");
                    UD.artifacts.write();
                    Launcher.PM("You did however manage to hang onto a trophy of sorts- ....rabies?", Long.parseLong(UD.ID));
                }
            }
            
            
            public boolean eligible(UserData UD){
                if(UD.artifacts.getData().contains("Rabies")){
                    return false;
                }
                
                return true;
            }
        });
        
        register(new Mission(
                "Frozen To The Bone",
                60 * 60 * 1000,
                35,
                2,
                ELEMENTAL,
                SHADOW,
                true,
                "!"
        ){
            public void bonus(UserData UD){
                if(oRan.nextInt(1000) < 100){
                    UD.materials.getData()[FREEZE]+=10;
                    UD.materials.write();
                    Launcher.PM("The frozen shamblers are no match for you! +10 freeze mats.",Long.parseLong(UD.ID));
                } else if(oRan.nextInt(1000) < 300){
                    UD.materials.getData()[FREEZE]+=3;
                    UD.materials.write();
                    Launcher.PM("Freeze vents are no fun, but your friends are able to unstick you well enough. +3 freeze mats!",Long.parseLong(UD.ID));
                } else {
                    Launcher.PM("You quickly find yourself frozen in front of some sort of mechanical tree. FFFFF",Long.parseLong(UD.ID));
                }
                
                if(oRan.nextInt(1000) < 50){
                    UD.artifacts.getData().add("Frozen Bone");
                    UD.artifacts.write();
                    Launcher.PM("You did however manage to hang onto a trophy of sorts- a literal frozen bone?", Long.parseLong(UD.ID));
                }
            }
            
            
            public boolean eligible(UserData UD){
                if(UD.artifacts.getData().contains("Frozen Bone")){
                    return false;
                }
                
                return true;
            }
        });
        
        
        
        Thread T = new Thread(){
            public void run(){
                while(true){
                    try{
                        sleep(15000);
                    } catch (InterruptedException ex) {
                        System.err.println("THREAD PROBLEMS ;)");
                    }
                    
                    Mission.check();
                }
            }
        };
        
        T.start();
    }
    
    private static void register(Mission M){
        missionNames.add(M.name);
        missions.put(M.name, M);
    }
    
    public static void embark(UserData UD, String miss){
        if(knights.getData().contains(Long.parseLong(UD.ID))){
            knights.getData().remove(Long.parseLong(UD.ID));
        }
        
        knights.getData().add(Long.parseLong(UD.ID));
        knights.write();
        whichMission.getData().put(Long.parseLong(UD.ID),miss);
        whichMission.write();
        start.getData().put(Long.parseLong(UD.ID),System.currentTimeMillis());
        start.write();
    }
    
    public static void grantMission(UserData UD){
        int t = UD.maxStars.getData();
        String miss = missionNames.get(oRan.nextInt(missionNames.size()));
        Mission M = missions.get(miss);
        
        int tries = 100;
        while(tries > 0){
            tries = tries - 1;
            
            if(UD.missions.getData().contains(miss)){
                miss = missionNames.get(oRan.nextInt(missionNames.size()));
                M = missions.get(miss);
            } else {
                if(t >= M.tier && M.eligible(UD)){
                    UD.missions.getData().add(miss);
                    UD.missions.write();
                    Launcher.PM("YOU'VE RECEIVED A NEW MISSION: "+miss,Long.parseLong(UD.ID));
                    return;
                } else {
                    miss = missionNames.get(oRan.nextInt(missionNames.size()));
                    M = missions.get(miss);
                }
            }
        }
        
    }
    
    
    public boolean eligible(UserData UD){
        return true;
    }
    
    public void checkRank(UserData UD){
        try{
        int above = UD.maxStars.getData();
        
        int a = Gear.star.get(UD.A.getData());
        int b = Gear.star.get(UD.B.getData());
        int c = Gear.star.get(UD.C.getData());
        int d = Gear.star.get(UD.armor.getData());
        int e = Gear.star.get(UD.helmet.getData());
        int f = Gear.star.get(UD.shield.getData());
        
        if(a>above&&b>above&&c>above&&d>above&&e>above&&f>above){
            UD.maxStars.append(1);
            Launcher.PM("Congratulations! You've advanced to rank "+(above+1)+"!",Long.parseLong(UD.ID));
        }
        }catch (Exception E){}
    }
}
