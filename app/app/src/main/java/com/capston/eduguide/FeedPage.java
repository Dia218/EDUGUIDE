package com.capston.eduguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;

public class FeedPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_page);

        ListView listview ;
        ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트 뷰 참조 및 Adapter 달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        // 첫번째 아이템 추가
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.test),
                ContextCompat.getDrawable(this,R.drawable.person),
                "name1","test1","something test 1");
        // 두번째 아이템 추가
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.test),
                ContextCompat.getDrawable(this,R.drawable.person),
                "name2","test2","something test 2");
        // 두번째 아이템 추가
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.test),
                ContextCompat.getDrawable(this,R.drawable.person),
                "name3","test3","something test 3");
        // 위에서 생성한 listview 클릭 이벤트 핸들러 정의.
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;

                String titleStr = item.getTitle() ;
                String descStr = item.getDesc() ;
                String usernameStr = item.getUsername();
                Drawable guideDrawable = item.getIcon();
                Drawable userIconDrawable = item.getUserIcon();
                // TODO : use item data.
                byte[] guideImage = DrawableToByte(guideDrawable);
                byte[] userImage = DrawableToByte(userIconDrawable);

                Intent intent = new Intent(getApplicationContext(),FeedDetail.class);
                intent.putExtra("title",titleStr);
                intent.putExtra("desc",descStr);
                intent.putExtra("username",usernameStr);
                intent.putExtra("image", guideImage);
                intent.putExtra("user",userImage);
                startActivity(intent);
            }

            public byte[] DrawableToByte (Drawable image){
                BitmapDrawable byteImage = (BitmapDrawable) image;
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = byteImage.getBitmap();
                float scale = (float) (1024/(float)bitmap.getWidth());
                int image_w = (int) (bitmap.getWidth() * scale);
                int image_h = (int) (bitmap.getHeight() * scale);
                Bitmap resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true);
                resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                return byteArray;
            }
        });
    }
}