package com.landak.eplanning;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnTpUrusan, btnUrusan, btnBdUrusan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnUrusan   =findViewById(R.id.btnUrusan);
        btnBdUrusan =findViewById(R.id.btnBdUrusan);

        btnUrusan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Memunculkan pesan berupa text dialog saat tombol "Klik Disini" diklik.
                Toast.makeText(getApplicationContext(), "PILIH TIPE URUSAN", Toast.LENGTH_SHORT).show();

                String[] singleChoiceItems = getResources().getStringArray(R.array.dialog_single_choice_array);
                int itemSelected = 0;
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setTitle("Select your gender")
                        .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {

                            }
                        })
                        .setPositiveButton("Ok", null)
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
    }
}
