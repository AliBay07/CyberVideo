package ui;

import facade.ui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

public class Payment {
    public void afficherPaiement(Account account,JFrame jFrame,Film film) {
        if (account instanceof SubscriberAccount) {
            JDialog dialog = new JDialog(jFrame, "Paiement", true);
            dialog.setSize(400,300);
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
            JLabel labelTexte = new JLabel("Sélectionnez une carte de paiement :");
            mainPanel.add(labelTexte, gbc);

            // Panel pour les cartes de crédit
            JPanel creditCardPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

            // Checkbox pour la carte de crédit
            JCheckBox creditCardCheckbox = new JCheckBox();
            creditCardPanel.add(creditCardCheckbox);

            // Récupérer la première carte de crédit (ou la seule carte si vous en avez qu'une)
            CreditCard creditCard = account.getCreditCard();

            // Zone pour l'icône de la carte de crédit
            ImageIcon creditCardIcon = new ImageIcon("src/ui/Images/credit_card.png");
            creditCardIcon = ScaleImage.scaleImageIcon(creditCardIcon, 20, 20);
            JLabel creditCardLabel = new JLabel(creditCardIcon);
            creditCardPanel.add(creditCardLabel);

            // Nom et prénom du user de la carte
            User user = account.getUser();
            JLabel userInfoLabel = new JLabel("Nom: " + user.getLastName() + ", Prénom: " + user.getFirstName());
            creditCardPanel.add(userInfoLabel);

            gbc.gridy = 1;
            mainPanel.add(creditCardPanel, gbc);

            // Bouton "Ajouter une carte de crédit"
            JButton addCreditCardButton = new JButton("Ajouter une carte de crédit");
            gbc.gridy = 2;
            mainPanel.add(addCreditCardButton, gbc);

            // Panel pour les cartes abonnées
            JPanel subscriberCardsPanel = new JPanel();
            subscriberCardsPanel.setLayout(new BoxLayout(subscriberCardsPanel, BoxLayout.Y_AXIS));

            // Liste de cartes abonnées (ici, nous supposons que vous avez une liste d'objets SubscriptionCard)
            List<SubscriptionCard> subscriptionCards = ((SubscriberAccount) account).getSubscriptionCard();

            for (SubscriptionCard subscriptionCard : subscriptionCards) {
                JPanel cardItemPanel = new JPanel();
                cardItemPanel.setLayout(new BoxLayout(cardItemPanel, BoxLayout.X_AXIS));

                // Checkbox pour la carte abonnée
                JCheckBox cardCheckbox = new JCheckBox();
                cardItemPanel.add(cardCheckbox);

                // Zone pour l'ID et le solde de la carte abonnée
                JLabel cardInfoLabel = new JLabel("ID: " + subscriptionCard.getId() + ", Solde: " + subscriptionCard.getBalance());
                cardItemPanel.add(cardInfoLabel);

                subscriberCardsPanel.add(cardItemPanel);
            }

            gbc.gridy = 3;
            mainPanel.add(subscriberCardsPanel, gbc);

            // Bouton "Sélectionner cette carte"
            JButton selectCardButton = new JButton("Sélectionner cette carte");


            selectCardButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Vérifiez d'abord si la case à cocher de la carte de crédit est sélectionnée
                    boolean creditCardSelected = creditCardCheckbox.isSelected();
                    CreditCard selectedCreditCard;
                    SubscriptionCard selectedSubscriptionCard;
                    if (creditCardSelected) {
                        selectedCreditCard = account.getCreditCard();

                        JPanel validateJPanel = new JPanel(new GridBagLayout());
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.gridx = 0;
                        gbc.gridy = 0;
                        gbc.anchor = GridBagConstraints.CENTER;
                        gbc.insets = new Insets(5, 5, 5, 5);

                        JLabel labelName = new JLabel("Film : "+film.getName());
                        validateJPanel.add(labelName, gbc);

                        gbc.gridy++;
                        JLabel labelDate = new JLabel("Date : "+String.valueOf(Calendar.getInstance().getTime()));
                        validateJPanel.add(labelDate, gbc);

                        gbc.gridy++;
                        JLabel labelCarte = new JLabel("Paiement par carte bancaire");
                        validateJPanel.add(labelCarte, gbc);

                        gbc.gridy++;
                        JLabel labelBank = new JLabel(selectedCreditCard.getBank());
                        validateJPanel.add(labelBank, gbc);

                        dialog.setTitle("Paiement effectué");
                        dialog.getContentPane().removeAll();
                        dialog.getContentPane().add(validateJPanel);
                        dialog.revalidate();
                        //accepter paiement et renvoyer vers une page de validation
                        //la page est surement dans affichage film et cette fonction return la carte
                    } else {
                        for (int i = 0; i < subscriberCardsPanel.getComponentCount(); i++) {
                            if (subscriberCardsPanel.getComponent(i) instanceof JPanel) {
                                JPanel cardItemPanel = (JPanel) subscriberCardsPanel.getComponent(i);
                                if (cardItemPanel.getComponent(0) instanceof JCheckBox) {
                                    JCheckBox checkbox = (JCheckBox) cardItemPanel.getComponent(0);
                                    if (checkbox.isSelected()) {
                                        selectedSubscriptionCard = subscriptionCards.get(i);
                                        //accepter paiement si solde >= 4e sinon erreur choisir autre carte puis renvoyer vers une page de validation
                                        dialog.dispose();
                                    }
                                }
                            }
                        }
                    }

                    // Vous avez maintenant soit selectedCreditCard ou selectedSubscriptionCard avec la carte sélectionnée.
                    // Vous pouvez faire ce que vous avez besoin de faire avec la carte ici.
                }
            });
            gbc.gridy = 4;
            mainPanel.add(selectCardButton, gbc);

            jFrame.setVisible(true);
            dialog.setVisible(true);
        } else {
            // Gérer le cas où ce n'est pas un compte abonné
        }
    }
}
