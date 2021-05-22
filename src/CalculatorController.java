import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Currency;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class CalculatorController {
    private static final NumberFormat currency = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentage = NumberFormat.getPercentInstance();

    private BigDecimal tipPercentage = new BigDecimal(0.15);

    @FXML
    private Label amountLabel;

    @FXML
    private Label percentageLabel;

    @FXML
    private Label tipLabel;

    @FXML
    private Slider percentageSlider;

    @FXML
    private TextField amountTextField;

    @FXML
    private Label totalLabel;

    @FXML
    private TextField tipTextField;

    @FXML
    private TextField totalTextField;

    private void updateResults (BigDecimal newTipPercentage, BigDecimal newAmount) {
      tipPercentage = newTipPercentage;
      // percentageLabel.setText(percentage.format(tipPercentage));
      BigDecimal tip = newAmount.multiply(tipPercentage);
      BigDecimal total = newAmount.add(tip);
      
      tipTextField.setText(currency.format(tip));
      totalTextField.setText(currency.format(total));
    }

    public void initialize() {
      currency.setRoundingMode(RoundingMode.HALF_UP);

      percentageLabel.textProperty().bind(percentageSlider.valueProperty().asString("%.0f%%"));
      
      percentageSlider.valueProperty().addListener(
        new ChangeListener<Number>(){
          @Override
          public void changed(javafx.beans.value.ObservableValue<? extends Number> arg0, Number oldNumber, Number newNumber) {
            updateResults(new BigDecimal(newNumber.intValue() / 100.0), new BigDecimal(amountTextField.getText()));
          };
        }
      );

      amountTextField.textProperty().addListener(
        new ChangeListener<String>(){
          @Override
          public void changed(javafx.beans.value.ObservableValue<? extends String> arg0, String oldNumber, String newNumber) {
            updateResults(tipPercentage, new BigDecimal(newNumber));
          };
        }
      );
    }

}
