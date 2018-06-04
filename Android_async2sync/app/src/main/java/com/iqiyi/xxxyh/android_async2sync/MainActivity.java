package com.iqiyi.xxxyh.android_async2sync;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
public Stack<AlertDialog>stack;
public Button btn;
public Handler handler = new Handler(){
    @Override
    public void handleMessage(Message msg) {
        if(stack.empty()){
            btn.setEnabled(true);
            return;
        }
        if(!stack.empty()&& msg.arg1 ==1){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AlertDialog dialog = stack.pop();
            dialog.show();
        }
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        btn =(Button)findViewById(R.id.btn_dialog);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEvent();
            }
        });
        stack = new Stack<>();


    }
    public void dialogEvent(){
        btn.setEnabled(false);
        if(stack == null ){
            return;
        }
        while (!stack.empty()){
            stack.pop();
        }
        for(int i =0;i<5;i++){
            AlertDialog dialog= createDialog("第"+i+"个弹出框");
            stack.push(dialog);
        }
        Message msg =Message.obtain();
        msg.arg1 =1 ;
        handler.sendMessage(msg);
    }
    public AlertDialog createDialog(String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage("2018年6月5日 : "+title);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("XXXXXXX","positiveButton");
                Message msg = Message.obtain();
                msg.arg1 =1;
                handler.sendMessage(msg);
            }
        });
      builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
          @Override
          public void onCancel(DialogInterface dialogInterface) {
              Log.d("XXXXXXX","onCancel");
              Message msg = Message.obtain();
              msg.arg1 =1;
              handler.sendMessage(msg);
          }
      });
        AlertDialog dialog = builder.create();
        return dialog;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
