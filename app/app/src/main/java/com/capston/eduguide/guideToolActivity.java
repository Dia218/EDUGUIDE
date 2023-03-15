package com.capston.eduguide;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Iterator;
import java.util.Vector;
import com.example.bottomnavi.R;

public class guideToolActivity extends AppCompatActivity {

    //가이드 박스 개수
    int guideMaxNum = 15;
    int guideMinNum = 9;

    //가이드 박스
    Vector<Button> guideVector = new Vector<>(guideMaxNum);
    //라인
    Vector<Button> lineVector = new Vector<>(guideMaxNum-1);
    //추가 버튼
    Vector<ImageButton> addbuttonVector = new Vector<>(guideMaxNum-guideMinNum);

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.layout_guidetool);

            //나중에 자바로 다시 짜는 게 나을 것 같음
            guideVector.add((Button) findViewById(R.id.guideBox1));
            guideVector.add((Button) findViewById(R.id.guideBox2));
            guideVector.add((Button) findViewById(R.id.guideBox3));
            guideVector.add((Button) findViewById(R.id.guideBox4));
            guideVector.add((Button) findViewById(R.id.guideBox5));
            guideVector.add((Button) findViewById(R.id.guideBox6));
            guideVector.add((Button) findViewById(R.id.guideBox7));
            guideVector.add((Button) findViewById(R.id.guideBox8));
            guideVector.add((Button) findViewById(R.id.guideBox9));
            guideVector.add((Button) findViewById(R.id.guideBox10));
            guideVector.add((Button) findViewById(R.id.guideBox11));
            guideVector.add((Button) findViewById(R.id.guideBox12));
            guideVector.add((Button) findViewById(R.id.guideBox13));
            guideVector.add((Button) findViewById(R.id.guideBox14));
            guideVector.add((Button) findViewById(R.id.guideBox15));

            lineVector.add((Button) findViewById(R.id.line1));
            lineVector.add((Button) findViewById(R.id.line2));
            lineVector.add((Button) findViewById(R.id.line3));
            lineVector.add((Button) findViewById(R.id.line4));
            lineVector.add((Button) findViewById(R.id.line5));
            lineVector.add((Button) findViewById(R.id.line6));
            lineVector.add((Button) findViewById(R.id.line7));
            lineVector.add((Button) findViewById(R.id.line8));
            lineVector.add((Button) findViewById(R.id.line9));
            lineVector.add((Button) findViewById(R.id.line10));
            lineVector.add((Button) findViewById(R.id.line11));
            lineVector.add((Button) findViewById(R.id.line12));
            lineVector.add((Button) findViewById(R.id.line13));
            lineVector.add((Button) findViewById(R.id.line14));

            addbuttonVector.add((ImageButton) findViewById(R.id.addButton0));
            addbuttonVector.add((ImageButton) findViewById(R.id.addButton1));
            addbuttonVector.add((ImageButton) findViewById(R.id.addButton2));
            addbuttonVector.add((ImageButton) findViewById(R.id.addButton3));
            addbuttonVector.add((ImageButton) findViewById(R.id.addButton4));
            addbuttonVector.add((ImageButton) findViewById(R.id.addButton5));

            //addButton에 이벤트리스너 달기
            Iterator<ImageButton> addbuttonIt = addbuttonVector.iterator();
            while (addbuttonIt.hasNext()) {
                ImageButton addButton = addbuttonIt.next();
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickAdd(v);
                    }
                });
            }

            //guideBox에 이벤트리스너 달기
            Iterator<Button> guideboxIt = guideVector.iterator();
            while (guideboxIt.hasNext()) {
                Button guideBox = guideboxIt.next();
                guideBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickGuide(v);
                    }
                });

                /*
                if(guideVector.indexOf(guideBox) >= 9) {
                    guideBox.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            onLongClickGuide(v);
                            return true;
                        }
                    });
                }
                */

            }
        }

    //guideBox의 터치 이벤트
    public void onClickGuide(View view) {


    }

    //guideBox의 길게 누르기 이벤트
    public void onLongClickGuide(View view) {
    }

    //addButtion의 터치 이벤트
    public void onClickAdd(View view) {
            //클릭한 addButtion의 인덱스
            int indexAdd = addbuttonVector.indexOf((ImageButton) view);
            //클릭한 addButtion 없애기
            view.setVisibility(View.GONE);
            //guideBox 보이게 하기
            addbuttonVector.get(10+indexAdd).setVisibility(View.VISIBLE);
            //다음 line 보이게 하기
        lineVector.get(10+indexAdd).setVisibility(View.VISIBLE);
        //다음 addButton 보이게 하기
        addbuttonVector.get(++indexAdd).setVisibility(View.VISIBLE);
    }

}
