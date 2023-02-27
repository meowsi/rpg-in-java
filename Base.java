import java.util.*;

public class Base {

	// defines
	public static final int SUCCESS = -2;
	public static final int ERROR = -1;

	String name;
    private int armour, base_hp, base_mana, hp, mana, powerAbility, swiftness, attack, burn, stun, poison;

	// Construtor
    Base(String _nome, int _hp, int _mana, int _PH, int _vel, int _atk, int _arm) {
		this.name = _nome;
		this.base_hp = _hp;
		this.hp = _hp;
		this.mana = _mana;
		this.base_mana = _mana;
		this.powerAbility = _PH;
		this.swiftness = _vel;
		this.attack = _atk;
		this.armour = _arm;
		this.burn = 0;
		this.stun = 0;
		this.poison = 0;
    }

	// Metodos getters
	int getHP()         { return this.hp;           }
	int getArmour()     { return this.armour;       }
	String getName()    { return this.name;         }
	int getBaseHP()     { return this.base_hp;      }
	int getBaseMana()   { return this.base_mana;    }
    int getMana()       { return this.mana;         }
    int getPH()         { return this.powerAbility; }
    int getSwiftness()  { return this.swiftness;    }
    int getAttack()     { return this.attack;       }
	int getBurn() { return this.burn; }
	int getPoison() { return this.poison; }
	int getStun() { return this.stun; }

	// Metodos setters
    void setHP(int _hp)           { this.hp           = _hp;     }
    void setMana(int _mana)       { this.mana         = _mana;   }
    void setPH(int _ph)           { this.powerAbility = _ph;     }
    void setSwiftness(int _vel)   { this.swiftness    = _vel;    }
    void setAttack(int _ataque)   { this.attack       = _ataque; }
	void setArmour(int _armour)  { this.armour      = _armour; }
	void setBurn(int value) { this.burn = value; }
	void setPoison(int value) { this.poison = value; }
	void setStun(int value) { this.stun = value; }
	int getOptions(Scanner scanner) {
		return SUCCESS;
	}

	int attack(int num, ArrayList<Base> enemy, ArrayList<Base> ally, Scanner scanner) {
		return SUCCESS;
	}

	void regenMana(){
    	this.setMana(this.getMana() + 10);
	}
    
}