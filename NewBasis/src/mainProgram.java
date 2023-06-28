import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.CertificateNotYetValidException;
import java.util.InputMismatchException;
import javax.swing.JOptionPane;

public class mainProgram {

	public static void main(String[] args) throws Exception {
		boolean endMain = false;
		int menuChoice;
		while (!endMain) {
			menuChoice = Integer
					.parseInt(JOptionPane.showInputDialog("1) Console view\n" + "2) UI view\n" + "3) EXIT\n"));

			switch (menuChoice) {

			case 1:
				JOptionPane.showMessageDialog(null, "Console!");
				Electionmain.main(args);
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "UI!");
				Electionmain.main(args);
				break;
			case 3:
				JOptionPane.showMessageDialog(null, "Exit!");
				endMain = true;
				break;
			default:
				JOptionPane.showMessageDialog(null, "wrong input! , enter value between 1-3");
				break;
			}
		}
	}
}