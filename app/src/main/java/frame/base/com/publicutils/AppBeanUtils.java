package frame.base.com.publicutils;

import android.app.Activity;
import android.content.Intent;

import frame.base.com.base.ImagePagerActivity;

/**
 * Created by hantao on 2018/4/25.
 */

public class AppBeanUtils {

    /**
     * @param activity
     * @param imgurl             图片url
     * @param contans_save_share 是否包含保存和分享按钮
     */
    public static void startImagePagerActivity(Activity activity, int showMark, boolean contans_save_share, String... imgurl) {
        Intent intent = new Intent(activity, ImagePagerActivity.class);
        intent.putExtra(ImagePagerActivity.IMAGES, imgurl);
        intent.putExtra(ImagePagerActivity.INTENT_EXTRA_SAVE_SHARE, contans_save_share);
        intent.putExtra(ImagePagerActivity.SHOW_POSITION, showMark);

        activity.startActivity(intent);

    }
}
