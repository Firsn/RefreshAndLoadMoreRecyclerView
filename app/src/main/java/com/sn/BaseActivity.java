package com.sn;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by 16Fan.Saila2007 on 2017/8/25.
 */

public class BaseActivity extends AppCompatActivity{


    protected Context context=null;
    protected Toast mToast=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void toastMes(Context context, String str){
        if(str==null
                ||"".equals(str)
                ||"null".equalsIgnoreCase(str)){

        }else{
            if (mToast==null){
                mToast=Toast.makeText(context,str,Toast.LENGTH_SHORT);
            }else{
                mToast.setText(str);
            }
            mToast.show();
        }
    }
}
