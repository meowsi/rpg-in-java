/***********************************
Ladrao tem quatro opcoes de ataque especial:

1- daggerThrow (custo de mana: 20)
	Causa dano com chance de critico
2- stab (custo de mana: 30)
	Causa dano, ignorando armadura
3- manaSteal (custo de mana: 15)
	Rouba 1/5 da mana atual do inimigo
4- usePotion (custo de mana: 35)
	Remove ailments e recupera vida

*************************************/

import java.util.*;

public class Rogue extends Base {

	public Rogue(String _name) {
		super(_name, 75, 75, 67, 100, 80, 2);
	}

	int getOptions(Scanner scanner) {
		List<String> attacks = new ArrayList<String>();

		attacks.add("1- Dagger Throw (20)");
		attacks.add("2- Stab (30)");
		attacks.add("3- Mana Steal (15)");
		attacks.add("4- Use Potion (35)");
		attacks.add("5- Basic Attack (0)");

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
		int baseDamage = 0, cost;
		
		switch (atk) {
			case 1: // daggerThrow
				cost = 20;
				baseDamage = 30;
				break;
			case 2: // stab
				cost = 30;
				baseDamage = 77;
				break;
			case 3: // manaSteal
				cost = 15;
				break;
			case 4: // usePotion
				cost = 35;
				break;
			case 5: // basicAttack
				cost = 0;
				baseDamage = 30;
				break;
			default:
				System.out.println("Invalid attack, try again!");
				return ERROR;
		}

		if (this.getMana() >= cost) {
			this.setMana(this.getMana()-cost);

			if (atk == 4) { // usePotion
				this.setHP(this.getHP() + 15);
				this.setBurn(0);
				this.setPoison(0);

				return SUCCESS;
			}

			int index = Util.chooseCharacter(enemy, 1, scanner);
			Base target = enemy.get(index);

			if (atk == 3) { // manaSteal
				this.setMana(this.getMana() + target.getMana()/5);
				target.setMana(target.getMana() - target.getMana()/5);

				return SUCCESS;
			}

			int damage = Util.physicalDamage(baseDamage, this.getAttack());

			// daggerThrow - tem chance de critico
			if (atk == 1) {
				Random rand = new Random();
				int daggerThrow_qtd = rand.nextInt(2);
				damage = damage * (daggerThrow_qtd + 1);
			}

			else if (atk == 2) { // stab - ignora armadura
				damage = damage + target.getArmour();
			}

			if(Util.takeDamage(target, damage) == 2){
				System.out.println(target.getName() + " has been defeated!\n");
				enemy.remove(index);
				return index;
			}
		}
		
		else {
			System.out.println("Your mana is not enough. Choose another attack!");
			return ERROR;
		}

		return SUCCESS;
	}
}