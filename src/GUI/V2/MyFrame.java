package GUI.V2;

import Controls.Control;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import javax.swing.*;

public class MyFrame extends JFrame {

    private final MyPanel panel;
    private Control control;

    public MyFrame(Control control){
        this.control = control;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel = new MyPanel(control);

        setUpLookAndFeel();

        this.setTitle("Mensch ärgere Dich nicht");

        this.setJMenuBar(setUpMenuBar());
        this.add(panel);
        pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void setUpLookAndFeel(){
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private JMenuBar setUpMenuBar(){
        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(setUpMenuGame());
        jMenuBar.add(setUpMenuFile());
        jMenuBar.add(setUpJMenuView());
        return jMenuBar;
    }

    private JMenu setUpMenuFile(){
        return new JMenu("File");
    }

    private JMenu setUpJMenuView(){
        JMenu jMenu = new JMenu("View");
        jMenu.add(new JMenuItem("Colors"));
        return jMenu;
    }

    private JMenu setUpMenuGame(){
        JMenu jMenu = new JMenu("Game");
        jMenu.add(addJMenuItemNewGame());
        jMenu.add(addJMenuItemNextPlayer());
        return jMenu;
    }

    private JMenuItem addJMenuItemNewGame(){
        JMenuItem jMenuItem = new JMenuItem("New Game");
        jMenuItem.addActionListener(e -> control.newGame());
        return jMenuItem;
    }

    private JMenuItem addJMenuItemNextPlayer(){
        JMenuItem jMenuItem = new JMenuItem("Next Player");
        jMenuItem.addActionListener(e -> control.nextPlayer());
        return jMenuItem;
    }

    public MyPanel getPanel() {
        return panel;
    }

    public void setControl(Control control){
        this.control = control;
    }
}
