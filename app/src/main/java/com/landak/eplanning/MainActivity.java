package com.landak.eplanning;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.landak.eplanning.MODEL.model_urusan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Service_Connector service_connector;
    ArrayList<model_urusan> arrayofurusan= new ArrayList<>();
    ArrayList<model_urusan> arrayofbdurusan= new ArrayList<>();
    //ini supaya bisa menentukan id urusan yang terpilih
    int urusanterpilih,bdurusanterpilih, indexbd,indexUr;
    private Button btnLihatProgram, btnUrusan, btnBdUrusan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnUrusan   =findViewById(R.id.btnUrusan);
        btnBdUrusan =findViewById(R.id.btnBdUrusan);
        btnLihatProgram= findViewById(R.id.btnLihatProgram);
        service_connector = new Service_Connector();
        indexbd=0;
        indexUr=0;

        btnUrusan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Memunculkan pesan berupa text dialog saat tombol "Klik Disini" diklik.

                //String[] singleChoiceItems = getResources().getStringArray(R.array.dialog_single_choice_array);
                final String[] arr = new String[arrayofurusan.size()];
                for(int i=0 ; i<arrayofurusan.size(); i++){
                    arr[i] = arrayofurusan.get(i).toString();
                    //getProductName or any suitable method
                }

                int itemSelected = indexUr;
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setTitle("PILIH URUSAN")
                        .setSingleChoiceItems(arr, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                urusanterpilih=arrayofurusan.get(selectedIndex).id_urusan;
                                indexUr=selectedIndex;

                                //btnUrusan.setText(arrayofurusan.get(selectedIndex).nama_urusan);
                                //getBdUrusan(arrayofurusan.get(selectedIndex).id_urusan);

                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                urusanterpilih=arrayofurusan.get(indexUr).id_urusan;
                                getBdUrusan(arrayofurusan.get(indexUr).id_urusan);
                                btnBdUrusan.setText("Pilih Bidang Urusan");
                                Toast.makeText(getApplicationContext(), "URUSAN TERPILIH", Toast.LENGTH_SHORT).show();
                                btnUrusan.setText(arrayofurusan.get(indexUr).nama_urusan);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        btnBdUrusan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Memunculkan pesan berupa text dialog saat tombol "Klik Disini" diklik.

                //String[] singleChoiceItems = getResources().getStringArray(R.array.dialog_single_choice_array);
                final String[] arr = new String[arrayofbdurusan.size()];
                for(int i=0 ; i<arrayofbdurusan.size(); i++){
                    arr[i] = arrayofbdurusan.get(i).toString();
                    //getProductName or any suitable method
                }
                int itemSelected = indexbd;
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setTitle("PILIH BIDANG URUSAN")
                        .setSingleChoiceItems(arr, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                bdurusanterpilih=arrayofbdurusan.get(selectedIndex).id_urusan;
                                indexbd=selectedIndex;

                                //btnBdUrusan.setText(arrayofbdurusan.get(selectedIndex).nama_urusan);
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                btnBdUrusan.setText(arrayofbdurusan.get(indexbd).nama_urusan);
                                Toast.makeText(getApplicationContext(), "BIDANG URUSAN TERPILIH", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        btnLihatProgram.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,ListProgramActivity.class);
                intent.putExtra("id_urusan",bdurusanterpilih);
                startActivity(intent);
            }
        });
        getUrusan();
    }


    private void getUrusan(){
        service_connector.sendgetrequest(this, "urusan", new Service_Connector.VolleyResponseListener_v3() {
            @Override
            public void onError(String message) {
            }
            @Override
            public void onResponese(String response) {
                try{
                    JSONArray urusans=new JSONArray(response);
                    for(int i=0;i<urusans.length();i++){
                        JSONObject urusan=new JSONObject(urusans.get(i).toString());
                        model_urusan mo=new model_urusan(urusan.getInt("id"),urusan.getString("nama"));
                        if(i==0){
                            urusanterpilih=mo.id_urusan;
                        }
                        arrayofurusan.add(mo);
                    }
                }
                catch (JSONException JEO){

                }

            }

            @Override
            public void onNoConnection(String message) {

            }

            @Override
            public void OnServerError(String message) {

            }

            @Override
            public void OnTimeOut() {

            }
        });
    }

    private void getBdUrusan(int idUrusan){
        service_connector.sendgetrequest(this, "bidangurusan/byurusan/"+ String.valueOf(idUrusan), new Service_Connector.VolleyResponseListener_v3() {
            @Override
            public void onError(String message) {
            }
            @Override
            public void onResponese(String response) {
                try{
                    arrayofbdurusan.clear();
                    JSONArray urusans=new JSONArray(response);
                    for(int i=0;i<urusans.length();i++){
                        JSONObject urusan=new JSONObject(urusans.get(i).toString());
                        model_urusan mo=new model_urusan(urusan.getInt("id"),urusan.getString("nama"));
                        if(i==0){
                            bdurusanterpilih=mo.id_urusan;
                        }
                        arrayofbdurusan.add(mo);
                    }
                }
                catch (JSONException JEO){

                }

            }

            @Override
            public void onNoConnection(String message) {

            }

            @Override
            public void OnServerError(String message) {

            }

            @Override
            public void OnTimeOut() {

            }
        });
    }
}
