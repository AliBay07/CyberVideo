package ui;

import facade.ui.Account;
import facade.ui.CreditCard;
import facade.ui.SubscriberAccount;
import facade.ui.SubscriptionCard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CreditSubscriberCard {

    public static JPanel displaySubscriberCards(JFrame jFrame,Account account) {
        // Panel principal avec BoxLayout
        JPanel subscriberCardsPanel = new JPanel();
        subscriberCardsPanel.setLayout(new BoxLayout(subscriberCardsPanel, BoxLayout.Y_AXIS));

        // Liste de cartes abonnées (ici, nous supposons que vous avez une liste d'objets SubscriptionCard)
        if (account instanceof SubscriberAccount) {
            List<SubscriptionCard> subscriptionCards = ((SubscriberAccount) account).getSubscriptionCard();

            for (SubscriptionCard subscriptionCard : subscriptionCards) {
                JPanel cardItemPanel = new JPanel();
                cardItemPanel.setLayout(new BoxLayout(cardItemPanel, BoxLayout.X_AXIS));

                // Zone pour l'ID et le solde de la carte abonnée
                JLabel cardInfoLabel = new JLabel("ID: " + subscriptionCard.getId() + ", Solde: " + subscriptionCard.getBalance());
                cardItemPanel.add(cardInfoLabel);

                // Bouton pour afficher les cartes de crédit
                JButton showCreditCardsButton = new JButton("Voir les cartes de crédit");
                cardItemPanel.add(showCreditCardsButton);

                // Ajouter cardItemPanel à subscriberCardsPanel
                subscriberCardsPanel.add(cardItemPanel);

                List<CreditCard> creditCards = account.getCreditCard();

                // Ajouter un ActionListener au bouton pour afficher le dialogue des cartes de crédit
                showCreditCardsButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Appeler une fonction pour afficher le dialogue des cartes de crédit
                        JDialog creditCardsDialog = new JDialog(jFrame, "Paiement", true);
                        creditCardsDialog.setTitle("Liste des cartes de crédit");
                        creditCardsDialog.setSize(400, 300);
                        creditCardsDialog.setLocationRelativeTo(null);

                        // Panel principal avec BoxLayout
                        JPanel creditCardsPanel = new JPanel();
                        creditCardsPanel.setLayout(new BoxLayout(creditCardsPanel, BoxLayout.Y_AXIS));

                        // Ajouter des JLabels pour chaque carte de crédit
                        for (CreditCard creditCard : creditCards) {
                            JLabel creditCardLabel = new JLabel("ID: " + creditCard.getId() + ", Banque: " + creditCard.getBank());
                            creditCardsPanel.add(creditCardLabel);
                        }

                        creditCardsDialog.getContentPane().add(creditCardsPanel);
                        creditCardsDialog.setVisible(true);
                    }
                });
            }
        }

        return subscriberCardsPanel;
    }

}
