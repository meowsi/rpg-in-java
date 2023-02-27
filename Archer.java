/***********************************
Arqueiro tem tres opcoes de ataque especial:

1- poisonArrow (custo de mana: 45)
	Causa dano e ailment "POISON"
2- powerArrow (custo de mana: 35)
	Causa dano
3- burningArrow (custo de mana: 45)
	Causa dano e ailment "BURN"

*************************************/

import java.util.*;

public class Archer extends Base {

	public Archer(String _name) {
		super(_name, 75, 70, 70, 70, 90, 3);
	}

	int getOptions(Scanner scanner) {
		List<String> attacks = new ArrayList<String>();

		attacks.add("1- Poison Arrow (45)");
		attacks.add("2- Power Arrow (35)");
		attacks.add("3- Burning Arrow (45)");
		attacks.add("4- Basic Attack (0)");

		for (int i = 0; i < attacks.size(); i++) {
			System.out.println(attacks.get(i));
		}

		int num = 0;
		System.out.print("\nAttack index: ");
		while (!scanner.hasNextInt()) {
			System.out.println("You must type an integer value.");
			scanner.next();
		}
		num = scanner.nextInt();

		return num;
	}

	int attack(int atk, ArrayList<Base> enemy, ArrayList<Base> ally, Scanner scanner) {
		int baseDamage, cost, aux;
		
		switch (atk) {
			case 1: // poisonArrow
				cost = 45;
				baseDamage = 65;
				break;
			case 2: // powerArrow
				cost = 35;
				baseDamage = 80;
				break;
			case 3: // burningArrow
				cost = 45;
				baseDamage = 70;
				break;
			case 4: // basicAttack
				cost = 0;
				baseDamage = 30;
				break;
			default:
				System.out.println("Invalid attack, try again!");
				return ERROR;
		}

		if (this.getMana() >= cost) {
			this.setMana(this.getMana()-cost);
			int damage = Util.physicalDamage(baseDamage, this.getAttack());

			// powerArrow
			if (atk == 2) {
				damage = (int)(damage + 0.04 * this.getPH());
			}

			int index = Util.chooseCharacter(enemy, 1, scanner);
			Base target = enemy.get(index);
			aux = Util.takeDamage(target, damage);
			if(aux == 2){
				System.out.println(target.getName() + " has been defeated!");
				enemy.remove(index);
				return index;
			}

			if (atk == 1){
				if(aux == 0){
					target.setPoison(5);
					System.out.println("Opponent has been poisoned!\n");
				}
			}

			else if (atk == 3)
				if(aux == 0){
					target.setBurn(3);
					System.out.println("Opponent has been burnt!\n");
				}
		}
		
		else {
			System.out.println("Your mana is not enough. Choose another attack!");
			return ERROR;
		}

		return SUCCESS;
	}
}