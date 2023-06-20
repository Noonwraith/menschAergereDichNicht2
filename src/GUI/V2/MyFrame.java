package GUI.V2;
import Controls.Control;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;

public class MyFrame extends JFrame {

    private final MyPanel panel;
    private final Control control;

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
    /*
    @Override
    public final Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        Dimension prefSize = null;
        Component c = getParent();
        if (c == null) {
            prefSize = new Dimension(
                    (int)d.getWidth(),(int)d.getHeight());
        } else if (c!=null &&
                c.getWidth()>d.getWidth() &&
                c.getHeight()>d.getHeight()) {
            prefSize = c.getSize();
        } else {
            prefSize = d;
        }
        int w = (int) prefSize.getWidth();
        int h = (int) prefSize.getHeight();
        // the smaller of the two sizes
        int s = (w>h ? h : w);
        return new Dimension(s,s);
    }

    /*
    @Override
    public void paint(Graphics g) {
        Dimension d = this.getSize();
        if(d.height > d.width){
            this.setSize(d.width, d.width);
        } else {
            this.setSize(d.height, d.height);
        }
        super.paint(g);
    }
    */

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
        jMenu.add(new JMenuItem("New Game"));
        jMenu.add(addJMenuItemNextPlayer());
        return jMenu;
    }

    private JMenuItem addJMenuItemNextPlayer(){
        JMenuItem jMenuItem = new JMenuItem("Next Player");
        jMenuItem.addActionListener(e -> control.nextPlayer());
        return jMenuItem;
    }





    public MyPanel getPanel() {
        return panel;
    }
}
