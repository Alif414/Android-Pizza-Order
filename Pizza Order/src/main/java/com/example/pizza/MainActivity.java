package com.example.pizza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Pizza pizza;
    TextView price;
    TextView pan;
    TextView crustText;
    TextView toppingSelect;
    List<String> toppingList = new ArrayList<>();
    LinearLayout liLay;
    double totTopPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pizza = new Pizza();
        price = findViewById(R.id.price);
        pan = findViewById(R.id.pan);
        crustText = findViewById(R.id.crustSelect);
        toppingSelect = findViewById(R.id.toppingSelect);
        liLay = findViewById(R.id.orderLayout);
    }

    public void radioPrice(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        //Depending on radio button selected, price and text will change
        switch (view.getId()){
            case R.id.size1:
                if (checked) {
                    pizza.setSizePrice(5.00);
                    pan.setText("Personal Pan");
                    liLay.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.size2:
                if (checked) {
                    pizza.setSizePrice(9.00);
                    pan.setText("Regular Pan");
                    liLay.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.size3:
                if (checked) {
                    pizza.setSizePrice(12.00);
                    pan.setText("Large Pan");
                    liLay.setVisibility(View.VISIBLE);
                }
                break;
        }

        switch (view.getId()){
            case R.id.crust1:
                if (checked) {
                    pizza.setCrustPrice(0.00);
                    crustText.setText("Thin");
                    liLay.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.crust2:
                if (checked){
                    pizza.setCrustPrice(1.00);
                    crustText.setText("Sicillian");
                    liLay.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.crust3:
                if (checked){
                    pizza.setCrustPrice(2.50);
                    crustText.setText("Stuffed");
                    liLay.setVisibility(View.VISIBLE);
                }
                break;
        }

        String tPrice = String.valueOf(calcPrice());
        price.setText("RM " + tPrice);
    }

    public void checkClick(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        int checkCount = 0;
        double sum = 0;
        int discCount = 0;

        CheckBox[] checkTopping;
        checkTopping = new CheckBox[]{
                (CheckBox)findViewById(R.id.cheese),
                (CheckBox)findViewById(R.id.pepperoni),
                (CheckBox)findViewById(R.id.chicken),
                (CheckBox)findViewById(R.id.bacon),
                (CheckBox)findViewById(R.id.beef),
                (CheckBox)findViewById(R.id.olives),
                (CheckBox)findViewById(R.id.pineapple),
                (CheckBox)findViewById(R.id.capsicum),
                (CheckBox)findViewById(R.id.mushroom)
        };

        String[] checkTString = {"Cheese", "Pepperoni", "Chicken", "Bacon", "Beef", "Olives", "Pineapple",
                "Capsicum", "Mushroom"};
        double[] pTopPrice = {pizza.cheese_Price, pizza.pepperoniPrice, pizza.chickenPrice, pizza.baconPrice,
        pizza.beefPrice, pizza.olivesPrice, pizza.pineapplePrice, pizza.capsicumPrice, pizza.mushroomPrice};
        double[] prices = { 2.50, 3.00, 2.00, 3.00, 4.00, 2.00, 1.00, 2.50, 1.00 };

        for ( int i = 0; i < checkTopping.length; i++ ) {
            //If item is checked the price will be added and the item will be added to a string
            if(checkTopping[i].isChecked())
            {
                if(!toppingList.contains(checkTString[i]))
                    toppingList.add(checkTString[i]);
                checkCount++;
                pTopPrice[i] = prices[i];
            }else{
                toppingList.remove(checkTString[i]);
                checkCount--;
                pTopPrice[i] = 0.00;
                if(checkCount >= 3) ((CheckBox) view).setEnabled(false);
                else ((CheckBox) view).setEnabled(true);
            }
            //Toppings price is calculated
            sum = sum + pTopPrice[i];
        }


        for ( int i = 1; i < 4; i++ ){
            if(checkTopping[i].isChecked())
                discCount++;
            else
                discCount--;
        }

        //If the 4 meat toppings are selected, there will be a 10% discount
        if (discCount >= 3)
            sum = sum - (sum * 0.1);

        //Total price set into another variable along with discount
        totTopPrice = sum;

        String tPrice = String.valueOf(calcPrice());
        price.setText("RM " + tPrice);
        String cCount = String.valueOf(discCount);

        //Toppings set in the view with a delimiter to separate them
        toppingSelect.setText(TextUtils.join(", ", toppingList));

    }

    private double calcPrice()
    {
        //Total price is calculated
        double  totalPrice = pizza.getSizePrice() + pizza.getCrustPrice() + totTopPrice;

        return totalPrice;
    }
}