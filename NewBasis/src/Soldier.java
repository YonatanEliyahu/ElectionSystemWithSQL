import java.io.Serializable;

public class Soldier extends Citizen implements Serializable {
public boolean isWeapon;

	public Soldier(String name, String id, int yearOfBirth, int age, int idKalpi,boolean isWeapon) throws IdException, IdKalpiException 
	{
		super(name, id, yearOfBirth, age, idKalpi);
		this.isWeapon  = isWeapon; 
	}
public boolean carryWeapon () {
	return this.isWeapon;
	
}

@Override
public boolean getcarryWeapon() {
	return this.isWeapon;
}

public static String getType() {
	return "I.D.F";
}
public String GetType() {
	return getType();
}



@Override
public String toString() {
 		return "-" + "Name: " + getName() + " ID: " + getId() + " age: " + getAge()  + " Status weapon: "+ isWeapon + " "+ "Kalpi ID: " + getIdKalpi();
}

}