import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.ArrayList;


class Cell extends JComponent {
    public Player player = null;
    public boolean mousePressed = false;
    public ArrayList<CellClickedListener> cellClickedListeners = new ArrayList<CellClickedListener>();
    private static final long serialVersionUID = 1L;
    private Image imgCross;
    private Image imgCircle;
    public Cell() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for(CellClickedListener i: cellClickedListeners){
                    i.cellClicked();
                }
            }
        });
    }

    public void setImgCross(Image imgCross) {
        this.imgCross = imgCross;
    }

    public void setImgCircle(Image imgCircle) {
        this.imgCircle = imgCircle;
    }
    @Override
    public void paintComponent(Graphics g)
    {
        try {
            //super.paintComponent(g);
            g.setColor(Color.gray);
            g.fillRect(0, 0, getWidth(), getHeight());
            if (player == Player.CIRCLE) {
                //g.setColor(Color.green);
                //g.fillOval(0, 0, getWidth(), getHeight());
                //g.drawArc(0,0,getWidth(),getHeight(),180, 360);
                //g.drawOval(0, 0, getWidth(), getHeight());
                g.drawImage(imgCircle, 0, 0, getWidth(), getHeight(), this);
            } else if (player == Player.CROSS) {
                //g.setColor(Color.red);
                //g.fillRect(getWidth()/4,getHeight()/4,getWidth()/2, getHeight()/2);
                //g.drawLine(0, 0, getWidth(), getHeight());
                //g.drawLine(getWidth(), 0, 0, getHeight());
                g.drawImage(imgCross, 0, 0, getWidth(), getHeight(), this);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, "Problem z wczytaniem grafiki");
        }

    }
    public void addCellClickedListener(CellClickedListener cellClickedListener){
        cellClickedListeners.add(cellClickedListener);
    }
    public void setPlayer(Player player){
        this.player = player;
        repaint();
    }
}//narysuj puste kółko i krzyżyk