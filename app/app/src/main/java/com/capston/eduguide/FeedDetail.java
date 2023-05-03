package com.capston.eduguide;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FeedDetail extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_detail);

        ImageView image = (ImageView) findViewById(R.id.guideImage);
        ImageView userImage = (ImageView) findViewById(R.id.userImage);
        TextView username = (TextView) findViewById(R.id.userName);
        TextView feedTitle = (TextView) findViewById(R.id.guideDesc);
        TextView feedDesc = (TextView) findViewById(R.id.tag);

        Intent secondIntent = getIntent();
        String userName = secondIntent.getStringExtra("username");
        String title = secondIntent.getStringExtra("title");
        String desc = secondIntent.getStringExtra("desc");

        byte[] guide = secondIntent.getByteArrayExtra("image");
        Bitmap guideImage = BitmapFactory.decodeByteArray(guide, 0, guide.length);

        byte[] user = secondIntent.getByteArrayExtra("user");
        Bitmap user_image = BitmapFactory.decodeByteArray(user, 0, user.length);

        username.setText(userName);
        feedTitle.setText(title);
        feedDesc.setText(desc);
        image.setImageBitmap(guideImage);
        userImage.setImageBitmap(user_image);

        Button btnReturn = (Button) findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
