package GUI.V2;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;

public class MyFrame extends JFrame {

    private int boardSize = 1000;

    private JDialog jDialog = new JDialog();

    public MyFrame(){
        setUpLookAndFeel();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setTitle("Mensche ärgere Dich nicht");

        this.setJMenuBar(setUpMenuBar());

        this.add(new MyPanel());
        pack();
        this.setSize(boardSize, boardSize);
        //this.setResizable(false);
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
        jMenuBar.add(setUpMenuFile());
        jMenuBar.add(setUpJMenuView());
        return jMenuBar;
    }

    private JMenu setUpMenuFile(){
        JMenu jMenu = new JMenu("File");
        return  jMenu;
    }

    private JMenu setUpJMenuView(){
        JMenu jMenu = new JMenu("View");
        jMenu.add(new JMenuItem("Colors"));
        return jMenu;
    }

    /**
     * Getter & Setter
     */
    public int getBoardSize() {
        return boardSize;
    }
}
