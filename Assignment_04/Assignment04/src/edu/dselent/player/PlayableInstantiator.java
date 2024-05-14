package edu.dselent.player;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.Map;

import edu.dselent.player.defaultintelligence.DefaultIntelligence;
import edu.dselent.player.defaultpower.DefaultPower;
import edu.dselent.player.defaultspeed.DefaultSpeed;
import edu.dselent.player.spring2024.team02.SkillDetails;
import edu.dselent.player.spring2024.team02.Team02PetInstance;
import edu.dselent.settings.ComputerPlayerSettings;
import edu.dselent.settings.PlayerSettings;
import edu.dselent.skill.Skills;


public class PlayableInstantiator
{
	public static List<Playable> instantiatePlayables(List<PlayerSettings> playerSettingsList)
	{
		List<Playable> playableList = new ArrayList<>();

		for(int i = 0; i< playerSettingsList.size(); i++)
		{
			PlayerSettings playerSettings = playerSettingsList.get(i);
			Playable playable = instantiatePlayable(i, playerSettings);
			playableList.add(playable);
		}

		return playableList;
	}

	public static Playable instantiatePlayable(int playableUid, PlayerSettings playerSettings)
	{
		Playable thePlayable = null;

		PlayerTypes playerType = playerSettings.getPlayerType();

		if(playerType == PlayerTypes.HUMAN)
		{
			thePlayable = new HumanPetInstance(playableUid, playerSettings);
		}
		else if(playerType == PlayerTypes.COMPUTER)
		{
			if(playerSettings instanceof ComputerPlayerSettings)
			{
				// Doug's default AI
				thePlayable = new ComputerPetInstance(playableUid, (ComputerPlayerSettings) playerSettings);
			}
			else
			{
				// TODO
				//Look into this more, added because of fall 2020 team 10
				ComputerPlayerSettings computerPlayerSettings = new ComputerPlayerSettings.ComputerPlayerSettingsBuilder()
						.withPetType(playerSettings.getPetType())
						.withPlayerType(playerSettings.getPlayerType())
						.withStartingHp(playerSettings.getStartingHp())
						.withPetName(playerSettings.getPetName())
						.withSkillSet(playerSettings.getSkillSet())
						.withRandomSeed(-1)
						.build();

				thePlayable = new ComputerPetInstance(playableUid, computerPlayerSettings);
			}
		}
		else if(playerType == PlayerTypes.DEFAULT_POWER)
		{
			thePlayable = new DefaultPower(playableUid, playerSettings);
		}
		else if(playerType == PlayerTypes.DEFAULT_SPEED)
		{
			thePlayable = new DefaultSpeed(playableUid, playerSettings);
		}
		else if(playerType == PlayerTypes.DEFAULT_INTELLIGENCE) {
			thePlayable = new DefaultIntelligence(playableUid, playerSettings);
		}
		else if(playerType == PlayerTypes.TEAM_02){
			Map<Skills, SkillDetails> skills = new HashMap<>();
			skills.put(Skills.ROCK_THROW, new SkillDetails(Skills.ROCK_THROW));
			skills.put(Skills.SCISSORS_POKE, new SkillDetails(Skills.SCISSORS_POKE));
			skills.put(Skills.PAPER_CUT, new SkillDetails(Skills.PAPER_CUT));
			skills.put(Skills.SHOOT_THE_MOON, new SkillDetails(Skills.SHOOT_THE_MOON));
			skills.put(Skills.REVERSAL_OF_FORTUNE, new SkillDetails(Skills.REVERSAL_OF_FORTUNE));
			thePlayable = new Team02PetInstance(playerSettings.getPetName(), playerSettings.getPlayerName(),
					playerSettings.getStartingHp(), skills, playerSettings.getPetType(),
					playerSettings.getPlayerType());
		}
		else
		{
			// TODO make custom exception
			throw new RuntimeException("Invalid playerType: " + playerType);
		}
		
		
		return thePlayable;
	}

}
