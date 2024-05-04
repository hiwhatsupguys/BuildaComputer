import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Lead Author(s):
 *
 * @author Anthony Bazalaki, Elias Zarate
 * <p>
 * <p>
 * Additional Resources:
 * <p>
 * https://stackoverflow.com/questions/26853598/jpanel-not-showing-components
 * <p>
 * Class Responsibilities:
 * The StoreView Class is responsible for displaying all the information a user needs
 * to buy parts and build thier comptuter.
 */

// Store view is a JFrame
public class StoreView extends JFrame {
    // A StoreView has...
    private JPanel mainPanel;
    // a JPanel currentPanel
    private JPanel currentPanel;
    // a JPanel storePanel
    private JPanel storePanel;
    // a JPanel homePanel
    private JPanel homePanel;
    // a JPanel pickPartTypePanel
    private JPanel pickPartTypePanel;
    // a JPanel specsPanel
    private JPanel specsPanel;
    // a JPanel partSelectButtonsPanel
    private JPanel partSelectButtonsPanel;
    // a JPanel buySellButtonsPanel
    private JPanel buySellButtonsPanel;
    // a JPanel partInfoPanel
    private JPanel partInfoPanel;
    // a JPanel topPanel
    private JPanel topPanel;
    // a Border panelBorder
    private Border panelBorder;
    // a Color textColor that is final
    private final Color textColor;
    // a Color borderColor that is final
    private final Color borderColor;
    // a Color panelColor that is final
    private final Color panelColor;
    // a Array of JButons partSelectButtons
    private JButton[] partSelectButtons;
    // a Width
    private int WIDTH;
    // a Height
    private int HEIGHT;
    // a StoreModel storeModel
    private StoreModel storeModel;
    // a Controller controller
    private Controller controller;
    // a PartInventory partInventory
    private PartInventory partInventory;
    // an Array of strings partTypes
    private String[] partTypes;
    // a number of part types
    private int numberOfPartTypes;
    // a JComboBox partSelectComboBox
    private JComboBox partSelectComboBox;
    // a JButton buyButton (for buying things in the store)
    private JButton buyButton;
    // a JButton sellButton (for selling things to the store/returning items that the user may not want)
    private JButton sellButton;
    // a JButton storeTabButton
    private JButton storeButton;
    // a JButton homeTabButton
    private JButton homeButton;
    // a JLable partInfoLable
    private JLabel partInfoLabel;
    private JLabel balanceLabel;
    // a Part currentPart
    private Part currentPart;
    private String currentPartType;
    private int storePanelHeight;
    private double balance;
    private final DecimalFormat balanceFormat = new DecimalFormat(".00");
    private JPanel topButtonsPanel;
    private JPanel computerPartsPanel;
    private JPanel userPartsPanel;
    private ArrayList<JCheckBox> checkBoxes;
    
    /**
     * constructor for a store view
     *
     * @param storeModel
     */
    public StoreView(StoreModel storeModel) {
        // this is to make it look the same on mac and windows
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // initializations
        this.storeModel = storeModel;
        textColor = Color.white;
        borderColor = Color.white;
        panelColor = Color.darkGray;
        WIDTH = 800;
        HEIGHT = 450;
        partInventory = storeModel.getPartInventory();
        controller = new Controller(storeModel, this);
        partTypes = partInventory.getPartTypes();
        numberOfPartTypes = partInventory.getNumberOfPartTypes();
        partSelectButtons = new JButton[numberOfPartTypes];
        partSelectComboBox = new JComboBox();
        // border (color and thickness)
        panelBorder = BorderFactory.createLineBorder(borderColor, 4);
        
        setTitle("Build a Computer");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
        setResizable(false);
        // puts in center of screen
        setLocationRelativeTo(null);
        
        // mainPanel (has both store, home, and top panels)
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        setupStorePanel();
        setupTopPanel();
        setupHomePanel();
        
        // add panels to storePanel
        storePanel.add(pickPartTypePanel, BorderLayout.CENTER);
        storePanel.add(specsPanel, BorderLayout.EAST);
        
        // add panels to homePanel
        
        // add panels to mainPanel
        mainPanel.add(storePanel, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        add(mainPanel);
        setVisible(true);
        
        // currentPanel
//        currentPanel = storePanel;
        setCurrentPanel(storePanel);
        
        updateTopPanel();
        
    }
    
    
    /**
     *
     */
    private void setupStorePanel() {
        
        // storePanel
        storePanel = new JPanel();
        storePanel.setLayout(new BorderLayout());
        storePanelHeight = (int) (HEIGHT * 0.92);
        storePanel.setPreferredSize(new Dimension(WIDTH, storePanelHeight));
//        storePanel.setBackground(Color.white);
        
        // partSelectButtons
        partSelectButtonsPanel = new JPanel();
        // testing
        partSelectButtonsPanel.setBackground(Color.green);
        
        // add the buttons with part types to the button panel
        GridLayout gridLayout = new GridLayout();
        // we might want to set rows and columns
        gridLayout.setRows(3);
//        gridLayout.setColumns(3);
        int gap = WIDTH / 100;
        gridLayout.setHgap(gap);
        gridLayout.setVgap(gap);
        partSelectButtonsPanel.setLayout(gridLayout);
//        buttonsPanel.setPreferredSize(new Dimension(100, 100));
        for (int i = 0; i < numberOfPartTypes; i++) {
            JButton button = new JButton(partTypes[i]);
            button.addActionListener(controller);
            button.setPreferredSize(new Dimension(100, 100));
            button.setFont(new Font("Verdana", Font.PLAIN, 20));
            partSelectButtonsPanel.add(button);
            partSelectButtons[i] = button;
        }
        
        // pickPartPanel
        pickPartTypePanel = new JPanel();
        pickPartTypePanel.setLayout(new BorderLayout());
        pickPartTypePanel.setPreferredSize(new Dimension(WIDTH / 2, storePanelHeight));
        pickPartTypePanel.setBackground(panelColor);
        pickPartTypePanel.setBorder(panelBorder);
        // make JLabel, set text and center it
        JLabel pickPartLabel = new JLabel("Select a type of part to buy", SwingConstants.CENTER);
        // centers text, optional
//        pickPartLabel.setVerticalAlignment(SwingConstants.CENTER);
        pickPartLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        pickPartLabel.setForeground(textColor);
        pickPartTypePanel.add(pickPartLabel, BorderLayout.NORTH);
        pickPartTypePanel.add(partSelectButtonsPanel, BorderLayout.CENTER);
        
        // specsPanel
        specsPanel = new JPanel();
        specsPanel.setLayout(new BorderLayout());
        specsPanel.setPreferredSize(new Dimension(WIDTH / 2, storePanelHeight));
        specsPanel.setBorder(panelBorder);
        specsPanel.setBackground(panelColor);
        
        // part info
        partInfoLabel = new JLabel("", SwingConstants.LEFT);
        partInfoLabel.setFont(new Font("Verdana", Font.PLAIN, 25));
        partInfoPanel = new JPanel();
        FlowLayout partInfoPanelLayout = new FlowLayout();
        partInfoPanelLayout.setAlignment(FlowLayout.LEFT);
        partInfoPanel.setLayout(partInfoPanelLayout);
//        partInfoPanel.setOpaque(false);
        partInfoPanel.add(partInfoLabel);
        
        // buy/sell buttons
        buyButton = new JButton("Buy");
        buyButton.addActionListener(controller);
        sellButton = new JButton("Sell");
        sellButton.addActionListener(controller);
        buySellButtonsPanel = new JPanel();
        buySellButtonsPanel.setLayout(new FlowLayout());
        // makes it transparent
        buySellButtonsPanel.setOpaque(false);
        // add buttons to panel
        buySellButtonsPanel.add(buyButton);
        buySellButtonsPanel.add(sellButton);
    }
    
    /**
     * The setupTopPanel method creates the top panel and adds it to the top of
     * the BorderLayout in the store view
     */
    private void setupTopPanel() {
        int topPanelHeight = HEIGHT - storePanelHeight;
        
        // topButtonsPanel
        topButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topButtonsPanel.setPreferredSize(new Dimension(WIDTH / 2, topPanelHeight));
        storeButton = new JButton("Store");
        homeButton = new JButton("Home");
        storeButton.addActionListener(controller);
        homeButton.addActionListener(controller);
        topButtonsPanel.add(storeButton);
        topButtonsPanel.add(homeButton);
        
        // topPanel
        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(WIDTH, topPanelHeight));
        topPanel.setLayout(new BorderLayout());
        topPanel.add(topButtonsPanel, BorderLayout.WEST);
        balanceLabel = new JLabel("");
        topPanel.add(balanceLabel, BorderLayout.EAST);
    }
    
    /**
     * The setupHomePanel method creates a new panel called homePanel, this panel will
     * track the list of parts a user has selected thus far as well as what the user will decided to build their
     * computer with
     */
    private void setupHomePanel() {
        homePanel = new JPanel(new BorderLayout());
        // user's parts on the left
        // user's computer on the right (with the parts that are in the computer)
        computerPartsPanel = new JPanel(new GridLayout(100, 1));
        computerPartsPanel.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT));
        computerPartsPanel.setBorder(panelBorder);
        computerPartsPanel.setBackground(Color.cyan);
        userPartsPanel = new JPanel();
        userPartsPanel.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT));
        userPartsPanel.setBackground(Color.blue);
        userPartsPanel.setBorder(panelBorder);
        
        checkBoxes = new ArrayList<>();
        
        
        homePanel.add(userPartsPanel, BorderLayout.WEST);
        homePanel.add(computerPartsPanel, BorderLayout.EAST);
    }
    
    /**
     * the update method updates the store view in its entirety
     */
    public void update() {
        updateSpecsPanel();
        updateTopPanel();
        updateHomePanel();
    }
    
    /**
     * The updateSpecsPanel is updates the specs panel whenever a user is looking through the store's
     * selection of parts
     */
    private void updateSpecsPanel() {
        // removes all components from specsPanel
        specsPanel.removeAll();
        Part[] partsOfType = partInventory.getPartsOfType(currentPartType);
        partSelectComboBox = new JComboBox(partsOfType);
        currentPart = (Part) partSelectComboBox.getSelectedItem();
        if (currentPart != null) {
            String info = currentPart.getInfo();
            info = "<html>" + info + "</html>";
            info = info.replace(",\n", "<br/>");
            partInfoLabel.setText(info);
        }
        
        specsPanel.add(partSelectComboBox, BorderLayout.NORTH);
        specsPanel.add(buySellButtonsPanel, BorderLayout.SOUTH);
        specsPanel.add(partInfoPanel, BorderLayout.CENTER);
        
        // need to call this method to show new components on already visible panel, see this link:
        // https://stackoverflow.com/questions/26853598/jpanel-not-showing-components
        specsPanel.revalidate();
//        specsPanel.repaint();
    }
    
    
    /**
     * The updateTopPanel method updates a user's balance after a part has been selected
     */
    private void updateTopPanel() {
        // UPDATE BALANCE
        balance = storeModel.getUser().getBalance();
        String balanceString = balanceFormat.format(balance);
        balanceLabel.setText("Balance: " + balanceString + "   ");
        topPanel.revalidate();
    }
    
    /**
     * updates changes in homePanel
     */
    private void updateHomePanel() {
        User user = storeModel.getUser();
        PartInventory inventory = user.getInventory();
        ArrayList<Part> ownedParts = inventory.getAllOwnedParts();
        
        // ADDING CHECKBOXES FROM USER INVENTORY!!!
        // CHECK IF THE CHECKBOX IS ALREADY THERE
        homePanel.revalidate();
    }
    
    /**
     * switches the current panel (storePanel or homePanel)
     *
     * @param newPanel
     */
    public void setCurrentPanel(JPanel newPanel) {
        // don't do anything if the newPanel is already the currentPanel
        if (newPanel == currentPanel) return;
        if (currentPanel == null) currentPanel = newPanel;
        
        currentPanel.setVisible(false);
        mainPanel.remove(currentPanel);
        currentPanel = newPanel;
        currentPanel.setVisible(true);
        mainPanel.add(currentPanel, BorderLayout.CENTER);
    }
    
    /**
     * @return
     */
    public ArrayList<JCheckBox> getCheckBoxes() {
        return checkBoxes;
    }
    
    /**
     * @param currentPartType
     */
    public void setCurrentPartType(String currentPartType) {
        this.currentPartType = currentPartType;
    }
    
    /**
     * @return currentPartType
     */
    public String getCurrentPartType() {
        return currentPartType;
    }
    
    /**
     * @return currentPart
     */
    public Part getCurrentPart() {
        return currentPart;
    }
    
    /**
     * @return storePanel
     */
    public JPanel getStorePanel() {
        return storePanel;
    }
    
    /**
     * @return homePanel
     */
    public JPanel getHomePanel() {
        return homePanel;
    }
    
    /**
     * @return currentPanel
     */
    public JPanel getCurrentPanel() {
        return currentPanel;
    }
    
    /**
     * @return partSelectedButtons
     */
    
    public JButton[] getPartSelectButtons() {
        return partSelectButtons;
    }
    
    public JButton getBuyButton() {
        return buyButton;
    }
    
    public JButton getSellButton() {
        return sellButton;
    }
    
    public JButton getStoreButton() {
        return storeButton;
    }
    
    public JButton getHomeButton() {
        return homeButton;
    }
    
    /**
     * The addCheckBox method adds a checkbox to the home panel based off
     * of the user's list of selcted parts
     *
     * @param partToAdd
     */
    public void addCheckBox(Part partToAdd) {
        String textToAdd = partToAdd.toString();
        // checks if the checkbox is already there
        for (JCheckBox checkBox1 : checkBoxes) {
            // if the checkbox is there, then don't do anything
            if (checkBox1.getText().equals(textToAdd)) {
                return;
            }
        }
        // add the checkbox to the panel
        JCheckBox checkBoxToAdd = new JCheckBox(textToAdd);
        checkBoxToAdd.addActionListener(controller);
        userPartsPanel.add(checkBoxToAdd);
        checkBoxes.add(checkBoxToAdd);
    }
    
    /**
     * The removeCheckBox method removes a checkbox from the home panel in the
     * event a user no longer wants the part they have selected
     *
     * @param partToRemove
     */
    public void removeCheckBox(Part partToRemove) {
        String textToRemove = partToRemove.toString();
        JCheckBox checkBoxToRemove = null;
        for (JCheckBox checkBox1 : checkBoxes) {
            if (checkBox1.getText().equals(textToRemove)) {
                checkBoxToRemove = checkBox1;
            }
        }
        if (checkBoxToRemove != null) {
            userPartsPanel.remove(checkBoxToRemove);
            checkBoxes.remove(checkBoxToRemove);
        }
    }
    
    // for testing
//    public void addCheckBox(JCheckBox checkBox) {
//        // checks if the checkbox is already there
//        for (JCheckBox checkBox1 : checkBoxes) {
//            // if the checkbox is there, then don't do anything
//            if (checkBox1.getText().equals(currentPart.toString())) return;
//        }
//        // add the checkbox to the panel
//        checkBox.addActionListener(controller);
//        userPartsPanel.add(checkBox);
//        checkBoxes.add(checkBox);
//    }
    
}
