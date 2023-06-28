import javax.swing.JOptionPane;

public class GraphicalUI implements Messageable {

		@Override
		public void showMessage(String msg) {
			JOptionPane.showMessageDialog(null, msg);
		}

		@Override
		public String getString(String msg) {
			return JOptionPane.showInputDialog(msg);
		}

	}


