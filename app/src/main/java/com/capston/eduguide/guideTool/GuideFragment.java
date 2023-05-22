package com.capston.eduguide.guideTool;


import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.capston.eduguide.Frag1Feed;
import com.capston.eduguide.MainActivity;
import com.capston.eduguide.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/*
* 가이드 띄우기, 가이드 관리, 가이드 - 파이어베이스 연동 등을 처리함
*
* 필드 : 가이드 박스 개수, 가이드 구성 인터페이스 저장 구조, 파이어베이스 인스턴스
* 생성 시 : 인터페이스 저장 구조 초기화, 이벤트 리스너 부착, 고정 모드 활성화 여부 결정
*
* 메소드 :
* 터치 이벤트 - 키워드 작성,
* 길게 누르기 이벤트 - 설명글 작성,
* 고정 모드 - addButton 비활성화,
* 가이드 데이터베이스 저장
* 가이드 데이터베이스 조회
* */

public class GuideFragment extends Fragment {


    //가이드 박스 개수
    static int guideMaxNum = 18;
    static int guideMinNum = 9;

    public boolean isDestroyed = false;
    static String id;

    Vector<Button> guideBoxViews = new Vector<>(guideMaxNum); // 가이드 박스
    Vector<Button> guideLineViews = new Vector<>(guideMaxNum-1); // 라인
    Vector<ImageButton> guideAddButtons = new Vector<>(guideMaxNum-guideMinNum); // 추가 버튼
    HashMap<Integer, String> boxInfos = new HashMap<>(guideMaxNum); // 가이드 박스 설명글

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(); // 파이어베이스 DB
    DatabaseReference guideDatabaseReference = firebaseDatabase.getReference("guide"); // 가이드 DB

    private View view;
    public static GuideFragment newInstance(int param1){
        GuideFragment fg = new GuideFragment();
        Bundle args = new Bundle();
        args.putInt("param1", param1);
        fg.setArguments(args);
        return fg; }

    //기본 생성자
    public GuideFragment(){

    }

    //생성자를 이용해 선언 시부터 데이터가 들어간 가이드객체를 생성
    public GuideFragment(String feedId){
        setGuide(feedId);
    }

    public void onStart(){
        super.onStart();
        if(isDestroyed){
            setFixmode(); //fix모드 메소드 호출
            isDestroyed = false;
        }
    }

    //뷰가 destroy될 경우 변수를 true로 변경(체크용)
    public void onDestroyView() {
        super.onDestroyView();
        isDestroyed = true;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.guide_guidetool, container, false);

        //isdestroyed가 true일 경우 비워진 해쉬맵을 다시 채워줌, fix모드 적용 안됨
        if(isDestroyed){
            setGuide(id);
            guideAddButtons.get(0).setVisibility(View.GONE);
        }

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

        //게시글 작성 메뉴가 아닐 경우
        if(!MainActivity.getCurrentMenu().equals("posting")) {
            setFixmode(); //fix모드 메소드 호출
            //setGuide(postId); //가이드 데이터 가져오기 호출? 호출은 바깥에서 해야하나? 게시글 ID를 어떻게 받아올까?
        }

        return view;
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

        //line 보이게 하기
        guideLineViews.get(guideMinNum + indexAdd - 1).setVisibility(View.VISIBLE);

        if(indexAdd == guideMaxNum-guideMinNum-1) //마지막 addbutton일 경우 뒷부분 생략
            return;

        //다음 addButton 보이게 하기
        guideAddButtons.get(++indexAdd).setVisibility(View.VISIBLE);
    }

    //가이드 고정(보여주기) 모드
    public void setFixmode() {
        //추가 버튼 나타나지 않게 함
        guideAddButtons.get(0).setVisibility(View.GONE);

        //짧게 터치 이벤트 리스너 달기
        Iterator<Button> guideboxIt = guideBoxViews.iterator();
        while (guideboxIt.hasNext()) {
            Button guideBox = guideboxIt.next();
            guideBox.setOnClickListener(new View.OnClickListener() { //짧게 터치 이벤트
                @Override
                public void onClick(View v) {
                    //설명글 창 띄우기
                    View guideDialogView = View.inflate(getContext(), R.layout.guide_guidebox_inform, null);
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                    dialogBuilder.setView(guideDialogView);
                    final AlertDialog informDialog = dialogBuilder.create();

                    //팝업창 제목 수정하기
                    ((TextView) guideDialogView.findViewById(R.id.titleKeyword)).setText(">ㅁ<// 이 단계에서는 : ");

                    //팝업창 설명 부분 수정하기
                    MultiAutoCompleteTextView guideInformTextView = (MultiAutoCompleteTextView) guideDialogView.findViewById(R.id.guideInform);
                    guideInformTextView.setText(boxInfos.get(guideBoxViews.indexOf(v)));
                    guideInformTextView.setEnabled(false);

                    informDialog.show();
                }
            });
        }
    }

    //파이어베이스에 가이드 데이터 저장
    public void regGuideContent(String postId) {
        Log.d("GuideItem", "regGuideContent called. postId: " + postId);

        List<GuideBoxItem> guideBoxItems = new LinkedList<GuideBoxItem>() {}; //가이드 박스 리스트
        Iterator<Button> guideboxIt = guideBoxViews.iterator();
        while (guideboxIt.hasNext()) {
            Button guideBox = guideboxIt.next();

            // 가이드박스 키워드 가져오기
            String keyword = String.valueOf(guideBox.getText());
            if(keyword.replace("단계", "").matches("^[0-9]*$")) { continue; } //미입력 상태인 경우([숫자]단계 형식일 경우), 스킵

            //가이드박스 설명글 가져오기
            int indexKey = guideBoxViews.indexOf(guideBox);
            String boxInfo;
            if(boxInfos.get(indexKey)==null) //미입력 상태인 경우
                boxInfo = "내용없음";
            else
                boxInfo = boxInfos.get(indexKey);

            guideBoxItems.add(new GuideBoxItem(keyword, boxInfo)); //리스트에 가이드박스 넣기
        }

        GuideItem guideItem = new GuideItem(postId, guideBoxItems); // 가이드 객체 생성
        guideDatabaseReference.child(postId).setValue(guideItem); //가이드 객체 DB에 넣기

        //위 코드에서 에러가 발생할 경우 해당 코드로 수정 :
        //guideDatabaseReference.child(postId).child("guideBox").setValue(guideBoxItems); //가이드박스 리스트 DB에 넣기

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

    //파이어베이스에서 가이드 데이터 가져오기
    public void setGuide(String postId) {
        id = postId;
        guideDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // 데이터가 존재할 경우 처리
                    DataSnapshot guideSnapshot = dataSnapshot.child(postId).child("guideBoxList");
                    int numGuideBoxes = (int) guideSnapshot.getChildrenCount();

                    //가이드 박스 개수 맞추기
                    int addNum = numGuideBoxes - guideMinNum;
                    if(addNum > 0) {
                        for(int i = 0; i < addNum; i++) {
                            //guideBox 보이게 하기
                            guideBoxViews.get(guideMinNum+i).setVisibility(View.VISIBLE);
                            //line 보이게 하기
                            guideLineViews.get(guideMinNum + i - 1).setVisibility(View.VISIBLE);
                        }
                    }

                    //데이터 가져와서 처리
                    Iterator<Button> guideboxIt = guideBoxViews.iterator();
                    int index = 0;
                    while (guideboxIt.hasNext()) {
                        Button guideBox = guideboxIt.next();
                        guideBox.setText(guideSnapshot.child(String.valueOf(index)).child("keyword").getValue(String.class)); //박스에 키워드 표시
                        boxInfos.put(index, guideSnapshot.child(String.valueOf(index)).child("boxInfo").getValue(String.class)); //해시맵에 설명글 저장
                        index++;
                    }

                } else {
                    // 데이터가 없을 경우 처리
                    System.out.println("데이터가 존재하지 않습니다.");
                    Toast.makeText(getActivity(), "데이터가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 에러 처리
                Toast.makeText(getActivity(), "데이터 읽기 실패: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}


