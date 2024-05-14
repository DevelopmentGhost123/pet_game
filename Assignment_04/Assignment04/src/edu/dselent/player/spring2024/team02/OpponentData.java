package edu.dselent.player.spring2024.team02;

import edu.dselent.event.AttackEvent;
import edu.dselent.event.PlayerEventInfo;
import edu.dselent.player.Playable;
import edu.dselent.player.Player;
import edu.dselent.skill.Skills;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OpponentData {
    public List<AttackEvent> attackHistory; // holds ATTACK and ATTACK_SHOOT_THE_MOON
    private List<PlayerEventInfo> opponentList;
    public List<PlayerEventInfo> opponentInfo;
    private List<Integer> opponentAttackCoolDowns; // INDICES: 0=Rock, 1=Paper, 2=Scissors, 3=RoF, 4 = StM
    private Skills opponentType;
    private List<Double> petHps;

    OpponentData(){
        attackHistory = new ArrayList<>();
        opponentList = new ArrayList<>();
        opponentAttackCoolDowns = new ArrayList<>(0);
        for (int i = 0; i < 5; i++)
            opponentAttackCoolDowns.add(0,0);
        petHps = new ArrayList<Double>();
        //opponentType =
    }

    public void addAttack(AttackEvent attack){
        attackHistory.add(attack); // StM different???
        if (attack.getAttackingSkillChoice() == Skills.ROCK_THROW)
        {
            opponentAttackCoolDowns.set(0, 1); // rock
        }
        else if (attack.getAttackingSkillChoice() == Skills.PAPER_CUT)
        {
            opponentAttackCoolDowns.set(1, 1); // paper
        }
        else if (attack.getAttackingSkillChoice() == Skills.SCISSORS_POKE)
        {
            opponentAttackCoolDowns.set(2, 1);
        }
        else if (attack.getAttackingSkillChoice() == Skills.REVERSAL_OF_FORTUNE)
        {
            opponentAttackCoolDowns.set(3, 6);
        }
        else if (attack.getAttackingSkillChoice() == Skills.SHOOT_THE_MOON)
        {
            opponentAttackCoolDowns.set(4, 6);
        }
    }

    public Integer getOpponentAttackCoolDown(Skills coolDown)
    {
        return opponentAttackCoolDowns.get(coolDown.ordinal());
    }


    // !!! do at round start event
    public void decrementCoolDowns()
    {
        for (int i =0; i < 5; i++)
        {
            if (opponentAttackCoolDowns.get(i) > 0)
                opponentAttackCoolDowns.set(i, opponentAttackCoolDowns.get(i)- 1); // decrements if cooldown is > 0
        }
    }

    public void setOpponents(List<PlayerEventInfo> opponents){
        opponentInfo = opponents;
    }

    public void resetOpponentData(){

        attackHistory = new ArrayList<>();
        opponentList = new ArrayList<>();
    }


    public List<PlayerEventInfo> getOpponentInfo() {
        return opponentInfo;
    }
    public List<Double> getPetHps()
    {
        return petHps;
    }
    public double getHPpercent(int opponentIndex)
    {
//        System.out.println("\t\t//////  getHPpercent(int opponentIndex)");

        if( petHps.size() == 0 ){
            if( opponentInfo.size() == 0 ){
                return 0;
            }
            else{
                return opponentInfo.get(opponentIndex).getStartingHp();
            }
        }

        double currentHp = petHps.get(opponentIndex);
        double startingHp = opponentInfo.get(opponentIndex).getStartingHp();
        return (currentHp / startingHp) * 100;
    }
}
