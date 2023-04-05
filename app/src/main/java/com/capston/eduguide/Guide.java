package com.capston.eduguide;


import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.capston.eduguide.R;
import java.util.Iterator;
import java.util.Vector;

public class Guide extends Fragment {


    //가이드 박스 개수
    int guideMaxNum = 18;
    int guideMinNum = 9;

    //가이드 박스
    Vector<Button> guideVector = new Vector<>(guideMaxNum);
    //라인
    Vector<Button> lineVector = new Vector<>(guideMaxNum-1);
    //추가 버튼
    Vector<ImageButton> addbuttonVector = new Vector<>(guideMaxNum-guideMinNum);

    private View view;
    public static Guide newInstance(int param1){
        Guide fg = new Guide();
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
        view=inflater.inflate(R.layout.frag1_guide, container, false);

        //가이드박스 벡터에 저장
        guideVector.add((Button) view.findViewById(R.id.guideBox1));
        guideVector.add((Button) view.findViewById(R.id.guideBox2));
        guideVector.add((Button) view.findViewById(R.id.guideBox3));
        guideVector.add((Button) view.findViewById(R.id.guideBox4));
        guideVector.add((Button) view.findViewById(R.id.guideBox5));
        guideVector.add((Button) view.findViewById(R.id.guideBox6));
        guideVector.add((Button) view.findViewById(R.id.guideBox7));
        guideVector.add((Button) view.findViewById(R.id.guideBox8));
        guideVector.add((Button) view.findViewById(R.id.guideBox9));
        guideVector.add((Button) view.findViewById(R.id.guideBox10));
        guideVector.add((Button) view.findViewById(R.id.guideBox11));
        guideVector.add((Button) view.findViewById(R.id.guideBox12));
        guideVector.add((Button) view.findViewById(R.id.guideBox13));
        guideVector.add((Button) view.findViewById(R.id.guideBox14));
        guideVector.add((Button) view.findViewById(R.id.guideBox15));
        guideVector.add((Button) view.findViewById(R.id.guideBox16));
        guideVector.add((Button) view.findViewById(R.id.guideBox17));
        guideVector.add((Button) view.findViewById(R.id.guideBox18));

        //라인 벡터에 저장
        lineVector.add((Button) view.findViewById(R.id.line1));
        lineVector.add((Button) view.findViewById(R.id.line2));
        lineVector.add((Button) view.findViewById(R.id.line3));
        lineVector.add((Button) view.findViewById(R.id.line4));
        lineVector.add((Button) view.findViewById(R.id.line5));
        lineVector.add((Button) view.findViewById(R.id.line6));
        lineVector.add((Button) view.findViewById(R.id.line7));
        lineVector.add((Button) view.findViewById(R.id.line8));
        lineVector.add((Button) view.findViewById(R.id.line9));
        lineVector.add((Button) view.findViewById(R.id.line10));
        lineVector.add((Button) view.findViewById(R.id.line11));
        lineVector.add((Button) view.findViewById(R.id.line12));
        lineVector.add((Button) view.findViewById(R.id.line13));
        lineVector.add((Button) view.findViewById(R.id.line14));
        lineVector.add((Button) view.findViewById(R.id.line15));
        lineVector.add((Button) view.findViewById(R.id.line16));
        lineVector.add((Button) view.findViewById(R.id.line17));

        //추가버튼 벡터에 저장
        addbuttonVector.add((ImageButton) view.findViewById(R.id.addButton0));
        addbuttonVector.add((ImageButton) view.findViewById(R.id.addButton1));
        addbuttonVector.add((ImageButton) view.findViewById(R.id.addButton2));
        addbuttonVector.add((ImageButton) view.findViewById(R.id.addButton3));
        addbuttonVector.add((ImageButton) view.findViewById(R.id.addButton4));
        addbuttonVector.add((ImageButton) view.findViewById(R.id.addButton5));
        addbuttonVector.add((ImageButton) view.findViewById(R.id.addButton6));
        addbuttonVector.add((ImageButton) view.findViewById(R.id.addButton7));
        addbuttonVector.add((ImageButton) view.findViewById(R.id.addButton8));

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
            guideBox.setOnClickListener(new View.OnClickListener() { //짧게 터치 이벤트
                @Override
                public void onClick(View v) {
                    onClickGuide(v);
                }
            });

            guideBox.setOnLongClickListener(new View.OnLongClickListener() { //길게 누르기 이벤트
                @Override
                public boolean onLongClick(View v) {
                    onLongClickGuide(v);
                    return true;
                }
            });
        }

        return view;
    }


    //guideBox의 터치 이벤트
    public void onClickGuide(View view) {
        //키워드 입력 창 띄우기
        View guideDialogView = View.inflate(getContext(), R.layout.guidebox_keyword, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setView(guideDialogView);
        final AlertDialog keywordDialog = dialogBuilder.create();
        keywordDialog.show();

        //키워드 입력 저장 버튼
        guideDialogView.findViewById(R.id.editKeyword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = String.valueOf(((EditText)guideDialogView.findViewById(R.id.guideKeyword)).getText());
                if( !keyword.trim().isEmpty() && keyword != "키워드") {
                    ((Button) view).setText(keyword);
                }
                keywordDialog.dismiss();
            }
        });
    }

    //guideBox의 길게 누르기 이벤트
    public void onLongClickGuide(View view) {
        //설명글 입력창 띄우기
        View guideDialogView = View.inflate(getContext(), R.layout.guidebox_inform, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setView(guideDialogView);
        final AlertDialog informDialog = dialogBuilder.create();
        informDialog.show();

        //설명글 입력 저장 버튼
        guideDialogView.findViewById(R.id.editInform).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                informDialog.dismiss();
            }
        });
    }

    //addButtion의 터치 이벤트
    public void onClickAdd(View view) {
        //클릭한 addButtion의 인덱스
        int indexAdd = addbuttonVector.indexOf((ImageButton) view);
        //클릭한 addButtion 없애기
        view.setVisibility(View.GONE);
        //guideBox 보이게 하기
        guideVector.get(guideMinNum+indexAdd).setVisibility(View.VISIBLE);

        if(indexAdd == guideMaxNum-guideMinNum-1) //마지막 addbutton일 경우 뒷부분 생략
            return;

        //다음 line 보이게 하기
        lineVector.get(guideMinNum + indexAdd).setVisibility(View.VISIBLE);
        //다음 addButton 보이게 하기
        addbuttonVector.get(++indexAdd).setVisibility(View.VISIBLE);
    }
}


