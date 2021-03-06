package blcs.lwb.utils.fragment.WeChat;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import blcs.lwb.lwbtool.View.FontSizeView;
import blcs.lwb.lwbtool.base.BaseActivity;
import blcs.lwb.lwbtool.base.BasePresenter;
import blcs.lwb.lwbtool.manager.AppManager;
import blcs.lwb.lwbtool.utils.DensityUtils;
import blcs.lwb.lwbtool.utils.IntentUtils;
import blcs.lwb.lwbtool.utils.LogUtils;
import blcs.lwb.lwbtool.utils.SPUtils;
import blcs.lwb.utils.Constants;
import blcs.lwb.utils.MainActivity;
import blcs.lwb.utils.R;
import butterknife.BindView;

public class FontSizeActivity extends BaseActivity {

    @BindView(R.id.tl_toolbar)
    Toolbar tlToolbar;
    @BindView(R.id.fsv_font_size)
    FontSizeView fsvFontSize;
    @BindView(R.id.tv_font_size1)
    TextView tv_font_size1;
    @BindView(R.id.tv_font_size2)
    TextView tv_font_size2;
    @BindView(R.id.tv_font_size3)
    TextView tv_font_size3;
    private float fontSizeScale;

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @Override
    public void initView() {
        initToolbar();
        fsvFontSize.setChangeCallbackListener(new FontSizeView.OnChangeCallbackListener() {
            @Override
            public void onChangeListener(int position) {
                int dimension = getResources().getDimensionPixelSize(R.dimen.sp_stander);
                //字体倍数
                fontSizeScale = (float) (0.875 + 0.125 * position);
                double v = fontSizeScale * (int) DensityUtils.px2sp(FontSizeActivity.this, dimension);
                changeTextSize((int) v);
            }
        });
        float  scale = (float) SPUtils.get(this, Constants.SP_FontScale, 0.0f);
        if(scale>0.5){
            int pos = (int) ((scale - 0.875) / 0.125);
            //注意： 写在改变监听下面 —— 否则初始字体不会改变
            fsvFontSize.setDefaultPosition(pos);
        }else{
            //注意： 写在改变监听下面 —— 否则初始字体不会改变
            fsvFontSize.setDefaultPosition(1);
        }
    }

    /**
     * 改变textsize 大小
     */
    private void changeTextSize(int dimension) {
        tv_font_size1.setTextSize(dimension);
        tv_font_size2.setTextSize(dimension);
        tv_font_size3.setTextSize(dimension);
    }

    private void initToolbar() {
        tlToolbar.setNavigationIcon(R.mipmap.ic_back_white);
        tlToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPUtils.put(FontSizeActivity.this,Constants.SP_FontScale,fontSizeScale);
                    //重启应用
                    AppManager.getAppManager().finishAllActivity();
                    IntentUtils.toActivity(FontSizeActivity.this, MainActivity.class,true);

            }
        });
        tlToolbar.setTitle("字体大小");
        tlToolbar.setTitleTextColor(Color.WHITE);
    }


    @Override
    public int bindLayout() {
        return R.layout.fragment_font_size;
    }

    /**
     * 重新配置缩放系数
     * @return
     */
    @Override
    public Resources getResources() {
        Resources res =super.getResources();
        Configuration config = res.getConfiguration();
        config.fontScale= 1;//1 设置正常字体大小的倍数
        res.updateConfiguration(config,res.getDisplayMetrics());
        return res;
    }
}
