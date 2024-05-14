package edu.dselent.player.spring2024.team02;

import edu.dselent.event.*;
import edu.dselent.player.*;
import edu.dselent.settings.PlayerSettings;
import edu.dselent.skill.Skills;
import edu.dselent.player.spring2024.team02.CalculateBestAttack;

import java.util.*;

import static edu.dselent.player.spring2024.team02.CalculateBestAttack.skillNotCooldown;

public class Team02PetInstance implements Playable {
    private static final Random aiRandom = new Random(123); // Hard-coded seed for consistency
    private PetTypes petType;
    private PlayerTypes playerType;
    private String petName;
    private double currentHp;
    private double startingHp;
    private int uid;
    private Map<Skills, SkillDetails> skills;
    private Player player;
    private ArrayList<Skills> availableSkills;

    private OpponentData opponentData;

    /***
     * Constructor for an AI player
     * @param petName name for pet
     * @param playerName name for Player
     * @param startingHp starting HP for pet
     * @param skills info on skills the pet has
     * @param petType type that the pet is
     * @param playerType type the player is
     */
    public Team02PetInstance(String petName, String playerName, double startingHp,Map<Skills, SkillDetails> skills, PetTypes petType, PlayerTypes playerType) {
        this.petName = petName;
        this.startingHp = startingHp;
        this.currentHp = startingHp;
        this.petType = petType;
        this.skills = skills;
        this.playerType = playerType;
        this.player = new Player(playerName, playerType);
        opponentData = new OpponentData();
        this.availableSkills = new ArrayList<>();

    }

    /***
     * Gets the PlayableUid for the player
     * @return uid
     */
    @Override
    public int getPlayableUid() {
        return uid;
    }

    /***
     * Sets the uid to the player
     * @param playableUid The unique id to assign to the current playable object
     */
    @Override
    public void setPlayableUid(int playableUid) {
        this.uid = playableUid;
    }

    /***
     * The players id is the same as the players uid
     * @return uid
     */
    public int getPlayableId() {
        return uid;
    }

    /***
     * Returns the players name.
     * @return playerName
     */
    @Override
    public String getPlayerName() {
        return player.getName();
    }

    /***
     * Returns the pets name.
     * @return petName
     */
    @Override
    public String getPetName() {
        return petName;
    }

    /***
     * Returns the playerType. In this case, it should always be computer
     * @return playerType
     */
    @Override
    public PlayerTypes getPlayerType() {
        return playerType;
    }

    /***
     * Returns the pet type
     * @return petType
     */
    @Override
    public PetTypes getPetType() {
        return petType;
    }

    /***
     * Returns the current HP of the pet
     * @return currentHp
     */
    @Override
    public double getCurrentHp() {
        return currentHp;
    }

    /***
     * This is the logic for the computer choosing a skill.
     * If the skill is not on cooldown, the skill will not be
     * able to be picked. Otherwise, the skill is randomly picked.
     *
     * @return chosenSkill - which is the skill the AI picks.
     */
    @Override
    public Skills chooseSkill() {
        // PUT THIS IN SO I CAN DEBUG AND UNDERSTAND DOUGS CODE

//        boolean validInput = false;
//        Skills chosenSkill = null;
//        while(!validInput){
//            chosenSkill = promptUserForSkillChoice(this);
//            if(skills.get(chosenSkill).getCooldown() == 0) {
//                validInput = true;
//            }
//        }
//        return chosenSkill;



        // HERE IS THE AI SKILL CHOICE CODE

//        System.out.println("\t\t//////  chooseSkill()");

        System.out.println("test choose");

//        List<Skills> skillNotCooldown = new ArrayList<>();
        skillNotCooldown.clear();
        for (Skills skill : Skills.values()) {
            if (!this.getSkills().get(skill).isCooldown())
            {
                skillNotCooldown.add(skill);
            }
        }
        if (skillNotCooldown.isEmpty()) {
            throw new IllegalStateException("No skills available due to cooldowns.");
        }
//        int skillIndex = aiRandom.nextInt(skillNotCooldown.size());
//        Skills chosenSkill = skillNotCooldown.get(skillIndex);
        return CalculateBestAttack.getBestAttack();

        //return CalculateBestAttack.getBestAttack(opponentData, getPlayableUid());
    }

    // TODO: Delete this later. Used for debugging.
    public Skills promptUserForSkillChoice(Playable player) {

//        System.out.println("\t\t//////  promptUserForSkillChoice(Playable player)");

        Scanner scanner = new Scanner(System.in);
        int chosenSkillIndex = -1;
        Skills chosenSkill = null;

        System.out.print(player.getPlayerName() + ", pick a skill {1 = Rock Throw, 2 = Scissors Poke, "
                + "3 = Paper Cut, 4 = Shoot the Moon, 5 = Reversal of Fortune}: ");

        while (true) {
            if (!scanner.hasNextInt()) {
                System.out.println("That's not a valid number. Please enter a number for a skill to use.");
                scanner.next();
                continue;
            }
            chosenSkillIndex = scanner.nextInt();
            if (chosenSkillIndex < 1 || chosenSkillIndex > 5 ) {
                System.out.println(chosenSkillIndex + " is not valid, please enter a valid skill number between 1 and 5.");
            } else if (player.getSkillRechargeTime(Skills.values()[chosenSkillIndex - 1]) > 0) {
                System.out.println(Skills.values()[chosenSkillIndex - 1] + " is currently on cooldown. Please choose another skill.");
            } else {
                if (chosenSkillIndex == 1)
                    chosenSkill = Skills.ROCK_THROW;
                else if (chosenSkillIndex == 2)
                    chosenSkill = Skills.SCISSORS_POKE;
                else if (chosenSkillIndex == 3)
                    chosenSkill = Skills.PAPER_CUT;
                else if (chosenSkillIndex == 4)
                    chosenSkill = Skills.SHOOT_THE_MOON;
                else if (chosenSkillIndex == 5)
                    chosenSkill = Skills.REVERSAL_OF_FORTUNE;
                break;
            }
        }
        return chosenSkill;
    }



    /***
     * Sets the current HP
     * @param hp
     */
    public void setCurrentHp(double hp){currentHp = hp;}

    /***
     * This is updating the currentHp based on the damage the player takes.
     * @param hp The hp which will be subtracted from the current hp
     */
    @Override
    public void updateHp(double hp) {

//        System.out.println("\t\t//////  updateHp(double hp)");

        double newHp = getCurrentHp() - hp;
        setCurrentHp(newHp);
    }

    /***
     * This resets the HP to the starting HP that the user entered.
     */
    @Override
    public void resetHp() {
        currentHp = startingHp;
    }

    /***
     * If the currentHP is greater than 0, the pet is awake
     * @return a boolean, true if pet is awake, false if pet is asleep
     */
    @Override
    public boolean isAwake() {
        return this.currentHp > 0;
    }

    /***
     * This method predicts which skill an opponent might use next.
     * It compiles a list of the player's own skills that are currently not on cooldown,
     * assuming any of these might be chosen by the opponent.
     * A skill is randomly selected from the available skills to represent the predicted opponent's choice.
     * @return Skills - the skill predicted to be used by the opponent
     */
    @Override
    public Skills getSkillPrediction()
    {

//        System.out.println("\t\t//////  getSkillPrediction()");

        //System.out.println("test skill");
        List<Skills> predictedSkill = new ArrayList<>();
        for (Skills skill : Skills.values()) {
            if (!this.getSkills().get(skill).isCooldown()) {
                predictedSkill.add(skill);
            }
        }
        if (predictedSkill.isEmpty()) {
            throw new IllegalStateException("No skills available for prediction due to cooldowns.");
        }
        int temp = aiRandom.nextInt(predictedSkill.size());
        Skills chosenSkill = predictedSkill.get(temp);
        System.out.println("(AI player) predicts opponent will use " + chosenSkill);
        return chosenSkill;
        //return PetScanner.promptUserForPrediction(this);
        //return null;
    }

    /***
     * Retrieves the cooldown (recharge time) for a specified skill.
     * @param skill The skill for which the recharge time is queried.
     * @return int - The current cooldown duration for the given skill.
     */
    @Override
    public int getSkillRechargeTime(Skills skill) {
        return getSkills().get(skill).getCooldown();
    }

    /***
     * Calculates the current health of the pet as a percentage of its starting health.
     * @return double - The percentage of health remaining. Returns 0 if
     * starting health is 0 to avoid division by zero.
     */
    @Override
    public double calculateHpPercent() {

//        System.out.println("\t\t//////  calculateHpPercent()");

        if (startingHp == 0) {
            return 0;
        }
        return (currentHp / startingHp) * 100;
    }

    /***
     * Retrieves the starting health of the pet.
     * @return double - The initial health value set for the pet.
     */
    @Override
    public double getStartingHp() {
        return startingHp;
    }
    /***
     * Resets the pet's health to its initial state and clears all cooldowns for its skills.
     */
    @Override
    public void reset() {
        resetHp();
        getSkills().forEach((p, pi)->setRechargeTime(p,0));
    }
    /***
     * Decrements the cooldown for each skill by one time unit.
     */
    @Override
    public void decrementRechargeTimes() {
        getSkills().forEach((p,pi)->pi.decrementCooldown());
    }

    /***
     * gets all skills for the pet
     * @return skills
     */
    public Map<Skills, SkillDetails> getSkills()
    {
        return skills;
    }

    /**
     * Sets what skills the pet has.
     * @param skills
     */
    public void setSkills(Map<Skills, SkillDetails> skills)
    {
        this.skills = skills;
    }
    /***
     * Retrieves the current set of skills along with their details.
     * @return Map<Skills, SkillDetails> - A map of skills paired with their specific details.
     */
    @Override
    public void setRechargeTime(Skills skill, int rechargeTime) {
        getSkills().get(skill).setCooldown(rechargeTime);
    }

    @Override
    public void update(Object event) {

//        System.out.println("\t\t//////  update(Object event)");

        //System.out.println(event.toString());
        if(event instanceof FightStartEvent){
            opponentData.resetOpponentData();
            FightStartEvent fse = (FightStartEvent) event;
            if (opponentData == null) {
                opponentData = new OpponentData();
            }
            opponentData.setOpponents(fse.getPlayerEventInfoList());
            CalculateBestAttack.setOpponentData(opponentData);
        }
        else if (event instanceof AttackEvent){
            AttackEvent ae = (AttackEvent) event;
            if(event instanceof AttackEventShootTheMoon){
                ae = (AttackEventShootTheMoon) event;
            }
            opponentData.addAttack(ae);
        }
        else if (event instanceof RoundStartEvent){
            RoundStartEvent rse = (RoundStartEvent) event;
            opponentData.decrementCoolDowns();
        }

    }
}
