import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class BlackJack{

    private class Card{
        String value;
        String type;

        Card(String value, String type){
            this.value = value;
            this.type = type;
        }

        public String toString(){
            return value + "-" + type;
        }

        public int getValue(){
            if("AJQK".contains(value)){
                if(value == "A"){
                    return 11;  
        }
        return 10;
        }
        return Integer.parseInt(value);
     }

     public boolean isAce(){
        return value.equals("A");
     }

     public String getImagePath(){
        return "./cards/" + toString() + ".png";
     }
   }


    ArrayList<Card> deck;
    Random random = new Random();

    Card hiddenCard;
    ArrayList <Card>dealerHand;
    int dealerSum;
    int dealerAceCount;

    ArrayList <Card>playerHand;
    int playerSum;
    int playerAceCount;   

    int boardWidth = 600;
    int boardHeight = boardWidth;

    int cardWidth = 110;
    int cardHeight = 154;

    int betAmount = 0;
    int playerMoney = 1000;

    int chipWidth = 30;
    int chipHeight = chipWidth;

    boolean didWin = false;
    boolean tie = false;

    JFrame frame = new JFrame("BlackJack");
    JPanel gamePanel = new JPanel(){
        @Override 
        public void paintComponent(Graphics g){
            super.paintComponent(g);

            try{
            Image hiddenCardImage = new ImageIcon(getClass().getResource("./cards/Back.png")).getImage();
            if(!standButton.isEnabled()){
                hiddenCardImage = new ImageIcon(getClass().getResource(hiddenCard.getImagePath())).getImage();
            }
            g.drawImage(hiddenCardImage, 20, 20, cardWidth, cardHeight, null);

            for(int i = 0;i<dealerHand.size();i++){
                Card card = dealerHand.get(i);
                Image cardImage = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                g.drawImage(cardImage, cardWidth + 25 + (cardWidth + 5)*i, 20, cardWidth, cardHeight, null);
            }

            for(int i = 0;i<playerHand.size();i++){
                Card card = playerHand.get(i);
                Image cardImage = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                g.drawImage(cardImage, 20 + (cardWidth + 5)*i, 320, cardWidth, cardHeight, null);
            }

            int black = betAmount/100;
            int red = (betAmount-(black*100))/25;
            int green = (betAmount-(black*100+red*25))/5;

            for(int i = 0;i<black;i++){
                Image chipImage = new ImageIcon(getClass().getResource("./chips/Black.png")).getImage();
                g.drawImage(chipImage, 20 + (chipWidth + 5)*i, 480, chipWidth, chipHeight, null);
            }

            for(int i = 0;i<red;i++){
                Image chipImage = new ImageIcon(getClass().getResource("./chips/Red.png")).getImage();
                g.drawImage(chipImage, 20 + (chipWidth + 5)*(i+black), 480, chipWidth, chipHeight, null);
            }

            for(int i = 0;i<green;i++){
                Image chipImage = new ImageIcon(getClass().getResource("./chips/Green.png")).getImage();
                g.drawImage(chipImage, 20 + (chipWidth + 5)*(i+black+red), 480, chipWidth, chipHeight, null);
            }

            if(!standButton.isEnabled()){
                dealerSum = reduceDealerAce();
                playerSum = reducePlayerAce();
                System.out.println("STAY: ");
                System.out.println("Dealer sum: " + dealerSum);
                System.out.println("Player sum: " + playerSum);
                playAgain.setEnabled(true);

                String message = "";
                if(playerSum>21){
                    message = "You bust, dealer wins!";
                    didWin = false;
                    tie = false;

                }
                else if (dealerSum>21){
                    message = "Dealer bust, you win!";
                    didWin = true;
                    tie = false;

            }
                else if(playerSum == dealerSum){
                    message = "Draw!";
                    tie = true;
                }
                else if(playerSum>dealerSum){
                    message = "You win!";
                    didWin = true;
                    tie = false;
                }
                else{
                    message = "Dealer wins!";
                    didWin = false;
                    tie = false;
                }

                g.setFont(new Font("Arial", Font.PLAIN, 30));
                g.setColor(Color.white);
                g.drawString(message, 220, 250);
        }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    };
    
    JPanel buttonPanel = new JPanel();
    JButton hitButton = new JButton("Hit");
    JButton standButton = new JButton("Stand");
    JButton betButton = new JButton("Bet");
    JTextField playerMoneyField = new JTextField("Money: " + playerMoney, 10);
    JButton playAgain = new JButton("Play Again");
    JButton allIn = new JButton("All In");


    BlackJack() {
        startGame();

        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(53, 101, 77));
        frame.add(gamePanel);

        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);
        standButton.setFocusable(false);
        buttonPanel.add(standButton);
        betButton.setFocusable(false);
        buttonPanel.add(betButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(playerMoneyField, BorderLayout.NORTH);
        playAgain.setFocusable(false);
        buttonPanel.add(playAgain);
        allIn.setFocusable(false);
        buttonPanel.add(allIn);

        hitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Card card = deck.remove(deck.size()-1);
                playerSum += card.getValue();
                playerAceCount += card.isAce() ? 1 : 0;
                playerHand.add(card);
                if(reducePlayerAce()>21){
                    hitButton.setEnabled(false);
                }
                betButton.setEnabled(false);
                gamePanel.repaint();
            }
        });

        standButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                hitButton.setEnabled(false);
                standButton.setEnabled(false);

                while(reduceDealerAce()<17){
                    Card card = deck.remove(deck.size()-1);
                    dealerSum += card.getValue();
                    dealerAceCount += card.isAce() ? 1 : 0;
                    dealerHand.add(card);
                }
                betButton.setEnabled(false);
                gamePanel.repaint();
            }
        });

        betButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(playerMoney>=5){
                betAmount += 5;
                playerMoney -= 5;
                }
                playerMoneyField.setText("Money: " + playerMoney);
                gamePanel.repaint();
            }
        });

        playAgain.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(tie){
                    playerMoney += betAmount;
                }
                else if(didWin){
                    playerMoney += betAmount*2;
                }
                betAmount = 0;
                startGame();
                playerMoneyField.setText("Money: " + playerMoney);
                standButton.setEnabled(true);
                hitButton.setEnabled(true);
                gamePanel.repaint();
            }
        });

        allIn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                betAmount += playerMoney;
                playerMoney = 0;
                allIn.setEnabled(false);
                betButton.setEnabled(false);
                playerMoneyField.setText("Money: " + playerMoney);
                gamePanel.repaint();
            }
        });

        gamePanel.repaint();
    }

    public void startGame(){
    
        buildDeck();
        shuffleDeck();
        playAgain.setEnabled(false);

        if(playerMoney>=5){
        betAmount = 5;
        playerMoney -= betAmount;
        playerMoneyField.setText("Money: " + playerMoney);
        gamePanel.repaint();
        betButton.setEnabled(true);
        allIn.setEnabled(true);
        }
        else {
            betButton.setEnabled(false);
            allIn.setEnabled(false);
        }

        dealerHand = new ArrayList<Card>();
        dealerSum = 0;
        dealerAceCount = 0;

        hiddenCard = deck.remove(deck.size()-1);
        dealerSum += hiddenCard.getValue();
        dealerAceCount += hiddenCard.isAce() ? 1 : 0;
        Card card = deck.remove(deck.size()-1);
        dealerSum += card.getValue();
        dealerAceCount += card.isAce() ? 1 : 0;
        dealerHand.add(card);

        System.out.println("DEALER:");
        System.out.println(hiddenCard);
        System.out.println(dealerHand);
        System.out.println("Dealer sum: " + dealerSum);
        System.out.println("Dealer Ace count: " + dealerAceCount);

        playerHand = new ArrayList<Card>();
        playerSum = 0;
        playerAceCount = 0;
        
        Card card1 = deck.remove(deck.size()-1);
        playerSum += card1.getValue();
        playerAceCount += card1.isAce() ? 1 : 0;
        Card card2 = deck.remove(deck.size()-1);
        playerSum += card2.getValue();
        playerAceCount += card2.isAce() ? 1 : 0;
        playerHand.add(card1);
        playerHand.add(card2);

        System.out.println("PLAYER:");
        System.out.println(playerHand);
        System.out.println("Player sum: " + playerSum);
        System.out.println("Player Ace count: " + playerAceCount);
        
    }
    
    public void buildDeck(){
        deck = new ArrayList<Card>();
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] types = {"H", "D", "C", "S"};
        for(int i = 0;i<types.length;i++){
            for(int j = 0;j<values.length;j++){
                deck.add(new Card(values[j], types[i]));
            }
        }
        System.out.println("BUILD DECK:");
        System.out.println(deck);
    }

    public void shuffleDeck(){
        for(int i = 0; i<deck.size();i++){
            int j = random.nextInt(deck.size());
            Card currCard = deck.get(i);
            Card randCard = deck.get(j);
            deck.set(i, randCard);
            deck.set(j, currCard);
        }
        System.out.println("AFTER SHUFFLE");
        System.out.println(deck);
    }

    public int reducePlayerAce(){
        while(playerSum>21 && playerAceCount>0){
            playerSum -= 10;
            playerAceCount--;
        }
        return playerSum;
    }

    public int reduceDealerAce(){
        while(dealerSum>21 && dealerAceCount>0){
            dealerSum -= 10;
            dealerAceCount--;
        }
        return dealerSum;
    }
}

    





