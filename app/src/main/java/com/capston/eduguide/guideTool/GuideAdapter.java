package com.capston.eduguide.guideTool;


import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.capston.eduguide.MainActivity;
import com.capston.eduguide.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class GuideAdapter extends Fragment {


    //가이드 박스 개수
    static int guideMaxNum = 18;
    static int guideMinNum = 9;

    Vector<Button> guideBoxViews = new Vector<>(guideMaxNum); // 가이드 박스
    Vector<Button> guideLineViews = new Vector<>(guideMaxNum-1); // 라인
    Vector<ImageButton> guideAddButtons = new Vector<>(guideMaxNum-guideMinNum); // 추가 버튼
    HashMap<Integer, String> boxInfos = new HashMap<>(guideMaxNum); // 가이드 박스 설명글

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(); // 파이어베이스 DB
    DatabaseReference guideDatabaseReference = firebaseDatabase.getReference("guide"); // 가이드 DB

    private View view;
    public static GuideAdapter newInstance(int param1){
        GuideAdapter fg = new GuideAdapter();
        Bundle args = new Bundle();
        args.putInt("param1", param1);
        fg.setArguments(args);
        return fg; }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.guide_guidetool, container, false);

        //가이드박스 벡터에 저장
        guideBoxViews.add((Button) view.findViewById(R.id.guideBox1));
        guideBoxViews.add((Button) view.findViewById(R.id.guideBox2));
        guideBoxViews.add((Button) view.findViewById(R.id.guideBox3));
        guideBoxViews.add((Button) view.findViewById(R.id.guideBox4));
        guideBoxViews.add((Button) view.findViewById(R.id.guideBox5));
        guideBoxViews.add((Button) view.findViewById(R.id.guideBox6));
        guideBoxViews.add((Button) view.findViewById(R.id.guideBox7));
        guideBoxViews.add((Button) view.findViewById(R.id.guideBox8));
        guideBoxViews.add((Button) view.findViewById(R.id.guideBox9));
        guideBoxViews.add((Button) view.findViewById(R.id.guideBox10));
        guideBoxViews.add((Button) view.findViewById(R.id.guideBox11));
        guideBoxViews.add((Button) view.findViewById(R.id.guideBox12));
        guideBoxViews.add((Button) view.findViewById(R.id.guideBox13));
        guideBoxViews.add((Button) view.findViewById(R.id.guideBox14));
        guideBoxViews.add((Button) view.findViewById(R.id.guideBox15));
        guideBoxViews.add((Button) view.findViewById(R.id.guideBox16));
        guideBoxViews.add((Button) view.findViewById(R.id.guideBox17));
        guideBoxViews.add((Button) view.findViewById(R.id.guideBox18));

        //라인 벡터에 저장
        guideLineViews.add((Button) view.findViewById(R.id.line1));
        guideLineViews.add((Button) view.findViewById(R.id.line2));
        guideLineViews.add((Button) view.findViewById(R.id.line3));
        guideLineViews.add((Button) view.findViewById(R.id.line4));
        guideLineViews.add((Button) view.findViewById(R.id.line5));
        guideLineViews.add((Button) view.findViewById(R.id.line6));
        guideLineViews.add((Button) view.findViewById(R.id.line7));
        guideLineViews.add((Button) view.findViewById(R.id.line8));
        guideLineViews.add((Button) view.findViewById(R.id.line9));
        guideLineViews.add((Button) view.findViewById(R.id.line10));
        guideLineViews.add((Button) view.findViewById(R.id.line11));
        guideLineViews.add((Button) view.findViewById(R.id.line12));
        guideLineViews.add((Button) view.findViewById(R.id.line13));
        guideLineViews.add((Button) view.findViewById(R.id.line14));
        guideLineViews.add((Button) view.findViewById(R.id.line15));
        guideLineViews.add((Button) view.findViewById(R.id.line16));
        guideLineViews.add((Button) view.findViewById(R.id.line17));

        //추가버튼 벡터에 저장
        guideAddButtons.add((ImageButton) view.findViewById(R.id.addButton0));
        guideAddButtons.add((ImageButton) view.findViewById(R.id.addButton1));
        guideAddButtons.add((ImageButton) view.findViewById(R.id.addButton2));
        guideAddButtons.add((ImageButton) view.findViewById(R.id.addButton3));
        guideAddButtons.add((ImageButton) view.findViewById(R.id.addButton4));
        guideAddButtons.add((ImageButton) view.findViewById(R.id.addButton5));
        guideAddButtons.add((ImageButton) view.findViewById(R.id.addButton6));
        guideAddButtons.add((ImageButton) view.findViewById(R.id.addButton7));
        guideAddButtons.add((ImageButton) view.findViewById(R.id.addButton8));

        //addButton에 이벤트리스너 달기
        Iterator<ImageButton> addbuttonIt = guideAddButtons.iterator();
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
        Iterator<Button> guideboxIt = guideBoxViews.iterator();
        while (guideboxIt.hasNext()) {
            Button guideBox = guideboxIt.next();
            guideBox.setOnClickListener(new View.OnClickListener() { //짧게 터치 이벤트
                @Override
                public void onClick(View v) {
                    onClickGuide(v);
                }
            });

            guideBox.setOnLongClickListener(new View.OnLongClickListener() { //길게 누르기 이벤트
                @Override
                public boolean onLongClick(View v) { onLongClickGuide(v); return true; }
            });
        }
/*
        Bundle bundle = getArguments();
        if(getArguments() != null){
            Integer index = bundle.getInt("guideboxsize");
            ArrayList<String> key = bundle.getStringArrayList("key");
            addbuttonVector.get(0).setVisibility(View.GONE);
            for(int num=0;num<(index);num++){
                addGuide(num, key.get(num));
                if(num > guideMinNum-1){
                    //addbuttonVector.get(0).setVisibility(View.GONE);
                    guideVector.get(num).setVisibility(View.VISIBLE);
                    lineVector.get(num-1).setVisibility(View.VISIBLE);
                }
            }
        }*/

        //게시글 작성 메뉴가 아닐 경우
        if(!MainActivity.getCurrentMenu().equals("posting")) {
            setFixmode(); //fix모드 메소드 호출
        }

        return view;
    }

    public void setFixmode() {
        //추가 버튼 나타나지 않게 함
        guideAddButtons.get(0).setVisibility(View.GONE);

        //짧게 터치 이벤트 비활성화
        Iterator<Button> guideboxIt = guideBoxViews.iterator();
        while (guideboxIt.hasNext()) {
            Button guideBox = guideboxIt.next();
            guideBox.setOnClickListener(new View.OnClickListener() { //짧게 터치 이벤트
                @Override
                public void onClick(View v) {
                    ;
                }
            });
        }
    }

    //guideBox의 터치 이벤트
    public void onClickGuide(View view) {
        //키워드 입력 창 띄우기
        View guideDialogView = View.inflate(getContext(), R.layout.guide_guidebox_keyword, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setView(guideDialogView);
        final AlertDialog keywordDialog = dialogBuilder.create();
        keywordDialog.show();

        //키워드 입력 저장 버튼
        guideDialogView.findViewById(R.id.editKeyword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = String.valueOf(((EditText)guideDialogView.findViewById(R.id.guideKeyword)).getText());
                if( !(keyword.trim().isEmpty() || keyword.equals("키워드")) ) ((Button) view).setText(keyword);
                keywordDialog.dismiss();
            }
        });
    }

    //guideBox의 길게 누르기 이벤트
    public void onLongClickGuide(View view) {
        //설명글 입력창 띄우기
        View guideDialogView = View.inflate(getContext(), R.layout.guide_guidebox_inform, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setView(guideDialogView);
        final AlertDialog informDialog = dialogBuilder.create();
        informDialog.show();

        //설명글 입력 저장 버튼
        guideDialogView.findViewById(R.id.editInform).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String boxInfo = String.valueOf(((MultiAutoCompleteTextView)guideDialogView.findViewById(R.id.guideInform)).getText()); // 설명글
                int indexKey = guideBoxViews.indexOf((Button) view); // 가이드박스의 인덱스 번호
                boxInfos.put(indexKey, boxInfo); //설명글 해시맵에 저장
                informDialog.dismiss();
            }
        });
    }

    //addButtion의 터치 이벤트
    public void onClickAdd(View view) {
        //클릭한 addButtion의 인덱스
        int indexAdd = guideAddButtons.indexOf((ImageButton) view);
        //클릭한 addButtion 없애기
        view.setVisibility(View.GONE);
        //guideBox 보이게 하기
        guideBoxViews.get(guideMinNum+indexAdd).setVisibility(View.VISIBLE);

        //다음 line 보이게 하기
        guideLineViews.get(guideMinNum + indexAdd - 1).setVisibility(View.VISIBLE);

        if(indexAdd == guideMaxNum-guideMinNum-1) //마지막 addbutton일 경우 뒷부분 생략
            return;

        //다음 addButton 보이게 하기
        guideAddButtons.get(++indexAdd).setVisibility(View.VISIBLE);
    }

    //가이드 DB 저장
    public void regGuideContent(String postId) {
        Log.d("GuideTool", "regGuideContent called. postId: " + postId);

        List<GuideBoxItem> guideBoxItems = new LinkedList<GuideBoxItem>() {}; //가이드 박스 리스트
        Iterator<Button> guideboxIt = guideBoxViews.iterator();
        while (guideboxIt.hasNext()) {
            Button guideBox = guideboxIt.next();

            String keyword = String.valueOf(guideBox.getText()); // 가이드박스 키워드
            if(keyword.replace("단계", "").matches("^[0-9]*$")) { continue; } //[숫자]단계 형식일 경우 저장하지 않고 스킵

            int indexKey = guideBoxViews.indexOf(guideBox);
            String boxInfo = boxInfos.get(indexKey); //가이드박스 설명글

            guideBoxItems.add(new GuideBoxItem(keyword, boxInfo)); //리스트에 가이드박스 넣기
        }

        String guideId = guideDatabaseReference.push().getKey(); // 새로운 가이드 ID 생성
        GuideItem guide = new GuideItem(postId, guideBoxItems); // 가이드 객체 생성
        guideDatabaseReference.child(guideId).setValue(guide); //가이드 객체 DB에 넣기

        Log.d("Guide", "GuideItem registered successfully.");

        guideDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("Guide", "GuideBoxes registered successfully.");
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }


    //일단은 박스 사이즈만 받아서 키워드만 임의 지정함.키워드랑 설명 저장 기능,db   생기면 시도해봄
    public void addGuide(int guideNum,String key){
        //Log.v("size",""+addbuttonVector.capacity());
        /*while(forGuideNum<guideNum-guideMinNum+1){
            addbuttonVector.get(forGuideNum).setVisibility(View.GONE);
            guideVector.get(guideMinNum+forGuideNum).setVisibility(View.VISIBLE);
            if(guideNum>10){
                lineVector.get(guideMinNum + forGuideNum).setVisibility(View.VISIBLE);
            }
            forGuideNum++;
            if(forGuideNum<guideNum-guideMinNum){
                lineVector.get(guideMinNum + forGuideNum).setVisibility(View.GONE);
            }
        }*/

        guideBoxViews.get(guideNum).setText(key);
    }
}

