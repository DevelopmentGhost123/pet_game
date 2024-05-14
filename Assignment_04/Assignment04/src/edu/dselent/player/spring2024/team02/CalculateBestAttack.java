package edu.dselent.player.spring2024.team02;

import edu.dselent.player.PetTypes;
import edu.dselent.player.Playable;
import edu.dselent.skill.Skills;
import java.util.logging.Logger;

import java.util.*;

public class CalculateBestAttack {

    // Use opponent data to calculate the best attack
    // Opponent data has the attack history and opponents.

    private static OpponentData opponent;
    private static int opponentIndex;
    static List<Skills> skillNotCooldown = new ArrayList<>();
    private static Random randomGenerator = new Random(12345);

    private static final Logger logger = Logger.getLogger(CalculateBestAttack.class.getName());

    public static Skills getBestAttack(){
        if(opponent.getOpponentInfo().get(opponentIndex).getPetType().equals(PetTypes.POWER))
            return bestSkillAgainstPower();
        else if(opponent.getOpponentInfo().get(opponentIndex).getPetType().equals(PetTypes.SPEED))
            return bestSkillAgainstSpeed();
        else
            return bestSkillAgainstIntelligence();
    }
    public static void setOpponentData(OpponentData newOpponentData) {
        opponent = newOpponentData;
    }
    private static Skills bestSkillAgainstIntelligence() {

        if (skillNotCooldown.contains(Skills.SHOOT_THE_MOON) && opponent.getOpponentAttackCoolDown(Skills.SHOOT_THE_MOON) > 4) {
            return Skills.SHOOT_THE_MOON;
        }
        // Adding logic for checking the conditions based on recharge
        // Example for ROCK_THROW if Scissor Poke is recharging
        if (skillNotCooldown.contains(Skills.ROCK_THROW) && opponent.getOpponentAttackCoolDown(Skills.SCISSORS_POKE) > 0) {
            return Skills.ROCK_THROW;
        }
        if (skillNotCooldown.contains(Skills.PAPER_CUT) && opponent.getOpponentAttackCoolDown(Skills.ROCK_THROW) > 0) {
            return Skills.PAPER_CUT;
        }
        if (skillNotCooldown.contains(Skills.SCISSORS_POKE) && opponent.getOpponentAttackCoolDown(Skills.PAPER_CUT) > 0) {
            return Skills.SCISSORS_POKE;
        }

        return fallbackSkill();
    }


    private static Skills bestSkillAgainstSpeed() {
       // double opponentHpPercentage = opponent.getPetHps().get(opponentIndex) / opponent.getOpponentInfo().get(opponentIndex).getStartingHp() * 100;

        opponentIndex = opponent.getOpponentInfo().get(opponentIndex).getPlayableUid();
        double opponentHpPercentage = opponent.getHPpercent(opponentIndex);
        if (opponentHpPercentage >= 75) {
            if (skillNotCooldown.contains(Skills.SCISSORS_POKE) && opponent.getOpponentAttackCoolDown(Skills.SCISSORS_POKE) > 0) {
                return Skills.SCISSORS_POKE;
            } else if (skillNotCooldown.contains(Skills.PAPER_CUT) && opponent.getOpponentAttackCoolDown(Skills.PAPER_CUT) > 0) {
                return Skills.PAPER_CUT;
            }
        } else if (opponentHpPercentage < 75 && opponentHpPercentage >= 25) {
            if (skillNotCooldown.contains(Skills.ROCK_THROW) && opponent.getOpponentAttackCoolDown(Skills.ROCK_THROW) > 0) {
                return Skills.ROCK_THROW;
            }
        } else { // HP < 25%
            if (skillNotCooldown.contains(Skills.PAPER_CUT) && opponent.getOpponentAttackCoolDown(Skills.PAPER_CUT) > 0) {
                return Skills.PAPER_CUT;
            }
        }

        return fallbackSkill();
    }


    private static Skills bestSkillAgainstPower() {
    //private static Skills bestSkillAgainstPower() {

        if (skillNotCooldown.contains(Skills.SHOOT_THE_MOON) && opponent.getOpponentAttackCoolDown(Skills.SHOOT_THE_MOON) > 4) {
            return Skills.SHOOT_THE_MOON;
        }
        if (skillNotCooldown.contains(Skills.REVERSAL_OF_FORTUNE) && opponent.getOpponentAttackCoolDown(Skills.REVERSAL_OF_FORTUNE) == 0) {
            return Skills.REVERSAL_OF_FORTUNE;
        }
        if (skillNotCooldown.contains(Skills.PAPER_CUT) && opponent.getOpponentAttackCoolDown(Skills.ROCK_THROW) > 0) {
            return Skills.PAPER_CUT;
        }
        if (skillNotCooldown.contains(Skills.ROCK_THROW) && opponent.getOpponentAttackCoolDown(Skills.SCISSORS_POKE) > 0) {
            return Skills.ROCK_THROW;
        }
        if (skillNotCooldown.contains(Skills.SCISSORS_POKE) && opponent.getOpponentAttackCoolDown(Skills.PAPER_CUT) > 0) {
            return Skills.SCISSORS_POKE;
        }

        return fallbackSkill();
    }

    private static Skills fallbackSkill() {
        List<Skills> availableSkills = skillNotCooldown.isEmpty() ? Arrays.asList(Skills.values()) : skillNotCooldown;
        return availableSkills.get(randomGenerator.nextInt(availableSkills.size()));
    }

    public static boolean shootTheMoonCheck(OpponentData opponentData, int myPlayableUid)
    {
        int numSkillsOnCooldown = 0;

        for (Skills skill : Skills.values()) {
            if (opponentData.getOpponentAttackCoolDown(skill) > 0) {
                numSkillsOnCooldown++;
            }
        }

        return numSkillsOnCooldown > 2; // Adjust this threshold as needed
    }

}
