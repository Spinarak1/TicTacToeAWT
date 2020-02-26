import javax.swing.*;
import java.awt.*;

public class SettingsDialog extends JDialog {
    public JComboBox<StartingPlayer> startingPlayerComboBox;
    public JComboBox<PlayerType> crossTypeBox;
    public JComboBox<PlayerType> circleTypeBox;
    public Settings settings;

    public void saveButtonPressed(){
        settings.circleType = (PlayerType)circleTypeBox.getSelectedItem();
        settings.crossType = (PlayerType)crossTypeBox.getSelectedItem();
        settings.startingPlayer = (StartingPlayer)startingPlayerComboBox.getSelectedItem();
        setVisible(false);
        dispose();
    }
    public SettingsDialog(JFrame parentFrame, Settings settings){
        super(parentFrame, "Settings", true);
        this.settings = settings;
        JPanel topPanel = new JPanel();
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(3, 2));
        topPanel.add(gridPanel);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        StartingPlayer[] startingPlayerStrings = { StartingPlayer.CIRCLE, StartingPlayer.CROSS, StartingPlayer.RANDOM };
        startingPlayerComboBox = new JComboBox<StartingPlayer>(startingPlayerStrings);
        switch (settings.startingPlayer){
            case CROSS:
                startingPlayerComboBox.setSelectedIndex(1);
                break;
            case CIRCLE:
                startingPlayerComboBox.setSelectedIndex(0);
                break;
            case RANDOM:
                startingPlayerComboBox.setSelectedIndex(2);
                break;
        }
        gridPanel.add(new JLabel("starting Player"));
        gridPanel.add(startingPlayerComboBox);
        add(topPanel);

        JButton cancelButton = new JButton("Cancel");
        JButton saveButton = new JButton("Save");
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        topPanel.add(buttonPanel);



        cancelButton.addActionListener((event) -> {
            setVisible(false);
            dispose();
        });
        saveButton.addActionListener((event) -> {
            saveButtonPressed();
        });
        PlayerType[] playerTypeStrings = {PlayerType.CPU_EASY, PlayerType.CPU_HARD, PlayerType.HUMAN};
        crossTypeBox = new JComboBox<PlayerType>(playerTypeStrings);
        crossTypeBox.setSelectedItem(settings.crossType);
        circleTypeBox = new JComboBox<PlayerType>(playerTypeStrings);
        circleTypeBox.setSelectedItem(settings.circleType);
        gridPanel.add(new JLabel("Cross Player Type"));
        gridPanel.add(crossTypeBox);
        gridPanel.add(new JLabel("Circle Player Type"));
        gridPanel.add(circleTypeBox);

        pack();
        setVisible(true);
        //
    }
}//modalne otwieranie okna(modality)
//znalezc blad
//
