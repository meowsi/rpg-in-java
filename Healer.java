/***********************************
Curandeiro tem tres opcoes de ataque especial:

1- healAlly (custo de mana: 50)
	Cura parte da vida/mana de um aliado
2- purify (custo de mana: 30)
	Anula todos os ailment de um aliado
3- lightBeam (custo de mana: 25)
	Causa dano

*************************************/

import java.util.*;

public class Healer extends Base {

	public Healer(String _name) {
		super(_name, 95, 80, 85, 60, 50, 2);
	}

	int getOptions(Scanner scanner) {
		List<String> attacks = new ArrayList<String>();

		attacks.add("1- Heal Ally (50)");
		attacks.add("2- Purify (30)");
		attacks.add("3- Light Beam (25)");
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
		int baseDamage = 0, cost;
		
		switch (atk) {
			case 1: // healAlly
				cost = 50;
				break;
			case 2: // purify
				cost = 30;
				break;
			case 3: // lightBeam
				cost = 25;
				baseDamage = 75;
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
			this.setMana(this.getMana() - cost);

			int damage = Util.physicalDamage(baseDamage, this.getAttack());
			int index;
			Base target;

			if(atk == 1 || atk == 2){
				index = Util.chooseCharacter(ally, 0, scanner);
				target = ally.get(index);
			}
			else{
				index = Util.chooseCharacter(enemy, 1, scanner);
				target = enemy.get(index);
			}

			if(atk == 1){ // healAlly
				int heal = 18;

				if(target.getBurn() != 0) target.setHP(target.getHP() + heal/2); // cura diminuida pela metade caso aliado esteja sob efeito BURN
				else target.setHP(target.getHP() + heal);

				target.setMana(target.getMana() + target.getMana()/5);

				return SUCCESS;
			}

			else if(atk == 2) { // purify
				target.setStun(0);
				target.setBurn(0);
				target.setPoison(0);

				return SUCCESS;
			}

			else if (atk == 3) { // lightBeam
				damage = (int)(damage + 0.04 * this.getPH());
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

	void regenMana(){
		this.setMana(this.getMana() + 20);
	}
}