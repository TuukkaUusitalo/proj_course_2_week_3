package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Map;

public class MainController {

    private Map<String, String> strings;
    private final LocalizationService localizationService = new LocalizationService();
    private final CartService cartService = new CartService();

    private String activeLanguageCode = "en";

    @FXML private ComboBox<String> comboLanguage;
    @FXML private Button btnApplyLanguage;

    @FXML private Label labelChooseLanguage;
    @FXML private Label labelItems;
    @FXML private TextField tfItemCount;

    @FXML private Button btnCreateInputs;
    @FXML private VBox itemsContainer;
    @FXML private Button btnCalculate;
    @FXML private Label labelTotal;

    @FXML
    public void initialize() {
        comboLanguage.getItems().addAll("English", "Finnish", "Swedish", "Japanese", "Arabic");
        comboLanguage.setValue("English");

        btnApplyLanguage.setOnAction(e -> applyLanguage());
        btnCreateInputs.setOnAction(e -> createItemFields());
        btnCalculate.setOnAction(e -> calculateTotal());

        applyLanguage();
    }

    private void applyLanguage() {

        String selected = comboLanguage.getValue();
        System.out.println("applyLanguage() called, selected: " + selected);

        activeLanguageCode = switch (selected) {
            case "Finnish" -> "fi";
            case "Swedish" -> "sv";
            case "Japanese" -> "ja";
            case "Arabic" -> "ar";
            default -> "en";
        };

        System.out.println("Active language code: " + activeLanguageCode);

        strings = localizationService.loadStrings(activeLanguageCode);
        System.out.println("Loaded localization entries: " + (strings == null ? 0 : strings.size()));

        labelChooseLanguage.setText(strings.get("select_language"));
        labelItems.setText(strings.get("enter_items"));
        btnCreateInputs.setText(strings.get("create_fields"));
        btnCalculate.setText(strings.get("calculate"));
    }

    private void createItemFields() {
        itemsContainer.getChildren().clear();
        btnCalculate.setDisable(false);

        String text = tfItemCount.getText();
        if (text == null || text.isBlank()) {
            // nothing to do
            return;
        }

        int count;
        try {
            count = Integer.parseInt(text.trim());
        } catch (NumberFormatException ex) {
            // invalid input; optionally show alert
            return;
        }

        for (int i = 0; i < count; i++) {
            TextField price = new TextField();
            TextField qty = new TextField();

            HBox row = new HBox(10,
                    new Label(strings.get("enter_price") + " #" + (i + 1)),
                    price,
                    new Label(strings.get("enter_quantity") + " #" + (i + 1)),
                    qty
            );

            itemsContainer.getChildren().add(row);
        }
    }

    private void calculateTotal() {
        int count = itemsContainer.getChildren().size();
        double[] prices = new double[count];
        int[] quantities = new int[count];

        for (int i = 0; i < count; i++) {
            HBox row = (HBox) itemsContainer.getChildren().get(i);
            TextField priceField = (TextField) row.getChildren().get(1);
            TextField qtyField = (TextField) row.getChildren().get(3);

            prices[i] = Double.parseDouble(priceField.getText());
            quantities[i] = Integer.parseInt(qtyField.getText());
        }

        ShoppingCart cart = new ShoppingCart();
        double total = cart.calculateTotal(prices, quantities);

        labelTotal.setText(strings.get("total_cost") + ": " + total);

        int cartId = cartService.saveCartRecord(count, total, activeLanguageCode);

        for (int i = 0; i < count; i++) {
            double price = prices[i];
            int qty = quantities[i];
            double subtotal = price * qty;

            cartService.saveCartItem(cartId, i + 1, price, qty, subtotal);
        }
    }
}
