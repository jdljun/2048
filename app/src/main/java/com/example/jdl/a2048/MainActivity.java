package com.example.jdl.a2048;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private  ImageView m;
    private LinearLayout l;
    private board b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b=new board();
        changeUI(b.get_mux_b());

        ImageButton ib=(ImageButton)findViewById(R.id.imageButton);
        ib.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                savescore();
                board d=new board();
                changeUI(d.get_mux_b());
                b=d;
                TextView t1=(TextView)findViewById(R.id.textView1);
                t1.setText("当前得分"+"\n"+getscore());
                TextView t2=(TextView)findViewById(R.id.textView2);
                t2.setText("最高得分"+"\n"+getmaxscore());
            }
        });
        TextView t2=(TextView)findViewById(R.id.textView2);
        t2.setText("最高得分"+"\n"+getmaxscore());
    }

    private void changeUI(int[] mux_b) {

        delete_old();
        for(int i=0;i<16;i++){
            if(i>=0 && i<=3) {
                l = (LinearLayout) findViewById(R.id.layout0);
            }
            if(i>=4 && i<=7) {
                l = (LinearLayout) findViewById(R.id.layout1);
            }
            if(i>=8 && i<=11) {
                l = (LinearLayout) findViewById(R.id.layout2);
            }
            if(i>=12 && i<=15) {
                l = (LinearLayout) findViewById(R.id.layout3);
            }
            m =new ImageView(this);
            switch(mux_b[i]){
                case 0:
                    m.setImageResource(R.drawable.num0);
                    break;
                case 2:
                    m.setImageResource(R.drawable.num2);
                    break;
                case 4:
                    m.setImageResource(R.drawable.num4);
                    break;
                case 8:
                    m.setImageResource(R.drawable.num8);
                    break;
                case 16:
                    m.setImageResource(R.drawable.num16);
                    break;
                case 32:
                    m.setImageResource(R.drawable.num32);
                    break;
                case 64:
                    m.setImageResource(R.drawable.num64);
                    break;
                case 128:
                    m.setImageResource(R.drawable.num128);
                    break;
                case 256:
                    m.setImageResource(R.drawable.num256);
                    break;
                case 512:
                    m.setImageResource(R.drawable.num512);
                    break;
                case 1024:
                    m.setImageResource(R.drawable.num1024);
                    break;
                case 2048:
                    m.setImageResource(R.drawable.num2048);
                    break;
                default:
                    break;

            }
            m.setPadding(20,0,20,0);
            LinearLayout.LayoutParams p= new  LinearLayout.LayoutParams(250,250);
            m.setLayoutParams(p);
            l.addView(m);
            TextView t1=(TextView)findViewById(R.id.textView1);
            t1.setText("当前得分"+"\n"+getscore());
        }
    }

    private void delete_old() {
        LinearLayout oldl=(LinearLayout) findViewById(R.id.layout0);
        int n=oldl.getChildCount();
        for(int i=1;i<=n;i++)
           oldl.removeView(oldl.getChildAt(n-i));

        oldl=(LinearLayout) findViewById(R.id.layout1);
        n=oldl.getChildCount();
        for(int i=1;i<=n;i++)
            oldl.removeView(oldl.getChildAt(n-i));

        oldl=(LinearLayout) findViewById(R.id.layout2);
        n=oldl.getChildCount();
        for(int i=1;i<=n;i++)
            oldl.removeView(oldl.getChildAt(n-i));

        oldl=(LinearLayout) findViewById(R.id.layout3);
        n=oldl.getChildCount();
        for(int i=1;i<=n;i++)
            oldl.removeView(oldl.getChildAt(n-i));

    }

    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
            if(y1 - y2 > 50) {
                b.up();
                changeUI(b.get_mux_b());
            } else if(y2 - y1 > 50) {
                b.down();
                changeUI(b.get_mux_b());
            } else if(x1 - x2 > 20) {
                b.left();
                changeUI(b.get_mux_b());
            } else if(x2 - x1 > 20) {
                b.right();
                changeUI(b.get_mux_b());
            }
        }
        if(b.notchange) {
            Toast.makeText(this, "game over! Your score is "+getscore(), Toast.LENGTH_SHORT).show();
            board d=new board();
            changeUI(d.get_mux_b());
            b=d;
        }
        return super.onTouchEvent(event);
    }


    public String getscore() {
        int score=0;
        int [] intb=b.get_mux_b();
        for(int i=0;i<16;i++)
            score+=intb[i];
        return ""+score;
    }

    public String getmaxscore() {
        String maxscore="";

        BufferedReader reader= null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(
                            openFileInput("score")));
            try {
                maxscore=reader.readLine();
            } catch (IOException e) {
                return "";
            }
        } catch (FileNotFoundException e) {
            return "";
        }


        return maxscore;
    }

    public void savescore(){
        int score=Integer.parseInt(getscore());
        int max;
        if(getmaxscore()=="")
            max=0;
        else
            max=Integer.parseInt(getmaxscore());
        if(score>max){
            BufferedWriter writer= null; //建立文件写的流，写文本
            try {
                writer = new BufferedWriter(
                        new OutputStreamWriter(
                                openFileOutput("score",MODE_PRIVATE) ));
                writer.write(""+score);
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

