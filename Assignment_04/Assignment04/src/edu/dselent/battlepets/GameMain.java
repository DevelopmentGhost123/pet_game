package edu.dselent.battlepets;

import java.lang.reflect.*;

public class GameMain
{
	private static void outputUsage()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("Usage: [args...]");
		sb.append("\n");
		sb.append("Arguments include a single integer argument for which version of the game to run");
		sb.append("\n");
		sb.append("0: Text version");
		sb.append("\n");
		sb.append("1: UI version");
		sb.append("\n");
		
		System.out.println(sb.toString());
	}
	
	public static void main(String[] args)
	{
		// TODO have option to load default game settings from properties file?
				
		GameRunner gameRunner = null;
		
		// Default to text version if no program arguments are specified for easier use
		if(args.length < 1)
		{
			gameRunner = new TextGameRunner();
		}
		else
		{
			try
			{
				int gameChoice = Integer.parseInt(args[0]);
				
				if(gameChoice == 0)
				{
					gameRunner = new TextGameRunner();
				}
				else if(gameChoice == 1)
				{
					gameRunner = new UiGameRunner();
				}
				else
				{
					outputUsage();
				}
			}
			catch(NumberFormatException nfe)
			{
				outputUsage();
			}
		}
		
		if(gameRunner != null)
		{
			gameRunner.runGame();
		}
		
	}
	public static void getInstanceInformation(Object instance) {
		Class<?> clazz = instance.getClass(); // Get the class of the instance

		// Get the simple name of the class
		String simpleName = clazz.getSimpleName();
		System.out.println("Simple Name: " + simpleName);

		// Get the full name of the class (including package)
		String fullName = clazz.getName();
		System.out.println("Full Name: " + fullName);

		// Get fields of the class
		Field[] fields = clazz.getDeclaredFields();
		if (fields.length > 0) {
			System.out.println("Fields:");
			for (Field field : fields) {
				field.setAccessible(true); // Enable access to private fields
				try {
					Object value = field.get(instance); // Get the value of the field from the instance
					System.out.println(field.getName() + ": " + value);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("No fields found.");
		}

		// Get methods of the class
		Method[] methods = clazz.getDeclaredMethods();
		if (methods.length > 0) {
			System.out.println("Methods:");
			for (Method method : methods) {
				System.out.println(method.getName());
			}
		} else {
			System.out.println("No methods found.");
		}
	}
}
