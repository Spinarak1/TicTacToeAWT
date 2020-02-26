import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class MyFrame extends JFrame{

    public static final int HEIGHT = 300;
    public static final int WIDTH = 300;
    private JButton menuButton;
    private JButton newGameButton;
    private JButton loadButton;
    private JButton saveButton;
    //private JButton settingsButton;
    private JLabel currentPlayerLabel;
    private JLabel computerNotification;
    private Game game;
    private Cell gameMap[][] = new Cell[3][3];

    public void buttonPressed(int x, int y){
        if(game.getGameState() != GameState.FINISHED) {
            try {
                game.move(x, y);
                showPlayerInCell(x, y);
                showCurrentPlayer();
                if (game.getCurrentPlayerType() == PlayerType.CPU_EASY) {
                    try {
                        computerNotification.setText("Komputer mysli");
                        int[] randomCell = game.pickComputerMove();
                        game.move(randomCell[0], randomCell[1]);
                        showPlayerInCell(randomCell[0], randomCell[1]);
                        showCurrentPlayer();
                        computerNotification.setText("");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Problem z ruchem komputera");
                    }
                }
            }catch (Exception e){
                JOptionPane.showMessageDialog(this, "Problem z ruchem");
            }
        }
    }
    private void cellDimming(Graphics g){
        if(game.getGameState() == GameState.RUNNING){

        }
    }

    private void showCurrentPlayer(){
        String player = "";
        if(game.getGameState() == GameState.RUNNING) {
            if (game.getCurrentPlayer() == Player.CIRCLE)
                player = "O";
            else
                player = "X";
            currentPlayerLabel.setText("Aktualny gracz: " + player);
        }
        else{
            if(game.getWinner() == null)
                currentPlayerLabel.setText("Remis");
            if(game.getWinner() == Player.CIRCLE)
                currentPlayerLabel.setText("Wygrywa kółko");
            else
                currentPlayerLabel.setText("Wygrywa krzyżyk");
        }
    }
    public void startButtonPressed(){
        game.start();
        for(int y = 0; y<game.MAP_HEIGHT; y++){
            for(int x = 0; x<game.MAP_WIDTH; x++){
                showPlayerInCell(x, y);
            }
        }
        showCurrentPlayer();
    }
    public void showPlayerInCell(int x, int y){
            gameMap[x][y].setPlayer(game.getCell(x, y));
    }
    public void loadButtonPressed(){
        try{
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                File file = fc.getSelectedFile();
                InputStream i = new FileInputStream(file);
                game.load(i);
                for(int y= 0; y< Game.MAP_HEIGHT; y++){
                    for(int x=0; x<Game.MAP_WIDTH; x++){
                        showPlayerInCell(x, y);
                    }
                }
                showCurrentPlayer();
                i.close();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Wczytywanie nie powiodlo sie:"+e.getMessage());
            startButtonPressed();
        }
    }
    public void saveButtonPressed(){
        try {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                FileOutputStream fos = new FileOutputStream(file);
                game.save(fos);
                fos.close();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Zapis nie powiodl sie:"+e.getMessage());
        }
    }
    public void createMenu(){
        JMenuBar menuBar;
        JMenu menu;
        JMenu help;
        JMenuItem startItem;
        JMenuItem loadItem;
        JMenuItem saveItem;
        JMenuItem settingsItem;
        JMenuItem helpItem;
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        help = new JMenu("Help");
        menuBar.add(menu);
        menuBar.add(help);
        startItem = new JMenuItem("Start");
        loadItem = new JMenuItem("Load");
        saveItem = new JMenuItem("Save");
        settingsItem = new JMenuItem("Settings");
        helpItem = new JMenuItem("About program");
        /*menuItem.addActionListener(event ->{
            JOptionPane.showMessageDialog(this, "kliknieto");
        });*/
        startItem.addActionListener((event)->{startButtonPressed();});
        saveItem.addActionListener((event) -> saveButtonPressed());
        loadItem.addActionListener((event) -> loadButtonPressed());
        settingsItem.addActionListener((event) -> new SettingsDialog(this, game.getSettings()));
        helpItem.addActionListener((event) ->{
            JOptionPane.showMessageDialog(this, "Kółko i krzyżyk\n" +
                    "2019 Patryk Krzyzaniak");
        });
        menu.add(startItem);
        menu.add(loadItem);
        menu.add(saveItem);
        menu.add(settingsItem);
        help.add(helpItem);
        setJMenuBar(menuBar);
    }

    public MyFrame() {
        game = new Game();
        /*menuButton = new JButton("Menu");
        newGameButton = new JButton("Start");
        JButton settingsButtton = new JButton("Settings");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        JPanel menuPanel = new JPanel();
        JPanel mainMenuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.X_AXIS));
        mainMenuPanel.setLayout(new BoxLayout(mainMenuPanel, BoxLayout.X_AXIS));
        menuPanel.add(menuButton);

        mainMenuPanel.add(newGameButton);
        mainMenuPanel.add(saveButton);
        mainMenuPanel.add(loadButton);
        mainMenuPanel.add(settingsButtton);*/
        //menuPanel.add(newGameButton);
        //menuPanel.add(saveButton);
        //menuPanel.add(loadButton);
        //menuPanel.add(settingsButtton);
        Image crossImage = null;
        Image circleImage = null;
        try {
            crossImage = ImageIO.read(new File("C:\\Users\\Spinarak\\Desktop\\cross.jpg"));
            circleImage = ImageIO.read(new File("C:\\Users\\Spinarak\\Desktop\\circle.png"));
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Nie udalo sie wczytac");
        }
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(3, 3));
        for(int y = 0; y<game.MAP_HEIGHT; y++){
            for(int x = 0; x<game.MAP_WIDTH; x++){
                buttons.add(gameMap[x][y] = new Cell());
                gameMap[x][y].setImgCircle(circleImage);
                gameMap[x][y].setImgCross(crossImage);
            }
        }
        currentPlayerLabel = new JLabel();
        computerNotification = new JLabel();
        gameMap[0][0].addCellClickedListener(()->{ buttonPressed(0, 0);});
        gameMap[0][1].addCellClickedListener(()->{ buttonPressed(0, 1);});
        gameMap[0][2].addCellClickedListener(()->{ buttonPressed(0, 2);});
        gameMap[1][0].addCellClickedListener(()->{ buttonPressed(1, 0);});
        gameMap[1][1].addCellClickedListener(()->{ buttonPressed(1, 1);});
        gameMap[1][2].addCellClickedListener(()->{ buttonPressed(1, 2);});
        gameMap[2][0].addCellClickedListener(()->{ buttonPressed(2, 0);});
        gameMap[2][1].addCellClickedListener(()->{ buttonPressed(2, 1);});
        gameMap[2][2].addCellClickedListener(()->{ buttonPressed(2, 2);});
        //newGameButton.addActionListener((event)->{startButtonPressed();});
        //saveButton.addActionListener((event) -> saveButtonPressed());
        //loadButton.addActionListener((event) -> loadButtonPressed());
        //settingsButtton.addActionListener((event) -> new SettingsDialog(this, game.getSettings()));

        setLayout(new GridLayout(4, 1));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(currentPlayerLabel);
        add(computerNotification);
        add(buttons);
        //add(menuPanel);
        //add(mainMenuPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        createMenu();
        setVisible(true);
        game.start();
        showCurrentPlayer();
    }
}
//symulacja myslenia komputera poprzez wyswietlenie komunikatu komputer mysli i odczekanie jednej sekundy
//po wyborze pola przez zamruganie kilkukrotne na odpowiednim polu
//poprawić wygląd okna
//wczytywanie puste pola