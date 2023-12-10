package ui;

import beans.Account;
import beans.CreditCard;
import beans.Film;
import beans.SubscriberAccount;
import beans.User;
import facade.ui.*;
import beans.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Payment {
    private JRadioButton selectedRadioButton = null;
    public void afficherPaiement(Account account, JFrame jFrame, Film film,Controller controller,BasePage afficherFilm) {
        if (account instanceof SubscriberAccount ) { //and nb de reservation < 3
            JDialog dialog = new JDialog(jFrame, "Paiement", true);
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
            JLabel labelTexte = new JLabel("Sélectionnez une carte de paiement :");
            mainPanel.add(labelTexte, gbc);

            // ButtonGroup pour les cartes de crédit
            ButtonGroup creditCardGroup = new ButtonGroup();

            // Radio bouton pour la carte de crédit
            JRadioButton creditCardRadioButton = null;

            List<CreditCard> creditCard = account.getCreditCards();
            JPanel creditCardPanel = new JPanel();
            creditCardPanel.setLayout(new BoxLayout(creditCardPanel, BoxLayout.Y_AXIS));

            for (CreditCard creditCard1 : creditCard) {
                JPanel cardItemPanel = new JPanel();
                cardItemPanel.setLayout(new BoxLayout(cardItemPanel, BoxLayout.X_AXIS));

                // ImageIcon de la carte de crédit
                ImageIcon creditCardIcon = new ImageIcon("src/ui/Images/credit_card.png");
                creditCardIcon = ScaleImage.scaleImageIcon(creditCardIcon, 20, 20);
                JLabel creditCardLabel = new JLabel(creditCardIcon);

                // Ajouter l'icône à cardItemPanel avant les informations
                cardItemPanel.add(creditCardLabel);

                // Radio bouton pour la carte abonnée
                creditCardRadioButton = new JRadioButton();
                cardItemPanel.add(creditCardRadioButton);
                creditCardGroup.add(creditCardRadioButton);

                // Informations utilisateur
                User user = account.getUser();
                JLabel userInfoLabel = new JLabel("Nom: " + user.getLastName() + ", Prénom: " + user.getFirstName());
                cardItemPanel.add(userInfoLabel);

                // Ajouter cardItemPanel à creditCardPanel
                creditCardPanel.add(cardItemPanel);
            }

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
            List<SubscriberCard> subscriptionCards = ((SubscriberAccount) account).getSubscriberCards();
            JRadioButton subsciptionRadioButton = null;
            for (SubscriberCard subscriptionCard : subscriptionCards) {
                JPanel cardItemPanel = new JPanel();
                cardItemPanel.setLayout(new BoxLayout(cardItemPanel, BoxLayout.X_AXIS));

                // Radio bouton pour la carte abonnée
                subsciptionRadioButton = new JRadioButton();
                cardItemPanel.add(subsciptionRadioButton);
                creditCardGroup.add(subsciptionRadioButton);

                // Zone pour l'ID et le solde de la carte abonnée
                JLabel cardInfoLabel = new JLabel("ID: " + subscriptionCard.getId() + ", Solde: " + subscriptionCard.getAmount());
                cardItemPanel.add(cardInfoLabel);

                subscriberCardsPanel.add(cardItemPanel);
            }

            gbc.gridy = 3;
            mainPanel.add(subscriberCardsPanel, gbc);

            // Bouton "Sélectionner cette carte"
            JButton selectCardButton = new JButton("Sélectionner cette carte");

            if(creditCardRadioButton.isSelected())
            {
                selectedRadioButton = creditCardRadioButton;
            }
            else if(subsciptionRadioButton.isSelected())
            {
                selectedRadioButton = subsciptionRadioButton;
            }


            selectCardButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Vérifiez d'abord si la case à cocher de la carte de crédit est sélectionnée
                    CreditCard selectedCreditCard;
                    SubscriberCard selectedSubscriptionCard;
                    boolean boolcredit = false;

                        for (int i = 0; i < creditCardPanel.getComponentCount(); i++) {
                            if (creditCardPanel.getComponent(i) instanceof JPanel) {
                                JPanel cardItemPanel = (JPanel) creditCardPanel.getComponent(i);
                                if (cardItemPanel.getComponent(1) instanceof JRadioButton) {
                                    System.out.println("hihi");
                                    JRadioButton radioButton = (JRadioButton) cardItemPanel.getComponent(1);
                                    if (radioButton.isSelected()) {
                                        selectedCreditCard = creditCard.get(i);
                                        //get the radio button selected contained in selectedRadioButton
                                        //get the parent of the radio button
                                        boolcredit = true;
                                        JPanel validateJPanel = new JPanel(new GridBagLayout());
                                        GridBagConstraints gbc = new GridBagConstraints();
                                        gbc.gridx = 0;
                                        gbc.gridy = 0;
                                        gbc.anchor = GridBagConstraints.CENTER;
                                        gbc.insets = new Insets(5, 5, 5, 5);

                                        JLabel labelName = new JLabel("Film : " + film.getName());
                                        validateJPanel.add(labelName, gbc);

                                        gbc.gridy++;
                                        JLabel labelDate = new JLabel("Date : " + String.valueOf(Calendar.getInstance().getTime()));
                                        validateJPanel.add(labelDate, gbc);

                                        gbc.gridy++;
                                        JLabel labelCarte = new JLabel("Paiement par carte bancaire");
                                        validateJPanel.add(labelCarte, gbc);

                                        gbc.gridy++;
                                        JLabel labelBank = new JLabel(String.valueOf(selectedCreditCard.getId()));
                                        validateJPanel.add(labelBank, gbc);

                                        gbc.gridy++;
                                        JButton validateButton = new JButton("Valider");
                                        validateJPanel.add(validateButton, gbc);
                                        validateButton.addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                dialog.dispose();
                                            }
                                        });
                                        //vérfifier que c'est bien ajouté à la bd
                                        //=> fermer la dispose
                                        //fermer après 4 secondes
                                        dialog.setTitle("Paiement effectué");
                                        dialog.getContentPane().removeAll();
                                        dialog.getContentPane().add(validateJPanel);
                                        dialog.revalidate();

                                        Timer timer = new Timer(4000, new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                dialog.dispose();
                                            }
                                        });
                                        timer.setRepeats(false); // Le timer ne se répétera pas
                                        timer.start();

                                        //accepter paiement et renvoyer vers une page de validation
                                        //la page est surement dans affichage film et cette fonction return la carte
                                    }
                                }
                            }
                        }
                    if (!boolcredit) {
                        for (int i = 0; i < subscriberCardsPanel.getComponentCount(); i++) {
                            if (subscriberCardsPanel.getComponent(i) instanceof JPanel) {
                                JPanel cardItemPanel2 = (JPanel) subscriberCardsPanel.getComponent(i);
                                if (cardItemPanel2.getComponent(0) instanceof JRadioButton) {
                                    JRadioButton radioButton2 = (JRadioButton) cardItemPanel2.getComponent(0);
                                    if (radioButton2.isSelected()) {
                                        selectedSubscriptionCard = subscriptionCards.get(i);
                                        if (selectedSubscriptionCard.getAmount() >= 4) {
                                            //creer un panel qui va avoir un gridbaglayout et qui va contenir les infos de la carte
                                            JPanel validateJPanel = new JPanel(new GridBagLayout());
                                            GridBagConstraints gbc = new GridBagConstraints();
                                            gbc.gridx = 0;
                                            gbc.gridy = 0;
                                            gbc.anchor = GridBagConstraints.CENTER;
                                            gbc.insets = new Insets(5, 5, 5, 5);

                                            JLabel labelName = new JLabel("Film : " + film.getName());
                                            validateJPanel.add(labelName, gbc);

                                            gbc.gridy++;
                                            JLabel labelDate = new JLabel("Date : " + String.valueOf(Calendar.getInstance().getTime()));
                                            validateJPanel.add(labelDate, gbc);

                                            gbc.gridy++;
                                            JLabel labelCarte = new JLabel("Paiement par carte abonnée");
                                            validateJPanel.add(labelCarte, gbc);

                                            gbc.gridy++;
                                            JLabel labelID = new JLabel("ID : " + selectedSubscriptionCard.getId());
                                            validateJPanel.add(labelID, gbc);

                                            gbc.gridy++;
                                            selectedSubscriptionCard.setAmount(selectedSubscriptionCard.getAmount() - 4);
                                            System.out.println(selectedSubscriptionCard.getAmount());
                                            JLabel labelSolde = new JLabel("Solde : " + selectedSubscriptionCard.getAmount());
                                            validateJPanel.add(labelSolde, gbc);

                                            gbc.gridy++;
                                            JButton validateButton = new JButton("Valider");
                                            validateJPanel.add(validateButton, gbc);
                                            validateButton.addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    dialog.dispose();
                                                }
                                            });

                                            dialog.setTitle("Paiement effectué");
                                            dialog.getContentPane().removeAll();
                                            dialog.getContentPane().add(validateJPanel);
                                            dialog.revalidate();

                                            //fermer après 4 secondes
                                            Timer timer = new Timer(4000, new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    dialog.dispose();
                                                }
                                            });
                                            timer.setRepeats(false); // Le timer ne se répétera pas
                                            timer.start();
                                        }
                                        //accepter paiement si solde >= 4e sinon erreur choisir autre carte puis renvoyer vers une page de validation
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
        }
        //if not a subscriberAccount
        else {
            JDialog dialog = new JDialog(jFrame, "Paiement", true);
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
            JLabel labelTexte = new JLabel("Sélectionnez une carte de paiement :");
            mainPanel.add(labelTexte, gbc);

            // Panel pour les cartes de crédit
            JPanel creditCardPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

            // ButtonGroup pour les cartes de crédit
            ButtonGroup creditCardGroup = new ButtonGroup();

            // Radio bouton pour la carte de crédit
            JRadioButton creditCardRadioButton = new JRadioButton();
            creditCardPanel.add(creditCardRadioButton);
            creditCardGroup.add(creditCardRadioButton);

            List<CreditCard> creditCard = account.getCreditCards();
            creditCardPanel = new JPanel();
            creditCardPanel.setLayout(new BoxLayout(creditCardPanel, BoxLayout.Y_AXIS));

            for (CreditCard creditCard1 : creditCard) {
                JPanel cardItemPanel = new JPanel();
                cardItemPanel.setLayout(new BoxLayout(cardItemPanel, BoxLayout.X_AXIS));

                // ImageIcon de la carte de crédit
                ImageIcon creditCardIcon = new ImageIcon("src/ui/Images/credit_card.png");
                creditCardIcon = ScaleImage.scaleImageIcon(creditCardIcon, 20, 20);
                JLabel creditCardLabel = new JLabel(creditCardIcon);

                // Ajouter l'icône à cardItemPanel avant les informations
                cardItemPanel.add(creditCardLabel);

                // Radio bouton pour la carte abonnée
                JRadioButton cardRadioButton = new JRadioButton();
                cardItemPanel.add(cardRadioButton);
                creditCardGroup.add(cardRadioButton);

                // Informations utilisateur
                User user = account.getUser();
                JLabel userInfoLabel = new JLabel("Nom: " + user.getLastName() + ", Prénom: " + user.getFirstName());
                cardItemPanel.add(userInfoLabel);

                // Ajouter cardItemPanel à creditCardPanel
                creditCardPanel.add(cardItemPanel);
            }

            gbc.gridy = 1;
            mainPanel.add(creditCardPanel, gbc);

            // Bouton "Ajouter une carte de crédit"
            JButton addCreditCardButton = new JButton("Ajouter une carte de crédit");
            gbc.gridy = 2;
            mainPanel.add(addCreditCardButton, gbc);

            JButton selectCardButton = new JButton("Sélectionner cette carte");

            selectCardButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Vérifiez d'abord si la case à cocher de la carte de crédit est sélectionnée
                    CreditCard selectedCreditCard = null;
                    SubscriptionCard selectedSubscriptionCard;

                    for (CreditCard creditCard : account.getCreditCards()) {
                        if (creditCard.getId() == (creditCard.getId())) {
                            selectedCreditCard = creditCard;
                            break;  // Sortez de la boucle dès que la carte est trouvée
                        }
                    }

                    if (selectedCreditCard != null) {
                        selectedCreditCard = account.getCreditCards().get(0);

                        JPanel validateJPanel = new JPanel(new GridBagLayout());
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.gridx = 0;
                        gbc.gridy = 0;
                        gbc.anchor = GridBagConstraints.CENTER;
                        gbc.insets = new Insets(5, 5, 5, 5);

                        JLabel labelName = new JLabel("Film : " + film.getName());
                        validateJPanel.add(labelName, gbc);

                        gbc.gridy++;
                        JLabel labelDate = new JLabel("Date : " + String.valueOf(Calendar.getInstance().getTime()));
                        validateJPanel.add(labelDate, gbc);

                        gbc.gridy++;
                        JLabel labelCarte = new JLabel("Paiement par carte bancaire");
                        validateJPanel.add(labelCarte, gbc);

                        gbc.gridy++;
                        JLabel labelBank = new JLabel(String.valueOf(selectedCreditCard.getId()));
                        validateJPanel.add(labelBank, gbc);

                        gbc.gridy++;
                        JButton validateButton = new JButton("Valider");
                        validateJPanel.add(validateButton, gbc);
                        validateButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                dialog.dispose();
                            }
                        });
                        dialog.setTitle("Paiement effectué");
                        dialog.getContentPane().removeAll();
                        dialog.getContentPane().add(validateJPanel);
                        dialog.revalidate();

                        Timer timer = new Timer(4000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                dialog.dispose();
                            }
                        });
                        timer.setRepeats(false); // Le timer ne se répétera pas
                        timer.start();

                        //accepter paiement et renvoyer vers une page de validation
                        //la page est surement dans affichage film et cette fonction return la carte
                    }
                }
            });
            gbc.gridy = 4;
            mainPanel.add(selectCardButton, gbc);

            jFrame.setVisible(true);
            dialog.setVisible(true);
        }
    }
}