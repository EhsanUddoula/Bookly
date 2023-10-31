package com.example.bookly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.help5g.uddoktapaysdk.UddoktaPay;

import java.util.HashMap;
import java.util.Map;

public class page9_pay_Activity extends AppCompatActivity {
    // Constants for payment
    private static final String API_KEY = "982d381360a69d419689740d9f2e26ce36fb7a50";
    private static final String CHECKOUT_URL = "https://sandbox.uddoktapay.com/api/checkout-v2";
    private static final String VERIFY_PAYMENT_URL = "https://sandbox.uddoktapay.com/api/verify-payment";
    private static final String REDIRECT_URL = "https://uddoktapay.com";
    private static final String CANCEL_URL = "https://uddoktapay.com";

    // Instance variables to store payment information
    private String storedFullName;
    private String storedEmail;
    private String storedAmount;
    private String storedInvoiceId;
    private String storedPaymentMethod;
    private String storedSenderNumber;
    private String storedTransactionId;
    private String storedDate;
    private String storedFee;
    private String storedChargedAmount;

    private String storedMetaKey1;
    private String storedMetaValue1;

    private String storedMetaKey2;
    private String storedMetaValue2;

    private String storedMetaKey3;
    private String storedMetaValue3;

    LinearLayout uiLayout,webView;
    TextView result,name,email,amount,goTo;
    Button payButton;
    String uid,total,nameBox,emailBox;
    WebView UddoktaPayWebView;
    String FULL_NAME,EMAIL,enteredAmount;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page9_pay);
        getSupportActionBar().setTitle("Payment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ColorDrawable color =new ColorDrawable(Color.parseColor("#FFD700"));
        getSupportActionBar().setBackgroundDrawable(color);

        db=FirebaseFirestore.getInstance();
        Bundle bundle =getIntent().getExtras();
        if(bundle != null){
            uid=bundle.getString("user");
            total=bundle.getString("total");
            nameBox=bundle.getString("name");
            emailBox=bundle.getString("email");
            FULL_NAME=nameBox;
            EMAIL=emailBox;
            double ff=Double.parseDouble(total.replaceAll("[^\\d.]",""));
            enteredAmount=Double.toString(ff);
        }

        uiLayout=findViewById(R.id.uilayout);
        webView=findViewById(R.id.weblayout);
        result=findViewById(R.id.result);
        goTo=findViewById(R.id.goToHome);
        name= findViewById(R.id.NamePay);
        email= findViewById(R.id.EmailPay);
        amount= findViewById(R.id.PricePay);
        payButton=findViewById(R.id.payIt);
        UddoktaPayWebView=findViewById(R.id.payview);

        amount.setText(total);
        name.setText(nameBox);
        email.setText(emailBox);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uiLayout.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);

                // Set your metadata values in the map
                Map<String, String> metadataMap = new HashMap<>();
                metadataMap.put("CustomMetaData1", "Meta Value 1");
                metadataMap.put("CustomMetaData2", "Meta Value 2");
                metadataMap.put("CustomMetaData3", "Meta Value 3");

                UddoktaPay.PaymentCallback paymentCallback = new UddoktaPay.PaymentCallback() {
                    @Override
                    public void onPaymentStatus(String status, String fullName, String email, String amount, String invoiceId,
                                                String paymentMethod, String senderNumber, String transactionId,
                                                String date, Map<String, String> metadataValues, String fee,String chargeAmount) {
                        // Callback method triggered when the payment status is received from the payment gateway.
                        // It provides information about the payment transaction.
                        storedFullName = fullName;
                        storedEmail = email;
                        storedAmount = amount;
                        storedInvoiceId = invoiceId;
                        storedPaymentMethod = paymentMethod;
                        storedSenderNumber = senderNumber;
                        storedTransactionId = transactionId;
                        storedDate = date;
                        storedFee = fee;
                        storedChargedAmount = chargeAmount;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Clear previous metadata values to avoid duplication
                                storedMetaKey1 = null;
                                storedMetaValue1 = null;
                                storedMetaKey2 = null;
                                storedMetaValue2 = null;
                                storedMetaKey3 = null;
                                storedMetaValue3 = null;

                                // Iterate through the metadata map and store the key-value pairs
                                for (Map.Entry<String, String> entry : metadataValues.entrySet()) {
                                    String metadataKey = entry.getKey();
                                    String metadataValue = entry.getValue();

                                    if ("CustomMetaData1".equals(metadataKey)) {
                                        storedMetaKey1 = metadataKey;
                                        storedMetaValue1 = metadataValue;
                                    } else if ("CustomMetaData2".equals(metadataKey)) {
                                        storedMetaKey2 = metadataKey;
                                        storedMetaValue2 = metadataValue;
                                    } else if ("CustomMetaData3".equals(metadataKey)) {
                                        storedMetaKey3 = metadataKey;
                                        storedMetaValue3 = metadataValue;
                                    }
                                }

                                // Update UI based on payment status
                                if ("COMPLETED".equals(status)) {
                                    webView.setVisibility(View.GONE);
                                    uiLayout.setVisibility(View.VISIBLE);
                                    db.collection("Cart").document(uid).delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d("deleteCart", "DocumentSnapshot successfully deleted!");

                                                    //Toast.makeText(getApplicationContext(), "Removed from Cart",Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("deleteCart", "Error deleting document", e);
                                                    Toast.makeText(getApplicationContext(), "Failed to remove",Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                    result.setText("Payment Completed");
                                    goTo.setText("Go To Home");
                                    // Handle payment completed case
                                } else if ("PENDING".equals(status)) {
                                    webView.setVisibility(View.GONE);
                                    uiLayout.setVisibility(View.VISIBLE);
                                    // Handle payment pending case
                                } else if ("ERROR".equals(status)) {
                                    webView.setVisibility(View.GONE);
                                    uiLayout.setVisibility(View.VISIBLE);
                                    // Handle payment error case
                                }
                            }
                        });
                    }
                };

                UddoktaPay uddoktapay = new UddoktaPay(UddoktaPayWebView, paymentCallback);
                uddoktapay.loadPaymentForm(API_KEY, FULL_NAME, EMAIL, enteredAmount, CHECKOUT_URL, VERIFY_PAYMENT_URL, REDIRECT_URL, CANCEL_URL, metadataMap);
            }
        });

    }
}