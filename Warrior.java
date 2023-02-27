/***********************************
Guerreiro tem tres opcoes de ataque especial:

1- stun (custo de mana: 40)
	Causa ailment "STUN"
2- whirlwind (custo de mana: 40)
	Causa dano em area - em seus inimigos e em si proprio
3- axeHit (custo de mana: 35)
	Causa dano e recupera vida

*************************************/

import java.util.*;

public class Warrior extends Base {

	public Warrior(String _nome) {
		super(_nome, 85, 60, 68, 60, 90, 4);
	}

	int getOptions(Scanner scanner) {
		List<String> attacks = new ArrayList<String>();

		attacks.add("1- Stun (40)");
		attacks.add("2- Whirlwind (40)");
		attacks.add("3- Axe Hit (35)");
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
		int baseDamage = 0, cost, aux;
		
		switch (atk) {
			case 1: // stun
				cost = 40;
				break;
			case 2: // whirlwind
				cost = 40;
				baseDamage = 60;
				break;
			case 3: // axeHit
				cost = 35;
				baseDamage = 82;
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

			// whirlwind
			if (atk == 2) {
				int save;
				save = Util.areaAttack(enemy, damage, scanner);
				this.setHP(this.getHP() - this.getHP()/5);

				return save;
			}

			int index = Util.chooseCharacter(enemy, 1, scanner);
			Base target = enemy.get(index);
			aux = Util.takeDamage(target, damage);

			// axeHit
			if (atk == 3){
				if(aux == 0)
					this.setHP(this.getHP() + damage/3);
			}

			if(aux == 2){
				System.out.println(target.getName() + " has been defeated!\n");
				enemy.remove(index);
				return index;
			}

			// stun
			if (atk == 1){
				if(aux == 0){
					target.setStun(1);
					System.out.println("Opponent has been stunned!\n");
				}
			}
		}

		else {
			System.out.println("Your mana is not enough. Choose another attack!");
			return ERROR;
		}

		return SUCCESS;
	}
}