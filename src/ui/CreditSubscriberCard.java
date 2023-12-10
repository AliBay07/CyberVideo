package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// ... (autres imports)

public class CreditSubscriberCard {
    private JRadioButton selectedRadioButton = null;
    private SubscriptionCard selectedSubscriptionCard = null;

    public JPanel displaySubscriberCards(JFrame jFrame, SubscriberAccount account) {
        // Panel principal avec BoxLayout
        JDialog dialog = new JDialog(jFrame, "Cartes d'Abonnement", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(null);

        // Panel principal avec GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        dialog.getContentPane().add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Texte en haut
        JLabel labelTexte = new JLabel("Sélectionnez une carte d'abonnement :");
        mainPanel.add(labelTexte, gbc);

        // Panel pour les cartes d'abonnement
        JPanel subscriptionCardPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // ButtonGroup pour les cartes d'abonnement
        ButtonGroup subscriptionCardGroup = new ButtonGroup();

        // Radio bouton pour la carte d'abonnement
        JRadioButton subscriptionCardRadioButton = null;

        ArrayList<SubscriptionCard> subscriptionCards = account.getSubscriptionCard();
        subscriptionCardPanel = new JPanel();
        subscriptionCardPanel.setLayout(new BoxLayout(subscriptionCardPanel, BoxLayout.Y_AXIS));

        for (SubscriptionCard subscriptionCard : subscriptionCards) {
            JPanel cardItemPanel = new JPanel();
            cardItemPanel.setLayout(new BoxLayout(cardItemPanel, BoxLayout.X_AXIS));

            // Radio bouton pour la carte d'abonnement
            subscriptionCardRadioButton = new JRadioButton();
            cardItemPanel.add(subscriptionCardRadioButton);
            subscriptionCardGroup.add(subscriptionCardRadioButton);

            // Informations utilisateur
            JLabel userInfoLabel = new JLabel("ID: " + subscriptionCard.getId() + ", Solde: " + subscriptionCard.getBalance());
            cardItemPanel.add(userInfoLabel);

            // Ajouter cardItemPanel à subscriptionCardPanel
            subscriptionCardPanel.add(cardItemPanel);
        }

        gbc.gridy = 1;
        mainPanel.add(subscriptionCardPanel, gbc);

        // Bouton "Sélectionner cette carte"
        JButton selectCardButton = new JButton("Sélectionner cette carte");
        gbc.gridy = 2;
        mainPanel.add(selectCardButton, gbc);

        if(subscriptionCardRadioButton.isSelected())
        {
            selectedRadioButton = subscriptionCardRadioButton;
            //get selected card from the radio button
            //selectedSubscriptionCard = subscriptionCards.get(subscriptionCardGroup.getButtonCount() - 1);
        }


        selectCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedRadioButton.isSelected()) {
                    //get selected card from the radio button
                    selectedSubscriptionCard = subscriptionCards.get(subscriptionCardGroup.getButtonCount() - 1);
                    System.out.println("Selected card: " + selectedSubscriptionCard.getId());

                    // Affichez les données de la carte dans un nouveau dialogue
                    JDialog cardInfoDialog = new JDialog(dialog, "Détails de la carte d'abonnement", true);
                    cardInfoDialog.setSize(300, 200);
                    cardInfoDialog.setLocationRelativeTo(dialog);

                    JPanel cardInfoPanel = new JPanel(new GridLayout(3, 2));
                    cardInfoPanel.add(new JLabel("ID:"));
                    cardInfoPanel.add(new JLabel(String.valueOf(selectedSubscriptionCard.getId())));
                    cardInfoPanel.add(new JLabel("Solde:"));
                    cardInfoPanel.add(new JLabel(String.valueOf(selectedSubscriptionCard.getBalance())));

                    cardInfoDialog.add(cardInfoPanel);
                    cardInfoDialog.setVisible(true);
                }
            }
        });

        return subscriptionCardPanel;
    }
}
