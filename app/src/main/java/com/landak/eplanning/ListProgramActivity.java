package com.landak.eplanning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.landak.eplanning.ADAPTER.ListProgramAdapter;
import com.landak.eplanning.MODEL.model_urusan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListProgramActivity extends AppCompatActivity {
    Service_Connector service_connector;
    ArrayList<model_urusan> arrayofprogram= new ArrayList<>();
    ListProgramAdapter programAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_program);
        service_connector=new Service_Connector();
        programAdapter= new ListProgramAdapter(arrayofprogram,this);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(programAdapter);
        Intent intent=getIntent();
        getProgram(intent.getIntExtra("id_urusan",0));

    }

    private void getProgram(int id_program){
        service_connector.sendgetrequest(this, "kppppd/bybidangurusan/"+ String.valueOf(id_program) , new Service_Connector.VolleyResponseListener_v3() {
            @Override
            public void onError(String message) {
            }
            @Override
            public void onResponese(String response) {
                try{
                    JSONArray urusans=new JSONArray(response);
                    for(int i=0;i<urusans.length();i++){
                        JSONObject urusan=new JSONObject(urusans.get(i).toString());
                        model_urusan mo=new model_urusan(urusan.getInt("id"),urusan.getString("indikatorKerja"));
                        arrayofprogram.add(mo);
                    }
                    programAdapter.notifyDataSetChanged();
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
