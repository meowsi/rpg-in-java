/************************* COISAS QUE FALTA FAZER *************************/

// Tratar na main as condicoes STUN, BURN e POISON em cada turno

import java.util.*;

class Main {
	public static void main(String[] args) {
	
		Scanner scanner = new Scanner(System.in);

		System.out.println("In this game, there are 2 opponent teams. Each team may have as many characters as desired.");
		
		// Selecionar a quantidade de personagens de cada jogador
		int numPersonagens;
		System.out.print("Type the amount of characters each team will have: ");
		while (!scanner.hasNextInt()) {
			System.out.println("You must type an integer value.");
			scanner.next();
		}
	
		numPersonagens = scanner.nextInt();
		System.out.println("You have selected " + numPersonagens + " character(s) for each team.");


		// Criar os personagens de cada time
		ArrayList<Base> player1 = new ArrayList<Base>();
		ArrayList<Base> player2 = new ArrayList<Base>();

		System.out.println("\nAvailable classes: ");
		System.out.println("1 - Mage\n2 - Warrior\n3 - Healer\n4 - Archer\n5 - Rogue");
		
		for (int j = 1; j < 3; j++) {
			System.out.println("\nPlayer " + j + ", choose your character(s): ");

			for (int i = 0; i < numPersonagens; i++) {
				int aux = i+1;
				System.out.println("\nChoose the attributes of character " + aux + ".");
				System.out.print("Class (int): ");
				while (!scanner.hasNextInt()) {
					System.out.println("You must type an integer value.");
					scanner.next();
				}
				int classe;
				classe = scanner.nextInt();
				scanner.nextLine();

				System.out.print("Name (String): ");
				String nome;
				nome = scanner.nextLine();
				
				Base pessoa;
				switch (classe) {
					case 1: pessoa = new Mage(nome);    break;
					case 2:	pessoa = new Warrior(nome); break;
					case 3: pessoa = new Healer(nome);  break;
					case 4: pessoa = new Archer(nome);  break;
					case 5: pessoa = new Rogue(nome);   break;
					default:
						pessoa = new Warrior(nome);
						break;
				}
				
				if      (j == 1) player1.add(pessoa);
				else if (j == 2) player2.add(pessoa);
			}
		}
		System.out.println();

		// Remove-se um personagem do array quando sua vida fica <= 0
		// Enquanto um dos arrays não estiver vazio, o jogo prossegue

		int count_p1 = 0, count_p2 = 0;
		while (player1.isEmpty() == false && player2.isEmpty() == false) {
			// Turno Player1
			for(int i=0; i<1; i++){
				int flag = 0;

				// checar ailments
				if(player1.get(count_p1).getBurn() != 0){ // checa BURN
					int burn_count = player1.get(count_p1).getBurn();

					System.out.println(player1.get(count_p1).name + " is burning.");
					player1.get(count_p1).setHP(player1.get(count_p1).getHP() - 3);
					if(Util.isDead(player1.get(count_p1)) == 1){
						System.out.println(player1.get(count_p1).getName() + " died!");
						player1.remove(count_p1);
						break;
					}
					player1.get(count_p1).setBurn(burn_count - 1);
				}
				if(player1.get(count_p1).getPoison() != 0){ // checa POISON
					int poison_count = player1.get(count_p1).getPoison();

					System.out.println(player1.get(count_p1).name + " has been poisoned.");
					player1.get(count_p1).setHP(player1.get(count_p1).getHP() - 3);
					if(Util.isDead(player1.get(count_p1)) == 1){
						System.out.println(player1.get(count_p1).getName() + " died!");
						player1.remove(count_p1);
						break;
					}
					player1.get(count_p1).setPoison(poison_count - 1);
				}
				if(player1.get(count_p1).getStun() == 1){ // checa STUN
					System.out.println();
					System.out.println(player1.get(count_p1).name + " is stunned.\n");
					player1.get(count_p1).setStun(0);
					count_p1++;
					break;
				}

				System.out.println(player1.get(count_p1).name + ", choose your attack.");
				System.out.println("Vida: " + player1.get(count_p1).getHP());
				System.out.println("Mana: " + player1.get(count_p1).getMana());
				System.out.println("Options: ");

				int num = player1.get(count_p1).getOptions(scanner);
				int result = player1.get(count_p1).attack(num, player2, player1, scanner);
				if(result >= 0){
					if(result < count_p2){
						count_p2--;
					}
					else if(result == count_p2 && result == player2.size()){
						count_p2 = 0;
					}
				}

				if(result == -1){
					i--; // ocorreu um erro, turno se repete
					flag = 1;
				}
				if(flag == 1) continue;
				player1.get(count_p1).regenMana();
				count_p1++;
				if(count_p1 == player1.size()) count_p1 = 0;
			}
			if(player1.isEmpty() == true) break;
			if(player2.isEmpty() == true) break;

			// Turno Player2
			for(int i=0; i<1; i++) {
				int flag = 0;

				// checar ailments
				if (player2.get(count_p2).getBurn() != 0) { // checa BURN
					int burn_count = player2.get(count_p2).getBurn();

					System.out.println(player2.get(count_p2).name + " is burning.");
					player2.get(count_p2).setHP(player2.get(count_p2).getHP() - 3);
					if (Util.isDead(player2.get(count_p2)) == 1) {
						System.out.println(player2.get(count_p2).getName() + " died!");
						player2.remove(count_p2);
						break;
					}
					player2.get(count_p2).setBurn(burn_count - 1);
				}
				if (player2.get(count_p2).getPoison() != 0) { // checa POISON
					int poison_count = player2.get(count_p2).getPoison();

					System.out.println(player2.get(count_p2).name + " has been poisoned.");
					player2.get(count_p2).setHP(player2.get(count_p2).getHP() - 3);
					if (Util.isDead(player2.get(count_p2)) == 1) {
						System.out.println(player2.get(count_p2).getName() + " died!");
						player2.remove(count_p2);
						break;
					}
					player2.get(count_p2).setPoison(poison_count - 1);
				}
				if (player2.get(count_p2).getStun() == 1) { // checa STUN
					System.out.println(player2.get(count_p2).name + " is stunned.");
					player2.get(count_p2).setStun(0);
					count_p2++;
					break;
				}

				System.out.println(player2.get(count_p2).name + ", choose your attack.");
				System.out.println("Vida: " + player2.get(count_p2).getHP());
				System.out.println("Mana: " + player2.get(count_p2).getMana());
				System.out.println("Options: ");

				int num = player2.get(count_p2).getOptions(scanner);
				int result = player2.get(count_p2).attack(num, player1, player2, scanner);
				if(result >= 0){
					if(result < count_p1){
						count_p1--;
					}
					else if(result == count_p1 && result == player1.size()){
						count_p1 = 0;
					}
				}
				else if (result == -1) {
					i--; // ocorreu um erro, turno se repete
					flag = 1;
				}

				if(flag == 1) continue;
				player2.get(count_p2).regenMana();
				count_p2++;
				if(count_p2 == player2.size()) count_p2 = 0;
			}
		}

		if (player1.isEmpty() == true) System.out.println("Player 2 wins!");
		else                           System.out.println("Player 1 wins!");

		if (scanner != null) scanner.close();
	}
}