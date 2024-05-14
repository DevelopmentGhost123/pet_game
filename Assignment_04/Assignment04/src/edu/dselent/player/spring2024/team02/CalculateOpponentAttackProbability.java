package edu.dselent.player.spring2024.team02;

import edu.dselent.skill.Skills;

import java.util.*;

public class CalculateOpponentAttackProbability {
    OpponentData opponentData;
    Map<Skills, Double> opponentAttackProbabilities;
    int numberOfSkillsUp = 0;
    double defaultProb = 0.125;

    // Use opponent data to calculate the best attack
    // Opponent data has the attack history and opponents.

    public CalculateOpponentAttackProbability(OpponentData opponentData) {

        this.opponentData = opponentData;
        for (Skills skill : Skills.values()) {
            opponentAttackProbabilities.put(skill, 0.0);

        }

    }

    public  Map<Skills, Double> calculateProbabilities(){
        double totalProb = 0;
        numberOfSkillsUp = 0;

        for (Skills skill : Skills.values()) {
            opponentAttackProbabilities.put(skill, 1.0);

            if (opponentData.getOpponentAttackCoolDown(skill) > 0) {
                totalProb -= opponentAttackProbabilities.get(skill);
                opponentAttackProbabilities.put(skill, 0.0);
                totalProb += defaultProb;
            }
            else {
                numberOfSkillsUp++;
            }
        }

        for (Skills skill : Skills.values()) {

        }


        return opponentAttackProbabilities;
    }

}