import java.util.*;

class Util {
	public static int chooseCharacter(ArrayList<Base> array, int type, Scanner scanner) {
		if (type == 0) // array de aliados
			System.out.println("Choose the ally you want to help.");
		else if (type == 1) // array de inimigos
			System.out.println("Choose the enemy to be attacked.");
		
		for (int i = 0; i < array.size(); i++) {
			System.out.println(i + "- " + array.get(i).getName());
		}
		
		System.out.print("Your choice: ");
		int i = scanner.nextInt();
		while(i > array.size() || i < 0) {
			System.out.println("Invalid choice.");
			System.out.print("Your choice: ");
			i = scanner.nextInt();
		}
		System.out.println();

		return i;
	}

	public static int physicalDamage(int baseDamage, int attack) {
		if(baseDamage == 0) return 0;

		double damage = 0.07 * attack + 0.1 * baseDamage;
		return (int)damage;
	}

	public static int isDead(Base person) {
		if (person.getHP() <= 0)
			return 1;
		return 0;
	}

	public static int takeDamage(Base target, int damage) {
		Random rand = new Random();
		int prob, aux;

		prob = rand.nextInt(100);
		if(prob < target.getSwiftness()/5){
			System.out.println(target.getName() + " dodged the attack.\n");
			return 1; // desviou
		}

		aux = damage - target.getArmour();
		if(aux < 0) aux = 0;
		target.setHP(target.getHP() - aux);
		aux = isDead(target);
		if(aux == 1) return 2; // morreu

		return 0;
	}

	public static int areaAttack(ArrayList<Base> enemy, int damage, Scanner scanner) {
		int save = -2
				;
		System.out.println("This attack deals area damage.");
		if (enemy.size() > 3) {
			System.out.println("You may choose 3 enemies to attack (must be sequential).");
			for (int i = 0; i < enemy.size(); i++) {
				System.out.println(i + "- " + enemy.get(i).getName());
			}
			System.out.print("Your choice: ");
			for (int i = 0; i < 3; i++) {
				int num = scanner.nextInt();
				while (num > enemy.size() || num < 0) {
					System.out.println("There is no such opponent. Choose a valid one.");
					num = scanner.nextInt();
				}

				Base target = enemy.get(num);
				if(takeDamage(target, damage) == 2){
					System.out.println(target.getName() + " has been defeated!\n");
					save = num;
				}
			}
			enemy.remove(save);
			System.out.println();
		}
		
		else {
			System.out.println("All enemies targeted!\n");
			for (int i = 0; i < enemy.size(); i++) {
				Base target = enemy.get(i);
				if(takeDamage(target, damage) == 2){
					System.out.println(target.getName() + " has been defeated!");
					save = i;
				}
			}
			enemy.remove(save);
		}

		return save;
	}
}