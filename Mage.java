/***********************************
Mago tem cinco opcoes de ataque especial:
 *obs: seu ataque basico tem multiplicador de PH

1- ignite (custo de mana: 35)
	Causa dano e ailment "BURN"
2- glacialStorm (custo de mana: 50)
	Causa dano em area
3- restoreMana (custo de mana: 10)
	Recupera mana
4- buff (custo de mana: 40)
	Aumenta ataque e armadura de um aliado
5- redemption (custo de mana: 50)
	Recupera um pouco da vida de todos do seu time

*************************************/

import java.util.*;

public class Mage extends Base {

	public Mage(String _nome) {
		super(_nome, 70, 90, 88, 50, 68, 1);
	}

	int getOptions(Scanner scanner) {
		List<String> attacks = new ArrayList<String>();

		attacks.add("1- Ignite (35)");
		attacks.add("2- Glacial Storm (50)");
		attacks.add("3- Restore Mana (10)");
		attacks.add("4- Buff (40)");
		attacks.add("5- Redemption (50)");
		attacks.add("6- Basic Attack (0)");

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
			case 1: // ignite
				cost = 35;
				baseDamage = 78;
				break;
			case 2: // glacialStorm
				cost = 50;
				baseDamage = 65;
				break;
			case 3: // restoreMana
				cost = 10;
				baseDamage = 0;
				break;
			case 4: // buff
				cost = 40;
				break;
			case 5: // redemption
				cost = 50;
				break;
			case 6: // basicAttack
				cost = 0;
				baseDamage = 30;
				break;
			default:
				System.out.println("Invalid attack, try again!");
				return ERROR;
		}

		if (this.getMana() >= cost) {
			this.setMana(this.getMana() - cost);

			if (atk == 3) { // restoreMana
				this.setMana(this.getMana() + 30);

				return SUCCESS;
			}

			int index;
			Base target;

			if (atk == 4) { // buff
				index = Util.chooseCharacter(ally, 0, scanner);
				target = ally.get(index);

				target.setAttack(target.getAttack() + 10);
				target.setArmour(target.getArmour() + 1);

				return SUCCESS;
			}

			if (atk == 5) { // redemption
				int heal = 7;
				for (int i = 0; i < ally.size(); i++) {
					target = ally.get(i);
					if(target.getBurn() != 0) target.setHP(target.getHP() + heal/2); // cura diminuida pela metade caso aliado esteja sob efeito BURN
					else target.setHP(target.getHP() + heal);
				}

				return SUCCESS;
			}

			int damage = Util.physicalDamage(baseDamage, this.getAttack());
			damage = (int)(damage + 0.04 * this.getPH());

			if (atk == 2) { // glacialStorm
				int save;
				save = Util.areaAttack(enemy, damage, scanner);
				return save;
			}
			else{
				index = Util.chooseCharacter(enemy, 1, scanner);
				target = enemy.get(index);
				aux = Util.takeDamage(target, damage);
				if(aux == 2){
					System.out.println(target.getName() + " has been defeated!\n");
					enemy.remove(index);
					return index;
				}

				if (atk == 1) { // ignite
					if(aux == 0){
						target.setBurn(3);
						System.out.println("Opponent has been burnt!\n");
					}
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